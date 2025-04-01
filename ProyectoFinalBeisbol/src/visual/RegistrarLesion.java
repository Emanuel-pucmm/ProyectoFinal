package visual;

import javax.swing.*;
import java.awt.*;
import java.time.*;
import logico.*;

public class RegistrarLesion extends JDialog {
    private JComboBox<Jugador> cbJugador;
    private JTextField txtTipo;
    private JSpinner spinnerDias;
    private SerieNacional serie;

    public RegistrarLesion(SerieNacional serie) {
        this.serie = serie;
        setTitle("Registrar Lesión");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setModal(true);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Jugador:"));
        cbJugador = new JComboBox<>();
        for (Equipo equipo : serie.getListEquipos()) {
            for (Jugador jugador : equipo.getJugadores()) {
                cbJugador.addItem(jugador);
            }
        }
        panel.add(cbJugador);

        panel.add(new JLabel("Tipo de Lesión:"));
        txtTipo = new JTextField();
        panel.add(txtTipo);

        panel.add(new JLabel("Días de Recuperación:"));
        spinnerDias = new JSpinner(new SpinnerNumberModel(15, 1, 365, 1));
        panel.add(spinnerDias);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> {
            Jugador jugador = (Jugador) cbJugador.getSelectedItem();
            Lesion lesion = new Lesion(
                txtTipo.getText(),
                LocalDate.now(),
                LocalDate.now().plusDays((int) spinnerDias.getValue()),
                (int) spinnerDias.getValue()
            );
            jugador.registrarLesion(lesion);
            JOptionPane.showMessageDialog(this, "Lesión registrada exitosamente!");
            dispose();
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