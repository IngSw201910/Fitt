package co.edu.javeriana.bittus.fitt.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import co.edu.javeriana.bittus.fitt.Modelo.Entrenamiento;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.BtnClickListenerRow;

public class EntrenamientosAdapter extends ArrayAdapter<Entrenamiento> {


    private List<Entrenamiento> listEntrenamiento;
    private Context context;
    private int resource;
    private Entrenamiento entrenamiento;
    private BtnClickListenerRow mClickListener = null;

    public EntrenamientosAdapter(@NonNull Context context, int resource, List<Entrenamiento> objects, BtnClickListenerRow listenerRow) {
        super(context, resource, objects);
        this.listEntrenamiento = objects;
        this.context = context;
        this.resource = resource;
        this.mClickListener = listenerRow;
    }



    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(resource, null);
        }

        entrenamiento = listEntrenamiento.get(position);

        TextView nombre = view.findViewById(R.id.textNombreSesion);
        nombre.setText(entrenamiento.getNombre());


        ImageButton editarB = view.findViewById(R.id.buttonEditarSesion);

        editarB.setTag(position);

        editarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClickListener!=null){
                    mClickListener.onBtnClickEdit((Integer) v.getTag());
                }

            }
        });

        ImageButton eliminarB = view.findViewById(R.id.buttonDelete4);
        eliminarB.setTag(position);

        eliminarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClickListener!=null){
                    mClickListener.onBtnClickDelete((Integer) v.getTag());
                }
            }
        });


        return  view;
    }

}
