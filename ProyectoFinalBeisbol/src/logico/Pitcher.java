package logico;

import java.time.LocalDate;
import java.util.ArrayList;

public class Pitcher extends Jugador {
	
	private static final long serialVersionUID = 1L;
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

    @Override
    public String getTipoJugador() {
        return "Lanzador";
    }

    // Sobrescribimos toString() (opcional)
    @Override
    public String toString() {
        // Por defecto, mostramos el nombre heredado de Jugador
        // Podrías hacer: return super.toString() + " (Pitcher)";
        return super.toString();
    }
}
