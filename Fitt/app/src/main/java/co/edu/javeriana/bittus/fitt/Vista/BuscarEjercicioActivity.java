package co.edu.javeriana.bittus.fitt.Vista;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
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
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDescanso;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDistancia;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioRepeticiones;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;
import co.edu.javeriana.bittus.fitt.Vista.PopUps.PopCrearEjercicioEntrenamientoDistancia;
import co.edu.javeriana.bittus.fitt.Vista.PopUps.PopCrearEjercicioEntrenamientoTiempo;
import co.edu.javeriana.bittus.fitt.Vista.PopUps.PopCrearEjercicioEntrenamientoRepeticion;

public class BuscarEjercicioActivity extends AppCompatActivity implements TextWatcher {

    private ListView listViewL;
    private EjerciciosAdapter adapterEjercicios;
    private List<Ejercicio> listaEjercicios;
    private ImageButton buscarEjercicioB;
    private EditText nombreEjercicioBuscar;



    private Ejercicio ejercicioSeleccionado;
    private EjercicioDistancia ejercicioDistancia;
    private EjercicioDescanso ejercicioDescanso;
    private EjercicioRepeticiones ejercicioRepeticion;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_ejercicio);

        listViewL = (ListView)findViewById(R.id.listEjerciciosBuscar);
        listaEjercicios = new ArrayList<Ejercicio>();
        buscarEjercicioB = (ImageButton) findViewById(R.id.imageButtonBuscarEjercicio);
        nombreEjercicioBuscar = (EditText) findViewById(R.id.editText3);


        nombreEjercicioBuscar.addTextChangedListener(this);



        adapterEjercicios = new EjerciciosAdapter(BuscarEjercicioActivity.this,R.layout.item_ejercicio_row,listaEjercicios);


        listViewL.setAdapter(adapterEjercicios);
        listViewL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ejercicioSeleccionado = listaEjercicios.get(position);
                abrirPopUp();
            }
        });

        database = FirebaseDatabase.getInstance();

        descargarEjercicios();

        buscarEjercicioB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }



    public void abrirPopUp(){
        if(ejercicioSeleccionado.getTipo().equals("Distancia")){

            abrirPopUpCrearEjercicioDistancia();
        }
        if(ejercicioSeleccionado.getTipo().equals("Duración")){

           abrirPopUpCrearEjercicioDuracion();
        }
        if(ejercicioSeleccionado.getTipo().equals("Repetición")){

            abrirPopUpCrearEjercicioRepeticion();
        }

    }

    public void abrirPopUpCrearEjercicioDistancia(){
        Intent intent = new Intent(BuscarEjercicioActivity.this, PopCrearEjercicioEntrenamientoDistancia.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("ejercicio", ejercicioSeleccionado);


        intent.putExtras(bundle);

        startActivityForResult(intent, Utils.REQUEST_CODE_EJERCICIO_DISTANCIA);

    }
    public void abrirPopUpCrearEjercicioDuracion(){

        Intent intent = new Intent(BuscarEjercicioActivity.this, PopCrearEjercicioEntrenamientoTiempo.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("ejercicio", ejercicioSeleccionado);

        intent.putExtras(bundle);

        startActivityForResult(intent, Utils.REQUEST_CODE_EJERCICIO_DURACION);


    }
    public void abrirPopUpCrearEjercicioRepeticion(){

        Intent intent = new Intent(BuscarEjercicioActivity.this, PopCrearEjercicioEntrenamientoRepeticion.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("ejercicio", ejercicioSeleccionado);

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
                intent.putExtra("ejercicioEntrenamiento",ejercicioDistancia);
            }



            setResult(Activity.RESULT_OK, intent);
            finish();


        }
        if (requestCode == Utils.REQUEST_CODE_EJERCICIO_DURACION && resultCode == RESULT_OK) {

            ejercicioDescanso = (EjercicioDescanso) data.getExtras().getSerializable("ejercicioDescanso");

            Intent intent = new Intent();
            if(ejercicioDescanso !=null){
                intent.putExtra("ejercicioEntrenamiento", ejercicioDescanso);
            }



            setResult(Activity.RESULT_OK, intent);
            finish();


        }
        if (requestCode == Utils.REQUEST_CODE_EJERCICIO_REPETICION && resultCode == RESULT_OK) {

            ejercicioRepeticion = (EjercicioRepeticiones) data.getExtras().getSerializable("ejercicioRepeticion");

            Intent intent = new Intent();
            if(ejercicioRepeticion!=null){
                intent.putExtra("ejercicioEntrenamiento",ejercicioRepeticion);
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


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.adapterEjercicios.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
