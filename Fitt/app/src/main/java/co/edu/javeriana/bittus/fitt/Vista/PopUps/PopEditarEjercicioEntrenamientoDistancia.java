package co.edu.javeriana.bittus.fitt.Vista.PopUps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDistancia;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;
import co.edu.javeriana.bittus.fitt.Vista.InformacionEjercicioActivity;
import pl.droidsonroids.gif.GifImageView;

public class PopEditarEjercicioEntrenamientoDistancia extends Activity {

    private ImageButton informacionEjercicioB;
    private ImageButton aceptarButton;
    private ImageButton cancelarButton;

    private TextView nombreEjercicioT;
    private TextView musculosEjercicioT;
    private TextView tipoEjercicioT;
    private TextView dificultadEjercicioT;
    private GifImageView gifImageView;
    private EjercicioDistancia ejercicio;

    private EditText distanciaT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_agregar_ejercicio_sesion_distancia);

        informacionEjercicioB = (ImageButton)findViewById(R.id.imageButtonInformacion);

        aceptarButton = (ImageButton)findViewById(R.id.imageButtonAceptar);
        cancelarButton = (ImageButton)findViewById(R.id.imageButtonCancelar);
        distanciaT = (EditText)findViewById(R.id.editText5Distanciasd);

        nombreEjercicioT = (TextView) findViewById(R.id.textNombreEjercicio);
        musculosEjercicioT = (TextView) findViewById(R.id.textMusculosEjercicio);
        tipoEjercicioT =(TextView)  findViewById(R.id.textTipoEjercicio);
        dificultadEjercicioT = (TextView) findViewById(R.id.textDificultadEjercicio);
        gifImageView = (GifImageView) findViewById(R.id.gifEjercicio);

        Bundle bundle = this.getIntent().getExtras();

        ejercicio = (EjercicioDistancia) bundle.getSerializable("ejercicioEntrenamiento");

        nombreEjercicioT.setText(ejercicio.getEjercicio().getNombre());
        musculosEjercicioT.setText(ejercicio.getEjercicio().getMusculos());
        tipoEjercicioT.setText(ejercicio.getEjercicio().getTipo());
        dificultadEjercicioT.setText(ejercicio.getEjercicio().getDificultad());
        distanciaT.setText(Integer.toString(ejercicio.getDistancia()));
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
                editarEjercicioSesion();
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
        Intent inten = new Intent(PopEditarEjercicioEntrenamientoDistancia.this, InformacionEjercicioActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("ejercicio",ejercicio);
        inten.putExtras(bundle);

        startActivity(inten);

    }
    private void editarEjercicioSesion() {
        String sDistancia = distanciaT.getText().toString();
        if(sDistancia.isEmpty()){
            distanciaT.setError("Campo obligatorio");

        }else{
            int distancia = Integer.parseInt(sDistancia);

            ejercicio.setDistancia(distancia);

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
