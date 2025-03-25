package logico;

import java.time.LocalDate;
import java.util.ArrayList;

public class Pitcher extends Jugador {
    private String tipoLanzador;
    private ArrayList<String> tiposLanzamientos;
    private PitcherEstadisticas statsPitcher;

    public Pitcher(String nombre, LocalDate fechaNacimiento, String edad, 
                 String nacionalidad, String manoDominante, LocalDate fechaDebut, 
                 float altura, String tipoLanzador) {
        super(nombre, fechaNacimiento, edad, nacionalidad, manoDominante, fechaDebut, altura);
        this.tipoLanzador = tipoLanzador;
        this.tiposLanzamientos = new ArrayList<>();
        this.statsPitcher = new PitcherEstadisticas(0, 0, 0, 0, 0, 0);
    }

    public String getTipoLanzador() {
        return tipoLanzador;
    }

    public void setTipoLanzador(String tipoLanzador) {
        this.tipoLanzador = tipoLanzador;
    }

    public ArrayList<String> getTiposLanzamientos() {
        return new ArrayList<>(tiposLanzamientos);
    }

    public PitcherEstadisticas getStatsPitcher() {
        return statsPitcher;
    }

    public String obtenerTipoPitcher() {
        return this.tipoLanzador;
    }

    public PitcherEstadisticas obtenerEstadisticas() {
        return this.statsPitcher;
    }

    public void actualizarEstadisticas(PitcherEstadisticas nuevasStats) {
        this.statsPitcher = nuevasStats;
    }

    public void agregarLanzamiento(String tipoLanzamiento) {
        if (tipoLanzamiento != null && !tipoLanzamiento.isEmpty()) {
            this.tiposLanzamientos.add(tipoLanzamiento);
        }
    }

    public ArrayList<String> obtenerLanzamientos() {
        return new ArrayList<>(this.tiposLanzamientos);
    }
}