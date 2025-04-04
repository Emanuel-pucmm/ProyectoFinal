package visual;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import logico.*;

public class RegistrarJugador extends JDialog {
    private JTextField txtNombre, txtNacionalidad;
    private JComboBox<String> cbTipo, cbMano, cbPosicion;
    private JComboBox<Equipo> cbEquipo;
    private JSpinner spinnerAltura;
    private JTextField txtFechaNac;  // ← Usamos un JTextField normal para la fecha
    private JLabel lblEdad;
    private SerieNacional serie;

    private static final String[] POSICIONES_BATEADOR = {
        "Catcher", "Primera Base", "Segunda Base", "Tercera Base",
        "Shortstop", "Jardinero Izquierdo", "Jardinero Central", "Jardinero Derecho"
    };
    private static final String[] POSICIONES_PITCHER = {
        "Abridor", "Relevista", "Cerrador"
    };

    public RegistrarJugador(SerieNacional serie) {
        this.serie = serie;
        setTitle("Registrar Jugador");
        setSize(450, 450);
        setLocationRelativeTo(null);
        setModal(true);

        JPanel panel = new JPanel(new GridLayout(10, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // NOMBRE
        panel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panel.add(txtNombre);

        // FECHA NAC (como texto)
        panel.add(new JLabel("Fecha Nacimiento (dd/MM/yyyy):"));
        txtFechaNac = new JTextField();
        // Inicializamos con la fecha de hoy
        txtFechaNac.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        // Al perder el foco, recalculamos edad
        txtFechaNac.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                calcularEdad();
            }
        });
        panel.add(txtFechaNac);

        // EDAD
        panel.add(new JLabel("Edad:"));
        lblEdad = new JLabel("0");
        panel.add(lblEdad);

        // TIPO
        panel.add(new JLabel("Tipo:"));
        cbTipo = new JComboBox<>(new String[]{"Bateador", "Lanzador"});
        panel.add(cbTipo);

        // NACIONALIDAD
        panel.add(new JLabel("Nacionalidad:"));
        txtNacionalidad = new JTextField();
        panel.add(txtNacionalidad);

        // MANO DOMINANTE
        panel.add(new JLabel("Mano Dominante:"));
        cbMano = new JComboBox<>(new String[]{"Derecha", "Izquierda"});
        panel.add(cbMano);

        // ALTURA
        panel.add(new JLabel("Altura (m):"));
        spinnerAltura = new JSpinner(new SpinnerNumberModel(1.70, 1.50, 2.20, 0.01));
        panel.add(spinnerAltura);

        // POSICIÓN
        panel.add(new JLabel("Posición/Tipo Lanzador:"));
        cbPosicion = new JComboBox<>(POSICIONES_BATEADOR);
        panel.add(cbPosicion);

        // Cambiar posiciones según tipo
        cbTipo.addActionListener(e -> {
            if ("Bateador".equals(cbTipo.getSelectedItem())) {
                cbPosicion.setModel(new DefaultComboBoxModel<>(POSICIONES_BATEADOR));
            } else {
                cbPosicion.setModel(new DefaultComboBoxModel<>(POSICIONES_PITCHER));
            }
        });

        // EQUIPO
        panel.add(new JLabel("Equipo al que pertenece:"));
        cbEquipo = new JComboBox<>();
        cbEquipo.removeAllItems();
        for (Equipo eq : serie.getListEquipos()) {
            cbEquipo.addItem(eq);
        }
        panel.add(cbEquipo);

        // BOTONES
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
            String textoFecha = txtFechaNac.getText().trim();
            LocalDate fechaNac = LocalDate.parse(textoFecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            int edad = Period.between(fechaNac, LocalDate.now()).getYears();
            lblEdad.setText(String.valueOf(edad));
        } catch (Exception e) {
            // Si la fecha no es válida, dejamos la edad en 0
            lblEdad.setText("0");
        }
    }

    private void registrarJugador() {
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Parseamos la fecha que escribió el usuario en txtFechaNac
        LocalDate fechaNac;
        try {
            fechaNac = LocalDate.parse(txtFechaNac.getText().trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Fecha de nacimiento inválida. Use dd/MM/yyyy", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (fechaNac.isAfter(LocalDate.now())) {
            JOptionPane.showMessageDialog(this, "La fecha de nacimiento no puede ser futura.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int edadCalculada = Period.between(fechaNac, LocalDate.now()).getYears();
        if (edadCalculada <= 0) {
            JOptionPane.showMessageDialog(this, "La edad debe ser mayor que 0.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        lblEdad.setText(String.valueOf(edadCalculada));

        float altura = ((Number) spinnerAltura.getValue()).floatValue();
        String tipoSeleccionado = (String) cbTipo.getSelectedItem();

        Jugador nuevoJugador = null;
        try {
            if ("Bateador".equals(tipoSeleccionado)) {
                nuevoJugador = new Bateador(
                    txtNombre.getText().trim(),
                    fechaNac,
                    lblEdad.getText(),
                    txtNacionalidad.getText().trim(),
                    cbMano.getSelectedItem().toString(),
                    LocalDate.now(),
                    altura,
                    cbPosicion.getSelectedItem().toString()
                );
            } else {
                nuevoJugador = new Pitcher(
                    txtNombre.getText().trim(),
                    fechaNac,
                    lblEdad.getText(),
                    txtNacionalidad.getText().trim(),
                    cbMano.getSelectedItem().toString(),
                    LocalDate.now(),
                    altura,
                    cbPosicion.getSelectedItem().toString()
                );
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error creando el jugador: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Equipo equipoSeleccionado = (Equipo) cbEquipo.getSelectedItem();
        if (equipoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un equipo válido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        serie.getListJugadores().add(nuevoJugador);
        equipoSeleccionado.getJugadores().add(nuevoJugador);

        JOptionPane.showMessageDialog(this, "Jugador registrado exitosamente!");

        // DEBUG
        System.out.println("=== [DEBUG] RegistrarJugador ===");
        System.out.println("Se agregó el jugador: " + nuevoJugador.getNombre()
            + " al equipo: " + equipoSeleccionado.getNombre()
            + " (Jugadores en ese equipo ahora: " + equipoSeleccionado.getJugadores().size() + ")");
        System.out.println("Jugadores totales en la serie: " + serie.getListJugadores().size());

        dispose();
    }
}


