package excepcion;

public class FechaInvalidaExcepcion extends Exception {
	 public FechaInvalidaExcepcion() {
	        super("La fecha ingresada no es v�lida (formato dd/MM/yyyy)");
	    }
	    
	    public FechaInvalidaExcepcion(String mensaje) {
	        super(mensaje);
	    }

}
