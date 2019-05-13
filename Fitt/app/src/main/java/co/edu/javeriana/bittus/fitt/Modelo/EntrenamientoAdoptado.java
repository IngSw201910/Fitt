package co.edu.javeriana.bittus.fitt.Modelo;

public class EntrenamientoAdoptado {
    private Entrenamiento entrenamiento;
    private String dia;
    private String hora;

    public EntrenamientoAdoptado() {
    }

    public Entrenamiento getEntrenamiento() {
        return entrenamiento;
    }

    public void setEntrenamiento(Entrenamiento entrenamiento) {
        this.entrenamiento = entrenamiento;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
