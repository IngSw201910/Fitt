package co.edu.javeriana.bittus.fitt.Vista;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import co.edu.javeriana.bittus.fitt.R;

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
