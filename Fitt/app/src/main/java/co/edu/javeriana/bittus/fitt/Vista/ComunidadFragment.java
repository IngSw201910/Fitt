package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import co.edu.javeriana.bittus.fitt.R;

public class ComunidadFragment extends Fragment {

    ImageButton buscarRutinasB;
    ImageButton parquesB;
    ImageButton chatsB;
    ImageButton mapaUsuariosB;
    ImageButton buscarUsuarioB;
    ImageButton ejerciciosB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comunidad, null, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v = getView();

        buscarRutinasB = v.findViewById(R.id.buttonBuscarRutinas);


        parquesB = v.findViewById(R.id.buttonParques);
        chatsB = v.findViewById(R.id.buttonChats);
        mapaUsuariosB = v.findViewById(R.id.buttonMapaUsuarios);
        buscarUsuarioB = v.findViewById(R.id.buttonBuscarUsuario);
        ejerciciosB = v.findViewById(R.id.buttonEjercicios);

        parquesB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ParquesActivity.class));
            }
        });

        buscarRutinasB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BuscarEntrenamiento.class));
            }
        });

        chatsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChatsActivity.class));
            }
        });

        mapaUsuariosB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MapaUsuariosActivity.class));
            }
        });

        buscarUsuarioB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BuscarUsuarioActivity.class));
            }
        });

        ejerciciosB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EjerciciosActivity.class));
            }
        });

    }
}
