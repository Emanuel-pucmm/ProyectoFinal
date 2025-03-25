package logico;

import java.util.ArrayList;

public class SerieNacional {
	 private ArrayList<Partido> listPartidos;
	 private ArrayList<Equipo> listEquipos;
	 private ArrayList<Jugador> listJugadores;
	
	 public SerieNacional(ArrayList<Partido> listPartidos, ArrayList<Equipo> listEquipos,
			ArrayList<Jugador> listJugadores) {
		super();
		this.listPartidos = listPartidos;
		this.listEquipos = listEquipos;
		this.listJugadores = listJugadores;
	}

	public ArrayList<Partido> getListPartidos() {
		return listPartidos;
	}

	public void setListPartidos(ArrayList<Partido> listPartidos) {
		this.listPartidos = listPartidos;
	}

	public ArrayList<Equipo> getListEquipos() {
		return listEquipos;
	}

	public void setListEquipos(ArrayList<Equipo> listEquipos) {
		this.listEquipos = listEquipos;
	}

	public ArrayList<Jugador> getListJugadores() {
		return listJugadores;
	}

	public void setListJugadores(ArrayList<Jugador> listJugadores) {
		this.listJugadores = listJugadores;
	}
	
	public void agregarEquipo(Equipo equipo) {
        if (equipo != null && !listEquipos.contains(equipo)) {
            listEquipos.add(equipo);
        }
    }
	
	public void agregarJugador(Jugador jugador) {
        if (jugador != null && !listJugadores.contains(jugador)) {
            listJugadores.add(jugador);
        }
    }
	
	public void agregarPartido(Partido partido) {
        if (partido != null && !listPartidos.contains(partido)) {
            listPartidos.add(partido);
        }
    }
	
	 public Jugador buscarJugador(String nombre) {
	        for (Jugador jugador : listJugadores) {
	            if (jugador.getNombre().equals(nombre)) {
	                return jugador;
	            }
	        }
	        return null;
	    }
	 
	 public Equipo buscarEquipo(String nombre) {
	        for (Equipo equipo : listEquipos) {
	            if (equipo.getNombre().equals(nombre)) {
	                return equipo;
	            }
	        }
	        return null;
	    }
	 
}
