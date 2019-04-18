package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import co.edu.javeriana.bittus.fitt.R;

public class CrearRutinaActivity extends AppCompatActivity {

    private Button aceptarB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_rutina);

        aceptarB = (Button) findViewById(R.id.buttonSiguienteCrearRutina);


        aceptarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CrearRutinaActivity.this, CrearRutinaSesionesActivity.class));
            }
        });

    }
}


