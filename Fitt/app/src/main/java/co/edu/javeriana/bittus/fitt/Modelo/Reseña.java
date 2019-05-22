package co.edu.javeriana.bittus.fitt.Modelo;

import java.io.Serializable;

public class Reseña implements Serializable {

    private Usuario usuario;
    private String reseña;
    private String fecha;
    private float calificacion;

    public Reseña(Usuario usuario, String reseña, String fecha, float calificacion) {
        this.usuario = usuario;
        this.reseña = reseña;
        this.fecha = fecha;
        this.calificacion = calificacion;
    }

    public Reseña() {

    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getReseña() {
        return reseña;
    }

    public void setReseña(String reseña) {
        this.reseña = reseña;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }
}
