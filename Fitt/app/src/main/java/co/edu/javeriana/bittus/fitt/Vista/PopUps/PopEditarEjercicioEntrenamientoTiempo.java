package co.edu.javeriana.bittus.fitt.Vista.PopUps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import co.edu.javeriana.bittus.fitt.Modelo.Ejercicio;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDescanso;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioTiempo;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.StringsMiguel;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;
import co.edu.javeriana.bittus.fitt.Vista.InformacionEjercicioActivity;
import pl.droidsonroids.gif.GifImageView;

public class PopEditarEjercicioEntrenamientoTiempo extends Activity {

    private ImageButton imageButtonInformacionEjercicio;
    private ImageButton imageButtonAceptar;
    private ImageButton imageButtonCancelar;

    private TextView textViewNombreEjercicio;
    private TextView textViewMusculos;
    private TextView textViewTipo;
    private TextView textViewDificultad;
    private GifImageView gifImageView;

    private EjercicioTiempo ejercicio;

    private EditText editTextTiempo;
    private EditText editTextSeries;
    private EditText editTextDescanso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_agregar_ejercicio_sesion_tiempo);

        imageButtonAceptar = (ImageButton)findViewById(R.id.buttonAceptar3);
        imageButtonCancelar = (ImageButton)findViewById(R.id.buttonCancelar3);
        imageButtonInformacionEjercicio = (ImageButton)findViewById(R.id.buttonInformacionEjercicio2);


        textViewNombreEjercicio = (TextView)findViewById(R.id.textNombreEjercicio2);
        textViewMusculos = (TextView)findViewById(R.id.textMusculosEjercicio2);
        textViewTipo = (TextView)findViewById(R.id.textTipoEjercicio2);
        textViewDificultad = (TextView)findViewById(R.id.textDificultadEjercicio2);
        gifImageView = (GifImageView) findViewById(R.id.gifEjercicio2);

        editTextTiempo = (EditText)findViewById(R.id.editTextTiempo);
        editTextSeries = (EditText)findViewById(R.id.editTextSeries);
        editTextDescanso = (EditText)findViewById(R.id.editTextDescanso);

        Bundle bundle = this.getIntent().getExtras();

        ejercicio = (EjercicioTiempo) bundle.getSerializable(StringsMiguel.LLAVE_EJERCICIO_ENTRENAMIENTO);

        textViewNombreEjercicio.setText(ejercicio.getEjercicio().getNombre());
        textViewMusculos.setText(ejercicio.getEjercicio().getMusculos());
        textViewTipo.setText(ejercicio.getEjercicio().getTipo());
        textViewDificultad.setText(ejercicio.getEjercicio().getDificultad());
        editTextTiempo.setText(Integer.toString(ejercicio.getTiempo()));
        editTextSeries.setText(Integer.toString(ejercicio.getSeries()));
        editTextDescanso.setText(Integer.toString(ejercicio.getDescanso()));

        Utils.descargarYMostrarGIF(ejercicio.getEjercicio().getRutaGIF(),gifImageView);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width/1.1), (int) (height/1.5));


        imageButtonInformacionEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verInfo();
            }
        });

        imageButtonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarEjercicioSesion();
            }
        });

        imageButtonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void verInfo() {
        Intent inten = new Intent(PopEditarEjercicioEntrenamientoTiempo.this, InformacionEjercicioActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(StringsMiguel.LLAVE_EJERCICIO,ejercicio.getEjercicio());
        inten.putExtras(bundle);

        startActivity(inten);

    }

        private void editarEjercicioSesion() {
            String sTiempo = editTextTiempo.getText().toString();
            String sSeries = editTextSeries.getText().toString();
            String sDescansos = editTextDescanso.getText().toString();

            boolean completado = true;

            if(sTiempo.isEmpty()){
                editTextTiempo.setError(StringsMiguel.CAMPO_OBLIGATORIO);
                completado = false;
            }
            if(sSeries.isEmpty()){
                editTextSeries.setError(StringsMiguel.CAMPO_OBLIGATORIO);
                completado = false;
            }
            if(sDescansos.isEmpty()){
                editTextDescanso.setError(StringsMiguel.CAMPO_OBLIGATORIO);
                completado = false;
            }
            if(completado){
                int tiempo = Integer.parseInt(sTiempo);
                int series = Integer.parseInt(sSeries);
                int descansos = Integer.parseInt(sDescansos);


                ejercicio.setDescanso(descansos);
                ejercicio.setTiempo(tiempo);
                ejercicio.setSeries(series);

                Intent intent = this.getIntent();
                Bundle bundle = intent.getExtras();
                bundle.putSerializable(StringsMiguel.LLAVE_EJERCICIO_ENTRENAMIENTO,ejercicio);
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