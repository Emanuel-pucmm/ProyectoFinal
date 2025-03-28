package visual;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VisualPrincipal extends JFrame {
    private JTable tablaJugadores;
    private JTable tablaEquipos;

    public VisualPrincipal() {
        configurarVentana();
        initComponents();
    }

    private void configurarVentana() {
        setTitle("Gesti�n de B�isbol");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Encabezado azul
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(12, 35, 64));
        headerPanel.setPreferredSize(new Dimension(100, 40));
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Panel de pesta�as
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Pesta�a de Jugadores
        tabbedPane.addTab("Jugadores", crearPanelJugadores());
        
        // Pesta�a de Equipos
        tabbedPane.addTab("Equipos", crearPanelEquipos());
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        getContentPane().add(mainPanel);
    }

    private JPanel crearPanelJugadores() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Botones
        JPanel panelBotones = new JPanel();
        JButton button = new JButton("Agregar");
        button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        	}
        });
        panelBotones.add(button);
        panelBotones.add(new JButton("Editar"));
        panelBotones.add(new JButton("Eliminar"));
        
        panel.add(panelBotones, BorderLayout.NORTH);
        
        // Tabla de jugadores
        String[] columnas = {"Nombre", "Posici�n", "Edad"};
        Object[][] datos = {
            {"Juan P�rez", "Bateador", "28"},
            {"Carlos Mart�nez", "Lanzador", "30"}
        };
        
        tablaJugadores = new JTable(datos, columnas);
        panel.add(new JScrollPane(tablaJugadores), BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel crearPanelEquipos() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Tabla de equipos
        String[] columnas = {"Nombre", "Estadio", "JG", "JP"};
        Object[][] datos = {
            {"Yankees", "Yankee Stadium", "95", "67"},
            {"Red Sox", "Fenway Park", "84", "78"}
        };
        
        tablaEquipos = new JTable(datos, columnas);
        panel.add(new JScrollPane(tablaEquipos), BorderLayout.CENTER);
        
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VisualPrincipal().setVisible(true);
        });
    }
}