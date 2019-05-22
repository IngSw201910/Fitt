package co.edu.javeriana.bittus.fitt.Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Parque implements Serializable {

    private String nombreParqueFire;
    private List<String> imagenes;
    private  List<ReseñaParque> reseñas;
    private float calificación;
    private double longitud;
    private double latitud;

    public Parque(String nombreParqueFire, float calificación, double latitud, double longitud) {
        this.nombreParqueFire = nombreParqueFire;
        imagenes = new ArrayList<String>();
        reseñas = new ArrayList<ReseñaParque>();
        this.calificación = calificación;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Parque() {

    }

    public String getNombreParque() {
        return nombreParqueFire;
    }

    public void setNombreParque(String nombreParque) {
        this.nombreParqueFire = nombreParque;
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
