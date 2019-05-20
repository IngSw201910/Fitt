package co.edu.javeriana.bittus.fitt.Modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Entrenador extends Usuario implements Serializable {
    private String descripccion;
    private String nombreTitulo;
    private String fotoTitulo;
    private String porqueElegirme;

    public Entrenador(String nombre, String correo, String direccionFoto, Date fechaNacimiento, String sexo, float altura, float peso, String descripccion, String nombreTitulo, String fotoTitulo, String porqueElegirme) {
        super(nombre, correo, direccionFoto, fechaNacimiento, sexo, altura, peso);
        this.descripccion = descripccion;
        this.nombreTitulo = nombreTitulo;
        this.fotoTitulo = fotoTitulo;
        this.porqueElegirme = porqueElegirme;
    }
    public Entrenador(){
        super();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }


    public String getDireccionFoto() {
        return direccionFoto;
    }

    public void setDireccionFoto(String direccionFoto) {
        this.direccionFoto = direccionFoto;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public List<Entrenamiento> getEntrenamientoList() {
        return entrenamientoList;
    }

    public void setEntrenamientoList(List<Entrenamiento> entrenamientoList) {
        this.entrenamientoList = entrenamientoList;
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
