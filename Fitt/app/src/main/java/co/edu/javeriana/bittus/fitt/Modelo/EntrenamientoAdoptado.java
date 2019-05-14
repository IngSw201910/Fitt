package co.edu.javeriana.bittus.fitt.Modelo;

public class EntrenamientoAdoptado {
    private Entrenamiento entrenamiento;
    private String dia;
    private String hora;

    public EntrenamientoAdoptado() {
    }

    public EntrenamientoAdoptado(Entrenamiento entrenamiento, String dia, String hora) {
        this.entrenamiento = entrenamiento;
        this.dia = dia;
        this.hora = hora;
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
