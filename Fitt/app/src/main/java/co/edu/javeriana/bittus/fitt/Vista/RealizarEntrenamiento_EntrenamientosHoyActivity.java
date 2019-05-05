package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.UtilsJhonny;

public class RealizarEntrenamiento_EntrenamientosHoyActivity extends AppCompatActivity {

    //Esta actividad muestra los entrenamientos que tiene programado el usuario para ese día

    private TextView diaSemana;
    private TextView fecha;

    private ListView entrenamientosL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_entrenamiento_entrenamientoshoy);


        diaSemana = findViewById(R.id.diaSemanaEntrenamientosTV);
        fecha = findViewById(R.id.fechaMisEntrenamientosTV);
        entrenamientosL = findViewById(R.id.listViewEntrenamientosHoy);


        // por ahora esta este evento para probar
        //DEBE SER CAMBIADO para desencadenar el evento cuando se toque un elemento de la lista
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RealizarEntrenamiento_EntrenamientosHoyActivity.this, RealizarEntrenamientoActivity.class));
            }
        });

        inicializarVista ();
    }

    private void inicializarVista(){
        Calendar c = Calendar.getInstance();
        diaSemana.setText(UtilsJhonny.diaSemana(c.get(Calendar.DAY_OF_WEEK)));

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String fechaFormateada = df.format(c.getTime());

        fecha.setText(fechaFormateada);

    }




}
