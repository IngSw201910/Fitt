package co.edu.javeriana.bittus.fitt.Vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import co.edu.javeriana.bittus.fitt.R;

public class Entrenador_buscarEntrenadorActivity extends AppCompatActivity {

    Spinner spinnerEntrenadorStars;
    Spinner spinnerEntrenadorAnios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenador_buscar_entrenador);

        spinnerEntrenadorStars = findViewById(R.id.spinnerEntrenadorStars);
        spinnerEntrenadorAnios = findViewById(R.id.spinnerEntrenadorAnios);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.numeroEstrellasEntrenador, android.R.layout.simple_spinner_item);
        spinnerEntrenadorStars.setAdapter(arrayAdapter);

        ArrayAdapter<CharSequence> arrayAdapterAnios = ArrayAdapter.createFromResource(this, R.array.numeroAniosEntrenador, android.R.layout.simple_spinner_item);
        spinnerEntrenadorAnios.setAdapter(arrayAdapterAnios);
    }

    public void nuevaBusqueda(View view){
        String aniosEntrenador = spinnerEntrenadorAnios.getSelectedItem().toString();
        String calificacionEntrenador = spinnerEntrenadorStars.getSelectedItem().toString();

        Log.d("OPC_BUSCAR_ENTRENADOR", aniosEntrenador + " - " + calificacionEntrenador);
    }

}
