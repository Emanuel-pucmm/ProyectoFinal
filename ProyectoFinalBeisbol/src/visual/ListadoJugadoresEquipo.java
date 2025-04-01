package visual;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import logico.*;

public class ListadoJugadoresEquipo extends JDialog {
    public ListadoJugadoresEquipo(SerieNacional serie, String nombreEquipo) {
        setTitle("Jugadores de " + nombreEquipo);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setModal(true);

        // Buscar equipo
        Equipo equipo = serie.getListEquipos().stream()
            .filter(e -> e.getNombre().equals(nombreEquipo))
            .findFirst().orElse(null);

        if (equipo == null) {
            JOptionPane.showMessageDialog(this, "Equipo no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        // Modelo de tabla
        DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Nombre", "Tipo", "Posición", "Edad", "Lesiones Activas"}, 0
        );

        // Llenar datos
        for (Jugador jugador : equipo.getJugadores()) {
            long lesionesActivas = jugador.getListaLesiones().stream()
                .filter(Lesion::estaActiva).count();

            model.addRow(new Object[]{
                jugador.getNombre(),
                jugador instanceof Bateador ? "Bateador" : "Lanzador",
                jugador instanceof Bateador ? 
                    ((Bateador)jugador).getPosicion() : 
                    ((Pitcher)jugador).getTipoLanzador(),
                jugador.getEdad(),
                lesionesActivas
            });
        }

        // Tabla
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Botón
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        // Diseño
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(btnCerrar, BorderLayout.SOUTH);
    }
}