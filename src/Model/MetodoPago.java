package Model;

public class MetodoPago {
    
    // * Atributos
    private int id;
    private String numeroTarjeta;
    private String cvv;
    private String fechaExpiracion;
    private String titular;
    private String tipo;
    
    // * Constructor vacío
    public MetodoPago() {
    }
    
    // * Constructor
    public MetodoPago(int id, String numeroTarjeta, String cvv, String fechaExpiracion, String titular, String tipo) {
        this.id = id;
        this.numeroTarjeta = numeroTarjeta;
        this.cvv = cvv;
        this.fechaExpiracion = fechaExpiracion;
        this.titular = titular;
        this.tipo = tipo;
    }
    
    // * Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    // * Método toString
    @Override
    public String toString() {
        System.out.println("████████████████████████████████");
        System.out.println("Método de Pago ID: " + id);
        System.out.println("Tipo: " + tipo);
        System.out.println("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        System.out.println("Titular: " + titular);
        System.out.println("Número: " + ocultarNumeroTarjeta());
        System.out.println("Fecha Expiración: " + fechaExpiracion);
        System.out.println("████████████████████████████████");
        return "";
    }
    
    // * Métodos adicionales
    private String ocultarNumeroTarjeta() {
        if (numeroTarjeta != null && numeroTarjeta.length() >= 4) {
            return "****-****-****-" + numeroTarjeta.substring(numeroTarjeta.length() - 4);
        }
        return "****-****-****-****";
    }
}
