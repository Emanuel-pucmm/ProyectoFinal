package excepcion;

public class FechaInvalidaExcepcion extends Exception {
	 public FechaInvalidaExcepcion() {
	        super("La fecha ingresada no es válida (formato dd/MM/yyyy)");
	    }
	    
	    public FechaInvalidaExcepcion(String mensaje) {
	        super(mensaje);
	    }

}
