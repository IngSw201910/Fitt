package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import co.edu.javeriana.bittus.fitt.Adapters.EjerciciosSesionAdapter;
import co.edu.javeriana.bittus.fitt.Modelo.Ejercicio;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDistancia;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDuracion;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioRepeticiones;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioSesion;
import co.edu.javeriana.bittus.fitt.R;

public class CrearSesionActivity extends AppCompatActivity {

    private ListView listaEjerciciosV;
    private List<EjercicioSesion> ejerciciosList;
    private Button aceptarCrearSesionB;
    private Button agregarEjercicioB;
    private Button agregarDescansoB;
    private EjerciciosSesionAdapter ejerciciosSesionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_sesion);




        listaEjerciciosV = findViewById(R.id.listEjercicios);
        aceptarCrearSesionB = findViewById(R.id.buttonAceptarCrearSesion);
        agregarEjercicioB = findViewById(R.id.buttonAdicionarEjercicio);
        agregarDescansoB = findViewById(R.id.buttonAgregarDescanso);

        ejerciciosList = new ArrayList<EjercicioSesion>();

        //Datos de prueba

        Ejercicio ejercicio = new Ejercicio("Trotar","Piernas", "Distancia","Baja", 0,"Trotar por la calle");
        Ejercicio ejercicio2 = new Ejercicio("Flexion Pared","Piernas, Abdomen", "Duracion","Media", 0,"Aguantar con una flexion en la pared");
        Ejercicio ejercicio3 = new Ejercicio("Abdominales","Abdomen", "Repeticion","Media", 0,"Realizar una abdominal");

        ejerciciosList.add(new EjercicioDistancia(ejercicio, 100));
        ejerciciosList.add(new EjercicioDuracion(ejercicio2, 20));
        ejerciciosList.add(new EjercicioRepeticiones(ejercicio3,30,5,30));

        //Fin de datos de prueba

        ejerciciosSesionAdapter = new EjerciciosSesionAdapter(CrearSesionActivity.this,ejerciciosList);

        listaEjerciciosV.setAdapter(ejerciciosSesionAdapter);


        agregarEjercicioB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CrearSesionActivity.this, BuscarEjercicioActivity.class));
            }
        });

        aceptarCrearSesionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        agregarDescansoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CrearSesionActivity.this, PopCrearEjercicioSesionDescanso.class));
            }
        });




    }

    public void abrirPopUpCrearEjercicioDistancia(){

        startActivity(new Intent(CrearSesionActivity.this, PopCrearEjercicioSesionDistancia.class));

    }
    public void abrirPopUpCrearEjercicioDuracion(){

        startActivity(new Intent(CrearSesionActivity.this, PopCrearEjercicioSesionDuracion.class));

    }
    public void abrirPopUpCrearEjercicioRepeticion(){

        startActivity(new Intent(CrearSesionActivity.this, PopCrearEjercicioSesionRepeticion.class));

    }
    public void eliminarEjercicio(EjercicioSesion ejercicioSesion) {

        ejerciciosList.remove(ejercicioSesion);
        ejerciciosSesionAdapter.notifyDataSetChanged();

    }



}
