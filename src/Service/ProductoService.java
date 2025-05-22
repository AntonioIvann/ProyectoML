package Service;

import Model.Producto;
import Repository.ProductoRepository;

import java.util.List;

/**
 * Servicio para la gestión de productos
 * @author v0
 */
public class ProductoService {
    
    private ProductoRepository productoRepository;
    
    /**
     * Constructor
     */
    public ProductoService() {
        this.productoRepository = new ProductoRepository();
    }
    
    /**
     * Obtiene todos los productos
     * @return Lista de productos
     */
    public List<Producto> obtenerTodos() {
        return productoRepository.obtenerTodos();
    }
    
    /**
     * Obtiene un producto por su ID
     * @param id ID del producto
     * @return Producto encontrado o null si no existe
     */
    public Producto obtenerPorId(int id) {
        return productoRepository.obtenerPorId(id);
    }
    
    /**
     * Obtiene productos por categoría
     * @param categoriaId ID de la categoría
     * @return Lista de productos de la categoría
     */
    public List<Producto> obtenerPorCategoria(int categoriaId) {
        return productoRepository.obtenerPorCategoria(categoriaId);
    }
    
    /**
     * Obtiene productos por vendedor
     * @param vendedorId ID del vendedor
     * @return Lista de productos del vendedor
     */
    public List<Producto> obtenerPorVendedor(int vendedorId) {
        return productoRepository.obtenerPorVendedor(vendedorId);
    }
    
    /**
     * Busca productos por nombre o descripción
     * @param termino Término de búsqueda
     * @return Lista de productos que coinciden con la búsqueda
     */
    public List<Producto> buscar(String termino) {
        return productoRepository.buscar(termino);
    }
    
    /**
     * Registra un nuevo producto
     * @param producto Producto a registrar
     * @return ID del producto registrado o -1 si falla
     */
    public int registrar(Producto producto) {
        // Validar que el producto tenga los datos necesarios
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            System.out.println("El nombre del producto es obligatorio");
            return -1;
        }
        
        if (producto.getPrecio() == null || producto.getPrecio().doubleValue() <= 0) {
            System.out.println("El precio del producto debe ser mayor a cero");
            return -1;
        }
        
        if (producto.getStock() < 0) {
            System.out.println("El stock del producto no puede ser negativo");
            return -1;
        }
        
        if (producto.getCategoria() == null || producto.getCategoria().getId() <= 0) {
            System.out.println("La categoría del producto es obligatoria");
            return -1;
        }
        
        if (producto.getVendedor() == null || producto.getVendedor().getId() <= 0) {
            System.out.println("El vendedor del producto es obligatorio");
            return -1;
        }
        
        return productoRepository.insertar(producto);
    }
    
    /**
     * Actualiza un producto existente
     * @param producto Producto a actualizar
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizar(Producto producto) {
        // Verificar si el producto existe
        Producto existente = productoRepository.obtenerPorId(producto.getId());
        if (existente == null) {
            System.out.println("El producto no existe");
            return false;
        }
        
        // Validar que el producto tenga los datos necesarios
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            System.out.println("El nombre del producto es obligatorio");
            return false;
        }
        
        if (producto.getPrecio() == null || producto.getPrecio().doubleValue() <= 0) {
            System.out.println("El precio del producto debe ser mayor a cero");
            return false;
        }
        
        if (producto.getStock() < 0) {
            System.out.println("El stock del producto no puede ser negativo");
            return false;
        }
        
        if (producto.getCategoria() == null || producto.getCategoria().getId() <= 0) {
            System.out.println("La categoría del producto es obligatoria");
            return false;
        }
        
        if (producto.getVendedor() == null || producto.getVendedor().getId() <= 0) {
            System.out.println("El vendedor del producto es obligatorio");
            return false;
        }
        
        return productoRepository.actualizar(producto);
    }
    
    /**
     * Actualiza el stock de un producto
     * @param productoId ID del producto
     * @param nuevoStock Nuevo valor de stock
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizarStock(int productoId, int nuevoStock) {
        // Verificar si el producto existe
        Producto existente = productoRepository.obtenerPorId(productoId);
        if (existente == null) {
            System.out.println("El producto no existe");
            return false;
        }
        
        if (nuevoStock < 0) {
            System.out.println("El stock del producto no puede ser negativo");
            return false;
        }
        
        return productoRepository.actualizarStock(productoId, nuevoStock);
    }
    
    /**
     * Elimina un producto
     * @param id ID del producto a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminar(int id) {
        // Verificar si el producto existe
        Producto existente = productoRepository.obtenerPorId(id);
        if (existente == null) {
            System.out.println("El producto no existe");
            return false;
        }
        
        return productoRepository.eliminar(id);
    }
}