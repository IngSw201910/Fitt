package co.edu.javeriana.bittus.fitt.Vista;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import co.edu.javeriana.bittus.fitt.Modelo.EjercicioRepeticiones;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioTiempo;
import co.edu.javeriana.bittus.fitt.Modelo.Ejercicio;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;
import pl.droidsonroids.gif.GifImageView;

public class EjercicioTiempoFragment extends Fragment {
    private FragmentEjercicioRepeticionesListener listener;

    private TextView descripcion;
    private TextView titulo;

    private TextView series;
    private Chronometer chrono;


    private ProgressBar seriesPB;
    private ProgressBar segundosPB;

    private Thread manejadorEjercicio;


    private EjercicioTiempo ejercicioTiempo;
    private int serie;
    private int segundo = 0;

    private GifImageView gifImageView;

    private long timeWhenStopped = 0;


    private int estado = COMENZANDO;

    private static final int COMENZANDO = 0;
    private static final int CORRIENDO = 1;
    private static final int PAUSADO = 2;

    private boolean ejercicioTerminado = false;


    Activity realizarEntrenaActivity;

    public interface FragmentEjercicioRepeticionesListener {
        void mostrarSiguienteEjercicio();

        boolean estaDandoInstrucciones();

        void darInstrucciones(String texto);

        void iniciarMusicaEjercicioRepeticionOTiempo();

        void detenerMusica();


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ejercicioTiempo = (EjercicioTiempo) getArguments().getSerializable("ejercicioTiempo");
        serie = getArguments().getInt("serie");
        return inflater.inflate(R.layout.fragment_ejercicio_tiempo, null, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View v = getView();
        descripcion = (TextView) v.findViewById(R.id.descripcionEjercicioTiempo);
        descripcion.setMovementMethod(new ScrollingMovementMethod());
        titulo = (TextView) v.findViewById(R.id.textView25);

        series = v.findViewById(R.id.tvSerieTiempo);
        chrono = v.findViewById(R.id.tiempoSerie);


        seriesPB = v.findViewById(R.id.pbSeriesTiempo);
        segundosPB = v.findViewById(R.id.pbTiempo);


        seriesPB.setMax(ejercicioTiempo.getSeries());
        seriesPB.setProgress(serie);
        series.setText(serie + "/" + ejercicioTiempo.getSeries());


        segundosPB.setMax(ejercicioTiempo.getTiempo());
        segundosPB.setProgress(0);


        gifImageView = v.findViewById(R.id.gifEjercicio5);

        descripcion.setText(ejercicioTiempo.getEjercicio().getDescripción());
        titulo.setText(ejercicioTiempo.getEjercicio().getNombre());
        Utils.descargarYMostrarGIF(ejercicioTiempo.getEjercicio().getRutaGIF(), gifImageView);



        inicializarCronometro(v);
        iniciarEjercicio();


    }

    private void iniciarEjercicio() {

        manejadorEjercicio = new Thread() {
            public void run() {
                try {
                    if (estado == COMENZANDO) {
                        while (listener.estaDandoInstrucciones()) ;
                        String instruccionInicial = "";
                        instruccionInicial += ejercicioTiempo.getEjercicio().getNombre();


                        if (serie == 1) {
                            instruccionInicial += ". " + ejercicioTiempo.getEjercicio().getDescripción();

                        }
                        instruccionInicial += ". " + "Serie " + serie + "," + ejercicioTiempo.getTiempo() + "segundos";
                        listener.darInstrucciones(instruccionInicial);
                        while (listener.estaDandoInstrucciones()) ;
                        estado = CORRIENDO;

                    }
                    listener.iniciarMusicaEjercicioRepeticionOTiempo();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            chrono.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                            chrono.start();
                        }
                    });
                } catch (Exception e) {
                    //Se termino corto la ejecución del ejercicio
                    e.printStackTrace();
                }
            }
        };
        manejadorEjercicio.start();
    }

    private void inicializarCronometro(View v) {
        chrono = (Chronometer) v.findViewById(R.id.tiempoSerie);
        chrono.stop();
        chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                try {
                    long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                    long segundos = time / 1000;
                    String t = segundos + " s";
                    segundo = (int) segundos;
                    chronometer.setText(t);

                    segundosPB.setProgress(segundo);


                    if (segundos != 0 && (ejercicioTiempo.getTiempo() / 2 == segundos || ((ejercicioTiempo.getTiempo() / 4) * 3) == segundos)) {
                        listener.darInstrucciones("Quedan " + (ejercicioTiempo.getTiempo() - segundo) + "segundos");
                    }

                    if (segundos >= ejercicioTiempo.getTiempo()) {
                        chrono.stop();
                        listener.detenerMusica();
                        listener.darInstrucciones("Fin de serie");
                        while (listener.estaDandoInstrucciones()) ;
                        listener.mostrarSiguienteEjercicio();

                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        chrono.setBase(SystemClock.elapsedRealtime());
        chrono.setText("0 s");

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentEjercicioRepeticionesListener) {
            listener = (FragmentEjercicioRepeticionesListener) context;
            Log.i("Si inicio el listener", "síiii");
        }
    }

    @Override
    public void onDetach() {
        listener.detenerMusica();
        if (chrono != null)
            chrono.stop();
        listener = null;
        super.onDetach();
    }

    public void pausar(){
        if (estado == CORRIENDO) {
            estado = PAUSADO;
            timeWhenStopped = (chrono.getBase() - SystemClock.elapsedRealtime());
            chrono.stop();
        }
    }

    public void reanudar(){
        if (estado == PAUSADO) {
            estado = CORRIENDO;
            chrono.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
            chrono.start();
        }
    }


}
