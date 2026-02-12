import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Inventario miInventario = new Inventario();
        Caja miCaja = new Caja();


        int opcion = 0;

        do {
            //Menu interactivo para el usuario
            System.out.println("\n ===== MAACADAMIA GESTION DE VENTAS =====");
            System.out.println("1. Ver Inventario");
            System.out.println("2. Registrar Venta");
            System.out.println("3. Ver las Ventas del dia");
            System.out.println("4. Vender Nuevo Producto");
            System.out.println("5. Salir");
            System.out.println("Seleccione una opcion: ");

            opcion = sc.nextInt();
            sc.nextLine(); //Limpiar Buffer del teclado

            //Las opciones que puede escoger el usuario y lo que ejecutan
            switch (opcion) {
                case 1 -> miInventario.listarTodo();
                case 2 -> ejecutarVenta(sc, miInventario, miCaja);
                case 3 -> miCaja.mostrarResumen();
                case 4 -> ejecutarVentaRapida(sc, miCaja);
                case 5 -> {
                    System.out.println("Generando Reporte del Día...");
                    Reportes.guardarCierre(miInventario.getListaProductos(),miCaja.getIngresosTotales());
                    System.out.println("Saliendo del sistema...: ");
                }
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 5);
    }

    // Métodos auxiliares para que el switch no sea gigante
    private static void ejecutarVenta(Scanner sc, Inventario inv, Caja caja) {
        System.out.print("Producto: ");
        String nombre = sc.nextLine();
        System.out.print("Cantidad: ");

        int cant = sc.nextInt();
        caja.procesarVenta(inv.buscarPorNombre(nombre), cant);
    }

    private static void ejecutarVentaRapida(Scanner sc, Caja caja) {
        System.out.println("\n--- VENTA DE OTRO PRODUCTO (Fuera de Inventario) ---");
        System.out.print("Producto: ");
        String producto = sc.nextLine();

        System.out.print("Precio por unidad: ");
        double precio = sc.nextDouble();

        System.out.print("Cantidad: ");
        int cantidad = sc.nextInt();
        caja.registrarVentaDirecta(producto, precio, cantidad);
    }
}