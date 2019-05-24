package co.edu.javeriana.bittus.fitt.Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Entrenamiento implements Serializable {


    private int numDiasDescanso;
    private String descripcion;
    private String dificultad;
    private boolean publica;
    private String nombre;
    private List<EjercicioEntrenamiento> ejercicioEntrenamientoList;
    private List<Reseña> reseñas;
    private int duracion;



    public Entrenamiento(int numDiasDescanso, String descripcion, String dificultad, boolean publica, String nombre, int duracion) {

        this.numDiasDescanso = numDiasDescanso;
        this.descripcion = descripcion;
        this.dificultad = dificultad;
        this.publica = publica;
        this.nombre = nombre;
        this.duracion = duracion;

        ejercicioEntrenamientoList = new ArrayList<EjercicioEntrenamiento>();
        reseñas = new ArrayList<Reseña>();
    }

    public List<Reseña> getReseñas() {
        return reseñas;
    }

    public void setReseñas(List<Reseña> reseñas) {
        this.reseñas = reseñas;
    }

    public Entrenamiento() {super();
        reseñas= new ArrayList<>();
        ejercicioEntrenamientoList = new ArrayList<>();
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public List<EjercicioEntrenamiento> getEjercicioEntrenamientoList() {
        return ejercicioEntrenamientoList;
    }

    public void setEjercicioEntrenamientoList(List<EjercicioEntrenamiento> ejercicioEntrenamientoList) {
        this.ejercicioEntrenamientoList = ejercicioEntrenamientoList;
    }

    public float calcularRating() {
        float rating = 0f;
        for (int i = 0; i < reseñas.size(); i++) {
            rating += reseñas.get(i).getCalificacion();
        }
        if (reseñas.size() == 0) {
            rating = 3f;
        } else {
            rating = rating / reseñas.size();
        }
        return  rating;
    }

    public int getNumDiasDescanso() {
        return numDiasDescanso;
    }

    public void setNumDiasDescanso(int numDiasDescanso) {
        this.numDiasDescanso = numDiasDescanso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public boolean isPublica() {
        return publica;
    }

    public void setPublica(boolean publica) {
        this.publica = publica;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


}
