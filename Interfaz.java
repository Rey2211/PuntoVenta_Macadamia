import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Interfaz extends JFrame {

    // 1. ATRIBUTOS (Solo se declaran una vez aquí arriba)
    private Inventario inventario;
    private Caja caja;
    private JTextArea areaPantalla;
    private JButton btnVenta, btnInventario, btnCerrar;
    private String rolActual;

    public Interfaz() {
        // Inicialización
        inventario = new Inventario();
        caja = new Caja();

        // Configuración de la Ventana
        setTitle("Macadamia Pastelería y Café - POS");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Área de texto
        areaPantalla = new JTextArea();
        areaPantalla.setEditable(false);
        areaPantalla.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaPantalla.setBackground(new Color(250, 250, 245));
        add(new JScrollPane(areaPantalla), BorderLayout.CENTER);

        // Panel de Botones
        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btnVenta = new JButton("VENTA");
        btnInventario = new JButton("INVENTARIO");
        btnCerrar = new JButton("CERRAR");

        panelBotones.add(btnVenta);
        panelBotones.add(btnInventario);
        panelBotones.add(btnCerrar);
        add(panelBotones, BorderLayout.SOUTH);

        // --- EVENTOS DE LOS BOTONES ---

        btnInventario.addActionListener(e -> {
            mostrarInventario();
            if ("ADMIN".equals(rolActual)) {
                int resp = JOptionPane.showConfirmDialog(this, "¿Editar precio?", "Admin", JOptionPane.YES_NO_OPTION);
                if (resp == JOptionPane.YES_OPTION) mostrarDialogoEditarPrecio();
            }
        });

        btnVenta.addActionListener(e -> mostrarDialogoDeVenta());

        btnCerrar.addActionListener(e -> {
            Reportes.guardarCierre(inventario.getListaProductos(), caja);
            JOptionPane.showMessageDialog(this, "Reporte generado.");
            System.exit(0);
        });
    }

    // 2. MÉTODOS DE APOYO (Fuera del constructor, dentro de la clase)

    public void setRolActual(String rol) {
        this.rolActual = rol;
        setTitle("Macadamia POS - Sesión: " + rol);
    }

    private void mostrarInventario() {
        areaPantalla.setText("====== INVENTARIO ======\n\n");
        for (Producto p : inventario.getListaProductos()) {
            areaPantalla.append(String.format("- %-18s | Stock: %-3d | Precio: $%.2f\n",
                    p.getnombre(), p.getstock(), p.getprecio()));
        }
    }

    private void mostrarDialogoDeVenta() {
        JComboBox<String> comboProd = new JComboBox<>();
        for (Producto p : inventario.getListaProductos()) comboProd.addItem(p.getnombre());

        JTextField txtCant = new JTextField("1");
        JComboBox<String> comboPago = new JComboBox<>(new String[]{"Efectivo", "Nequi", "Daviplata"});

        JPanel pan = new JPanel(new GridLayout(0, 1));
        pan.add(new JLabel("Producto:")); pan.add(comboProd);
        pan.add(new JLabel("Cantidad:")); pan.add(txtCant);
        pan.add(new JLabel("Pago:")); pan.add(comboPago);

        if (JOptionPane.showConfirmDialog(this, pan, "Venta", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                String nombre = (String) comboProd.getSelectedItem();
                int cant = Integer.parseInt(txtCant.getText());
                String pago = (String) comboPago.getSelectedItem();

                Producto p = inventario.buscarPorNombre(nombre);
                if (p != null && p.getstock() >= cant) {
                    double total = p.getprecio() * cant;
                    p.reducirStock(cant);
                    caja.registrarVenta(nombre, cant, total, pago);
                    areaPantalla.setText("Venta OK: " + nombre + " x" + cant + " (" + pago + ")");
                } else {
                    JOptionPane.showMessageDialog(this, "Stock insuficiente.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos.");
            }
        }
    }

    private void mostrarDialogoEditarPrecio() {
        JComboBox<String> combo = new JComboBox<>();
        for (Producto p : inventario.getListaProductos()) {
            combo.addItem(p.getnombre());
        }
        JTextField txtPre = new JTextField();

        JPanel pan = new JPanel(new GridLayout(0, 1));
        pan.add(new JLabel("Seleccione Producto:"));
        pan.add(combo);
        pan.add(new JLabel("Ingrese Nuevo Precio:"));
        pan.add(txtPre);

        if (JOptionPane.showConfirmDialog(this, pan, "Editar Precio - MODO ADMIN", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                double nuevoP = Double.parseDouble(txtPre.getText());
                String nombreProd = (String) combo.getSelectedItem();

                // --- CONEXIÓN CON LA BASE DE DATOS ---
                // Aquí es donde ocurre la magia: guardamos permanentemente
                inventario.actualizarPrecioEnDB(nombreProd, nuevoP);

                areaPantalla.setText("PRECIO ACTUALIZADO\n" + nombreProd + " ahora cuesta $" + nuevoP);
                mostrarInventario(); // Refrescamos la pantalla para ver el cambio

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: Ingrese un valor numérico válido.");
            }
        }
    }
}