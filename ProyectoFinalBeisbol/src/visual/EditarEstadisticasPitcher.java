package visual;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import logico.*;

public class EditarEstadisticasPitcher extends JDialog {
    private Pitcher pitcher;
    private JTextField txtEntradasLanzadas;
    private JTextField txtPonchesLanzados;
    private JTextField txtStrikes;
    private JTextField txtBolas;
    private JTextField txtJuegosJugados;
    private JTextField txtCarrerasPermitidas;

    public EditarEstadisticasPitcher(Pitcher pitcher) {
        this.pitcher = pitcher;
        initUI();
    }

    private void initUI() {
        setTitle("Editar Estadísticas de " + pitcher.getNombre());
        setSize(500, 350);
        setLocationRelativeTo(null);
        setModal(true);

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Entradas Lanzadas
        panel.add(new JLabel("Entradas Lanzadas:"));
        txtEntradasLanzadas = new JTextField(String.valueOf(pitcher.getStatsPitcher().getEntradasLanzadas()));
        panel.add(txtEntradasLanzadas);

        // Ponches Lanzados
        panel.add(new JLabel("Ponches Lanzados:"));
        txtPonchesLanzados = new JTextField(String.valueOf(pitcher.getStatsPitcher().getPonchesLanzados()));
        panel.add(txtPonchesLanzados);

        // Strikes
        panel.add(new JLabel("Strikes:"));
        txtStrikes = new JTextField(String.valueOf(pitcher.getStatsPitcher().getStrikes()));
        panel.add(txtStrikes);

        // Bolas
        panel.add(new JLabel("Bolas:"));
        txtBolas = new JTextField(String.valueOf(pitcher.getStatsPitcher().getBolas()));
        panel.add(txtBolas);

        // Juegos Jugados
        panel.add(new JLabel("Juegos Jugados:"));
        txtJuegosJugados = new JTextField(String.valueOf(pitcher.getStatsPitcher().getJuegosJugados()));
        panel.add(txtJuegosJugados);

        // Carreras Permitidas
        panel.add(new JLabel("Carreras Permitidas:"));
        txtCarrerasPermitidas = new JTextField(String.valueOf(pitcher.getStatsPitcher().getCarrerasPermitidas()));
        panel.add(txtCarrerasPermitidas);

        // Botones
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardarEstadisticas());
        panel.add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        panel.add(btnCancelar);

        add(panel);
    }

    private void guardarEstadisticas() {
        try {
            int entradas = Integer.parseInt(txtEntradasLanzadas.getText());
            int ponches = Integer.parseInt(txtPonchesLanzados.getText());
            int strikes = Integer.parseInt(txtStrikes.getText());
            int bolas = Integer.parseInt(txtBolas.getText());
            int juegos = Integer.parseInt(txtJuegosJugados.getText());
            int carreras = Integer.parseInt(txtCarrerasPermitidas.getText());

            pitcher.getStatsPitcher().setEntradasLanzadas(entradas);
            pitcher.getStatsPitcher().setPonchesLanzados(ponches);
            pitcher.getStatsPitcher().setStrikes(strikes);
            pitcher.getStatsPitcher().setBolas(bolas);
            pitcher.getStatsPitcher().setJuegosJugados(juegos);
            pitcher.getStatsPitcher().setCarrerasPermitidas(carreras);

            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese valores numéricos válidos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
