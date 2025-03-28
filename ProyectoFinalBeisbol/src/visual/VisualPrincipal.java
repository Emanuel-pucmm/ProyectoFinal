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
        setTitle("Gestión de Béisbol");
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

        // Panel de pestañas
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Pestaña de Jugadores
        tabbedPane.addTab("Jugadores", crearPanelJugadores());
        
        // Pestaña de Equipos
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
        String[] columnas = {"Nombre", "Posición", "Edad"};
        Object[][] datos = {
            {"Juan Pérez", "Bateador", "28"},
            {"Carlos Martínez", "Lanzador", "30"}
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