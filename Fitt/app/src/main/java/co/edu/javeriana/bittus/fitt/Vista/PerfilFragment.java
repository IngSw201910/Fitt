package co.edu.javeriana.bittus.fitt.Vista;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import co.edu.javeriana.bittus.fitt.Modelo.Usuario;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.Permisos;
import co.edu.javeriana.bittus.fitt.Utilidades.PersistenciaFirebase;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;
import co.edu.javeriana.bittus.fitt.Utilidades.StringsMiguel;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;
import co.edu.javeriana.bittus.fitt.Utilidades.UtilsMiguel;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static co.edu.javeriana.bittus.fitt.Utilidades.PersistenciaFirebase.descargarFotoYPonerEnImageView;

public class PerfilFragment extends Fragment {


    private ImageView fotoPerfil;

    private ImageButton editarNombre;
    private ImageButton editarCorreo;
    private ImageButton ediarNacimiento;
    private ImageButton editarPeso;
    private ImageButton editarAltura;
    private ImageButton cambiarFoto;
    private ImageButton tomarFoto;
    private Button seguidores;
    private Button siguiendo;

    private EditText nombre;
    private EditText correo;
    private EditText nacimiento;
    private EditText peso;
    private EditText altura;

    private CheckBox privacidad;

    private Bitmap bitmapFoto;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser mAuth;

    private TextView textViewDistancia;
    private TextView textViewPasos;
    private TextView textViewCalorias;


    private Usuario usuario;

    Bitmap imagen;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_perfil, null, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v = getView();

        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(RutasBaseDeDatos.RUTA_USUARIOS).child(mAuth.getUid());

        editarNombre =v.findViewById(R.id.imageButtonEditarNombre);
        editarCorreo =v.findViewById(R.id.imageButtonEdicarCorreo);
        ediarNacimiento =v.findViewById(R.id.imageButtonEditarNacieminnto);
        editarPeso =v.findViewById(R.id.imageButtonEditarPeso);
        editarAltura =v.findViewById(R.id.imageButtonEditarAltura);
        seguidores =v.findViewById(R.id.buttonSeguidoresUsuario);
        siguiendo =v.findViewById(R.id.buttonSiguiendo);
        fotoPerfil= v.findViewById(R.id.imageViewFotoPerfil);
        cambiarFoto= v.findViewById(R.id.imageButtonCargarFotoPerfil);
        tomarFoto= v.findViewById(R.id.imageButtonTomarFoto);
        textViewDistancia = v.findViewById(R.id.textViewDistanciaRecorrida);
        textViewPasos = v.findViewById(R.id.textViewPasosDados);
        textViewCalorias = v.findViewById(R.id.textViewCalorias);
        


        nombre =v.findViewById(R.id.editTextNombre);
        nombre.setEnabled(false);

        correo =v.findViewById(R.id.editTextCorreo);
        correo.setEnabled(false);


        nacimiento =v.findViewById(R.id.editTextNaciemiento);
        nacimiento.setEnabled(false);


        peso =v.findViewById(R.id.editTextPeso);
        peso.setEnabled(false);

        altura =v.findViewById(R.id.editTextAltura);
        altura.setEnabled(false);

        privacidad =v.findViewById(R.id.radioButtonPrivacidad);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                usuario = dataSnapshot.getValue(Usuario.class);
                nombre.setText(usuario.getNombre());
                correo.setText(usuario.getCorreo());
                peso.setText(String.valueOf(usuario.getPeso())+ " kg");
                altura.setText(String.valueOf(usuario.getAltura()) + " cm ");
                Date fecha=new Date();
                fecha.setYear(usuario.getFechaNacimiento().getYear());
                fecha.setMonth(usuario.getFechaNacimiento().getMonth());
                fecha.setDate(usuario.getFechaNacimiento().getDate());
                nacimiento.setText(fecha.getDate()+"/"+fecha.getMonth()+"/"+fecha.getYear());
                descargarFotoYPonerEnImageView(usuario.getDireccionFoto(),fotoPerfil);
                textViewDistancia.setText(Float.toString(usuario.getDistanciaRecorrida()) + "km");
                textViewPasos.setText(Float.toString(usuario.getPasosDados())+ "pasos");
                textViewCalorias.setText(Float.toString(usuario.getCaloriasQuemadas())+ "kcal");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        cambiarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarFoto();

            }
        });

        tomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tomarFoto();

            }
        });

        editarNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre.setEnabled(true);
                nombre.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        boolean procesado = false;

                        if (actionId == EditorInfo.IME_ACTION_DONE) {


                            if(nombre.getText()!=null){

                                String nombreUs= nombre.getText().toString();
                                usuario.setNombre(nombreUs);

                                myRef.setValue(usuario);
                                nombre.setText(nombreUs);
                                nombre.setEnabled(false);

                            }

                            procesado = true;
                        }
                        return procesado;
                    }
                });
            }
            }
        );

        editarCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correo.setEnabled(true);
                correo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        boolean procesado = false;

                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            // Mostrar mensaje

                            if(correo.getText()!=null){
                                String correoNew= correo.getText().toString();
                                usuario.setCorreo(correoNew);
                                myRef.setValue(usuario);
                                correo.setText(correoNew);
                                correo.setEnabled(false);
                                //Guardar nuevo nombre

                            }

                            procesado = true;
                        }
                        return procesado;
                    }
                });
            }
        });

        ediarNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nacimiento.setEnabled(true);
                nacimiento.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        boolean procesado = false;

                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            // Mostrar mensaje

                            if(nacimiento.getText()!=null){
                                String nacimientoNew= nacimiento.getText().toString();

                                //    usuario.setFechaNacimiento(SimpleDateFormat.parse(nacimientoNew));

                                myRef.setValue(usuario);
                                nacimiento.setText(nacimientoNew);
                                nacimiento.setEnabled(false);
                                //Guardar nuevo nombre

                            }

                            procesado = true;
                        }
                        return procesado;
                    }
                });
            }
        });

        editarAltura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                altura.setEnabled(true);
                altura.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        boolean procesado = false;

                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            // Mostrar mensaje

                            if(altura.getText()!=null){
                                float alturaNew= Float.parseFloat(altura.getText().toString());
                                usuario.setAltura(alturaNew);
                                myRef.setValue(usuario);
                                altura.setEnabled(false);


                            }

                            procesado = true;
                        }
                        return procesado;
                    }
                });
            }
        });

        editarPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peso.setEnabled(true);
                peso.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        boolean procesado = false;

                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            // Mostrar mensaje

                            if(peso.getText()!=null){
                                float pesoNew= Float.parseFloat(peso.getText().toString());
                                usuario.setPeso(pesoNew);
                                myRef.setValue(usuario);
                                peso.setEnabled(false);
                                //Guardar nuevo nombre

                            }

                            procesado = true;
                        }
                        return procesado;
                    }
                });
            }
        });

        seguidores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(getActivity(), SeguidoresActivity.class);
                intent.putExtra("usuario", (Serializable) usuario);
                intent.putExtra("id",(Serializable) mAuth.getUid());
                startActivity(intent);
            }
        });

        siguiendo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(getActivity(), SeguidosActivity.class);
                 intent.putExtra("usuario", (Serializable) usuario);
                 intent.putExtra("id",(Serializable) mAuth.getUid());
                startActivity(intent);
            }
        });

        privacidad.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    usuario.setPrivacidad(true);
                    myRef.setValue(usuario);
                }else{
                    usuario.setPrivacidad(false);
                    myRef.setValue(usuario);
                }
            }
        });


    }


    private void cargarFoto() {
        //Codigo de request recomendado UtilsMiguel.REQUEST_CODE_UPLOAD_PHOTO
        Permisos.requestPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE,"Es necesario para carga una foto", UtilsMiguel.REQUEST_CODE_PERMISSION);

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.createChooser(intent, StringsMiguel.SELECCIONAR_APLICACION);
        startActivityForResult(intent, UtilsMiguel.REQUEST_CODE_UPLOAD_PHOTO);

    }

    private void tomarFoto() {
        Permisos.requestPermission(getActivity(), Manifest.permission.CAMERA,"Es necesario para tomar fotos", UtilsMiguel.REQUEST_CODE_PERMISSION);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
          startActivityForResult(intent, UtilsMiguel.REQUEST_CODE_TAKE_PHOTO);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        Bitmap bitmapFoto;
        Log.i("Ufff", requestCode+ "");
        if(requestCode== UtilsMiguel.REQUEST_CODE_TAKE_PHOTO && resultCode== Activity.RESULT_OK){
            Bundle extras = data.getExtras();
            bitmapFoto = (Bitmap) extras.get("data");
            //Subir foto a firebase y descargarlas fotos en imagenes, borrar linea de abajo
            imagen=bitmapFoto;
            PersistenciaFirebase.subirArchivoFirebase(RutasBaseDeDatos.RUTA_FOTO_USUARIOS,mAuth.getUid(),UtilsMiguel.getImageUri(getActivity(),bitmapFoto,mAuth.getUid()));
            fotoPerfil.setImageBitmap(bitmapFoto);
        }else if(requestCode == UtilsMiguel.REQUEST_CODE_UPLOAD_PHOTO  && resultCode==Activity.RESULT_OK){
            Uri path = data.getData();
            try {
                bitmapFoto = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),path);
                //Subir foto a firebase y descargarlas fotos en imagenes, borrar linea de abajo
                imagen=bitmapFoto;
                PersistenciaFirebase.subirArchivoFirebase(RutasBaseDeDatos.RUTA_FOTO_USUARIOS,mAuth.getUid(),UtilsMiguel.getImageUri(getActivity(),bitmapFoto,mAuth.getUid()));
                fotoPerfil.setImageBitmap(bitmapFoto);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }


}
