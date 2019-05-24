package co.edu.javeriana.bittus.fitt.Utilidades;


/*
Nombre: RutasBaseDeDatos
Descripción: Clase que contiene las rutas con la información de la base de datos.
 */

public final class RutasBaseDeDatos {

    private static String rutaEjercicios = "/ejercicios/";

    public static String RUTA_ENTRENAMIENTOS = "/entrenamientos/";
    private static String rutaEntrenamientosPublicos = "/entrenamientos_publicos/";
    private static String rutaEntrenamientosAdoptados = "/entrenamientos_adoptados/";



    private static String rutaEntrenadores ="/Entrenadores/";
    private static String rutaParques ="/Parques/";

    private static final String RUTA_USUARIOS_LOCALIZACION = "/LocalizacionUsuarios/";


    public static final String RUTA_USUARIOS = "/usuarios/";

    public static final String RUTA_FOTO_USUARIOS = "/FotoUsuarios/";

    public static final String RUTA_FOTO_TITULOS = "/FotoTitulos/";

    public static final String RUTA_FOTO_PARQUE = "/FotoParque/";


    public static String getRutaEjercicios() {
        return rutaEjercicios;
    }



    public static String getRutaEntrenamientosPublicos(){return rutaEntrenamientosPublicos;}
    public static String getRutaEntrenamientosAdoptados(){return rutaEntrenamientosAdoptados;}


    public static String getRutaEntrenadores(){ return rutaEntrenadores; }
    public static String getRutaParques() { return rutaParques; }

    public static String getRutaUsuariosLocalizacion() {
        return RUTA_USUARIOS_LOCALIZACION;
    }

}
