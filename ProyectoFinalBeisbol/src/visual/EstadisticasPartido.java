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
        setSize(900, 600);
        setLocationRelativeTo(null);
        setModal(true);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblResultado = new JLabel("Resultado: 0 - 0", JLabel.CENTER);
        lblResultado.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblResultado, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Tabla equipo local
        modelLocal = new DefaultTableModel(new Object[]{"Nombre", "Posición", "Hits", "Turnos"}, 0);
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
                    }
                }
            }
        });
        tabbedPane.addTab(local.getNombre(), new JScrollPane(tablaLocal));

        // Tabla equipo visita
        modelVisita = new DefaultTableModel(new Object[]{"Nombre", "Posición", "Hits", "Turnos"}, 0);
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
                    bateador.getStatsBateador().getTurnosAlBate()
                });
            }
        }
    }

    public void simularInning() {
        Random rand = new Random();
        int hitsLocal = rand.nextInt(5);
        int hitsVisita = rand.nextInt(5);

        // Actualizar stats
        actualizarEstadisticas(local, hitsLocal);
        actualizarEstadisticas(visita, hitsVisita);

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

    private void actualizarEstadisticas(Equipo equipo, int hits) {
        Random rand = new Random();
        for (int i = 0; i < hits; i++) {
            int index = rand.nextInt(equipo.getJugadores().size());
            Jugador jugador = equipo.getJugadores().get(index);
            if (jugador instanceof Bateador) {
                Bateador bateador = (Bateador) jugador;
                bateador.getStatsBateador().actualizarHits(1);
                bateador.getStatsBateador().actualizarTurnosAlBate(1);
            }
        }
    }

    public void finalizarPartido() {
        // Actualizar records
        if (carrerasLocal > carrerasVisita) {
            local.actualizarRecord(1, 0);
            visita.actualizarRecord(0, 1);
        } else if (carrerasVisita > carrerasLocal) {
            local.actualizarRecord(0, 1);
            visita.actualizarRecord(1, 0);
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

    public int getCarrerasLocal() {
        return carrerasLocal;
    }

    public int getCarrerasVisita() {
        return carrerasVisita;
    }
}