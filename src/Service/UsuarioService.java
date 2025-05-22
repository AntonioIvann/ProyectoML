package Service;

import Model.Usuario;
import Repository.UsuarioRepository;

import java.util.List;

/**
 * Servicio para la gestión de usuarios
 * @author v0
 */
public class UsuarioService {
    
    private UsuarioRepository usuarioRepository;
    
    /**
     * Constructor
     */
    public UsuarioService() {
        this.usuarioRepository = new UsuarioRepository();
    }
    
    /**
     * Obtiene todos los usuarios
     * @return Lista de usuarios
     */
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.obtenerTodos();
    }
    
    /**
     * Obtiene un usuario por su ID
     * @param id ID del usuario
     * @return Usuario encontrado o null si no existe
     */
    public Usuario obtenerPorId(int id) {
        return usuarioRepository.obtenerPorId(id);
    }
    
    /**
     * Obtiene un usuario por su email
     * @param email Email del usuario
     * @return Usuario encontrado o null si no existe
     */
    public Usuario obtenerPorEmail(String email) {
        return usuarioRepository.obtenerPorEmail(email);
    }
    
    /**
     * Registra un nuevo usuario
     * @param usuario Usuario a registrar
     * @return ID del usuario registrado o -1 si falla
     */
    public int registrar(Usuario usuario) {
        // Verificar si el email ya está registrado
        Usuario existente = usuarioRepository.obtenerPorEmail(usuario.getEmail());
        if (existente != null) {
            System.out.println("El email ya está registrado");
            return -1;
        }
        
        return usuarioRepository.insertar(usuario);
    }
    
    /**
     * Actualiza un usuario existente
     * @param usuario Usuario a actualizar
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizar(Usuario usuario) {
        // Verificar si el usuario existe
        Usuario existente = usuarioRepository.obtenerPorId(usuario.getId());
        if (existente == null) {
            System.out.println("El usuario no existe");
            return false;
        }
        
        // Verificar si el nuevo email ya está registrado por otro usuario
        if (!existente.getEmail().equals(usuario.getEmail())) {
            Usuario emailExistente = usuarioRepository.obtenerPorEmail(usuario.getEmail());
            if (emailExistente != null) {
                System.out.println("El email ya está registrado por otro usuario");
                return false;
            }
        }
        
        return usuarioRepository.actualizar(usuario);
    }
    
    /**
     * Elimina un usuario
     * @param id ID del usuario a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminar(int id) {
        // Verificar si el usuario existe
        Usuario existente = usuarioRepository.obtenerPorId(id);
        if (existente == null) {
            System.out.println("El usuario no existe");
            return false;
        }
        
        return usuarioRepository.eliminar(id);
    }
    
    /**
     * Autentica un usuario
     * @param email Email del usuario
     * @param password Contraseña del usuario
     * @return Usuario autenticado o null si las credenciales son inválidas
     */
    public Usuario autenticar(String email, String password) {
        return usuarioRepository.autenticar(email, password);
    }
}