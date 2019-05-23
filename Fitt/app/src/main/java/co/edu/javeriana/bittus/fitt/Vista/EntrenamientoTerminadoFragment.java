package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import co.edu.javeriana.bittus.fitt.Adapters.EjerciciosEntrenamientoInformacionAdapter;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioRepeticiones;
import co.edu.javeriana.bittus.fitt.Modelo.Entrenamiento;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.StringsMiguel;


public class EntrenamientoTerminadoFragment extends Fragment {
    private FragmentEjercicioRepeticionesListener listener;

    private Entrenamiento entrenamiento;
    private int calorias;
    private String tiempo;

    private TextView caloriasTV;
    private TextView tiempoTV;

    private ListView listViewEjerciciosEntrenamiento;

    public interface FragmentEjercicioRepeticionesListener {
        void darInstrucciones(String texto);
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        entrenamiento = (Entrenamiento) getArguments().getSerializable("entrenamiento");
        calorias = getArguments().getInt("calorias");
        tiempo = getArguments().getString("tiempo");
        listener.darInstrucciones("Entrenamiento terminado!");
        return inflater.inflate(R.layout.fragment_entrenamiento_terminado, null, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View v = getView();

        caloriasTV = v.findViewById(R.id.caloriasFinalEntrenamiento);
        tiempoTV = v.findViewById(R.id.tiempoFinalEntrenamiento);

        caloriasTV.setText("CALORIAS:\n"+calorias);
        tiempoTV.setText("TIEMPO:\n"+tiempo);

        listViewEjerciciosEntrenamiento = (ListView) v.findViewById(R.id.listViewEjerciciosEntrenamiento2);



        EjerciciosEntrenamientoInformacionAdapter adapter = new EjerciciosEntrenamientoInformacionAdapter(getActivity(),entrenamiento.getEjercicioEntrenamientoList());


        listViewEjerciciosEntrenamiento.setAdapter(adapter);
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
