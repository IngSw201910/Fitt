package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import co.edu.javeriana.bittus.fitt.Modelo.Usuario;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.PersistenciaFirebase;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;
import co.edu.javeriana.bittus.fitt.Utilidades.StringsMiguel;

public class MenuPrincipalUsuarioFragment extends AppCompatActivity
implements BottomNavigationView.OnNavigationItemSelectedListener {


    private BottomNavigationView opcionesNavegacion;

    private String tipo;


    //prueba comentario
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu_principal_usuario);
        descargarInformacionSinSuscripcionACambios(null);

        opcionesNavegacion = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        opcionesNavegacion.setOnNavigationItemSelectedListener(this);


    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.action_comunidad:
                fragment = new ComunidadFragment();
                break;
            case R.id.action_inicio:
                if (tipo.equals("usuarioPredeterminado"))
                    fragment = new InicioUsuarioFragment();
                if (tipo.equals("entrenador"))
                    fragment = new InicioEntrenadorFragment();
                break;
            case R.id.action_perfil:
                if (tipo.equals("usuarioPredeterminado"))
                    fragment = new PerfilFragment();
                if (tipo.equals("entrenador"))
                    fragment = new PerfilEntrenadorFragment();
                break;
        }

        return loadFragment(fragment);
    }

    private void descargarInformacionSinSuscripcionACambios(String ruta) {

        DatabaseReference myRef;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();


        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        myRef = database.getReference(RutasBaseDeDatos.RUTA_USUARIOS + mAuth.getUid() + "/tipo");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tipo = (String) dataSnapshot.getValue();
                if (tipo.equals("entrenador"))
                    loadFragment(new InicioEntrenadorFragment());
                if (tipo.equals("usuarioPredeterminado"))
                    loadFragment(new InicioUsuarioFragment());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Error:", "Error en la consulta", databaseError.toException());
            }
        });
    }
}
