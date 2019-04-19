package co.edu.javeriana.bittus.fitt.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import co.edu.javeriana.bittus.fitt.Modelo.Ejercicio;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Vista.BuscarEjercicioActivity;
import co.edu.javeriana.bittus.fitt.Vista.PopCrearEjercicioSesionDistancia;
import pl.droidsonroids.gif.GifImageView;

public class EjerciciosAdapter extends ArrayAdapter<Ejercicio> {

    protected List<Ejercicio> listEjercicios;
    protected Context context;
    private int resource;


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
        final Ejercicio ejercicio = listEjercicios.get(position);

        TextView nombre = view.findViewById(R.id.textNombreEjercicioBuscar);
        nombre.setText(ejercicio.getNombre());

        TextView musculos = view.findViewById(R.id.textMusculosEjercicioBuscar);
        musculos.setText(ejercicio.getMusculos());

        TextView tipo = view.findViewById(R.id.textTipoEjercicio);
        tipo.setText(ejercicio.getTipo());

        TextView dificultad = view.findViewById(R.id.textDificultadEjercicio);
        dificultad.setText(ejercicio.getDificultad());

        GifImageView gifImageView = view.findViewById(R.id.gifBuscarEjercicio);
        gifImageView.setImageResource(ejercicio.getGif());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ejercicio.getTipo().equals("Distancia")){
                    BuscarEjercicioActivity b = (BuscarEjercicioActivity)context;
                    b.abrirPopUpCrearEjercicioDistancia();
                }
                if(ejercicio.getTipo().equals("Duración")){
                    BuscarEjercicioActivity b = (BuscarEjercicioActivity)context;
                    b.abrirPopUpCrearEjercicioDuracion();
                }
                if(ejercicio.getTipo().equals("Repetición")){
                    BuscarEjercicioActivity b = (BuscarEjercicioActivity)context;
                    b.abrirPopUpCrearEjercicioRepeticion();
                }


            }
        });


        return  view;
    }


}
