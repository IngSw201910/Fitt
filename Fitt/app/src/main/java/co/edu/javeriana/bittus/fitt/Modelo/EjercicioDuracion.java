package co.edu.javeriana.bittus.fitt.Modelo;

public class EjercicioDuracion extends EjercicioSesion {

    private int duracion;


    public EjercicioDuracion(Ejercicio ejercicio, int duracion) {
        super(ejercicio);
        this.duracion = duracion;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
}
