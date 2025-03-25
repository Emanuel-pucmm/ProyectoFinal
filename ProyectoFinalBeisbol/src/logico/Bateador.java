package logico;

import java.time.LocalDate;

public class Bateador extends Jugador {
    private String posicion;
    private BateadorEstadisticas statsBateador;

    public Bateador(String nombre, LocalDate fechaNacimiento, String edad, 
                   String nacionalidad, String manoDominante, LocalDate fechaDebut, 
                   float altura, String posicion) {
        super(nombre, fechaNacimiento, edad, nacionalidad, manoDominante, fechaDebut, altura);
        this.posicion = posicion;
        this.statsBateador = new BateadorEstadisticas(0, 0, 0, 0, 0);
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public BateadorEstadisticas getStatsBateador() {
        return statsBateador;
    }

    public String obtenerPosicion() {
        return this.posicion;
    }

    public BateadorEstadisticas obtenerEstadisticas() {
        return this.statsBateador;
    }

    public void actualizarEstadisticas(BateadorEstadisticas nuevasStats) {
        this.statsBateador = nuevasStats;
    }
    
    @Override
    public String getTipoJugador() {
        return "Bateador";
    }
}