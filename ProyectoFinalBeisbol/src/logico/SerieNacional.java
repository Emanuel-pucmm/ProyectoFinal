package logico;

import java.util.ArrayList;

import logical.Control;
import logical.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerieNacional implements Serializable{
	
	private static final long serialVersionUID = 1L;
    private ArrayList<Partido> listPartidos;
    private ArrayList<Equipo> listEquipos;
    private ArrayList<Jugador> listJugadores;
    private ArrayList<Lesion> listLesiones;  // ← Nuevo atributo
    private ArrayList<User> misUsers;
    private static User loginUser;
    private static SerieNacional control;
    private static SerieNacional instance;

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
        misUsers = new ArrayList<>();
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
    public boolean confirmLogin(String username, String password) {
        // If misUsers is empty or null, login will always fail
        if (misUsers == null || misUsers.isEmpty()) {
            System.out.println("No users found in the system");
            return false;
        }
        
        for (User user : misUsers) {
            System.out.println("Checking user: " + user.getUserName());
            if (user.getUserName().equals(username) && user.checkPassword(password)) {
                loginUser = user;
                System.out.println("Login successful for: " + username);
                return true;
            }
        }
        System.out.println("Login failed for: " + username);
        return false;
    }
    public void regUser(User user) {
    	if (misUsers == null) {
            misUsers = new ArrayList<>();
        }
        misUsers.add(user);
        guardarDatos();
    }
    private void guardarDatos() {
        try {
            FileOutputStream fileOut = new FileOutputStream("beisbol.dat");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this);  // Guardamos la instancia actual
            objectOut.close();
            fileOut.close();
            System.out.println("Datos guardados correctamente en beisbol.dat");
        } catch (IOException e) {
            System.err.println("Error al guardar datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public ArrayList<User> getMisUsers() { 
    	return misUsers; }
    
    public void setMisUsers(ArrayList<User> misUsers) { 
    	this.misUsers = misUsers; }
    
    public static User getLoginUser() { 
    	return getInstance().loginUser;
    	 }
    
    public static void setLoginUser(User login) { 
    	loginUser = login; }
    
    

    public static void setControl(SerieNacional temp) {
        control = temp;
    }
    public static SerieNacional getInstance() {
        if (control == null) {
            control = new SerieNacional();
        }
        return control;
    }
   
}
