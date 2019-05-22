package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import co.edu.javeriana.bittus.fitt.Modelo.Entrenamiento;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.StringsMiguel;

public class InformacionEntrenamientoActivity extends AppCompatActivity {

    private TextView textViewNombreEntrenamiento;
    private TextView textViewDuracion;
    private TextView textViewDificultad;
    private TextView textViewDescanso;
    private TextView textViewDescripcion;
    private ListView listViewReseñas;
    private ImageView imageViewFotoCreador;
    private TextView textViewNombreCreador;
    private ImageButton imageButtonAdoptar;
    private ImageButton imageButtonEjercicios;
    private RatingBar ratingBarEntrenamiento;

    private Entrenamiento entrenamiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_entrenamiento);

        textViewNombreEntrenamiento = (TextView)findViewById(R.id.textViewNombreEntrenamiento);
        textViewDuracion = (TextView)findViewById(R.id.textViewDuracion);
        textViewDificultad = (TextView)findViewById(R.id.textViewDificultad);
        textViewDescanso = (TextView)findViewById(R.id.textViewDescanso);
        textViewDescripcion = (TextView)findViewById(R.id.textViewDescripcion);
        textViewNombreCreador = (TextView)findViewById(R.id.textViewNombreCreador);

        listViewReseñas = (ListView) findViewById(R.id.listViewResenas);

        imageViewFotoCreador = (ImageView) findViewById(R.id.imageViewPerfilCreador);

        imageButtonAdoptar = (ImageButton) findViewById(R.id.imageButtonAdoptar);
        imageButtonAdoptar = (ImageButton) findViewById(R.id.imageButtonVerEjerciciosEntrenamiento);

        ratingBarEntrenamiento = (RatingBar) findViewById(R.id.ratingBarEntrenamiento);

        Bundle bundle = getIntent().getExtras();
        entrenamiento = (Entrenamiento) bundle.getSerializable(StringsMiguel.LLAVE_ENTRENAMIENTO);

        textViewNombreEntrenamiento.setText(entrenamiento.getNombre());
        textViewDuracion.setText(entrenamiento.getDuracion()+" minutos");
        textViewDificultad.setText(entrenamiento.getDificultad());
        textViewDescanso.setText(entrenamiento.getNumDiasDescanso()+" día(s)");
        textViewDescripcion.setText(entrenamiento.getDescripcion());

        ratingBarEntrenamiento.setRating(entrenamiento.calcularRating());

    }
}
