package logico;

public class PitcherEstadisticas {
	private int entradasLanzadas;
    private int ponchesLanzados;
    private int strikes;
    private int bolas;
    private int juegosJugados;
    private int carrerasPermitidas;
	
    public PitcherEstadisticas(int entradasLanzadas, int ponchesLanzados, int strikes, int bolas, int juegosJugados,
			int carrerasPermitidas) {
		super();
		this.entradasLanzadas = entradasLanzadas;
		this.ponchesLanzados = ponchesLanzados;
		this.strikes = strikes;
		this.bolas = bolas;
		this.juegosJugados = juegosJugados;
		this.carrerasPermitidas = carrerasPermitidas;
	}

	public int getEntradasLanzadas() {
		return entradasLanzadas;
	}

	public void setEntradasLanzadas(int entradasLanzadas) {
		this.entradasLanzadas = entradasLanzadas;
	}

	public int getPonchesLanzados() {
		return ponchesLanzados;
	}

	public void setPonchesLanzados(int ponchesLanzados) {
		this.ponchesLanzados = ponchesLanzados;
	}

	public int getStrikes() {
		return strikes;
	}

	public void setStrikes(int strikes) {
		this.strikes = strikes;
	}

	public int getBolas() {
		return bolas;
	}

	public void setBolas(int bolas) {
		this.bolas = bolas;
	}

	public int getJuegosJugados() {
		return juegosJugados;
	}

	public void setJuegosJugados(int juegosJugados) {
		this.juegosJugados = juegosJugados;
	}

	public int getCarrerasPermitidas() {
		return carrerasPermitidas;
	}

	public void setCarrerasPermitidas(int carrerasPermitidas) {
		this.carrerasPermitidas = carrerasPermitidas;
	}
	 public void actualizarEntradasLanzadas(int entradas) {
	        this.entradasLanzadas += entradas;
	    }

	    public void actualizarPonches(int ponches) {
	        this.ponchesLanzados += ponches;
	    }

	    public void actualizarStrikes(int strikes) {
	        this.strikes += strikes;
	    }

	    public void actualizarBolas(int bolas) {
	        this.bolas += bolas;
	    }

	    public void actualizarJuegosJugados(int juegos) {
	        this.juegosJugados += juegos;
	    }

	    public void actualizarCarrerasPermitidas(int carreras) {
	        this.carrerasPermitidas += carreras;
	    }

	    // Método de cálculo de efectividad
	    public float calcEfectividad() {
	        if (entradasLanzadas == 0) {
	            return 0.0f; // Evitar división por cero
	        }
	        return (carrerasPermitidas * 9.0f) / entradasLanzadas;
	    }
	
}
