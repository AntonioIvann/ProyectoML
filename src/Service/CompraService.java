package Service;

import Model.Carrito;
import Model.Compra;
import Model.DetalleCompra;
import Model.ItemCarrito;
import Model.Producto;
import Model.Usuario;
import Repository.CompraRepository;
import Repository.ProductoRepository;

import java.math.*;
import java.util.*;

/**
 * Servicio para la gestión de compras
 * @author v0
 */
public class CompraService {
    
    private CompraRepository compraRepository;
    private ProductoRepository productoRepository;
    
    /**
     * Constructor
     */
    public CompraService() {
        this.compraRepository = new CompraRepository();
        this.productoRepository = new ProductoRepository();
    }
    
    /**
     * Obtiene todas las compras
     * @return Lista de compras
     */
    public List<Compra> obtenerTodas() {
        return compraRepository.obtenerTodas();
    }
    
    /**
     * Obtiene una compra por su ID
     * @param id ID de la compra
     * @return Compra encontrada o null si no existe
     */
    public Compra obtenerPorId(int id) {
        return compraRepository.obtenerPorId(id);
    }
    
    /**
     * Obtiene las compras de un usuario
     * @param usuarioId ID del usuario
     * @return Lista de compras del usuario
     */
    public List<Compra> obtenerPorUsuario(int usuarioId) {
        return compraRepository.obtenerPorUsuario(usuarioId);
    }
    
    /**
     * Realiza una compra a partir de un carrito
     * @param carrito Carrito de compras
     * @return ID de la compra realizada o -1 si falla
     */
    public int realizarCompra(Carrito carrito) {
        // Validar que el carrito tenga items
        if (carrito.getItems().isEmpty()) {
            System.out.println("El carrito está vacío");
            return -1;
        }
        
        // Validar que el usuario exista
        if (carrito.getUsuario() == null || carrito.getUsuario().getId() <= 0) {
            System.out.println("El usuario no es válido");
            return -1;
        }
        
        // Validar stock de productos
        for (ItemCarrito item : carrito.getItems()) {
            Producto producto = productoRepository.obtenerPorId(item.getProducto().getId());
            if (producto == null) {
                System.out.println("El producto " + item.getProducto().getId() + " no existe");
                return -1;
            }
            
            if (producto.getStock() < item.getCantidad()) {
                System.out.println("No hay suficiente stock para el producto " + producto.getNombre());
                return -1;
            }
        }
        
        // Crear la compra
        Compra compra = new Compra();
        compra.setComprador(carrito.getUsuario());
        compra.setFecha(new Date());
        compra.setTotal(carrito.getTotal());
        compra.setEstado("Pendiente");
        
        // Crear los detalles de la compra
        List<DetalleCompra> detalles = new ArrayList<>();
        for (ItemCarrito item : carrito.getItems()) {
            DetalleCompra detalle = new DetalleCompra();
            detalle.setCompra(compra);
            detalle.setProducto(item.getProducto());
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(item.getPrecioUnitario());
            detalle.setSubtotal(item.getSubtotal());
            detalles.add(detalle);
        }
        
        compra.setDetalles(detalles);
        
        // Insertar la compra
        int compraId = compraRepository.insertar(compra);
        
        // Si la compra se realizó correctamente, vaciar el carrito
        if (compraId > 0) {
            carrito.vaciar();
        }
        
        return compraId;
    }
    
    /**
     * Actualiza el estado de una compra
     * @param compraId ID de la compra
     * @param nuevoEstado Nuevo estado de la compra
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizarEstado(int compraId, String nuevoEstado) {
        // Verificar si la compra existe
        Compra existente = compraRepository.obtenerPorId(compraId);
        if (existente == null) {
            System.out.println("La compra no existe");
            return false;
        }
        
        // Validar el nuevo estado
        if (nuevoEstado == null || nuevoEstado.trim().isEmpty()) {
            System.out.println("El estado no puede estar vacío");
            return false;
        }
        
        return compraRepository.actualizarEstado(compraId, nuevoEstado);
    }
}