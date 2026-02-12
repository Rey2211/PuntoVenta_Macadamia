import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Interfaz extends JFrame {
    // Importamos la logica
    private Inventario inventario;
    private Caja caja;

    // Parte Visual
    private JTextArea areaPantalla;
    private JButton btnInventario, btnVenta, btnVentaRapida, btnCierre;

    public Interfaz() {
        // Inicializamos la lógica
        inventario = new Inventario();
        caja = new Caja();

        // Configuración de la ventana (JFrame)
        setTitle("Macadamia Pastelería y Café");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout()); // Organiza en Norte, Sur, Este, Oeste y Centro

        // 1. Área de texto para mostrar información (Centro)
        areaPantalla = new JTextArea();
        areaPantalla.setEditable(false);
        areaPantalla.setBackground(new Color(245, 245, 220)); // Color del fondo
        add(new JScrollPane(areaPantalla), BorderLayout.CENTER);

        // 2. Panel de botones (Sur)
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 4)); // 1 fila, 4 columnas

        btnInventario = new JButton("Ver Inventario");
        btnVenta = new JButton("Venta");
        btnVentaRapida = new JButton("Venta Otro");
        btnCierre = new JButton("Cierre/Salir");

        panelBotones.add(btnInventario);
        panelBotones.add(btnVenta);
        panelBotones.add(btnVentaRapida);
        panelBotones.add(btnCierre);

        add(panelBotones, BorderLayout.SOUTH);

        // --- ASIGNAR ACCIONES A LOS BOTONES ---

        // Acción de Ver Inventario
        btnInventario.addActionListener(e -> {
            areaPantalla.setText("--- ESTADO DEL INVENTARIO ---\n");
            for (Producto p : inventario.getListaProductos()) {
                areaPantalla.append(p.getnombre() + " - Stock: " + p.getstock() + " - $" + p.getprecio() + "\n");
            }
        });

        // Acción de Cierre de Día
        btnCierre.addActionListener(e -> {
            Reportes.guardarCierre(inventario.getListaProductos(), caja.getIngresosTotales());
            JOptionPane.showMessageDialog(this, "Reporte generado. El sistema se cerrará.");
            System.exit(0);
        });

        // Acción de Venta (Simplificada para empezar)
        btnVenta.addActionListener(e -> {
            String nombre = JOptionPane.showInputDialog(this, "Nombre del producto:");
            String cantStr = JOptionPane.showInputDialog(this, "Cantidad:");

            if (nombre != null && cantStr != null) {
                int cant = Integer.parseInt(cantStr);
                Producto p = inventario.buscarPorNombre(nombre);
                caja.procesarVenta(p, cant);
                areaPantalla.setText("Venta registrada: " + nombre + " x" + cant);
            }
        });
    }

    public static void main(String[] args) {
        // Ejecutamos la interfaz
        SwingUtilities.invokeLater(() -> {
            Interfaz gui = new Interfaz();
            gui.setVisible(true);
            gui.setLocationRelativeTo(null); // Centra la ventana
        });
    }
}
