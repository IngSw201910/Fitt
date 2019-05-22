package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;



import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Modelo.Entrenador;
import co.edu.javeriana.bittus.fitt.Vista.PopUps.PopSolicitarEntrenador;


public class EntrenadorActivity extends AppCompatActivity {

    String idEntrenador = "default";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenador);

        //Checar si tiene el cliente un entrenador asociado;
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
            Intent nextActivity = new Intent(this, ChatsActivity.class);
            startActivity(nextActivity);
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


