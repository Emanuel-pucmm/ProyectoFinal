package visual;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Comparator;
import logico.*;

public class ListadoEquipos extends JDialog {
    public ListadoEquipos(SerieNacional serie) {
        setTitle("Listado de Equipos");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setModal(true);

        // Modelo de tabla
        DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Equipo", "Estadio", "Juegos Ganados", "Juegos Perdidos", "% Victorias", "Jugadores"}, 0
        );

        // Ordenar por porcentaje de victorias (descendente)
        serie.getListEquipos().sort(Comparator.comparingDouble(Equipo::calcPorcentajeVictorias).reversed());

        // Llenar datos
        for (Equipo equipo : serie.getListEquipos()) {
            model.addRow(new Object[]{
                equipo.getNombre(),
                equipo.getEstadio(),
                equipo.getJuegosGan(),
                equipo.getJuegosPer(),
                String.format("%.1f%%", equipo.calcPorcentajeVictorias()),
                equipo.getJugadores().size()
            });
        }

        // Tabla con configuración
        JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(table);

        // Botones
        JButton btnVerDetalles = new JButton("Ver Jugadores");
        btnVerDetalles.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String nombreEquipo = (String) model.getValueAt(selectedRow, 0);
                new ListadoJugadoresEquipo(serie, nombreEquipo).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un equipo", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnVerDetalles);
        panelBotones.add(btnCerrar);

        // Diseño
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }
}