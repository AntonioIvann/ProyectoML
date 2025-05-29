package Controller;

import Model.Carrito;
import Model.Compra;
import Model.Producto;
import Model.Usuario;
import Service.CompraService;
import Service.ProductoService;
import Service.UsuarioService;

import java.util.List;

public class CompraController {
    
    private CompraService compraService;
    private ProductoService productoService;
    private UsuarioService usuarioService;
    
    public CompraController() {
        this.compraService = new CompraService();
        this.productoService = new ProductoService();
        this.usuarioService = new UsuarioService();
    }
    
    public List<Compra> obtenerTodas() {
        return compraService.obtenerTodas();
    }
    
    public Compra obtenerPorId(int id) {
        return compraService.obtenerPorId(id);
    }
    
    public List<Compra> obtenerPorUsuario(int usuarioId) {
        return compraService.obtenerPorUsuario(usuarioId);
    }
    
    public int realizarCompra(Carrito carrito) {
        if (carrito == null || carrito.getItems().isEmpty()) {
            System.out.println("El carrito está vacío");
            return -1;
        }
        
        return compraService.realizarCompra(carrito);
    }
    
    public Carrito crearCarrito(int usuarioId) {
        Usuario usuario = usuarioService.obtenerPorId(usuarioId);
        if (usuario == null) {
            System.out.println("El usuario no existe");
            return null;
        }
        
        return new Carrito(0, usuario);
    }
    
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
    
    public boolean eliminarDelCarrito(Carrito carrito, int productoId) {
        if (carrito == null) {
            System.out.println("El carrito no es válido");
            return false;
        }
        
        carrito.eliminarProducto(productoId);
        return true;
    }
    
    public boolean vaciarCarrito(Carrito carrito) {
        if (carrito == null) {
            System.out.println("El carrito no es válido");
            return false;
        }
        
        carrito.vaciar();
        return true;
    }
    
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