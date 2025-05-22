package Model;

public class Usuario {
    
    // * Atributos
    private int id;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String telefono;
    private String direccion;
    private boolean esVendedor;
    
    // * Constructor vacío
    public Usuario() {
    }
    
    // * Constructor
    public Usuario(int id, String nombre, String apellido, String email, String password, String telefono, String direccion, boolean esVendedor) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.direccion = direccion;
        this.esVendedor = esVendedor;
    }
    
    // * Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean isEsVendedor() {
        return esVendedor;
    }

    public void setEsVendedor(boolean esVendedor) {
        this.esVendedor = esVendedor;
    }
    
    // * Método toString
    @Override
    public String toString() {
        System.out.println("████████████████████████████████");
        System.out.println("Usuario ID: " + id);
        System.out.println("Nombre: " + nombre + " " + apellido);
        System.out.println("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        System.out.println("Email: " + email);
        System.out.println("Teléfono: " + telefono);
        System.out.println("Dirección: " + direccion);
        System.out.println("Tipo: " + (esVendedor ? "Vendedor" : "Comprador"));
        System.out.println("████████████████████████████████");
        return "";
    }
}
