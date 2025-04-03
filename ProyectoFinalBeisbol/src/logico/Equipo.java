package logico;

import java.util.ArrayList;
import java.util.List;

public class Equipo {
	private String nombre;
    private String estadio;
    private boolean calificado;
    private int juegosGan;
    private int juegosPer;
    private ArrayList<Jugador> jugadores;
    private ArrayList<Partido> partidosJugados;
    
	public Equipo(String nombre, String estadio, boolean calificado, int juegosGan, int juegosPer,
			ArrayList<Jugador> jugadores, ArrayList<Partido> partidosJugados) {
		super();
		this.nombre = nombre;
		this.estadio = estadio;
		this.calificado = calificado;
		this.juegosGan = juegosGan;
		this.juegosPer = juegosPer;
		this.jugadores = jugadores;
		this.partidosJugados = partidosJugados;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEstadio() {
		return estadio;
	}

	public void setEstadio(String estadio) {
		this.estadio = estadio;
	}

	public boolean isCalificado() {
		return calificado;
	}

	public void setCalificado(boolean calificado) {
		this.calificado = calificado;
	}

	public int getJuegosGan() {
		return juegosGan;
	}

	public void setJuegosGan(int juegosGan) {
		this.juegosGan = juegosGan;
	}

	public int getJuegosPer() {
		return juegosPer;
	}

	public void setJuegosPer(int juegosPer) {
		this.juegosPer = juegosPer;
	}

	public ArrayList<Jugador> getJugadores() {
		return jugadores;
	}

	public void setJugadores(ArrayList<Jugador> jugadores) {
		this.jugadores = jugadores;
	}

	public ArrayList<Partido> getPartidosJugados() {
		return partidosJugados;
	}

	public void setPartidosJugados(ArrayList<Partido> partidosJugados) {
		this.partidosJugados = partidosJugados;
	}
	
	public void agregarJugador(Jugador jugador) {
        if (jugador != null) {
            jugadores.add(jugador);
        }
    }
	
	public boolean eliminarJugador(Jugador jugador) {
        return jugadores.remove(jugador);
    }
	
	public List<Jugador> obtenerJugadores() {
        return jugadores;
    }
	
	  public void actualizarRecord(int juegosGan, int juegosPer) {
	        this.juegosGan += juegosGan;
	        this.juegosPer += juegosPer;
	    }
	  
	  public float calcPorcentajeVictorias() {
	        int totalJuegos = juegosGan + juegosPer;
	        if (totalJuegos == 0) {
	            return 0.0f; 
	        }
	        return (float) juegosGan / totalJuegos * 100;
	    }
	  @Override
	  public String toString() {
	      return this.nombre; // Esto hará que aparezca el nombre en el ComboBox
	  }
}
