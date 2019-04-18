package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Modelo.Sesion;
import co.edu.javeriana.bittus.fitt.Adapters.SesionesAdapter;

public class CrearRutinaSesionesActivity extends AppCompatActivity {


    private Button siguienteB;
    private Button adicionarB;
    private ListView listViewL;
    private SesionesAdapter adapterSesion;
    private List<Sesion> modeloList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_rutina_sesiones);


        siguienteB = (Button) findViewById(R.id.buttonAceptarCrearRutina);
        listViewL = (ListView) findViewById(R.id.listSesiones);
        adicionarB = (Button)findViewById(R.id.buttonAdicionarSesion);

        modeloList = new ArrayList<Sesion>();

        //Datos de prueba

        modeloList.add(new Sesion("Piernas", "20:00"));

        //Fin datos de prueba


        adapterSesion = new SesionesAdapter(CrearRutinaSesionesActivity.this, R.layout.item_sesiones_nuevas_row, modeloList);


        listViewL.setAdapter(adapterSesion);

        siguienteB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CrearRutinaSesionesActivity.this, MenuPrincipalUsuarioActivity.class));
            }
        });

        adicionarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CrearRutinaSesionesActivity.this, CrearSesionActivity.class));
            }
        });

    }



}
