package co.edu.javeriana.bittus.fitt.Vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import co.edu.javeriana.bittus.fitt.Modelo.Usuario;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Modelo.Entrenador;

public class Entrenador_miEntrenadorActivity extends AppCompatActivity {

    private Entrenador item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenador_mi_entrenador);
        item= (Entrenador) getIntent().getSerializableExtra("objectData");

        TextView nombre = (TextView) findViewById(R.id.entrenador_nombre);
        nombre.setText(item.getNombre());

    }
}
