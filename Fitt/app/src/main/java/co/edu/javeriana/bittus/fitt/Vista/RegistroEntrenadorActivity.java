package co.edu.javeriana.bittus.fitt.Vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import co.edu.javeriana.bittus.fitt.R;

public class RegistroEntrenadorActivity extends AppCompatActivity {


    private ImageView imageViewTitulo;

    private ImageButton imageButtonTomarFoto;
    private ImageButton imageButtonCargarFoto;

    private Button imageButtonRegistrarEntrenador;

    private EditText editTextDescripcion;
    private EditText editTextPorqueElegir;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_entrenador);



        imageViewTitulo = (ImageView) findViewById(R.id.imageViewTitulo);

        imageButtonTomarFoto = (ImageButton) findViewById(R.id.imageButtonTomarFoto);
        imageButtonCargarFoto = (ImageButton) findViewById(R.id.imageButtonCargarFoto);
        imageButtonRegistrarEntrenador = (Button) findViewById(R.id.buttonRegistrarse);

        editTextDescripcion = (EditText) findViewById(R.id.editTextExperiencia);
        editTextPorqueElegir = (EditText) findViewById(R.id.editTextPorqueElegirte);







    }
}
