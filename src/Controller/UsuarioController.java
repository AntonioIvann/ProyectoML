package Controller;

import Model.Usuario;
import Service.UsuarioService;
import UI.MenuPrincipal;

import java.util.List;

/**
 * Controlador para la gestión de usuarios
 * @author v0
 */
public class UsuarioController {
    
    private UsuarioService usuarioService;
    
    /**
     * Constructor
     */
    public UsuarioController() {
        this.usuarioService = new UsuarioService();
    }
    
    /**
     * Obtiene todos los usuarios
     * @return Lista de usuarios
     */
    public List<Usuario> obtenerTodos() {
        return usuarioService.obtenerTodos();
    }
    
    /**
     * Obtiene un usuario por su ID
     * @param id ID del usuario
     * @return Usuario encontrado o null si no existe
     */
    public Usuario obtenerPorId(int id) {
        return usuarioService.obtenerPorId(id);
    }
    
    /**
     * Registra un nuevo usuario
     * @param nombre Nombre del usuario
     * @param apellido Apellido del usuario
     * @param email Email del usuario
     * @param password Contraseña del usuario
     * @param telefono Teléfono del usuario
     * @param direccion Dirección del usuario
     * @param esVendedor Indica si el usuario es vendedor
     * @return ID del usuario registrado o -1 si falla
     */
    public int registrar(String nombre, String apellido, String email, String password, 
                         String telefono, String direccion, boolean esVendedor) {
        // Validar datos
        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("El nombre es obligatorio");
            return -1;
        }
        
        if (apellido == null || apellido.trim().isEmpty()) {
            System.out.println("El apellido es obligatorio");
            return -1;
        }
        
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            System.out.println("El email no es válido");
            return -1;
        }
        
        if (password == null || password.trim().isEmpty() || password.length() < 6) {
            System.out.println("La contraseña debe tener al menos 6 caracteres");
            return -1;
        }
        
        // Crear el usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setTelefono(telefono);
        usuario.setDireccion(direccion);
        usuario.setEsVendedor(esVendedor);
        
        return usuarioService.registrar(usuario);
    }
    
    /**
     * Actualiza un usuario existente
     * @param id ID del usuario
     * @param nombre Nombre del usuario
     * @param apellido Apellido del usuario
     * @param email Email del usuario
     * @param password Contraseña del usuario
     * @param telefono Teléfono del usuario
     * @param direccion Dirección del usuario
     * @param esVendedor Indica si el usuario es vendedor
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizar(int id, String nombre, String apellido, String email, String password, 
                              String telefono, String direccion, boolean esVendedor) {
        // Validar datos
        if (id <= 0) {
            System.out.println("ID de usuario no válido");
            return false;
        }
        
        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("El nombre es obligatorio");
            return false;
        }
        
        if (apellido == null || apellido.trim().isEmpty()) {
            System.out.println("El apellido es obligatorio");
            return false;
        }
        
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            System.out.println("El email no es válido");
            return false;
        }
        
        // Obtener el usuario actual
        Usuario usuario = usuarioService.obtenerPorId(id);
        if (usuario == null) {
            System.out.println("El usuario no existe");
            return false;
        }
        
        // Actualizar los datos
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        
        // Solo actualizar la contraseña si se proporciona una nueva
        if (password != null && !password.trim().isEmpty()) {
            if (password.length() < 6) {
                System.out.println("La contraseña debe tener al menos 6 caracteres");
                return false;
            }
            usuario.setPassword(password);
        }
        
        usuario.setTelefono(telefono);
        usuario.setDireccion(direccion);
        usuario.setEsVendedor(esVendedor);
        
        return usuarioService.actualizar(usuario);
    }
    
    /**
     * Elimina un usuario
     * @param id ID del usuario a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminar(int id) {
        if (id <= 0) {
            System.out.println("ID de usuario no válido");
            return false;
        }
        
        return usuarioService.eliminar(id);
    }
    
    /**
     * Autentica un usuario
     * @param email Email del usuario
     * @param password Contraseña del usuario
     * @return Usuario autenticado o null si las credenciales son inválidas
     */
    public Usuario autenticar(String email, String password) {
        try {
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            System.out.println("El email no es válido");
            return null;
        }
        
        if (password == null || password.trim().isEmpty()) {
            System.out.println("La contraseña es obligatoria");
            return null;
        } 

        } catch (Exception e){
            System.err.println("Error al autenticar: " + e);
        }
        return usuarioService.autenticar(email, password);
        
    }
}