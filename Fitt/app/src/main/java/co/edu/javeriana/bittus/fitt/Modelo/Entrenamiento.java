package co.edu.javeriana.bittus.fitt.Modelo;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Entrenamiento implements Serializable {

    private String nombre;




    private List<EjercicioEntrenamiento> ejercicioEntrenamientoList;

    public Entrenamiento(String nombre) {
        this.nombre = nombre;

        ejercicioEntrenamientoList = new ArrayList<EjercicioEntrenamiento>();
    }
    public Entrenamiento() {super();};

    public List<EjercicioEntrenamiento> getEjercicioEntrenamientoList() {
        return ejercicioEntrenamientoList;
    }

    public void setEjercicioEntrenamientoList(List<EjercicioEntrenamiento> ejercicioEntrenamientoList) {
        this.ejercicioEntrenamientoList = ejercicioEntrenamientoList;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }





}
