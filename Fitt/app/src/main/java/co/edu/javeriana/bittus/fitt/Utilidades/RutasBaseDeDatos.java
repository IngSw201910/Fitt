package co.edu.javeriana.bittus.fitt.Utilidades;


/*
Nombre: RutasBaseDeDatos
Descripción: Clase que contiene las rutas con la información de la base de datos.
 */

public final class RutasBaseDeDatos {

    private static String rutaEjercicios = "/ejercicios/";
    private static String rutaRutinas = "/rutinas/";
    private static String rutaUsuarios ="/Usuarios/";
    private static String rutaEntrenadores ="/Entrenadores/";

    public static String getRutaEjercicios() {
        return rutaEjercicios;
    }
    public static String getRutaRutinas() {
        return rutaRutinas;
    }
    public static String getRutaUsuarios(){ return rutaUsuarios; }
    public static String getRutaEntrenadores(){ return rutaEntrenadores; }
}
