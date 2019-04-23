package co.edu.javeriana.bittus.fitt.Vista.PopUps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDuracion;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;
import co.edu.javeriana.bittus.fitt.Vista.InformacionEjercicioActivity;
import pl.droidsonroids.gif.GifImageView;

public class PopEditarEjercicioSesionDuracion extends Activity {

    private ImageButton informacionEjercicioB;
    private ImageButton aceptarButton;
    private ImageButton cancelarButton;

    private TextView nombreEjercicioT;
    private TextView musculosEjercicioT;
    private TextView tipoEjercicioT;
    private TextView dificultadEjercicioT;
    private GifImageView gifImageView;

    private EjercicioDuracion ejercicio;

    private EditText duracionT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_agregar_ejercicio_sesion_duracion);

        aceptarButton = (ImageButton)findViewById(R.id.buttonAceptar3);
        cancelarButton = (ImageButton)findViewById(R.id.buttonCancelar3);
        informacionEjercicioB = (ImageButton)findViewById(R.id.buttonInformacionEjercicio2);
        duracionT = (EditText)findViewById(R.id.editTextDuracion2);

        nombreEjercicioT = (TextView)findViewById(R.id.textNombreEjercicio2);
        musculosEjercicioT = (TextView)findViewById(R.id.textMusculosEjercicio2);
        tipoEjercicioT = (TextView)findViewById(R.id.textTipoEjercicio2);
        dificultadEjercicioT = (TextView)findViewById(R.id.textDificultadEjercicio2);
        gifImageView = (GifImageView) findViewById(R.id.gifEjercicio2);

        Bundle bundle = this.getIntent().getExtras();

        ejercicio = (EjercicioDuracion) bundle.getSerializable("ejercicioSesion");

        nombreEjercicioT.setText(ejercicio.getEjercicio().getNombre());
        musculosEjercicioT.setText(ejercicio.getEjercicio().getMusculos());
        tipoEjercicioT.setText(ejercicio.getEjercicio().getTipo());
        dificultadEjercicioT.setText(ejercicio.getEjercicio().getDificultad());
        duracionT.setText(Integer.toString(ejercicio.getDuracion()));
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
        Intent inten = new Intent(PopEditarEjercicioSesionDuracion.this, InformacionEjercicioActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("ejercicio",ejercicio);
        inten.putExtras(bundle);

        startActivity(inten);

    }

        private void editarEjercicioSesion() {
        String sDuracion = duracionT.getText().toString();

        if(sDuracion.isEmpty()){
                duracionT.setError("Campo obligatorio");
        }else {
            int duracion = Integer.parseInt(sDuracion);

            ejercicio.setDuracion(duracion);

            Intent intent = this.getIntent();
            Bundle bundle = intent.getExtras();
            bundle.putSerializable("ejercicioSesion",ejercicio);
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
