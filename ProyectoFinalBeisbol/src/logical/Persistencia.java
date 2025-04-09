package logical;

import java.io.*;

import logico.SerieNacional;

public class Persistencia {
    private static final String ARCHIVO = "beisbol.dat";

    public static void guardarDatos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            oos.writeObject(Control.getInstance());
        } catch (IOException e) {
            System.err.println("Error al guardar datos: " + e.getMessage());
        }
    }

    public static void cargarDatos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO))) {
            SerieNacional.setControl((SerieNacional) ois.readObject());
        } catch (FileNotFoundException e) {
            // Primera ejecución, se crea con valores por defecto
            guardarDatos();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
        }
    }
    public static void eliminarArchivo() {
        File archivo = new File(ARCHIVO);
        if (archivo.exists()) {
            archivo.delete();
        }
    }
}