package excepcion;

public class NombreInvalidoExcepcion extends Exception {
	 public NombreInvalidoExcepcion() {
	        super("El nombre no puede contener n�meros o caracteres especiales");
	    }

}
