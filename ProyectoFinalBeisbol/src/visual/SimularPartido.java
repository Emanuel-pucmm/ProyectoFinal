package visual;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import logico.*;

public class SimularPartido extends JDialog {
    private JComboBox<Equipo> cbLocal, cbVisita;
    private SerieNacional serie;

    public SimularPartido(SerieNacional serie) {
        this.serie = serie;
        setTitle("Simular Partido");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setModal(true);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Equipo Local:"));
        cbLocal = new JComboBox<>();
        for (Equipo equipo : serie.getListEquipos()) {
            cbLocal.addItem(equipo);
        }
        panel.add(cbLocal);

        panel.add(new JLabel("Equipo Visitante:"));
        cbVisita = new JComboBox<>();
        for (Equipo equipo : serie.getListEquipos()) {
            cbVisita.addItem(equipo);
        }
        panel.add(cbVisita);

        JButton btnSimular = new JButton("Simular Partido");
        btnSimular.addActionListener(e -> {
            Equipo local = (Equipo) cbLocal.getSelectedItem();
            Equipo visita = (Equipo) cbVisita.getSelectedItem();

            if (local.equals(visita)) {
                JOptionPane.showMessageDialog(this, "Seleccione equipos diferentes", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            new EstadisticasPartido(serie, local, visita).setVisible(true);
            dispose();
        });

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnSimular);
        panelBotones.add(btnCancelar);

        add(panel, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }
}