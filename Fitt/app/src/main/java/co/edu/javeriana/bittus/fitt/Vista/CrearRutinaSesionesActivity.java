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


    private Button aceptarB;
    private Button adicionarB;
    private ListView listViewL;
    private SesionesAdapter adapterSesion;
    private List<Sesion> sesionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_rutina_sesiones);






        aceptarB = (Button) findViewById(R.id.buttonAceptarCrearRutina);
        listViewL = (ListView) findViewById(R.id.listSesiones);
        adicionarB = (Button)findViewById(R.id.buttonAdicionarSesion);

        sesionList = new ArrayList<Sesion>();

        //Datos de prueba

        sesionList.add(new Sesion("Piernas", "20:00"));

        //Fin datos de prueba


        adapterSesion = new SesionesAdapter(CrearRutinaSesionesActivity.this, R.layout.item_sesiones_nuevas_row, sesionList);


        listViewL.setAdapter(adapterSesion);

        aceptarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finalizar();

            }
        });

        adicionarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CrearRutinaSesionesActivity.this, CrearSesionActivity.class));
            }
        });

    }

    private void finalizar() {
        Intent intent=new Intent();
        setResult(1,intent);
        finish();
    }


    public void editarSesion(Sesion sesion) {

        startActivity(new Intent(CrearRutinaSesionesActivity.this, CrearSesionActivity.class));
    }

    public void eliminarSesion(Sesion sesion) {

        sesionList.remove(sesion);
        adapterSesion.notifyDataSetChanged();

    }
}
