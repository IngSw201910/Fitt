package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import co.edu.javeriana.bittus.fitt.Adapters.EntrenamientosAdapter;
import co.edu.javeriana.bittus.fitt.Modelo.Ejercicio;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDescanso;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDistancia;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioEntrenamiento;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioRepeticiones;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioTiempo;
import co.edu.javeriana.bittus.fitt.Modelo.Entrenamiento;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.BtnClickListenerEntrenamientoRow;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;
import co.edu.javeriana.bittus.fitt.Utilidades.StringsMiguel;

public class BuscarEntrenamientosActivity extends AppCompatActivity implements TextWatcher {

    FirebaseDatabase database;
    DatabaseReference myRef;
    private List<Entrenamiento> listaEntrenamientosPublicos = new ArrayList<>();
    private ListView listViewL;
    private EntrenamientosAdapter adapter;
    private EditText nombreEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_buscar_entrenamientos);
        listViewL = (ListView)findViewById(R.id.listViewRutinas);
        nombreEdit = (EditText)findViewById(R.id.editTextNombre);


        nombreEdit.addTextChangedListener(this);

        adapter = new EntrenamientosAdapter(this,R.layout.item_entrenamiento_row, listaEntrenamientosPublicos,  new BtnClickListenerEntrenamientoRow() {
            @Override
            public void onBtnClickAdoptar(int position) {

                adoptarEntrenamiento(listaEntrenamientosPublicos.get(position));
            }

            @Override
            public void onBtnClickInfo(int position) {
                mostrarInfoEntrenamiento(listaEntrenamientosPublicos.get(position));
            }

        });

        listViewL.setAdapter(adapter);


        database = FirebaseDatabase.getInstance();
        descargarEntrenamientosPublicos();
    }

    private void mostrarInfoEntrenamiento(Entrenamiento entrenamiento) {
            startActivity(new Intent(BuscarEntrenamientosActivity.this, InformacionEntrenamientoActivity.class));
    }


    //El sistema descarga la lista de rutinas publicas y la información correspondiente
    private void descargarEntrenamientosPublicos (){

        myRef = database.getReference(RutasBaseDeDatos.getRutaEntrenamientosPublicos());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()){ //PARA CADA USUARIO
                        Entrenamiento entrenamiento = singleSnapshot.getValue(Entrenamiento.class);
                        List<EjercicioEntrenamiento> ejercicioEntrenamientos = new ArrayList<>();

                        DataSnapshot singleSnapshotE = singleSnapshot.child("ejercicioEntrenamientoList");
                            for(DataSnapshot singleSnapshotF: singleSnapshotE.getChildren()){
                                EjercicioEntrenamiento ejercicioEntrenamiento = singleSnapshotF.getValue(EjercicioEntrenamiento.class);

                                if(ejercicioEntrenamiento.getEjercicio().getTipo().equals(StringsMiguel.EJERCICIO_TIPO_REPETICIÓN)){
                                    EjercicioRepeticiones ejercicioRepeticiones = singleSnapshotF.getValue(EjercicioRepeticiones.class);
                                    ejercicioEntrenamientos.add(ejercicioRepeticiones);
                                }
                                if(ejercicioEntrenamiento.getEjercicio().getTipo().equals(StringsMiguel.EJERCICIO_TIPO_DESCANSO)){
                                    EjercicioDescanso ejercicioDescanso = singleSnapshotF.getValue(EjercicioDescanso.class);
                                    ejercicioEntrenamientos.add(ejercicioDescanso);
                                }
                                if(ejercicioEntrenamiento.getEjercicio().getTipo().equals(StringsMiguel.EJERCICIO_TIPO_TIEMPO)){
                                    EjercicioTiempo ejercicioTiempo = singleSnapshotF.getValue(EjercicioTiempo.class);
                                    ejercicioEntrenamientos.add(ejercicioTiempo);
                                }
                                if(ejercicioEntrenamiento.getEjercicio().getTipo().equals(StringsMiguel.EJERCICIO_TIPO_DISTANCIA)){
                                    EjercicioDistancia ejercicioDistancia = singleSnapshotF.getValue(EjercicioDistancia.class);
                                    ejercicioEntrenamientos.add(ejercicioDistancia);
                                }


                            }



                        entrenamiento.setEjercicioEntrenamientoList(ejercicioEntrenamientos);
                        Log.i("PRUEBARUTINA:", entrenamiento.getDescripcion());
                        listaEntrenamientosPublicos.add(entrenamiento);
                }
                //adapterRutinas.notifyDataSetChanged(); //linea para notificar los cambios
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Error:", "Error en la consulta", databaseError.toException());
            }
        });
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.adapter.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void adoptarEntrenamiento (Entrenamiento entrenamiento){
        Log.i("Entrenamientoo", entrenamiento.getNombre());
        Intent intent = new Intent(BuscarEntrenamientosActivity.this, AdoptarEntrenamientoActivity.class);
        intent.putExtra("entrenamiento", entrenamiento);
        startActivity(intent);
    }
}
