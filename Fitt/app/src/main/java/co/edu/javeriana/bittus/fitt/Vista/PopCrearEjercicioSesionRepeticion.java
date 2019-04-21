package co.edu.javeriana.bittus.fitt.Vista;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import co.edu.javeriana.bittus.fitt.Modelo.Ejercicio;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioRepeticiones;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;
import pl.droidsonroids.gif.GifImageView;

public class PopCrearEjercicioSesionRepeticion extends Activity {

    private ImageButton informacionEjercicioB;
    private ImageButton aceptarButton;
    private ImageButton cancelarButton;

    private TextView nombreEjercicioT;
    private TextView musculosEjercicioT;
    private TextView tipoEjercicioT;
    private TextView dificultadEjercicioT;
    private GifImageView gifImageView;

    private  Ejercicio ejercicio;

    private EditText repeticionesT;
    private EditText seriesT;
    private EditText descansoT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_agregar_ejercicio_sesion_repeticion);

        aceptarButton = findViewById(R.id.buttonAceptar4);
        cancelarButton = findViewById(R.id.buttonCancelar4);
        informacionEjercicioB = findViewById(R.id.buttonInformacionEjercicio3);
        repeticionesT = findViewById(R.id.editTextRepeticiones);
        seriesT = findViewById(R.id.editTextSeries);
        descansoT = findViewById(R.id.editTextDescansos);

        nombreEjercicioT = findViewById(R.id.textNombreEjercicio3);
        musculosEjercicioT = findViewById(R.id.textMusculosEjercicio3);
        tipoEjercicioT = findViewById(R.id.textTipoEjercicio3);
        dificultadEjercicioT = findViewById(R.id.textDificultadEjercicio3);
        gifImageView = findViewById(R.id.gifEjercicio3);

        Bundle bundle = this.getIntent().getExtras();

        ejercicio = (Ejercicio) bundle.getSerializable("ejercicio");

        nombreEjercicioT.setText(ejercicio.getNombre());
        musculosEjercicioT.setText(ejercicio.getMusculos());
        tipoEjercicioT.setText(ejercicio.getTipo());
        dificultadEjercicioT.setText(ejercicio.getDificultad());
        Utils.descargarYMostrarGIF(ejercicio.getRutaGIF(),gifImageView);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width/1.1), (int) (height/1.5));

        informacionEjercicioB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PopCrearEjercicioSesionRepeticion.this, InformacionEjercicioActivity.class));
            }
        });

        aceptarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearEjercicioSesion();
            }
        });

        cancelarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
    }
    private void crearEjercicioSesion() {
        String sRepeticiones = repeticionesT.getText().toString();
        String sSeries = seriesT.getText().toString();
        String sDescansos = descansoT.getText().toString();

        int repeticiones = Integer.parseInt(sRepeticiones);
        int series = Integer.parseInt(sSeries);
        int descansos = Integer.parseInt(sDescansos);

        EjercicioRepeticiones ejercicioRepeticiones = new EjercicioRepeticiones(ejercicio, repeticiones, series,descansos);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        bundle.putSerializable("ejercicioRepeticion", ejercicioRepeticiones);
        intent.putExtras(bundle);


        if (getParent() == null) {
            setResult(Activity.RESULT_OK, intent);
        } else {
            getParent().setResult(Activity.RESULT_OK, intent);
        }
        finish();
    }
}
