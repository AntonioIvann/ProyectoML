package Controller;

import Model.Categoria;
import Model.Producto;
import Model.Usuario;
import Service.CategoriaService;
import Service.ProductoService;
import Service.UsuarioService;

import java.math.BigDecimal;
import java.util.List;

/**
 * Controlador para la gestión de productos
 * @author v0
 */
public class ProductoController {
    
    private ProductoService productoService;
    private CategoriaService categoriaService;
    private UsuarioService usuarioService;
    
    /**
     * Constructor
     */
    public ProductoController() {
        this.productoService = new ProductoService();
        this.categoriaService = new CategoriaService();
        this.usuarioService = new UsuarioService();
    }
    
    /**
     * Obtiene todos los productos
     * @return Lista de productos
     */
    public List<Producto> obtenerTodos() {
        return productoService.obtenerTodos();
    }
    
    /**
     * Obtiene un producto por su ID
     * @param id ID del producto
     * @return Producto encontrado o null si no existe
     */
    public Producto obtenerPorId(int id) {
        return productoService.obtenerPorId(id);
    }
    
    /**
     * Obtiene productos por categoría
     * @param categoriaId ID de la categoría
     * @return Lista de productos de la categoría
     */
    public List<Producto> obtenerPorCategoria(int categoriaId) {
        return productoService.obtenerPorCategoria(categoriaId);
    }
    
    /**
     * Obtiene productos por vendedor
     * @param vendedorId ID del vendedor
     * @return Lista de productos del vendedor
     */
    public List<Producto> obtenerPorVendedor(int vendedorId) {
        return productoService.obtenerPorVendedor(vendedorId);
    }
    
    /**
     * Busca productos por nombre o descripción
     * @param termino Término de búsqueda
     * @return Lista de productos que coinciden con la búsqueda
     */
    public List<Producto> buscar(String termino) {
        return productoService.buscar(termino);
    }
    
    /**
     * Registra un nuevo producto
     * @param nombre Nombre del producto
     * @param descripcion Descripción del producto
     * @param precio Precio del producto
     * @param stock Stock del producto
     * @param categoriaId ID de la categoría
     * @param vendedorId ID del vendedor
     * @param imagen URL de la imagen
     * @return ID del producto registrado o -1 si falla
     */
    public int registrar(String nombre, String descripcion, BigDecimal precio, int stock, 
                         int categoriaId, int vendedorId, String imagen) {
        // Validar datos
        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("El nombre es obligatorio");
            return -1;
        }
        
        if (precio == null || precio.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("El precio debe ser mayor a cero");
            return -1;
        }
        
        if (stock < 0) {
            System.out.println("El stock no puede ser negativo");
            return -1;
        }
        
        // Obtener la categoría
        Categoria categoria = categoriaService.obtenerPorId(categoriaId);
        if (categoria == null) {
            System.out.println("La categoría no existe");
            return -1;
        }
        
        // Obtener el vendedor
        Usuario vendedor = usuarioService.obtenerPorId(vendedorId);
        if (vendedor == null) {
            System.out.println("El vendedor no existe");
            return -1;
        }
        
        if (!vendedor.isEsVendedor()) {
            System.out.println("El usuario no es un vendedor");
            return -1;
        }
        
        // Crear el producto
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setCategoria(categoria);
        producto.setVendedor(vendedor);
        producto.setImagen(imagen);
        producto.setActivo(true);
        
        return productoService.registrar(producto);
    }
    
    /**
     * Actualiza un producto existente
     * @param id ID del producto
     * @param nombre Nombre del producto
     * @param descripcion Descripción del producto
     * @param precio Precio del producto
     * @param stock Stock del producto
     * @param categoriaId ID de la categoría
     * @param vendedorId ID del vendedor
     * @param imagen URL de la imagen
     * @param activo Indica si el producto está activo
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizar(int id, String nombre, String descripcion, BigDecimal precio, int stock, 
                              int categoriaId, int vendedorId, String imagen, boolean activo) {
        // Validar datos
        if (id <= 0) {
            System.out.println("ID de producto no válido");
            return false;
        }
        
        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("El nombre es obligatorio");
            return false;
        }
        
        if (precio == null || precio.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("El precio debe ser mayor a cero");
            return false;
        }
        
        if (stock < 0) {
            System.out.println("El stock no puede ser negativo");
            return false;
        }
        
        // Obtener el producto actual
        Producto producto = productoService.obtenerPorId(id);
        if (producto == null) {
            System.out.println("El producto no existe");
            return false;
        }
        
        // Obtener la categoría
        Categoria categoria = categoriaService.obtenerPorId(categoriaId);
        if (categoria == null) {
            System.out.println("La categoría no existe");
            return false;
        }
        
        // Obtener el vendedor
        Usuario vendedor = usuarioService.obtenerPorId(vendedorId);
        if (vendedor == null) {
            System.out.println("El vendedor no existe");
            return false;
        }
        
        if (!vendedor.isEsVendedor()) {
            System.out.println("El usuario no es un vendedor");
            return false;
        }
        
        // Actualizar los datos
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setCategoria(categoria);
        producto.setVendedor(vendedor);
        producto.setImagen(imagen);
        producto.setActivo(activo);
        
        return productoService.actualizar(producto);
    }
    
    /**
     * Actualiza el stock de un producto
     * @param productoId ID del producto
     * @param nuevoStock Nuevo valor de stock
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizarStock(int productoId, int nuevoStock) {
        if (productoId <= 0) {
            System.out.println("ID de producto no válido");
            return false;
        }
        
        if (nuevoStock < 0) {
            System.out.println("El stock no puede ser negativo");
            return false;
        }
        
        return productoService.actualizarStock(productoId, nuevoStock);
    }
    
    /**
     * Elimina un producto
     * @param id ID del producto a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminar(int id) {
        if (id <= 0) {
            System.out.println("ID de producto no válido");
            return false;
        }
        
        return productoService.eliminar(id);
    }
}