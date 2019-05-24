package co.edu.javeriana.bittus.fitt.Vista;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import co.edu.javeriana.bittus.fitt.Adapters.BusquedaEntrenadorAdapter;
import co.edu.javeriana.bittus.fitt.Adapters.UsuariosAdapter;
import co.edu.javeriana.bittus.fitt.Modelo.Entrenador;
import co.edu.javeriana.bittus.fitt.Modelo.Usuario;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;

public class Entrenador_buscarEntrenadorActivity extends AppCompatActivity implements TextWatcher {

    private ListView listViewUsuarios;
    private BusquedaEntrenadorAdapter adapterUsuarios;
    private List<Entrenador> listUsuarios;
    private EditText EditTextNombreUsuarioABuscar;

    private Entrenador usuarioSeleccionado;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenador_buscar_entrenador);

        //------------------------------------------
        listViewUsuarios = (ListView)findViewById(R.id.mostrarEntrenadoresList);
        listUsuarios = new ArrayList<Entrenador>();

        EditTextNombreUsuarioABuscar = (EditText) findViewById(R.id.buscarEntrenadorText);

        EditTextNombreUsuarioABuscar.addTextChangedListener(this);

        adapterUsuarios = new BusquedaEntrenadorAdapter(this, R.layout.item_entrenador_row, listUsuarios);

        listViewUsuarios.setAdapter(adapterUsuarios);

        listViewUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                usuarioSeleccionado = listUsuarios.get(position);

            }
        });

        database = FirebaseDatabase.getInstance();
        descargarUsuarios();
    }

    public void nuevaBusqueda(View view){

    }

    private void descargarUsuarios (){
        myRef = database.getReference("Usuarios");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    Entrenador aux =singleSnapshot.getValue(Entrenador.class);
                    if(aux.getTipoUsuario() != 0) {
                        listUsuarios.add(aux);
                    }
                }
                adapterUsuarios.notifyDataSetChanged();
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

}
