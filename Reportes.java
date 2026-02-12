import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;

public class Reportes {
    public static void guardarCierre(ArrayList<Producto> inventario, double totalCaja){
        //Fragmento de codigo para obtener la fecha en el archivo .txt
        String fecha = LocalDate.now().toString();
        String nombreArchivo = "Reporte_" + fecha + ".txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))){
            writer.println("==== Reporte de ventas - " + fecha + " ========");
            writer.println("Dinero total en caja; $" + totalCaja);
            writer.println("\nEstado Final Del Inventario");
            writer.println("---------------------------");
            for (Producto producto : inventario) {
                writer.println("producto" + producto.getnombre());
                writer.println("Stock Restante: " + producto.getstock());

            writer.println("---------------------------");

            }

            System.out.println("--- Reporte generado con exito --- " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al generar el Reporte " + e.getMessage());
        }
    }

}
