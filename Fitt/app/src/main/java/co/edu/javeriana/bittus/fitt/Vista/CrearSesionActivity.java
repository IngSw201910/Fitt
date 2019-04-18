package co.edu.javeriana.bittus.fitt.Vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import co.edu.javeriana.bittus.fitt.Adapters.EjerciciosAdapter;
import co.edu.javeriana.bittus.fitt.Modelo.Ejercicio;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDistancia;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDuracion;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioSesion;
import co.edu.javeriana.bittus.fitt.R;

public class CrearSesionActivity extends AppCompatActivity {

    private ListView listaEjerciciosV;
    private List<EjercicioSesion> ejerciciosList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_sesion);

        listaEjerciciosV = findViewById(R.id.listEjercicios);

        ejerciciosList = new ArrayList<EjercicioSesion>();

        //Datos de prueba

        Ejercicio ejercicio = new Ejercicio("Trotar","Piernas", "Distancia","Baja", 0,"Trotar por la calle");
        Ejercicio ejercicio2 = new Ejercicio("Flexion Pared","Piernas, Abdomen", "Duracion","Media", 0,"Aguantar con una flexion en la pared");

        ejerciciosList.add(new EjercicioDistancia(ejercicio, 100));
        ejerciciosList.add(new EjercicioDuracion(ejercicio2, 20));

        //Fin de datos de prueba


        listaEjerciciosV.setAdapter(new EjerciciosAdapter(CrearSesionActivity.this,ejerciciosList));






    }






}
