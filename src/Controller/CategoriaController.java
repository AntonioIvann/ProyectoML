package Controller;

import Model.Categoria;
import Service.CategoriaService;

import java.util.List;

/**
 * Controlador para la gestión de categorías
 * @author v0
 */
public class CategoriaController {
    
    private CategoriaService categoriaService;
    
    /**
     * Constructor
     */
    public CategoriaController() {
        this.categoriaService = new CategoriaService();
    }
    
    /**
     * Obtiene todas las categorías
     * @return Lista de categorías
     */
    public List<Categoria> obtenerTodas() {
        return categoriaService.obtenerTodas();
    }
    
    /**
     * Obtiene una categoría por su ID
     * @param id ID de la categoría
     * @return Categoría encontrada o null si no existe
     */
    public Categoria obtenerPorId(int id) {
        return categoriaService.obtenerPorId(id);
    }
    
    /**
     * Registra una nueva categoría
     * @param nombre Nombre de la categoría
     * @param descripcion Descripción de la categoría
     * @return ID de la categoría registrada o -1 si falla
     */
    public int registrar(String nombre, String descripcion) {
        // Validar datos
        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("El nombre es obligatorio");
            return -1;
        }
        
        // Crear la categoría
        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);
        
        return categoriaService.registrar(categoria);
    }
    
    /**
     * Actualiza una categoría existente
     * @param id ID de la categoría
     * @param nombre Nombre de la categoría
     * @param descripcion Descripción de la categoría
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizar(int id, String nombre, String descripcion) {
        // Validar datos
        if (id <= 0) {
            System.out.println("ID de categoría no válido");
            return false;
        }
        
        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("El nombre es obligatorio");
            return false;
        }
        
        // Obtener la categoría actual
        Categoria categoria = categoriaService.obtenerPorId(id);
        if (categoria == null) {
            System.out.println("La categoría no existe");
            return false;
        }
        
        // Actualizar los datos
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);
        
        return categoriaService.actualizar(categoria);
    }
    
    /**
     * Elimina una categoría
     * @param id ID de la categoría a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminar(int id) {
        if (id <= 0) {
            System.out.println("ID de categoría no válido");
            return false;
        }
        
        return categoriaService.eliminar(id);
    }
}