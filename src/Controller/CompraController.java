package Controller;

import Model.Carrito;
import Model.Compra;
import Model.Producto;
import Model.Usuario;
import Service.CompraService;
import Service.ProductoService;
import Service.UsuarioService;

import java.util.List;

/**
 * Controlador para la gestión de compras
 * @author v0
 */
public class CompraController {
    
    private CompraService compraService;
    private ProductoService productoService;
    private UsuarioService usuarioService;
    
    /**
     * Constructor
     */
    public CompraController() {
        this.compraService = new CompraService();
        this.productoService = new ProductoService();
        this.usuarioService = new UsuarioService();
    }
    
    /**
     * Obtiene todas las compras
     * @return Lista de compras
     */
    public List<Compra> obtenerTodas() {
        return compraService.obtenerTodas();
    }
    
    /**
     * Obtiene una compra por su ID
     * @param id ID de la compra
     * @return Compra encontrada o null si no existe
     */
    public Compra obtenerPorId(int id) {
        return compraService.obtenerPorId(id);
    }
    
    /**
     * Obtiene las compras de un usuario
     * @param usuarioId ID del usuario
     * @return Lista de compras del usuario
     */
    public List<Compra> obtenerPorUsuario(int usuarioId) {
        return compraService.obtenerPorUsuario(usuarioId);
    }
    
    /**
     * Realiza una compra a partir de un carrito
     * @param carrito Carrito de compras
     * @return ID de la compra realizada o -1 si falla
     */
    public int realizarCompra(Carrito carrito) {
        if (carrito == null || carrito.getItems().isEmpty()) {
            System.out.println("El carrito está vacío");
            return -1;
        }
        
        return compraService.realizarCompra(carrito);
    }
    
    /**
     * Crea un carrito de compras para un usuario
     * @param usuarioId ID del usuario
     * @return Carrito de compras
     */
    public Carrito crearCarrito(int usuarioId) {
        Usuario usuario = usuarioService.obtenerPorId(usuarioId);
        if (usuario == null) {
            System.out.println("El usuario no existe");
            return null;
        }
        
        return new Carrito(0, usuario);
    }
    
    /**
     * Agrega un producto al carrito
     * @param carrito Carrito de compras
     * @param productoId ID del producto
     * @param cantidad Cantidad a agregar
     * @return true si se agregó correctamente, false en caso contrario
     */
    public boolean agregarAlCarrito(Carrito carrito, int productoId, int cantidad) {
        if (carrito == null) {
            System.out.println("El carrito no es válido");
            return false;
        }
        
        if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor a cero");
            return false;
        }
        
        Producto producto = productoService.obtenerPorId(productoId);
        if (producto == null) {
            System.out.println("El producto no existe");
            return false;
        }
        
        if (producto.getStock() < cantidad) {
            System.out.println("No hay suficiente stock disponible");
            return false;
        }
        
        carrito.agregarProducto(producto, cantidad);
        return true;
    }
    
    /**
     * Elimina un producto del carrito
     * @param carrito Carrito de compras
     * @param productoId ID del producto
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminarDelCarrito(Carrito carrito, int productoId) {
        if (carrito == null) {
            System.out.println("El carrito no es válido");
            return false;
        }
        
        carrito.eliminarProducto(productoId);
        return true;
    }
    
    /**
     * Vacía el carrito
     * @param carrito Carrito de compras
     * @return true si se vació correctamente, false en caso contrario
     */
    public boolean vaciarCarrito(Carrito carrito) {
        if (carrito == null) {
            System.out.println("El carrito no es válido");
            return false;
        }
        
        carrito.vaciar();
        return true;
    }
    
    /**
     * Actualiza el estado de una compra
     * @param compraId ID de la compra
     * @param nuevoEstado Nuevo estado de la compra
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizarEstado(int compraId, String nuevoEstado) {
        if (compraId <= 0) {
            System.out.println("ID de compra no válido");
            return false;
        }
        
        if (nuevoEstado == null || nuevoEstado.trim().isEmpty()) {
            System.out.println("El estado no puede estar vacío");
            return false;
        }
        
        return compraService.actualizarEstado(compraId, nuevoEstado);
    }
}