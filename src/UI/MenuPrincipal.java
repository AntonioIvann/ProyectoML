package UI;

import Model.Usuario;

import java.util.Scanner;

/**
 * Menú principal de la aplicación
 * @author v0
 */
public class MenuPrincipal {
    
    private static Scanner scanner;
    private static Usuario usuarioActual;
    private static MenuUsuario menuUsuario;
    private static MenuProducto menuProducto;
    private static MenuCompra menuCompra;
    private static MenuAdmin menuAdmin;
    
    /**
     * Constructor
     */
    public MenuPrincipal() {
        this.scanner = new Scanner(System.in);
        this.menuUsuario = new MenuUsuario(this);
        this.menuProducto = new MenuProducto(this);
        this.menuCompra = new MenuCompra(this);
        this.menuAdmin = new MenuAdmin(this);
    }
    
    /**
     * Inicia el menú principal
     */
    public static void iniciar() {
        boolean salir = false;
        
        while (!salir) {
            mostrarMenu();
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    // Iniciar sesión
                    iniciarSesion();  
                    MenuPrincipal.mostrarMenuUsuarioLogueado();
                    break;
                case 2:
                    // Registrarse
                    registrarse();
                    break;
                case 0:
                    // Salir
                    System.exit(0);
                    System.out.println("¡Gracias por usar MercadoLibre :)!");
                    System.out.println("¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }
        }
    }
    
    /**
     * Muestra el menú principal
     */
    public static void mostrarMenu() {
        System.out.println("\n===== MERCADO LIBRE =====");
        System.out.println("\nInicie sesión para comenzar. :)");
        
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrarse");
            System.out.println("0. Salir");
        
            System.out.print("Seleccione una opción: ");
        }
    
    
    /**
     * Muestra el menú para usuarios logueados
     */
    public static void mostrarMenuUsuarioLogueado() {
        /*if (usuarioActual != null) {
            System.out.println("Usuario: " + usuarioActual.getNombre() + " " + usuarioActual.getApellido());
            mostrarMenuUsuarioLogueado();
        } else {*/
        System.out.println("Bienvenido a Mercado Libre " + usuarioActual.getNombre() + "!");
        System.out.println("0. Cerrar sesión");
        System.out.println("1. Ver mi perfil");
        System.out.println("2. Ver productos");
        System.out.println("3. Buscar productos");
        System.out.println("4. Ver categorías");
        System.out.println("5. Ver carrito");
        System.out.println("6. Mis compras");
        
        if (usuarioActual.isEsVendedor() == true) {
            System.out.println("7. Mis productos");
            System.out.println("8. Agregar producto");
            System.out.println("9. Administración");
            System.out.println("Seleccione una opción: ");
            procesarOpcionUsuarioLogueado();
        }
        
        // Menú de administrador (simulado para el usuario con ID 1)
        if (usuarioActual.isEsVendedor() == false) {
            System.out.println("Sellecione una opcion: ");
            procesarOpcionUsuarioLogueado();
        }
        
        System.out.println("0. Cerrar sesión");
    }
    
    /**
     * Lee una opción del usuario
     * @return Opción seleccionada
     */
    public static int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error en leer opcion: " + e.getMessage());
            return -1;
        }
    }
    
    /**
     * Inicia sesión de un usuario
     */
    public static void iniciarSesion() {
        menuUsuario.iniciarSesion();
    }
    
    /**
     * Registra un nuevo usuario
     */
    public static void registrarse() {
        menuUsuario.registrarse();
    }
    
    /**
     * Procesa la opción seleccionada por un usuario logueado
     * @param opcion Opción seleccionada
     */
    public static void procesarOpcionUsuarioLogueado() {
        Scanner sc = new Scanner(System.in);
        int opcion = sc.nextInt();
        sc.nextLine();
        switch (opcion) {
            case 1:
                // Ver mi perfil
                menuUsuario.verPerfil();
                break;
            case 2:
                // Ver productos
                menuProducto.mostrarProductos();
                break;
            case 3:
                // Buscar productos
                menuProducto.buscarProductos();
                break;
            case 4:
                // Ver categorías
                menuProducto.mostrarCategorias();
                break;
            case 5:
                // Ver carrito
                menuCompra.verCarrito();
                break;
            case 6:
                // Mis compras
                menuCompra.verMisCompras();
                break;
            case 7:
                // Mis productos (solo vendedores)
                if (usuarioActual.isEsVendedor()) {
                    menuProducto.verMisProductos();
                } else {
                    System.out.println("Opción no válida. Intente nuevamente.");
                    MenuPrincipal.mostrarMenuUsuarioLogueado();
                }
                break;
            case 8:
                // Agregar producto (solo vendedores)
                if (usuarioActual.isEsVendedor()) {
                    menuProducto.agregarProducto();
                } else {
                    System.out.println("Opción no válida. Intente nuevamente.");
                    MenuPrincipal.mostrarMenuUsuarioLogueado();
                }
                break;
            case 9:
                // Administración (solo admin)
                if (usuarioActual.getId() == 1) {
                    menuAdmin.mostrarMenu();
                } else {
                    System.out.println("Opción no válida. Intente nuevamente.");
                    MenuPrincipal.mostrarMenuUsuarioLogueado();
                }
                break;
            case 0:
                // Cerrar sesión
                cerrarSesion();
                break;
            default:
                System.out.println("Opción no válida. Intente nuevamente.");
                break;
        }
    }
    
    /**
     * Cierra la sesión del usuario actual
     */
    public static void cerrarSesion() {
        usuarioActual = null;
        MenuPrincipal.iniciar();
        System.out.println("Sesión cerrada correctamente.");
    }
    
    /**
     * Obtiene el usuario actual
     * @return Usuario actual
     */
    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }
    
    /**
     * Establece el usuario actual
     * @param usuarioActual Usuario actual
     */
    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }
    
    /**
     * Obtiene el scanner
     * @return Scanner
     */
    public static Scanner getScanner() {
        return scanner;
    }
    
    /**
     * Método principal
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        MenuPrincipal.iniciar();
    }
}