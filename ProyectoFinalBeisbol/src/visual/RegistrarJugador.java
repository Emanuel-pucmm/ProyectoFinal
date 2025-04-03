package visual;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import logico.*;

public class RegistrarJugador extends JDialog {
    private JTextField txtNombre, txtNacionalidad;
    private JComboBox<String> cbTipo, cbMano, cbPosicion;
    private JComboBox<Equipo> cbEquipo;
    private JSpinner spinnerAltura;
    private JFormattedTextField txtFechaNac;
    private JLabel lblEdad;
    private SerieNacional serie;

    // Posibles posiciones
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

        // Panel principal
        JPanel panel = new JPanel(new GridLayout(10, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ========== NOMBRE ==========
        panel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panel.add(txtNombre);

        // ========== FECHA NACIMIENTO (dd/MM/yyyy) ==========
        panel.add(new JLabel("Fecha Nacimiento (dd/MM/yyyy):"));
        txtFechaNac = new JFormattedTextField();
        DateFormatter dateFormatter = new DateFormatter(new SimpleDateFormat("dd/MM/yyyy"));
        txtFechaNac.setFormatterFactory(new DefaultFormatterFactory(dateFormatter));
        // Fecha por defecto: hoy
        String fechaHoy = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        txtFechaNac.setText(fechaHoy);
        // Listener para recalcular la edad
        txtFechaNac.addPropertyChangeListener("value", e -> calcularEdad());
        panel.add(txtFechaNac);

        // ========== EDAD ==========
        panel.add(new JLabel("Edad:"));
        lblEdad = new JLabel("0");
        panel.add(lblEdad);

        // ========== TIPO (Bateador / Lanzador) ==========
        panel.add(new JLabel("Tipo:"));
        cbTipo = new JComboBox<>(new String[]{"Bateador", "Lanzador"});
        panel.add(cbTipo);

        // ========== NACIONALIDAD ==========
        panel.add(new JLabel("Nacionalidad:"));
        txtNacionalidad = new JTextField();
        panel.add(txtNacionalidad);

        // ========== MANO DOMINANTE ==========
        panel.add(new JLabel("Mano Dominante:"));
        cbMano = new JComboBox<>(new String[]{"Derecha", "Izquierda"});
        panel.add(cbMano);

        // ========== ALTURA ==========
        panel.add(new JLabel("Altura (m):"));
        spinnerAltura = new JSpinner(new SpinnerNumberModel(1.70, 1.50, 2.20, 0.01));
        panel.add(spinnerAltura);

        // ========== POSICIÓN O ROL DE LANZADOR ==========
        panel.add(new JLabel("Posición/Tipo Lanzador:"));
        cbPosicion = new JComboBox<>(POSICIONES_BATEADOR);
        panel.add(cbPosicion);

        // Cambiar combo de posiciones cuando cambie el tipo
        cbTipo.addActionListener(e -> {
            if (cbTipo.getSelectedItem().equals("Bateador")) {
                cbPosicion.setModel(new DefaultComboBoxModel<>(POSICIONES_BATEADOR));
            } else {
                cbPosicion.setModel(new DefaultComboBoxModel<>(POSICIONES_PITCHER));
            }
        });

        // ========== EQUIPO AL QUE PERTENECE ==========
        panel.add(new JLabel("Equipo al que pertenece:"));
        cbEquipo = new JComboBox<>();
        // Cargar equipos de la serie en el ComboBox
        cbEquipo.removeAllItems();
        for (Equipo eq : serie.getListEquipos()) {
            cbEquipo.addItem(eq);
        }
        panel.add(cbEquipo);

        // ========== BOTONES ==========
        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> registrarJugador());

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnCancelar);

        // Agregar el panel principal y el panel de botones
        add(panel, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * Calcula la edad a partir de la fecha en txtFechaNac
     * y la muestra en lblEdad.
     */
    private void calcularEdad() {
        try {
            String textoFecha = txtFechaNac.getText().trim();
            LocalDate fechaNac = LocalDate.parse(textoFecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            int edad = Period.between(fechaNac, LocalDate.now()).getYears();
            lblEdad.setText(String.valueOf(edad));
        } catch (DateTimeException e) {
            // Si la fecha está en formato incorrecto o es inválida, ponemos 0
            lblEdad.setText("0");
        }
    }

    /**
     * Método para registrar el jugador.
     * Incluye validaciones de la fecha de nacimiento y de la altura.
     */
    private void registrarJugador() {
        // 1. Validación básica del nombre
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Ingrese el nombre",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // 2. Validación y creación del jugador
        Jugador jugador = null;
        LocalDate fechaNac;
        try {
            fechaNac = LocalDate.parse(
                txtFechaNac.getText().trim(),
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
            );
        } catch (DateTimeException e) {
            // Error en el parseo de la fecha: formato incorrecto o fecha inválida
            JOptionPane.showMessageDialog(
                this,
                "Fecha de nacimiento inválida o en formato incorrecto (use dd/MM/yyyy).",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Validar que la fecha de nacimiento no sea futura
        if (fechaNac.isAfter(LocalDate.now())) {
            JOptionPane.showMessageDialog(
                this,
                "La fecha de nacimiento no puede ser una fecha futura.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Validar edad resultante > 0 (evita que se registre alguien de 0 años o negativo)
        int edadCalculada = Period.between(fechaNac, LocalDate.now()).getYears();
        if (edadCalculada <= 0) {
            JOptionPane.showMessageDialog(
                this,
                "La edad calculada debe ser mayor que 0. Verifique la fecha de nacimiento.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Actualizamos la etiqueta de edad para que coincida con la validación
        lblEdad.setText(String.valueOf(edadCalculada));

        // Conversión correcta de la altura a float
        float altura = ((Number) spinnerAltura.getValue()).floatValue();

        // Construimos el objeto Jugador
        try {
            if (cbTipo.getSelectedItem().equals("Bateador")) {
                jugador = new Bateador(
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
                jugador = new Pitcher(
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
            JOptionPane.showMessageDialog(
                this,
                "Error en los datos: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // 3. Registrar en SerieNacional
        try {
            if (serie == null) {
                JOptionPane.showMessageDialog(
                    this,
                    "Error: Serie Nacional no disponible",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Obtener el equipo seleccionado del combobox
            Equipo equipoSeleccionado = (Equipo) cbEquipo.getSelectedItem();
            if (equipoSeleccionado == null) {
                JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un equipo válido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Agregar el jugador al equipo seleccionado
            equipoSeleccionado.getJugadores().add(jugador);

            // 4. Cierra el diálogo
            SwingUtilities.invokeLater(this::dispose);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "Error al guardar: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
