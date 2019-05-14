package co.edu.javeriana.bittus.fitt.Modelo;

import java.io.Serializable;

public class EjercicioDistancia extends EjercicioEntrenamiento implements Serializable {

    private int distancia;

    public EjercicioDistancia(Ejercicio ejercicio, int distancia) {
        super(ejercicio);
        this.distancia = distancia;
    }
    public EjercicioDistancia() {
        super();
    }



    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }
}
