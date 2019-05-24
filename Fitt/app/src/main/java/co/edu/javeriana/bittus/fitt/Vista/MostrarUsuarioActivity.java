package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

import co.edu.javeriana.bittus.fitt.Modelo.Entrenador;
import co.edu.javeriana.bittus.fitt.Modelo.Usuario;

import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;

import static co.edu.javeriana.bittus.fitt.Utilidades.PersistenciaFirebase.descargarFotoYPonerEnImageView;


//Mostrar Usuario que no se sigue
public class MostrarUsuarioActivity extends AppCompatActivity {


    private Entrenador aux;
    private Usuario item;
    private Usuario usuarioConectado;

    private TextView nombre;
    private Button seguidos;
    private Button seguidores;
    private Button seguir;
    private Button mensaje;
    private Button ubicacion;
    private TextView entrenador;
    private ImageView fotoU;

    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseUser mAuth;
    private String uidUsuario;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_usuario);

        //aux=(Entrenador) getIntent().getSerializableExtra("objectData");
        item= (Usuario) getIntent().getSerializableExtra("objectData");
        uidUsuario = (String) getIntent().getStringExtra("llaveUsuario");


        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(RutasBaseDeDatos.RUTA_USUARIOS).child(mAuth.getUid());

        seguir =findViewById(R.id.buttonSeguirUN);



        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                usuarioConectado = dataSnapshot.getValue(Usuario.class);
                seguir.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {

                        if(SePuedeSeguir()){
                            seguirUsuario();
                        }else if(usuarioConectado.validarSeguido(uidUsuario)){
                            dejarDeSeguir();
                        }else{
                            Toast.makeText(MostrarUsuarioActivity.this,"No puede seguirse a si mismo",Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        nombre = findViewById(R.id.textViewNombreUsuarioN);
        nombre.setText(item.getNombre());

        seguidos =findViewById(R.id.buttonSeguidosUN);
        seguidos.setText(item.getSeguidosList().size()+" seguidos");

        seguidores =findViewById(R.id.buttonSeguidoresUN);
        seguidores.setText(item.getSeguidoresList().size()+" seguidores");

        fotoU= findViewById(R.id.imageViewFotoUsuarioN);
        descargarFotoYPonerEnImageView(item.getDireccionFoto(),fotoU);



        entrenador =findViewById(R.id.textViewNombreEntrenadorUN);
        //entrenador.setText();

        seguidores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MostrarUsuarioActivity.this, SeguidoresActivity.class);
                intent.putExtra("listaSeguidores", (Serializable) item.getSeguidoresList());
                startActivity(intent);
            }
        });

        seguidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(MostrarUsuarioActivity.this, SeguidosActivity.class);
                intent.putExtra("listaSeguidos", (Serializable) item.getSeguidosList());
                startActivity(intent);
            }
        });
    }

    private void seguirUsuario() {

        usuarioConectado.getSeguidosList().add(uidUsuario);
        if(item.getTipo().compareTo("entrenador")==0){
            myRef.setValue(usuarioConectado, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    Entrenador aux= (Entrenador) item;
                    aux.getSeguidoresList().add(mAuth.getUid());
                    myRef = database.getReference(RutasBaseDeDatos.RUTA_USUARIOS).child(uidUsuario);
                    myRef.setValue(aux);
                }
            });
        }else {
            myRef.setValue(usuarioConectado, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    item.getSeguidoresList().add(mAuth.getUid());
                    myRef = database.getReference(RutasBaseDeDatos.RUTA_USUARIOS).child(uidUsuario);
                    myRef.setValue(item);
                }
            });
        }

    }
    private boolean SePuedeSeguir(){
        if(uidUsuario.compareTo(mAuth.getUid())==0){
            return false;
        }else if(usuarioConectado.validarSeguido(uidUsuario)){
            return false;
        }
        return true;
    }
    private void dejarDeSeguir() {

        usuarioConectado.getSeguidosList().remove(uidUsuario);
        myRef.setValue(usuarioConectado, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                item.getSeguidoresList().remove(mAuth.getUid());
                myRef = database.getReference(RutasBaseDeDatos.RUTA_USUARIOS).child(uidUsuario);
                myRef.setValue(item);
            }
        });

    }

}
