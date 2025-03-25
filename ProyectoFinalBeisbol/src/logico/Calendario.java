package logico;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Calendario {
    // Atributo seg�n UML
    private ArrayList<Partido> partidos;

    // Constructor
    public Calendario() {
        this.partidos = new ArrayList<>();
    }

    // M�todos seg�n UML
    public void agregarPartido(Partido partido) {
        if (partido != null) {
            this.partidos.add(partido);
        }
    }

    public Partido obtenerPartidoPorFecha(LocalDate fecha) {
        for (Partido partido : partidos) {
            if (partido.getFecha().equals(fecha)) {
                return partido;
            }
        }
        return null;
    }

    public void generarCalendario(boolean completar) {
        // L�gica para generar el calendario
        if (completar) {
            // Generar calendario completo
        } else {
            // Generar calendario b�sico
        }
    }

    // Getter y Setter
    public ArrayList<Partido> getPartidos() {
        return new ArrayList<>(partidos); // Devuelve copia para proteger encapsulamiento
    }

    public void setPartidos(ArrayList<Partido> partidos) {
        if (partidos != null) {
            this.partidos = new ArrayList<>(partidos);
        }
    }
}
