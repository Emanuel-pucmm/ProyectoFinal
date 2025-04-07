package visual;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;
import logico.*;
import excepcion.*;
public class RegistrarLesion extends JDialog {
    private JComboBox<Jugador> cbJugador;
    private JTextField txtTipo;
    private JSpinner spinnerDias;
    private JTextField txtFechaInicio; // ← Usamos JTextField simple
    private JLabel lblFechaFin, lblEquipo;
    private SerieNacional serie;

    public RegistrarLesion(SerieNacional serie) {
        this.serie = serie;
        setTitle("Registrar Lesión [Texto en fecha]");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setModal(true);

        // Debug
        System.out.println("=== [DEBUG] Constructor RegistrarLesion ===");
        System.out.println("Cantidad de equipos en la serie: " + serie.getListEquipos().size());

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

        // 4) Fecha Inicio (dd/MM/yyyy) como JTextField
        panel.add(new JLabel("Fecha Inicio (dd/MM/yyyy):"));
        txtFechaInicio = new JTextField();
        // inicializamos con la fecha de hoy
        txtFechaInicio.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        // cada vez que pierda el foco, recalculamos fecha fin
        txtFechaInicio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                calcularFechaFin();
            }
        });
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

        // Cargar jugadores en el combo
        cargarComboJugadores();
        // Calcular la fecha fin por defecto
        calcularFechaFin();
    }

    private void cargarComboJugadores() {
        System.out.println("=== [DEBUG] RegistrarLesion -> cargarComboJugadores() ===");
        cbJugador.removeAllItems();
        try {
            if (serie != null && serie.getListEquipos() != null) {
                for (Equipo equipo : serie.getListEquipos()) {
                    if (equipo.getJugadores() != null) {
                        for (Jugador jugador : equipo.getJugadores()) {
                            if (jugador != null) {
                                cbJugador.addItem(jugador);
                                System.out.println("   - Jugador agregado al combo: " + jugador.getNombre());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                this,
                "Error al cargar jugadores: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
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
        } else {
            lblEquipo.setText("Seleccione un jugador");
        }
    }

    private void calcularFechaFin() {
        try {
            String fechaStr = txtFechaInicio.getText().trim();
            if (!fechaStr.isEmpty()) {
                LocalDate fechaInicio = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                int diasRecuperacion = (int) spinnerDias.getValue();
                LocalDate fechaFin = fechaInicio.plusDays(diasRecuperacion);
                lblFechaFin.setText(fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
        } catch (Exception e) {
            lblFechaFin.setText("Fecha inválida");
        }
    }

    private void registrarLesion() {
    	try {
            // Validaciones
            if(txtTipo.getText().trim().isEmpty()) {
                throw new CampoVacioExcepcion("tipo de lesión");
            }
            
            Jugador jugador = (Jugador) cbJugador.getSelectedItem();
            if(jugador == null) {
                throw new EquipoNoSeleccionadoExcepcion();
            }
            
            LocalDate fechaInicio;
            try {
                fechaInicio = LocalDate.parse(txtFechaInicio.getText().trim(), 
                                           DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                if(fechaInicio.isAfter(LocalDate.now())) {
                    throw new FechaInvalidaExcepcion("La fecha no puede ser futura");
                }
            } catch(Exception e) {
                throw new FechaInvalidaExcepcion();
            }
            
            int diasRecuperacion = (int) spinnerDias.getValue();
            if(diasRecuperacion <= 0) {
                throw new NumeroNegativoExcepcion("días de recuperación");
            }

            LocalDate fechaFin = fechaInicio.plusDays(diasRecuperacion);
            
            Lesion lesion = new Lesion(
                txtTipo.getText().trim(),
                fechaInicio,
                fechaFin,
                diasRecuperacion
            );

            jugador.registrarLesion(lesion);
            JOptionPane.showMessageDialog(this, "Lesión registrada exitosamente!");
            dispose();

        } catch (CampoVacioExcepcion | FechaInvalidaExcepcion | 
                 NumeroNegativoExcepcion | EquipoNoSeleccionadoExcepcion ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error inesperado: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

