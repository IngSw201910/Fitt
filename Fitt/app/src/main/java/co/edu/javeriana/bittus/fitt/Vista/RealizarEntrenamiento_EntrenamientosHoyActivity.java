package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import co.edu.javeriana.bittus.fitt.Adapters.EntrenamientosAdapter;
import co.edu.javeriana.bittus.fitt.Adapters.EntrenamientosHoyAdapter;
import co.edu.javeriana.bittus.fitt.Modelo.Ejercicio;
import co.edu.javeriana.bittus.fitt.Modelo.Entrenamiento;
import co.edu.javeriana.bittus.fitt.Modelo.EntrenamientoAdoptado;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;
import co.edu.javeriana.bittus.fitt.Utilidades.UtilsJhonny;

public class RealizarEntrenamiento_EntrenamientosHoyActivity extends AppCompatActivity {

    //Esta actividad muestra los entrenamientos que tiene programado el usuario para ese día

    private TextView diaSemana;
    private TextView fecha;

    private ListView entrenamientosL;
    private List<EntrenamientoAdoptado> listaEntrenamientosAdoptadosHoy = new ArrayList<>();;

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseUser user = mAuth.getCurrentUser();



    private EntrenamientosHoyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_entrenamiento_entrenamientoshoy);


        diaSemana = findViewById(R.id.diaSemanaEntrenamientosTV);
        fecha = findViewById(R.id.fechaMisEntrenamientosTV);
        entrenamientosL = findViewById(R.id.listViewEntrenamientosHoy);

        adapter = new EntrenamientosHoyAdapter(this ,R.layout.item_entrenamiento_row, listaEntrenamientosAdoptadosHoy);
        entrenamientosL.setAdapter(adapter);

        // por ahora esta este evento para probar
        //DEBE SER CAMBIADO para desencadenar el evento cuando se toque un elemento de la lista
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RealizarEntrenamiento_EntrenamientosHoyActivity.this, RealizarEntrenamientoActivity.class));
            }
        });


        diaSemana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RealizarEntrenamiento_EntrenamientosHoyActivity.this, AdoptarEntrenamientoActivity.class));
            }
        });

        entrenamientosL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EntrenamientoAdoptado entrenamientoAdoptado = listaEntrenamientosAdoptadosHoy.get(position);
                Intent intent = new Intent (RealizarEntrenamiento_EntrenamientosHoyActivity.this, RealizarEntrenamientoActivity.class);
                intent.putExtra("entrenamiento",entrenamientoAdoptado.getEntrenamiento());
                startActivity(intent);

            }
        });

        inicializarVista ();
    }

    private void inicializarVista(){
        Calendar c = Calendar.getInstance();
        diaSemana.setText(UtilsJhonny.diaSemana(c.get(Calendar.DAY_OF_WEEK)));

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String fechaFormateada = df.format(c.getTime());

        fecha.setText(fechaFormateada);

        descargarInformacionSinSuscripcionACambios(RutasBaseDeDatos.getRutaEntrenamientosAdoptados()+user.getUid()+"/"+diaSemana.getText().toString()+"/");

    }

    private void descargarInformacionSinSuscripcionACambios (String ruta) {

        DatabaseReference myRef;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();


        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        myRef = database.getReference(ruta);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    listaEntrenamientosAdoptadosHoy.add(snapshot.getValue(EntrenamientoAdoptado.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Error:", "Error en la consulta", databaseError.toException());
            }
        });
    }




}
