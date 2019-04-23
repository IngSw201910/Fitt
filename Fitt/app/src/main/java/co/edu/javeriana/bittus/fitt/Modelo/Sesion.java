package co.edu.javeriana.bittus.fitt.Modelo;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sesion implements Serializable {

    private String nombre;
    private int duracion;



    private List<EjercicioSesion> ejercicioSesionList;

    public Sesion(String nombre, int duracion) {
        this.nombre = nombre;
        this.duracion = duracion;
        ejercicioSesionList = new ArrayList<EjercicioSesion>();
    }
    public Sesion() {super();};

    public List<EjercicioSesion> getEjercicioSesionList() {
        return ejercicioSesionList;
    }

    public void setEjercicioSesionList(List<EjercicioSesion> ejercicioSesionList) {
        this.ejercicioSesionList = ejercicioSesionList;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }




}
