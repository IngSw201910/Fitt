package co.edu.javeriana.bittus.fitt.Vista;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

import co.edu.javeriana.bittus.fitt.Modelo.Entrenamiento;
import co.edu.javeriana.bittus.fitt.Modelo.EntrenamientoAdoptado;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.PersistenciaFirebase;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;

public class AdoptarEntrenamientoActivity extends AppCompatActivity {

    private EditText hora;
    private Spinner dia;

    private TextView nombre;
    private TextView dificultad;
    private TextView descripcion;

    private Entrenamiento entrenamiento;

    private Button adoptarEntrenamientoB;

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseUser user = mAuth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adoptar_entrenamiento);

        hora = findViewById(R.id.horaEntrenamiento);
        dia = findViewById(R.id.selectorDia);

        nombre = findViewById(R.id.nombreEntrenamiento);
        dificultad = findViewById(R.id.dificultadEntrenamiento);
        descripcion = findViewById(R.id.descripcionEntrenamiento);

        adoptarEntrenamientoB = findViewById(R.id.adoptarEntrenamiento);




        entrenamiento = (Entrenamiento) getIntent().getSerializableExtra("entrenamiento");

        nombre.setText("Nombre: "+entrenamiento.getNombre());
        dificultad.setText("Dificultad: "+entrenamiento.getDificultad());
        descripcion.setText("Descripción: "+ entrenamiento.getDescripcion());





        hora.setShowSoftInputOnFocus(false);

        hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                int currentMinute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(AdoptarEntrenamientoActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        String amPm;
                        if (hourOfDay >= 12) {
                            if (hourOfDay > 12) {
                                hourOfDay -= 12;
                            }
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        hora.setText(String.format("%02d:%02d", hourOfDay, minutes) + " "+amPm);

                    }
                }, currentHour, currentMinute, false);
                timePickerDialog.setTitle("Seleccione la hora para realizar el entrenamiento");
                timePickerDialog.show();
            }
        });

        adoptarEntrenamientoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adoptarEntrenamiento();
            }
        });


    }

    private void adoptarEntrenamiento (){
        EntrenamientoAdoptado entrenamientoAdoptado = new EntrenamientoAdoptado ();
        entrenamientoAdoptado.setEntrenamiento(entrenamiento);
        entrenamientoAdoptado.setDia(dia.getSelectedItem().toString());
        entrenamientoAdoptado.setHora(hora.getText().toString());
        PersistenciaFirebase.almacenarInformacionConKey(RutasBaseDeDatos.getRutaEntrenamientosAdoptados()+user.getUid()+"/"+dia.getSelectedItem().toString()+"/", entrenamientoAdoptado );

        Toast.makeText(AdoptarEntrenamientoActivity.this, "Rutina adoptada exitosamente!", Toast.LENGTH_LONG).show();

        startActivity(new Intent(AdoptarEntrenamientoActivity.this, BuscarEntrenamientosActivity.class));
    }
}
