import UI.MenuPrincipal;

public class MercadoLibre {
    public static void main(String[] args) {
        try {
            System.out.println("Iniciando MercadoLibre...");
            
            // Crear una instancia del menú principal
            MenuPrincipal menuPrincipal = new MenuPrincipal();
            
            // Iniciar la aplicación
            menuPrincipal.iniciar();
            
        } catch (Exception e) {
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}