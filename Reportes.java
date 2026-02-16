import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;

public class Reportes {
    public static void guardarCierre(ArrayList<Producto> inventario,
                                     ArrayList<Caja.RegistroVenta> ventasExtras,
                                     double totalCaja) {

        String fecha = LocalDate.now().toString();
        String nombreArchivo = "Reporte_" + fecha + ".txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            writer.println("======= REPORTE DE VENTAS - " + fecha + " =======");
            writer.println("Dinero TOTAL en caja: $" + totalCaja);
            writer.println("===========================================");

            // SECCIÓN 1: Ventas Extras (Lo nuevo)
            if (!ventasExtras.isEmpty()) {
                writer.println("\n--- DETALLE DE VENTAS EXTRAS/OTROS ---");
                writer.println(String.format("%-20s | %-5s | %-10s", "Producto", "Cant", "Total"));
                writer.println("-------------------------------------------");
                for (Caja.RegistroVenta v : ventasExtras) {
                    writer.println(String.format("%-20s | %-5d | $%-10.2f", v.nombre, v.cantidad, v.total));
                }
            }

            // SECCIÓN 2: Estado del Inventario
            writer.println("\n--- ESTADO FINAL DEL INVENTARIO FIJO ---");
            for (Producto p : inventario) {
                writer.println("Producto: " + p.getnombre() + " | Stock Restante: " + p.getstock());
            }

            System.out.println("✅ Reporte generado con éxito: " + nombreArchivo);

        } catch (IOException e) {
            System.out.println("❌ Error al guardar el reporte: " + e.getMessage());
        }
    }

    public static void guardarCierre(ArrayList<Producto> listaProductos, double ingresosTotales) {
    }
}
