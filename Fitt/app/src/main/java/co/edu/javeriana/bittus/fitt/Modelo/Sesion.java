package co.edu.javeriana.bittus.fitt.Modelo;

import android.widget.Button;

public class Sesion {

    private String nombre;
    private String duracion;



    public Sesion(String nombre, String duracion) {
        this.nombre = nombre;
        this.duracion = duracion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }




}
