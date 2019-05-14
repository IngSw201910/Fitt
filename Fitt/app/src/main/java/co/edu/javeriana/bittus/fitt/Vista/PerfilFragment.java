package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.os.Bundle;
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
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PerfilFragment extends Fragment {


    ImageButton editarNombre;
    ImageButton editarCorreo;
    ImageButton ediarNacimiento;
    ImageButton editarPeso;
    ImageButton editarAltura;
    Button seguidores;
    Button siguiendo;

    EditText nombre;
    EditText correo;
    EditText nacimiento;
    EditText registro;
    EditText peso;
    EditText altura;

    CheckBox privacidad;

    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseUser mAuth;

    private Usuario usuario;

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
        myRef = database.getReference(RutasBaseDeDatos.getRutaUsuarios()).child(mAuth.getUid());



        editarNombre =v.findViewById(R.id.imageButtonEditarNombre);
        editarCorreo =v.findViewById(R.id.imageButtonEdicarCorreo);
        ediarNacimiento =v.findViewById(R.id.imageButtonEditarNacieminnto);
        editarPeso =v.findViewById(R.id.imageButtonEditarPeso);
        editarAltura =v.findViewById(R.id.imageButtonEditarAltura);
        seguidores =v.findViewById(R.id.buttonSeguidoresUsuario);
        siguiendo =v.findViewById(R.id.buttonSiguiendo);

        nombre =v.findViewById(R.id.editTextNombre);
        nombre.setEnabled(false);

        correo =v.findViewById(R.id.editTextCorreo);
        correo.setEnabled(false);


        nacimiento =v.findViewById(R.id.editTextNaciemiento);
        nacimiento.setEnabled(false);

        registro = v.findViewById(R.id.editTextRegistro);
        registro.setEnabled(false);

        peso =v.findViewById(R.id.editTextPeso);
        peso.setEnabled(false);

        altura =v.findViewById(R.id.editTextAltura);
        altura.setEnabled(false);

        privacidad =v.findViewById(R.id.radioButtonPrivacidad);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                usuario = dataSnapshot.getValue(Usuario.class);
                nombre.setText(usuario.getNombre());
                correo.setText(usuario.getCorreo());
                peso.setText(String.valueOf(usuario.getPeso()));
                altura.setText(String.valueOf(usuario.getAltura()));
                nacimiento.setText(String.valueOf(usuario.getFechaNacimiento()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
               // intent.putExtra("listaSeguidores",);
                startActivity(intent);
            }
        });

        siguiendo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(getActivity(), SeguidosActivity.class);
                // intent.putExtra("listaSeguidos",);
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

    private void descargarUsuarios (){

        myRef = database.getReference(RutasBaseDeDatos.getRutaUsuarios());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    Usuario aux =singleSnapshot.getValue(Usuario.class);

                    if(aux.getCorreo().compareTo(mAuth.getEmail())==0){

                    }
                    //Log.i("Prueba", singleSnapshot.getValue(Usuario.class).getDireccionFoto());
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Error:", "Error en la consulta", databaseError.toException());
            }
        });
    }


}
