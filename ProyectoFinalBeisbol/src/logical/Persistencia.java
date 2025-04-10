package logical;

import java.io.*;

import logico.SerieNacional;

public class Persistencia {
    private static final String ARCHIVO = "beisbol.dat";

    public static void guardarDatos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            oos.writeObject(SerieNacional.getInstance());
        } catch (IOException e) {
            System.err.println("Error al guardar datos: " + e.getMessage());
        }
    }

    public static void cargarDatos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO))) {
            SerieNacional temp = (SerieNacional) ois.readObject();
            
            SerieNacional.setControl(temp);
            SerieNacional.setInstance(temp);
            if (temp.getMisUsers() != null) {
                SerieNacional.getInstance().setMisUsers(temp.getMisUsers());
            }
        } catch (FileNotFoundException e) {
            
            User admin = new User("Administrador", "admin", "admin123");
            SerieNacional.getInstance().regUser(admin);
        } catch (Exception e) {
            System.err.println("Error al cargar: " + e.getMessage());
        }
    }
    public static void eliminarArchivo() {
        File archivo = new File(ARCHIVO);
        if (archivo.exists()) {
            archivo.delete();
        }
    }

}