package co.edu.javeriana.bittus.fitt.Vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import co.edu.javeriana.bittus.fitt.Adapters.UsuariosAdapter;
import co.edu.javeriana.bittus.fitt.Modelo.Ejercicio;
import co.edu.javeriana.bittus.fitt.Modelo.Usuario;
import co.edu.javeriana.bittus.fitt.R;

public class BuscarUsuarioActivity extends AppCompatActivity {

    private ListView listViewUsuarios;
    private UsuariosAdapter adapterUsuarios;
    private List<Usuario> listUsuarios;
    private ImageButton ImageButtonBuscarUsuarios;
    private EditText EditTextNombreUsuarioABuscar;

    private Usuario usuarioSeleccionado;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_usuario);
        listViewUsuarios = (ListView)findViewById(R.id.listUsuariosBuscar);
        listUsuarios = new ArrayList<Usuario>();
        ImageButtonBuscarUsuarios = (ImageButton) findViewById(R.id.imageButtonBuscarUsuario);
        EditTextNombreUsuarioABuscar = (EditText) findViewById(R.id.editTextUsuario);

        EditTextNombreUsuarioABuscar.addTextChangedListener((TextWatcher) this);

        adapterUsuarios = new UsuariosAdapter(BuscarUsuarioActivity.this, R.layout.item_usuario_row, listUsuarios);

        listViewUsuarios.setAdapter(adapterUsuarios);
        listViewUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                usuarioSeleccionado = listUsuarios.get(position);
                //abrirPopUp();
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
}
