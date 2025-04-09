package visual;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import logical.Control;
import logical.Persistencia;

public class Login extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtPassword;

    public Login() {
        configurarVentana();
        initComponents();
    }

    private void configurarVentana() {
        setTitle("Sistema de Béisbol - Login");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        panel.add(txtUsuario);

        panel.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        JButton btnLogin = new JButton("Iniciar Sesión");
        btnLogin.addActionListener(e -> autenticarUsuario());

        add(panel, BorderLayout.CENTER);
        add(btnLogin, BorderLayout.SOUTH);
    }

    private void autenticarUsuario() {
        String usuario = txtUsuario.getText();
        String password = new String(txtPassword.getPassword());

        if (Control.getInstance().confirmLogin(usuario, password)) {
            new VisualPrincipal().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        Persistencia.cargarDatos();
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}