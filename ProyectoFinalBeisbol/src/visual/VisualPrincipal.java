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
        // Única instancia de la Serie
        serie = new SerieNacional();
        initComponents();
    }

    private void initComponents() {
        setTitle("Sistema de Gestión de Béisbol - Con Imagen SerieNacional (JPG)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Maximiza la ventana al inicio (opcional)
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Menú
        configurarMenuBar();

        // Banner (cargamos "SerieNacional.jpg")
        configurarBanner();

        // Look & Feel del sistema
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
        // Forzamos abrir la ventana RegistrarLesion sin validar equipos
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
     * Carga la imagen "SerieNacional.jpg" y la muestra como banner en la parte superior (NORTH).
     */
    private void configurarBanner() {
        try {
            // Ajusta la ruta dependiendo de dónde esté tu archivo:
            //  - Si tu imagen está en src/main/resources (Maven) o en una carpeta "resources" configurada, 
            //    asegúrate de que la ruta sea la misma en getResource:
            //    getClass().getResource("/SerieNacional.jpg")
            //  - Si la IDE copia la carpeta "resources" al classpath, quizá debas usar "/resources/SerieNacional.jpg".
            
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/SerieNacional.jpg"));

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int ancho = screenSize.width;
            int alto = (int) (ancho * 0.2); // 20% de la anchura

            // Escalamos la imagen
            Image imagenEscalada = originalIcon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);

            // Creamos un JLabel con la imagen ya escalada
            JLabel lblBanner = new JLabel(new ImageIcon(imagenEscalada));
            lblBanner.setHorizontalAlignment(SwingConstants.CENTER);

            // Lo añadimos arriba (NORTH) de la ventana
            add(lblBanner, BorderLayout.NORTH);

        } catch (Exception e) {
            // Si no se encuentra la imagen o hay error, mostramos un mensaje de error en su lugar
            JLabel lblError = new JLabel("Banner no disponible - " + e.getMessage());
            lblError.setFont(new Font("Arial", Font.BOLD, 20));
            lblError.setForeground(Color.RED);
            lblError.setHorizontalAlignment(SwingConstants.CENTER);
            add(lblError, BorderLayout.NORTH);

            System.err.println("Error cargando banner: " + e.getMessage());
        }
    }

    /**
     * Abre la ventana de RegistrarLesion sin chequear si la serie está vacía.
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

    /**
     * Verifica si hay equipos antes de listar lesiones.
     */
    private void verificarAntesDeListarLesiones() {
        if (serie.getListEquipos().isEmpty()) {
            showError("No hay equipos registrados.");
            return;
        }
        new ListadoLesiones(serie).setVisible(true);
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

