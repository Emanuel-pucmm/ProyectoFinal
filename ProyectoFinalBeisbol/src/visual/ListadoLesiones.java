package visual;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import logico.*;

public class ListadoLesiones extends JDialog {
    public ListadoLesiones(SerieNacional serie) {
        setTitle("Listado de Lesiones");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setModal(true);

        // Modelo de tabla
        DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Jugador", "Equipo", "Tipo Lesi�n", "Fecha Inicio", "Fecha Fin", "D�as Restantes"}, 0
        );

        // Llenar datos con verificaciones de null
        try {
            if (serie != null && serie.getListEquipos() != null) {
                for (Equipo equipo : serie.getListEquipos()) {
                    if (equipo != null && equipo.getJugadores() != null) {
                        for (Jugador jugador : equipo.getJugadores()) {
                            if (jugador != null && jugador.getListaLesiones() != null) {
                                for (Lesion lesion : jugador.getListaLesiones()) {
                                    if (lesion != null && lesion.getFechaInicio() != null && lesion.getFechaFin() != null) {
                                        long diasRestantes = lesion.getFechaFin().toEpochDay() - java.time.LocalDate.now().toEpochDay();
                                        model.addRow(new Object[]{
                                            jugador.getNombre(),
                                            equipo.getNombre(),
                                            lesion.getTipo(),
                                            lesion.getFechaInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                                            lesion.getFechaFin().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                                            diasRestantes > 0 ? diasRestantes : "Recuperado"
                                        });
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar lesiones: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }

        // Verificar si hay datos
        if (model.getRowCount() == 0) {
            model.addRow(new Object[]{"No hay lesiones registradas", "", "", "", "", ""});
        }

        // Tabla con scroll
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Bot�n de cierre
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        // Dise�o
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(btnCerrar, BorderLayout.SOUTH);
    }
}