package UI;

import Controller.CompraController;
import Model.Carrito;
import Model.Compra;
import Model.DetalleCompra;
import Model.ItemCarrito;
import Model.Usuario;

import java.util.List;
import java.util.Scanner;

/**
 * Menú para la gestión de compras
 * @author v0
 */
public class MenuCompra {
    
    private static MenuPrincipal menuPrincipal;
    private static Scanner scanner;
    private static CompraController compraController;
    
    /**
     * Constructor
     * @param menuPrincipal Menú principal
     */
    public MenuCompra(MenuPrincipal menuPrincipal) {
        this.menuPrincipal = menuPrincipal;
        this.scanner = menuPrincipal.getScanner();
        this.compraController = new CompraController();
    }
    
    /**
     * Muestra el carrito de compras
     */
    public static void verCarrito() {
        Usuario usuarioActual = menuPrincipal.getUsuarioActual();
        
        if (usuarioActual != null) {
            // Crear carrito si no existe en la sesión (simulado)
            Carrito carrito = compraController.crearCarrito(usuarioActual.getId());
            
            System.out.println("\n===== CARRITO DE COMPRAS =====");
            
            if (carrito.getItems().isEmpty()) {
                System.out.println("El carrito está vacío.");
                MenuPrincipal.mostrarMenuUsuarioLogueado();
            } else {
                System.out.println("Productos en el carrito:");
                int i = 1;
                for (ItemCarrito item : carrito.getItems()) {
                    System.out.println(i + ". " + item.getProducto().getNombre() + 
                                      " - Cantidad: " + item.getCantidad() + 
                                      " - Precio: $" + item.getPrecioUnitario() + 
                                      " - Subtotal: $" + item.getSubtotal());
                    i++;
                }
                
                System.out.println("\nTotal: $" + carrito.getTotal());
                
                System.out.println("\n1. Realizar compra");
                System.out.println("2. Eliminar producto del carrito");
                System.out.println("3. Vaciar carrito");
                System.out.println("0. Volver");
                
                System.out.print("Seleccione una opción: ");
                int opcion = Integer.parseInt(scanner.nextLine());
                
                switch (opcion) {
                    case 1:
                        realizarCompra(carrito);
                        MenuPrincipal.mostrarMenuUsuarioLogueado();
                        break;
                    case 2:
                        eliminarDelCarrito(carrito);
                        break;
                    case 3:
                        vaciarCarrito(carrito);
                        break;
                    case 0:
                        MenuPrincipal.mostrarMenuUsuarioLogueado();
                        // Volver
                        break;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                        break;
                }
            }
        } else {
            System.out.println("Debe iniciar sesión para ver el carrito.");
        }
    }
    
    /**
     * Realiza una compra
     * @param carrito Carrito de compras
     */
    private static void realizarCompra(Carrito carrito) {
        if (carrito.getItems().isEmpty()) {
            System.out.println("El carrito está vacío.");
            return;
        }
        
        System.out.println("\n===== REALIZAR COMPRA =====");
        System.out.println("Total a pagar: $" + carrito.getTotal());
        System.out.print("¿Confirmar compra? (S/N): ");
        String confirmacion = scanner.nextLine();
        
        if (confirmacion.equalsIgnoreCase("S")) {
            int compraId = compraController.realizarCompra(carrito);
            
            if (compraId > 0) {
                System.out.println("¡Compra realizada correctamente!");
                System.out.println("ID de compra: " + compraId);
            } else {
                System.out.println("Error al realizar la compra. Intente nuevamente.");
            }
        } else {
            System.out.println("Compra cancelada.");
        }
    }
    
    /**
     * Elimina un producto del carrito
     * @param carrito Carrito de compras
     */
    private static void eliminarDelCarrito(Carrito carrito) {
        if (carrito.getItems().isEmpty()) {
            System.out.println("El carrito está vacío.");
            return;
        }
        
        System.out.print("Ingrese el número del producto a eliminar: ");
        int indice = Integer.parseInt(scanner.nextLine()) - 1;
        
        if (indice >= 0 && indice < carrito.getItems().size()) {
            ItemCarrito item = carrito.getItems().get(indice);
            boolean eliminado = compraController.eliminarDelCarrito(carrito, item.getProducto().getId());
            
            if (eliminado) {
                System.out.println("Producto eliminado del carrito.");
            } else {
                System.out.println("Error al eliminar el producto del carrito.");
            }
        } else {
            System.out.println("Número de producto no válido.");
        }
    }
    
    /**
     * Vacía el carrito
     * @param carrito Carrito de compras
     */
    private static void vaciarCarrito(Carrito carrito) {
        if (carrito.getItems().isEmpty()) {
            System.out.println("El carrito ya está vacío.");
            return;
        }
        
        System.out.print("¿Está seguro de vaciar el carrito? (S/N): ");
        String confirmacion = scanner.nextLine();
        
        if (confirmacion.equalsIgnoreCase("S")) {
            boolean vaciado = compraController.vaciarCarrito(carrito);
            
            if (vaciado) {
                System.out.println("Carrito vaciado correctamente.");
            } else {
                System.out.println("Error al vaciar el carrito.");
            }
        } else {
            System.out.println("Operación cancelada.");
        }
    }
    
    /**
     * Muestra las compras del usuario actual
     */
    public static void verMisCompras() {
        Usuario usuarioActual = menuPrincipal.getUsuarioActual();
        
        if (usuarioActual != null) {
            List<Compra> compras = compraController.obtenerPorUsuario(usuarioActual.getId());
            
            System.out.println("\n===== MIS COMPRAS =====");
            
            if (compras.isEmpty()) {
                System.out.println("No tiene compras registradas.");
                MenuPrincipal.mostrarMenuUsuarioLogueado();
            } else {
                for (Compra compra : compras) {
                    System.out.println("ID: " + compra.getId() + 
                                      " - Fecha: " + compra.getFecha() + 
                                      " - Total: $" + compra.getTotal() + 
                                      " - Estado: " + compra.getEstado());
                }
                
                System.out.println("\n1. Ver detalle de compra");
                System.out.println("0. Volver");
                
                System.out.print("Seleccione una opción: ");
                int opcion = Integer.parseInt(scanner.nextLine());
                
                switch (opcion) {
                    case 1:
                        verDetalleCompra();
                        break;
                    case 0:
                    MenuCompra.verCarrito();
                        // Volver
                        break;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                        break;
                }
            }
        } else {
            System.out.println("Debe iniciar sesión para ver sus compras.");
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
            Usuario usuarioActual = menuPrincipal.getUsuarioActual();
            
            // Verificar que la compra pertenezca al usuario actual
            if (usuarioActual != null && compra.getComprador().getId() == usuarioActual.getId()) {
                System.out.println("\n===== DETALLE DE COMPRA =====");
                System.out.println("ID: " + compra.getId());
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
                
                System.out.println("\n0. Volver");
                
                System.out.print("Seleccione una opción: ");
                int opcion = Integer.parseInt(scanner.nextLine());
                
                if (opcion != 0) {
                    System.out.println("Opción no válida. Intente nuevamente.");
                }
            } else {
                System.out.println("No tiene permiso para ver esta compra.");
            }
        } else {
            System.out.println("Compra no encontrada.");
        }
    }
}