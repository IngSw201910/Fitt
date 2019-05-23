package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import co.edu.javeriana.bittus.fitt.Modelo.EjercicioEntrenamiento;
import co.edu.javeriana.bittus.fitt.Modelo.Entrenamiento;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.PersistenciaFirebase;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;
import co.edu.javeriana.bittus.fitt.Utilidades.StringsMiguel;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;

public class EditarEntrenamientoActivity extends AppCompatActivity {

    private ImageButton ImageButtonSiguiente;
    private Spinner spinnerDificultad;
    private Spinner spinnerDescanso;

    private EditText editTextNombreEntrenamiento;
    private EditText editTextDescripcion;
    private EditText editTextDuracion;

    private Entrenamiento entrenamientoActual;
    private String llave;

    private RadioButton radioButtonPublica;
    private RadioButton radioButtonPrivada;

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseUser user = mAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_entrenamiento);

        ImageButtonSiguiente = (ImageButton) findViewById(R.id.imageButtonSiguienteCrearRutina);
        spinnerDificultad = (Spinner)findViewById(R.id.spinnerDificultad);
        spinnerDescanso = (Spinner)findViewById(R.id.spinnerDiasDescanso);
        editTextNombreEntrenamiento = (EditText) findViewById(R.id.editTextNombreEntrenamiento);
        editTextDescripcion = (EditText)findViewById(R.id.editTextDescripcionEntrenamiento);
        editTextDuracion = (EditText) findViewById(R.id.editTextDuracion);
        radioButtonPublica = (RadioButton) findViewById(R.id.radioButtonPublica);
        radioButtonPrivada = (RadioButton) findViewById(R.id.radioButtonPrivada);

        entrenamientoActual= (Entrenamiento) getIntent().getSerializableExtra(StringsMiguel.LLAVE_ENTRENAMIENTO);
        llave = (String) getIntent().getStringExtra(StringsMiguel.LLAVE_LLAVE);


        editTextNombreEntrenamiento.setText(entrenamientoActual.getNombre());
        editTextDescripcion.setText(entrenamientoActual.getDescripcion());
        editTextDuracion.setText(Integer.toString(entrenamientoActual.getDuracion()));




        List<String> stringDificultadList = new ArrayList<>();
        Collections.addAll(stringDificultadList, StringsMiguel.DIFICULTADES_ENTRENAMIENTOS);
        ArrayAdapter<String> comboAdapterDificultad = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, stringDificultadList);
        spinnerDificultad.setAdapter(comboAdapterDificultad);


        switch (entrenamientoActual.getDificultad()){

            case "Fácil":{
                spinnerDificultad.setSelection(0);
                break;
            }
            case "Media":{
                spinnerDificultad.setSelection(1);
                break;
            }
            case "Difícil":{
                spinnerDificultad.setSelection(2);
                break;
            }
        }


        List<String> stringDescansoList = new ArrayList<>();
        Collections.addAll(stringDescansoList, StringsMiguel.ENTRENAMIENTOS_DESCANSOS_STRING);
        ArrayAdapter<String> comboAdapterDescanso = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, stringDescansoList);
        spinnerDescanso.setAdapter(comboAdapterDescanso);

        switch (entrenamientoActual.getNumDiasDescanso()){

            case 0:{
                spinnerDescanso.setSelection(0);
                break;
            }
            case 1:{
                spinnerDescanso.setSelection(1);
                break;
            }
            case 2:{
                spinnerDescanso.setSelection(2);
                break;
            }
            case 3:{
                spinnerDescanso.setSelection(3);
                break;
            }
            case 4:{
                spinnerDescanso.setSelection(4);
                break;
            }
            case 5:{
                spinnerDescanso.setSelection(5);
                break;
            }
            case 6:{
                spinnerDescanso.setSelection(6);
                break;
            }

        }
        
        ImageButtonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguientePantalla();
            }
        });

    }

    private void siguientePantalla(){

        boolean completo = true;
        if(editTextNombreEntrenamiento.getText().toString().isEmpty()){
            editTextNombreEntrenamiento.setError(StringsMiguel.CAMPO_OBLIGATORIO);
            completo = false;
        }
        if(editTextDescripcion.getText().toString().isEmpty()){
            editTextDescripcion.setError(StringsMiguel.CAMPO_OBLIGATORIO);
            completo = false;
        }
        if(editTextDuracion.getText().toString().isEmpty()){
            editTextDuracion.setError(StringsMiguel.CAMPO_OBLIGATORIO);
            completo = false;
        }

        if(completo){
            startActivityForResult(new Intent(EditarEntrenamientoActivity.this, CrearEntrenamientoEjerciciosActivity.class),Utils.REQUEST_CODE_CREAR_ENTRENAMIENTO_EJERCICIOS);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== Utils.REQUEST_CODE_CREAR_ENTRENAMIENTO_EJERCICIOS &&data!=null)
        {
            List<EjercicioEntrenamiento> entrenamientoList = (List<EjercicioEntrenamiento>) data.getExtras().getSerializable(StringsMiguel.LLAVE_EJERCICIOS_ENTRENAMIENTO);

            String nombreRutina = editTextNombreEntrenamiento.getText().toString();
            String descripcion = editTextDescripcion.getText().toString();
            String dificultad = (String) spinnerDificultad.getSelectedItem();
            String sDuracion = editTextDuracion.getText().toString();

            boolean publica = radioButtonPublica.isChecked();
            String sDiasDescanso = (String) spinnerDescanso.getSelectedItem();


            int diasDescanso = Integer.parseInt(sDiasDescanso);
            int duracion = Integer.parseInt(sDuracion);


            Entrenamiento entrenamiento = new Entrenamiento(diasDescanso,descripcion,dificultad,publica,nombreRutina,duracion);
            entrenamiento.setEjercicioEntrenamientoList(entrenamientoList);


            String key = PersistenciaFirebase.almacenarInformacionConKey(RutasBaseDeDatos.RUTA_ENTRENAMIENTOS+user.getUid()+"/", entrenamiento);
            if (entrenamiento.isPublica())
                PersistenciaFirebase.almacenarInformacionConKeyPersonalizada(RutasBaseDeDatos.getRutaEntrenamientosPublicos(),entrenamiento, key);

            finish();
        }
    }

}


