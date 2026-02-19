import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        // 1. Inicializamos los datos (estos se pasarán a la interfaz después)
        Inventario inventario = new Inventario();
        Caja caja = new Caja();

        // 2. Creamos el Login (como es un JDialog modal, el código se detiene aquí)
        Login ventanaLogin = new Login(null); // Pasamos null porque no hay ventana padre aún
        ventanaLogin.setVisible(true);

        // 3. Una vez se cierra el login (con dispose), verificamos si entró alguien
        if (ventanaLogin.isAutenticado()) {
            String rol = ventanaLogin.getRolUsuario();

            // 4. Si es correcto, abrimos la Interfaz pasandole lo necesario
            new Interfaz(rol, inventario, caja);
        } else {
            // Si el usuario cerró la ventana sin loguearse, salimos
            System.exit(0);
        }
    }
}
