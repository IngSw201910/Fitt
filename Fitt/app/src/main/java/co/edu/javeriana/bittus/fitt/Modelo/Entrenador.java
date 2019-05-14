package co.edu.javeriana.bittus.fitt.Modelo;

import java.io.Serializable;
import java.util.Date;

public class Entrenador extends Usuario implements Serializable {
    private String descripccion;
    private String nombreTitulo;
    private String fotoTitulo;
    private String porqueElegirme;

    public Entrenador(String nombre, String correo, String contraseña, String direccionFoto, Date fechaNacimiento, String sexo, float altura, float peso) {
        super(nombre, correo, direccionFoto, fechaNacimiento, sexo, altura, peso);
    }

    public String getDescripccion() {
        return descripccion;
    }

    public void setDescripccion(String descripccion) {
        this.descripccion = descripccion;
    }

    public String getNombreTitulo() {
        return nombreTitulo;
    }

    public void setNombreTitulo(String nombreTitulo) {
        this.nombreTitulo = nombreTitulo;
    }

    public String getFotoTitulo() {
        return fotoTitulo;
    }

    public void setFotoTitulo(String fotoTitulo) {
        this.fotoTitulo = fotoTitulo;
    }

    public String getPorqueElegirme() {
        return porqueElegirme;
    }

    public void setPorqueElegirme(String porqueElegirme) {
        this.porqueElegirme = porqueElegirme;
    }
}
