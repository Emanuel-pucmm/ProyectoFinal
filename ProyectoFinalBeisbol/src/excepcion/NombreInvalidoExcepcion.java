package excepcion;

public class NombreInvalidoExcepcion extends Exception {
	 public NombreInvalidoExcepcion() {
	        super("El nombre no puede contener números o caracteres especiales");
	    }

}
