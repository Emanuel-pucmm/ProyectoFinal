package visual;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import logico.*;
import excepcion.*;

public class EditarEstadisticasBateador extends JDialog {
    private Bateador bateador;
    private JSpinner spinnerHits;
    private JSpinner spinnerTurnos;
    private JSpinner spinnerHR;
    private JSpinner spinnerCarreras;

    public EditarEstadisticasBateador(Bateador bateador) {
        this.bateador = bateador;
        setTitle("Editar Stats: " + bateador.getNombre());
        setSize(400, 300);
        setLocationRelativeTo(null);
        setModal(true);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Componentes
        panel.add(new JLabel("Hits:"));
        spinnerHits = new JSpinner(new SpinnerNumberModel(
            bateador.getStatsBateador().getHits(), 
            0, 
            1000, 
            1
        ));
        panel.add(spinnerHits);

        panel.add(new JLabel("Turnos al bate:"));
        spinnerTurnos = new JSpinner(new SpinnerNumberModel(
            bateador.getStatsBateador().getTurnosAlBate(), 
            0, 
            1000, 
            1
        ));
        panel.add(spinnerTurnos);

        panel.add(new JLabel("Home Runs:"));
        spinnerHR = new JSpinner(new SpinnerNumberModel(
            bateador.getStatsBateador().getHomeRuns(), 
            0, 
            100, 
            1
        ));
        panel.add(spinnerHR);

        panel.add(new JLabel("Carreras anotadas:"));
        spinnerCarreras = new JSpinner(new SpinnerNumberModel(
            bateador.getStatsBateador().getCarrerasAnotadas(), 
            0, 
            500, 
            1
        ));
        panel.add(spinnerCarreras);

        // Botones
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardarEstadisticas());

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        // Dise�o
        add(panel, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void guardarEstadisticas() {
    	 try {
             int hits = (int) spinnerHits.getValue();
             int turnos = (int) spinnerTurnos.getValue();
             int hr = (int) spinnerHR.getValue();
             int carreras = (int) spinnerCarreras.getValue();
             
             if(hits < 0 || turnos < 0 || hr < 0 || carreras < 0) {
                 throw new NumeroNegativoExcepcion("estad�sticas");
             }
             
             if(turnos < hits) {
                 throw new Exception("Los turnos no pueden ser menores que los hits");
             }

             bateador.getStatsBateador().setHits(hits);
             bateador.getStatsBateador().setTurnosAlBate(turnos);
             bateador.getStatsBateador().setHomeRuns(hr);
             bateador.getStatsBateador().setCarrerasAnotadas(carreras);

             JOptionPane.showMessageDialog(this, "Estad�sticas actualizadas!");
             dispose();

         } catch (NumeroNegativoExcepcion e) {
             JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
         } catch (Exception e) {
             JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
         }
     }
}