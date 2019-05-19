package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.CheckBox;

import java.io.Serializable;

import co.edu.javeriana.bittus.fitt.Modelo.Usuario;
import co.edu.javeriana.bittus.fitt.R;

public class MostrarUsuarioSeguidoActivity extends AppCompatActivity{

    private Usuario item;

    private TextView nombre;
    private Button seguidos;
    private Button seguidores;
    private Button seguir;
    private TextView entrenador;
    private CheckBox creadas;
    private CheckBox adoptadas;


    //Mostrar Usuario que sigue



        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mostrar_usuario);

            item= (Usuario) getIntent().getSerializableExtra("objectData");

            nombre=findViewById(R.id.textViewNombreUsuario);
            nombre.setText(item.getNombre());

            seguidos=findViewById(R.id.buttonSeguidosUS);
            seguidos.setText(item.getSeguidosList().size()+" seguidos");

            seguidores=findViewById(R.id.buttonSeguidoresUS);
            seguidores.setText(item.getSeguidoresList().size()+" seguidores");

            seguir =findViewById(R.id.buttonSeguirUS);

            entrenador=findViewById(R.id.textViewNombreEntrenadorUS);

            seguidores.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent =new Intent(MostrarUsuarioSeguidoActivity.this, SeguidoresActivity.class);
                    intent.putExtra("listaSeguidores", (Serializable) item.getSeguidoresList());
                    startActivity(intent);
                }
            });

            seguidos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent =new Intent(MostrarUsuarioSeguidoActivity.this, SeguidosActivity.class);
                    intent.putExtra("listaSeguidos", (Serializable) item.getSeguidosList());
                    startActivity(intent);
                }
            });

        }


}
