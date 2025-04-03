package visual;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.*;
import logico.*;

public class RegistrarLesion extends JDialog {
    private JComboBox<Jugador> cbJugador;
    private JTextField txtTipo;
    private JSpinner spinnerDias;
    private JFormattedTextField txtFechaInicio;
    private JLabel lblFechaFin, lblEquipo;
    private SerieNacional serie;

    public RegistrarLesion(SerieNacional serie) { // Correg� el typo en "SerieNacional"
        this.serie = serie;
        setTitle("Registrar Lesi�n");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setModal(true);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Jugador:"));
        cbJugador = new JComboBox<>();
        cbJugador.addActionListener(e -> actualizarEquipoJugador());
        panel.add(cbJugador);

        panel.add(new JLabel("Equipo:"));
        lblEquipo = new JLabel("Seleccione un jugador");
        panel.add(lblEquipo);

        panel.add(new JLabel("Tipo de Lesi�n:"));
        txtTipo = new JTextField();
        panel.add(txtTipo);

        panel.add(new JLabel("Fecha Inicio (dd/MM/yyyy):"));
        txtFechaInicio = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        txtFechaInicio.setValue(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))); // Corregido
        txtFechaInicio.addPropertyChangeListener("value", e -> calcularFechaFin());
        panel.add(txtFechaInicio);

        panel.add(new JLabel("D�as de Recuperaci�n:"));
        spinnerDias = new JSpinner(new SpinnerNumberModel(15, 1, 365, 1));
        spinnerDias.addChangeListener(e -> calcularFechaFin());
        panel.add(spinnerDias);

        panel.add(new JLabel("Fecha Estimada de Fin:"));
        lblFechaFin = new JLabel("");
        panel.add(lblFechaFin);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> registrarLesion());

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnCancelar);

        add(panel, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        cargarComboJugadores();
        calcularFechaFin();
    }

    private void cargarComboJugadores() {
        cbJugador.removeAllItems();
        if (serie != null && serie.getListEquipos() != null) {
            for (Equipo equipo : serie.getListEquipos()) {
                if (equipo.getJugadores() != null) {
                    for (Jugador jugador : equipo.getJugadores()) {
                        cbJugador.addItem(jugador);
                    }
                }
            }
        }
    }

    private void actualizarEquipoJugador() {
        Jugador jugador = (Jugador) cbJugador.getSelectedItem();
        if (jugador != null && serie != null && serie.getListEquipos() != null) {
            for (Equipo equipo : serie.getListEquipos()) {
                if (equipo.getJugadores() != null && equipo.getJugadores().contains(jugador)) {
                    lblEquipo.setText(equipo.getNombre());
                    break;
                }
            }
        }
    }

    private void calcularFechaFin() {
        try {
            String fechaStr = txtFechaInicio.getText();
            if (fechaStr != null && !fechaStr.isEmpty()) {
                LocalDate fechaInicio = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                int diasRecuperacion = (int) spinnerDias.getValue();
                LocalDate fechaFin = fechaInicio.plusDays(diasRecuperacion);
                lblFechaFin.setText(fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
        } catch (Exception e) {
            lblFechaFin.setText("Fecha inv�lida");
        }
    }

    private void registrarLesion() {
        try {
            // Validaci�n b�sica
            if (txtTipo.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el tipo de lesi�n", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Jugador jugador = (Jugador) cbJugador.getSelectedItem();
            if (jugador == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un jugador", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener fecha de inicio
            LocalDate fechaInicio = LocalDate.parse(txtFechaInicio.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            int diasRecuperacion = (int) spinnerDias.getValue();

            Lesion lesion = new Lesion(
                txtTipo.getText(),
                fechaInicio,
                fechaInicio.plusDays(diasRecuperacion),
                diasRecuperacion
            );

            jugador.registrarLesion(lesion);

            JOptionPane.showMessageDialog(this, "Lesi�n registrada exitosamente!");
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al registrar: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
