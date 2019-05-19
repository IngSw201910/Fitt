package co.edu.javeriana.bittus.fitt.Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Usuario implements Serializable {

    private List<Entrenamiento> entrenamientoList;
    private String nombre;
    private String id;
    private String correo;
    private String direccionFoto;
    private Date fechaNacimiento;
    private String sexo;
    private float altura;
    private float peso;
    private List<Usuario> seguidosList;
    private List<Usuario> seguidoresList;
    private boolean privacidad;



    public Usuario(String correo, String id, String nombre){
        this.nombre = nombre;
        this.correo = correo;
        this.id = id;
    }


    public Usuario(String nombre, String correo, String direccionFoto, Date fechaNacimiento, String sexo, float altura, float peso) {
        this.nombre = nombre;
        this.correo = correo;
        this.direccionFoto = direccionFoto;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.altura = altura;
        this.peso = peso;
        this.privacidad =false;
        entrenamientoList = new ArrayList<Entrenamiento>();
        seguidosList = new ArrayList<Usuario>();
        seguidoresList = new ArrayList<Usuario>();
    }


    public Usuario() {
        entrenamientoList = new ArrayList<Entrenamiento>();
        seguidosList = new ArrayList<Usuario>();
        seguidoresList = new ArrayList<Usuario>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public  List<Usuario> getSeguidosList(){
        return seguidosList;
    }

    public void setSeguidosList(List<Usuario> seguidosList){
        this.seguidosList=seguidosList;
    }

    public List<Usuario> getSeguidoresList(){
        return seguidoresList;
    }

    public void setSeguidoresList(List<Usuario> seguidoresList){
        this.seguidoresList=seguidoresList;
    }

    public boolean getPrivacidad(){
        return privacidad;
    }

    public void setPrivacidad(boolean privacidad) {
        this.privacidad = privacidad;
    }

    //usuario cambiado
}
