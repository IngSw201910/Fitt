package co.edu.javeriana.bittus.fitt.Utilidades;


/*
Nombre: RutasBaseDeDatos
Descripción: Clase que contiene las rutas con la información de la base de datos.
 */

public final class RutasBaseDeDatos {

    private static String rutaEjercicios = "/ejercicios/";
    private static String rutaEntrenamientos = "/entrenamientos/";
    private static String rutaEntrenamientosPublicos = "/entrenamientos_publicos/";

    public static String getRutaEjercicios() {
        return rutaEjercicios;
    }
    public static String getRutaEntrenamientos() {
        return rutaEntrenamientos;
    }
    public static String getRutaEntrenamientosPublicos(){return rutaEntrenamientosPublicos;}
}
