package co.edu.javeriana.bittus.fitt.Vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Modelo.Entrenador;

public class Entrenador_miEntrenadorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenador_mi_entrenador);
        TextView nombre = (TextView) findViewById(R.id.txt_nameEntrenador);

        Bundle extras = getIntent().getExtras();
        Entrenador coach = extras.getParcelable("entrenador");

        if(coach != null){
            nombre.setText(coach.getNombre());
        }
    }
}