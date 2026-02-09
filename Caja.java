import java.sql.SQLOutput;

public class Caja {
    private double ingresosTotales;

    //Metodo para las ventas del inventario
    public void procesarVenta(Producto p, int cantidad) {
        if (p == null) {
            System.out.println("Error: Producto no encontrado");
            return;
        }
        if (p.getstock() >= cantidad) {
            double total = p.getprecio() * cantidad;
            p.setStock(p.getstock() - cantidad); //Actualiza el Stock del producto
            ingresosTotales += total;
            System.out.println("Venta Realizada: " + p.getnombre() + " x" + cantidad);
            System.out.println("Total: $" + total);
        } else {
            System.out.println("No hay suficiente Stock. Disponible: " + p.getstock());
        }
    }

    //Metodo para ventas fuera del inventario

    public void registrarVentaDirecta(String producto, double precio, int cantidad) {
        double total = precio * cantidad;
        ingresosTotales += total;
        System.out.println("Venta Realizada: " + producto + " x" + cantidad);
        System.out.println("Total: $" + total);
    }

    public void mostrarResumen(){
        System.out.println("\n Ventas totales acumuladas: $" + ingresosTotales);
    }
}
