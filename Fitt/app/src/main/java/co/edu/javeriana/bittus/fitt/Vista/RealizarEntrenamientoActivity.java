package co.edu.javeriana.bittus.fitt.Vista;

import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import co.edu.javeriana.bittus.fitt.Modelo.Ejercicio;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioEntrenamiento;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioRepeticiones;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioTiempo;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDescanso;

import co.edu.javeriana.bittus.fitt.Modelo.Entrenamiento;
import co.edu.javeriana.bittus.fitt.R;

public class RealizarEntrenamientoActivity extends AppCompatActivity implements EjercicioRepeticionesFragment.FragmentEjercicioRepeticionesListener, EjercicioTiempoFragment.FragmentEjercicioRepeticionesListener, EjercicioDescansoFragment.FragmentEjercicioRepeticionesListener {

    private Chronometer chrono;

    private TextView iniciarPausarReaundarTV;

    private ImageButton iniciaroReaundarPausarB;
    private ImageButton siguienteEjercicioB;
    private ImageButton finalizarB;

    private ImageButton sonidoAyudaB;
    private ImageButton sonidoMusicaB;

    private int estado;
    private static final int NOINICIADO = 0;
    private static final int CORRIENDO = 1;
    private static final int PAUSADO = 2;

    private long timeWhenStopped = 0;

    private TextToSpeech textToSpeech;

    private Entrenamiento entrenamiento;
    private EjercicioEntrenamiento siguienteEjercicio;


    private int numSiguienteEjercicio = 0;

    private int serieEjercicio = 1;

    private boolean sigueDescanso = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_entrenamiento);

        inicializarCronometro();

        //MOCK ENTRENAMIENTO:
        entrenamiento = new Entrenamiento();
        entrenamiento.setDescripcion("");
        List<EjercicioEntrenamiento> ejerciciosEntrenamiento = new ArrayList<EjercicioEntrenamiento>();

        //EJERCICIO 1:
        Ejercicio ejercicioGrande = new Ejercicio();
        ejercicioGrande.setDescripción("Coloque las dos manos junto al suelo y flexione los codos");
        ejercicioGrande.setRutaGIF("/imagenes/ejercicios/prueba.gif");
        ejercicioGrande.setTipo("Repetición");


        //EjercicioRepeticiones ejercicioRepeticiones = new EjercicioRepeticiones(ejercicioGrande, 30, 4, 2);


        /*ejerciciosEntrenamiento.add(ejercicioRepeticiones);
        ejerciciosEntrenamiento.add(ejercicioRepeticiones);
        ejerciciosEntrenamiento.add(ejercicioRepeticiones);*/

        EjercicioTiempo ejercicioTiempo = new EjercicioTiempo(20, 5, 10);
        ejerciciosEntrenamiento.add(ejercicioTiempo);

        entrenamiento.setEjercicioEntrenamientoList(ejerciciosEntrenamiento);
        //FIN MOCK ENTRENAMIENTO


        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    Locale locSpanish = new Locale("spa", "MEX");


                    int result = textToSpeech.setLanguage(Locale.ENGLISH);
                    //int result = textToSpeech.setLanguage(locSpanish);
                    //Locale loc = new Locale ("spa", "ESP");
                    //int result = textToSpeech.setLanguage(loc);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(RealizarEntrenamientoActivity.this, "Lenguaje no soportado en su celular.", Toast.LENGTH_LONG).show();
                    }

                    textToSpeech.setPitch(0.6f);
                    textToSpeech.setSpeechRate(1.0f);

                    darInstrucciones("Hello! Welcome to your daily training");
                }
            }
        });


        iniciarPausarReaundarTV = findViewById(R.id.iniciarPausarReaunarTV);
        iniciaroReaundarPausarB = findViewById(R.id.btnIniciarPausarReanudar);
        siguienteEjercicioB = findViewById(R.id.btnSiguienteEjercicio);
        finalizarB = findViewById(R.id.btnFinalizarEntrenamiento);

        sonidoAyudaB = findViewById(R.id.btnSonidoNarrador);
        sonidoMusicaB = findViewById(R.id.btnSonidoMusica);


        /*Fragment fragment = new EjercicioTiempoFragment();

        fragment = new EjercicioRepeticionesFragment();
        loadFragment(fragment);*/

        estado = NOINICIADO;

        iniciaroReaundarPausarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (estado) {
                    case NOINICIADO:
                        estado = CORRIENDO;
                        iniciarEntrenamiento();
                        break;

                    case CORRIENDO:
                        estado = PAUSADO;
                        pausarEntrenamiento();
                        break;

                    case PAUSADO:
                        estado = CORRIENDO;
                        reanudarEntrenamiento();
                        break;
                }
            }
        });

    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.ejercicioActual, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void iniciarEntrenamiento() {
        chrono.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
        chrono.start();


        mostrarSiguienteEjercicio();

        cambiarBoton();

    }


    private void pausarEntrenamiento() {
        timeWhenStopped = (chrono.getBase() - SystemClock.elapsedRealtime());
        chrono.stop();
        cambiarBoton();
    }

    private void reanudarEntrenamiento() {
        chrono.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
        chrono.start();
        cambiarBoton();
    }

    private void cambiarBoton() {
        if (estado == CORRIENDO) {
            iniciaroReaundarPausarB.setImageResource(R.drawable.pausar);
            iniciaroReaundarPausarB.setColorFilter(getResources().getColor(R.color.verdeFitt));

            iniciarPausarReaundarTV.setText("Pausar");
        }
        if (estado == PAUSADO) {
            iniciaroReaundarPausarB.setImageResource(R.drawable.reanudar);
            iniciaroReaundarPausarB.setColorFilter(getResources().getColor(R.color.verdeFitt));

            iniciarPausarReaundarTV.setText("Reanudar");
        }
    }


    private void inicializarCronometro() {
        chrono = (Chronometer) findViewById(R.id.tiempoEntrenamiento);
        chrono.stop();
        chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                int h = (int) (time / 3600000);
                int m = (int) (time - h * 3600000) / 60000;
                int s = (int) (time - h * 3600000 - m * 60000) / 1000;
                String t = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
                chronometer.setText(t);
            }
        });
        chrono.setBase(SystemClock.elapsedRealtime());
        chrono.setText("00:00:00");
    }

    public void darInstrucciones(String texto) {
        textToSpeech.speak(texto, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }


    @Override
    public void mostrarSiguienteEjercicio() {


        siguienteEjercicio = entrenamiento.getEjercicioEntrenamientoList().get(numSiguienteEjercicio);

        Fragment fragment = null;
        Bundle args = new Bundle();


        if (siguienteEjercicio instanceof EjercicioRepeticiones) {
            if (sigueDescanso) {
                fragment = new EjercicioDescansoFragment();
                args.putSerializable("ejercicioDescanso", (EjercicioDescanso) new EjercicioDescanso(null, ((EjercicioRepeticiones) siguienteEjercicio).getDescanso()));
                sigueDescanso = false;
            } else {
                if (serieEjercicio <= ((EjercicioRepeticiones) siguienteEjercicio).getSeries()) {
                    fragment = new EjercicioRepeticionesFragment();
                    args.putSerializable("ejercicioRepeticiones", (EjercicioRepeticiones) siguienteEjercicio);
                    args.putInt("serie", serieEjercicio);
                    serieEjercicio++;
                }
                //si ya acabo las series
                else {
                    serieEjercicio = 1;
                    numSiguienteEjercicio++;
                }
                sigueDescanso = true;
            }
        } else {
            if (siguienteEjercicio instanceof EjercicioTiempo) {
                if (sigueDescanso) {
                    fragment = new EjercicioDescansoFragment();
                    args.putSerializable("ejercicioDescanso", (EjercicioDescanso) new EjercicioDescanso(null, ((EjercicioTiempo) siguienteEjercicio).getDescanso()));
                    sigueDescanso = false;
                }
                else {

                    if (serieEjercicio <= ((EjercicioTiempo) siguienteEjercicio).getSeries()) {
                        fragment = new EjercicioTiempoFragment();
                        args.putSerializable("ejercicioTiempo", (EjercicioTiempo) siguienteEjercicio);
                        args.putInt("serie", serieEjercicio);
                        serieEjercicio++;
                    } else {
                        serieEjercicio = 1;
                        numSiguienteEjercicio++;
                    }
                    sigueDescanso = true;
                }

            }
            else{
                if (siguienteEjercicio instanceof EjercicioDescanso){
                    fragment = new EjercicioDescansoFragment();
                    args.putSerializable("ejercicioDescanso", (EjercicioDescanso) siguienteEjercicio);
                }
            }



        }


        if (fragment != null) {
            fragment.setArguments(args);
            loadFragment(fragment);
        } else {
            Toast.makeText(RealizarEntrenamientoActivity.this, "Entrenamiento terminado!", Toast.LENGTH_LONG).show();
        }

    }

}
