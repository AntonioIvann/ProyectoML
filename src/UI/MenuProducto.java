package UI;

import Controller.CategoriaController;
import Controller.CompraController;
import Controller.ProductoController;
import Model.Carrito;
import Model.Categoria;
import Model.Producto;
import Model.Usuario;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 * Menú para la gestión de productos
 */
public class MenuProducto {
    
    private static final String usuarioActual = null;
    private MenuPrincipal menuPrincipal;
    private Scanner scanner;
    private ProductoController productoController;
    private CategoriaController categoriaController;
    private CompraController compraController;
    
    /**
     * Constructor
     * @param menuPrincipal Menú principal
     */
    public MenuProducto(MenuPrincipal menuPrincipal) {
        this.menuPrincipal = menuPrincipal;
        this.scanner = menuPrincipal.getScanner();
        this.productoController = new ProductoController();
        this.categoriaController = new CategoriaController();
        this.compraController = new CompraController();
    }
    
    /**
     * Muestra todos los productos
     */
    public void mostrarProductos() {
        List<Producto> productos = productoController.obtenerTodos();
        
        System.out.println("\n===== PRODUCTOS =====");
        
        if (productos.isEmpty()) {
            System.out.println("No hay productos disponibles.");
        } else {
            for (Producto producto : productos) {
                System.out.println(producto.getId() + ". " + producto.getNombre() + 
                                  " - $" + producto.getPrecio() + 
                                  " - Stock: " + producto.getStock() +
                                  " - Categoría: " + producto.getCategoria().getNombre());
            }
            
            System.out.println("\n1. Ver detalle de producto");
            System.out.println("0. Volver");
            
            System.out.print("Seleccione una opción: ");
            int opcion = Integer.parseInt(scanner.nextLine());
            switch (opcion) {
                case 1:
                    verDetalleProducto();
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
    }
    
    /**
     * Muestra el detalle de un producto
     */
    private void verDetalleProducto() {
        System.out.print("Ingrese el ID del producto: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        Producto producto = productoController.obtenerPorId(id);
        
        if (producto != null) {
            System.out.println("\n===== DETALLE DE PRODUCTO =====");
            System.out.println("ID: " + producto.getId());
            System.out.println("Nombre: " + producto.getNombre());
            System.out.println("Descripción: " + producto.getDescripcion());
            System.out.println("Precio: $" + producto.getPrecio());
            System.out.println("Stock: " + producto.getStock());
            System.out.println("Categoría: " + producto.getCategoria().getNombre());
            System.out.println("Vendedor: " + producto.getVendedor().getNombre() + " " + 
            producto.getVendedor().getApellido());
        }
        
            
            Usuario usuarioActual = menuPrincipal.getUsuarioActual();
            
            if (usuarioActual != null) {
                System.out.println("\n1. Agregar al carrito");
                
                // Si el usuario es el vendedor del producto, mostrar opciones de edición
                if (usuarioActual.getId() == producto.getVendedor().getId()) {
                    System.out.println("2. Editar producto");
                    System.out.println("3. Eliminar producto");
                }
                
                System.out.println("0. Volver");
                
                System.out.print("Seleccione una opción: ");
                int opcion = Integer.parseInt(scanner.nextLine());
                
                switch (opcion) {
                    case 1:
                        agregarAlCarrito(producto);
                        break;
                    case 2:
                        if (usuarioActual.getId() == producto.getVendedor().getId()) {
                            editarProducto(producto);
                        } else {
                            System.out.println("Opción no válida. Intente nuevamente.");
                        } MenuPrincipal.mostrarMenuUsuarioLogueado();
                        break;
                    case 3:
                        if (usuarioActual.getId() == producto.getVendedor().getId()) {
                            eliminarProducto(producto);
                        } else {
                            System.out.println("Opción no válida. Intente nuevamente.");
                        } MenuPrincipal.mostrarMenuUsuarioLogueado();
                        break;
                    case 0:
                        // Volver
                        MenuPrincipal.mostrarMenuUsuarioLogueado();
                        break;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                        break;
                }
            } else {
                System.out.println("\nInicie sesión para comprar este producto.");
                System.out.println("0. Volver");
                
                System.out.print("Seleccione una opción: ");
                int opcion = Integer.parseInt(scanner.nextLine());
                
                if (opcion != 0) {
                    System.out.println("Seleccione una opcion valida");
                } else {
                    mostrarProductos();
                }
            }
    }
            
    
    /**
     * Agrega un producto al carrito
     * @param producto Producto a agregar
     */
    private void agregarAlCarrito(Producto producto) {
        Usuario usuarioActual = menuPrincipal.getUsuarioActual();
        
        if (usuarioActual != null) {
            System.out.print("Cantidad: ");
            int cantidad = Integer.parseInt(scanner.nextLine());
            
            if (cantidad <= 0) {
                System.out.println("La cantidad debe ser mayor a cero.");
                return;
            }
            
            if (cantidad > producto.getStock()) {
                System.out.println("No hay suficiente stock disponible.");
                return;
            }
            
            // Crear carrito si no existe en la sesión (simulado)
            Carrito carrito = compraController.crearCarrito(usuarioActual.getId());
            
            boolean agregado = compraController.agregarAlCarrito(carrito, producto.getId(), cantidad);
            
            if (agregado) {
                System.out.println("Producto agregado al carrito correctamente.");
            } else {
                System.out.println("Error al agregar el producto al carrito.");
            }
        }
    }
    
    /**
     * Edita un producto
     * @param producto Producto a editar
     */
    private void editarProducto(Producto producto) {
        System.out.println("\n===== EDITAR PRODUCTO =====");
        
        System.out.print("Nombre [" + producto.getNombre() + "]: ");
        String nombre = scanner.nextLine();
        if (nombre.trim().isEmpty()) {
            nombre = producto.getNombre();
        }
        
        System.out.print("Descripción [" + producto.getDescripcion() + "]: ");
        String descripcion = scanner.nextLine();
        if (descripcion.trim().isEmpty()) {
            descripcion = producto.getDescripcion();
        }
        
        System.out.print("Precio [$" + producto.getPrecio() + "]: ");
        String precioStr = scanner.nextLine();
        BigDecimal precio = precioStr.trim().isEmpty() ? 
                           producto.getPrecio() : 
                           new BigDecimal(precioStr);
        
        System.out.print("Stock [" + producto.getStock() + "]: ");
        String stockStr = scanner.nextLine();
        int stock = stockStr.trim().isEmpty() ? 
                   producto.getStock() : 
                   Integer.parseInt(stockStr);
        
        System.out.print("Categoría ID [" + producto.getCategoria().getId() + "]: ");
        String categoriaIdStr = scanner.nextLine();
        int categoriaId = categoriaIdStr.trim().isEmpty() ? 
                         producto.getCategoria().getId() : 
                         Integer.parseInt(categoriaIdStr);
        
        System.out.print("Imagen [" + producto.getImagen() + "]: ");
        String imagen = scanner.nextLine();
        if (imagen.trim().isEmpty()) {
            imagen = producto.getImagen();
        }
        
        System.out.print("¿Activo? (S/N) [" + (producto.isActivo() ? "S" : "N") + "]: ");
        String activoStr = scanner.nextLine();
        boolean activo = activoStr.equalsIgnoreCase("S") || 
                        (activoStr.trim().isEmpty() && producto.isActivo());
        
        boolean actualizado = productoController.actualizar(
            producto.getId(), nombre, descripcion, precio, stock, 
            categoriaId, producto.getVendedor().getId(), imagen, activo
        );
        
        if (actualizado) {
            System.out.println("Producto actualizado correctamente.");
        } else {
            System.out.println("Error al actualizar el producto.");
        }
    }
    
    /**
     * Elimina un producto
     * @param producto Producto a eliminar
     */
    private void eliminarProducto(Producto producto) {
        System.out.println("\n===== ELIMINAR PRODUCTO =====");
        System.out.print("¿Está seguro de eliminar el producto '" + producto.getNombre() + "'? (S/N): ");
        String confirmacion = scanner.nextLine();
        
        if (confirmacion.equalsIgnoreCase("S")) {
            boolean eliminado = productoController.eliminar(producto.getId());
            
            if (eliminado) {
                System.out.println("Producto eliminado correctamente.");
            } else {
                System.out.println("Error al eliminar el producto.");
            }
        } else {
            System.out.println("Operación cancelada.");
        }
    }
    
    /**
     * Busca productos por nombre o descripción
     */
    public void buscarProductos() {
        System.out.println("\n===== BUSCAR PRODUCTOS =====");
        System.out.print("Ingrese término de búsqueda: ");
        String termino = scanner.nextLine();
        
        List<Producto> productos = productoController.buscar(termino);
        
        if (productos.isEmpty()) {
            System.out.println("No se encontraron productos que coincidan con la búsqueda.");
        } else {
            System.out.println("\nResultados de la búsqueda:");
            for (Producto producto : productos) {
                System.out.println(producto.getId() + ". " + producto.getNombre() + 
                                  " - $" + producto.getPrecio() + 
                                  " - Stock: " + producto.getStock() +
                                  " - Categoría: " + producto.getCategoria().getNombre());
            }
            
            System.out.println("\n1. Ver detalle de producto");
            System.out.println("0. Volver");
            
            System.out.print("Seleccione una opción: ");
            int opcion = Integer.parseInt(scanner.nextLine());
            
            switch (opcion) {
                case 1:
                    verDetalleProducto();
                    break;
                case 0:
                if (usuarioActual != null) {
                    MenuPrincipal.mostrarMenu();
                    } else {
                    MenuPrincipal.mostrarMenuUsuarioLogueado();
                }
                    // Volver
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
    public void mostrarCategorias() {
        List<Categoria> categorias = categoriaController.obtenerTodas();
        
        System.out.println("\n===== CATEGORÍAS =====");
        
        if (categorias.isEmpty()) {
            System.out.println("No hay categorías disponibles.");
        } else {
            for (Categoria categoria : categorias) {
                System.out.println(categoria.getId() + ". " + categoria.getNombre() + 
                                  " - " + categoria.getDescripcion());
            }
            
            System.out.println("\n1. Ver productos por categoría");
            System.out.println("0. Volver");
            
            System.out.print("Seleccione una opción: ");
            int opcion = Integer.parseInt(scanner.nextLine());
            switch (opcion) {
                case 1:
                    verProductosPorCategoria();
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
        }
    
    /**
     * Muestra los productos de una categoría
     */
    private void verProductosPorCategoria() {
        System.out.print("Ingrese el ID de la categoría: ");
        int categoriaId = Integer.parseInt(scanner.nextLine());
        
        List<Producto> productos = productoController.obtenerPorCategoria(categoriaId);
        
        if (productos.isEmpty()) {
            System.out.println("No hay productos en esta categoría.");
            MenuPrincipal.mostrarMenuUsuarioLogueado();
        } else {
            System.out.println("\nProductos de la categoría:");
            for (Producto producto : productos) {
                System.out.println(producto.getId() + ". " + producto.getNombre() + 
                                  " - $" + producto.getPrecio() + 
                                  " - Stock: " + producto.getStock());
            }
            
            System.out.println("\n1. Ver detalle de producto");
            System.out.println("0. Volver");
            
            System.out.print("Seleccione una opción: ");
            int opcion = Integer.parseInt(scanner.nextLine());
            
            switch (opcion) {
                case 1:
                    verDetalleProducto();
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
        }
    
    /**
     * Muestra los productos del vendedor actual
     */
    public void verMisProductos() {
        Usuario usuarioActual = menuPrincipal.getUsuarioActual();
        
        if (usuarioActual != null && usuarioActual.isEsVendedor()) {
            List<Producto> productos = productoController.obtenerPorVendedor(usuarioActual.getId());
            
            System.out.println("\n===== MIS PRODUCTOS =====");
            
            if (productos.isEmpty()) {
                System.out.println("No tiene productos registrados.");
            } else {
                for (Producto producto : productos) {
                    System.out.println(producto.getId() + ". " + producto.getNombre() + 
                                      " - $" + producto.getPrecio() + 
                                      " - Stock: " + producto.getStock() +
                                      " - " + (producto.isActivo() ? "Activo" : "Inactivo"));
                }
                
                System.out.println("\n1. Ver detalle de producto");
                System.out.println("2. Agregar nuevo producto");
                System.out.println("0. Volver");
                
                System.out.print("Seleccione una opción: ");
                int opcion = Integer.parseInt(scanner.nextLine());
                
                switch (opcion) {
                    case 1:
                        verDetalleProducto();
                        break;
                    case 2:
                        agregarProducto();
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
            System.out.println("Debe ser un vendedor para acceder a esta opción.");
        }
    }
    
    /**
     * Agrega un nuevo producto
     */
    public void agregarProducto() {
        Usuario usuarioActual = menuPrincipal.getUsuarioActual();
        
        if (usuarioActual != null && usuarioActual.isEsVendedor()) {
            System.out.println("\n===== AGREGAR PRODUCTO =====");
            
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            
            System.out.print("Descripción: ");
            String descripcion = scanner.nextLine();
            
            System.out.print("Precio: $");
            BigDecimal precio = new BigDecimal(scanner.nextLine());
            
            System.out.print("Stock: ");
            int stock = Integer.parseInt(scanner.nextLine());
            
            // Mostrar categorías disponibles
            List<Categoria> categorias = categoriaController.obtenerTodas();
            System.out.println("\nCategorías disponibles:");
            for (Categoria categoria : categorias) {
                System.out.println(categoria.getId() + ". " + categoria.getNombre());
            }
            
            System.out.print("Categoría ID: ");
            int categoriaId = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Imagen (URL): ");
            String imagen = scanner.nextLine();
            
            int id = productoController.registrar(
                nombre, descripcion, precio, stock, categoriaId, usuarioActual.getId(), imagen
            );
            
            if (id > 0) {
                System.out.println("Producto agregado correctamente con ID: " + id);
            } else {
                System.out.println("Error al agregar el producto.");
            }
        } else {
            System.out.println("Debe ser un vendedor para agregar productos.");
        }
    }
}