package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;
import co.edu.javeriana.bittus.fitt.Vista.PopUps.PerfilFragment;

public class MenuPrincipalUsuarioFragment extends AppCompatActivity
implements BottomNavigationView.OnNavigationItemSelectedListener{


    private BottomNavigationView opcionesNavegacion;


    //prueba comentario
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_usuario);

        opcionesNavegacion = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        opcionesNavegacion.setOnNavigationItemSelectedListener(this);

        loadFragment(new InicioUsuarioFragment());


    }

    private boolean loadFragment (Fragment fragment){
        if (fragment != null){
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
        switch (menuItem.getItemId()){
            case R.id.action_comunidad:
                fragment = new ComunidadFragment();
                break;
            case R.id.action_inicio:
                fragment = new InicioUsuarioFragment();
                break;
            case R.id.action_perfil:
                fragment = new PerfilFragment();
                break;
        }

        return loadFragment(fragment);
    }
}
