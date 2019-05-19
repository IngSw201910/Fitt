package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

import co.edu.javeriana.bittus.fitt.Modelo.Usuario;

import co.edu.javeriana.bittus.fitt.R;


//Mostrar Usuario que no se sigue
public class MostrarUsuarioActivity extends AppCompatActivity {


    private Usuario item;

    private TextView nombre;
    private Button seguidos;
    private Button seguidores;
    private Button seguir;
    private Button mensaje;
    private Button ubicacion;
    private TextView entrenador;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_usuario);

        item= (Usuario) getIntent().getSerializableExtra("objectData");

        nombre = findViewById(R.id.textViewNombreUsuarioN);
        nombre.setText(item.getNombre());

        seguidos =findViewById(R.id.buttonSeguidosUN);
        seguidos.setText(item.getSeguidosList().size()+" seguidos");

        seguidores =findViewById(R.id.buttonSeguidoresUN);
        seguidores.setText(item.getSeguidoresList().size()+" seguidores");

        seguir =findViewById(R.id.buttonSeguirUN);
        seguir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


            }
        }
        );


        entrenador =findViewById(R.id.textViewNombreEntrenadorUN);
        //entrenador.setText();

        seguidores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MostrarUsuarioActivity.this, SeguidoresActivity.class);
                intent.putExtra("listaSeguidores", (Serializable) item.getSeguidoresList());
                startActivity(intent);
            }
        });

        seguidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(MostrarUsuarioActivity.this, SeguidosActivity.class);
                intent.putExtra("listaSeguidos", (Serializable) item.getSeguidosList());
                startActivity(intent);
            }
        });
    }

}
