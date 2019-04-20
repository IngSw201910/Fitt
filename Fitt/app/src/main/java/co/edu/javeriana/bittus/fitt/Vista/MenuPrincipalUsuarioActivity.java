package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;

public class MenuPrincipalUsuarioActivity extends AppCompatActivity {


    private ImageButton salirB;
    private FirebaseAuth mAuth;
    private ImageButton crearRutinaB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_usuario);


        salirB = (ImageButton) findViewById(R.id.logout);
        crearRutinaB = (ImageButton) findViewById(R.id.btnCrearRutina);


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
