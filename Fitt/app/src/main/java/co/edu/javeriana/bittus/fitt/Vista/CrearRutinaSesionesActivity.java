package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Modelo.Sesion;
import co.edu.javeriana.bittus.fitt.Adapters.SesionesAdapter;
import co.edu.javeriana.bittus.fitt.Utilidades.BtnClickListenerRow;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;

public class CrearRutinaSesionesActivity extends AppCompatActivity {


    private ImageButton aceptarB;
    private ImageButton adicionarB;
    private ListView listViewL;
    private SesionesAdapter adapterSesion;
    private List<Sesion> sesionList;
    private int positionEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_rutina_sesiones);






        aceptarB = (ImageButton) findViewById(R.id.buttonAceptarCrearRutina);
        listViewL = (ListView) findViewById(R.id.listSesiones);
        adicionarB = (ImageButton)findViewById(R.id.buttonAdicionarSesion);

        sesionList = new ArrayList<Sesion>();

        //Datos de prueba

        //sesionList.add(new Sesion("Piernas", 20));

        //Fin datos de prueba


        adapterSesion = new SesionesAdapter(CrearRutinaSesionesActivity.this, R.layout.item_sesiones_nuevas_row, sesionList, new BtnClickListenerRow() {
            @Override
            public void onBtnClickEdit(int position) {
                editarSesion(sesionList.get(position), position);
            }

            @Override
            public void onBtnClickDelete(int position) {
                eliminarSesion(sesionList.get(position));
            }
        });


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
                crearSesion();
            }
        });

    }

    private void finalizar() {

        if(sesionList.isEmpty()){
            int duracion = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getApplicationContext(), "La lista de sesiones no puede estar vacía",duracion);
            toast.show();
        }else{
            Intent intent=this.getIntent();

            Bundle bundle = new Bundle();
            bundle.putSerializable("sesiones", (Serializable) sesionList);
            intent.putExtras(bundle);
            setResult(Utils.REQUEST_CODE_CREAR_RUTINA_SESIONES,intent);

            int duracion = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(getApplicationContext(), "Se ha creado la rutina exítosamente",duracion);
            toast.show();

            finish();


        }

    }

    public void crearSesion(){

        startActivityForResult(new Intent(CrearRutinaSesionesActivity.this, CrearSesionActivity.class),Utils.REQUEST_CODE_CREAR_SESION);

    }
    public void editarSesion(Sesion sesion, int position) {

        positionEditar = position;

        Intent intent = new Intent(CrearRutinaSesionesActivity.this, EditarSesionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("sesion",sesion);
        intent.putExtras(bundle);

        startActivityForResult(intent, Utils.REQUEST_CODE_EDITAR_SESION);
    }

    public void eliminarSesion(Sesion sesion) {

        sesionList.remove(sesion);
        adapterSesion.notifyDataSetChanged();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== Utils.REQUEST_CODE_CREAR_SESION&&data!=null)
        {
            Sesion sesion = (Sesion) data.getExtras().getSerializable("sesion");

            Intent intent = new Intent();
            if(sesion!=null){
                sesionList.add(sesion);
                adapterSesion.notifyDataSetChanged();
            }

        }
        if(requestCode== Utils.REQUEST_CODE_EDITAR_SESION&&data!=null)
        {
            Sesion sesion = (Sesion) data.getExtras().getSerializable("sesion");

            sesionList.set(positionEditar, sesion);
            adapterSesion.notifyDataSetChanged();

        }
    }

}
