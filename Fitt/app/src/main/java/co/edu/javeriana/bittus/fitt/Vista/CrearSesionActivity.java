package co.edu.javeriana.bittus.fitt.Vista;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import co.edu.javeriana.bittus.fitt.Adapters.EjerciciosSesionAdapter;
import co.edu.javeriana.bittus.fitt.Modelo.Ejercicio;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDistancia;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDuracion;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioRepeticiones;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioSesion;
import co.edu.javeriana.bittus.fitt.Modelo.Sesion;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;

public class CrearSesionActivity extends AppCompatActivity {

    private ListView listaEjerciciosV;
    private List<EjercicioSesion> ejerciciosList;
    private ImageButton aceptarCrearSesionB;
    private ImageButton agregarEjercicioB;
    private ImageButton agregarDescansoB;
    private EjerciciosSesionAdapter ejerciciosSesionAdapter;

    private EditText nombreT;
    private EditText duracionT;
    private int posicionEditar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_sesion);


        nombreT = (EditText)findViewById(R.id.editText);
        duracionT = (EditText)findViewById(R.id.editText2);

        listaEjerciciosV = (ListView)findViewById(R.id.listEjercicios);
        aceptarCrearSesionB = (ImageButton)findViewById(R.id.buttonAceptarCrearSesion);
        agregarEjercicioB = (ImageButton)findViewById(R.id.buttonAdicionarEjercicio);
        agregarDescansoB = (ImageButton)findViewById(R.id.buttonAgregarDescanso);

        ejerciciosList = new ArrayList<EjercicioSesion>();

        //Datos de prueba
        /*
        Ejercicio ejercicio = new Ejercicio("Trotar","Piernas", "Distancia","Baja", 0,"Trotar por la calle");
        Ejercicio ejercicio2 = new Ejercicio("Flexion Pared","Piernas, Abdomen", "Duracion","Media", 0,"Aguantar con una flexion en la pared");
        Ejercicio ejercicio3 = new Ejercicio("Abdominales","Abdomen", "Repeticion","Media", 0,"Realizar una abdominal");

        ejerciciosList.add(new EjercicioDistancia(ejercicio, 100));
        ejerciciosList.add(new EjercicioDuracion(ejercicio2, 20));
        ejerciciosList.add(new EjercicioRepeticiones(ejercicio3,30,5,30));
        */
        //Fin de datos de prueba

        ejerciciosSesionAdapter = new EjerciciosSesionAdapter(CrearSesionActivity.this,ejerciciosList);

        listaEjerciciosV.setAdapter(ejerciciosSesionAdapter);


        agregarEjercicioB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarEjercicio();
            }
        });

        aceptarCrearSesionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearSesion();
            }
        });


        agregarDescansoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarDescanso();
            }
        });




    }

    private void agregarDescanso() {
        Intent intent = new Intent(CrearSesionActivity.this, PopCrearEjercicioSesionDescanso.class);



        startActivityForResult(intent, Utils.REQUEST_CODE_EJERCICIO_DESCANSO);
    }

    private void crearSesion() {

        String sNombre = nombreT.getText().toString();
        String sDuracion = duracionT.getText().toString();


        int duracion = Integer.parseInt(sDuracion);


        Sesion sesion = new Sesion(sNombre, duracion);

        sesion.setEjercicioSesionList(ejerciciosList);

        Intent intent = this.getIntent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("sesion", sesion);
        intent.putExtras(bundle);


        if (getParent() == null) {
            setResult(Activity.RESULT_OK, intent);
        } else {
            getParent().setResult(Activity.RESULT_OK, intent);
        }
        finish();
    }

    private void buscarEjercicio(){
        Intent intent = new Intent(new Intent(CrearSesionActivity.this, BuscarEjercicioActivity.class));

        startActivityForResult(intent, Utils.REQUEST_CODE_BUSCAR_EJERCICIO);



    }


    public void abrirPopUpCrearEjercicioDistancia(){

        startActivity(new Intent(CrearSesionActivity.this, PopCrearEjercicioSesionDistancia.class));

    }
    public void abrirPopUpCrearEjercicioDuracion(){

        startActivity(new Intent(CrearSesionActivity.this, PopCrearEjercicioSesionDuracion.class));

    }
    public void abrirPopUpCrearEjercicioRepeticion(EjercicioSesion ejercicioSesion, int posicion){

        posicionEditar = posicion;
        Intent intent = new Intent(CrearSesionActivity.this, PopCrearEjercicioSesionRepeticionEditar.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("ejercicioSesion",(EjercicioRepeticiones)ejercicioSesion);
        intent.putExtras(bundle);
        startActivityForResult(intent, Utils.REQUEST_CODE_EJERCICIO_REPETICION_EDITAR);

    }
    public void eliminarEjercicio(EjercicioSesion ejercicioSesion) {

        ejerciciosList.remove(ejercicioSesion);
        ejerciciosSesionAdapter.notifyDataSetChanged();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            switch(requestCode) {
                case (Utils.REQUEST_CODE_BUSCAR_EJERCICIO) : {
                    adicionarEjercicio((EjercicioSesion) data.getExtras().getSerializable("ejercicioSesion"));
                    break;
                }
                case (Utils.REQUEST_CODE_EJERCICIO_DESCANSO):{
                    adicionarEjercicio((EjercicioSesion) data.getExtras().getSerializable("ejercicioSesion"));
                    break;
                }
                case (Utils.REQUEST_CODE_EJERCICIO_REPETICION_EDITAR):{
                    editarEjercicio((EjercicioSesion) data.getExtras().getSerializable("ejercicioSesion"));
                    ejerciciosSesionAdapter.notifyDataSetChanged();
                    break;
                }
            }
        }

    }

    private void editarEjercicio(EjercicioSesion ejercicioSesion) {

        ejerciciosList.set(posicionEditar, ejercicioSesion);
        ejerciciosSesionAdapter.notifyDataSetChanged();

    }

    private void adicionarEjercicio(EjercicioSesion ejercicioSesion) {

        ejerciciosList.add(ejercicioSesion);
        ejerciciosSesionAdapter.notifyDataSetChanged();

    }
}
