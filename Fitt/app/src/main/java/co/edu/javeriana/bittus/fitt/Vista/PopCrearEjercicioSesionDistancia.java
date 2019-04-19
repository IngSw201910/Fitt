package co.edu.javeriana.bittus.fitt.Vista;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import co.edu.javeriana.bittus.fitt.R;

public class PopCrearEjercicioSesionDistancia extends Activity {

    private Button informacionEjercicioB;
    private Button aceptarButton;
    private Button cancelarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_agregar_ejercicio_sesion_distancia);

        informacionEjercicioB = findViewById(R.id.buttonInformacionEjercicio);

        aceptarButton = findViewById(R.id.buttonAceptar);
        cancelarButton = findViewById(R.id.buttonCancelar);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width/1.1), (int) height/2);


        informacionEjercicioB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PopCrearEjercicioSesionDistancia.this, InformacionEjercicioActivity.class));
            }
        });

        aceptarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cancelarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
