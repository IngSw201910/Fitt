package co.edu.javeriana.bittus.fitt.Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Entrenador extends Usuario implements Serializable {
    private String descripccion;
    private String nombreTitulo;
    private String fotoTitulo;
    private String porqueElegirme;
    private List<String> clientes;



    public Entrenador(String nombre, String correo, String direccionFoto, Date fechaNacimiento, String sexo, float altura, float peso, String descripccion, String nombreTitulo, String fotoTitulo, String porqueElegirme, String tipo) {
        super(nombre, correo, direccionFoto, fechaNacimiento, sexo, altura, peso,tipo);
        this.descripccion = descripccion;
        this.nombreTitulo = nombreTitulo;
        this.fotoTitulo = fotoTitulo;
        this.porqueElegirme = porqueElegirme;
        clientes = new ArrayList<String>();
    }



    public float getDistanciaRecorrida() {
        return distanciaRecorrida;
    }

    public void setDistanciaRecorrida(float distanciaRecorrida) {
        this.distanciaRecorrida = distanciaRecorrida;
    }

    public float getPasosDados() {
        return pasosDados;
    }

    public void setPasosDados(float pasosDados) {
        this.pasosDados = pasosDados;
    }

    public float getCaloriasQuemadas() {
        return caloriasQuemadas;
    }

    public void setCaloriasQuemadas(float caloriasQuemadas) {
        this.caloriasQuemadas = caloriasQuemadas;
    }

    public Entrenador(){
        super();
        clientes = new ArrayList<String>();
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Entrenamiento> getEntrenamientoList() {
        return entrenamientoList;
    }

    public void setEntrenamientoList(List<Entrenamiento> entrenamientoList) {
        this.entrenamientoList = entrenamientoList;
    }



    public  List<String> getSeguidosList(){
        return seguidosList;
    }

    public void setSeguidosList(List<String> seguidosList){
        this.seguidosList=seguidosList;
    }

    public List<String> getSeguidoresList(){
        return seguidoresList;
    }

    public void setSeguidoresList(List<String> seguidoresList){
        this.seguidoresList=seguidoresList;
    }

    public boolean getPrivacidad(){
        return privacidad;
    }

    public void setPrivacidad(boolean privacidad) {
        this.privacidad = privacidad;
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


    public List<String> getClientes() {
        return clientes;
    }

    public void setClientes(List<String> clientes) {
        this.clientes = clientes;
    }



}
