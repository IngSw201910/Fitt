package co.edu.javeriana.bittus.fitt.Modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Usuario {

    private List<Rutina> rutinaList;
    private String nombre;
    private String correo;
    private String contraseña;
    private String direccionFoto;
    private Date fechaNacimiento;
    private String sexo;
    private float altura;
    private float peso;



    public Usuario(String nombre, String correo, String contraseña, String direccionFoto, Date fechaNacimiento, String sexo, float altura, float peso) {
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.direccionFoto = direccionFoto;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.altura = altura;
        this.peso = peso;
        rutinaList = new ArrayList<Rutina>();
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

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
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

    public List<Rutina> getRutinaList() {
        return rutinaList;
    }

    public void setRutinaList(List<Rutina> rutinaList) {
        this.rutinaList = rutinaList;
    }
}
