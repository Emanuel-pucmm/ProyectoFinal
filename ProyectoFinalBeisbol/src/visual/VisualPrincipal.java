package visual;

import javax.swing.*;

import logical.Persistencia;

import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import logico.SerieNacional;
import logico.Equipo;

public class VisualPrincipal extends JFrame {
   private SerieNacional serie;
   private static Socket sfd = null;
   private static DataOutputStream SalidaSocket = null;
   private JMenu mnJugadores;
   private JMenu mnLesiones;
   private JMenu mnEquipos;
   private JMenu mnRespaldo;

   public VisualPrincipal() {
      serie = SerieNacional.getInstance();
      initComponents();
   }

   private void initComponents() {
      setTitle("Sistema de Gestión de Béisbol");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setExtendedState(JFrame.MAXIMIZED_BOTH);
      getContentPane().setLayout(new BorderLayout());

      configurarMenuBar();
      setVisible(true);
      configurarBanner();
      setSystemLookAndFeel();
   }

   private void configurarMenuBar() {
      Persistencia.cargarDatos();

      JMenuBar menuBar = new JMenuBar();

      // Menú Jugadores
      mnJugadores = new JMenu("Jugadores");
      addMenuItem(mnJugadores, "Registrar Jugador", new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            new RegistrarJugador(serie).setVisible(true);
         }
      });
      addMenuItem(mnJugadores, "Listar Jugadores", new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            new ListadoJugadores(serie).setVisible(true);
         }
      });
      menuBar.add(mnJugadores);

      // Menú Lesiones
      mnLesiones = new JMenu("Lesiones");
      addMenuItem(mnLesiones, "Registrar Lesión", new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            abrirForzadoRegistrarLesion();
         }
      });
      addMenuItem(mnLesiones, "Listar Lesiones", new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            verificarAntesDeListarLesiones();
         }
      });
      menuBar.add(mnLesiones);

      // Menú Equipos
      mnEquipos = new JMenu("Equipos");
      addMenuItem(mnEquipos, "Registrar Equipo", new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            new RegistrarEquipo(serie).setVisible(true);
         }
      });
      addMenuItem(mnEquipos, "Listar Equipos", new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            new ListadoEquipos(serie).setVisible(true);
         }
      });
      menuBar.add(mnEquipos);

      // Menú Partidos
      JMenu mnPartidos = new JMenu("Partidos");
      addMenuItem(mnPartidos, "Simular Todos los Partidos", new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            simularTemporadaCompleta();
         }
      });
      addMenuItem(mnPartidos, "Simular Partido Manual", new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            verificarAntesDeSimularManual();
         }
      });
      addMenuItem(mnPartidos, "Tabla de Posiciones", new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            verificarAntesDeMostrarTabla();
         }
      });
      menuBar.add(mnPartidos);

      // Menú Respaldo
      mnRespaldo = new JMenu("Respaldo");
      JMenuItem mntmRespaldo = new JMenuItem("Generar Respaldo");
      mntmRespaldo.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            generarRespaldo();
         }
      });
      mnRespaldo.add(mntmRespaldo);
      menuBar.add(mnRespaldo);

      if (SerieNacional.getLoginUser() != null) {
         String tipoUsuario = SerieNacional.getLoginUser().getTipo();
         if (!tipoUsuario.equalsIgnoreCase("Administrador")) {
            mnJugadores.setVisible(false);
            mnLesiones.setVisible(false);
            mnEquipos.setVisible(false);
            mnRespaldo.setVisible(false);
         }
      }

      setJMenuBar(menuBar);
   }

   private void configurarBanner() {
      try {
         ImageIcon originalIcon = new ImageIcon(getClass().getResource("/bannerBaseball.jpg"));
         int ancho = getWidth();
         int alto = getHeight();

         Image imagenEscalada = originalIcon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
         JLabel lblBanner = new JLabel(new ImageIcon(imagenEscalada));
         lblBanner.setHorizontalAlignment(SwingConstants.CENTER);

         getContentPane().removeAll();
         getContentPane().add(lblBanner, BorderLayout.CENTER);
         revalidate();
         repaint();

         for (ComponentListener cl : getComponentListeners()) {
            if (cl instanceof ComponentAdapter) return;
         }

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
      new Thread(new Runnable() {
         public void run() {
            try {
               sfd = new Socket("localhost", 7000);
               DataInputStream aux = new DataInputStream(new FileInputStream("beisbol.dat"));
               SalidaSocket = new DataOutputStream(sfd.getOutputStream());

               int unByte;
               while ((unByte = aux.read()) != -1) {
                  SalidaSocket.write(unByte);
                  SalidaSocket.flush();
               }

               aux.close();
               JOptionPane.showMessageDialog(null, "Respaldo generado exitosamente", "Respaldo", JOptionPane.INFORMATION_MESSAGE);
            } catch (UnknownHostException uhe) {
               JOptionPane.showMessageDialog(null, "Servidor no encontrado: " + uhe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ioe) {
               JOptionPane.showMessageDialog(null, "Error de comunicación: " + ioe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
               try {
                  if (SalidaSocket != null) SalidaSocket.close();
                  if (sfd != null) sfd.close();
               } catch (IOException e) {
                  System.err.println("Error al cerrar conexión: " + e.getMessage());
               }
            }
         }
      }).start();
   }

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
