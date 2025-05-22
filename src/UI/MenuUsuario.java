package UI;

import Controller.UsuarioController;
import Model.Usuario;

import java.util.Scanner;

/**
 * Menú para la gestión de usuarios
 * @author v0
 */
public class MenuUsuario {
    
    private MenuPrincipal menuPrincipal;
    private Scanner scanner;
    private UsuarioController usuarioController;
    
    /**
     * Constructor
     * @param menuPrincipal Menú principal
     */
    public MenuUsuario(MenuPrincipal menuPrincipal) {
        this.menuPrincipal = menuPrincipal;
        this.scanner = menuPrincipal.getScanner();
        this.usuarioController = new UsuarioController();
    }
    
    /**
     * Inicia sesión de un usuario
     */
    public void iniciarSesion() {
        System.out.println("\n===== INICIAR SESIÓN =====");
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();
        
        Usuario usuario = usuarioController.autenticar(email, password);
        
        if (usuario != null) {
            System.out.println("¡Bienvenido, " + usuario.getNombre() + "!");
            menuPrincipal.setUsuarioActual(usuario);
        } else {
            System.out.println("Credenciales incorrectas. Intente nuevamente.");
        }
    }
    
    /**
     * Registra un nuevo usuario
     */
    public void registrarse() {
        System.out.println("\n===== REGISTRARSE =====");
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();
        
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        
        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();
        
        System.out.print("¿Es vendedor? (S/N): ");
        boolean esVendedor = scanner.nextLine().equalsIgnoreCase("S");
        
        int id = usuarioController.registrar(nombre, apellido, email, password, telefono, direccion, esVendedor);
        
        if (id > 0) {
            System.out.println("Usuario registrado correctamente con ID: " + id);
            System.out.println("Ahora puede iniciar sesión con sus credenciales.");
        } else {
            System.out.println("Error al registrar el usuario. Intente nuevamente.");
        }
    }
    
    /**
     * Muestra el perfil del usuario actual
     */
    public void verPerfil() {
        Usuario usuario = menuPrincipal.getUsuarioActual();
        
        if (usuario != null) {
            System.out.println("\n===== MI PERFIL =====");
            System.out.println("ID: " + usuario.getId());
            System.out.println("Nombre: " + usuario.getNombre());
            System.out.println("Apellido: " + usuario.getApellido());
            System.out.println("Email: " + usuario.getEmail());
            System.out.println("Teléfono: " + usuario.getTelefono());
            System.out.println("Dirección: " + usuario.getDireccion());
            System.out.println("Tipo: " + (usuario.isEsVendedor() ? "Vendedor" : "Comprador"));
            
            System.out.println("\n1. Editar perfil");
            System.out.println("0. Volver");
            
            System.out.print("Seleccione una opción: ");
            int opcion = Integer.parseInt(scanner.nextLine());
            
            switch (opcion) {
                case 1:
                    editarPerfil();
                    break;
                case 0:
                    // Volver
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }
        }
    }
    
    /**
     * Edita el perfil del usuario actual
     */
    private void editarPerfil() {
        Usuario usuario = menuPrincipal.getUsuarioActual();
        
        if (usuario != null) {
            System.out.println("\n===== EDITAR PERFIL =====");
            
            System.out.print("Nombre [" + usuario.getNombre() + "]: ");
            String nombre = scanner.nextLine();
            if (nombre.trim().isEmpty()) {
                nombre = usuario.getNombre();
            }
            
            System.out.print("Apellido [" + usuario.getApellido() + "]: ");
            String apellido = scanner.nextLine();
            if (apellido.trim().isEmpty()) {
                apellido = usuario.getApellido();
            }
            
            System.out.print("Email [" + usuario.getEmail() + "]: ");
            String email = scanner.nextLine();
            if (email.trim().isEmpty()) {
                email = usuario.getEmail();
            }
            
            System.out.print("Contraseña (dejar en blanco para mantener la actual): ");
            String password = scanner.nextLine();
            
            System.out.print("Teléfono [" + usuario.getTelefono() + "]: ");
            String telefono = scanner.nextLine();
            if (telefono.trim().isEmpty()) {
                telefono = usuario.getTelefono();
            }
            
            System.out.print("Dirección [" + usuario.getDireccion() + "]: ");
            String direccion = scanner.nextLine();
            if (direccion.trim().isEmpty()) {
                direccion = usuario.getDireccion();
            }
            
            System.out.print("¿Es vendedor? (S/N) [" + (usuario.isEsVendedor() ? "S" : "N") + "]: ");
            String esVendedorStr = scanner.nextLine();
            boolean esVendedor = esVendedorStr.equalsIgnoreCase("S") || 
                                (esVendedorStr.trim().isEmpty() && usuario.isEsVendedor());
            
            boolean actualizado = usuarioController.actualizar(
                usuario.getId(), nombre, apellido, email, password, telefono, direccion, esVendedor
            );
            
            if (actualizado) {
                System.out.println("Perfil actualizado correctamente.");
                // Actualizar el usuario actual con los nuevos datos
                Usuario usuarioActualizado = usuarioController.obtenerPorId(usuario.getId());
                menuPrincipal.setUsuarioActual(usuarioActualizado);
            } else {
                System.out.println("Error al actualizar el perfil. Intente nuevamente.");
            }
        }
    }
}