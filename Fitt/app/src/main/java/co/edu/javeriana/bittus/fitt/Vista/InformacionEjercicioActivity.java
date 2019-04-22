package co.edu.javeriana.bittus.fitt.Vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import co.edu.javeriana.bittus.fitt.Modelo.Ejercicio;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;
import pl.droidsonroids.gif.GifImageView;

public class InformacionEjercicioActivity extends AppCompatActivity {


    private GifImageView gifEjercicio;
    private TextView nombreEjercicioT;
    private TextView musculosEjercicioT;
    private TextView dificultadEjercicioT;
    private TextView tipoEjercicioT;
    private TextView descripcionEjercicioT;
    private Ejercicio ejercicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_ejercicio);

        gifEjercicio = (GifImageView) findViewById(R.id.gifEjercicio);
        nombreEjercicioT = (TextView) findViewById(R.id.textNombreEjercicio);
        musculosEjercicioT = (TextView) findViewById(R.id.textMusculosEjercicio);
        dificultadEjercicioT = (TextView) findViewById(R.id.textDificultadEjercicio);
        tipoEjercicioT = (TextView)findViewById(R.id.textTipoEjercicio);
        descripcionEjercicioT = (TextView) findViewById(R.id.textDescripcionEjercicio);


        Bundle bundle = this.getIntent().getExtras();
        ejercicio = (Ejercicio) bundle.getSerializable("ejercicio");


        nombreEjercicioT.setText(ejercicio.getNombre());
        musculosEjercicioT.setText(ejercicio.getMusculos());
        tipoEjercicioT.setText(ejercicio.getTipo());
        dificultadEjercicioT.setText(ejercicio.getDificultad());

        descripcionEjercicioT.setText(ejercicio.getDescripción());
        Utils.descargarYMostrarGIF(ejercicio.getRutaGIF(),gifEjercicio);


    }
}
