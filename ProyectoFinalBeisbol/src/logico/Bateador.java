package logico;

import java.time.LocalDate;

public class Bateador extends Jugador {
	
	private static final long serialVersionUID = 1L;
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

    @Override
    public String getTipoJugador() {
        return "Bateador";
    }

    // (Opcional) sobrescribimos toString() para mostrar algo más si queremos
    @Override
    public String toString() {
        // Llama al método de Jugador, que muestra el nombre
        // Podrías añadirle más info, por ejemplo:
        // return super.toString() + " (Bateador)";
        return super.toString();
    }
}
