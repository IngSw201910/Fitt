package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class CrearEntrenamientoActivity extends AppCompatActivity {

    private ImageButton ImageButtonSiguiente;
    private Spinner spinnerDificultad;
    private Spinner spinnerDescanso;

    private EditText editTextNombreEntrenamiento;
    private EditText editTextDescripcion;

    private RadioButton radioButtonPublica;

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
        radioButtonPublica = (RadioButton) findViewById(R.id.radioButtonPublica);

        List<String> stringDificultadList = new ArrayList<>();
        Collections.addAll(stringDificultadList, StringsMiguel.DIFICULTADES_ENTRENAMIENTOS);
        ArrayAdapter<String> comboAdapterDificultad = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, stringDificultadList);
        spinnerDificultad.setAdapter(comboAdapterDificultad);


        List<String> stringDescansoList = new ArrayList<>();
        Collections.addAll(stringDescansoList, StringsMiguel.ENTRENAMIENTOS_DESCANSOS_STRING);
        ArrayAdapter<String> comboAdapterDescanso = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, stringDescansoList);
        spinnerDescanso.setAdapter(comboAdapterDescanso);




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


        if(completo){
            startActivityForResult(new Intent(CrearEntrenamientoActivity.this, CrearEntrenamientoEjerciciosActivity.class),Utils.REQUEST_CODE_CREAR_ENTRENAMIENTO_EJERCICIOS);
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
            boolean publica = radioButtonPublica.isChecked();
            String sDiasDescanso = (String) spinnerDescanso.getSelectedItem();


            int diasDescanso = Integer.parseInt(sDiasDescanso);

            Entrenamiento entrenamiento = new Entrenamiento(0,diasDescanso,descripcion,dificultad,publica,nombreRutina);
            entrenamiento.setEjercicioEntrenamientoList(entrenamientoList);


            Utils.almacenarInformacionConKey(RutasBaseDeDatos.getRutaEntrenamientos()+user.getUid()+"/", entrenamiento);
            if (entrenamiento.isPublica())
                PersistenciaFirebase.almacenarInformacionConKey(RutasBaseDeDatos.getRutaEntrenamientosPublicos(),entrenamiento);

            finish();
        }
    }

}


