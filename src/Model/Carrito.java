package Model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Carrito {
    
    // * Atributos
    private int id;
    private Usuario usuario;
    private List<ItemCarrito> items;
    private BigDecimal total;
    
    // * Constructor vacío
    public Carrito() {
        this.items = new ArrayList<>();
        this.total = BigDecimal.ZERO;
    }
    
    // * Constructor
    public Carrito(int id, Usuario usuario) {
        this.id = id;
        this.usuario = usuario;
        this.items = new ArrayList<>();
        this.total = BigDecimal.ZERO;
    }
    
    // * Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ItemCarrito> getItems() {
        return items;
    }

    public void setItems(List<ItemCarrito> items) {
        this.items = items;
        calcularTotal();
    }

    public BigDecimal getTotal() {
        return total;
    }
    
    // * Métodos adicionales
    public void calcularTotal() {
        this.total = BigDecimal.ZERO;
        for (ItemCarrito item : items) {
            this.total = this.total.add(item.getSubtotal());
        }
    }
    
    public void agregarProducto(Producto producto, int cantidad) {
        // Buscar si el producto ya está en el carrito
        for (ItemCarrito item : items) {
            if (item.getProducto().getId() == producto.getId()) {
                item.setCantidad(item.getCantidad() + cantidad);
                item.calcularSubtotal();
                calcularTotal();
                return;
            }
        }
        
        // Si no está, crear un nuevo item
        ItemCarrito nuevoItem = new ItemCarrito();
        nuevoItem.setProducto(producto);
        nuevoItem.setCantidad(cantidad);
        nuevoItem.setPrecioUnitario(producto.getPrecio());
        nuevoItem.calcularSubtotal();
        items.add(nuevoItem);
        calcularTotal();
    }
    
    public void eliminarProducto(int productoId) {
        items.removeIf(item -> item.getProducto().getId() == productoId);
        calcularTotal();
    }
    
    public void vaciar() {
        items.clear();
        total = BigDecimal.ZERO;
    }
    
    // * Método toString
    @Override
    public String toString() {
        System.out.println("████████████████████████████████");
        System.out.println("Carrito ID: " + id);
        System.out.println("Usuario: " + (usuario != null ? usuario.getNombre() + " " + usuario.getApellido() : "No asignado"));
        System.out.println("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        System.out.println("Items: " + items.size());
        System.out.println("Total: $" + total);
        System.out.println("████████████████████████████████");
        return "";
    }
}
