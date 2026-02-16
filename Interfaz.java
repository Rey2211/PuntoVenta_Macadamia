import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Interfaz extends JFrame {

    // Lógica del negocio
    private Inventario inventario;
    private Caja caja;

    // Componentes visuales
    private JTextArea areaPantalla;
    private JButton btnVenta, btnInventario, btnCerrar;

    public Interfaz() {
        // 1. Inicializamos la lógica (Carga los productos fijos)
        inventario = new Inventario();
        caja = new Caja();

        // 2. Configuración de la Ventana Principal
        setTitle("Punto de Venta - Macadamia");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- ZONA CENTRAL: PANTALLA DE INFORMACIÓN ---
        areaPantalla = new JTextArea();
        areaPantalla.setEditable(false);
        areaPantalla.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaPantalla.setBackground(new Color(250, 250, 240)); // Color crema suave
        add(new JScrollPane(areaPantalla), BorderLayout.CENTER);

        // --- ZONA SUR: BOTONERA SIMPLIFICADA (3 Botones) ---
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 3, 10, 10)); // 1 fila, 3 col, separación de 10px
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen alrededor

        btnVenta = new JButton("REALIZAR VENTA");
        btnInventario = new JButton("VER INVENTARIO");
        btnCerrar = new JButton("CERRAR SISTEMA");

        // Personalizamos colores para diferenciar funciones
        btnVenta.setBackground(new Color(144, 238, 144)); // Verde claro
        btnInventario.setBackground(new Color(173, 216, 230)); // Azul claro
        btnCerrar.setBackground(new Color(255, 182, 193)); // Rojo claro

        panelBotones.add(btnVenta);
        panelBotones.add(btnInventario);
        panelBotones.add(btnCerrar);

        add(panelBotones, BorderLayout.SOUTH);

        // =======================================================
        //                   LÓGICA DE LOS BOTONES
        // =======================================================

        // 1. BOTÓN VER INVENTARIO (Ahora con opción de edición)
        btnInventario.addActionListener(e -> {
            // Primero: Mostramos el inventario en el área de texto como siempre
            areaPantalla.setText("");
            areaPantalla.append("====== INVENTARIO ACTUAL ======\n\n");
            for (Producto p : inventario.getListaProductos()) {
                areaPantalla.append(String.format("- %-20s | Stock: %d | Precio: $%.2f\n",
                        p.getnombre(), p.getstock(), p.getprecio()));
            }
            areaPantalla.append("\n===============================\n");

            // Segundo: Preguntamos si desea editar algo usando un cuadro de confirmación
            int respuesta = JOptionPane.showConfirmDialog(this,
                    "¿Deseas editar el precio de algún producto?",
                    "Gestión de Inventario",
                    JOptionPane.YES_NO_OPTION);

            if (respuesta == JOptionPane.YES_OPTION) {
                mostrarDialogoEditarPrecio();
            }
        });

        // 2. BOTÓN REALIZAR VENTA (¡Aquí está el cambio grande!)
        btnVenta.addActionListener(e -> {
            mostrarDialogoDeVenta();
        });

        // 3. BOTÓN CERRAR (Genera reporte y sale)
        btnCerrar.addActionListener(e -> {
            Reportes.guardarCierre(
                    inventario.getListaProductos(),
                    caja.getHistorialVentasExtras(), // Si aún usas ventas extras, sino borra esta línea
                    caja.getIngresosTotales()
            );
            JOptionPane.showMessageDialog(this, "Reporte guardado. ¡Hasta mañana!");
            System.exit(0);
        });
    }
    private void mostrarDialogoEditarPrecio() {
        // 1. Lista de productos para elegir cuál editar
        JComboBox<String> comboProductos = new JComboBox<>();
        for (Producto p : inventario.getListaProductos()) {
            comboProductos.addItem(p.getnombre());
        }

        // 2. Campo para el nuevo precio
        JTextField txtNuevoPrecio = new JTextField();

        JPanel panelEditar = new JPanel(new GridLayout(0, 1));
        panelEditar.add(new JLabel("Selecciona el producto a modificar:"));
        panelEditar.add(comboProductos);
        panelEditar.add(new JLabel("Nuevo precio ($):"));
        panelEditar.add(txtNuevoPrecio);

        int resultado = JOptionPane.showConfirmDialog(null, panelEditar,
                "Actualizar Precio", JOptionPane.OK_CANCEL_OPTION);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                String nombre = (String) comboProductos.getSelectedItem();
                double precioNuevo = Double.parseDouble(txtNuevoPrecio.getText());

                // 3. Buscamos y actualizamos
                Producto p = inventario.buscarPorNombre(nombre);
                if (p != null) {
                    p.setPrecio(precioNuevo); // Necesitas crear este setter en la clase Producto
                    JOptionPane.showMessageDialog(this, "Precio actualizado: " + nombre + " ahora cuesta $" + precioNuevo);

                    // Refrescamos la pantalla para ver el cambio
                    btnInventario.doClick();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingresa un precio válido");
            }
        }
    }


    // --- METODO PRIVADO PARA GENERAR FORMULARIO DE VENTA ---
    private void mostrarDialogoDeVenta() {
        // Crear el ComboBox (Lista desplegable)
        JComboBox<String> comboProductos = new JComboBox<>();

        // Llenamos la lista con los nombres de TU inventario real
        for (Producto p : inventario.getListaProductos()) {
            comboProductos.addItem(p.getnombre());
        }

        // Campo para la cantidad
        JTextField txtCantidad = new JTextField("1");

        // Panel auxiliar para poner ambos elementos juntos
        JPanel panelVenta = new JPanel(new GridLayout(0, 1));
        panelVenta.add(new JLabel("Selecciona el Producto:"));
        panelVenta.add(comboProductos);
        panelVenta.add(new JLabel("Cantidad a vender:"));
        panelVenta.add(txtCantidad);

        // Mostramos la ventana emergente con el combo y el campo de texto
        int resultado = JOptionPane.showConfirmDialog(null, panelVenta,
                "Nueva Venta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // Si el usuario le dio a "OK"
        if (resultado == JOptionPane.OK_OPTION) {
            try {
                String nombreSeleccionado = (String) comboProductos.getSelectedItem();
                int cantidad = Integer.parseInt(txtCantidad.getText());

                // Buscamos el objeto producto real
                Producto productoReal = inventario.buscarPorNombre(nombreSeleccionado);

                // Ejecutamos la venta
                if (productoReal != null) {
                    // Verificamos si hay stock suficiente antes de vender
                    if (productoReal.getstock() >= cantidad) {
                        caja.procesarVenta(productoReal, cantidad);
                        areaPantalla.setText("VENTA EXITOSA:\n" +
                                cantidad + "x " + nombreSeleccionado +
                                "\nTotal: $" + (productoReal.getprecio() * cantidad));
                    } else {
                        JOptionPane.showMessageDialog(this, "⚠No hay suficiente stock. Quedan: " + productoReal.getstock());
                    }
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: La cantidad debe ser un número entero.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Interfaz gui = new Interfaz();
            gui.setVisible(true);
            gui.setLocationRelativeTo(null);
        });

    }

}
