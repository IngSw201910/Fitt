package co.edu.javeriana.bittus.fitt.Vista;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.edu.javeriana.bittus.fitt.Adapters.EjerciciosEntrenamientoAdapter;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDescanso;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDistancia;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioRepeticiones;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioEntrenamiento;
import co.edu.javeriana.bittus.fitt.Modelo.Entrenamiento;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.BtnClickListenerRow;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;
import co.edu.javeriana.bittus.fitt.Vista.PopUps.PopCrearEjercicioEntrenamientoDescanso;
import co.edu.javeriana.bittus.fitt.Vista.PopUps.PopEditarEjercicioEntrenamientoDescanso;
import co.edu.javeriana.bittus.fitt.Vista.PopUps.PopEditarEjercicioEntrenamientoDistancia;
import co.edu.javeriana.bittus.fitt.Vista.PopUps.PopEditarEjercicioEntrenamientoTiempo;
import co.edu.javeriana.bittus.fitt.Vista.PopUps.PopEditarEjercicioEntrenamientoRepeticion;

public class paraBorrar extends AppCompatActivity {

    private ListView listaEjerciciosV;
    private List<EjercicioEntrenamiento> ejerciciosList;
    private ImageButton aceptarCrearEntrenamientoB;
    private ImageButton agregarEjercicioB;
    private ImageButton agregarDescansoB;
    private EjerciciosEntrenamientoAdapter ejerciciosEntrenamientoAdapter;

    private EditText nombreT;

    private int posicionEditar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_entrenamiento);


        nombreT = (EditText)findViewById(R.id.editText);


        listaEjerciciosV = (ListView)findViewById(R.id.listEjercicios);
        aceptarCrearEntrenamientoB = (ImageButton)findViewById(R.id.buttonAceptarCrearSesion);
        agregarEjercicioB = (ImageButton)findViewById(R.id.buttonAdicionarEjercicio);
        agregarDescansoB = (ImageButton)findViewById(R.id.buttonAgregarDescanso);

        ejerciciosList = new ArrayList<EjercicioEntrenamiento>();



        ejerciciosEntrenamientoAdapter = new EjerciciosEntrenamientoAdapter(paraBorrar.this, ejerciciosList, new BtnClickListenerRow() {
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


        listaEjerciciosV.setAdapter(ejerciciosEntrenamientoAdapter);


        agregarEjercicioB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarEjercicio();
            }
        });

        aceptarCrearEntrenamientoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearEntrenamiento();
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
        Intent intent = new Intent(paraBorrar.this, PopCrearEjercicioEntrenamientoDescanso.class);



        startActivityForResult(intent, Utils.REQUEST_CODE_EJERCICIO_DESCANSO);
    }

    private void crearEntrenamiento() {

        String sNombre = nombreT.getText().toString();


        boolean completo = true;
        if(sNombre.isEmpty()){
            nombreT.setError("Campo obligatorio");
            completo = false;
        }

        if(ejerciciosList.isEmpty()){
            completo = false;
            int duracion = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getApplicationContext(), "La lista de ejercicios no puede estar vacía",duracion);
            toast.show();
        }
        if(completo){




        }


    }

    private void buscarEjercicio(){
        Intent intent = new Intent(new Intent(paraBorrar.this, BuscarEjercicioActivity.class));

        startActivityForResult(intent, Utils.REQUEST_CODE_BUSCAR_EJERCICIO);



    }


    public void abrirPopUpCrearEjercicioDistancia(EjercicioEntrenamiento ejercicioEntrenamiento, int posicion){

        posicionEditar = posicion;

        Intent intent = new Intent(paraBorrar.this, PopEditarEjercicioEntrenamientoDistancia.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("ejercicioEntrenamiento",(EjercicioDistancia) ejercicioEntrenamiento);
        intent.putExtras(bundle);
        startActivityForResult(intent, Utils.REQUEST_CODE_EJERCICIO_DISTANCIA_EDITAR);

    }
    public void abrirPopUpCrearEjercicioDuracion(EjercicioEntrenamiento ejercicioEntrenamiento, int posicion){

        posicionEditar = posicion;
        Intent intent;

        if(!ejercicioEntrenamiento.getEjercicio().getNombre().equals("Descanso")){
            intent = new Intent(paraBorrar.this, PopEditarEjercicioEntrenamientoTiempo.class);
        }
        else{
            intent = new Intent(paraBorrar.this, PopEditarEjercicioEntrenamientoDescanso.class);
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("ejercicioEntrenamiento",(EjercicioDescanso) ejercicioEntrenamiento);
        intent.putExtras(bundle);
        startActivityForResult(intent, Utils.REQUEST_CODE_EJERCICIO_TIEMPO_EDITAR);

    }
    public void abrirPopUpCrearEjercicioRepeticion(EjercicioEntrenamiento ejercicioEntrenamiento, int posicion){

        posicionEditar = posicion;
        Intent intent = new Intent(paraBorrar.this, PopEditarEjercicioEntrenamientoRepeticion.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("ejercicioEntrenamiento",(EjercicioRepeticiones) ejercicioEntrenamiento);
        intent.putExtras(bundle);
        startActivityForResult(intent, Utils.REQUEST_CODE_EJERCICIO_REPETICION_EDITAR);

    }
    public void eliminarEjercicio(EjercicioEntrenamiento ejercicioEntrenamiento) {

        ejerciciosList.remove(ejercicioEntrenamiento);
        ejerciciosEntrenamientoAdapter.notifyDataSetChanged();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            switch(requestCode) {
                case (Utils.REQUEST_CODE_BUSCAR_EJERCICIO) : {
                    adicionarEjercicio((EjercicioEntrenamiento) data.getExtras().getSerializable("ejercicioEntrenamiento"));
                    break;
                }
                case (Utils.REQUEST_CODE_EJERCICIO_DESCANSO):{
                    adicionarEjercicio((EjercicioEntrenamiento) data.getExtras().getSerializable("ejercicioEntrenamiento"));
                    break;
                }
                case (Utils.REQUEST_CODE_EJERCICIO_REPETICION_EDITAR):{
                    editarEjercicio((EjercicioEntrenamiento) data.getExtras().getSerializable("ejercicioEntrenamiento"));
                    ejerciciosEntrenamientoAdapter.notifyDataSetChanged();
                    break;
                }
                case (Utils.REQUEST_CODE_EJERCICIO_TIEMPO_EDITAR):{
                    editarEjercicio((EjercicioEntrenamiento) data.getExtras().getSerializable("ejercicioEntrenamiento"));
                    ejerciciosEntrenamientoAdapter.notifyDataSetChanged();
                    break;
                }
                case (Utils.REQUEST_CODE_EJERCICIO_DISTANCIA_EDITAR):{
                    editarEjercicio((EjercicioEntrenamiento) data.getExtras().getSerializable("ejercicioEntrenamiento"));
                    ejerciciosEntrenamientoAdapter.notifyDataSetChanged();
                    break;
                }
            }
        }

    }

    private void editarEjercicio(EjercicioEntrenamiento ejercicioEntrenamiento) {

        ejerciciosList.set(posicionEditar, ejercicioEntrenamiento);
        ejerciciosEntrenamientoAdapter.notifyDataSetChanged();

    }

    private void adicionarEjercicio(EjercicioEntrenamiento ejercicioEntrenamiento) {

        ejerciciosList.add(ejercicioEntrenamiento);
        ejerciciosEntrenamientoAdapter.notifyDataSetChanged();

    }
}
