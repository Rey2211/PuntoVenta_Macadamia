import java.util.ArrayList;

public class Caja {
    // Variables para separar el dinero por "bolsillos"
    private double efectivo;
    private double nequi;
    private double daviplata;

    // Lista para el historial de ventas del día
    private ArrayList<RegistroVenta> historialVentas;

    public Caja() {
        this.efectivo = 0.0;
        this.nequi = 0.0;
        this.daviplata = 0.0;
        this.historialVentas = new ArrayList<>();
    }

    /**
     * Registra una venta procesada desde el inventario.
     * @param producto Nombre del producto vendido.
     * @param cantidad Unidades vendidas.
     * @param total Valor total de la transacción.
     * @param metodoPago "Efectivo", "Nequi" o "Daviplata".
     */
    public void registrarVenta(String producto, int cantidad, double total, String metodoPago) {
        // 1. Sumamos al contador específico según el metodo de pago
        switch (metodoPago) {
            case "Efectivo":
                this.efectivo += total;
                break;
            case "Nequi":
                this.nequi += total;
                break;
            case "Daviplata":
                this.daviplata += total;
                break;
        }

        // 2. Guardamos el registro en el historial para el reporte detallado
        // Incluimos el metodo de pago en el nombre para que aparezca en el .txt
        String detalle = producto + " [" + metodoPago + "]";
        historialVentas.add(new RegistroVenta(detalle, cantidad, total));
    }

    // --- GETTERS PARA EL REPORTE ---

    public double getEfectivo() { return efectivo; }

    public double getNequi() { return nequi; }

    public double getDaviplata() { return daviplata; }

    public double getIngresosTotales() {
        return efectivo + nequi + daviplata;
    }

    public ArrayList<RegistroVenta> getHistorialVentas() {
        return historialVentas;
    }

    // --- CLASE INTERNA PARA EL REGISTRO ---
    public static class RegistroVenta {
        public String nombre;
        public int cantidad;
        public double total;

        public RegistroVenta(String nombre, int cantidad, double total) {
            this.nombre = nombre;
            this.cantidad = cantidad;
            this.total = total;
        }
    }
    public String generarReporte() {
        StringBuilder sb = new StringBuilder();
        sb.append("======= REPORTE DE CIERRE =======\n");
        sb.append("Total en Caja: $").append(this.historialVentas).append("\n"); // Usa tu variable de saldo
        sb.append("=================================");
        return sb.toString();
    }
}