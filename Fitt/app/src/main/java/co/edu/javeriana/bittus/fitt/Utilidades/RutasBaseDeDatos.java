package co.edu.javeriana.bittus.fitt.Utilidades;


/*
Nombre: RutasBaseDeDatos
Descripción: Clase que contiene las rutas con la información de la base de datos.
 */

public final class RutasBaseDeDatos {

    private static String rutaEjercicios = "/ejercicios/";

    private static String rutaEntrenamientos = "/entrenamientos/";
    private static String rutaEntrenamientosPublicos = "/entrenamientos_publicos/";
    private static String rutaEntrenamientosAdoptados = "/entrenamientos_adoptados/";


    private static String rutaRutinas = "/rutinas/";
    private static String rutaUsuarios ="/Usuarios/";


    public static String getRutaEjercicios() {
        return rutaEjercicios;
    }
    public static String getRutaEntrenamientos() {
        return rutaEntrenamientos;
    }

    public static String getRutaEntrenamientosPublicos(){return rutaEntrenamientosPublicos;}
    public static String getRutaEntrenamientosAdoptados(){return rutaEntrenamientosAdoptados;}

    public static String getRutaUsuarios(){ return rutaUsuarios; }

}
