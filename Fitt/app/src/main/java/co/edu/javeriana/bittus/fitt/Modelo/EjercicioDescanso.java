package co.edu.javeriana.bittus.fitt.Modelo;

import java.io.Serializable;

public class EjercicioDescanso extends EjercicioEntrenamiento implements Serializable {

    private int duracion;


    public EjercicioDescanso(Ejercicio ejercicio, int duracion) {
        super(ejercicio);
        this.duracion = duracion;
    }

    public EjercicioDescanso() {
        super();
    }


    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
}
