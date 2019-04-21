package co.edu.javeriana.bittus.fitt.Modelo;

import java.io.Serializable;

public class Ejercicio implements Serializable {

    private String nombre;
    private String musculos;
    private String tipo;
    private String dificultad;
    private int gif;
    private String descripción;
    private String rutaGIF;

    public Ejercicio(){
        super();
    };

    public Ejercicio(String nombre, String musculos, String tipo, String dificultad, int gif, String descripción) {
        this.nombre = nombre;
        this.musculos = musculos;
        this.tipo = tipo;
        this.dificultad = dificultad;
        this.gif = gif;
        this.descripción = descripción;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMusculos() {
        return musculos;
    }

    public void setMusculos(String musculos) {
        this.musculos = musculos;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public int getGif() {
        return gif;
    }

    public void setGif(int gif) {
        this.gif = gif;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    public String getRutaGIF() {
        return rutaGIF;
    }

    public void setRutaGIF(String rutaGIF) {
        this.rutaGIF = rutaGIF;
    }
}
