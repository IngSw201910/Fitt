package co.edu.javeriana.bittus.fitt.Vista;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class nuevoMensajeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MensajeUsuarioAdapter mensajeUsuarioAdapter;
    private List<Usuario> mUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_mensaje);

        recyclerView = findViewById(R.id.listaUsuariosChat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUsuarios = new ArrayList<>();

        readUsers();

    }

    private void readUsers(){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsuarios.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Usuario usuario = snapshot.getValue(Usuario.class);
                    assert usuario != null;
                    assert firebaseUser != null;

                    if(!usuario.getId().equals(firebaseUser.getUid())){
                        mUsuarios.add(usuario);
                    }

                }

                mensajeUsuarioAdapter = new MensajeUsuarioAdapter(getBaseContext(), mUsuarios);
                recyclerView.setAdapter(mensajeUsuarioAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
