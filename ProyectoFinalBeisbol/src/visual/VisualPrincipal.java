package visual;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import logico.*;

public class VisualPrincipal extends JFrame {
    private SerieNacional serie;

    public VisualPrincipal() {
        serie = new SerieNacional(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        setTitle("Sistema de Gestión de Béisbol");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JMenuBar menuBar = new JMenuBar();

        // Menú Jugadores
        JMenu mnJugadores = new JMenu("Jugadores");
        JMenuItem mntmRegistrarJugador = new JMenuItem("Registrar Jugador");
        mntmRegistrarJugador.addActionListener(e -> new RegistrarJugador(serie).setVisible(true));
        mnJugadores.add(mntmRegistrarJugador);

        JMenuItem mntmListarJugadores = new JMenuItem("Listar Jugadores");
        mntmListarJugadores.addActionListener(e -> new ListadoJugadores(serie).setVisible(true));
        mnJugadores.add(mntmListarJugadores);

        JMenuItem mntmRegistrarLesion = new JMenuItem("Registrar Lesión");
        mntmRegistrarLesion.addActionListener(e -> new RegistrarLesion(serie).setVisible(true));
        mnJugadores.add(mntmRegistrarLesion);

        JMenuItem mntmListarLesiones = new JMenuItem("Listar Lesiones");
        mntmListarLesiones.addActionListener(e -> new ListadoLesiones(serie).setVisible(true));
        mnJugadores.add(mntmListarLesiones);
        menuBar.add(mnJugadores);

        // Menú Equipos
        JMenu mnEquipos = new JMenu("Equipos");
        JMenuItem mntmRegistrarEquipo = new JMenuItem("Registrar Equipo");
        mntmRegistrarEquipo.addActionListener(e -> new RegistrarEquipo(serie).setVisible(true));
        mnEquipos.add(mntmRegistrarEquipo);

        JMenuItem mntmListarEquipos = new JMenuItem("Listar Equipos");
        mntmListarEquipos.addActionListener(e -> new ListadoEquipos(serie).setVisible(true));
        mnEquipos.add(mntmListarEquipos);
        menuBar.add(mnEquipos);

        // Menú Partidos
        JMenu mnPartidos = new JMenu("Partidos");
        JMenuItem mntmSimularPartido = new JMenuItem("Simular Partido");
        mntmSimularPartido.addActionListener(e -> new SimularPartido(serie).setVisible(true));
        mnPartidos.add(mntmSimularPartido);

        JMenuItem mntmTablaPosiciones = new JMenuItem("Tabla de Posiciones");
        mntmTablaPosiciones.addActionListener(e -> new TablaPosiciones(serie).setVisible(true));
        mnPartidos.add(mntmTablaPosiciones);
        menuBar.add(mnPartidos);

        setJMenuBar(menuBar);

        JLabel lblBanner = new JLabel(new ImageIcon("banner_beisbol.jpg"));
        lblBanner.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblBanner, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new VisualPrincipal().setVisible(true));
    }
}