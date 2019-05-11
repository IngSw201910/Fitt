package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.edu.javeriana.bittus.fitt.Modelo.EjercicioRepeticiones;
import co.edu.javeriana.bittus.fitt.R;





public class EntrenamientoTerminadoFragment extends Fragment {
    private FragmentEjercicioRepeticionesListener listener;

    public interface FragmentEjercicioRepeticionesListener {
        void darInstrucciones(String texto);
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listener.darInstrucciones("Entrenamiento terminado!");
        return inflater.inflate(R.layout.fragment_entrenamiento_terminado, null, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EjercicioRepeticionesFragment.FragmentEjercicioRepeticionesListener) {
            listener = (EntrenamientoTerminadoFragment.FragmentEjercicioRepeticionesListener) context;
        }
    }

    @Override
    public void onDetach() {

        listener = null;
        super.onDetach();


    }


}
