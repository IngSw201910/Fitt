package co.edu.javeriana.bittus.fitt.Vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import co.edu.javeriana.bittus.fitt.Adapters.EjerciciosEntrenamientoInformacionAdapter;
import co.edu.javeriana.bittus.fitt.Modelo.Entrenamiento;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.StringsMiguel;

public class InformacionEntrenamientoEjerciciosActivity extends AppCompatActivity {

    private Entrenamiento entrenamiento;

    private ListView listViewEjerciciosEntrenamiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_entrenamiento_ejercicios);

        listViewEjerciciosEntrenamiento = (ListView) findViewById(R.id.listViewEjerciciosEntrenamiento);

        entrenamiento = (Entrenamiento) getIntent().getExtras().getSerializable(StringsMiguel.LLAVE_ENTRENAMIENTO);

        EjerciciosEntrenamientoInformacionAdapter adapter = new EjerciciosEntrenamientoInformacionAdapter(InformacionEntrenamientoEjerciciosActivity.this,entrenamiento.getEjercicioEntrenamientoList());


        listViewEjerciciosEntrenamiento.setAdapter(adapter);
    }
}
