package co.edu.javeriana.bittus.fitt.Vista;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.List;

import co.edu.javeriana.bittus.fitt.Adapters.EjerciciosSesionAdapter;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDistancia;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDuracion;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioRepeticiones;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioSesion;
import co.edu.javeriana.bittus.fitt.Modelo.Sesion;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.BtnClickListenerRow;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;
import co.edu.javeriana.bittus.fitt.Vista.PopUps.PopCrearEjercicioSesionDescanso;
import co.edu.javeriana.bittus.fitt.Vista.PopUps.PopEditarEjercicioSesionDescanso;
import co.edu.javeriana.bittus.fitt.Vista.PopUps.PopEditarEjercicioSesionDistancia;
import co.edu.javeriana.bittus.fitt.Vista.PopUps.PopEditarEjercicioSesionDuracion;
import co.edu.javeriana.bittus.fitt.Vista.PopUps.PopEditarEjercicioSesionRepeticion;

public class EditarSesionActivity extends AppCompatActivity {

    private ListView listaEjerciciosV;
    private List<EjercicioSesion> ejerciciosList;
    private ImageButton aceptarCrearSesionB;
    private ImageButton agregarEjercicioB;
    private ImageButton agregarDescansoB;
    private EjerciciosSesionAdapter ejerciciosSesionAdapter;

    private EditText nombreT;
    private EditText duracionT;
    private int posicionEditar;

    private Sesion sesion;

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


        Bundle bundle = getIntent().getExtras();

        sesion = (Sesion)bundle.getSerializable("sesion");


        ejerciciosList = sesion.getEjercicioSesionList();

        nombreT.setText(sesion.getNombre());
        duracionT.setText(Integer.toString(sesion.getDuracion()));

        ejerciciosSesionAdapter = new EjerciciosSesionAdapter(EditarSesionActivity.this, ejerciciosList, new BtnClickListenerRow() {
            @Override
            public void onBtnClickEdit(int position) {
                if (ejerciciosList.get(position).getEjercicio().getTipo().equals("Distancia")) {
                    abrirPopUpCrearEjercicioDistancia(ejerciciosList.get(position), position);
                } else if (ejerciciosList.get(position).getEjercicio().getTipo().equals("Repetición")) {
                    abrirPopUpCrearEjercicioRepeticion(ejerciciosList.get(position), position);
                } else if (ejerciciosList.get(position).getEjercicio().getTipo().equals("Duración")) {
                    abrirPopUpCrearEjercicioDuracion(ejerciciosList.get(position), position);
                }
            }

            @Override
            public void onBtnClickDelete(int position) {
                eliminarEjercicio(ejerciciosList.get(position));
            }
        });

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
        Intent intent = new Intent(EditarSesionActivity.this, PopCrearEjercicioSesionDescanso.class);



        startActivityForResult(intent, Utils.REQUEST_CODE_EJERCICIO_DESCANSO);
    }

    private void crearSesion() {

        String sNombre = nombreT.getText().toString();
        String sDuracion = duracionT.getText().toString();


        int duracion = Integer.parseInt(sDuracion);



        sesion.setDuracion(duracion);
        sesion.setNombre(sNombre);
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
        Intent intent = new Intent(new Intent(EditarSesionActivity.this, BuscarEjercicioActivity.class));

        startActivityForResult(intent, Utils.REQUEST_CODE_BUSCAR_EJERCICIO);



    }


    public void abrirPopUpCrearEjercicioDistancia(EjercicioSesion ejercicioSesion, int posicion){

        posicionEditar = posicion;

        Intent intent = new Intent(EditarSesionActivity.this, PopEditarEjercicioSesionDistancia.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("ejercicioSesion",(EjercicioDistancia)ejercicioSesion);
        intent.putExtras(bundle);
        startActivityForResult(intent, Utils.REQUEST_CODE_EJERCICIO_DISTANCIA_EDITAR);

    }
    public void abrirPopUpCrearEjercicioDuracion(EjercicioSesion ejercicioSesion, int posicion){

        posicionEditar = posicion;
        Intent intent;

        if(!ejercicioSesion.getEjercicio().getNombre().equals("Descanso")){
            intent = new Intent(EditarSesionActivity.this, PopEditarEjercicioSesionDuracion.class);
        }
        else{
            intent = new Intent(EditarSesionActivity.this, PopEditarEjercicioSesionDescanso.class);
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("ejercicioSesion",(EjercicioDuracion)ejercicioSesion);
        intent.putExtras(bundle);
        startActivityForResult(intent, Utils.REQUEST_CODE_EJERCICIO_DURACION_EDITAR);

    }
    public void abrirPopUpCrearEjercicioRepeticion(EjercicioSesion ejercicioSesion, int posicion){

        posicionEditar = posicion;
        Intent intent = new Intent(EditarSesionActivity.this, PopEditarEjercicioSesionRepeticion.class);
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
                case (Utils.REQUEST_CODE_EJERCICIO_DURACION_EDITAR):{
                    editarEjercicio((EjercicioSesion) data.getExtras().getSerializable("ejercicioSesion"));
                    ejerciciosSesionAdapter.notifyDataSetChanged();
                    break;
                }
                case (Utils.REQUEST_CODE_EJERCICIO_DISTANCIA_EDITAR):{
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
