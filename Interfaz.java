import javax.swing.*;
import java.awt.*;

public class Interfaz extends JFrame {

    private Inventario inventario;
    private Caja caja;
    private JTextArea areaPantalla;
    private JButton btnVenta, btnInventario, btnCerrar;

    public Interfaz() {
        inventario = new Inventario();
        caja = new Caja();

        // Configuración de la Ventana
        setTitle("Macadamia Pastelería y Café - Punto de Venta");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Área de texto (Pantalla)
        areaPantalla = new JTextArea();
        areaPantalla.setEditable(false);
        areaPantalla.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaPantalla.setBackground(new Color(250, 250, 245));
        add(new JScrollPane(areaPantalla), BorderLayout.CENTER);

        // Panel de Botones Principal
        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btnVenta = new JButton("REALIZAR VENTA");
        btnInventario = new JButton("VER INVENTARIO");
        btnCerrar = new JButton("   CERRAR SISTEMA");

        // Colores temáticos
        btnVenta.setBackground(new Color(180, 230, 180));
        btnInventario.setBackground(new Color(180, 210, 230));
        btnCerrar.setBackground(new Color(230, 180, 180));

        panelBotones.add(btnVenta);
        panelBotones.add(btnInventario);
        panelBotones.add(btnCerrar);
        add(panelBotones, BorderLayout.SOUTH);

        // --- EVENTOS ---

        btnInventario.addActionListener(e -> {
            mostrarInventario();
            int respuesta = JOptionPane.showConfirmDialog(this, "¿Deseas editar el precio de algún producto?", "Gestión", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                mostrarDialogoEditarPrecio();
            }
        });

        btnVenta.addActionListener(e -> mostrarDialogoDeVenta());

        btnCerrar.addActionListener(e -> {
            Reportes.guardarCierre(inventario.getListaProductos(), caja);
            JOptionPane.showMessageDialog(this, "Reporte generado. ¡Hasta luego!");
            System.exit(0);
        });
    }

    private void mostrarInventario() {
        areaPantalla.setText("====== ESTADO DEL INVENTARIO ======\n\n");
        for (Producto p : inventario.getListaProductos()) {
            areaPantalla.append(String.format("- %-18s | Stock: %-3d | Precio: $%.2f\n",
                    p.getnombre(), p.getstock(), p.getprecio()));
        }
    }

    private void mostrarDialogoDeVenta() {
        // Preparar componentes del formulario
        JComboBox<String> comboProductos = new JComboBox<>();
        for (Producto p : inventario.getListaProductos()) {
            comboProductos.addItem(p.getnombre());
        }

        JTextField txtCantidad = new JTextField("1");

        // --- Selector de Métodos de Pago ---
        String[] metodos = {"Efectivo", "Nequi", "Daviplata"};
        JComboBox<String> comboPagos = new JComboBox<>(metodos);

        JPanel formulario = new JPanel(new GridLayout(0, 1, 5, 5));
        formulario.add(new JLabel("Seleccione Producto:"));
        formulario.add(comboProductos);
        formulario.add(new JLabel("Cantidad:"));
        formulario.add(txtCantidad);
        formulario.add(new JLabel("Método de Pago:"));
        formulario.add(comboPagos);

        int result = JOptionPane.showConfirmDialog(this, formulario, "Nueva Venta", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String nombre = (String) comboProductos.getSelectedItem();
                int cant = Integer.parseInt(txtCantidad.getText());
                String medioPago = (String) comboPagos.getSelectedItem();

                Producto p = inventario.buscarPorNombre(nombre);
                if (p != null && p.getstock() >= cant) {
                    double total = p.getprecio() * cant;


                    // Llamamos a la nueva lógica de la Caja
                    caja.registrarVenta(nombre, cant, total, medioPago);

                    areaPantalla.setText("VENTA REGISTRADA\n------------------\n" +
                            "Producto: " + nombre + "\n" +
                            "Cantidad: " + cant + "\n" +
                            "Pago vía: " + medioPago + "\n" +
                            "Total:    $" + total);
                } else {
                    JOptionPane.showMessageDialog(this, "Stock insuficiente.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: Ingrese una cantidad válida.");
            }
        }
    }

    private void mostrarDialogoEditarPrecio() {
        JComboBox<String> combo = new JComboBox<>();
        for (Producto p : inventario.getListaProductos()) { combo.addItem(p.getnombre()); }
        JTextField txtPrecio = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Producto:"));
        panel.add(combo);
        panel.add(new JLabel("Nuevo Precio:"));
        panel.add(txtPrecio);

        if (JOptionPane.showConfirmDialog(this, panel, "Editar Precio", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                double nuevoP = Double.parseDouble(txtPrecio.getText());
                Producto p = inventario.buscarPorNombre((String) combo.getSelectedItem());
                if (p != null) {
                    p.setPrecio(nuevoP);
                    mostrarInventario();
                }
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Precio no válido."); }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Interfaz().setVisible(true);
        });
    }
}
