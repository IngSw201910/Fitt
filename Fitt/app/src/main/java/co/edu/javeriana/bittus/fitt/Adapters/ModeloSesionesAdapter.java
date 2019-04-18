package co.edu.javeriana.bittus.fitt.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import co.edu.javeriana.bittus.fitt.Modelo.Sesion;
import co.edu.javeriana.bittus.fitt.R;

public class ModeloSesionesAdapter extends ArrayAdapter<Sesion> {


    private List<Sesion> modeloList;
    private Context context;
    private int resource;


    public ModeloSesionesAdapter(@NonNull Context context, int resource, List<Sesion> objects) {
        super(context, resource, objects);
        this.modeloList = objects;
        this.context = context;
        this.resource = resource;
    }



    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(resource, null);
        }
        Sesion modelo = modeloList.get(position);

        TextView nombre = view.findViewById(R.id.textNombreSesion);
        nombre.setText(modelo.getNombre());

        TextView duracion = view.findViewById(R.id.textDuracion);
        duracion.setText(modelo.getDuracion());

        return  view;
    }
}
