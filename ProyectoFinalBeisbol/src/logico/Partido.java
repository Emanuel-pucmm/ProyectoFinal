package logico;

import java.time.LocalDate;

public class Partido {
    // Atributos
    private LocalDate fecha;
    private Equipo equipoLocal;
    private Equipo equipoVisita;
    private Resultado resultadoFinal;

    // Constructor
    public Partido(LocalDate fecha, Equipo equipoLocal, Equipo equipoVisita) {
        this.fecha = fecha;
        this.equipoLocal = equipoLocal;
        this.equipoVisita = equipoVisita;
        this.resultadoFinal = null; // Inicialmente sin resultado
    }

    // Getters y Setters
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public Equipo getEquipoVisita() {
        return equipoVisita;
    }

    public Resultado getResultadoFinal() {
        return resultadoFinal;
    }

    // Método para asignar equipos
    public void asignarEquipos(Equipo local, Equipo visita) {
        this.equipoLocal = local;
        this.equipoVisita = visita;
    }

    // Método para registrar resultado
    public void registrarResultado(int carrerasLocal, int carrerasVisita) {
        this.resultadoFinal = new Resultado(carrerasLocal, carrerasVisita, carrerasVisita, carrerasVisita, carrerasVisita, carrerasVisita);
        
        // Actualizar records de equipos
        if (carrerasLocal > carrerasVisita) {
            equipoLocal.actualizarRecord(1, 0);
            equipoVisita.actualizarRecord(0, 1);
        } else {
            equipoLocal.actualizarRecord(0, 1);
            equipoVisita.actualizarRecord(1, 0);
        }
    }

    // Método para generar estadísticas (devuelve String formateado)
    public String generarEstadisticasPartido() {
        if (resultadoFinal == null) {
            return "Partido sin resultado registrado";
        }
        
        return String.format(
            "Fecha: %s | Local: %s %d - Visitante: %s %d | Ganador: %s",
            fecha.toString(),
            equipoLocal.getNombre(),
            resultadoFinal.getCarrerasLocal(),
            equipoVisita.getNombre(),
            resultadoFinal.getCarrerasVisita(),
            resultadoFinal.getCarrerasLocal() > resultadoFinal.getCarrerasVisita() 
                ? equipoLocal.getNombre() : equipoVisita.getNombre()
        );
    }
}