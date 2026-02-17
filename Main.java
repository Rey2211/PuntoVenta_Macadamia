import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 1. Abrimos el Login primero
            Login ventanaLogin = new Login(null);
            ventanaLogin.setVisible(true);

            // 2. Solo si el usuario se autenticó correctamente...
            if (ventanaLogin.isAutenticado()) {
                Interfaz gui = new Interfaz();

                // 3. ¡Aquí pasamos el rol!
                gui.setRolActual(ventanaLogin.getRolUsuario());

                gui.setLocationRelativeTo(null);
                gui.setVisible(true);
            } else {
                // Si cerró el login sin entrar, cerramos todo
                System.exit(0);
            }
        });
    }
}