package co.edu.javeriana.bittus.fitt.Vista;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import co.edu.javeriana.bittus.fitt.Adapters.MensajeUsuarioAdapter;
import co.edu.javeriana.bittus.fitt.Adapters.UsuariosAdapter;
import co.edu.javeriana.bittus.fitt.Modelo.Usuario;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;

public class nuevoMensajeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    //private MensajeUsuarioAdapter mensajeUsuarioAdapter;
    private List<Usuario> mUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_mensaje);

        recyclerView = (RecyclerView) findViewById(R.id.listaUsuariosChat);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mUsuarios = new ArrayList<>();
        adapter = new MensajeUsuarioAdapter(getApplicationContext(), mUsuarios);
        recyclerView.setAdapter(adapter);
        readUsers();

    }

    private void readUsers(){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(RutasBaseDeDatos.RUTA_USUARIOS);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsuarios.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Usuario usuario = snapshot.getValue(Usuario.class);
                    assert usuario != null;
                    assert firebaseUser != null;

                    if(!snapshot.getKey().equals(firebaseUser.getUid().toString())){
                        mUsuarios.add(usuario);
                        Log.d("CHAT_DATABASE", "Usuario:" + snapshot.getKey() + " - " + firebaseUser.getUid());
                    }
                }
                adapter = new MensajeUsuarioAdapter(getApplicationContext(), mUsuarios);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ERROR CHAT_DATABASE", "No se pudo cargar lista de Usuarios");
            }
        });
    }
}
