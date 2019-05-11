package co.edu.javeriana.bittus.fitt.Vista;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.IOException;
import java.util.Date;

import co.edu.javeriana.bittus.fitt.Modelo.Usuario;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.DatePickerFragment;
import co.edu.javeriana.bittus.fitt.Utilidades.Permisos;
import co.edu.javeriana.bittus.fitt.Utilidades.StringsMiguel;
import co.edu.javeriana.bittus.fitt.Utilidades.UtilsMiguel;

public class RegistroUsuarioActivity extends AppCompatActivity implements View.OnClickListener{



    private EditText editTextFechaNacimiento;
    private EditText editTextNombre;
    private EditText editTextCorreo;
    private EditText editTextContraseña;
    private EditText editTextPeso;
    private EditText editTextAltura;
    private RadioButton radioButtonHombre;
    private RadioButton radioButtonMujer;
    private RadioButton radioButtonOtro;
    private ImageButton imageButtonTomarFoto;
    private ImageButton imageButtonCargarFoto;

    private Button buttonRegistrarse;

    private ImageView imageViewFotoPerfil;
    private Bitmap bitmapFoto;

    private Date fechaNacimiento;

    private boolean esEntrenador = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        Intent intent = getIntent();
        if(intent.getExtras()!=null){
            esEntrenador = true;

        }


        editTextFechaNacimiento = (EditText) findViewById(R.id.editTextFechaDeNacimiento);
        editTextFechaNacimiento.setOnClickListener(this);

        editTextNombre = (EditText) findViewById(R.id.editTextNombre);
        editTextCorreo = (EditText) findViewById(R.id.editTextCorreo);
        editTextContraseña = (EditText) findViewById(R.id.editTextContraseña);
        editTextPeso = (EditText) findViewById(R.id.editTextPeso);
        editTextAltura = (EditText) findViewById(R.id.editTextAltura);

        radioButtonHombre = (RadioButton)findViewById(R.id.radioButtonHombre);
        radioButtonMujer = (RadioButton)findViewById(R.id.radioButtonMujer);
        radioButtonOtro = (RadioButton)findViewById(R.id.radioButtonOtro);

        imageButtonTomarFoto = (ImageButton) findViewById(R.id.imageButtonTomarFoto);
        imageButtonCargarFoto = (ImageButton) findViewById(R.id.imageButtonCargarFoto);

        imageViewFotoPerfil = (ImageView) findViewById(R.id.imageViewPerfil);

        buttonRegistrarse = (Button) findViewById(R.id.buttonRegistrarse);

        buttonRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });
        imageButtonTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tomarFoto();
            }
        });
        imageButtonCargarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarFoto();
            }
        });
    }

    private void cargarFoto() {
        Permisos.requestPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE,"Es necesario para carga una foto", UtilsMiguel.REQUEST_CODE_PERMISSION);




        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.createChooser(intent, StringsMiguel.SELECCIONAR_APLICACION);
        startActivityForResult(intent, UtilsMiguel.REQUEST_CODE_UPLOAD_PHOTO);

    }

    private void tomarFoto() {
        Permisos.requestPermission(this, Manifest.permission.CAMERA,"Es necesario para tomar fotos", UtilsMiguel.REQUEST_CODE_PERMISSION);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, UtilsMiguel.REQUEST_CODE_TAKE_PHOTO);


        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if(requestCode== UtilsMiguel.REQUEST_CODE_TAKE_PHOTO && resultCode==RESULT_OK){
            Bundle extras = data.getExtras();
            bitmapFoto = (Bitmap) extras.get("data");
            imageViewFotoPerfil.setImageBitmap(bitmapFoto);
        }else if(requestCode == UtilsMiguel.REQUEST_CODE_UPLOAD_PHOTO  && resultCode==RESULT_OK){
            Uri path = data.getData();
            try {
                bitmapFoto = MediaStore.Images.Media.getBitmap(this.getContentResolver(),path);
                imageViewFotoPerfil.setImageBitmap(bitmapFoto);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
    private void registrarUsuario() {

        boolean completo = true;

        String nombre = editTextNombre.getText().toString();
        String correo = editTextCorreo.getText().toString();
        String contraseña = editTextContraseña.getText().toString();
        String sPeso = editTextPeso.getText().toString();
        String sAltura = editTextAltura.getText().toString();
        String sexo = "Hombre";

        if(nombre.isEmpty()){
            editTextNombre.setError(StringsMiguel.CAMPO_OBLIGATORIO);
            completo = false;
        }
        if(correo.isEmpty()){
            editTextCorreo.setError(StringsMiguel.CAMPO_OBLIGATORIO);
            completo = false;
        }
        if(contraseña.isEmpty()){
            editTextContraseña.setError(StringsMiguel.CAMPO_OBLIGATORIO);
            completo = false;
        }
        if(sPeso.isEmpty()){
            editTextPeso.setError(StringsMiguel.CAMPO_OBLIGATORIO);
            completo = false;
        }
        if(sAltura.isEmpty()){
            editTextAltura.setError(StringsMiguel.CAMPO_OBLIGATORIO);
            completo = false;
        }
        if(editTextFechaNacimiento.getText().toString().isEmpty()){
            editTextFechaNacimiento.setError(StringsMiguel.CAMPO_OBLIGATORIO);
            completo = false;
        }
        if(bitmapFoto==null){
            int duracion = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(getApplicationContext(), StringsMiguel.SIN_FOTO,duracion);
            toast.show();
            completo = false;
        }
        if(completo){
            if(radioButtonMujer.isChecked()){
                sexo = "Mujer";
            }else if(radioButtonOtro.isChecked()){
                sexo = "Otro";
            }
            Usuario usuarioNuevo = new Usuario(nombre,correo,contraseña,"dirección",fechaNacimiento,sexo,Float.parseFloat(sAltura),Float.parseFloat(sPeso));




            Intent intent = new Intent(RegistroUsuarioActivity.this, MenuPrincipalUsuarioFragment.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(StringsMiguel.LLAVE_USUARIO,usuarioNuevo);
            intent.putExtras(bundle);
            startActivity(intent);

            //Aquí va el llamdo para registrarlo en firebase
            //el bitmap tiene la foto
            //recordar incluir que debe iniciar sesión inmediatamente
            int duracion = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(getApplicationContext(), StringsMiguel.REGISTRO_USUARIO_CORRECTO+nombre,duracion);
            toast.show();


        }

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
                fechaNacimiento = new Date(year,month,day);
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
