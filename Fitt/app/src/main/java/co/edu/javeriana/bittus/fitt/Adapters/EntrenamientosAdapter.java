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
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import co.edu.javeriana.bittus.fitt.Filtros.RutinasFiltro;
import co.edu.javeriana.bittus.fitt.Modelo.Entrenamiento;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.BtnClickListenerEntrenamientoRow;


public class EntrenamientosAdapter extends ArrayAdapter<Entrenamiento> {

    private List<Entrenamiento> listEntrenamiento;
    private Context context;
    private int resource;
    private Entrenamiento entrenamiento;
    private RutinasFiltro rutinasFiltro;
    private BtnClickListenerEntrenamientoRow mClickListener;

    public EntrenamientosAdapter(@NonNull Context context, int resource, List<Entrenamiento> objects, BtnClickListenerEntrenamientoRow listenerAdoptar) {
        super(context, resource, objects);
        this.listEntrenamiento = objects;
        this.context = context;
        this.resource = resource;

        this.mClickListener = listenerAdoptar;

    }



    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(resource, null);
        }

        entrenamiento = listEntrenamiento.get(position);

        TextView nombre = view.findViewById(R.id.textView6);
        nombre.setText(entrenamiento.getNombre());

        TextView dificultad = view.findViewById(R.id.textView14);
        dificultad.setText(entrenamiento.getDificultad());

        ImageButton adoptar = view.findViewById(R.id.adoptar);
        adoptar.setTag(position);

        ImageButton info = view.findViewById(R.id.imageButtonInformacion);
        info.setTag(position);

        RatingBar ratingBar = view.findViewById(R.id.ratingBarEntrenamiento);
        ratingBar.setRating(entrenamiento.calcularRating(entrenamiento));
        

        adoptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClickListener != null)
                    mClickListener.onBtnClickAdoptar((Integer) v.getTag());
            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClickListener != null)
                    mClickListener.onBtnClickInfo((Integer) v.getTag());
            }
        });


        return  view;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if(rutinasFiltro ==null){
            rutinasFiltro = new RutinasFiltro(listEntrenamiento, this);
        }


        return rutinasFiltro;


    }

}
