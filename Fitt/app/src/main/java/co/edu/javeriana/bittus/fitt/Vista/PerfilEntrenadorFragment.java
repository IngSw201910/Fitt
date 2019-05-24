package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import co.edu.javeriana.bittus.fitt.Modelo.Entrenador;
import co.edu.javeriana.bittus.fitt.Modelo.Usuario;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;

public class PerfilEntrenadorFragment extends Fragment {

    ImageButton editarNombre;
    ImageButton editarCorreo;
    ImageButton ediarNacimiento;
    ImageButton editarPeso;
    ImageButton editarAltura;
    ImageButton editarExperiencia;

    Button seguidores;
    Button siguiendo;

    EditText nombre;
    EditText correo;
    EditText nacimiento;
    EditText peso;
    EditText altura;
    EditText experiencia;

    CheckBox privacidad;

    RatingBar puntaje;

    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseUser mAuth;

    private Entrenador entrenador;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_perfil_entrenador, null, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v = getView();

        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(RutasBaseDeDatos.getRutaEntrenadores()).child(mAuth.getUid());

        editarNombre =v.findViewById(R.id.imageButtonEditarNombreE);
        editarCorreo =v.findViewById(R.id.imageButtonEditarCorreoE);
        ediarNacimiento =v.findViewById(R.id.imageButtonEdiatNaciemintoE);
        editarPeso =v.findViewById(R.id.imageButtonEditarPesoE);
        editarAltura =v.findViewById(R.id.imageButtonEditarAlturaE);
        editarExperiencia =v.findViewById(R.id.imageButtonEdiatExperiencia);
        seguidores =v.findViewById(R.id.buttonSeguidoresE);
        siguiendo =v.findViewById(R.id.buttonSiguiendoE);

        puntaje=v.findViewById(R.id.ratingBarPerfilE);
        puntaje.setNumStars(5);
        puntaje.setEnabled(false);

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


        experiencia =v.findViewById(R.id.editTextExperiencia);
        experiencia.setEnabled(false);


        privacidad =v.findViewById(R.id.radioButtonPrivacidadE);


        puntaje =v.findViewById(R.id.ratingBarPerfilE);
        puntaje.setEnabled(false);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                entrenador = dataSnapshot.getValue(Entrenador.class);
                nombre.setText(entrenador.getNombre());
                correo.setText(entrenador.getCorreo());
                peso.setText(String.valueOf(entrenador.getPeso()));
                altura.setText(String.valueOf(entrenador.getAltura()));
                nacimiento.setText(entrenador.getFechaNacimiento().getDate()+"/"+entrenador.getFechaNacimiento().getMonth()+"/"+entrenador.getFechaNacimiento().getYear());
                //puntaje.setRating(entrenador.get);
                experiencia.setText(entrenador.getDescripccion());

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
                            // Mostrar mensaje

                            if(nombre.getText()!=null){
                                String nombreUs= nombre.getText().toString();

                                nombre.setEnabled(false);
                                //Guardar nuevo nombre

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
                                altura.setEnabled(false);
                                //Guardar nuevo nombre

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

        editarExperiencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                experiencia.setEnabled(true);
                experiencia.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        boolean procesado = false;

                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            // Mostrar mensaje

                            if(experiencia.getText()!=null){
                                String experienciaNew= experiencia.getText().toString();
                                experiencia.setEnabled(false);
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
                startActivity(new Intent(getActivity(), SeguidoresActivity.class));
            }
        });

        siguiendo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SeguidosActivity.class));
            }
        });

        privacidad.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    //Cambiar a privado
                }else{
                    //Cambiar a publico
                }
            }
        });
    }
}
