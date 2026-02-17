import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;

public class Reportes {

    // Cambiamos los parámetros: ahora recibe la lista de productos y el objeto Caja completo
    public static void guardarCierre(ArrayList<Producto> inventario, Caja caja) {

        String fecha = LocalDate.now().toString();
        String nombreArchivo = "Reporte_" + fecha + ".txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            writer.println("======= REPORTE DE VENTAS - MACADAMIA =======");
            writer.println("Fecha: " + fecha);
            writer.println("=============================================");

            // 1. RESUMEN DE INGRESOS POR MEDIO DE PAGO
            writer.println("\n--- RESUMEN DE CAJA ---");
            writer.println(String.format("Efectivo:  $%.2f", caja.getEfectivo()));
            writer.println(String.format("Nequi:     $%.2f", caja.getNequi()));
            writer.println(String.format("Daviplata: $%.2f", caja.getDaviplata()));
            writer.println("---------------------------------------------");
            writer.println(String.format("TOTAL DÍA: $%.2f", caja.getIngresosTotales()));

            // 2. DETALLE DE TRANSACCIONES
            writer.println("\n--- DETALLE DE VENTAS ---");
            writer.println(String.format("%-25s | %-5s | %-10s", "Producto [Pago]", "Cant", "Subtotal"));
            writer.println("---------------------------------------------");

            for (Caja.RegistroVenta v : caja.getHistorialVentas()) {
                writer.println(String.format("%-25s | %-5d | $%-10.2f",
                        v.nombre, v.cantidad, v.total));
            }

            // 3. ESTADO DEL INVENTARIO
            writer.println("\n--- STOCK FINAL ---");
            for (Producto p : inventario) {
                writer.println("- " + p.getnombre() + ": " + p.getstock() + " unidades.");
            }

            System.out.println("Reporte generado: " + nombreArchivo);

        } catch (IOException e) {
            System.out.println("Error al crear el reporte: " + e.getMessage());
        }
    }
}
