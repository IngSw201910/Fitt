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

    private TextView descripcion;

    private TextView series;
    private Chronometer chrono;


    private ProgressBar segundosDescansoPB;


    private EjercicioDescanso ejercicioDescanso;
    private int segundo = 0;

    private long timeWhenStopped = 0;




    private int estado;

    private static final int CORRIENDO = 0;
    private static final int PAUSADO = 1;





    Activity realizarEntrenaActivity;

    public interface FragmentEjercicioRepeticionesListener {
        void mostrarSiguienteEjercicio();

        void darInstrucciones(String texto);

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
        chrono.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
        chrono.start();

    }

    private void inicializarCronometro(View v){
        chrono  = (Chronometer) v.findViewById(R.id.duracionDescanso);
        chrono.stop();
        chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                long segundos = time/1000;
                String t = segundos + " s";
                segundo = (int) segundos;
                chronometer.setText(t);

                segundosDescansoPB.setProgress(segundo);


                if (segundos%5 == 0 ){
                    listener.darInstrucciones("Quedan "+ (ejercicioDescanso.getDuracion() - segundo) + "segundos de descanso");
                }

                if (segundos >= ejercicioDescanso.getDuracion()){
                    listener.mostrarSiguienteEjercicio();
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
        listener = null;
        super.onDetach();
    }


}
