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

import co.edu.javeriana.bittus.fitt.Modelo.Rutina;
import co.edu.javeriana.bittus.fitt.Modelo.Sesion;
import co.edu.javeriana.bittus.fitt.R;


public class RutinasAdapter extends ArrayAdapter<Rutina> {

    private List<Rutina> listRutina;
    private Context context;
    private int resource;
    private Rutina rutina;


    public RutinasAdapter(@NonNull Context context, int resource, List<Rutina> objects) {
        super(context, resource, objects);
        this.listRutina = objects;
        this.context = context;
        this.resource = resource;

    }



    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(resource, null);
        }

        rutina = listRutina.get(position);

        TextView nombre = view.findViewById(R.id.textView6);
        nombre.setText(rutina.getNombre());

        TextView dificultad = view.findViewById(R.id.textView14);
        dificultad.setText(rutina.getDificultad());

        int duracionEnHoras = duracionEnHoras();

        TextView duracionHoras = view.findViewById(R.id.textView15);
        duracionHoras.setText(Integer.toString(duracionEnHoras));


        TextView reiteraciones = view.findViewById(R.id.textView8);
        reiteraciones.setText(Integer.toString(rutina.getFrecuencia()));

        return  view;
    }

    private int duracionEnHoras() {

        int duracion = 0;
        List<Sesion> sesionList = rutina.getSesionList();

        for (Sesion sesion: sesionList)
        {
            duracion += sesion.getDuracion();
        }

        duracion =  duracion*rutina.getFrecuencia();
        duracion = duracion/60;
        return  duracion;
    }


}
