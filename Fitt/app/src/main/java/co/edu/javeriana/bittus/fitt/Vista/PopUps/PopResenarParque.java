package co.edu.javeriana.bittus.fitt.Vista.PopUps;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import co.edu.javeriana.bittus.fitt.R;

public class PopResenarParque extends Activity {

    private TextView nombreUsuario;
    private ImageView fotoUsuario;
    private RatingBar calificacion;
    private EditText reseña;
    private Button botonCancelar;
    private Button botonPublicar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_resenar_parque);

        nombreUsuario = (TextView) findViewById(R.id.textViewNombreUsuario);
        fotoUsuario = (ImageView) findViewById(R.id.imageViewFP);
        calificacion = (RatingBar) findViewById(R.id.ratingBarResena);
        reseña = (EditText) findViewById(R.id.editTextReseña);
        botonCancelar = (Button) findViewById(R.id.buttonCancelarReseña);
        botonPublicar = (Button) findViewById(R.id.buttonPublicarReseña);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width/1.1), (int) (height/2.4));

        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        botonPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

    }
}
