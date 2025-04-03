package logico;

import java.util.ArrayList;
import java.util.List;

public class SerieNacional {
    
    private ArrayList<Partido> listPartidos;
    private ArrayList<Equipo> listEquipos;
    private ArrayList<Jugador> listJugadores;

    /**
     * Constructor que recibe las listas ya creadas.
     * Si necesitas que arranquen siempre vacías,
     * puedes pasar new ArrayList<>() en cada parámetro
     * cuando crees la SerieNacional.
     */
    public SerieNacional(ArrayList<Partido> listPartidos,
                         ArrayList<Equipo> listEquipos,
                         ArrayList<Jugador> listJugadores) {
        // Asigna las listas
        this.listPartidos = listPartidos;
        this.listEquipos = listEquipos;
        this.listJugadores = listJugadores;
    }

    /**
     * Constructor por defecto, inicializa todas las listas
     * como ArrayList vacíos.
     */
    public SerieNacional() {
        this.listPartidos = new ArrayList<>();
        this.listEquipos = new ArrayList<>();
        this.listJugadores = new ArrayList<>();
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

