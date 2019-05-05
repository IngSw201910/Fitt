package co.edu.javeriana.bittus.fitt.Vista.PopUps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import co.edu.javeriana.bittus.fitt.Modelo.EjercicioRepeticiones;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;
import co.edu.javeriana.bittus.fitt.Vista.InformacionEjercicioActivity;
import pl.droidsonroids.gif.GifImageView;

public class PopEditarEjercicioEntrenamientoRepeticion extends Activity {

    private ImageButton informacionEjercicioB;
    private ImageButton aceptarButton;
    private ImageButton cancelarButton;

    private TextView nombreEjercicioT;
    private TextView musculosEjercicioT;
    private TextView tipoEjercicioT;
    private TextView dificultadEjercicioT;
    private GifImageView gifImageView;

    private  EjercicioRepeticiones ejercicio;

    private EditText repeticionesT;
    private EditText seriesT;
    private EditText descansoT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_agregar_ejercicio_sesion_repeticion);

        aceptarButton = (ImageButton)findViewById(R.id.buttonAceptar4);
        cancelarButton = (ImageButton)findViewById(R.id.buttonCancelar4);
        informacionEjercicioB = (ImageButton)findViewById(R.id.buttonInformacionEjercicio3);
        repeticionesT = (EditText)findViewById(R.id.editTextRepeticiones);
        seriesT = (EditText)findViewById(R.id.editTextSeries);
        descansoT = (EditText)findViewById(R.id.editTextDescansos);

        nombreEjercicioT = findViewById(R.id.textNombreEjercicio3);
        musculosEjercicioT = findViewById(R.id.textMusculosEjercicio3);
        tipoEjercicioT = findViewById(R.id.textTipoEjercicio3);
        dificultadEjercicioT = findViewById(R.id.textDificultadEjercicio3);
        gifImageView = findViewById(R.id.gifEjercicio3);

        Bundle bundle = this.getIntent().getExtras();

        ejercicio = (EjercicioRepeticiones) bundle.getSerializable("ejercicioEntrenamiento");


        repeticionesT.setText(Integer.toString(ejercicio.getRepeticiones()));
        seriesT.setText(Integer.toString(ejercicio.getSeries()));
        descansoT.setText(Integer.toString(ejercicio.getDescanso()));

        nombreEjercicioT.setText(ejercicio.getEjercicio().getNombre());
        musculosEjercicioT.setText(ejercicio.getEjercicio().getMusculos());
        tipoEjercicioT.setText(ejercicio.getEjercicio().getTipo());
        dificultadEjercicioT.setText(ejercicio.getEjercicio().getDificultad());
        Utils.descargarYMostrarGIF(ejercicio.getEjercicio().getRutaGIF(),gifImageView);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width/1.1), (int) (height/1.5));

        informacionEjercicioB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verInfo();
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

    private void verInfo() {
        Intent inten = new Intent(PopEditarEjercicioEntrenamientoRepeticion.this, InformacionEjercicioActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("ejercicio",ejercicio.getEjercicio());
        inten.putExtras(bundle);

        startActivity(inten);

    }

    private void crearEjercicioSesion() {
        String sRepeticiones = repeticionesT.getText().toString();
        String sSeries = seriesT.getText().toString();
        String sDescansos = descansoT.getText().toString();

        boolean completado = true;

        if(sRepeticiones.isEmpty()){
            repeticionesT.setError("Campo obligatorio");
            completado = false;
        }
        if(sSeries.isEmpty()){
            seriesT.setError("Campo obligatorio");
            completado = false;
        }
        if(sDescansos.isEmpty()){
            descansoT.setError("Campo obligatorio");
            completado = false;
        }
        if(completado){
            int repeticiones = Integer.parseInt(sRepeticiones);
            int series = Integer.parseInt(sSeries);
            int descansos = Integer.parseInt(sDescansos);


            ejercicio.setDescanso(descansos);
            ejercicio.setRepeticiones(repeticiones);
            ejercicio.setSeries(series);

            Intent intent = this.getIntent();
            Bundle bundle = intent.getExtras();
            bundle.putSerializable("ejercicioEntrenamiento",ejercicio);
            intent.putExtras(bundle);

            if (getParent() == null) {
                setResult(Activity.RESULT_OK, intent);
            } else {
                getParent().setResult(Activity.RESULT_OK, intent);
            }
            finish();
        }


    }
}
