package Repository;

import Config.JDBC;
import Model.Categoria;
import Model.Producto;
import Model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepository {
    
    private CategoriaRepository categoriaRepository;
    private UsuarioRepository usuarioRepository;
    
    public ProductoRepository() {
        this.categoriaRepository = new CategoriaRepository();
        this.usuarioRepository = new UsuarioRepository();
    }
    
    public List<Producto> obtenerTodos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE activo = true";
        
        try (Connection conn = JDBC.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Producto producto = mapearProducto(rs);
                productos.add(producto);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener productos: " + e.getMessage());
        }
        
        return productos;
    }
    
    public Producto obtenerPorId(int id) {
        String sql = "SELECT * FROM productos WHERE id = ?";
        
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearProducto(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener producto por ID: " + e.getMessage());
        }
        
        return null;
    }
    
    public List<Producto> obtenerPorCategoria(int categoriaId) {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE categoria_id = ? AND activo = true";
        
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, categoriaId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Producto producto = mapearProducto(rs);
                    productos.add(producto);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener productos por categoría: " + e.getMessage());
        }
        
        return productos;
    }
    
    public List<Producto> obtenerPorVendedor(int vendedorId) {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE vendedor_id = ?";
        
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, vendedorId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Producto producto = mapearProducto(rs);
                    productos.add(producto);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener productos por vendedor: " + e.getMessage());
        }
        
        return productos;
    }
    
    public List<Producto> buscar(String termino) {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE (nombre ILIKE ? OR descripcion ILIKE ?) AND activo = true";
        
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String terminoBusqueda = "%" + termino + "%";
            pstmt.setString(1, terminoBusqueda);
            pstmt.setString(2, terminoBusqueda);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Producto producto = mapearProducto(rs);
                    productos.add(producto);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar productos: " + e.getMessage());
        }
        
        return productos;
    }
    
    public int insertar(Producto producto) {
        String sql = "INSERT INTO productos (nombre, descripcion, precio, stock, categoria_id, vendedor_id, imagen, activo) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
        
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getDescripcion());
            pstmt.setBigDecimal(3, producto.getPrecio());
            pstmt.setInt(4, producto.getStock());
            pstmt.setInt(5, producto.getCategoria().getId());
            pstmt.setInt(6, producto.getVendedor().getId());
            pstmt.setString(7, producto.getImagen());
            pstmt.setBoolean(8, producto.isActivo());
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar producto: " + e.getMessage());
        }
        
        return -1;
    }
    
    public boolean actualizar(Producto producto) {
        String sql = "UPDATE productos SET nombre = ?, descripcion = ?, precio = ?, " +
                     "stock = ?, categoria_id = ?, vendedor_id = ?, imagen = ?, activo = ? " +
                     "WHERE id = ?";
        
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getDescripcion());
            pstmt.setBigDecimal(3, producto.getPrecio());
            pstmt.setInt(4, producto.getStock());
            pstmt.setInt(5, producto.getCategoria().getId());
            pstmt.setInt(6, producto.getVendedor().getId());
            pstmt.setString(7, producto.getImagen());
            pstmt.setBoolean(8, producto.isActivo());
            pstmt.setInt(9, producto.getId());
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }
    
    public boolean actualizarStock(int productoId, int nuevoStock) {
        String sql = "UPDATE productos SET stock = ? WHERE id = ?";
        
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, nuevoStock);
            pstmt.setInt(2, productoId);
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar stock: " + e.getMessage());
            return false;
        }
    }
    
    public boolean eliminar(int id) {
        String sql = "UPDATE productos SET activo = false WHERE id = ?";
        
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }
    
    private Producto mapearProducto(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
        producto.setId(rs.getInt("id"));
        producto.setNombre(rs.getString("nombre"));
        producto.setDescripcion(rs.getString("descripcion"));
        producto.setPrecio(rs.getBigDecimal("precio"));
        producto.setStock(rs.getInt("stock"));
        producto.setImagen(rs.getString("imagen"));
        producto.setActivo(rs.getBoolean("activo"));
        
        int categoriaId = rs.getInt("categoria_id");
        Categoria categoria = categoriaRepository.obtenerPorId(categoriaId);
        producto.setCategoria(categoria);
        
        int vendedorId = rs.getInt("vendedor_id");
        Usuario vendedor = usuarioRepository.obtenerPorId(vendedorId);
        producto.setVendedor(vendedor);
        
        return producto;
    }
}