package Controller;

import Model.Categoria;
import Service.CategoriaService;

import java.util.List;

public class CategoriaController {
    
    private CategoriaService categoriaService;

    public CategoriaController() {
        this.categoriaService = new CategoriaService();
    }

    public List<Categoria> obtenerTodas() {
        return categoriaService.obtenerTodas();
    }
    
    public Categoria obtenerPorId(int id) {
        return categoriaService.obtenerPorId(id);
    }
    
    public int registrar(String nombre, String descripcion) {

        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("El nombre es obligatorio");
            return -1; // Código de error
        }
        
        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);
        
        return categoriaService.registrar(categoria);
    }
    
    public boolean actualizar(int id, String nombre, String descripcion) {
        if (id <= 0) {
            System.out.println("ID de categoría no válido");
            return false;
        }
        
        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("El nombre es obligatorio");
            return false;
        }
        
        Categoria categoria = categoriaService.obtenerPorId(id);
        if (categoria == null) {
            System.out.println("La categoría no existe");
            return false;
        }
        
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);
        
        return categoriaService.actualizar(categoria);
    }
    
    public boolean eliminar(int id) {
        if (id <= 0) {
            System.out.println("ID de categoría no válido");
            return false;
        }
        
        return categoriaService.eliminar(id);
    }
}
