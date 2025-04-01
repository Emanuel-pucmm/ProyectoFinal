package logico;

import java.time.*;

public class Partido {
    private LocalDate fecha;
    private Equipo equipoLocal;
    private Equipo equipoVisita;
    private Resultado resultado;
    
    public Partido(LocalDate fecha, Equipo equipoLocal, Equipo equipoVisita, Resultado resultado) {
		super();
		this.fecha = fecha;
		this.equipoLocal = equipoLocal;
		this.equipoVisita = equipoVisita;
		this.resultado = resultado;
	}

   

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Equipo getEquipoLocal() {
		return equipoLocal;
	}

	public void setEquipoLocal(Equipo equipoLocal) {
		this.equipoLocal = equipoLocal;
	}

	public Equipo getEquipoVisita() {
		return equipoVisita;
	}

	public void setEquipoVisita(Equipo equipoVisita) {
		this.equipoVisita = equipoVisita;
	}

	public Resultado getResultado() {
		return resultado;
	}

	public void setResultado(Resultado resultado) {
		this.resultado = resultado;
	}
	 // Nuevo método para registrar resultado
    public void registrarResultado(int carrerasLocal, int carrerasVisita) {
        this.resultado = new Resultado(carrerasLocal, carrerasVisita, carrerasVisita, carrerasVisita, carrerasVisita, carrerasVisita);
        if (carrerasLocal > carrerasVisita) {
            equipoLocal.actualizarRecord(1, 0);
            equipoVisita.actualizarRecord(0, 1);
        } else {
            equipoLocal.actualizarRecord(0, 1);
            equipoVisita.actualizarRecord(1, 0);
        }
    }
	
}