package co.edu.javeriana.bittus.fitt.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import java.util.List;


import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDistancia;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDuracion;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioRepeticiones;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioSesion;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Vista.CrearSesionActivity;


public class EjerciciosSesionAdapter extends ArrayAdapter<EjercicioSesion> {


    protected List<EjercicioSesion> listEjercios;
    protected Context context;
    private EjercicioSesion ejercicioSesion;

    public EjerciciosSesionAdapter(@NonNull Context context, List<EjercicioSesion> objects) {
        super(context, R.layout.item_ejercicio_duracion_nuevo_row, objects);
        this.listEjercios = objects;
        this.context = context;

    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(listEjercios.get(position) instanceof EjercicioDistancia){
            return getViewEjercicioDistancia(position,view, parent);
        }
        if(listEjercios.get(position) instanceof EjercicioDuracion){
            return getViewEjercicioDuracion(position,view, parent);
        }
        if(listEjercios.get(position) instanceof EjercicioRepeticiones){
            return getViewEjercicioRepeticiones(position,view, parent);
        }
        return  view;
    }

    private View getViewEjercicioRepeticiones(int position, View view, ViewGroup parent) {

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_ejercicio_repeticion_nuevo_row, null);
        }
        ejercicioSesion = listEjercios.get(position);
        EjercicioRepeticiones ejercicio = (EjercicioRepeticiones) ejercicioSesion;

        TextView nombre = view.findViewById(R.id.textNombreEjercicioRepeticion);
        nombre.setText(ejercicio.getEjercicio().getNombre());

        TextView repeticiones = view.findViewById(R.id.textEjercicioRepeticiones);
        String repeticionS = Integer.toString(ejercicio.getRepeticiones());
        repeticiones.setText(repeticionS);


        TextView series = view.findViewById(R.id.textEjercicioSeries);
        String serieS = Integer.toString(ejercicio.getSeries());
        series.setText(serieS);

        TextView descanso = view.findViewById(R.id.textEjercicioDescanso);
        String descansoS = Integer.toString(ejercicio.getDescanso());
        descanso.setText(descansoS+"s");


        ImageButton editarB = view.findViewById(R.id.buttonEdit3);

        editarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarEjercicioRepeticion();
            }
        });

        ImageButton eliminarB = view.findViewById(R.id.buttonDelete3);

        eliminarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarEjercicio();
            }
        });

        return view;

    }

    private void editarEjercicioRepeticion() {
        CrearSesionActivity crearSesionActivity = (CrearSesionActivity) context;
        crearSesionActivity.abrirPopUpCrearEjercicioRepeticion(ejercicioSesion, getPosition(ejercicioSesion));
    }

    private View getViewEjercicioDuracion(int position, View view, ViewGroup parent) {

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_ejercicio_duracion_nuevo_row, null);
        }
        ejercicioSesion = listEjercios.get(position);
        EjercicioDuracion ejercicio = (EjercicioDuracion) ejercicioSesion;

        TextView nombre = view.findViewById(R.id.textNombreEjercicioDuracion);
        nombre.setText(ejercicio.getEjercicio().getNombre());

        TextView duracion = view.findViewById(R.id.textDuracionEjercicioDuracion);
        String duracionS = Integer.toString(ejercicio.getDuracion());
        duracion.setText(duracionS + "s");


        ImageButton editarB = (ImageButton) view.findViewById(R.id.buttonEdit2);

        editarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarEjercicioDuracion();

            }
        });

        ImageButton eliminarB = (ImageButton) view.findViewById(R.id.buttonDelete2);

        eliminarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarEjercicio();
            }
        });

        return view;
    }

    private void editarEjercicioDuracion() {
        CrearSesionActivity crearSesionActivity = (CrearSesionActivity) context;
        crearSesionActivity.abrirPopUpCrearEjercicioDuracion(ejercicioSesion, getPosition(ejercicioSesion));
    }

    private View getViewEjercicioDistancia(int position, View view, ViewGroup parent) {

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_ejercicio_distancia_nuevo_row, null);
        }
        ejercicioSesion = listEjercios.get(position);
        EjercicioDistancia ejercicio = (EjercicioDistancia) ejercicioSesion;

        TextView nombre = view.findViewById(R.id.textNombreEjercicioDistancia);
        nombre.setText(ejercicio.getEjercicio().getNombre());

        TextView distancia = view.findViewById(R.id.textDistanciaEjercicioDistancia);
        String distanciaS = Integer.toString(ejercicio.getDistancia());
        distancia.setText(distanciaS + "mts");

        ImageButton editarB = view.findViewById(R.id.buttonEdit);

        editarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarEjercicioDistancia();

            }
        });


        ImageButton eliminarB = view.findViewById(R.id.buttonDelete);

        eliminarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                eliminarEjercicio();
            }
        });

        return view;
    }

    private void editarEjercicioDistancia() {
        CrearSesionActivity crearSesionActivity = (CrearSesionActivity) context;
        crearSesionActivity.abrirPopUpCrearEjercicioDistancia(ejercicioSesion, getPosition(ejercicioSesion));
    }

    private void eliminarEjercicio() {

        CrearSesionActivity crearSesionActivity = (CrearSesionActivity) context;
        crearSesionActivity.eliminarEjercicio(ejercicioSesion);

    }


}
