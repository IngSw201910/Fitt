package co.edu.javeriana.bittus.fitt.Vista.PopUps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Vista.Entrenador_buscarEntrenadorActivity;

public class PopSolicitarEntrenador extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_solicitarentrenador);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int widthDisplay = displayMetrics.widthPixels;
        int heightDisplay = displayMetrics.heightPixels;

        getWindow().setLayout((int) (widthDisplay*0.8),(int) (heightDisplay*0.5));
    }

    public void goToBuscarEntrenador(View view){
        Intent nextActivity = new Intent(this, Entrenador_buscarEntrenadorActivity.class);
        startActivity(nextActivity);
    }
}
