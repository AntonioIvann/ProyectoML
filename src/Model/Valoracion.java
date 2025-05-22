package Model;

import java.util.Date;

public class Valoracion {
    
    // * Atributos
    private int id;
    private Producto producto;
    private Usuario usuario;
    private int puntuacion;
    private String comentario;
    private Date fecha;
    
    // * Constructor vacío
    public Valoracion() {
    }
    
    // * Constructor
    public Valoracion(int id, Producto producto, Usuario usuario, int puntuacion, String comentario, Date fecha) {
        this.id = id;
        this.producto = producto;
        this.usuario = usuario;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
        this.fecha = fecha;
    }
    
    // * Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        if (puntuacion >= 1 && puntuacion <= 5) {
            this.puntuacion = puntuacion;
        } else {
            throw new IllegalArgumentException("La puntuación debe estar entre 1 y 5");
        }
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    // * Método toString
    @Override
    public String toString() {
        System.out.println("████████████████████████████████");
        System.out.println("Valoración ID: " + id);
        System.out.println("Producto: " + (producto != null ? producto.getNombre() : "No asignado"));
        System.out.println("Usuario: " + (usuario != null ? usuario.getNombre() + " " + usuario.getApellido() : "No asignado"));
        System.out.println("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        System.out.println("Puntuación: " + puntuacion + "/5");
        System.out.println("Fecha: " + fecha);
        System.out.println("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        System.out.println("Comentario: " + comentario);
        System.out.println("████████████████████████████████");
        return "";
    }
}
