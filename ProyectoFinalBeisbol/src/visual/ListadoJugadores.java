package visual;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import logico.*;

public class ListadoJugadores extends JDialog {
    public ListadoJugadores(SerieNacional serie) {
        setTitle("Listado de Jugadores");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setModal(true);

        String[] columnas = {"Nombre", "Tipo", "Equipo", "Posición", "Edad", "Nacionalidad"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        for (Equipo equipo : serie.getListEquipos()) {
            for (Jugador jugador : equipo.getJugadores()) {
                String tipo = jugador instanceof Bateador ? "Bateador" : "Lanzador";
                String posicion = jugador instanceof Bateador ? 
                    ((Bateador)jugador).getPosicion() : 
                    ((Pitcher)jugador).getTipoLanzador();

                model.addRow(new Object[]{
                    jugador.getNombre(),
                    tipo,
                    equipo.getNombre(),
                    posicion,
                    jugador.getEdad(),
                    jugador.getNacionalidad()
                });
            }
        }

        JTable tabla = new JTable(model);
        tabla.setAutoCreateRowSorter(true);
        JScrollPane scroll = new JScrollPane(tabla);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(btnCerrar, BorderLayout.SOUTH);

        add(panel);
    }
}