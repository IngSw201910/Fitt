package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;



import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Modelo.Entrenador;



public class EntrenadorActivity extends AppCompatActivity {

    Entrenador coach = new Entrenador("Camilo Perez");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenador);
    }

    public void goToMiEntrenador(View view){
        Intent nextActivity = new Intent(this, Entrenador_miEntrenadorActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("entrenador", coach);
        nextActivity.putExtra("bundle", bundle);
        startActivity(nextActivity);
    }

    public void goToChatEntrenador(View view){
        Intent nextActivity = new Intent(this, ChatsActivity.class);
        startActivity(nextActivity);
    }

    public void goToBuscarEntrenador(View view){
        Intent nextActivity = new Intent(this, Entrenador_buscarEntrenadorActivity.class);
        startActivity(nextActivity);
    }
}
