package co.edu.javeriana.bittus.fitt.Utilidades;


/*
Nombre: RutasBaseDeDatos
Descripción: Clase que contiene las rutas con la información de la base de datos.
 */

public final class RutasBaseDeDatos {

    private static String rutaEjercicios = "/ejercicios/";
    private static String rutaRutinas = "/rutinas/";

    public static final String RUTA_USUARIOS = "/usuarios/";

    public static final String RUTA_FOTO_USUARIOS = "/FotoUsuarios/";

    public static final String RUTA_FOTO_TITULOS = "/FotoTitulos/";


    public static String getRutaEjercicios() {
        return rutaEjercicios;
    }
    public static String getRutaRutinas() {
        return rutaRutinas;
    }



}
