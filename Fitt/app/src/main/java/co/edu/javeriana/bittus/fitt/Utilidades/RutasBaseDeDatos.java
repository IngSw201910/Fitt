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



    private static String rutaUsuarios ="/Usuarios/";
    private static String rutaEntrenadores ="/Entrenadores/";
    private static String rutaParques ="/Parques/";


    public static final String RUTA_USUARIOS = "/usuarios/";

    public static final String RUTA_FOTO_USUARIOS = "/FotoUsuarios/";

    public static final String RUTA_FOTO_TITULOS = "/FotoTitulos/";


    public static String getRutaEjercicios() {
        return rutaEjercicios;
    }
    public static String getRutaEntrenamientos() {
        return rutaEntrenamientos;
    }


    public static String getRutaEntrenamientosPublicos(){return rutaEntrenamientosPublicos;}
    public static String getRutaEntrenamientosAdoptados(){return rutaEntrenamientosAdoptados;}

    public static String getRutaUsuarios(){ return rutaUsuarios; }

    public static String getRutaEntrenadores(){ return rutaEntrenadores; }
    public static String getRutaParques() { return rutaParques; }
}
