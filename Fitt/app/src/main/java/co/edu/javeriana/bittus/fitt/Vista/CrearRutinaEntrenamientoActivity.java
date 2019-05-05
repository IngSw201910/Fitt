package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import co.edu.javeriana.bittus.fitt.Modelo.Entrenamiento;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Adapters.EntrenamientosAdapter;
import co.edu.javeriana.bittus.fitt.Utilidades.BtnClickListenerRow;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;

public class CrearRutinaEntrenamientoActivity extends AppCompatActivity {


    private ImageButton aceptarB;
    private ImageButton adicionarB;
    private ListView listViewL;
    private EntrenamientosAdapter adapterEntrenamiento;
    private List<Entrenamiento> entrenamientoList;
    private int positionEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_rutina_entrenamiento);






        aceptarB = (ImageButton) findViewById(R.id.buttonAceptarCrearRutina);
        listViewL = (ListView) findViewById(R.id.listSesiones);
        adicionarB = (ImageButton)findViewById(R.id.buttonAdicionarSesion);

        entrenamientoList = new ArrayList<Entrenamiento>();


        adapterEntrenamiento = new EntrenamientosAdapter(CrearRutinaEntrenamientoActivity.this, R.layout.item_entrenamientos_nuevas_row, entrenamientoList, new BtnClickListenerRow() {
            @Override
            public void onBtnClickEdit(int position) {
                editarEntrenamiento(entrenamientoList.get(position), position);
            }

            @Override
            public void onBtnClickDelete(int position) {
                eliminarSesion(entrenamientoList.get(position));
            }
        });


        listViewL.setAdapter(adapterEntrenamiento);

        aceptarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finalizar();

            }
        });

        adicionarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearEntrenamiento();
            }
        });

    }

    private void finalizar() {

        if(entrenamientoList.isEmpty()){
            int duracion = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getApplicationContext(), "La lista de entrenamientos no puede estar vacía",duracion);
            toast.show();
        }else{
            Intent intent=this.getIntent();

            Bundle bundle = new Bundle();
            bundle.putSerializable("entrenamientos", (Serializable) entrenamientoList);
            intent.putExtras(bundle);
            setResult(Utils.REQUEST_CODE_CREAR_RUTINA_SESIONES,intent);

            int duracion = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(getApplicationContext(), "Se ha creado la rutina exítosamente",duracion);
            toast.show();

            finish();


        }

    }

    public void crearEntrenamiento(){

        startActivityForResult(new Intent(CrearRutinaEntrenamientoActivity.this, paraBorrar.class),Utils.REQUEST_CODE_CREAR_SESION);

    }
    public void editarEntrenamiento(Entrenamiento entrenamiento, int position) {

        positionEditar = position;

        Intent intent = new Intent(CrearRutinaEntrenamientoActivity.this, EditarEntrenamientoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("entrenamiento", entrenamiento);
        intent.putExtras(bundle);

        startActivityForResult(intent, Utils.REQUEST_CODE_EDITAR_SESION);
    }

    public void eliminarSesion(Entrenamiento entrenamiento) {

        entrenamientoList.remove(entrenamiento);
        adapterEntrenamiento.notifyDataSetChanged();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== Utils.REQUEST_CODE_CREAR_SESION&&data!=null)
        {
            Entrenamiento entrenamiento = (Entrenamiento) data.getExtras().getSerializable("entrenamiento");

            Intent intent = new Intent();
            if(entrenamiento !=null){
                entrenamientoList.add(entrenamiento);
                adapterEntrenamiento.notifyDataSetChanged();
            }

        }
        if(requestCode== Utils.REQUEST_CODE_EDITAR_SESION&&data!=null)
        {
            Entrenamiento entrenamiento = (Entrenamiento) data.getExtras().getSerializable("entrenamiento");

            entrenamientoList.set(positionEditar, entrenamiento);
            adapterEntrenamiento.notifyDataSetChanged();

        }
    }

}
