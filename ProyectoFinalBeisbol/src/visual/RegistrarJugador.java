package visual;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import logico.*;
import excepcion.*;

public class RegistrarJugador extends JDialog {
    private JTextField txtNombre, txtNacionalidad;
    private JComboBox<String> cbTipo, cbMano, cbPosicion;
    private JComboBox<Equipo> cbEquipo;
    private JSpinner spinnerAltura;
    private JTextField txtFechaNac;
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
        txtFechaNac.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
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
            lblEdad.setText("0");
        }
    }
    
    private void validarNombre(String nombre) throws NombreInvalidoExcepcion, CampoVacioExcepcion {
        if(nombre.isEmpty()) {
            throw new CampoVacioExcepcion("nombre");
        }
        if(nombre.matches(".*\\d.*") || !nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            throw new NombreInvalidoExcepcion();
        }
    }

    private void registrarJugador() {
        try {
            // Validaciones
            validarNombre(txtNombre.getText().trim());
            
            if(txtNacionalidad.getText().trim().isEmpty()) {
                throw new CampoVacioExcepcion("nacionalidad");
            }

            // Validar fecha
            LocalDate fechaNac;
            try {
                fechaNac = LocalDate.parse(txtFechaNac.getText().trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                if(fechaNac.isAfter(LocalDate.now())) {
                    throw new FechaInvalidaExcepcion("La fecha no puede ser futura");
                }
            } catch(Exception e) {
                throw new FechaInvalidaExcepcion();
            }

            // Validar edad
            int edad = Period.between(fechaNac, LocalDate.now()).getYears();
            if(edad <= 0) {
                throw new NumeroNegativoExcepcion("edad");
            }

            // Validar altura
            float altura = ((Number) spinnerAltura.getValue()).floatValue();
            if(altura <= 0) {
                throw new NumeroNegativoExcepcion("altura");
            }

            // Crear jugador
            Jugador nuevoJugador = null;
            String tipoSeleccionado = (String) cbTipo.getSelectedItem();
            
            if ("Bateador".equals(tipoSeleccionado)) {
                nuevoJugador = new Bateador(
                    txtNombre.getText().trim(),
                    fechaNac,
                    String.valueOf(edad),
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
                    String.valueOf(edad),
                    txtNacionalidad.getText().trim(),
                    cbMano.getSelectedItem().toString(),
                    LocalDate.now(),
                    altura,
                    cbPosicion.getSelectedItem().toString()
                );
            }

            // Validar equipo seleccionado
            Equipo equipoSeleccionado = (Equipo) cbEquipo.getSelectedItem();
            if (equipoSeleccionado == null) {
                throw new EquipoNoSeleccionadoExcepcion();
            }

            // Registrar jugador
            serie.getListJugadores().add(nuevoJugador);
            equipoSeleccionado.getJugadores().add(nuevoJugador);

            JOptionPane.showMessageDialog(this, "Jugador registrado exitosamente!");
            dispose();

        } catch (NombreInvalidoExcepcion | CampoVacioExcepcion | 
                 FechaInvalidaExcepcion | NumeroNegativoExcepcion |
                 EquipoNoSeleccionadoExcepcion e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error inesperado: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}