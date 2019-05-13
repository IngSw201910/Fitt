package co.edu.javeriana.bittus.fitt.Vista;

import android.media.MediaPlayer;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class RealizarEntrenamientoActivity extends AppCompatActivity implements EjercicioRepeticionesFragment.FragmentEjercicioRepeticionesListener, EjercicioTiempoFragment.FragmentEjercicioRepeticionesListener, EjercicioDescansoFragment.FragmentEjercicioRepeticionesListener, EntrenamientoTerminadoFragment.FragmentEjercicioRepeticionesListener {

    private Chronometer chrono;

    private TextView iniciarPausarReaundarTV;

    private ImageButton iniciaroReanudarPausarB;
    private ImageButton siguienteEjercicioB;
    private ImageButton finalizarB;

    private ImageButton sonidoAyudaB;
    private ImageButton sonidoMusicaB;

    private int estado;
    private static final int NOINICIADO = 0;
    private static final int CORRIENDO = 1;
    private static final int PAUSADO = 2;


    private int estadoSonidoAyuda = ACTIVADO;
    private int estadoSonidoMusica = ACTIVADO;
    private static final int ACTIVADO = 0;
    private static final int DESACTIVADO = 1;

    private long timeWhenStopped = 0;

    private TextToSpeech textToSpeech;
    private MediaPlayer reproductor;

    private Entrenamiento entrenamiento;
    private EjercicioEntrenamiento siguienteEjercicio;


    private Fragment ejercicioActual;


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
        ejercicioGrande.setNombre("Flexiones de pecho");
        ejercicioGrande.setDescripción("Coloque las dos manos junto al suelo y flexione los codos");
        ejercicioGrande.setRutaGIF("/imagenes/ejercicios/prueba.gif");
        ejercicioGrande.setTipo("Repetición");


        EjercicioRepeticiones ejercicioRepeticiones = new EjercicioRepeticiones(ejercicioGrande, 15, 2, 10);


        /*ejerciciosEntrenamiento.add(ejercicioRepeticiones);
        ejerciciosEntrenamiento.add(ejercicioRepeticiones);
        ejerciciosEntrenamiento.add(ejercicioRepeticiones);*/

        EjercicioTiempo ejercicioTiempo = new EjercicioTiempo(ejercicioGrande, 20, 2, 20);


        ejerciciosEntrenamiento.add(ejercicioRepeticiones);
        ejerciciosEntrenamiento.add(ejercicioTiempo);


        entrenamiento.setEjercicioEntrenamientoList(ejerciciosEntrenamiento);
        //FIN MOCK ENTRENAMIENTO


        iniciaroReanudarPausarB = findViewById(R.id.btnIniciarPausarReanudar);
        iniciaroReanudarPausarB.setFocusableInTouchMode(false);


        iniciarPausarReaundarTV = findViewById(R.id.iniciarPausarReaunarTV);

        siguienteEjercicioB = findViewById(R.id.btnSiguienteEjercicio);
        finalizarB = findViewById(R.id.btnFinalizarEntrenamiento);

        sonidoAyudaB = findViewById(R.id.btnSonidoNarrador);
        sonidoMusicaB = findViewById(R.id.btnSonidoMusica);

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
                    } else {

                        textToSpeech.setPitch(0.6f);
                        textToSpeech.setSpeechRate(1.0f);

                        darInstrucciones("Hola, ¿preparado para entrenar?");
                        while (estaDandoInstrucciones()) ;
                    }
                    iniciaroReanudarPausarB.setFocusableInTouchMode(true);
                }
            }
        });


        estado = NOINICIADO;

        iniciaroReanudarPausarB.setOnClickListener(new View.OnClickListener() {
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


        sonidoAyudaB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (estadoSonidoAyuda) {
                    case ACTIVADO:
                        estadoSonidoAyuda = DESACTIVADO;
                        textToSpeech.stop();
                        cambiarBotonSonidoAyuda();
                        break;
                    case DESACTIVADO:
                        estadoSonidoAyuda = ACTIVADO;
                        cambiarBotonSonidoAyuda();
                        break;
                }
            }
        });

        sonidoMusicaB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (estadoSonidoMusica) {
                    case ACTIVADO:
                        estadoSonidoMusica = DESACTIVADO;
                        reproductor.setVolume(0.0f, 0.0f);
                        cambiarBotonSonidoMusica();
                        break;
                    case DESACTIVADO:
                        estadoSonidoMusica = ACTIVADO;
                        reproductor.setVolume(1.0f, 1.0f);
                        cambiarBotonSonidoMusica();
                        break;
                }
            }
        });

        siguienteEjercicioB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textToSpeech != null)
                    textToSpeech.stop();
                if (reproductor != null)
                    reproductor.stop();



                mostrarSiguienteEjercicio();
                if (estado == PAUSADO)
                    pausarEntrenamiento();

            }
        });

    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.ejercicioActual, fragment)
                    .commitAllowingStateLoss();
            ejercicioActual = fragment;
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
        if (textToSpeech != null){
            textToSpeech.stop();
        }
        if (reproductor != null){
            reproductor.pause();
        }

        if (ejercicioActual != null){
            if (ejercicioActual instanceof EjercicioDescansoFragment)
                ((EjercicioDescansoFragment) ejercicioActual).pausar();
            if (ejercicioActual instanceof EjercicioTiempoFragment)
                ((EjercicioTiempoFragment) ejercicioActual).pausar();
            if (ejercicioActual instanceof EjercicioRepeticionesFragment)
                ((EjercicioRepeticionesFragment) ejercicioActual).pausar();
        }


        cambiarBoton();
    }

    private void reanudarEntrenamiento() {
        chrono.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
        chrono.start();
        if (reproductor != null)
            reproductor.start();

        if (ejercicioActual != null){
            if (ejercicioActual instanceof EjercicioDescansoFragment)
                ((EjercicioDescansoFragment) ejercicioActual).reanudar();
            if (ejercicioActual instanceof EjercicioTiempoFragment)
                ((EjercicioTiempoFragment) ejercicioActual).reanudar();
            if (ejercicioActual instanceof EjercicioRepeticionesFragment)
                ((EjercicioRepeticionesFragment) ejercicioActual).reanudar();
        }

        cambiarBoton();
    }

    private void cambiarBoton() {
        if (estado == CORRIENDO) {
            iniciaroReanudarPausarB.setImageResource(R.drawable.pausar);
            iniciaroReanudarPausarB.setColorFilter(getResources().getColor(R.color.verdeFitt));

            iniciarPausarReaundarTV.setText("Pausar");
        }
        if (estado == PAUSADO) {
            iniciaroReanudarPausarB.setImageResource(R.drawable.reanudar);
            iniciaroReanudarPausarB.setColorFilter(getResources().getColor(R.color.verdeFitt));

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
        if (estadoSonidoAyuda == ACTIVADO) {
            textToSpeech.speak(texto, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        if (reproductor != null) {
            reproductor.stop();
        }
    }


    @Override
    public void mostrarSiguienteEjercicio() {
        Fragment fragment = null;
        Bundle args = new Bundle();

        if (numSiguienteEjercicio < entrenamiento.getEjercicioEntrenamientoList().size()) {
            siguienteEjercicio = entrenamiento.getEjercicioEntrenamientoList().get(numSiguienteEjercicio);


            if (siguienteEjercicio instanceof EjercicioRepeticiones) {
                if (sigueDescanso) {
                    fragment = new EjercicioDescansoFragment();
                    args.putSerializable("ejercicioDescanso", (EjercicioDescanso) new EjercicioDescanso(null, ((EjercicioRepeticiones) siguienteEjercicio).getDescanso()));
                    sigueDescanso = false;
                    if (serieEjercicio > ((EjercicioRepeticiones) siguienteEjercicio).getSeries()) {
                        numSiguienteEjercicio++;
                        serieEjercicio = 1;
                    }
                } else {
                    if (serieEjercicio <= ((EjercicioRepeticiones) siguienteEjercicio).getSeries()) {
                        fragment = new EjercicioRepeticionesFragment();
                        args.putSerializable("ejercicioRepeticiones", (EjercicioRepeticiones) siguienteEjercicio);
                        args.putInt("serie", serieEjercicio);
                        serieEjercicio++;
                        sigueDescanso = true;
                    }
                }
            } else {
                if (siguienteEjercicio instanceof EjercicioTiempo) {
                    if (sigueDescanso) {
                        fragment = new EjercicioDescansoFragment();
                        args.putSerializable("ejercicioDescanso", (EjercicioDescanso) new EjercicioDescanso(null, ((EjercicioTiempo) siguienteEjercicio).getDescanso()));
                        sigueDescanso = false;
                        if (serieEjercicio > ((EjercicioTiempo) siguienteEjercicio).getSeries()) {
                            numSiguienteEjercicio++;
                            serieEjercicio = 1;
                        }
                    } else {

                        if (serieEjercicio <= ((EjercicioTiempo) siguienteEjercicio).getSeries()) {
                            fragment = new EjercicioTiempoFragment();
                            args.putSerializable("ejercicioTiempo", (EjercicioTiempo) siguienteEjercicio);
                            args.putInt("serie", serieEjercicio);
                            serieEjercicio++;
                            sigueDescanso = true;
                        }

                    }

                } else {
                    if (siguienteEjercicio instanceof EjercicioDescanso) {
                        fragment = new EjercicioDescansoFragment();
                        args.putSerializable("ejercicioDescanso", (EjercicioDescanso) siguienteEjercicio);
                        numSiguienteEjercicio++;
                    }
                }


            }


            if (fragment != null) {
                fragment.setArguments(args);
                loadFragment(fragment);
            }

        } else {
            finalizarEntrenamiento();
        }

    }

    public void cambiarBotonSonidoAyuda() {
        if (estadoSonidoAyuda == ACTIVADO) {
            sonidoAyudaB.setImageResource(R.drawable.sonido_activado);
            sonidoAyudaB.setColorFilter(getResources().getColor(R.color.verdeFitt));
        }
        if (estadoSonidoAyuda == DESACTIVADO) {
            sonidoAyudaB.setImageResource(R.drawable.sonido_desactivado);
            sonidoAyudaB.setColorFilter(getResources().getColor(R.color.verdeFitt));
        }
    }

    public void cambiarBotonSonidoMusica() {
        if (estadoSonidoMusica == ACTIVADO) {
            sonidoMusicaB.setImageResource(R.drawable.sonido_activado);
            sonidoMusicaB.setColorFilter(getResources().getColor(R.color.verdeFitt));
        }
        if (estadoSonidoMusica == DESACTIVADO) {
            sonidoMusicaB.setImageResource(R.drawable.sonido_desactivado);
            sonidoMusicaB.setColorFilter(getResources().getColor(R.color.verdeFitt));
        }
    }

    public void finalizarEntrenamiento() {

        chrono.stop();
        Toast.makeText(RealizarEntrenamientoActivity.this, "Entrenamiento terminado!", Toast.LENGTH_LONG).show();
        Toast.makeText(RealizarEntrenamientoActivity.this, "Los datos de este entrenamiento serán almacenados", Toast.LENGTH_LONG).show();
        Fragment fragment = new EntrenamientoTerminadoFragment();
        loadFragment(fragment);
    }

    public boolean estaDandoInstrucciones() {
        if (textToSpeech.isSpeaking())
            return true;
        return false;
    }

    public void iniciarMusicaEjercicioRepeticionOTiempo() {
        reproductor = MediaPlayer.create(this, R.raw.training_music);
        reproductor.setLooping(true);
        reproductor.start();
    }

    public void iniciarMusicaEjercicioDescanso() {
        reproductor = MediaPlayer.create(this, R.raw.sonido_descanso);
        reproductor.setLooping(true);
        reproductor.start();
    }

    public void detenerMusica() {
        if (reproductor != null) {
            reproductor.stop();
            reproductor = null;
        }

    }
}
