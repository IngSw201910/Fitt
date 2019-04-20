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

import co.edu.javeriana.bittus.fitt.Modelo.Ejercicio;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;
import co.edu.javeriana.bittus.fitt.Vista.BuscarEjercicioActivity;
import pl.droidsonroids.gif.GifImageView;

public class EjerciciosAdapter extends ArrayAdapter<Ejercicio> {

    protected List<Ejercicio> listEjercicios;
    protected Context context;
    private int resource;
    private Ejercicio ejercicioSeleccionado;


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

        /*GifImageView gifImageView = view.findViewById(R.id.gifBuscarEjercicio);
        gifImageView.setImageResource(ejercicio.getGif());*/
        GifImageView gifImageView = view.findViewById(R.id.gifEjercicio);
        Utils.descargarYMostrarGIF(ejercicio.getRutaGIF(), gifImageView);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });


        return  view;
    }

    public void abrirPopUp(){
        if(ejercicioSeleccionado.getTipo().equals("Distancia")){
            BuscarEjercicioActivity b = (BuscarEjercicioActivity)context;
            b.abrirPopUpCrearEjercicioDistancia(ejercicioSeleccionado);
        }
        if(ejercicioSeleccionado.getTipo().equals("Duración")){
            BuscarEjercicioActivity b = (BuscarEjercicioActivity)context;
            b.abrirPopUpCrearEjercicioDuracion(ejercicioSeleccionado);
        }
        if(ejercicioSeleccionado.getTipo().equals("Repetición")){
            BuscarEjercicioActivity b = (BuscarEjercicioActivity)context;
            b.abrirPopUpCrearEjercicioRepeticion(ejercicioSeleccionado);
        }

    }

}
