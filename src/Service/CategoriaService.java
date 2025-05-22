package Service;

import Model.Categoria;
import Repository.CategoriaRepository;

import java.util.List;

/**
 * Servicio para la gestión de categorías
 * @author v0
 */
public class CategoriaService {
    
    private CategoriaRepository categoriaRepository;
    
    /**
     * Constructor
     */
    public CategoriaService() {
        this.categoriaRepository = new CategoriaRepository();
    }
    
    /**
     * Obtiene todas las categorías
     * @return Lista de categorías
     */
    public List<Categoria> obtenerTodas() {
        return categoriaRepository.obtenerTodas();
    }
    
    /**
     * Obtiene una categoría por su ID
     * @param id ID de la categoría
     * @return Categoría encontrada o null si no existe
     */
    public Categoria obtenerPorId(int id) {
        return categoriaRepository.obtenerPorId(id);
    }
    
    /**
     * Registra una nueva categoría
     * @param categoria Categoría a registrar
     * @return ID de la categoría registrada o -1 si falla
     */
    public int registrar(Categoria categoria) {
        // Validar que la categoría tenga los datos necesarios
        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
            System.out.println("El nombre de la categoría es obligatorio");
            return -1;
        }
        
        return categoriaRepository.insertar(categoria);
    }
    
    /**
     * Actualiza una categoría existente
     * @param categoria Categoría a actualizar
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizar(Categoria categoria) {
        // Verificar si la categoría existe
        Categoria existente = categoriaRepository.obtenerPorId(categoria.getId());
        if (existente == null) {
            System.out.println("La categoría no existe");
            return false;
        }
        
        // Validar que la categoría tenga los datos necesarios
        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
            System.out.println("El nombre de la categoría es obligatorio");
            return false;
        }
        
        return categoriaRepository.actualizar(categoria);
    }
    
    /**
     * Elimina una categoría
     * @param id ID de la categoría a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminar(int id) {
        // Verificar si la categoría existe
        Categoria existente = categoriaRepository.obtenerPorId(id);
        if (existente == null) {
            System.out.println("La categoría no existe");
            return false;
        }
        
        return categoriaRepository.eliminar(id);
    }
}