package logico;

import java.time.LocalDate;
import java.util.ArrayList;
import java.io.Serializable;

public abstract class Jugador implements Serializable {
	
	private static final long serialVersionUID = 1L;
    protected String nombre;
    protected LocalDate fechaNacimiento;
    protected String edad;  // manejado como String
    protected String nacionalidad;
    protected String manoDominante;
    protected LocalDate fechaDebut;
    protected double altura;
    protected ArrayList<Lesion> listaLesiones;

    // Constructor
    public Jugador(String nombre, LocalDate fechaNacimiento, String edad,
                   String nacionalidad, String manoDominante, LocalDate fechaDebut,
                   double altura) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
        this.nacionalidad = nacionalidad;
        this.manoDominante = manoDominante;
        this.fechaDebut = fechaDebut;
        this.altura = altura;
        this.listaLesiones = new ArrayList<>();
    }

    // Getters / Setters
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEdad() {
        return edad;
    }
    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getManoDominante() {
        return manoDominante;
    }
    public void setManoDominante(String manoDominante) {
        this.manoDominante = manoDominante;
    }

    public LocalDate getFechaDebut() {
        return fechaDebut;
    }
    public void setFechaDebut(LocalDate fechaDebut) {
        this.fechaDebut = fechaDebut;
    }

    public double getAltura() {
        return altura;
    }
    public void setAltura(double altura) {
        this.altura = altura;
    }

    public ArrayList<Lesion> getListaLesiones() {
        return listaLesiones;
    }

    // Método para registrar una lesión
    public void registrarLesion(Lesion lesion) {
        if (listaLesiones == null) {
            listaLesiones = new ArrayList<>();
        }
        listaLesiones.add(lesion);
    }

    // Método abstracto
    public abstract String getTipoJugador();

    // =========================
    // SOBRESCRIBIMOS toString()
    // =========================
    @Override
    public String toString() {
        // Para que el ComboBox (o cualquier impresión) muestre el nombre real del jugador
        return this.nombre;
    }
}

