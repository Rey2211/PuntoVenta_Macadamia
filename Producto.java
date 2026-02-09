public class Producto {
    private String nombre;
    private double precio;
    private int stock;

    public Producto(String nombre, double precio, int stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock =stock;
    }

    // El atributo Get nos ayuda a "leer" los datos
    public String getnombre() { return nombre; }
    public double getprecio() { return precio; }
    public int getstock() { return stock; }

    // El atributo Set nos ayuda a actualizar el stock luego de una venta

    public void setStock(int stock) { this.stock = stock; }


}
