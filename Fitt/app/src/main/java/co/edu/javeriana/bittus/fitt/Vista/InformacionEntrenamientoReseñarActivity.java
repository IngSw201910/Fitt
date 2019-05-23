package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import co.edu.javeriana.bittus.fitt.Adapters.ReseñaAdaptador;
import co.edu.javeriana.bittus.fitt.Modelo.Entrenamiento;
import co.edu.javeriana.bittus.fitt.Modelo.Reseña;
import co.edu.javeriana.bittus.fitt.Modelo.Usuario;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.PersistenciaFirebase;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;
import co.edu.javeriana.bittus.fitt.Utilidades.StringsMiguel;
import co.edu.javeriana.bittus.fitt.Utilidades.StringsSebastian;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;
import co.edu.javeriana.bittus.fitt.Vista.PopUps.PopResenar;

public class InformacionEntrenamientoReseñarActivity extends AppCompatActivity {

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
    private Button buttonReseñar;

    private Entrenamiento entrenamiento;
    private Usuario usuarioCreador;
    private String llaveEntrenamiento;
    private String llaveUsuarioCreador;
    private Usuario usuario;
    private ReseñaAdaptador adaptador;

    private DatabaseReference myRef;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_entrenamiento_resenar);

        textViewNombreEntrenamiento = (TextView)findViewById(R.id.textViewNombreEntrenamiento);
        textViewDuracion = (TextView)findViewById(R.id.textViewDuracion);
        textViewDificultad = (TextView)findViewById(R.id.textViewDificultad);
        textViewDescanso = (TextView)findViewById(R.id.textViewDescanso);
        textViewDescripcion = (TextView)findViewById(R.id.textViewDescripcion);
        textViewNombreCreador = (TextView)findViewById(R.id.textViewNombreCreador);

        listViewReseñas = (ListView) findViewById(R.id.listViewResenas);

        imageViewFotoCreador = (ImageView) findViewById(R.id.imageViewPerfilCreador);

        imageButtonAdoptar = (ImageButton) findViewById(R.id.imageButtonAdoptar);
        imageButtonEjercicios = (ImageButton) findViewById(R.id.imageButtonVerEjerciciosEntrenamiento);
        buttonReseñar = (Button) findViewById(R.id.buttonReseñar);

        ratingBarEntrenamiento = (RatingBar) findViewById(R.id.ratingBarEntrenamiento);

        Bundle bundle = getIntent().getExtras();
        entrenamiento = (Entrenamiento) bundle.getSerializable(StringsMiguel.LLAVE_ENTRENAMIENTO);
        llaveEntrenamiento = (String) bundle.getSerializable(StringsMiguel.LLAVE_LLAVE);
        llaveUsuarioCreador = (String) bundle.getSerializable(StringsMiguel.LLAVE_USUARIO);


        Log.i("llave", llaveEntrenamiento);

        textViewNombreEntrenamiento.setText(entrenamiento.getNombre());
        textViewDuracion.setText(entrenamiento.getDuracion()+" minutos");
        textViewDificultad.setText(entrenamiento.getDificultad());
        textViewDescanso.setText(entrenamiento.getNumDiasDescanso()+" día(s)");
        textViewDescripcion.setText(entrenamiento.getDescripcion());

        ratingBarEntrenamiento.setRating(entrenamiento.calcularRating());


        adaptador = new ReseñaAdaptador(this,R.layout.elemento_lista_resena_parque,entrenamiento.getReseñas());

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

        myRef = database.getReference(RutasBaseDeDatos.RUTA_USUARIOS).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuario = dataSnapshot.getValue(Usuario.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        imageButtonAdoptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adoptar();
            }
        });
        imageButtonEjercicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejercicios();
            }
        });
        buttonReseñar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reseñar();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Utils.REQUEST_CODE_RESENA  && resultCode==RESULT_OK){
            Reseña reseñarecibida = (Reseña) data.getExtras().getSerializable(StringsSebastian.LLAVE_RESENA);
            subirReseña(reseñarecibida);
            adaptador.notifyDataSetChanged();
        }


    }

    private void reseñar() {

        Intent intent = new Intent(InformacionEntrenamientoReseñarActivity.this, PopResenar.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(StringsMiguel.LLAVE_USUARIO, usuario);
        intent.putExtras(bundle);
        startActivityForResult(intent, Utils.REQUEST_CODE_RESENA);


    }
    public void subirReseña (Reseña reseña){
        entrenamiento.getReseñas().add(reseña);
        database = FirebaseDatabase.getInstance();
        String key =  llaveEntrenamiento ;
        myRef=database.getReference(RutasBaseDeDatos.RUTA_ENTRENAMIENTOS+llaveUsuarioCreador+"/"+key);
        myRef.setValue(entrenamiento);
        ratingBarEntrenamiento.setRating(entrenamiento.calcularRating());
    }
    private void ejercicios() {
        Intent intent = new Intent(InformacionEntrenamientoReseñarActivity.this, InformacionEntrenamientoEjerciciosActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable(StringsMiguel.LLAVE_ENTRENAMIENTO, entrenamiento);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    private void adoptar() {
        Intent intent = new Intent(InformacionEntrenamientoReseñarActivity.this, AdoptarEntrenamientoActivity.class);
        intent.putExtra(StringsMiguel.LLAVE_ENTRENAMIENTO, entrenamiento);
        startActivity(intent);
        finish();
    }
}
