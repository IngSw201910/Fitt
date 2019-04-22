package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
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



        mAuth = FirebaseAuth.getInstance();

        salirB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.cerrarSesion();
                //finish();
            }
        });

        crearRutinaB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CrearRutinaActivity.class));
            }
        });

    }




}
