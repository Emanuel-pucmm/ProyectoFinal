package excepcion;

public class NumeroNegativoExcepcion extends Exception {
	public NumeroNegativoExcepcion(String campo) {
        super("El campo " + campo + " no puede ser negativo");
    }

}
