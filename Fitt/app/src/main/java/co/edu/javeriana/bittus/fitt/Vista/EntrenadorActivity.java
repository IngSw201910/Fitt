package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import co.edu.javeriana.bittus.fitt.Modelo.Usuario;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Modelo.Entrenador;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;
import co.edu.javeriana.bittus.fitt.Vista.PopUps.PopSolicitarEntrenador;


public class EntrenadorActivity extends AppCompatActivity {

    String idEntrenador = "default";

    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseUser mAuth;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenador);

        //Checar si tiene el cliente un entrenador asociado;
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Usuarios").child(mAuth.getUid());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuario = dataSnapshot.getValue(Usuario.class);
                idEntrenador = usuario.getIdEntrenador();
                Log.d("GETTING_IDENTRENADOR", idEntrenador);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public boolean tieneEntrenador(String idEntrenador){
        if(idEntrenador.equals("default")){
            return false;
        }else{
            return true;
        }
    }

    public void goToMiEntrenador(View view){
        if(tieneEntrenador(idEntrenador)){
            Intent nextActivity = new Intent(this, Entrenador_miEntrenadorActivity.class);
            //nextActivity.putExtra("entrenador", coach);
            //startActivity(nextActivity);
        }else{
            Intent nextPopUp = new Intent(this, PopSolicitarEntrenador.class);
            startActivity(nextPopUp);
        }
    }
    public void goToChatEntrenador(View view){
        if(tieneEntrenador(idEntrenador)){
            Intent intent = new Intent(this, MensajeActivity.class);
            intent.putExtra("id", idEntrenador);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else{
            Intent nextPopUp = new Intent(this, PopSolicitarEntrenador.class);
            startActivity(nextPopUp);
        }
    }

    public void goToBuscarEntrenador(View view){
        Intent nextActivity = new Intent(this, Entrenador_buscarEntrenadorActivity.class);
        startActivity(nextActivity);
    }
}


