package co.edu.javeriana.bittus.fitt.Vista;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import co.edu.javeriana.bittus.fitt.Adapters.UsuariosAdapter;
import co.edu.javeriana.bittus.fitt.Modelo.Usuario;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;

public class SeguidoresActivity extends AppCompatActivity implements TextWatcher{

    private ListView listViewUsuarios;
    private UsuariosAdapter adapterUsuarios;
    //private List<Usuario> listUsuarios;
    private ImageButton ImageButtonBuscarUsuarios;
    private EditText EditTextNombreUsuarioABuscar;

    private Usuario usuarioSeleccionado;

    FirebaseDatabase database;
    DatabaseReference myRef;

    private Usuario item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_seguidores);

        item= (Usuario) getIntent().getSerializableExtra("usuario");

        listViewUsuarios = (ListView)findViewById(R.id.listUsuariosBuscar);
        ImageButtonBuscarUsuarios = (ImageButton) findViewById(R.id.imageButtonBuscarUsuarioSeguidor);
        EditTextNombreUsuarioABuscar = (EditText) findViewById(R.id.editTextUsuarioSeguidor);

        EditTextNombreUsuarioABuscar.addTextChangedListener((TextWatcher) this);

        if(item.getSeguidoresList()!=null) {

           /* adapterUsuarios = new UsuariosAdapter(SeguidoresActivity.this, R.layout.item_usuario_row, item.getSeguidoresList());

            listViewUsuarios.setAdapter(adapterUsuarios);
            listViewUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    usuarioSeleccionado = item.getSeguidoresList().get(position);

                }
            });

            database = FirebaseDatabase.getInstance();

            descargarUsuarios();

            ImageButtonBuscarUsuarios.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });*/
        } else{
            Toast.makeText(SeguidoresActivity.this, "No hay seguidores", Toast.LENGTH_SHORT).show();
        }

    }

    private void descargarUsuarios (){

        /*myRef = database.getReference(RutasBaseDeDatos.);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    listUsuarios.add(singleSnapshot.getValue(Usuario.class));
                    Log.i("Prueba", singleSnapshot.getValue(Ejercicio.class).getRutaGIF());
                }
                adapterUsuarios.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Error:", "Error en la consulta", databaseError.toException());
            }
        });*/
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
