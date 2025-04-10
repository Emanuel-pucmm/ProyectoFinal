package cliente;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

public class Cliente extends Frame implements ActionListener {
    static Socket sfd = null;
    static DataOutputStream SalidaSocket;
    static TextField salida;
    static TextArea entrada;
    String texto;

    public Cliente() {
        setTitle("Sistema de Respaldo");
        setSize(350, 200);
        
        // Componentes igual que tu profesor pero simplificados para respaldo
        salida = new TextField(30);
        salida.addActionListener(this);
        
        entrada = new TextArea();
        entrada.setEditable(false);
        
        add("South", salida);
        add("Center", entrada);
        setVisible(true);
        
        // Conexión automática al iniciar (como en tu código original)
        conectarServidor();
    }

    private void conectarServidor() {
        try {
            sfd = new Socket("localhost", 7000); // Auto-conecta al localhost
            SalidaSocket = new DataOutputStream(sfd.getOutputStream());
            entrada.append("Conectado al servidor de respaldo\n");
        } catch (Exception e) {
            entrada.append("Error al conectar: " + e.getMessage() + "\n");
        }
    }

    public void actionPerformed(ActionEvent e) {
        texto = salida.getText();
        salida.setText("");
        
        if (texto.equalsIgnoreCase("respaldo")) {
            enviarRespaldo();
        }
    }

    private void enviarRespaldo() {
        try {
            DataInputStream aux = new DataInputStream(new FileInputStream("beisbol.dat"));
            int unByte;
            
            entrada.append("Enviando respaldo...\n");
            while ((unByte = aux.read()) != -1) {
                SalidaSocket.write(unByte);
                SalidaSocket.flush();
            }
            aux.close();
            entrada.append("Respaldo enviado exitosamente\n");
        } catch (Exception ex) {
            entrada.append("Error: " + ex.getMessage() + "\n");
        }
    }

    public boolean handleEvent(Event e) {
        if ((e.target == this) && (e.id == Event.WINDOW_DESTROY)) {
            try {
                if (sfd != null) sfd.close();
            } catch (IOException ioe) {
                System.out.println("Error al cerrar: " + ioe);
            }
            dispose();
        }
        return true;
    }

    public static void main(String[] args) {
        new Cliente();
    }
}