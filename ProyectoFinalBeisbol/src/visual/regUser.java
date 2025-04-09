package visual;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import logical.Control;
import logical.User;

public class regUser extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private JTextField txtNombreUsuario;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JComboBox<String> cbTipo;

    public regUser() {
        setTitle("Registrar Nuevo Usuario");
        setBounds(100, 100, 450, 228);
        setLocationRelativeTo(null);
        setModal(true);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        // Componentes
        JLabel lblNombreUsuario = new JLabel("Nombre Usuario:");
        lblNombreUsuario.setBounds(20, 26, 97, 14);
        contentPanel.add(lblNombreUsuario);

        txtNombreUsuario = new JTextField();
        txtNombreUsuario.setBounds(20, 49, 127, 20);
        contentPanel.add(txtNombreUsuario);
        txtNombreUsuario.setColumns(10);

        cbTipo = new JComboBox<>();
        cbTipo.setModel(new DefaultComboBoxModel<>(new String[] {"<Seleccione>", "Administrador", "Comercial"}));
        cbTipo.setBounds(20, 113, 127, 20);
        contentPanel.add(cbTipo);

        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setBounds(20, 88, 97, 14);
        contentPanel.add(lblTipo);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(190, 49, 147, 20);
        contentPanel.add(txtPassword);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(189, 26, 97, 14);
        contentPanel.add(lblPassword);

        JLabel lblConfirmarPassword = new JLabel("Confirmar Password:");
        lblConfirmarPassword.setBounds(189, 88, 167, 14);
        contentPanel.add(lblConfirmarPassword);

        txtConfirmPassword = new JPasswordField();
        txtConfirmPassword.setBounds(190, 113, 147, 20);
        contentPanel.add(txtConfirmPassword);

        // Panel de botones
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPane.add(cancelButton);
    }

    private void registrarUsuario() {
        String usuario = txtNombreUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());
        String confirmPassword = new String(txtConfirmPassword.getPassword());
        String tipo = cbTipo.getSelectedItem().toString();

        // Validaciones
        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (tipo.equals("<Seleccione>")) {
            JOptionPane.showMessageDialog(this, "Seleccione un tipo de usuario", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Registrar el usuario
        User newUser = new User(tipo, usuario, password);
        Control.getInstance().regUser(newUser);
        JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente");
        dispose();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    regUser dialog = new regUser();
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    dialog.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}