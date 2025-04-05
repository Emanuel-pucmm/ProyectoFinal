package visual;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import logico.SerieNacional;
import logico.Equipo;

public class VisualPrincipal extends JFrame {
    private SerieNacional serie;

    public VisualPrincipal() {
        // ÚNICA instancia de la Serie (no crees otra en tu main)
        serie = new SerieNacional();
        initComponents();
    }

    private void initComponents() {
        setTitle("Sistema de Gestión de Béisbol - [Debug Version]");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Menú
        configurarMenuBar();

        // Banner (opcional, puedes omitir si no tienes la imagen)
        configurarBanner();

        // Look & Feel
        setSystemLookAndFeel();
    }

    private void configurarMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Menú Jugadores
        JMenu mnJugadores = new JMenu("Jugadores");
        addMenuItem(mnJugadores, "Registrar Jugador", e -> new RegistrarJugador(serie).setVisible(true));
        addMenuItem(mnJugadores, "Listar Jugadores", e -> new ListadoJugadores(serie).setVisible(true));
        menuBar.add(mnJugadores);

        // Menú Lesiones
        JMenu mnLesiones = new JMenu("Lesiones");
        // Fuerza abrir la ventana “Registrar Lesión” sin importar si hay o no equipos
        addMenuItem(mnLesiones, "Registrar Lesión", e -> abrirForzadoRegistrarLesion());
        addMenuItem(mnLesiones, "Listar Lesiones", e -> verificarAntesDeListarLesiones());
        menuBar.add(mnLesiones);

        // Menú Equipos
        JMenu mnEquipos = new JMenu("Equipos");
        addMenuItem(mnEquipos, "Registrar Equipo", e -> new RegistrarEquipo(serie).setVisible(true));
        addMenuItem(mnEquipos, "Listar Equipos", e -> new ListadoEquipos(serie).setVisible(true));
        menuBar.add(mnEquipos);

        // Menú Partidos
        JMenu mnPartidos = new JMenu("Partidos");
        addMenuItem(mnPartidos, "Simular Todos los Partidos", e -> simularTemporadaCompleta());
        addMenuItem(mnPartidos, "Simular Partido Manual", e -> verificarAntesDeSimularManual());
        addMenuItem(mnPartidos, "Tabla de Posiciones", e -> verificarAntesDeMostrarTabla());
        menuBar.add(mnPartidos);

        setJMenuBar(menuBar);
    }

    /**
     * Versión que abre la ventana "RegistrarLesion" sin chequear si la serie está vacía,
     * para descartar que el problema sea el if (serie.getListEquipos().isEmpty()).
     */
    private void abrirForzadoRegistrarLesion() {
        System.out.println("=== [DEBUG] abrirForzadoRegistrarLesion() ===");
        System.out.println("Cantidad de equipos en la serie: " + serie.getListEquipos().size());
        for (Equipo eq : serie.getListEquipos()) {
            System.out.println("  -> " + eq.getNombre() + ", Jugadores: " + eq.getJugadores().size());
        }

        try {
            new RegistrarLesion(serie).setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            showError("Ocurrió una excepción al abrir la ventana de lesiones: " + ex.getMessage());
        }
    }

    private void verificarAntesDeListarLesiones() {
        // Aquí sí chequeamos si la serie está vacía para listar
        if (serie.getListEquipos().isEmpty()) {
            showError("No hay equipos registrados.");
            return;
        }
        new ListadoLesiones(serie).setVisible(true);
    }

    private void configurarBanner() {
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/resources/bannerBaseball.jpg"));
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int ancho = screenSize.width;
            int alto = (int) (ancho * 0.2);
            Image imagenEscalada = originalIcon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            JLabel lblBanner = new JLabel(new ImageIcon(imagenEscalada));
            lblBanner.setHorizontalAlignment(SwingConstants.CENTER);
            add(lblBanner, BorderLayout.NORTH);
        } catch (Exception e) {
            JLabel lblError = new JLabel("Banner no disponible - " + e.getMessage());
            lblError.setFont(new Font("Arial", Font.BOLD, 20));
            lblError.setForeground(Color.RED);
            lblError.setHorizontalAlignment(SwingConstants.CENTER);
            add(lblError, BorderLayout.NORTH);
            System.err.println("Error cargando banner: " + e.getMessage());
        }
    }

    private void addMenuItem(JMenu menu, String texto, ActionListener listener) {
        JMenuItem item = new JMenuItem(texto);
        item.addActionListener(listener);
        menu.add(item);
    }

    private void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Error al configurar Look and Feel: " + e.getMessage());
        }
    }

    private void showError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // ==================== SIMULACIÓN DE TEMPORADA ====================

    private void simularTemporadaCompleta() {
        if (serie.getListEquipos().size() < 2) {
            showError("Se necesitan al menos 2 equipos");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Simular todos los partidos de la temporada?\nCada equipo jugará como local y visitante.",
            "Confirmar",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            List<Equipo> equipos = serie.getListEquipos();
            for (int i = 0; i < equipos.size(); i++) {
                for (int j = 0; j < equipos.size(); j++) {
                    if (i != j) {
                        simularPartidoAutomatico(equipos.get(i), equipos.get(j));
                    }
                }
            }
            JOptionPane.showMessageDialog(this, "Temporada simulada exitosamente!");
        }
    }

    private void simularPartidoAutomatico(Equipo local, Equipo visita) {
        EstadisticasPartido partido = new EstadisticasPartido(serie, local, visita, true);

        // Simular 9 innings
        for (int i = 0; i < 9; i++) {
            partido.simularInning();
        }
        partido.finalizarPartido();
    }

    private void verificarAntesDeSimularManual() {
        if (serie.getListEquipos().size() < 2) {
            showError("Se necesitan al menos 2 equipos");
            return;
        }
        new SimularPartido(serie).setVisible(true);
    }

    private void verificarAntesDeMostrarTabla() {
        if (serie.getListEquipos().isEmpty()) {
            showError("No hay equipos registrados");
            return;
        }
        new TablaPosiciones(serie).setVisible(true);
    }

    // ==================== MAIN ====================
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            VisualPrincipal frame = new VisualPrincipal();
            frame.setVisible(true);
        });
    }
}
