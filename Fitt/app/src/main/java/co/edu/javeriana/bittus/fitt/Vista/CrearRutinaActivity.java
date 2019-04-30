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

import co.edu.javeriana.bittus.fitt.Modelo.Entrenamiento;
import co.edu.javeriana.bittus.fitt.Modelo.Rutina;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;

public class CrearRutinaActivity extends AppCompatActivity {

    private ImageButton siguienteB;
    private Spinner dificultadSpin;
    private Spinner descansoSpin;

    private EditText nombreRutinaT;
    private EditText descripcionRutinaT;
    private EditText frecuenciaT;

    private RadioButton publicaRB;

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseUser user = mAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_rutina);

        siguienteB = (ImageButton) findViewById(R.id.buttonSiguienteCrearRutina);
        dificultadSpin = (Spinner)findViewById(R.id.spinner2);
        descansoSpin = (Spinner)findViewById(R.id.spinner3);
        nombreRutinaT = (EditText) findViewById(R.id.nombreRutina);
        descripcionRutinaT = (EditText)findViewById(R.id.descripcionRutina);
        frecuenciaT = (EditText)findViewById(R.id.duracionRutina);
        publicaRB = (RadioButton) findViewById(R.id.radioButton2);

        List<String> stringDificultadList = new ArrayList<>();
        String[] strDificultad = new String[] {"Fácil", "Media", "Difícil"};
        Collections.addAll(stringDificultadList, strDificultad);
        ArrayAdapter<String> comboAdapterDificultad = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, stringDificultadList);
        dificultadSpin.setAdapter(comboAdapterDificultad);


        List<String> stringDescansoList = new ArrayList<>();
        String[] strDescanso = new String[] {"1", "2", "3", "4","5","6"};
        Collections.addAll(stringDescansoList, strDescanso);
        ArrayAdapter<String> comboAdapterDescanso = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, stringDescansoList);
        descansoSpin.setAdapter(comboAdapterDescanso);




        siguienteB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguientePantalla();
            }
        });

    }

    private void siguientePantalla(){

        boolean completo = true;
        if(nombreRutinaT.getText().toString().isEmpty()){
            nombreRutinaT.setError("Campo obligatorio");
            completo = false;
        }
        if(descripcionRutinaT.getText().toString().isEmpty()){
            descripcionRutinaT.setError("Campo obligatorio");
            completo = false;
        }
        if(frecuenciaT.getText().toString().isEmpty()){
            frecuenciaT.setError("Campo obligatorio");
            completo = false;
        }

        if(completo){
            startActivityForResult(new Intent(CrearRutinaActivity.this, CrearRutinaEntrenamientoActivity.class),Utils.REQUEST_CODE_CREAR_RUTINA_SESIONES);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== Utils.REQUEST_CODE_CREAR_RUTINA_SESIONES&&data!=null)
        {
            List<Entrenamiento> entrenamientoList = (List<Entrenamiento>) data.getExtras().getSerializable("entrenamientos");





            String nombreRutina = nombreRutinaT.getText().toString();
            String descripcion = descripcionRutinaT.getText().toString();
            String sFrecuencua = frecuenciaT.getText().toString();
            String dificultad = (String) dificultadSpin.getSelectedItem();
            boolean publica = publicaRB.isChecked();
            String sDiasDescanso = (String) descansoSpin.getSelectedItem();

            int frecuencia = Integer.parseInt(sFrecuencua);
            int diasDescanso = Integer.parseInt(sDiasDescanso);

            Rutina rutina = new Rutina(0,diasDescanso,descripcion,dificultad,publica,nombreRutina,frecuencia);
            rutina.setEntrenamientoList(entrenamientoList);


            Utils.almacenarInformacionConKey(RutasBaseDeDatos.getRutaRutinas()+user.getUid()+"/", rutina);

            Log.i("Rutina", nombreRutina + " "+ frecuencia+ " "+descripcion);


            finish();
        }
    }

}


