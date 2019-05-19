package co.edu.javeriana.bittus.fitt.Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Parque implements Serializable {

    private String latYLong;
    private List<String> imagenes;
    private  List<ReseñaParque> reseñas;
    private float calificación;
    private double longitud;
    private double latitud;

    public Parque(String latYLong, float calificación, double latitud, double longitud) {
        this.latYLong = latYLong;
        imagenes = new ArrayList<String>();
        reseñas = new ArrayList<ReseñaParque>();
        this.calificación = calificación;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Parque(float calificación) {

    }

    public String getLatYLong() {
        return latYLong;
    }

    public void setLatYLong(String latYLong) {
        this.latYLong = latYLong;
    }

    public List<String> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<String> imagenes) {
        this.imagenes = imagenes;
    }

    public List<ReseñaParque> getReseñas() {
        return reseñas;
    }

    public void setReseñas(List<ReseñaParque> reseñas) {
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
