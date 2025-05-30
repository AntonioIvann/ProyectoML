package Repository;

import Config.JDBC;
import Model.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRepository {
    public List<Categoria> obtenerTodas() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categorias";
        
        try (Connection conn = JDBC.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Categoria categoria = mapearCategoria(rs);
                categorias.add(categoria);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener categorías: " + e.getMessage());
        }
        
        return categorias;
    }
    
    public Categoria obtenerPorId(int id) {
        String sql = "SELECT * FROM categorias WHERE id = ?";
        
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearCategoria(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener categoría por ID: " + e.getMessage());
        }
        
        return null;
    }

    public int insertar(Categoria categoria) {
        String sql = "INSERT INTO categorias (nombre, descripcion) VALUES (?, ?) RETURNING id";
        
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, categoria.getNombre());
            pstmt.setString(2, categoria.getDescripcion());
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar categoría: " + e.getMessage());
        }
        
        return -1;
    }
    public boolean actualizar(Categoria categoria) {
        String sql = "UPDATE categorias SET nombre = ?, descripcion = ? WHERE id = ?";
        
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, categoria.getNombre());
            pstmt.setString(2, categoria.getDescripcion());
            pstmt.setInt(3, categoria.getId());
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar categoría: " + e.getMessage());
            return false;
        }
    }
    public boolean eliminar(int id) {
        String sql = "DELETE FROM categorias WHERE id = ?";
        
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar categoría: " + e.getMessage());
            return false;
        }
    }
    private Categoria mapearCategoria(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setId(rs.getInt("id"));
        categoria.setNombre(rs.getString("nombre"));
        categoria.setDescripcion(rs.getString("descripcion"));
        return categoria;
    }
}