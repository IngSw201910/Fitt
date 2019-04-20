package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import co.edu.javeriana.bittus.fitt.Modelo.Rutina;
import co.edu.javeriana.bittus.fitt.Modelo.Sesion;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;

public class CrearRutinaActivity extends AppCompatActivity {

    private Button siguienteB;
    private Spinner dificultadSpin;
    private Spinner descansoSpin;

    private TextView nombreRutinaT;
    private TextView descripcionRutinaT;
    private TextView frecuenciaT;

    private RadioButton publicaRB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_rutina);

        siguienteB = (Button) findViewById(R.id.buttonSiguienteCrearRutina);
        dificultadSpin = findViewById(R.id.spinner2);
        descansoSpin = findViewById(R.id.spinner3);
        nombreRutinaT = findViewById(R.id.nombreRutina);
        descripcionRutinaT = findViewById(R.id.descripcionRutina);
        frecuenciaT = findViewById(R.id.duracionRutina);
        publicaRB = findViewById(R.id.radioButton2);

        List<String> stringDificultadList = new ArrayList<>();
        String[] strDificultad = new String[] {"Baja", "Media", "Alta"};
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
        startActivityForResult(new Intent(CrearRutinaActivity.this, CrearRutinaSesionesActivity.class),Utils.REQUEST_CODE_CREAR_RUTINA_SESIONES);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== Utils.REQUEST_CODE_CREAR_RUTINA_SESIONES&&data!=null)
        {
            List<Sesion> sesionList = (List<Sesion>) data.getExtras().getSerializable("sesiones");

            String nombreRutina = nombreRutinaT.getText().toString();
            String descripcion = descripcionRutinaT.getText().toString();
            String sFrecuencua = frecuenciaT.getText().toString();
            String dificultad = (String) dificultadSpin.getSelectedItem();
            boolean publica = publicaRB.isChecked();
            String sDiasDescanso = (String) descansoSpin.getSelectedItem();

            int frecuencia = Integer.parseInt(sFrecuencua);
            int diasDescanso = Integer.parseInt(sDiasDescanso);

            Rutina rutina = new Rutina(0,diasDescanso,descripcion,dificultad,publica,nombreRutina,frecuencia);
            rutina.setSesionList(sesionList);


            //está es la rutina que toca subir a firebase

            Log.i("Rutina", nombreRutina + " "+ frecuencia+ " "+descripcion);


            finish();
        }
    }

}


