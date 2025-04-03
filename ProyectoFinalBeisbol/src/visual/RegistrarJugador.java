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

        // =================== NOMBRE ===================
        panel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panel.add(txtNombre);

        // =================== FECHA NACIMIENTO ===================
        panel.add(new JLabel("Fecha Nacimiento (dd/MM/yyyy):"));
        txtFechaNac = new JFormattedTextField();
        DateFormatter dateFormatter = new DateFormatter(new SimpleDateFormat("dd/MM/yyyy"));
        txtFechaNac.setFormatterFactory(new DefaultFormatterFactory(dateFormatter));
        // Fecha por defecto hoy
        String fechaHoy = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        txtFechaNac.setText(fechaHoy);
        // Listener para recalcular la edad
        txtFechaNac.addPropertyChangeListener("value", e -> calcularEdad());
        panel.add(txtFechaNac);

        // =================== EDAD ===================
        panel.add(new JLabel("Edad:"));
        lblEdad = new JLabel("0");
        panel.add(lblEdad);

        // =================== TIPO (BATEADOR / LANZADOR) ===================
        panel.add(new JLabel("Tipo:"));
        cbTipo = new JComboBox<>(new String[]{"Bateador", "Lanzador"});
        panel.add(cbTipo);

        // =================== NACIONALIDAD ===================
        panel.add(new JLabel("Nacionalidad:"));
        txtNacionalidad = new JTextField();
        panel.add(txtNacionalidad);

        // =================== MANO DOMINANTE ===================
        panel.add(new JLabel("Mano Dominante:"));
        cbMano = new JComboBox<>(new String[]{"Derecha", "Izquierda"});
        panel.add(cbMano);

        // =================== ALTURA ===================
        panel.add(new JLabel("Altura (m):"));
        spinnerAltura = new JSpinner(new SpinnerNumberModel(1.70, 1.50, 2.20, 0.01));
        panel.add(spinnerAltura);

        // =================== POSICIÓN/ROL ===================
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

        // =================== EQUIPO AL QUE PERTENECE ===================
        panel.add(new JLabel("Equipo al que pertenece:"));
        cbEquipo = new JComboBox<>();
        // Cargar equipos de la serie:
        cbEquipo.removeAllItems();
        for (Equipo eq : serie.getListEquipos()) {
            cbEquipo.addItem(eq);
        }
        panel.add(cbEquipo);

        // =================== BOTONES ===================
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

    private void calcularEdad() {
        try {
            String textoFecha = txtFechaNac.getText().trim();
            LocalDate fechaNac = LocalDate.parse(textoFecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            int edad = Period.between(fechaNac, LocalDate.now()).getYears();
            lblEdad.setText(String.valueOf(edad));
        } catch (Exception e) {
            lblEdad.setText("0");
        }
    }

    private void registrarJugador() {
        // 1) Validar si hay equipos
        if (cbEquipo.getItemCount() == 0) {
            JOptionPane.showMessageDialog(
                this,
                "No hay equipos registrados. Registre un equipo primero.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // 2) Obtener datos
        String nombre = txtNombre.getText().trim();
        String nacionalidad = txtNacionalidad.getText().trim();
        String manoDominante = (String) cbMano.getSelectedItem();
        float altura = (float) spinnerAltura.getValue();
        String posicion = (String) cbPosicion.getSelectedItem();
        String tipoSeleccionado = (String) cbTipo.getSelectedItem();

        // 3) Validaciones
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo 'Nombre' está vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (nacionalidad.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo 'Nacionalidad' está vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (posicion == null || posicion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una posición.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 4) Parsear fecha de nacimiento
        LocalDate fechaNac;
        try {
            String fechaTexto = txtFechaNac.getText().trim();
            fechaNac = LocalDate.parse(fechaTexto, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Fecha de nacimiento inválida. Use dd/MM/yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 5) Crear jugador
        String edad = lblEdad.getText(); // Se maneja como String en la clase Jugador
        Jugador nuevoJugador;
        if ("Bateador".equals(tipoSeleccionado)) {
            nuevoJugador = new Bateador(
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
            nuevoJugador = new Pitcher(
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

        // 6) Agregar a la lista de jugadores de la serie
        serie.agregarJugador(nuevoJugador);

        // 7) Asociar al equipo seleccionado
        Equipo equipoSeleccionado = (Equipo) cbEquipo.getSelectedItem();
        if (equipoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "No se ha seleccionado un equipo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        equipoSeleccionado.agregarJugador(nuevoJugador); // Uso del método que retorna boolean

        // 8) Mensaje de éxito
        JOptionPane.showMessageDialog(
            this,
            "¡El jugador ha sido registrado exitosamente!",
            "Registro Exitoso",
            JOptionPane.INFORMATION_MESSAGE
        );

        // Cierra el diálogo
        dispose();
    }
}

