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

    public RegistrarLesion(SerieNacional serie) {
        this.serie = serie;
        setTitle("Registrar Lesión");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setModal(true);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1) Jugador
        panel.add(new JLabel("Jugador:"));
        cbJugador = new JComboBox<>();
        cbJugador.addActionListener(e -> actualizarEquipoJugador());
        panel.add(cbJugador);

        // 2) Equipo
        panel.add(new JLabel("Equipo:"));
        lblEquipo = new JLabel("Seleccione un jugador");
        panel.add(lblEquipo);

        // 3) Tipo de Lesión
        panel.add(new JLabel("Tipo de Lesión:"));
        txtTipo = new JTextField();
        panel.add(txtTipo);

        // 4) Fecha Inicio
        panel.add(new JLabel("Fecha Inicio (dd/MM/yyyy):"));
        txtFechaInicio = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        txtFechaInicio.setValue(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        txtFechaInicio.addPropertyChangeListener("value", e -> calcularFechaFin());
        panel.add(txtFechaInicio);

        // 5) Días de Recuperación
        panel.add(new JLabel("Días de Recuperación:"));
        spinnerDias = new JSpinner(new SpinnerNumberModel(15, 1, 365, 1));
        spinnerDias.addChangeListener(e -> calcularFechaFin());
        panel.add(spinnerDias);

        // 6) Fecha Estimada de Fin
        panel.add(new JLabel("Fecha Estimada de Fin:"));
        lblFechaFin = new JLabel("");
        panel.add(lblFechaFin);

        // Botones
        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> registrarLesion());

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnCancelar);

        add(panel, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Cargar jugadores en el ComboBox
        cargarComboJugadores();
        // Calcular la fecha final por defecto
        calcularFechaFin();
    }

    /**
     * Carga todos los jugadores de todos los equipos en el ComboBox,
     * validando que no sean nulos.
     */
    private void cargarComboJugadores() {
        cbJugador.removeAllItems();
        try {
            // Asegurar que la serie y sus equipos no sean nulos
            if (serie != null && serie.getListEquipos() != null) {
                for (Equipo equipo : serie.getListEquipos()) {
                    if (equipo.getJugadores() != null) {
                        for (Jugador jugador : equipo.getJugadores()) {
                            // Verificar que el jugador no sea nulo
                            if (jugador != null) {
                                cbJugador.addItem(jugador);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "Error al cargar jugadores: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Actualiza la etiqueta que muestra el equipo del jugador seleccionado.
     */
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

    /**
     * Calcula la fecha estimada de fin de la lesión, en base a la
     * fecha de inicio y los días de recuperación, y la muestra en pantalla.
     */
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
            lblFechaFin.setText("Fecha inválida");
        }
    }

    /**
     * Registra la lesión en la lista de lesiones del jugador seleccionado.
     */
    private void registrarLesion() {
        try {
            // Validación básica
            if (txtTipo.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Ingrese el tipo de lesión",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Validar que se haya seleccionado un jugador
            Jugador jugador = (Jugador) cbJugador.getSelectedItem();
            if (jugador == null) {
                JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un jugador",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Obtener fecha de inicio
            LocalDate fechaInicio = LocalDate.parse(
                txtFechaInicio.getText(),
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
            );

            // Obtener días de recuperación
            int diasRecuperacion = (int) spinnerDias.getValue();

            // Crear el objeto Lesion
            Lesion lesion = new Lesion(
                txtTipo.getText(),
                fechaInicio,
                fechaInicio.plusDays(diasRecuperacion),
                diasRecuperacion
            );

            // Registrar la lesión en el jugador
            jugador.registrarLesion(lesion);

            JOptionPane.showMessageDialog(
                this,
                "Lesión registrada exitosamente!"
            );
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "Error al registrar: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
