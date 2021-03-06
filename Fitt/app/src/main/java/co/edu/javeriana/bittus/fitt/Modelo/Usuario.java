package co.edu.javeriana.bittus.fitt.Modelo;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@IgnoreExtraProperties
public class Usuario implements Serializable {



    protected List<Entrenamiento> entrenamientoList;
    protected String nombre;
    protected String idEntrenador = "default";
    protected String id;
    protected String correo;
    protected String direccionFoto;
    protected Date fechaNacimiento;
    protected String sexo;
    protected float altura;
    protected float peso;
    protected List<String> seguidosList;
    protected List<String> seguidoresList;
    protected boolean privacidad;
    protected String tipo;
    protected float distanciaRecorrida;
    protected float pasosDados;
    protected float caloriasQuemadas;





    public Usuario(String correo, String id, String nombre) {
        this.correo = correo;
        this.nombre = nombre;
        this.id = id;

    }
    public Usuario(String correo, String nombre) {

        this.nombre = nombre;
        this.correo = correo;
    }





    public Usuario(String nombre, String correo, String direccionFoto, Date fechaNacimiento, String sexo, float altura, float peso, String tipo) {

        this.nombre = nombre;
        this.correo = correo;
        this.direccionFoto = direccionFoto;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.altura = altura;
        this.peso = peso;
        this.privacidad =false;
        this.tipo=tipo;
        entrenamientoList = new ArrayList<Entrenamiento>();
        seguidosList = new ArrayList<String>();
        seguidoresList = new ArrayList<String>();
        distanciaRecorrida = 0;
        pasosDados = 0;
        caloriasQuemadas = 0;
    }


    public Usuario() {
        entrenamientoList = new ArrayList<Entrenamiento>();
        seguidosList = new ArrayList<String>();
        seguidoresList = new ArrayList<String>();
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
    public String getTipo() {
        return tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean validarSeguido(String uidUsuario) {
        for(String usuario: seguidosList){
            if(usuario.compareTo(uidUsuario)==0){
                return true;
            }
        }
        return false;

    }

    public String getIdEntrenador() {
        return idEntrenador;
    }

    public void setIdEntrenador(String idEntrenador) {
        this.idEntrenador = idEntrenador;
    }
    //usuario cambiado
}
