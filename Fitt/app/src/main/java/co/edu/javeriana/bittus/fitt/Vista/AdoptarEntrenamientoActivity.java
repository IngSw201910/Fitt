package co.edu.javeriana.bittus.fitt.Vista;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import java.util.Calendar;
import co.edu.javeriana.bittus.fitt.R;

public class AdoptarEntrenamientoActivity extends AppCompatActivity {

    private EditText hora;
    private Spinner dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adoptar_entrenamiento);

        hora = findViewById(R.id.horaEntrenamiento);
        dia = findViewById(R.id.selectorDia);

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


    }
}
