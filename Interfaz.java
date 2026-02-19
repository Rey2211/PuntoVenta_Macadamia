import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Interfaz extends JFrame {
    private Inventario inventario;
    private Caja caja;
    private JPanel panelProductos; // Donde aparecerán los botones
    private JTextArea areaTicket;  // Para ver la cuenta actual
    private double totalVentaActual = 0;
    private JButton btnCobrar;

    public Interfaz(String rol, Inventario inv, Caja c) {
        this.inventario = inv;
        this.caja = c;

        setTitle("Macadamia POS - " + rol);
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // --- 1. PANEL NORTE: CATEGORÍAS ---
        JPanel panelCategorias = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String[] categorias = {"Bebidas", "Postres", "Varios"};

        for (String cat : categorias) {
            JButton btnCat = new JButton(cat);
            btnCat.setFont(new Font("Arial", Font.BOLD, 14));
            btnCat.addActionListener(e -> filtrarProductos(cat));
            panelCategorias.add(btnCat);
        }
        add(panelCategorias, BorderLayout.NORTH);

        // --- 2. PANEL CENTRAL: GRILLA DE PRODUCTOS ---
        panelProductos = new JPanel(new GridLayout(0, 3, 10, 10)); // 3 columnas
        add(new JScrollPane(panelProductos), BorderLayout.CENTER);

        // --- 3. PANEL ESTE: TICKET Y CONTROL ---
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setPreferredSize(new Dimension(300, 0));

        areaTicket = new JTextArea("--- TICKET DE VENTA ---\n");
        areaTicket.setEditable(false);
        panelDerecho.add(new JScrollPane(areaTicket), BorderLayout.CENTER);

        this.btnCobrar = new JButton("COBRAR (TOTAL: $0)");
        this.btnCobrar.setBackground(new Color(46, 125, 50));
        btnCobrar.setBackground(new Color(46, 125, 50)); // Verde
        btnCobrar.setForeground(Color.WHITE);
        btnCobrar.setFont(new Font("Arial", Font.BOLD, 16));
        btnCobrar.addActionListener(e -> finalizarVenta());
        panelDerecho.add(btnCobrar, BorderLayout.SOUTH);

        add(panelDerecho, BorderLayout.EAST);

        // Cargar todos los productos al inicio
        filtrarProductos("Bebidas");
        setVisible(true);
    }

    private void filtrarProductos(String categoria) {
        panelProductos.removeAll(); // Borra lo anterior

        for (Producto p : inventario.getListaProductos()) {
            if (p.getCategoria().equalsIgnoreCase(categoria)) {
                JButton btnProd = new JButton("<html><center>" + p.getnombre() + "<br>$" + p.getprecio() + "</center></html>");
                btnProd.setPreferredSize(new Dimension(150, 100));
                btnProd.addActionListener(e -> agregarAlTicket(p));
                panelProductos.add(btnProd);
            }
        }
        panelProductos.revalidate(); // Re-calcula el diseño
        panelProductos.repaint();    // Pinta de nuevo los botones
    }

    private void agregarAlTicket(Producto p) {
        // Aquí podrías pedir la cantidad con un JOptionPane si quieres
        totalVentaActual += p.getprecio();
        areaTicket.append("- " + p.getnombre() + " ($" + p.getprecio() + ")\n");

        btnCobrar.setText("COBRAR (TOTAL: $" + String.format("%.0f", totalVentaActual) + ")");

        areaTicket.setCaretPosition(areaTicket.getDocument().getLength());
    }

    private void finalizarVenta() {
        JOptionPane.showMessageDialog(this, "Venta realizada por: $" + totalVentaActual);
        // Aquí llamarías a caja.registrarVenta y limpiarías el ticket
        areaTicket.setText("--- TICKET DE VENTA ---\n");
        totalVentaActual = 0;
    }
}