package excepcion;

public class UsuarioExistenteExcepcion extends Exception {
    private static final long serialVersionUID = 1L;

    public UsuarioExistenteExcepcion(String nombreUsuario) {
        super("El usuario '" + nombreUsuario + "' ya existe en el sistema");
    }
}
