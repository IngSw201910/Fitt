package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Adapters.Utils;

public class MenuPrincipalUsuarioActivity extends AppCompatActivity {


    private Button salirB;
    private FirebaseAuth mAuth;
    private Button crearRutinaB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_usuario);


        salirB = (Button) findViewById(R.id.logout);
        crearRutinaB = (Button) findViewById(R.id.crearRutina);


        mAuth = FirebaseAuth.getInstance();

        salirB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.cerrarSesion();
                finish();
            }
        });

        crearRutinaB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuPrincipalUsuarioActivity.this, CrearRutinaActivity.class));
            }
        });

    }


}
