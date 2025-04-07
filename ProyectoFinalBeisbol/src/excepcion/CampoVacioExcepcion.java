package excepcion;

public class CampoVacioExcepcion extends Exception {
	public CampoVacioExcepcion(String campo) {
        super("El campo " + campo + " no puede estar vacío");
    }

}
