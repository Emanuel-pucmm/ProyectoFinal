package visual;

import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.time.format.*;
import java.time.temporal.*;
import logico.*;

public class RegistrarJugador extends JDialog {
    private JTextField txtNombre, txtNacionalidad, txtPosicion;
    private JComboBox<String> cbTipo, cbMano;
    private JSpinner spinnerAltura;
    private JFormattedTextField txtFechaNac;
    private JLabel lblEdad;
    private SerieNacional serie;

    public RegistrarJugador(SerieNacional serie) {
        this.serie = serie;
        setTitle("Registrar Jugador");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setModal(true);

        JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panel.add(txtNombre);

        panel.add(new JLabel("Fecha Nacimiento (dd/MM/yyyy):"));
        txtFechaNac = new JFormattedTextField(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        txtFechaNac.setValue(LocalDate.now());
        txtFechaNac.addPropertyChangeListener("value", e -> calcularEdad());
        panel.add(txtFechaNac);

        panel.add(new JLabel("Edad:"));
        lblEdad = new JLabel("0");
        panel.add(lblEdad);

        panel.add(new JLabel("Tipo:"));
        cbTipo = new JComboBox<>(new String[]{"Bateador", "Lanzador"});
        panel.add(cbTipo);

        panel.add(new JLabel("Nacionalidad:"));
        txtNacionalidad = new JTextField();
        panel.add(txtNacionalidad);

        panel.add(new JLabel("Mano Dominante:"));
        cbMano = new JComboBox<>(new String[]{"Derecha", "Izquierda"});
        panel.add(cbMano);

        panel.add(new JLabel("Altura (m):"));
        spinnerAltura = new JSpinner(new SpinnerNumberModel(1.70, 1.50, 2.20, 0.01));
        panel.add(spinnerAltura);

        panel.add(new JLabel("Posición/Tipo Lanzador:"));
        txtPosicion = new JTextField();
        panel.add(txtPosicion);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> registrarJugador());

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnCancelar);

        add(panel, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void calcularEdad() {
        try {
            LocalDate fechaNac = LocalDate.parse(txtFechaNac.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            int edad = Period.between(fechaNac, LocalDate.now()).getYears();
            lblEdad.setText(String.valueOf(edad));
        } catch (Exception e) {
            lblEdad.setText("0");
        }
    }

    private void registrarJugador() {
        try {
            LocalDate fechaNac = LocalDate.parse(txtFechaNac.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String edad = lblEdad.getText();
            String nombre = txtNombre.getText();
            String nacionalidad = txtNacionalidad.getText();
            String manoDominante = (String) cbMano.getSelectedItem();
            float altura = (float) spinnerAltura.getValue();
            String posicion = txtPosicion.getText();

            if (nombre.isEmpty() || nacionalidad.isEmpty() || posicion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Jugador jugador;
            if (cbTipo.getSelectedItem().equals("Bateador")) {
                jugador = new Bateador(nombre, fechaNac, edad, nacionalidad, manoDominante, LocalDate.now(), altura, posicion);
            } else {
                jugador = new Pitcher(nombre, fechaNac, edad, nacionalidad, manoDominante, LocalDate.now(), altura, posicion);
            }

            serie.agregarJugador(jugador);
            JOptionPane.showMessageDialog(this, "Jugador registrado exitosamente!");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en los datos ingresados", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}