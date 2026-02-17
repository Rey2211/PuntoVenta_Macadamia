import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        // Opcional: Intentar que la interfaz se vea como el sistema operativo (Windows/Mac)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Si falla, usará el estilo básico de Java
        }

        // Lanzamos la interfaz de forma segura en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            Interfaz ventana = new Interfaz();

            // Centrar la ventana en la pantalla
            ventana.setLocationRelativeTo(null);

            // Hacerla visible
            ventana.setVisible(true);

            System.out.println("Macadamia POS: Sistema iniciado correctamente.");
        });
    }
}