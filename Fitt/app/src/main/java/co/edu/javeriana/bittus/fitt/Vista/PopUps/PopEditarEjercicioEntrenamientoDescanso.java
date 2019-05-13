package co.edu.javeriana.bittus.fitt.Vista.PopUps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDescanso;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.StringsMiguel;

public class PopEditarEjercicioEntrenamientoDescanso extends Activity {


    private ImageButton aceptarButton;
    private ImageButton cancelarButton;

    private EditText duracionT;
    private EjercicioDescanso ejercicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_agregar_ejercicio_sesion_descanso);

        aceptarButton = (ImageButton) findViewById(R.id.buttonAceptar);
        cancelarButton = (ImageButton) findViewById(R.id.imageButtonCancelar);

        duracionT = (EditText)findViewById(R.id.editTextDuracion);


        Bundle bundle = this.getIntent().getExtras();

        ejercicio = (EjercicioDescanso) bundle.getSerializable(StringsMiguel.LLAVE_EJERCICIO_ENTRENAMIENTO);
        duracionT.setText(Integer.toString(ejercicio.getDuracion()));
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width/1.1), (int) (height/2.4));

        aceptarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarEjercicioSesion();
            }
        });

        cancelarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
    private void editarEjercicioSesion() {
        String sDuracion = duracionT.getText().toString();
        int duracion = Integer.parseInt(sDuracion);
        if(sDuracion.isEmpty()){
            duracionT.setError(StringsMiguel.CAMPO_OBLIGATORIO);
        }else {
            ejercicio.setDuracion(duracion);

            Intent intent = this.getIntent();
            Bundle bundle = intent.getExtras();
            bundle.putSerializable(StringsMiguel.LLAVE_EJERCICIO_ENTRENAMIENTO,ejercicio);
            intent.putExtras(bundle);


            if (getParent() == null) {
                setResult(Activity.RESULT_OK, intent);
            } else {
                getParent().setResult(Activity.RESULT_OK, intent);
            }
            finish();
        }

    }
}
