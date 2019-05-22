package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import co.edu.javeriana.bittus.fitt.Modelo.Entrenamiento;
import co.edu.javeriana.bittus.fitt.Modelo.Usuario;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.PersistenciaFirebase;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;
import co.edu.javeriana.bittus.fitt.Utilidades.StringsMiguel;
import co.edu.javeriana.bittus.fitt.Utilidades.UtilsMiguel;

public class InformacionEntrenamientoActivity extends AppCompatActivity {

    private TextView textViewNombreEntrenamiento;
    private TextView textViewDuracion;
    private TextView textViewDificultad;
    private TextView textViewDescanso;
    private TextView textViewDescripcion;
    private ListView listViewReseñas;
    private ImageView imageViewFotoCreador;
    private TextView textViewNombreCreador;
    private ImageButton imageButtonAdoptar;
    private ImageButton imageButtonEjercicios;
    private RatingBar ratingBarEntrenamiento;

    private Entrenamiento entrenamiento;
    private Usuario usuarioCreador;
    private String llaveEntrenamiento;

    private DatabaseReference myRef;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_entrenamiento);

        textViewNombreEntrenamiento = (TextView)findViewById(R.id.textViewNombreEntrenamiento);
        textViewDuracion = (TextView)findViewById(R.id.textViewDuracion);
        textViewDificultad = (TextView)findViewById(R.id.textViewDificultad);
        textViewDescanso = (TextView)findViewById(R.id.textViewDescanso);
        textViewDescripcion = (TextView)findViewById(R.id.textViewDescripcion);
        textViewNombreCreador = (TextView)findViewById(R.id.textViewNombreCreador);

        listViewReseñas = (ListView) findViewById(R.id.listViewResenas);

        imageViewFotoCreador = (ImageView) findViewById(R.id.imageViewPerfilCreador);

        imageButtonAdoptar = (ImageButton) findViewById(R.id.imageButtonAdoptar);
        imageButtonAdoptar = (ImageButton) findViewById(R.id.imageButtonVerEjerciciosEntrenamiento);

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
                myRef = database.getReference(RutasBaseDeDatos.RUTA_USUARIOS+keyUsuario);
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        usuarioCreador = dataSnapshot.getValue(Usuario.class);
                        textViewNombreCreador.setText(usuarioCreador.getNombre());
                        PersistenciaFirebase.descargarFotoYPonerEnImageView(usuarioCreador.getDireccionFoto(),imageViewFotoCreador);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
