package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import co.edu.javeriana.bittus.fitt.R;

public class CrearRutinaActivity extends AppCompatActivity {

    private Button siguienteB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_rutina);

        siguienteB = (Button) findViewById(R.id.buttonSiguienteCrearRutina);




        siguienteB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguientePantalla();
            }
        });

    }

    private void siguientePantalla(){
        startActivityForResult(new Intent(CrearRutinaActivity.this, CrearRutinaSesionesActivity.class),1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==1)
        {
            finish();
        }
    }

}


