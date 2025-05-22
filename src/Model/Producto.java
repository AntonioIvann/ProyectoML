package Model;

import java.math.BigDecimal;

public class Producto {
    
    // * Atributos
    private int id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private int stock;
    private Categoria categoria;
    private Usuario vendedor;
    private String imagen;
    private boolean activo;
    
    // * Constructor vacío
    public Producto() {
    }
    
    // * Constructor
    public Producto(int id, String nombre, String descripcion, BigDecimal precio, int stock, 
                   Categoria categoria, Usuario vendedor, String imagen, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
        this.vendedor = vendedor;
        this.imagen = imagen;
        this.activo = activo;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Usuario getVendedor() {
        return vendedor;
    }

    public void setVendedor(Usuario vendedor) {
        this.vendedor = vendedor;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    // * Método toString
    @Override
    public String toString() {
        System.out.println("████████████████████████████████");
        System.out.println(nombre);
        System.out.println(categoria.getNombre());
        System.out.println("ID: " + id);
        System.out.println("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        System.out.println("$" + precio);
        System.out.println("Stock: " + stock);
        System.out.println("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        System.out.println("Vendedor: " + vendedor.getNombre() + " " + vendedor.getApellido());
        System.out.println("");
        System.out.println(descripcion);
        System.out.println("Estado: " + (activo ? "Activo" : "Inactivo"));
        System.out.println("████████████████████████████████");
        return "";
    }
}
