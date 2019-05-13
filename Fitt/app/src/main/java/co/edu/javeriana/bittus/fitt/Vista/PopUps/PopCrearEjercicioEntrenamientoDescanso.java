package co.edu.javeriana.bittus.fitt.Vista.PopUps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDescanso;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.StringsMiguel;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;

public class PopCrearEjercicioEntrenamientoDescanso extends Activity {


    private ImageButton aceptarButton;
    private ImageButton cancelarButton;

    private EditText duracionT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_agregar_ejercicio_sesion_descanso);

        aceptarButton = (ImageButton) findViewById(R.id.imageButtonAceptar);
        cancelarButton = (ImageButton) findViewById(R.id.imageButtonCancelar);

        duracionT = (EditText)findViewById(R.id.editTextDuracion);



        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width/1.1), (int) (height/2.4));

        aceptarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearEjercicioDescanso();
            }
        });

        cancelarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }
    private void crearEjercicioDescanso() {


        String sDuracion = duracionT.getText().toString();


        if(sDuracion.isEmpty()){
            duracionT.setError(StringsMiguel.CAMPO_OBLIGATORIO);
        }
        else{
            int duracion = Integer.parseInt(sDuracion);

            EjercicioDescanso ejercicioDescanso = new EjercicioDescanso(Utils.EJERCICIO_DESCANSO,duracion);

            Intent intent = this.getIntent();
            Bundle bundle = new Bundle();
            bundle.putSerializable(StringsMiguel.LLAVE_EJERCICIO_ENTRENAMIENTO, ejercicioDescanso);
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
