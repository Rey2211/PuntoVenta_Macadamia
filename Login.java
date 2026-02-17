import javax.swing.*;
import java.awt.*;

public class Login extends JDialog {
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private boolean autenticado = false;
    private String rolUsuario = "";

    public Login(Frame parent) {
        super(parent, "Acceso al Sistema - Macadamia", true);
        setLayout(new GridLayout(3, 2, 10, 10));
        setSize(300, 150);
        setLocationRelativeTo(parent);

        add(new JLabel("  Usuario:"));
        txtUsuario = new JTextField();
        add(txtUsuario);

        add(new JLabel("  Contraseña:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.addActionListener(e -> validar());
        add(btnEntrar);

        JButton btnSalir = new JButton("Salir");
        btnSalir.addActionListener(e -> System.exit(0));
        add(btnSalir);
    }

    private void validar() {
        String user = txtUsuario.getText();
        String pass = new String(txtPassword.getPassword());

        // Credenciales quemadas (Para un POS real, esto iría en base de datos)
        if (user.equals("admin") && pass.equals("1234")) {
            autenticado = true;
            rolUsuario = "ADMIN";
            dispose(); // Cierra el login
        } else if (user.equals("cajero") && pass.equals("0000")) {
            autenticado = true;
            rolUsuario = "CAJERO";
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o clave incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isAutenticado() { return autenticado; }
    public String getRolUsuario() { return rolUsuario; }
}

