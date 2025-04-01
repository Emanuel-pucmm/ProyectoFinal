package visual;

import javax.swing.*;
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

    public RegistrarLesion(SerieNacional serie) {
        this.serie = serie;
        setTitle("Registrar Lesión");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setModal(true);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Jugador:"));
        cbJugador = new JComboBox<>();
        for (Equipo equipo : serie.getListEquipos()) {
            for (Jugador jugador : equipo.getJugadores()) {
                cbJugador.addItem(jugador);
            }
        }
        cbJugador.addActionListener(e -> actualizarEquipoJugador());
        panel.add(cbJugador);

        panel.add(new JLabel("Equipo:"));
        lblEquipo = new JLabel("Seleccione un jugador");
        panel.add(lblEquipo);

        panel.add(new JLabel("Tipo de Lesión:"));
        txtTipo = new JTextField();
        panel.add(txtTipo);

        panel.add(new JLabel("Fecha Inicio (dd/MM/yyyy):"));
        txtFechaInicio = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        txtFechaInicio.setValue(LocalDate.now());
        txtFechaInicio.addPropertyChangeListener("value", e -> calcularFechaFin());
        panel.add(txtFechaInicio);

        panel.add(new JLabel("Días de Recuperación:"));
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

        // Calcular fecha fin inicial
        calcularFechaFin();
    }

    private void actualizarEquipoJugador() {
        Jugador jugador = (Jugador) cbJugador.getSelectedItem();
        if (jugador != null) {
            for (Equipo equipo : serie.getListEquipos()) {
                if (equipo.getJugadores().contains(jugador)) {
                    lblEquipo.setText(equipo.getNombre());
                    break;
                }
            }
        }
    }

    private void calcularFechaFin() {
        try {
            LocalDate fechaInicio = LocalDate.parse(txtFechaInicio.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            int diasRecuperacion = (int) spinnerDias.getValue();
            LocalDate fechaFin = fechaInicio.plusDays(diasRecuperacion);
            lblFechaFin.setText(fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } catch (Exception e) {
            lblFechaFin.setText("Fecha inválida");
        }
    }

    private void registrarLesion() {
        try {
            Jugador jugador = (Jugador) cbJugador.getSelectedItem();
            String tipo = txtTipo.getText();
            LocalDate fechaInicio = LocalDate.parse(txtFechaInicio.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            int diasRecuperacion = (int) spinnerDias.getValue();
            LocalDate fechaFin = fechaInicio.plusDays(diasRecuperacion);

            if (tipo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el tipo de lesión", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Lesion lesion = new Lesion(
                tipo,
                fechaInicio,
                fechaFin,
                diasRecuperacion
            );
            jugador.registrarLesion(lesion);
            JOptionPane.showMessageDialog(this, "Lesión registrada exitosamente!");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en los datos ingresados", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}