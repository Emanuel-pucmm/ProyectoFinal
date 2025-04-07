package visual;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import logico.SerieNacional;
import logico.Equipo;
import excepcion.*;
public class RegistrarEquipo extends JDialog {
    private JTextField txtNombre, txtEstadio;
    private SerieNacional serie;

    public RegistrarEquipo(SerieNacional serie) {
        this.serie = serie;
        setTitle("Registrar Equipo");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setModal(true);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Nombre del Equipo:"));
        txtNombre = new JTextField();
        panel.add(txtNombre);

        panel.add(new JLabel("Estadio:"));
        txtEstadio = new JTextField();
        panel.add(txtEstadio);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> {
        	try {
                String nombre = txtNombre.getText().trim();
                String estadio = txtEstadio.getText().trim();
                
                if(nombre.isEmpty()) throw new CampoVacioExcepcion("nombre del equipo");
                if(estadio.isEmpty()) throw new CampoVacioExcepcion("estadio");
                
                // Validar nombre sin números
                if(nombre.matches(".*\\d.*")) {
                    throw new NombreInvalidoExcepcion();
                }

            Equipo equipo = new Equipo(
                txtNombre.getText().trim(),
                txtEstadio.getText().trim(),
                false,
                0, 0,
                new ArrayList<>(),
                new ArrayList<>()
            );

            serie.agregarEquipo(equipo);
            JOptionPane.showMessageDialog(this, "Equipo registrado exitosamente!");
            dispose();

        	} catch (CampoVacioExcepcion | NombreInvalidoExcepcion ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnCancelar);

        add(panel, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }
}

