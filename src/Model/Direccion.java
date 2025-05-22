package Model;

public class Direccion {
    
    // * Atributos
    private int id;
    private String calle;
    private String numero;
    private String ciudad;
    private String estado;
    private String pais;
    private String codigoPostal;
    private String referencia;
    private Usuario usuario;
    
    // * Constructor vacío
    public Direccion() {
    }
    
    // * Constructor
    public Direccion(int id, String calle, String numero, String ciudad, String estado, String pais, String codigoPostal, String referencia, Usuario usuario) {
        this.id = id;
        this.calle = calle;
        this.numero = numero;
        this.ciudad = ciudad;
        this.estado = estado;
        this.pais = pais;
        this.codigoPostal = codigoPostal;
        this.referencia = referencia;
        this.usuario = usuario;
    }
    
    // * Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    // * Método toString
    @Override
    public String toString() {
        System.out.println("████████████████████████████████");
        System.out.println("Dirección ID: " + id);
        System.out.println("Usuario: " + (usuario != null ? usuario.getNombre() + " " + usuario.getApellido() : "No asignado"));
        System.out.println("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        System.out.println(calle + " " + numero);
        System.out.println(ciudad + ", " + estado);
        System.out.println(pais + ", CP: " + codigoPostal);
        if (referencia != null && !referencia.isEmpty()) {
            System.out.println("Referencia: " + referencia);
        }
        System.out.println("████████████████████████████████");
        return "";
    }
    
    // * Métodos adicionales
    public String getDireccionCompleta() {
        return calle + " " + numero + ", " + ciudad + ", " + estado + ", " + pais + ", CP: " + codigoPostal;
    }
}
