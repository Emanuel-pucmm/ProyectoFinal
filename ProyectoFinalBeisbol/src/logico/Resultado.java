package logico;

import java.io.Serializable;

public class Resultado implements Serializable {
	
	private static final long serialVersionUID = 1L;
    private int hitsLocal;
    private int hitsVisita;
    private int carrerasLocal;
    private int carrerasVisita;
    private int erroresLocal;
    private int erroresVisita;

    // Constructor
    public Resultado(int hitsLocal, int hitsVisita, int carrerasLocal, int carrerasVisita ,int erroresLocal, int erroresVisita) {
        this.hitsLocal = hitsLocal;
        this.hitsVisita = hitsVisita;
        this.carrerasLocal = carrerasLocal;
        this.carrerasVisita = carrerasVisita;
        this.erroresLocal = erroresLocal;
        this.erroresVisita = erroresVisita;
    }

    // Getters
    public int getHitsLocal() {
        return hitsLocal;
    }

    public int getHitsVisita() {
        return hitsVisita;
    }

    public int getCarrerasLocal() {
        return carrerasLocal;
    }

    public int getCarrerasVisita() {
        return carrerasVisita;
    }

    public int getErroresLocal() {
        return erroresLocal;
    }

    public int getErroresVisita() {
        return erroresVisita;
    }

    // Setters
    public void setHitsLocal(int hitsLocal) {
        this.hitsLocal = hitsLocal;
    }

    public void setHitsVisita(int hitsVisita) {
        this.hitsVisita = hitsVisita;
    }

    public void setCarrerasLocal(int carrerasLocal) {
        this.carrerasLocal = carrerasLocal;
    }

    public void setCarrerasVisita(int carrerasVisita) {
        this.carrerasVisita = carrerasVisita;
    }

    public void setErroresLocal(int erroresLocal) {
        this.erroresLocal = erroresLocal;
    }

    public void setErroresVisita(int erroresVisita) {
        this.erroresVisita = erroresVisita;
    }

    // Métodos para actualizar estadísticas
    public void actualizarHitsTotal(int hitsLocal, int hitsVisita) {
        this.hitsLocal += hitsLocal;
        this.hitsVisita += hitsVisita;
    }

    public void actualizarCarrerasTotal(int carrerasLocal, int carrerasVisita) {
        this.carrerasLocal += carrerasLocal;
        this.carrerasVisita += carrerasVisita;
    }

    public void actualizarErroresTotal(int erroresLocal, int erroresVisita) {
        this.erroresLocal += erroresLocal;
        this.erroresVisita += erroresVisita;
    }

}