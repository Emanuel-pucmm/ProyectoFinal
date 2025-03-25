package logico;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Jugador {
    // Atributos
    protected String nombre;
    protected LocalDate fechaNacimiento;
    protected String edad;
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

    // Getters y Setters
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
        if (lesion != null) {
            this.listaLesiones.add(lesion);
        }
    }
    public abstract String getTipoJugador();
}
