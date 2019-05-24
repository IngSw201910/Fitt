package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import co.edu.javeriana.bittus.fitt.Adapters.ReseñaAdaptador;
import co.edu.javeriana.bittus.fitt.Modelo.Entrenamiento;
import co.edu.javeriana.bittus.fitt.Modelo.Usuario;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.PersistenciaFirebase;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;
import co.edu.javeriana.bittus.fitt.Utilidades.StringsMiguel;

public class InformacionMiEntrenamientoActivity extends AppCompatActivity {

    private TextView textViewNombreEntrenamiento;
    private TextView textViewDuracion;
    private TextView textViewDificultad;
    private TextView textViewDescanso;
    private TextView textViewDescripcion;
    private ListView listViewReseñas;



    private ImageButton imageButtonEjercicios;
    private RatingBar ratingBarEntrenamiento;

    private Entrenamiento entrenamiento;

    private String llaveEntrenamiento;

    private DatabaseReference myRef;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_mi_entrenamiento);

        textViewNombreEntrenamiento = (TextView)findViewById(R.id.textViewNombreEntrenamiento);
        textViewDuracion = (TextView)findViewById(R.id.textViewDuracion);
        textViewDificultad = (TextView)findViewById(R.id.textViewDificultad);
        textViewDescanso = (TextView)findViewById(R.id.textViewDescanso);
        textViewDescripcion = (TextView)findViewById(R.id.textViewDescripcion);


        listViewReseñas = (ListView) findViewById(R.id.listViewResenas);


        imageButtonEjercicios = (ImageButton) findViewById(R.id.imageButtonVerEjerciciosEntrenamiento);

        ratingBarEntrenamiento = (RatingBar) findViewById(R.id.ratingBarEntrenamiento);

        Bundle bundle = getIntent().getExtras();
        entrenamiento = (Entrenamiento) bundle.getSerializable(StringsMiguel.LLAVE_ENTRENAMIENTO);
        llaveEntrenamiento = (String) bundle.getSerializable(StringsMiguel.LLAVE_LLAVE);

        textViewNombreEntrenamiento.setText(entrenamiento.getNombre());
        textViewDuracion.setText(entrenamiento.getDuracion()+" minutos");
        textViewDificultad.setText(entrenamiento.getDificultad());
        textViewDescanso.setText(entrenamiento.getNumDiasDescanso()+" día(s)");
        textViewDescripcion.setText(entrenamiento.getDescripcion());

        ratingBarEntrenamiento.setRating(entrenamiento.calcularRating());


        ReseñaAdaptador adaptador = new ReseñaAdaptador(this,R.layout.elemento_lista_resena_parque,entrenamiento.getReseñas());

        listViewReseñas.setAdapter(adaptador);


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(RutasBaseDeDatos.RUTA_ENTRENAMIENTOS);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String keyUsuario = "";
                for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()) {
                    for(DataSnapshot singleSnapshotB: singleSnapshot.getChildren() ){

                        if(singleSnapshotB.getKey().equals(llaveEntrenamiento)){
                            keyUsuario = singleSnapshot.getKey();
                            Log.i("USUARIO_UID", keyUsuario);
                            break;
                        }
                    }
                    if(!keyUsuario.equals("")){
                        break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        imageButtonEjercicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejercicios();
            }
        });
    }

    private void ejercicios() {
        Intent intent = new Intent(InformacionMiEntrenamientoActivity.this, InformacionEntrenamientoEjerciciosActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable(StringsMiguel.LLAVE_ENTRENAMIENTO, entrenamiento);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    private void adoptar() {
        Intent intent = new Intent(InformacionMiEntrenamientoActivity.this, AdoptarEntrenamientoActivity.class);
        intent.putExtra(StringsMiguel.LLAVE_ENTRENAMIENTO, entrenamiento);
        startActivity(intent);
        finish();
    }
}
