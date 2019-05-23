package co.edu.javeriana.bittus.fitt.Vista;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ImageButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import co.edu.javeriana.bittus.fitt.Adapters.UsuariosAdapter;
import co.edu.javeriana.bittus.fitt.Modelo.Usuario;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.BtnClickListenerSeguir;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;

public class BuscarUsuarioActivity extends AppCompatActivity implements TextWatcher {

    private ListView listViewUsuarios;
    private UsuariosAdapter adapterUsuarios;
    private List<Usuario> listUsuarios;
    private ImageButton ImageButtonBuscarUsuarios;
    private EditText EditTextNombreUsuarioABuscar;

    private List<String> uidsUsuarios;

    private Usuario usuarioSeleccionado;

    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseUser mAuth;

    private Usuario usuario;
    private Usuario item;
    private String uidUsuario;
    private int positionEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_usuario);


        uidsUsuarios = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(RutasBaseDeDatos.RUTA_USUARIOS).child(mAuth.getUid());


        listViewUsuarios = (ListView)findViewById(R.id.listUsuariosBuscar);
        listUsuarios = new ArrayList<Usuario>();
        ImageButtonBuscarUsuarios = (ImageButton) findViewById(R.id.imageButtonBuscarUsuarioSeguidor);
        EditTextNombreUsuarioABuscar = (EditText) findViewById(R.id.editTextUsuarioSeguido);

        EditTextNombreUsuarioABuscar.addTextChangedListener(this);

        adapterUsuarios = new UsuariosAdapter(BuscarUsuarioActivity.this, R.layout.item_usuario_row, listUsuarios);



        listViewUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                //Si el usuario no lo sigue
                Log.i("entra","entra");
                abrirSiguienteVentana(listUsuarios.get(position), uidsUsuarios.get(position));

                /*if(!usuario.validarSeguido(uidUsuario)){
abrirSiguienteVentanaUS(listUsuarios.get(position), uidsUsuarios.get(position));
                }else{

                }*/

            }
        });
        listViewUsuarios.setAdapter(adapterUsuarios);
        myRef = database.getReference(RutasBaseDeDatos.RUTA_USUARIOS).child(mAuth.getUid());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                usuario = dataSnapshot.getValue(Usuario.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        database = FirebaseDatabase.getInstance();

        descargarUsuarios();

        ImageButtonBuscarUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public boolean usuarioSeguido(){
        for(String usr: usuario.getSeguidosList()){
            if(mAuth.getUid().equals(usr)){
                return true;
            }
        }
        return false;
    }

    private void abrirSiguienteVentana(Usuario usuario, String uidUsuario) {
        Intent intent = new Intent(BuscarUsuarioActivity.this, MostrarUsuarioActivity.class);

        intent.putExtra("objectData",usuario);
        intent.putExtra("llaveUsuario", uidUsuario);

        startActivity(intent);
    }

    private void abrirSiguienteVentanaUS(Usuario usuario, String uidUsuario) {
        Intent intent = new Intent(BuscarUsuarioActivity.this, MostrarUsuarioSeguidoActivity.class);

        intent.putExtra("objectData",usuario);
        intent.putExtra("llaveUsuario", uidUsuario);

        startActivity(intent);
    }


    private void descargarUsuarios (){

      myRef = database.getReference(RutasBaseDeDatos.RUTA_USUARIOS);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    Usuario aux =singleSnapshot.getValue(Usuario.class);
                    uidsUsuarios.add(singleSnapshot.getKey());
                    listUsuarios.add(aux);
                }
                if(listUsuarios!=null) {
                    adapterUsuarios.notifyDataSetChanged();
                }else{
                    Toast.makeText(BuscarUsuarioActivity.this, "No hay usuarios en el sistema", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Error:", "Error en la consulta", databaseError.toException());
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.adapterUsuarios.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void seguirUsuario() {

        usuario.getSeguidosList().add(uidUsuario);
       myRef.setValue(usuario, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                item.getSeguidoresList().add(mAuth.getUid());
                myRef = database.getReference(RutasBaseDeDatos.RUTA_USUARIOS).child(uidUsuario);
                myRef.setValue(item);
            }
        });

    }

    public void dejarDeSeguir(){
        usuario.getSeguidosList().remove(uidUsuario);
        myRef.setValue(usuario, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                item.getSeguidoresList().remove(mAuth.getUid());
                myRef = database.getReference(RutasBaseDeDatos.RUTA_USUARIOS).child(uidUsuario);
                myRef.setValue(item);
            }
        });
    }
}
