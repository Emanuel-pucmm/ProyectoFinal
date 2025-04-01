package visual;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import logico.*;

public class TablaPosiciones extends JDialog {
    public TablaPosiciones(SerieNacional serie) {
        setTitle("Tabla de Posiciones");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setModal(true);

        // Ordenar equipos por porcentaje de victorias
        List<Equipo> equiposOrdenados = new ArrayList<>(serie.getListEquipos());
        equiposOrdenados.sort(Comparator.comparingDouble(Equipo::calcPorcentajeVictorias).reversed());

        String[] columnas = {"Posición", "Equipo", "JG", "JP", "% Victorias"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        int posicion = 1;
        for (Equipo equipo : equiposOrdenados) {
            model.addRow(new Object[]{
                posicion++,
                equipo.getNombre(),
                equipo.getJuegosGan(),
                equipo.getJuegosPer(),
                String.format("%.1f", equipo.calcPorcentajeVictorias()) + "%"
            });
        }

        JTable tabla = new JTable(model);
        JScrollPane scroll = new JScrollPane(tabla);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(btnCerrar, BorderLayout.SOUTH);

        add(panel);
    }
}