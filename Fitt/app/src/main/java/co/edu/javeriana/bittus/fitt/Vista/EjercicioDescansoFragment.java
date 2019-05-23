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

import java.util.concurrent.TimeUnit;

import co.edu.javeriana.bittus.fitt.Modelo.EjercicioRepeticiones;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioTiempo;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDescanso;
import co.edu.javeriana.bittus.fitt.Modelo.Ejercicio;
import co.edu.javeriana.bittus.fitt.R;

public class EjercicioDescansoFragment extends Fragment {
    private FragmentEjercicioRepeticionesListener listener;



    private TextView series;
    private Chronometer chrono;


    private ProgressBar segundosDescansoPB;


    private EjercicioDescanso ejercicioDescanso;
    private int segundo = 0;

    private long timeWhenStopped = 0;

    Thread manejadorEjercicio;


    private int estado;

    private static final int COMENZANDO = 0;
    private static final int CORRIENDO = 1;
    private static final int PAUSADO = 2;


    Activity realizarEntrenaActivity;

    public interface FragmentEjercicioRepeticionesListener {
        void mostrarSiguienteEjercicio();

        boolean estaDandoInstrucciones();

        void darInstrucciones(String texto);

        void iniciarMusicaEjercicioDescanso();

        void detenerMusica();

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ejercicioDescanso = (EjercicioDescanso) getArguments().getSerializable("ejercicioDescanso");
        return inflater.inflate(R.layout.fragment_ejercicio_descanso, null, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View v = getView();


        segundosDescansoPB = v.findViewById(R.id.pbSegundosDescanso);


        segundosDescansoPB.setMax(ejercicioDescanso.getDuracion());
        segundosDescansoPB.setProgress(0);

        inicializarCronometro(v);


        iniciarEjercicio();


    }

    private void iniciarEjercicio() {

        manejadorEjercicio = new Thread() {
            public void run() {
                try {
                    if (estado == COMENZANDO) {
                        while (listener.estaDandoInstrucciones()) ;

                        listener.darInstrucciones("Descanso.   " + ejercicioDescanso.getDuracion() + "segundos");
                        while (listener.estaDandoInstrucciones()) ;
                        estado = CORRIENDO;

                    }
                    listener.iniciarMusicaEjercicioDescanso();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            chrono.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                            chrono.start();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        manejadorEjercicio.start();
    }

    private void inicializarCronometro(View v) {
        chrono = (Chronometer) v.findViewById(R.id.duracionDescanso);
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

                    segundosDescansoPB.setProgress(segundo);


                    if (segundos != 0 && (ejercicioDescanso.getDuracion() / 2 == segundos || ((ejercicioDescanso.getDuracion() / 4) * 3) == segundos)) {
                        listener.darInstrucciones("Quedan " + (ejercicioDescanso.getDuracion() - segundo) + "segundos de descanso");
                    }

                    if (segundos >= ejercicioDescanso.getDuracion()) {
                        chrono.stop();
                        listener.detenerMusica();
                        listener.darInstrucciones("Fin del descanso");
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
        }
    }

    @Override
    public void onDetach() {
        listener.detenerMusica();
        listener = null;
        if (chrono != null)
            chrono.stop();
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
