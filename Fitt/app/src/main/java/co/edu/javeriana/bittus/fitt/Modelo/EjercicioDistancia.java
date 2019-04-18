package co.edu.javeriana.bittus.fitt.Modelo;

public class EjercicioDistancia extends EjercicioSesion {

    private int distancia;

    public EjercicioDistancia(Ejercicio ejercicio, int distancia) {
        super(ejercicio);
        this.distancia = distancia;
    }


    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }
}
