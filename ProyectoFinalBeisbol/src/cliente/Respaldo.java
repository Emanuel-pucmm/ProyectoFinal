package cliente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.Socket;

public class Respaldo extends JDialog {
    private final JPanel contentPanel = new JPanel();

    public Respaldo() {
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(new FlowLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            
            {
                JButton okButton = new JButton("Generar Respaldo");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Lógica idéntica a la de Nueva.java
                        try {
                            Socket sfd = new Socket("localhost", 7000);
                            DataInputStream aux = new DataInputStream(new FileInputStream("beisbol.dat"));
                            DataOutputStream SalidaSocket = new DataOutputStream(sfd.getOutputStream());
                            
                            int unByte;
                            while ((unByte = aux.read()) != -1) {
                                SalidaSocket.write(unByte);
                                SalidaSocket.flush();
                            }
                            
                            aux.close();
                            JOptionPane.showMessageDialog(Respaldo.this, "Respaldo generado exitosamente");
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(Respaldo.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                buttonPane.add(okButton);
            }
            
            {
                JButton cancelButton = new JButton("Cancelar");
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                });
                buttonPane.add(cancelButton);
            }
        }
    }
}