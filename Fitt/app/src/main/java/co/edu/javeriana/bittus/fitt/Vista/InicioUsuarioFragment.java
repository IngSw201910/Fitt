package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;

public class InicioUsuarioFragment extends Fragment {


    private ImageButton salirB;
    private FirebaseAuth mAuth;
    private ImageButton crearRutinaB;



    private ImageButton logrosB;
    private ImageButton miProgresoB;
    private ImageButton misRutinasB;
    private ImageButton realizarEntrenamientoB;
    private ImageButton calendarioB;
    private ImageButton entrenadorB;
    private ImageButton iniciarRecorridoB;






    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inicio_usuario, null, false);
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View v = getView();


        salirB = (ImageButton) v.findViewById(R.id.logout);
        crearRutinaB = (ImageButton) v.findViewById(R.id.btnCrearRutina);

        logrosB = (ImageButton) v.findViewById(R.id.btnMisLogros);


        miProgresoB = (ImageButton) v.findViewById(R.id.btnMiProgreso);
        misRutinasB = (ImageButton) v.findViewById(R.id.btnMisRutinas);
        realizarEntrenamientoB = (ImageButton) v.findViewById(R.id.btnRealizarEntrenamiento);
        calendarioB = (ImageButton) v.findViewById(R.id.btnCalendario);
        entrenadorB = (ImageButton) v.findViewById(R.id.btnEntrenador);
        iniciarRecorridoB = (ImageButton) v.findViewById(R.id.btnIniciarRecorrido);







        mAuth = FirebaseAuth.getInstance();

        salirB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.cerrarSesion();
                getActivity().finish();
            }
        });

        crearRutinaB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CrearEntrenamientoActivity.class));
            }
        });

        miProgresoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MiProgresoActivity.class));
            }
        });

        logrosB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LogrosActivity.class));
            }
        });

        misRutinasB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MisRutinasActivity.class));
            }
        });

        realizarEntrenamientoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RealizarEntrenamiento_EntrenamientosHoyActivity.class));
            }
        });

        calendarioB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CalendarioActivity.class));
            }
        });

        entrenadorB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EntrenadorActivity.class));
            }
        });

        iniciarRecorridoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), IniciarRecorridoActivity.class));
            }
        });

    }
}
