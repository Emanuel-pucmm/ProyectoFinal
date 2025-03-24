package logico;

import java.time.LocalDate;

public class Lesion {
    // Atributos seg�n el UML
    private String tipo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int diasRecuperacion;

    // Constructor
    public Lesion(String tipo, LocalDate fechaInicio, LocalDate fechaFin, int diasRecuperacion) {
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.diasRecuperacion = diasRecuperacion;
    }

    // Getters y Setters
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getDiasRecuperacion() {
        return diasRecuperacion;
    }

    public void setDiasRecuperacion(int diasRecuperacion) {
        this.diasRecuperacion = diasRecuperacion;
    }

    // M�todo para calcular d�as fuera (versi�n simplificada)
    public int calcDiasFuera() {
        if (fechaInicio != null && fechaFin != null) {
            // Diferencia en d�as usando toEpochDay()
            return (int)(fechaFin.toEpochDay() - fechaInicio.toEpochDay());
        }
        return diasRecuperacion;
    }

    // M�todo para verificar si la lesi�n est� activa
    public boolean estaActiva() {
        LocalDate hoy = LocalDate.now();
        return !hoy.isBefore(fechaInicio) && !hoy.isAfter(fechaFin);
    }

    // M�todo toString
    @Override
    public String toString() {
        return "Lesion{" +
                "tipo='" + tipo + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", diasRecuperacion=" + diasRecuperacion +
                '}';
    }
}
