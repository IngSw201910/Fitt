package co.edu.javeriana.bittus.fitt.Modelo;
import java.io.Serializable;

public class Entrenador implements Serializable {
    private int id;
    private String nombre;
    private int cantidadClientes;
    private boolean titulo;
    private int añosEx;
    private String acercaDe;

    public Entrenador(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){ return nombre;}
}
