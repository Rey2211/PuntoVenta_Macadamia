public class Producto {
    private String nombre;
    private int stock;
    private double precio;

    public Producto(String nombre, double precio, int stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }
    // METODO PARA REDUCIR EL STOCK
    public void reducirStock(int cantidad) {
        this.stock -= cantidad;
    }

    // METODO PARA CAMBIAR EL PRECIO (Lo necesitamos para el modo Admin)
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    // Asegúrate de tener estos getters también
    public String getnombre() { return nombre; }
    public int getstock() { return stock; }
    public double getprecio() { return precio; }


}
