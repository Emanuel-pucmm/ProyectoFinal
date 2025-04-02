package visual;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import logico.*;

public class RegistrarJugador extends JDialog {
    private JTextField txtNombre, txtNacionalidad;
    private JComboBox<String> cbTipo, cbMano, cbPosicion;
    private JComboBox<Equipo> cbEquipo;        // Nuevo combo para equipo
    private JSpinner spinnerAltura;
    private JFormattedTextField txtFechaNac;
    private JLabel lblEdad;
    private SerieNacional serie;

    // Posibles posiciones
    private static final String[] POSICIONES_BATEADOR = {
        "Catcher",
        "Primera Base",
        "Segunda Base",
        "Tercera Base",
        "Shortstop",
        "Jardinero Izquierdo",
        "Jardinero Central",
        "Jardinero Derecho"
    };

    private static final String[] POSICIONES_PITCHER = {
        "Abridor",
        "Relevista",
        "Cerrador"
    };

    public RegistrarJugador(SerieNacional serie) {
        this.serie = serie;
        setTitle("Registrar Jugador");
        setSize(450, 450);  // Ajusta altura para el nuevo campo
        setLocationRelativeTo(null);
        setModal(true);

        // Ahora tendremos 10 filas (en lugar de 9)
        JPanel panel = new JPanel(new GridLayout(10, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Nombre
        panel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panel.add(txtNombre);

        // Fecha de nacimiento (dd/MM/yyyy)
        panel.add(new JLabel("Fecha Nacimiento (dd/MM/yyyy):"));
        txtFechaNac = new JFormattedTextField();

        // Formateador para dd/MM/yyyy
        DateFormatter dateFormatter = new DateFormatter(new SimpleDateFormat("dd/MM/yyyy"));
        txtFechaNac.setFormatterFactory(new DefaultFormatterFactory(dateFormatter));

        // Fecha por defecto hoy
        String fechaPorDefecto = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        txtFechaNac.setText(fechaPorDefecto);

        // Listener para calcular la edad
        txtFechaNac.addPropertyChangeListener("value", e -> calcularEdad());
        panel.add(txtFechaNac);

        // Edad
        panel.add(new JLabel("Edad:"));
        lblEdad = new JLabel("0");
        panel.add(lblEdad);

        // Tipo (Bateador / Lanzador)
        panel.add(new JLabel("Tipo:"));
        cbTipo = new JComboBox<>(new String[]{"Bateador", "Lanzador"});
        panel.add(cbTipo);

        // Nacionalidad
        panel.add(new JLabel("Nacionalidad:"));
        txtNacionalidad = new JTextField();
        panel.add(txtNacionalidad);

        // Mano Dominante
        panel.add(new JLabel("Mano Dominante:"));
        cbMano = new JComboBox<>(new String[]{"Derecha", "Izquierda"});
        panel.add(cbMano);

        // Altura
        panel.add(new JLabel("Altura (m):"));
        spinnerAltura = new JSpinner(new SpinnerNumberModel(1.70, 1.50, 2.20, 0.01));
        panel.add(spinnerAltura);

        // Posición/Tipo de lanzador
        panel.add(new JLabel("Posición/Tipo Lanzador:"));
        cbPosicion = new JComboBox<>(POSICIONES_BATEADOR); // por defecto Bateador
        panel.add(cbPosicion);

        // Listener para cambiar el combo según Bateador/Lanzador
        cbTipo.addActionListener(e -> {
            if (cbTipo.getSelectedItem().equals("Bateador")) {
                cbPosicion.setModel(new DefaultComboBoxModel<>(POSICIONES_BATEADOR));
            } else {
                cbPosicion.setModel(new DefaultComboBoxModel<>(POSICIONES_PITCHER));
            }
        });

        // ================== NUEVO CAMPO: Equipo al que pertenece ==================
        panel.add(new JLabel("Equipo al que pertenece:"));
        cbEquipo = new JComboBox<>();
        // Llenamos el combo con los equipos registrados en la serie
        for (Equipo eq : serie.getListEquipos()) {
            cbEquipo.addItem(eq);
        }
        panel.add(cbEquipo);
        // ==========================================================================

        // Botones
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

    // Calcula la edad en base a la fecha de nacimiento
    private void calcularEdad() {
        try {
            String fechaTexto = txtFechaNac.getText().trim();
            LocalDate fechaNac = LocalDate.parse(
                fechaTexto,
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
            );
            int edad = Period.between(fechaNac, LocalDate.now()).getYears();
            lblEdad.setText(String.valueOf(edad));
        } catch (Exception e) {
            lblEdad.setText("0");
        }
    }

    // Registra el jugador
    private void registrarJugador() {
        try {
            // Parsear la fecha con dd/MM/yyyy
            String fechaTexto = txtFechaNac.getText().trim();
            LocalDate fechaNac = LocalDate.parse(
                fechaTexto,
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
            );

            String edad = lblEdad.getText();
            String nombre = txtNombre.getText().trim();
            String nacionalidad = txtNacionalidad.getText().trim();
            String manoDominante = (String) cbMano.getSelectedItem();
            float altura = (float) spinnerAltura.getValue();
            String posicion = (String) cbPosicion.getSelectedItem();

            // Validar campos obligatorios
            if (nombre.isEmpty() || nacionalidad.isEmpty() || posicion.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Complete todos los campos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Creamos el Jugador (Bateador o Pitcher)
            Jugador jugador;
            if (cbTipo.getSelectedItem().equals("Bateador")) {
                jugador = new Bateador(
                    nombre,
                    fechaNac,
                    edad,
                    nacionalidad,
                    manoDominante,
                    LocalDate.now(),
                    altura,
                    posicion
                );
            } else {
                jugador = new Pitcher(
                    nombre,
                    fechaNac,
                    edad,
                    nacionalidad,
                    manoDominante,
                    LocalDate.now(),
                    altura,
                    posicion
                );
            }

            // Lo agregamos a la Serie (como antes)
            serie.agregarJugador(jugador);

            // ========== NUEVO: Asociar el jugador al equipo seleccionado ==========
            Equipo equipoSeleccionado = (Equipo) cbEquipo.getSelectedItem();
            if (equipoSeleccionado != null) {
                // Añadimos el jugador al equipo. 
                // Sustituye "getJugadores()" por tu método real si se llama distinto.
                equipoSeleccionado.getJugadores().add(jugador);
            }
            // ======================================================================

            JOptionPane.showMessageDialog(this, "¡Jugador registrado exitosamente!");
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "Error en los datos ingresados",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
