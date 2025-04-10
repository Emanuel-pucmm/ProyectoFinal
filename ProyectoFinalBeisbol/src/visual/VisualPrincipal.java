package visual;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.ResourceBundle.Control;

import logico.SerieNacional;
import logico.Equipo;

public class VisualPrincipal extends JFrame {
    private SerieNacional serie;
    private static Socket sfd = null;
    private static DataOutputStream SalidaSocket = null;


    public VisualPrincipal() {
        // Única instancia de la Serie
        serie = SerieNacional.getInstance();
        initComponents();
      
    }

    private void initComponents() {
        setTitle("Sistema de Gestión de Béisbol");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Maximiza la ventana al inicio (opcional)
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // Menú
        configurarMenuBar();
        
        setVisible(true);

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
        
        JMenu mnRespaldo = new JMenu("Respaldo");
        JMenuItem mntmRespaldo = new JMenuItem("Generar Respaldo");
        mntmRespaldo.addActionListener(e -> generarRespaldo());
        mnRespaldo.add(mntmRespaldo);
        menuBar.add(mnRespaldo);
        
        setJMenuBar(menuBar);
      

        setJMenuBar(menuBar);
        if (SerieNacional.getLoginUser() != null) {
            String tipoUsuario = SerieNacional.getLoginUser().getTipo();
            
            // Si el usuario es Anotador, deshabilitamos todos los menús excepto Partidos
            if (!tipoUsuario.equals("Anotador")) {
                // El usuario es Administrador u otro tipo, todos los menús están visibles
                // No hacemos nada, todos los menús ya están habilitados por defecto
            } else {
                // El usuario es Anotador, solo dejamos visible el menú de Partidos
                mnJugadores.setVisible(false);
                mnLesiones.setVisible(false);
                mnEquipos.setVisible(false);
                // El menú Partidos permanece visible
            }
        }
    }

    /**
     * Carga la imagen "SerieNacional.jpg" y la muestra como banner en la parte superior (NORTH).
     */
    private void configurarBanner() {
    	   try {
    	      ImageIcon originalIcon = new ImageIcon(getClass().getResource("/bannerBaseball.jpg"));

    	      int ancho = getWidth();
    	      int alto = getHeight();

    	      Image imagenEscalada = originalIcon.getImage().getScaledInstance(
    	         ancho,
    	         alto,
    	         Image.SCALE_SMOOTH
    	      );

    	      JLabel lblBanner = new JLabel(new ImageIcon(imagenEscalada));
    	      lblBanner.setHorizontalAlignment(SwingConstants.CENTER);

    	      getContentPane().removeAll(); // Borra todo lo anterior
    	      getContentPane().add(lblBanner, BorderLayout.CENTER);

    	      revalidate();
    	      repaint();

    	      // Asegúrate de no añadir múltiples listeners
    	      for (ComponentListener cl : getComponentListeners()) {
    	         if (cl instanceof ComponentAdapter) return;
    	      }

    	      // Redibujar el banner si cambia el tamaño
    	      addComponentListener(new ComponentAdapter() {
    	         @Override
    	         public void componentResized(ComponentEvent e) {
    	            configurarBanner();
    	         }
    	      });

    	   } catch (Exception e) {
    	      JLabel error = new JLabel(" Error: bannerBaseball.jpg no encontrado");
    	      error.setForeground(Color.RED);
    	      getContentPane().removeAll();
    	      getContentPane().add(error, BorderLayout.CENTER);
    	      System.err.println("Error al cargar el banner: " + e.getMessage());
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
    private void generarRespaldo() {
        new Thread(() -> {
            try {
                sfd = new Socket("localhost", 7000); // Conexión automática
                DataInputStream aux = new DataInputStream(new FileInputStream("beisbol.dat"));
                SalidaSocket = new DataOutputStream(sfd.getOutputStream());
                
                int unByte;
                while ((unByte = aux.read()) != -1) {
                    SalidaSocket.write(unByte);
                    SalidaSocket.flush();
                }
                
                aux.close();
                JOptionPane.showMessageDialog(this, 
                    "Respaldo generado exitosamente", 
                    "Respaldo", 
                    JOptionPane.INFORMATION_MESSAGE);
                
            } catch (UnknownHostException uhe) {
                JOptionPane.showMessageDialog(this, 
                    "Servidor no encontrado: " + uhe.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (IOException ioe) {
                JOptionPane.showMessageDialog(this, 
                    "Error de comunicación: " + ioe.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (SalidaSocket != null) SalidaSocket.close();
                    if (sfd != null) sfd.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar conexión: " + e.getMessage());
                }
            }
        }).start();
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

   
}

