package co.edu.javeriana.bittus.fitt.Vista;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import co.edu.javeriana.bittus.fitt.Adapters.MensajeRecienteAdapter;
import co.edu.javeriana.bittus.fitt.Adapters.MensajeUsuarioAdapter;
import co.edu.javeriana.bittus.fitt.Modelo.Chatlist;
import co.edu.javeriana.bittus.fitt.Modelo.Usuario;
import co.edu.javeriana.bittus.fitt.Modelo.Chat;
import co.edu.javeriana.bittus.fitt.R;

public class ChatsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Usuario> mUsuarios;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    private List<Chatlist> usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        mUsuarios = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recentChats);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new MensajeRecienteAdapter(getApplicationContext(), mUsuarios);
        recyclerView.setAdapter(adapter);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        usersList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Chatlist").child(firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chatlist chatlist = snapshot.getValue(Chatlist.class);
                    usersList.add(chatlist);
                }

                chatList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void gotoNewMessage(View view){
        Intent nextActivity = new Intent(this, nuevoMensajeActivity.class);
        startActivity(nextActivity);
    }

    private void chatList() {
        mUsuarios = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("usuarios");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsuarios.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Usuario user = snapshot.getValue(Usuario.class);
                    for (Chatlist chatlist : usersList){
                        if (snapshot.getKey().equals(chatlist.getId())){
                            mUsuarios.add(user);
                        }
                    }
                }
                adapter = new MensajeRecienteAdapter(getApplicationContext(), mUsuarios);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
