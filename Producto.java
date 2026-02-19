public class Producto {
    private String nombre;
    private double precio;
    private int stock;
    private boolean manejaStock;
    private String categoria; // <--- Nuevo campo
    private String rutaImagen; // <--- Para el icono

    public Producto(String nombre, double precio, int stock, boolean manejaStock, String categoria, String rutaImagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.manejaStock = manejaStock;
        this.categoria = categoria;
        this.rutaImagen = rutaImagen;
    }

    // Getters y Setters
    public boolean isManejaStock() { return manejaStock; }
    public void setManejaStock(boolean manejaStock) { this.manejaStock = manejaStock; }
    public String getnombre() { return nombre; }
    public int getstock() { return stock; }
    public double getprecio() { return precio; }
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    // metodo de reducir stock
    public void reducirStock(int cantidad) {
        if (this.manejaStock) { // Solo resta si el producto es de inventario físico
            if (this.stock >= cantidad) {
                this.stock -= cantidad;
            }
        }
        // Si manejaStock es false (como el café), no hace nada y deja el stock intacto.
    }

    // Actualiza tu constructor para incluirlo
    public Producto(String nombre, double precio, int stock, boolean manejaStock, String categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.manejaStock = manejaStock;
        this.categoria = categoria;
    }

    public String getCategoria() { return categoria; }

}
