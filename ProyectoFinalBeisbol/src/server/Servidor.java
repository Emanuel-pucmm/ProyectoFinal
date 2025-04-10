package server;

import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        try {
            ServerSocket sfd = new ServerSocket(7000);
            System.out.println("Servidor iniciado en puerto 7000...");
            
            while (true) {
                Socket nsfd = sfd.accept();
                System.out.println("Conexion aceptada de: " + nsfd.getInetAddress());
                
                DataInputStream EntradaSocket = new DataInputStream(nsfd.getInputStream());
                DataOutputStream escritor = new DataOutputStream(new FileOutputStream("beisbol_respaldo.dat"));
                
                int unByte;
                while ((unByte = EntradaSocket.read()) != -1) {
                    escritor.write(unByte);
                }
                
                EntradaSocket.close();
                escritor.close();
                System.out.println("Respaldo recibido correctamente");
            }
        } catch (IOException ioe) {
            System.out.println("Error: " + ioe);
        }
    }
}