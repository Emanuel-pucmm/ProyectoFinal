package logico;

import java.io.Serializable;

public class BateadorEstadisticas implements Serializable{
	
	private static final long serialVersionUID = 1L;
	 private int hits;
	 private int homeRuns;
	 private int carrerasAnotadas;
	 private int juegosJugados;
	 private int turnosAlBate;
	public BateadorEstadisticas(int hits, int homeRuns, int carrerasAnotadas, int juegosJugados, int turnosAlBate) {
		super();
		this.hits = hits;
		this.homeRuns = homeRuns;
		this.carrerasAnotadas = carrerasAnotadas;
		this.juegosJugados = juegosJugados;
		this.turnosAlBate = turnosAlBate;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public int getHomeRuns() {
		return homeRuns;
	}
	public void setHomeRuns(int homeRuns) {
		this.homeRuns = homeRuns;
	}
	public int getCarrerasAnotadas() {
		return carrerasAnotadas;
	}
	public void setCarrerasAnotadas(int carrerasAnotadas) {
		this.carrerasAnotadas = carrerasAnotadas;
	}
	public int getJuegosJugados() {
		return juegosJugados;
	}
	public void setJuegosJugados(int juegosJugados) {
		this.juegosJugados = juegosJugados;
	}
	public int getTurnosAlBate() {
		return turnosAlBate;
	}
	public void setTurnosAlBate(int turnosAlBate) {
		this.turnosAlBate = turnosAlBate;
	}
	 
	  public void actualizarHits(int hits) {
	        this.hits += hits;
	    }
	  public void actualizarHomeRuns(int homeRuns) {
	        this.homeRuns += homeRuns;
	    }
	  public void actualizarCarrerasAnotadas(int carrerasAnotadas) {
	        this.carrerasAnotadas += carrerasAnotadas;
	    }
	  public void actualizarTurnosAlBate(int turnosAlBate) {
	        this.turnosAlBate += turnosAlBate;
	    }
	  public float calcPromedioBateo() {
	        if (turnosAlBate == 0) {
	            return 0.0f; // Evitar división por cero
	        }
	        return (float) hits / turnosAlBate;
	    }
}
