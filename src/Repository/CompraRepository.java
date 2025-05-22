package Repository;

import Config.JDBC;
import Model.Compra;
import Model.DetalleCompra;
import Model.Producto;
import Model.Usuario;

import java.sql.*;
import java.util.*;

/**
 * Repositorio para operaciones CRUD de compras en la base de datos
 * @author v0
 */
public class CompraRepository {
    
    private UsuarioRepository usuarioRepository;
    private ProductoRepository productoRepository;
    
    /**
     * Constructor
     */
    public CompraRepository() {
        this.usuarioRepository = new UsuarioRepository();
        this.productoRepository = new ProductoRepository();
    }
    
    /**
     * Obtiene todas las compras de la base de datos
     * @return Lista de compras
     */
    public List<Compra> obtenerTodas() {
        List<Compra> compras = new ArrayList<>();
        String sql = "SELECT * FROM compras";
        
        try (Connection conn = JDBC.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Compra compra = mapearCompra(rs);
                // Obtener los detalles de la compra
                compra.setDetalles(obtenerDetallesPorCompra(compra.getId()));
                compras.add(compra);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener compras: " + e.getMessage());
        }
        
        return compras;
    }
    
    /**
     * Obtiene una compra por su ID
     * @param id ID de la compra
     * @return Compra encontrada o null si no existe
     */
    public Compra obtenerPorId(int id) {
        String sql = "SELECT * FROM compras WHERE id = ?";
        
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Compra compra = mapearCompra(rs);
                    // Obtener los detalles de la compra
                    compra.setDetalles(obtenerDetallesPorCompra(compra.getId()));
                    return compra;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener compra por ID: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Obtiene las compras de un usuario
     * @param usuarioId ID del usuario
     * @return Lista de compras del usuario
     */
    public List<Compra> obtenerPorUsuario(int usuarioId) {
        List<Compra> compras = new ArrayList<>();
        String sql = "SELECT * FROM compras WHERE comprador_id = ?";
        
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, usuarioId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Compra compra = mapearCompra(rs);
                    // Obtener los detalles de la compra
                    compra.setDetalles(obtenerDetallesPorCompra(compra.getId()));
                    compras.add(compra);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener compras por usuario: " + e.getMessage());
        }
        
        return compras;
    }
    
    /**
     * Inserta una nueva compra en la base de datos
     * @param compra Compra a insertar
     * @return ID de la compra insertada o -1 si falla
     */
    public int insertar(Compra compra) {
        String sql = "INSERT INTO compras (comprador_id, fecha, total, estado) " +
                     "VALUES (?, ?, ?, ?) RETURNING id";
        
        Connection conn = null;
        try {
            conn = JDBC.getConnection();
            conn.setAutoCommit(false);
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, compra.getComprador().getId());
                pstmt.setTimestamp(2, new Timestamp(compra.getFecha().getTime()));
                pstmt.setBigDecimal(3, compra.getTotal());
                pstmt.setString(4, compra.getEstado());
                
                int compraId;
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        compraId = rs.getInt(1);
                        
                        // Insertar los detalles de la compra
                        for (DetalleCompra detalle : compra.getDetalles()) {
                            detalle.getCompra().setId(compraId);
                            int detalleId = insertarDetalle(detalle, conn);
                            if (detalleId == -1) {
                                conn.rollback();
                                return -1;
                            }
                            
                            // Actualizar el stock del producto
                            Producto producto = detalle.getProducto();
                            int nuevoStock = producto.getStock() - detalle.getCantidad();
                            if (nuevoStock < 0) {
                                conn.rollback();
                                return -1;
                            }
                            
                            if (!productoRepository.actualizarStock(producto.getId(), nuevoStock)) {
                                conn.rollback();
                                return -1;
                            }
                        }
                        
                        conn.commit();
                        return compraId;
                    } else {
                        conn.rollback();
                        return -1;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar compra: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
            return -1;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    System.err.println("Error al restaurar autocommit: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Actualiza el estado de una compra
     * @param compraId ID de la compra
     * @param nuevoEstado Nuevo estado de la compra
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizarEstado(int compraId, String nuevoEstado) {
        String sql = "UPDATE compras SET estado = ? WHERE id = ?";
        
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nuevoEstado);
            pstmt.setInt(2, compraId);
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar estado de compra: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Obtiene los detalles de una compra
     * @param compraId ID de la compra
     * @return Lista de detalles de la compra
     */
    public List<DetalleCompra> obtenerDetallesPorCompra(int compraId) {
        List<DetalleCompra> detalles = new ArrayList<>();
        String sql = "SELECT * FROM detalles_compra WHERE compra_id = ?";
        
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, compraId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    DetalleCompra detalle = mapearDetalle(rs);
                    detalles.add(detalle);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener detalles de compra: " + e.getMessage());
        }
        
        return detalles;
    }
    
    /**
     * Inserta un detalle de compra en la base de datos
     * @param detalle Detalle a insertar
     * @param conn Conexión a la base de datos
     * @return ID del detalle insertado o -1 si falla
     * @throws SQLException Si ocurre un error en la base de datos
     */
    private int insertarDetalle(DetalleCompra detalle, Connection conn) throws SQLException {
        String sql = "INSERT INTO detalles_compra (compra_id, producto_id, cantidad, precio_unitario, subtotal) " +
                     "VALUES (?, ?, ?, ?, ?) RETURNING id";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, detalle.getCompra().getId());
            pstmt.setInt(2, detalle.getProducto().getId());
            pstmt.setInt(3, detalle.getCantidad());
            pstmt.setBigDecimal(4, detalle.getPrecioUnitario());
            pstmt.setBigDecimal(5, detalle.getSubtotal());
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        
        return -1;
    }
    
    /**
     * Mapea un ResultSet a un objeto Compra
     * @param rs ResultSet con los datos de la compra
     * @return Objeto Compra
     * @throws SQLException Si ocurre un error al acceder a los datos
     */
    private Compra mapearCompra(ResultSet rs) throws SQLException {
        Compra compra = new Compra();
        compra.setId(rs.getInt("id"));
        
        // Obtener el comprador
        int compradorId = rs.getInt("comprador_id");
        Usuario comprador = usuarioRepository.obtenerPorId(compradorId);
        compra.setComprador(comprador);
        
        compra.setFecha(rs.getTimestamp("fecha"));
        compra.setTotal(rs.getBigDecimal("total"));
        compra.setEstado(rs.getString("estado"));
        
        return compra;
    }
    
    /**
     * Mapea un ResultSet a un objeto DetalleCompra
     * @param rs ResultSet con los datos del detalle
     * @return Objeto DetalleCompra
     * @throws SQLException Si ocurre un error al acceder a los datos
     */
    private DetalleCompra mapearDetalle(ResultSet rs) throws SQLException {
        DetalleCompra detalle = new DetalleCompra();
        detalle.setId(rs.getInt("id"));
        
        // Obtener la compra
        int compraId = rs.getInt("compra_id");
        Compra compra = new Compra();
        compra.setId(compraId);
        detalle.setCompra(compra);
        
        // Obtener el producto
        int productoId = rs.getInt("producto_id");
        Producto producto = productoRepository.obtenerPorId(productoId);
        detalle.setProducto(producto);
        
        detalle.setCantidad(rs.getInt("cantidad"));
        detalle.setPrecioUnitario(rs.getBigDecimal("precio_unitario"));
        detalle.setSubtotal(rs.getBigDecimal("subtotal"));
        
        return detalle;
    }
}