package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;



import co.edu.javeriana.bittus.fitt.R;


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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_perfil, null, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v = getView();


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
                                nombre.setEnabled(false);
                                //Guardar nuevo nombres
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

        privacidad.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener(){

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
