package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import co.edu.javeriana.bittus.fitt.Adapters.EjerciciosAdapter;
import co.edu.javeriana.bittus.fitt.Modelo.Ejercicio;
import co.edu.javeriana.bittus.fitt.R;

public class BuscarEjercicioActivity extends AppCompatActivity {

    private ListView listViewL;
    private EjerciciosAdapter adapterSesion;
    private List<Ejercicio> ejerciciosList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_ejercicio);

        listViewL = findViewById(R.id.listEjerciciosBuscar);
        ejerciciosList = new ArrayList<Ejercicio>();


        //Datos de prueba

        Ejercicio ejercicio = new Ejercicio("Flexión con pies levantados", "Brazos", "Distancia","Media",R.drawable.gif_prueba,"Flexión con un apoyo en los pies");
        ejerciciosList.add(ejercicio);

        //Fin datos de prueba

        adapterSesion = new EjerciciosAdapter(BuscarEjercicioActivity.this,R.layout.item_ejercicio_row,ejerciciosList);

        listViewL.setAdapter(adapterSesion);


    }

    public void abrirPopUpCrearEjercicioDistancia(){

        startActivity(new Intent(BuscarEjercicioActivity.this, PopCrearEjercicioSesionDistancia.class));
        finish();
    }
    public void abrirPopUpCrearEjercicioDuracion(){

        startActivity(new Intent(BuscarEjercicioActivity.this, PopCrearEjercicioSesionDuracion.class));
        finish();

    }
    public void abrirPopUpCrearEjercicioRepeticion(){

        startActivity(new Intent(BuscarEjercicioActivity.this, PopCrearEjercicioSesionRepeticion.class));
        finish();

    }






}
