package co.edu.javeriana.bittus.fitt.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import co.edu.javeriana.bittus.fitt.Filtros.RutinasFiltro;
import co.edu.javeriana.bittus.fitt.Modelo.Entrenamiento;
import co.edu.javeriana.bittus.fitt.Modelo.EntrenamientoAdoptado;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.BtnClickListenerEntrenamientoRow;


public class EntrenamientosHoyAdapter extends ArrayAdapter<EntrenamientoAdoptado> {

    private List<EntrenamientoAdoptado> listEntrenamiento;
    private Context context;
    private int resource;
    private EntrenamientoAdoptado entrenamientoAdoptado;
    private RutinasFiltro rutinasFiltro;
    private BtnClickListenerEntrenamientoRow mClickListenerAdoptar;

    public EntrenamientosHoyAdapter(@NonNull Context context, int resource, List<EntrenamientoAdoptado> objects) {
        super(context, resource, objects);
        this.listEntrenamiento = objects;
        this.context = context;
        this.resource = resource;


    }



    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(resource, null);
        }

        entrenamientoAdoptado = listEntrenamiento.get(position);

        TextView nombre = view.findViewById(R.id.textView6);
        nombre.setText(entrenamientoAdoptado.getEntrenamiento().getNombre());

        TextView dificultad = view.findViewById(R.id.textView14);
        dificultad.setText(entrenamientoAdoptado.getEntrenamiento().getDificultad());

        return  view;
    }



}
