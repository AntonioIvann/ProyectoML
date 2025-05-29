package Service;

import Model.Categoria;
import Repository.CategoriaRepository;

import java.util.List;

public class CategoriaService {
    
    private CategoriaRepository categoriaRepository;
    
    public CategoriaService() {
        this.categoriaRepository = new CategoriaRepository();
    }
    
    public List<Categoria> obtenerTodas() {
        return categoriaRepository.obtenerTodas();
    }
    
    public Categoria obtenerPorId(int id) {
        return categoriaRepository.obtenerPorId(id);
    }
    
    public int registrar(Categoria categoria) {
        // Validar que la categoría tenga los datos necesarios
        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
            System.out.println("El nombre de la categoría es obligatorio");
            return -1;
        }
        
        return categoriaRepository.insertar(categoria);
    }
    
    public boolean actualizar(Categoria categoria) {
        Categoria existente = categoriaRepository.obtenerPorId(categoria.getId());
        if (existente == null) {
            System.out.println("La categoría no existe");
            return false;
        }
        
        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
            System.out.println("El nombre de la categoría es obligatorio");
            return false;
        }
        
        return categoriaRepository.actualizar(categoria);
    }
    
    public boolean eliminar(int id) {
        Categoria existente = categoriaRepository.obtenerPorId(id);
        if (existente == null) {
            System.out.println("La categoría no existe");
            return false;
        }
        
        return categoriaRepository.eliminar(id);
    }
}