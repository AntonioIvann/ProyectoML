package Model;

public class Categoria {
    
    // * Atributos
    private int id;
    private String nombre;
    private String descripcion;
    
    // * Constructor vacío
    public Categoria() {
    }
    
    // * Constructor
    public Categoria(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
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
    
    // * Método toString
    @Override
    public String toString() {
        System.out.println("████████████████████████████████");
        System.out.println("Categoría: " + nombre);
        System.out.println("ID: " + id);
        System.out.println("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        System.out.println(descripcion);
        System.out.println("████████████████████████████████");
        return "";
    }
}
