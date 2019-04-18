package co.edu.javeriana.bittus.fitt.Modelo;

public abstract class EjercicioSesión {


    
    private Ejercicio ejercicio;

    public EjercicioSesión(Ejercicio ejercicio) {
        this.ejercicio = ejercicio;
    }

    public Ejercicio getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Ejercicio ejercicio) {
        this.ejercicio = ejercicio;
    }
}
