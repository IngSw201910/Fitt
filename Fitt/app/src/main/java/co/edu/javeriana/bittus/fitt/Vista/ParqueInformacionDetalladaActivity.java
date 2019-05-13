package co.edu.javeriana.bittus.fitt.Vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import co.edu.javeriana.bittus.fitt.R;

public class ParqueInformacionDetalladaActivity extends AppCompatActivity {

    TextView nombreParque;
    TextView direccion;
    ImageButton btnTomarFoto;
    ImageButton btnAñadirFotos;
    GridView gridView;
    RatingBar calificacion;
    Button añadirReseña;
    ListView reseñas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parque_informacion_detallada);
        nombreParque = (TextView) findViewById(R.id.textViewNombreParque);
        direccion = (TextView) findViewById(R.id.textViewDireccion);
        btnTomarFoto = (ImageButton) findViewById(R.id.imageButtonTomarFoto);
        btnAñadirFotos = (ImageButton) findViewById(R.id.imageButtonAnadirFotos);
        gridView= (GridView) findViewById(R.id.grid_view);
        calificacion = (RatingBar) findViewById(R.id.ratingBarDetalle);
        añadirReseña = (Button) findViewById(R.id.buttonAgregarReseña);
        reseñas = (ListView) findViewById(R.id.ListViewReseñas);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        nombreParque.setText(bundle.getString("titulo"));
        direccion.setText(bundle.getString("direccion"));




    }
}
