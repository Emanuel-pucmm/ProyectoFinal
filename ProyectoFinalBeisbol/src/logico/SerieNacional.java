package logico;

import java.util.ArrayList;
import java.io.Serializable;

public class SerieNacional implements Serializable{
	
	private static final long serialVersionUID = 1L;
    private ArrayList<Partido> listPartidos;
    private ArrayList<Equipo> listEquipos;
    private ArrayList<Jugador> listJugadores;
    private ArrayList<Lesion> listLesiones;  // ← Nuevo atributo

    /**
     * Constructor que recibe las listas ya creadas.
     */
    public SerieNacional(ArrayList<Partido> listPartidos,
                         ArrayList<Equipo> listEquipos,
                         ArrayList<Jugador> listJugadores,
                         ArrayList<Lesion> listLesiones) {
        this.listPartidos = listPartidos;
        this.listEquipos = listEquipos;
        this.listJugadores = listJugadores;
        this.listLesiones = listLesiones;
    }

    /**
     * Constructor por defecto (inicializa todas las listas vacías).
     */
    public SerieNacional() {
        this.listPartidos = new ArrayList<>();
        this.listEquipos = new ArrayList<>();
        this.listJugadores = new ArrayList<>();
        this.listLesiones = new ArrayList<>();
    }

    // ================== GETTERS / SETTERS ================== //

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

    // Getter y Setter para la lista de Lesiones
    public ArrayList<Lesion> getListLesiones() {
        return listLesiones;
    }

    public void setListLesiones(ArrayList<Lesion> listLesiones) {
        this.listLesiones = listLesiones;
    }

    // ================== MÉTODOS AUXILIARES ================== //

    /**
     * Agrega un equipo a la lista de equipos, si no está ya incluido.
     */
    public void agregarEquipo(Equipo equipo) {
        if (equipo != null && !listEquipos.contains(equipo)) {
            listEquipos.add(equipo);
        }
    }

    /**
     * Agrega un jugador a la lista general de jugadores,
     * si no está ya incluido.
     * Nota: Esto NO lo asigna a ningún equipo automáticamente.
     */
    public void agregarJugador(Jugador jugador) {
        if (jugador != null && !listJugadores.contains(jugador)) {
            listJugadores.add(jugador);
        }
    }

    /**
     * Agrega un partido a la lista de partidos,
     * si no está ya incluido.
     */
    public void agregarPartido(Partido partido) {
        if (partido != null && !listPartidos.contains(partido)) {
            listPartidos.add(partido);
        }
    }

    /**
     * Busca un jugador por nombre en la lista general de jugadores.
     * Devuelve el jugador si lo encuentra, o null si no existe.
     */
    public Jugador buscarJugador(String nombre) {
        for (Jugador jugador : listJugadores) {
            if (jugador.getNombre().equalsIgnoreCase(nombre)) {
                return jugador;
            }
        }
        return null;
    }

    /**
     * Busca un equipo por nombre en la lista de equipos.
     * Devuelve el equipo si lo encuentra, o null si no existe.
     */
    public Equipo buscarEquipo(String nombre) {
        for (Equipo equipo : listEquipos) {
            if (equipo.getNombre().equalsIgnoreCase(nombre)) {
                return equipo;
            }
        }
        return null;
    }
}
