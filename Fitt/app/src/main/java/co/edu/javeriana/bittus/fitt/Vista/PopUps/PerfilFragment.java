package co.edu.javeriana.bittus.fitt.Vista.PopUps;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Vista.BuscarRutinas;
import co.edu.javeriana.bittus.fitt.Vista.BuscarUsuarioActivity;
import co.edu.javeriana.bittus.fitt.Vista.ChatsActivity;
import co.edu.javeriana.bittus.fitt.Vista.EjerciciosActivity;
import co.edu.javeriana.bittus.fitt.Vista.MapaUsuariosActivity;
import co.edu.javeriana.bittus.fitt.Vista.ParquesActivity;

public class PerfilFragment extends Fragment {

    ImageButton buscarRutinasB;
    ImageButton parquesB;
    ImageButton chatsB;
    ImageButton mapaUsuariosB;
    ImageButton buscarUsuarioB;
    ImageButton ejerciciosB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_perfil, null, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v = getView();

    }
}
