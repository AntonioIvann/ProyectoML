package Model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Compra {
    
    // * Atributos
    private int id;
    private Usuario comprador;
    private Date fecha;
    private BigDecimal total;
    private String estado;
    private List<DetalleCompra> detalles;
    
    // * Constructor vacío
    public Compra() {
    }
    
    // * Constructor
    public Compra(int id, Usuario comprador, Date fecha, BigDecimal total, String estado, List<DetalleCompra> detalles) {
        this.id = id;
        this.comprador = comprador;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
        this.detalles = detalles;
    }
    
    // * Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getComprador() {
        return comprador;
    }

    public void setComprador(Usuario comprador) {
        this.comprador = comprador;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<DetalleCompra> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleCompra> detalles) {
        this.detalles = detalles;
    }
    
    // * Método toString
    @Override
    public String toString() {
        System.out.println("████████████████████████████████");
        System.out.println("Compra ID: " + id);
        System.out.println("Comprador: " + comprador.getNombre() + " " + comprador.getApellido());
        System.out.println("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        System.out.println("Fecha: " + fecha);
        System.out.println("Total: $" + total);
        System.out.println("Estado: " + estado);
        System.out.println("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        System.out.println("Productos: " + (detalles != null ? detalles.size() : 0));
        System.out.println("████████████████████████████████");
        return "";
    }
}
