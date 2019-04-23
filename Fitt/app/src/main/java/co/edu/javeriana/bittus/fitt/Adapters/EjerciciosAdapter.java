package co.edu.javeriana.bittus.fitt.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.List;

import co.edu.javeriana.bittus.fitt.Filtros.EjerciciosFiltro;
import co.edu.javeriana.bittus.fitt.Modelo.Ejercicio;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;
import pl.droidsonroids.gif.GifImageView;

public class EjerciciosAdapter extends ArrayAdapter<Ejercicio> {

    protected List<Ejercicio> listEjercicios;
    protected Context context;
    private int resource;
    private Ejercicio ejercicioSeleccionado;
    private EjerciciosFiltro ejerciciosFiltro;


    public EjerciciosAdapter(@NonNull Context context, int resource, List<Ejercicio> objects) {
        super(context, resource, objects);
        this.listEjercicios = objects;
        this.context = context;
        this.resource = resource;

    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(resource, null);
        }
        ejercicioSeleccionado = listEjercicios.get(position);
        Ejercicio ejercicio = ejercicioSeleccionado;

        TextView nombre = view.findViewById(R.id.textNombreEjercicioBuscar);
        nombre.setText(ejercicio.getNombre());

        TextView musculos = view.findViewById(R.id.textMusculosEjercicioBuscar);
        musculos.setText(ejercicio.getMusculos());

        TextView tipo = view.findViewById(R.id.textTipoEjercicio);
        tipo.setText(ejercicio.getTipo());

        TextView dificultad = view.findViewById(R.id.textDificultadEjercicio);
        dificultad.setText(ejercicio.getDificultad());


        GifImageView gifImageView = view.findViewById(R.id.gifEjercicio4);
        Utils.descargarYMostrarGIF(ejercicio.getRutaGIF(), gifImageView);


        return  view;
    }
    @NonNull
    @Override
    public Filter getFilter() {
        if(ejerciciosFiltro ==null){
            ejerciciosFiltro = new EjerciciosFiltro(listEjercicios, this);
        }


        return ejerciciosFiltro;


    }



}
