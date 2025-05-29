package UI;

import Controller.CategoriaController;
import Controller.CompraController;
import Controller.UsuarioController;
import Model.Categoria;
import Model.Compra;
import Model.Usuario;
import Model.DetalleCompra;

import java.util.List;
import java.util.Scanner;

/**
 * Menú para la administración del sistema
 * @author v0
 */
public class MenuAdmin {
    
    private static MenuPrincipal menuPrincipal;
    private static Scanner scanner;
    private static UsuarioController usuarioController;
    private static CategoriaController categoriaController;
    private static CompraController compraController;
    
    /**
     * Constructor
     * @param menuPrincipal Menú principal
     */
    public MenuAdmin(MenuPrincipal menuPrincipal) {
        this.menuPrincipal = menuPrincipal;
        this.scanner = menuPrincipal.getScanner();
        this.usuarioController = new UsuarioController();
        this.categoriaController = new CategoriaController();
        this.compraController = new CompraController();
    }
    
    /**
     * Muestra el menú de administración
     */
    public static void mostrarMenu() {
        Usuario usuarioActual = menuPrincipal.getUsuarioActual();
        
        // Verificar que el usuario sea administrador (simulado con ID 1)
        if (usuarioActual != null && usuarioActual.getId() == 1) {
            boolean salir = false;
            
            while (!salir) {
                System.out.println("\n===== ADMINISTRACIÓN =====");
                System.out.println("1. Gestionar usuarios");
                System.out.println("2. Gestionar categorías");
                System.out.println("3. Gestionar compras");
                System.out.println("0. Volver al menú principal");
                
                System.out.print("Seleccione una opción: ");
                int opcion = Integer.parseInt(scanner.nextLine());
                
                switch (opcion) {
                    case 1:
                        gestionarUsuarios();
                        break;
                    case 2:
                        gestionarCategorias();
                        break;
                    case 3:
                        gestionarCompras();
                        break;
                    case 0:
                    MenuPrincipal.mostrarMenuUsuarioLogueado();
                        break;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                        break;
                }
            }
        } else {
            System.out.println("No tiene permisos de administrador.");
        }
    }
    
    /**
     * Gestiona los usuarios
     */
    private static void gestionarUsuarios() {
        boolean salir = false;
        
        while (!salir) {
            System.out.println("\n===== GESTIÓN DE USUARIOS =====");
            System.out.println("1. Ver todos los usuarios");
            System.out.println("2. Buscar usuario por ID");
            System.out.println("3. Eliminar usuario");
            System.out.println("0. Volver");
            
            System.out.print("Seleccione una opción: ");
            int opcion = Integer.parseInt(scanner.nextLine());
            
            switch (opcion) {
                case 1:
                    verTodosLosUsuarios();
                    break;
                case 2:
                    buscarUsuarioPorId();
                    break;
                case 3:
                    eliminarUsuario();
                    break;
                case 0:
                    MenuAdmin.mostrarMenu();
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }
        }
    }
    
    /**
     * Muestra todos los usuarios
     */
    private static void verTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioController.obtenerTodos();
        
        System.out.println("\n===== USUARIOS =====");
        
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            for (Usuario usuario : usuarios) {
                System.out.println("ID: " + usuario.getId() + 
                                  " - Nombre: " + usuario.getNombre() + " " + usuario.getApellido() + 
                                  " - Email: " + usuario.getEmail() + 
                                  " - Tipo: " + (usuario.isEsVendedor() ? "Vendedor" : "Comprador"));
            }
        }
    }
    
    /**
     * Busca un usuario por su ID
     */
    private static void buscarUsuarioPorId() {
        System.out.print("Ingrese el ID del usuario: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        Usuario usuario = usuarioController.obtenerPorId(id);
        
        if (usuario != null) {
            System.out.println("\n===== DETALLE DE USUARIO =====");
            System.out.println("ID: " + usuario.getId());
            System.out.println("Nombre: " + usuario.getNombre());
            System.out.println("Apellido: " + usuario.getApellido());
            System.out.println("Email: " + usuario.getEmail());
            System.out.println("Teléfono: " + usuario.getTelefono());
            System.out.println("Dirección: " + usuario.getDireccion());
            System.out.println("Tipo: " + (usuario.isEsVendedor() ? "Vendedor" : "Comprador"));
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }
    
    /**
     * Elimina un usuario
     */
    private static void eliminarUsuario() {
        System.out.print("Ingrese el ID del usuario a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        // No permitir eliminar al administrador
        if (id == 1) {
            System.out.println("No se puede eliminar al administrador.");
            return;
        }
        
        Usuario usuario = usuarioController.obtenerPorId(id);
        
        if (usuario != null) {
            System.out.println("Usuario: " + usuario.getNombre() + " " + usuario.getApellido());
            System.out.print("¿Está seguro de eliminar este usuario? (S/N): ");
            String confirmacion = scanner.nextLine();
            
            if (confirmacion.equalsIgnoreCase("S")) {
                boolean eliminado = usuarioController.eliminar(id);
                
                if (eliminado) {
                    System.out.println("Usuario eliminado correctamente.");
                } else {
                    System.out.println("Error al eliminar el usuario.");
                }
            } else {
                System.out.println("Operación cancelada.");
            }
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }
    
    /**
     * Gestiona las categorías
     */
    private static void gestionarCategorias() {
        boolean salir = false;
        
        while (!salir) {
            System.out.println("\n===== GESTIÓN DE CATEGORÍAS =====");
            System.out.println("1. Ver todas las categorías");
            System.out.println("2. Agregar categoría");
            System.out.println("3. Editar categoría");
            System.out.println("4. Eliminar categoría");
            System.out.println("0. Volver");
            
            System.out.print("Seleccione una opción: ");
            int opcion = Integer.parseInt(scanner.nextLine());
            
            switch (opcion) {
                case 1:
                    verTodasLasCategorias();
                    break;
                case 2:
                    agregarCategoria();
                    break;
                case 3:
                    editarCategoria();
                    break;
                case 4:
                    eliminarCategoria();
                    break;
                case 0:
                MenuAdmin.mostrarMenu();
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }
        }
    }
    
    /**
     * Muestra todas las categorías
     */
    private static void verTodasLasCategorias() {
        List<Categoria> categorias = categoriaController.obtenerTodas();
        
        System.out.println("\n===== CATEGORÍAS =====");
        
        if (categorias.isEmpty()) {
            System.out.println("No hay categorías registradas.");
        } else {
            for (Categoria categoria : categorias) {
                System.out.println("ID: " + categoria.getId() + 
                                  " - Nombre: " + categoria.getNombre() + 
                                  " - Descripción: " + categoria.getDescripcion());
            }
        }
    }
    
    /**
     * Agrega una nueva categoría
     */
    private static void agregarCategoria() {
        System.out.println("\n===== AGREGAR CATEGORÍA =====");
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        
        int id = categoriaController.registrar(nombre, descripcion);
        
        if (id > 0) {
            System.out.println("Categoría agregada correctamente con ID: " + id);
        } else {
            System.out.println("Error al agregar la categoría.");
        }
    }
    
    /**
     * Edita una categoría
     */
    private static void editarCategoria() {
        System.out.print("Ingrese el ID de la categoría a editar: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        Categoria categoria = categoriaController.obtenerPorId(id);
        
        if (categoria != null) {
            System.out.println("\n===== EDITAR CATEGORÍA =====");
            
            System.out.print("Nombre [" + categoria.getNombre() + "]: ");
            String nombre = scanner.nextLine();
            if (nombre.trim().isEmpty()) {
                nombre = categoria.getNombre();
            }
            
            System.out.print("Descripción [" + categoria.getDescripcion() + "]: ");
            String descripcion = scanner.nextLine();
            if (descripcion.trim().isEmpty()) {
                descripcion = categoria.getDescripcion();
            }
            
            boolean actualizado = categoriaController.actualizar(id, nombre, descripcion);
            
            if (actualizado) {
                System.out.println("Categoría actualizada correctamente.");
            } else {
                System.out.println("Error al actualizar la categoría.");
            }
        } else {
            System.out.println("Categoría no encontrada.");
        }
    }
    
    /**
     * Elimina una categoría
     */
    private static void eliminarCategoria() {
        System.out.print("Ingrese el ID de la categoría a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        Categoria categoria = categoriaController.obtenerPorId(id);
        
        if (categoria != null) {
            System.out.println("Categoría: " + categoria.getNombre());
            System.out.print("¿Está seguro de eliminar esta categoría? (S/N): ");
            String confirmacion = scanner.nextLine();
            
            if (confirmacion.equalsIgnoreCase("S")) {
                boolean eliminado = categoriaController.eliminar(id);
                
                if (eliminado) {
                    System.out.println("Categoría eliminada correctamente.");
                } else {
                    System.out.println("Error al eliminar la categoría.");
                }
            } else {
                System.out.println("Operación cancelada.");
            }
        } else {
            System.out.println("Categoría no encontrada.");
        }
    }
    
    /**
     * Gestiona las compras
     */
    private static void gestionarCompras() {
        boolean salir = false;
        
        while (!salir) {
            System.out.println("\n===== GESTIÓN DE COMPRAS =====");
            System.out.println("1. Ver todas las compras");
            System.out.println("2. Ver detalle de compra");
            System.out.println("3. Actualizar estado de compra");
            System.out.println("0. Volver");
            
            System.out.print("Seleccione una opción: ");
            int opcion = Integer.parseInt(scanner.nextLine());
            
            switch (opcion) {
                case 1:
                    verTodasLasCompras();
                    break;
                case 2:
                    verDetalleCompra();
                    break;
                case 3:
                    actualizarEstadoCompra();
                    break;
                case 0:
                    MenuAdmin.mostrarMenu();
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }
        }
    }
    
    /**
     * Muestra todas las compras
     */
    private static void verTodasLasCompras() {
        List<Compra> compras = compraController.obtenerTodas();
        
        System.out.println("\n===== COMPRAS =====");
        
        if (compras.isEmpty()) {
            System.out.println("No hay compras registradas.");
        } else {
            for (Compra compra : compras) {
                System.out.println("ID: " + compra.getId() + 
                                  " - Comprador: " + compra.getComprador().getNombre() + " " + compra.getComprador().getApellido() + 
                                  " - Fecha: " + compra.getFecha() + 
                                  " - Total: $" + compra.getTotal() + 
                                  " - Estado: " + compra.getEstado());
            }
        }
    }
    
    /**
     * Muestra el detalle de una compra
     */
    private static void verDetalleCompra() {
        System.out.print("Ingrese el ID de la compra: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        Compra compra = compraController.obtenerPorId(id);
        
        if (compra != null) {
            System.out.println("\n===== DETALLE DE COMPRA =====");
            System.out.println("ID: " + compra.getId());
            System.out.println("Comprador: " + compra.getComprador().getNombre() + " " + compra.getComprador().getApellido());
            System.out.println("Email: " + compra.getComprador().getEmail());
            System.out.println("Fecha: " + compra.getFecha());
            System.out.println("Estado: " + compra.getEstado());
            System.out.println("Total: $" + compra.getTotal());
            
            System.out.println("\nProductos:");
            for (DetalleCompra detalle : compra.getDetalles()) {
                System.out.println("- " + detalle.getProducto().getNombre() + 
                                  " - Cantidad: " + detalle.getCantidad() + 
                                  " - Precio: $" + detalle.getPrecioUnitario() + 
                                  " - Subtotal: $" + detalle.getSubtotal());
            }
        } else {
            System.out.println("Compra no encontrada.");
        }
    }
    
    /**
     * Actualiza el estado de una compra
     */
    private static void actualizarEstadoCompra() {
        System.out.print("Ingrese el ID de la compra: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        Compra compra = compraController.obtenerPorId(id);
        
        if (compra != null) {
            System.out.println("Compra ID: " + compra.getId());
            System.out.println("Estado actual: " + compra.getEstado());
            
            System.out.println("\nEstados disponibles:");
            System.out.println("1. Pendiente");
            System.out.println("2. Pagado");
            System.out.println("3. Enviado");
            System.out.println("4. Entregado");
            System.out.println("5. Cancelado");
            
            System.out.print("Seleccione el nuevo estado: ");
            int opcion = Integer.parseInt(scanner.nextLine());
            
            String nuevoEstado;
            switch (opcion) {
                case 1:
                    nuevoEstado = "Pendiente";
                    break;
                case 2:
                    nuevoEstado = "Pagado";
                    break;
                case 3:
                    nuevoEstado = "Enviado";
                    break;
                case 4:
                    nuevoEstado = "Entregado";
                    break;
                case 5:
                    nuevoEstado = "Cancelado";
                    break;
                default:
                    System.out.println("Opción no válida.");
                    return;
            }
            
            boolean actualizado = compraController.actualizarEstado(id, nuevoEstado);
            
            if (actualizado) {
                System.out.println("Estado de compra actualizado correctamente.");
            } else {
                System.out.println("Error al actualizar el estado de la compra.");
            }
        } else {
            System.out.println("Compra no encontrada.");
        }
    }
}