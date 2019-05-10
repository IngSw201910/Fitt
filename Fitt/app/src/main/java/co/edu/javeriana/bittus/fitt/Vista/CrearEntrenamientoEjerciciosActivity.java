package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import co.edu.javeriana.bittus.fitt.Adapters.EjerciciosEntrenamientoAdapter;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDescanso;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDistancia;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioEntrenamiento;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioRepeticiones;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioTiempo;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.BtnClickListenerRow;
import co.edu.javeriana.bittus.fitt.Utilidades.StringsMiguel;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;
import co.edu.javeriana.bittus.fitt.Vista.PopUps.PopCrearEjercicioEntrenamientoDescanso;
import co.edu.javeriana.bittus.fitt.Vista.PopUps.PopEditarEjercicioEntrenamientoDescanso;
import co.edu.javeriana.bittus.fitt.Vista.PopUps.PopEditarEjercicioEntrenamientoDistancia;
import co.edu.javeriana.bittus.fitt.Vista.PopUps.PopEditarEjercicioEntrenamientoRepeticion;
import co.edu.javeriana.bittus.fitt.Vista.PopUps.PopEditarEjercicioEntrenamientoTiempo;

public class CrearEntrenamientoEjerciciosActivity extends AppCompatActivity {


    private ImageButton imageButtonGuardar;
    private ImageButton imageButtonAdicionarEjercicio;
    private ImageButton imageButtonAdicionarDescanso;
    private ListView listViewEjercicios;
    private EjerciciosEntrenamientoAdapter adapterEjercicios;
    private List<EjercicioEntrenamiento> listEjercicios;
    private int positionEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_entrenamiento_ejercicios);






        imageButtonGuardar = (ImageButton) findViewById(R.id.imageButtonGuardarEntrenamiento);
        listViewEjercicios = (ListView) findViewById(R.id.listViewEjerciciosEntrenamiento);
        imageButtonAdicionarEjercicio = (ImageButton)findViewById(R.id.imageButtonAdicionarEjercicioEntrenamiento);
        imageButtonAdicionarDescanso = (ImageButton)findViewById(R.id.imageButtonAdicionarDescanso);


        listEjercicios = new ArrayList<EjercicioEntrenamiento>();


        adapterEjercicios = new EjerciciosEntrenamientoAdapter(CrearEntrenamientoEjerciciosActivity.this, listEjercicios, new BtnClickListenerRow() {
            @Override
            public void onBtnClickEdit(int position) {
                positionEditar = position;
                editarEjercicioEntrenamiento(listEjercicios.get(position));
            }

            @Override
            public void onBtnClickDelete(int position) {
                eliminarEjercicioEntrenamiento(listEjercicios.get(position));
            }
        });





        listViewEjercicios.setAdapter(adapterEjercicios);

        imageButtonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finalizar();

            }
        });

        imageButtonAdicionarEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarEjercicio();
            }
        });

        imageButtonAdicionarDescanso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarEjercicioDescanso();
            }
        });
    }

    private void adicionarEjercicioDescanso() {
        Intent intent = new Intent(new Intent(CrearEntrenamientoEjerciciosActivity.this, PopCrearEjercicioEntrenamientoDescanso.class));

        startActivityForResult(intent, Utils.REQUEST_CODE_EJERCICIO_DESCANSO);

    }

    private void finalizar() {

        if(listEjercicios.isEmpty()){
            int duracion = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getApplicationContext(), StringsMiguel.LISTRA_EJERCICIOS_VACIOS,duracion);
            toast.show();
        }else{
            Intent intent=this.getIntent();

            Bundle bundle = new Bundle();
            bundle.putSerializable(StringsMiguel.LLAVE_EJERCICIOS_ENTRENAMIENTO, (Serializable) listEjercicios);
            intent.putExtras(bundle);
            setResult(Utils.REQUEST_CODE_CREAR_ENTRENAMIENTO_EJERCICIOS,intent);

            int duracion = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(getApplicationContext(), StringsMiguel.CREACION_RUTINA_EXITOSA,duracion);
            toast.show();

            finish();


        }

    }
    private void buscarEjercicio(){
        Intent intent = new Intent(new Intent(CrearEntrenamientoEjerciciosActivity.this, BuscarEjercicioActivity.class));

        startActivityForResult(intent, Utils.REQUEST_CODE_BUSCAR_EJERCICIO);



    }
    public void editarEjercicioEntrenamiento(EjercicioEntrenamiento ejercicioEntrenamiento) {

        if (ejercicioEntrenamiento.getEjercicio().getTipo().equals(StringsMiguel.EJERCICIO_TIPO_DISTANCIA)) {
            abrirPopUpEditarEjercicioDistancia(ejercicioEntrenamiento);
        } else if (ejercicioEntrenamiento.getEjercicio().getTipo().equals(StringsMiguel.EJERCICIO_TIPO_REPETICIÓN)) {
            abrirPopUpEditarEjercicioRepeticiones(ejercicioEntrenamiento);
        } else if (ejercicioEntrenamiento.getEjercicio().getTipo().equals(StringsMiguel.EJERCICIO_TIPO_DESCANSO)) {
            abrirPopUpEditarEjercicioDescanso(ejercicioEntrenamiento);
        }  else if (ejercicioEntrenamiento.getEjercicio().getTipo().equals(StringsMiguel.EJERCICIO_TIPO_TIEMPO)) {
            abrirPopUpEditarEjercicioTiempo(ejercicioEntrenamiento);
        }

    }
    public void abrirPopUpEditarEjercicioDistancia(EjercicioEntrenamiento ejercicioEntrenamiento){
        Intent intent = new Intent(CrearEntrenamientoEjerciciosActivity.this, PopEditarEjercicioEntrenamientoDistancia.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(StringsMiguel.LLAVE_EJERCICIO_ENTRENAMIENTO,(EjercicioDistancia) ejercicioEntrenamiento);
        intent.putExtras(bundle);
        startActivityForResult(intent, Utils.REQUEST_CODE_EJERCICIO_DISTANCIA_EDITAR);
    }

    public void abrirPopUpEditarEjercicioDescanso(EjercicioEntrenamiento ejercicioEntrenamiento){


        Intent intent = new Intent(CrearEntrenamientoEjerciciosActivity.this, PopEditarEjercicioEntrenamientoDescanso.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(StringsMiguel.LLAVE_EJERCICIO_ENTRENAMIENTO,(EjercicioDescanso) ejercicioEntrenamiento);
        intent.putExtras(bundle);
        startActivityForResult(intent, Utils.REQUEST_CODE_EJERCICIO_DESCANSO_EDITAR);
    }

    public void abrirPopUpEditarEjercicioTiempo(EjercicioEntrenamiento ejercicioEntrenamiento){
        Intent intent = new Intent(CrearEntrenamientoEjerciciosActivity.this, PopEditarEjercicioEntrenamientoTiempo.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(StringsMiguel.LLAVE_EJERCICIO_ENTRENAMIENTO,(EjercicioTiempo) ejercicioEntrenamiento);
        intent.putExtras(bundle);
        startActivityForResult(intent, Utils.REQUEST_CODE_EJERCICIO_TIEMPO_EDITAR);
    }

    public void abrirPopUpEditarEjercicioRepeticiones(EjercicioEntrenamiento ejercicioEntrenamiento){
        Intent intent = new Intent(CrearEntrenamientoEjerciciosActivity.this, PopEditarEjercicioEntrenamientoRepeticion.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(StringsMiguel.LLAVE_EJERCICIO_ENTRENAMIENTO,(EjercicioRepeticiones) ejercicioEntrenamiento);
        intent.putExtras(bundle);
        startActivityForResult(intent, Utils.REQUEST_CODE_EJERCICIO_REPETICION_EDITAR);
    }



    public void eliminarEjercicioEntrenamiento(EjercicioEntrenamiento    ejercicioEntrenamiento) {

        listEjercicios.remove(ejercicioEntrenamiento);
        adapterEjercicios.notifyDataSetChanged();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            switch(requestCode) {
                case (Utils.REQUEST_CODE_BUSCAR_EJERCICIO) : {
                    adicionarEjercicio((EjercicioEntrenamiento) data.getExtras().getSerializable(StringsMiguel.LLAVE_EJERCICIO_ENTRENAMIENTO));
                    break;
                }
                case (Utils.REQUEST_CODE_EJERCICIO_DESCANSO):{

                    adicionarEjercicio((EjercicioEntrenamiento) data.getExtras().getSerializable(StringsMiguel.LLAVE_EJERCICIO_ENTRENAMIENTO));
                    break;
                }
                case (Utils.REQUEST_CODE_EJERCICIO_REPETICION_EDITAR):{
                    editarEjercicio((EjercicioEntrenamiento) data.getExtras().getSerializable(StringsMiguel.LLAVE_EJERCICIO_ENTRENAMIENTO));
                    adapterEjercicios.notifyDataSetChanged();
                    break;
                }
                case (Utils.REQUEST_CODE_EJERCICIO_TIEMPO_EDITAR):{
                    editarEjercicio((EjercicioEntrenamiento) data.getExtras().getSerializable(StringsMiguel.LLAVE_EJERCICIO_ENTRENAMIENTO));
                    adapterEjercicios.notifyDataSetChanged();
                    break;
                }
                case (Utils.REQUEST_CODE_EJERCICIO_DESCANSO_EDITAR):{
                    editarEjercicio((EjercicioEntrenamiento) data.getExtras().getSerializable(StringsMiguel.LLAVE_EJERCICIO_ENTRENAMIENTO));
                    adapterEjercicios.notifyDataSetChanged();
                    break;
                }
                case (Utils.REQUEST_CODE_EJERCICIO_DISTANCIA_EDITAR):{
                    editarEjercicio((EjercicioEntrenamiento) data.getExtras().getSerializable(StringsMiguel.LLAVE_EJERCICIO_ENTRENAMIENTO));
                    adapterEjercicios.notifyDataSetChanged();
                    break;
                }
            }
        }
    }
    private void adicionarEjercicio(EjercicioEntrenamiento ejercicioEntrenamiento) {

        listEjercicios.add(ejercicioEntrenamiento);
        adapterEjercicios.notifyDataSetChanged();

    }
    private void editarEjercicio(EjercicioEntrenamiento ejercicioEntrenamiento) {

        listEjercicios.set(positionEditar, ejercicioEntrenamiento);
        adapterEjercicios.notifyDataSetChanged();

    }
}
