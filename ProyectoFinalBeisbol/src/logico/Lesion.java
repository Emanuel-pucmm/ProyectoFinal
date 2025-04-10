package logico;

import java.io.Serializable;
import java.time.LocalDate;

public class Lesion implements Serializable {

	private static final long serialVersionUID = 1L;
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

    // Método para calcular días fuera (versión simplificada)
    public int calcDiasFuera() {
        if (fechaInicio != null && fechaFin != null) {
            // Diferencia en días usando toEpochDay()
            return (int) (fechaFin.toEpochDay() - fechaInicio.toEpochDay());
        }
        return diasRecuperacion;
    }

    // Método para verificar si la lesión está activa
    public boolean estaActiva() {
        LocalDate hoy = LocalDate.now();
        return !hoy.isBefore(fechaInicio) && !hoy.isAfter(fechaFin);
    }

    // =====================================================
    // Nuevo método para añadir la lesión a la lista en SerieNacional
    // =====================================================
    public static void registrarLesion(SerieNacional serie, Lesion lesion) {
        // Agrega la lesión al atributo listLesiones de la clase SerieNacional
        serie.getListLesiones().add(lesion); // ← Añadir esta línea
    }
}
