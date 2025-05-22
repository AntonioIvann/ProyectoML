package Config;

import java.sql.*;

public class JDBC {
    // Constantes de conexión
    private static final String URL = "jdbc:postgresql://localhost:5432/MercadoLibre";
    private static final String USER = "admin";
    private static final String PASSWORD = "123456";
    
    private static Connection connection = null;
    
    /**
     * Obtiene una conexión a la base de datos
     * @return Conexión a la base de datos
     * @throws SQLException Si ocurre un error al conectar
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Cargar el driver de PostgreSQL
                Class.forName("org.postgresql.Driver");
                
                // Crear la conexión
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión a la base de datos establecida correctamente");
            } catch (ClassNotFoundException e) {
                System.err.println("Error al cargar el driver de PostgreSQL: " + e.getMessage());
                throw new SQLException("No se pudo cargar el driver de PostgreSQL", e);
            } catch (SQLException e) {
                System.err.println("Error al conectar a la base de datos: " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }
    
    /**
     * Cierra la conexión a la base de datos
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexión a la base de datos cerrada correctamente");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}