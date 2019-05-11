package co.edu.javeriana.bittus.fitt.Vista;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import co.edu.javeriana.bittus.fitt.Modelo.EjercicioRepeticiones;
import co.edu.javeriana.bittus.fitt.Modelo.Ejercicio;
import co.edu.javeriana.bittus.fitt.R;

public class EjercicioRepeticionesFragment extends Fragment {
    private FragmentEjercicioRepeticionesListener listener;

    private TextView descripcion;

    private TextView series;
    private TextView repeticiones;


    private ProgressBar seriesPB;
    private ProgressBar repeticionesPB;


    private EjercicioRepeticiones ejercicioRepeticiones;
    private int serie;
    private int repeticion = 0;


    private TextToSpeech textToSpeech;



    private static final int COMENZANDO = 0;
    private static final int CORRIENDO = 1;
    private static final int PAUSADO = 2;

    private int estado = COMENZANDO;

    private boolean ejercicioTerminado = false;

    Thread manejadorEjercicio;

    Activity realizarEntrenaActivity;

    public interface FragmentEjercicioRepeticionesListener {
        void mostrarSiguienteEjercicio();

        void iniciarMusicaEjercicioRepeticionOTiempo();
        void detenerMusica();

        void darInstrucciones(String texto);
        boolean estaDandoInstrucciones ();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ejercicioRepeticiones = (EjercicioRepeticiones) getArguments().getSerializable("ejercicioRepeticiones");
        serie = getArguments().getInt("serie");
        return inflater.inflate(R.layout.fragment_ejercicio_repeticiones, null, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View v = getView();
        TextView descripcion = (TextView) v.findViewById(R.id.descripcionEjercicioRepeticiones);
        descripcion.setMovementMethod(new ScrollingMovementMethod());

        series = v.findViewById(R.id.tvSerieRepeticiones);
        repeticiones = v.findViewById(R.id.tvRepeticiones);


        seriesPB = v.findViewById(R.id.pbSerieRepeticiones);
        repeticionesPB = v.findViewById(R.id.pbRepeticiones);


        seriesPB.setMax(ejercicioRepeticiones.getSeries());
        seriesPB.setProgress(serie);
        series.setText(serie + "/" + ejercicioRepeticiones.getSeries());


        repeticionesPB.setMax(ejercicioRepeticiones.getRepeticiones());
        repeticionesPB.setProgress(0);
        repeticiones.setText(repeticion + "/" + ejercicioRepeticiones.getRepeticiones());

        iniciarEjercicio();


    }

    private void iniciarEjercicio() {

        manejadorEjercicio = new Thread() {
            public void run() {
                if (estado == COMENZANDO){
                    while (listener.estaDandoInstrucciones());
                    String instruccionInicial = "";

                    instruccionInicial += ejercicioRepeticiones.getEjercicio().getNombre();
                    if (serie == 1 ){
                        instruccionInicial += ". " + ejercicioRepeticiones.getEjercicio().getDescripción();
                    }
                    instruccionInicial += ". "+ "Serie "+ serie +"," + ejercicioRepeticiones.getRepeticiones() + "repeticiones";

                    listener.darInstrucciones(instruccionInicial);
                    while (listener.estaDandoInstrucciones());

                    estado = CORRIENDO;

                }
                listener.iniciarMusicaEjercicioRepeticionOTiempo();
                while (repeticion < ejercicioRepeticiones.getRepeticiones()) {
                    if (estado == CORRIENDO) {
                        listener.darInstrucciones("1");
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            this.interrupt();
                            break;
                        }
                        listener.darInstrucciones("2");
                        repeticion++;
                        getActivity().runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                repeticionesPB.setProgress(repeticion);
                                repeticiones.setText(repeticion + "/" + ejercicioRepeticiones.getRepeticiones());
                            }
                        });


                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            this.interrupt();
                            break;
                        }

                    }

                }
                if (!this.isInterrupted()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listener.detenerMusica();
                            listener.darInstrucciones("Fin de serie");
                            while (listener.estaDandoInstrucciones());
                            listener.mostrarSiguienteEjercicio();
                        }
                    });
                }
            }
        };

        manejadorEjercicio.start();
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
        if (manejadorEjercicio != null)
            manejadorEjercicio.interrupt();

        listener = null;
        super.onDetach();


    }


}
