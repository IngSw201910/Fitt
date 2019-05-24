package co.edu.javeriana.bittus.fitt.Modelo;

import java.io.Serializable;

public class LocalizacionUsuario implements Serializable {

    private String UID;
    private double latitud;
    private double longitud;
    private Usuario usuario;

    public LocalizacionUsuario(String UID, double latitud, double longitud, Usuario usuario) {
        this.UID = UID;
        this.latitud = latitud;
        this.longitud = longitud;
        this.usuario = usuario;
    }

    public LocalizacionUsuario() {

    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
