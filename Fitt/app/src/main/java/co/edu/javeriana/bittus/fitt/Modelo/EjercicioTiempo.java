package co.edu.javeriana.bittus.fitt.Modelo;

import java.io.Serializable;

public class EjercicioTiempo extends EjercicioEntrenamiento implements Serializable {


    private int tiempo;
    private int series;
    private int descanso;

    public EjercicioTiempo(Ejercicio ejercicio, int tiempo, int series, int descanso) {
        super(ejercicio);
        this.tiempo = tiempo;
        this.series = series;
        this.descanso = descanso;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getDescanso() {
        return descanso;
    }

    public void setDescanso(int descanso) {
        this.descanso = descanso;
    }
}
