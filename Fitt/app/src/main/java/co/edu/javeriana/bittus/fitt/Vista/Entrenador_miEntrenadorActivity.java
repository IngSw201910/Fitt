package co.edu.javeriana.bittus.fitt.Vista;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import co.edu.javeriana.bittus.fitt.Modelo.Usuario;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Modelo.Entrenador;
import co.edu.javeriana.bittus.fitt.Utilidades.PersistenciaFirebase;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;

public class Entrenador_miEntrenadorActivity extends AppCompatActivity {

    private Entrenador item;
    FirebaseUser mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Button miEntrenador_addB;
    Usuario usuario;
    private Entrenador entrenador;
    private String idUsuario;
    private String idEntrenador = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenador_mi_entrenador);
        item= (Entrenador) getIntent().getSerializableExtra("objectData");

        miEntrenador_addB = findViewById(R.id.miEntrenador_addB);
        TextView nombre = (TextView) findViewById(R.id.entrenador_nombre);
        nombre.setText(item.getNombre());

        TextView clientes = (TextView) findViewById(R.id.miEntrenador_Clientes);
        clientes.setText(item.getClientes().size() + " Clientes");

        TextView titulo = (TextView) findViewById(R.id.miEntrenador_titulo);
        titulo.setText(item.getNombreTitulo());

        TextView descripcción = (TextView) findViewById(R.id.miEntrenador_Desc);
        descripcción.setText(item.getPorqueElegirme());

        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();

        idUsuario = mAuth.getUid();
        Log.d("GETTING_IDUSUARIO", idUsuario);

        myRef = database.getReference("usuarios");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Entrenador aux = singleSnapshot.getValue(Entrenador.class);

                    if(item.getCorreo().equals(aux.getCorreo())){
                        String idTemp = singleSnapshot.getKey();
                        idEntrenador = idTemp;
                        Log.d("MATCH", idEntrenador);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //buscar si existe el usuario en lista clientes
        actualizarEstadoBotton();

        miEntrenador_addB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                agregarUsuario();
            }
        });
    }

    private void actualizarEstadoBotton(){
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("usuarios").child(idEntrenador);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                entrenador = dataSnapshot.getValue(Entrenador.class);
                Log.d("ENTRENADOR", idEntrenador);
                if(entrenador.getClientes().contains(idUsuario)){
                    Log.d("QUERY", "ENCONTRADO");
                    miEntrenador_addB.setVisibility(View.INVISIBLE);
                }else{
                    Log.d("QUERY", "NO ENCONTRADO");
                    miEntrenador_addB.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void agregarUsuario(){
        //List<String> clientesTemp = entrenador.getClientes();
        //clientesTemp.add(idUsuario);
        //PersistenciaFirebase.almacenarInformacionConRuta(RutasBaseDeDatos.RUTA_USUARIOS+idEntrenador+"/clientes/", clientesTemp);
    }
}
