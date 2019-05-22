package co.edu.javeriana.bittus.fitt.Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Parque implements Serializable {

    private String nombreParqueFire;
    private String nombre;
    private List<String> imagenes;
    private  List<Reseña> reseñas;
    private float calificación;
    private double longitud;
    private double latitud;

    public Parque(String nombre ,float calificación, double latitud, double longitud) {
        this.nombre = nombre;
        imagenes = new ArrayList<String>();
        reseñas = new ArrayList<Reseña>();
        this.calificación = calificación;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Parque() {

    }

    public String getNombreParqueFire() {
        return nombreParqueFire;
    }

    public void setNombreParqueFire(String nombreParqueFire) {
        this.nombreParqueFire = nombreParqueFire;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<String> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<String> imagenes) {
        this.imagenes = imagenes;
    }

    public List<Reseña> getReseñas() {
        return reseñas;
    }

    public void setReseñas(List<Reseña> reseñas) {
        this.reseñas = reseñas;
    }

    public float getCalificación() {
        return calificación;
    }

    public void setCalificación(float calificación) {
        this.calificación = calificación;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }
}
