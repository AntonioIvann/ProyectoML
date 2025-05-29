package Config;

// Se importan las clases necesarias para manejar conexiones SQL
import java.sql.*;

public class JDBC {
    // Constantes para la URL de conexión, el usuario y la contraseña de la base de datos
    private static final String URL = "jdbc:postgresql://localhost:5432/MercadoLibre"; 
    private static final String USER = "admin"; 
    private static final String PASSWORD = "123456";

    // Variable estática para mantener una única instancia de la conexión
    private static Connection connection = null;

    // Método público y estático para obtener una conexión a la base de datos
    public static Connection getConnection() throws SQLException {
        // Verifica si la conexión es nula o está cerrada antes de crear una nueva
        if (connection == null || connection.isClosed()) {
            try {
                // Carga el driver JDBC para PostgreSQL
                Class.forName("org.postgresql.Driver");

                // Establece la conexión usando los datos especificados (URL, usuario y contraseña)
                connection = DriverManager.getConnection(URL, USER, PASSWORD);

                // Mensaje de éxito en la conexión
                System.out.println("Conexión a la base de datos establecida correctamente");
            } catch (ClassNotFoundException e) {
                // Error al cargar el driver
                System.err.println("Error al cargar el driver de PostgreSQL: " + e.getMessage());

                // Lanza una excepción de tipo SQLException si falla la carga del driver
                throw new SQLException("No se pudo cargar el driver de PostgreSQL", e);
            } catch (SQLException e) {
                // Error al intentar conectarse a la base de datos
                System.err.println("Error al conectar a la base de datos: " + e.getMessage());

                // Lanza nuevamente la excepción para que pueda ser manejada externamente
                throw e;
            }
        }

        // Retorna la conexión activa (o recién creada)
        return connection;
    }

    // Método público y estático para cerrar la conexión a la base de datos
    public static void closeConnection() {
        // Verifica que la conexión no sea nula antes de intentar cerrarla
        if (connection != null) {
            try {
                // Cierra la conexión
                connection.close();

                // Mensaje de éxito al cerrar la conexión
                System.out.println("Conexión a la base de datos cerrada correctamente");
            } catch (SQLException e) {
                // Error al cerrar la conexión
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
