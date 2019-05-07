package co.edu.javeriana.bittus.fitt.Modelo;

//Notas
//Encontrar una forma de obtener la fecha por firebase

public class Reseña {
    private int id;
    private int referencia;
    private int catAsociada;
    private String comentario;
    private String fecha;
    private int calificacion;

    public Reseña(){ super(); }

    public Reseña(int id, int referencia, int catAsociada, String comentario, int calificacion){
        this.id = id;
        this.referencia = referencia;
        this.catAsociada = catAsociada;
        this.comentario = comentario;
        this.calificacion = calificacion;
        this.fecha = "01/01/2019";
    }

    public int getIdReseña(){ return id;}
    public int getReferencia(){ return referencia;}
    public int getCatAsociada(){ return catAsociada;}
    public String getComentario(){ return comentario;}
    public String getFecha(){ return fecha;}
    public int getCalificacion(){ return calificacion;}
}
