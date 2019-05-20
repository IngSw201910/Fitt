package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

import co.edu.javeriana.bittus.fitt.Modelo.Entrenador;
import co.edu.javeriana.bittus.fitt.Modelo.Usuario;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.PersistenciaFirebase;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;
import co.edu.javeriana.bittus.fitt.Utilidades.StringsMiguel;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;
import co.edu.javeriana.bittus.fitt.Utilidades.UtilsMiguel;

public class RegistroEntrenadorActivity extends AppCompatActivity {


    private ImageView imageViewTitulo;

    private ImageButton imageButtonTomarFoto;
    private ImageButton imageButtonCargarFoto;

    private Button imageButtonRegistrarEntrenador;

    private EditText editTextDescripcion;
    private EditText editTextPorqueElegir;
    private EditText editTextNombreTitulo;

    private FirebaseAuth firebaseAuth;
    private Bitmap bitmapFoto;

    private Usuario usuario;

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
        editTextNombreTitulo = (EditText) findViewById(R.id.editTextNombreDelTitulo);

        firebaseAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        usuario = (Usuario) bundle.getSerializable(StringsMiguel.LLAVE_USUARIO);

        imageButtonCargarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.cargarFotoDesdeCamara(RegistroEntrenadorActivity.this, UtilsMiguel.REQUEST_CODE_UPLOAD_PHOTO);
            }
        });
        imageButtonTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.tomarFotoDesdeCamara(RegistroEntrenadorActivity.this, UtilsMiguel.REQUEST_CODE_TAKE_PHOTO);
            }
        });

        imageButtonRegistrarEntrenador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarEntrenador();
            }
        });

    }

    private void registrarEntrenador(){

        boolean completo = true;

        String descripcion = editTextDescripcion.getText().toString();
        String porQueElegirme = editTextPorqueElegir.getText().toString();
        String nombreDelTitulo = editTextNombreTitulo.getText().toString();

        if(descripcion.isEmpty()){
            completo = false;
            editTextDescripcion.setError(StringsMiguel.CAMPO_OBLIGATORIO);
        }
        if(porQueElegirme.isEmpty()){
            completo = false;
            editTextPorqueElegir.setError(StringsMiguel.CAMPO_OBLIGATORIO);
        }
        if(nombreDelTitulo.isEmpty()){
            completo = false;
            editTextNombreTitulo.setError(StringsMiguel.CAMPO_OBLIGATORIO);
        }
        if(bitmapFoto==null){
            int duracion = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(getApplicationContext(), StringsMiguel.SIN_FOTO,duracion);
            toast.show();
            completo = false;
        }
        if(completo){
            FirebaseUser user = firebaseAuth.getCurrentUser();
            Uri uriFoto = UtilsMiguel.getImageUri(RegistroEntrenadorActivity.this,bitmapFoto, user.getUid());
            PersistenciaFirebase.subirArchivoFirebase(RutasBaseDeDatos.RUTA_FOTO_TITULOS, user.getUid(), uriFoto);
            Entrenador entrenador = new Entrenador(usuario.getNombre(), usuario.getCorreo(), usuario.getDireccionFoto(), usuario.getFechaNacimiento(), usuario.getSexo(), usuario.getAltura(), usuario.getPeso(), descripcion, nombreDelTitulo, RutasBaseDeDatos.RUTA_FOTO_TITULOS+user.getUid(), porQueElegirme);
            PersistenciaFirebase.almacenarInformacionConRuta(RutasBaseDeDatos.RUTA_USUARIOS+user.getUid(), entrenador);

            Intent intentMenuPrincipal = new Intent(RegistroEntrenadorActivity.this, MenuPrincipalUsuarioFragment.class);
            Toast.makeText(getApplicationContext(), StringsMiguel.REGISTRO_USUARIO_CORRECTO,Toast.LENGTH_LONG).show();
            startActivity(intentMenuPrincipal);
            finish();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if(requestCode== UtilsMiguel.REQUEST_CODE_TAKE_PHOTO && resultCode==RESULT_OK){
            Bundle extras = data.getExtras();
            bitmapFoto = (Bitmap) extras.get("data");
            imageViewTitulo.setImageBitmap(bitmapFoto);
        }else if(requestCode == UtilsMiguel.REQUEST_CODE_UPLOAD_PHOTO  && resultCode==RESULT_OK){
            Uri path = data.getData();
            try {
                bitmapFoto = MediaStore.Images.Media.getBitmap(this.getContentResolver(),path);
                imageViewTitulo.setImageBitmap(bitmapFoto);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
