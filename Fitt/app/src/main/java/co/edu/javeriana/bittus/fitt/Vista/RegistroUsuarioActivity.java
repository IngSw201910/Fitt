package co.edu.javeriana.bittus.fitt.Vista;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.DatePickerFragment;

public class RegistroUsuarioActivity extends AppCompatActivity implements View.OnClickListener{



    private EditText editTextFechaNacimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        editTextFechaNacimiento = (EditText) findViewById(R.id.editTextFechaDeNacimiento);
        editTextFechaNacimiento.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editTextFechaDeNacimiento:
                mostrarDatePickerDialog();
                break;
        }
    }
    private void mostrarDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = dosDigitos(day )+ " / " + dosDigitos(month+1) + " / " + year;
                editTextFechaNacimiento.setText(selectedDate);

                Log.i("entro","entro");
            }
        });
        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }

    private String dosDigitos(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

}
