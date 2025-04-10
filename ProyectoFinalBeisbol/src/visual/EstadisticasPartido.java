package visual;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import logico.*;

public class EstadisticasPartido extends JDialog {
    private Equipo local, visita;
    private SerieNacional serie;
    private DefaultTableModel modelLocal, modelVisita;
    private JLabel lblResultado;
    private int carrerasLocal = 0, carrerasVisita = 0;
    private boolean simulacionAutomatica = false;

    public EstadisticasPartido(SerieNacional serie, Equipo local, Equipo visita) {
        this(serie, local, visita, false);
    }

    public EstadisticasPartido(SerieNacional serie, Equipo local, Equipo visita, boolean simulacionAutomatica) {
        this.serie = serie;
        this.local = local;
        this.visita = visita;
        this.simulacionAutomatica = simulacionAutomatica;
        
        if (!simulacionAutomatica) {
            initUI();
        }
    }

    private void initUI() {
        setTitle("Partido: " + local.getNombre() + " vs " + visita.getNombre());
        setSize(1000, 700); // Aumenté el tamaño para acomodar más columnas
        setLocationRelativeTo(null);
        setModal(true);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblResultado = new JLabel("Resultado: 0 - 0", JLabel.CENTER);
        lblResultado.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblResultado, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Tabla equipo local - Añadí columnas para estadísticas de pitchers
        modelLocal = new DefaultTableModel(new Object[]{"Nombre", "Posición", "Hits", "Turnos", "Ponches", "Entradas", "EF"}, 0);
        actualizarTabla(modelLocal, local);
        JTable tablaLocal = new JTable(modelLocal);
        tablaLocal.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = tablaLocal.rowAtPoint(e.getPoint());
                    Jugador jugador = local.getJugadores().get(row);
                    if (jugador instanceof Bateador) {
                        new EditarEstadisticasBateador((Bateador) jugador).setVisible(true);
                        actualizarTabla(modelLocal, local);
                    } else if (jugador instanceof Pitcher) {
                        new EditarEstadisticasPitcher((Pitcher) jugador).setVisible(true);
                        actualizarTabla(modelLocal, local);
                    }
                }
            }
        });
        tabbedPane.addTab(local.getNombre(), new JScrollPane(tablaLocal));

        // Tabla equipo visita - Añadí columnas para estadísticas de pitchers
        modelVisita = new DefaultTableModel(new Object[]{"Nombre", "Posición", "Hits", "Turnos", "Ponches", "Entradas", "EF"}, 0);
        actualizarTabla(modelVisita, visita);
        JTable tablaVisita = new JTable(modelVisita);
        tablaVisita.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = tablaVisita.rowAtPoint(e.getPoint());
                    Jugador jugador = visita.getJugadores().get(row);
                    if (jugador instanceof Bateador) {
                        new EditarEstadisticasBateador((Bateador) jugador).setVisible(true);
                        actualizarTabla(modelVisita, visita);
                    } else if (jugador instanceof Pitcher) {
                        new EditarEstadisticasPitcher((Pitcher) jugador).setVisible(true);
                        actualizarTabla(modelVisita, visita);
                    }
                }
            }
        });
        tabbedPane.addTab(visita.getNombre(), new JScrollPane(tablaVisita));

        panel.add(tabbedPane, BorderLayout.CENTER);

        JButton btnSimularInning = new JButton("Simular Inning");
        btnSimularInning.addActionListener(e -> simularInning());

        JButton btnFinalizar = new JButton("Finalizar Partido");
        btnFinalizar.addActionListener(e -> finalizarPartido());

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnSimularInning);
        panelBotones.add(btnFinalizar);
        panel.add(panelBotones, BorderLayout.SOUTH);

        add(panel);
    }

    private void actualizarTabla(DefaultTableModel model, Equipo equipo) {
        model.setRowCount(0);
        for (Jugador jugador : equipo.getJugadores()) {
            if (jugador instanceof Bateador) {
                Bateador bateador = (Bateador) jugador;
                model.addRow(new Object[]{
                    bateador.getNombre(),
                    bateador.getPosicion(),
                    bateador.getStatsBateador().getHits(),
                    bateador.getStatsBateador().getTurnosAlBate(),
                    0, // Ponches para bateadores
                    0, // Entradas para bateadores
                    0.0f // EF para bateadores
                });
            } else if (jugador instanceof Pitcher) {
                Pitcher pitcher = (Pitcher) jugador;
                model.addRow(new Object[]{
                    pitcher.getNombre(),
                    0, // Hits para pitchers
                    0, // Turnos para pitchers
                    pitcher.getStatsPitcher().getPonchesLanzados(),
                    pitcher.getStatsPitcher().getEntradasLanzadas(),
                    String.format("%.2f", pitcher.getStatsPitcher().calcEfectividad()) // Formateado a 2 decimales
                });
            }
        }
    }

    public void simularInning() {
        Random rand = new Random();
        int hitsLocal = rand.nextInt(5);
        int hitsVisita = rand.nextInt(5);
        int ponchesLocal = rand.nextInt(3); // Ponches del pitcher local
        int ponchesVisita = rand.nextInt(3); // Ponches del pitcher visita
        int carrerasPermitidasLocal = rand.nextInt(3); // Carreras permitidas por pitcher local
        int carrerasPermitidasVisita = rand.nextInt(3); // Carreras permitidas por pitcher visita

        // Actualizar stats
        actualizarEstadisticas(local, hitsLocal, ponchesVisita, carrerasPermitidasVisita);
        actualizarEstadisticas(visita, hitsVisita, ponchesLocal, carrerasPermitidasLocal);

        // 3 hits = 1 carrera
        carrerasLocal += hitsLocal / 3;
        carrerasVisita += hitsVisita / 3;

        if (lblResultado != null) {
            lblResultado.setText("Resultado: " + carrerasLocal + " - " + carrerasVisita);
        }
        if (modelLocal != null) {
            actualizarTabla(modelLocal, local);
        }
        if (modelVisita != null) {
            actualizarTabla(modelVisita, visita);
        }
    }

    private void actualizarEstadisticas(Equipo equipo, int hits, int ponches, int carrerasPermitidas) {
        Random rand = new Random();
        
        // Actualizar bateadores (hits)
        for (int i = 0; i < hits; i++) {
            int index = rand.nextInt(equipo.getJugadores().size());
            Jugador jugador = equipo.getJugadores().get(index);
            if (jugador instanceof Bateador) {
                Bateador bateador = (Bateador) jugador;
                bateador.getStatsBateador().actualizarHits(1);
                bateador.getStatsBateador().actualizarTurnosAlBate(1);
            }
        }
        
        // Actualizar pitchers (ponches, entradas y carreras permitidas)
        for (Jugador jugador : equipo.getJugadores()) {
            if (jugador instanceof Pitcher) {
                Pitcher pitcher = (Pitcher) jugador;
                pitcher.getStatsPitcher().actualizarPonches(ponches);
                pitcher.getStatsPitcher().actualizarEntradasLanzadas(1); // 1 entrada por inning
                pitcher.getStatsPitcher().actualizarCarrerasPermitidas(carrerasPermitidas);
                pitcher.getStatsPitcher().actualizarJuegosJugados(1);
                break; // Solo el primer pitcher encontrado
            }
        }
    }

    public void finalizarPartido() {
        // Actualizar records
        if (carrerasLocal > carrerasVisita) {
            local.actualizarRecord(1, 0);
            visita.actualizarRecord(0, 1);
            
            // Actualizar juegos ganados/perdidos de los pitchers
            actualizarJuegosPitchers(local, visita);
        } else if (carrerasVisita > carrerasLocal) {
            local.actualizarRecord(0, 1);
            visita.actualizarRecord(1, 0);
            
            // Actualizar juegos ganados/perdidos de los pitchers
            actualizarJuegosPitchers(visita, local);
        } else {
            // Empate - simular innings extra hasta que haya un ganador
            simularInning();
            finalizarPartido();
            return;
        }

        if (!simulacionAutomatica) {
            JOptionPane.showMessageDialog(this, "Partido finalizado!\nResultado: " + 
                local.getNombre() + " " + carrerasLocal + " - " + 
                visita.getNombre() + " " + carrerasVisita);
            dispose();
        }
    }

    private void actualizarJuegosPitchers(Equipo ganador, Equipo perdedor) {
        // Buscar pitchers abridores (simplificado - en realidad debería ser el pitcher de decisión)
        for (Jugador jugador : ganador.getJugadores()) {
            if (jugador instanceof Pitcher) {
                ((Pitcher) jugador).getStatsPitcher();
                break;
            }
        }
        
        for (Jugador jugador : perdedor.getJugadores()) {
            if (jugador instanceof Pitcher) {
                ((Pitcher) jugador).getStatsPitcher();
                break;
            }
        }
    }

    public int getCarrerasLocal() {
        return carrerasLocal;
    }

    public int getCarrerasVisita() {
        return carrerasVisita;
    }
}