package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import co.edu.javeriana.bittus.fitt.Adapters.MisEntrenamientosAdapter;
import co.edu.javeriana.bittus.fitt.Modelo.Entrenamiento;
import co.edu.javeriana.bittus.fitt.Modelo.EntrenamientoAdoptado;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.BtnClickListenerEntrenamientoRow;
import co.edu.javeriana.bittus.fitt.Utilidades.BtnClickListenerMisEntrenamientoRow;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;
import co.edu.javeriana.bittus.fitt.Utilidades.StringsMiguel;

public class MisEntrenamientosActivity extends AppCompatActivity {



    private ListView listViewEntrenamientos;
    private MisEntrenamientosAdapter adapter;
    private List<Entrenamiento> entrenamientos;
    private List<Boolean> adoptado;

    private String llave = "";
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_entrenamientos);

        listViewEntrenamientos = (ListView) findViewById(R.id.listViewMisEntrenamientos);


        adoptado = new ArrayList<>();
        entrenamientos = new ArrayList<>();



        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        adapter = new MisEntrenamientosAdapter(this,R.layout.item_entrenamiento_mio_row,entrenamientos, new BtnClickListenerMisEntrenamientoRow() {

            @Override
            public void onBtnClickAdoptarOEliminar(int position) {

            }

            @Override
            public void onBtnClickInfo(int position) {
                mostrarInfo(entrenamientos.get(position));
            }

            @Override
            public void onBtnClickBorrar(int position) {

            }
        }, adoptado);

        listViewEntrenamientos.setAdapter(adapter);
        obtenerEntrenamientos();

    }

    private void mostrarInfo(Entrenamiento entrenamiento) {
        Intent intent = new Intent(MisEntrenamientosActivity.this, InformacionEntrenamientoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(StringsMiguel.LLAVE_ENTRENAMIENTO, entrenamiento);
        obtenerKeys(entrenamiento);
        bundle.putSerializable(StringsMiguel.LLAVE_LLAVE,llave );
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void obtenerEntrenamientos() {
        myRef = database.getReference(RutasBaseDeDatos.RUTA_ENTRENAMIENTOS+firebaseAuth.getCurrentUser().getUid());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()) {
                    Entrenamiento entrenamiento = singleSnapshot.getValue(Entrenamiento.class);
                    entrenamientos.add(entrenamiento);
                    adoptado.add(Boolean.FALSE);
                    adapter.notifyDataSetChanged();
                }
                obtenerEntrenamientosAdoptados();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void obtenerEntrenamientosAdoptados() {
        myRef = database.getReference(RutasBaseDeDatos.getRutaEntrenamientosAdoptados()+firebaseAuth.getCurrentUser().getUid());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()) {

                    for(DataSnapshot singleSnapsho2: singleSnapshot.getChildren()){
                        EntrenamientoAdoptado entrenamientoAdoptado = singleSnapsho2.getValue(EntrenamientoAdoptado.class);
                        boolean existe = false;
                        for (Entrenamiento entrenamiento: entrenamientos) {
                            if(entrenamientoAdoptado.getEntrenamiento().getNombre().equals(entrenamiento.getNombre())){
                                existe = true;
                                break;
                            }
                        }
                        if(!existe){
                            entrenamientos.add(entrenamientoAdoptado.getEntrenamiento());
                            adoptado.add(Boolean.TRUE);
                            adapter.notifyDataSetChanged();
                        }
                    }



                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void obtenerKeys(final Entrenamiento entrenamiento) {


        myRef = database.getReference(RutasBaseDeDatos.RUTA_ENTRENAMIENTOS);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()) {

                    for (DataSnapshot singleSnapshot2: singleSnapshot.getChildren()) {
                        Entrenamiento entrenamientoE = singleSnapshot2.getValue(Entrenamiento.class);

                        if(entrenamientoE.getNombre().equals(entrenamiento.getNombre())){
                            llave = singleSnapshot2.getKey();

                        }

                    }


                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
