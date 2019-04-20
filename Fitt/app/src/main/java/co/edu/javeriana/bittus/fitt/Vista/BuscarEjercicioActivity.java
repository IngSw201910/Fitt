package co.edu.javeriana.bittus.fitt.Vista;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import co.edu.javeriana.bittus.fitt.Adapters.EjerciciosAdapter;
import co.edu.javeriana.bittus.fitt.Modelo.Ejercicio;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDistancia;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDuracion;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioRepeticiones;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;

public class BuscarEjercicioActivity extends AppCompatActivity {

    private ListView listViewL;
    private EjerciciosAdapter adapterEjercicios;
    private List<Ejercicio> listaEjercicios;


    private EjercicioDistancia ejercicioDistancia;
    private EjercicioDuracion ejercicioDuracion;
    private EjercicioRepeticiones ejercicioRepeticion;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_ejercicio);

        listViewL = findViewById(R.id.listEjerciciosBuscar);
        listaEjercicios = new ArrayList<Ejercicio>();


        //Datos de prueba

        //Ejercicio ejercicio = new Ejercicio("Flexión con pies levantados", "Brazos", "Distancia","Media",R.drawable.gif_prueba,"Flexión con un apoyo en los pies");
        //listaEjercicios.add(ejercicio);

        //Fin datos de prueba

        adapterEjercicios = new EjerciciosAdapter(BuscarEjercicioActivity.this,R.layout.item_ejercicio_row,listaEjercicios);

        listViewL.setAdapter(adapterEjercicios);
        database = FirebaseDatabase.getInstance();

        descargarEjercicios();



    }

    public void abrirPopUpCrearEjercicioDistancia(Ejercicio ejercicio){
        Intent intent = new Intent(BuscarEjercicioActivity.this, PopCrearEjercicioSesionDistancia.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("ejercicio", ejercicio);


        intent.putExtras(bundle);

        startActivityForResult(intent, Utils.REQUEST_CODE_EJERCICIO_DISTANCIA);

    }
    public void abrirPopUpCrearEjercicioDuracion(Ejercicio ejercicio){

        Intent intent = new Intent(BuscarEjercicioActivity.this, PopCrearEjercicioSesionDuracion.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("ejercicio", ejercicio);

        intent.putExtras(bundle);

        startActivityForResult(intent, Utils.REQUEST_CODE_EJERCICIO_DURACION);


    }
    public void abrirPopUpCrearEjercicioRepeticion(Ejercicio ejercicio){

        Intent intent = new Intent(BuscarEjercicioActivity.this, PopCrearEjercicioSesionRepeticion.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("ejercicio", ejercicio);

        intent.putExtras(bundle);

        startActivityForResult(intent, Utils.REQUEST_CODE_EJERCICIO_REPETICION);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Utils.REQUEST_CODE_EJERCICIO_DISTANCIA && resultCode == RESULT_OK) {

            ejercicioDistancia = (EjercicioDistancia) data.getExtras().getSerializable("ejercicioDistancia");

            Intent intent = new Intent();
            if(ejercicioDistancia!=null){
                intent.putExtra("ejercicioSesion",ejercicioDistancia);
            }



            setResult(Activity.RESULT_OK, intent);
            finish();


        }
        if (requestCode == Utils.REQUEST_CODE_EJERCICIO_DURACION && resultCode == RESULT_OK) {

            ejercicioDuracion = (EjercicioDuracion) data.getExtras().getSerializable("ejercicioDuracion");

            Intent intent = new Intent();
            if(ejercicioDuracion!=null){
                intent.putExtra("ejercicioSesion",ejercicioDuracion);
            }



            setResult(Activity.RESULT_OK, intent);
            finish();


        }
        if (requestCode == Utils.REQUEST_CODE_EJERCICIO_REPETICION && resultCode == RESULT_OK) {

            ejercicioRepeticion = (EjercicioRepeticiones) data.getExtras().getSerializable("ejercicioRepeticion");

            Intent intent = new Intent();
            if(ejercicioRepeticion!=null){
                intent.putExtra("ejercicioSesion",ejercicioRepeticion);
            }



            setResult(Activity.RESULT_OK, intent);
            finish();


        }
    }

    //El sistema descarga la lista de ejercicios de Firebase con la información correspondiente.
    private void descargarEjercicios (){

        myRef = database.getReference(RutasBaseDeDatos.getRutaEjercicios());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    listaEjercicios.add(singleSnapshot.getValue(Ejercicio.class));
                    Log.i("Prueba", singleSnapshot.getValue(Ejercicio.class).getRutaGIF());
                }
                adapterEjercicios.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Error:", "Error en la consulta", databaseError.toException());
            }
        });
    }




}
