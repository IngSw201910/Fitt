package co.edu.javeriana.bittus.fitt.Vista;

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
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;

public class MisEntrenamientosActivity extends AppCompatActivity {



    private ListView listViewEntrenamientos;
    private MisEntrenamientosAdapter adapter;
    private List<Entrenamiento> entrenamientos;
    private List<Boolean> adoptado;

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


        adapter = new MisEntrenamientosAdapter(this,R.layout.item_entrenamiento_mio_row,entrenamientos, new BtnClickListenerEntrenamientoRow() {
            @Override
            public void onBtnClickAdoptar(int position) {

            }

            @Override
            public void onBtnClickInfo(int position) {

            }
        }, adoptado);

        listViewEntrenamientos.setAdapter(adapter);
        obtenerEntrenamientos();

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
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
