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


import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDescanso;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDistancia;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioEntrenamiento;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioRepeticiones;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.BtnClickListenerRow;



public class EjerciciosEntrenamientoAdapter extends ArrayAdapter<EjercicioEntrenamiento> {


    protected List<EjercicioEntrenamiento> listEjercios;
    protected Context context;
    private EjercicioEntrenamiento ejercicioEntrenamiento;
    private BtnClickListenerRow mClickListenerEditar = null;


    public EjerciciosEntrenamientoAdapter(@NonNull Context context, List<EjercicioEntrenamiento> objects, BtnClickListenerRow listenerEditar) {
        super(context, R.layout.item_ejercicio_duracion_nuevo_row, objects);
        this.listEjercios = objects;
        this.context = context;
        this.mClickListenerEditar = listenerEditar;

    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(listEjercios.get(position).getEjercicio().getTipo().equals("Distancia")){
            return getViewEjercicioDistancia(position,view, parent);
        }
        if(listEjercios.get(position).getEjercicio().getTipo().equals("Duración")){
            return getViewEjercicioDuracion(position,view, parent);
        }
        if(listEjercios.get(position).getEjercicio().getTipo().equals("Repetición")){
            return getViewEjercicioRepeticiones(position,view, parent);
        }
        return  view;
    }

    private View getViewEjercicioRepeticiones(int position, View view, ViewGroup parent) {

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_ejercicio_repeticion_nuevo_row, null);
        }
        ejercicioEntrenamiento = listEjercios.get(position);
        EjercicioRepeticiones ejercicio = (EjercicioRepeticiones) ejercicioEntrenamiento;

        TextView nombre = (TextView) view.findViewById(R.id.textNombreEjercicioRepeticion);
        nombre.setText(ejercicio.getEjercicio().getNombre());

        TextView repeticiones = (TextView)view.findViewById(R.id.textEjercicioRepeticiones);
        String repeticionS = Integer.toString(ejercicio.getRepeticiones());
        repeticiones.setText(repeticionS);


        TextView series = (TextView)view.findViewById(R.id.textEjercicioSeries);
        String serieS = Integer.toString(ejercicio.getSeries());
        series.setText(serieS);

        TextView descanso = (TextView)view.findViewById(R.id.textEjercicioDescanso);
        String descansoS = Integer.toString(ejercicio.getDescanso());
        descanso.setText(descansoS+"s");


        ImageButton editarB = (ImageButton) view.findViewById(R.id.buttonEdit3);

        editarB.setTag(position);

        editarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClickListenerEditar != null)
                    mClickListenerEditar.onBtnClickEdit((Integer) v.getTag());
            }
        });

        ImageButton eliminarB =(ImageButton) view.findViewById(R.id.buttonDelete3);

        eliminarB.setTag(position);


        eliminarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClickListenerEditar != null)
                    mClickListenerEditar.onBtnClickDelete((Integer) v.getTag());
            }
        });

        return view;

    }



    private View getViewEjercicioDuracion(int position, View view, ViewGroup parent) {

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_ejercicio_duracion_nuevo_row, null);
        }
        ejercicioEntrenamiento = listEjercios.get(position);
        EjercicioDescanso ejercicio = (EjercicioDescanso) ejercicioEntrenamiento;

        TextView nombre = (TextView) view.findViewById(R.id.textNombreEjercicioDuracion);
        nombre.setText(ejercicio.getEjercicio().getNombre());

        TextView duracion = (TextView) view.findViewById(R.id.textDuracionEjercicioDuracion);
        String duracionS = Integer.toString(ejercicio.getDuracion());
        duracion.setText(duracionS + "s");


        ImageButton editarB = (ImageButton) view.findViewById(R.id.buttonEdit2);

        editarB.setTag(position);

        editarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClickListenerEditar != null)
                    mClickListenerEditar.onBtnClickEdit((Integer) v.getTag());
            }
        });

        ImageButton eliminarB = (ImageButton) view.findViewById(R.id.buttonDelete2);
        eliminarB.setTag(position);

        eliminarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClickListenerEditar != null)
                    mClickListenerEditar.onBtnClickDelete((Integer) v.getTag());
            }
        });

        return view;
    }



    private View getViewEjercicioDistancia(int position, View view, ViewGroup parent) {

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_ejercicio_distancia_nuevo_row, null);
        }
        ejercicioEntrenamiento = listEjercios.get(position);
        EjercicioDistancia ejercicio = (EjercicioDistancia) ejercicioEntrenamiento;

        TextView nombre = (TextView) view.findViewById(R.id.textNombreEjercicioDistancia);
        nombre.setText(ejercicio.getEjercicio().getNombre());


        TextView distancia = (TextView) view.findViewById(R.id.textDistanciaEjercicioDistancia);
        String distanciaS = Integer.toString(ejercicio.getDistancia());
        distancia.setText(distanciaS + "mts");

        ImageButton editarB = (ImageButton) view.findViewById(R.id.buttonEdit);

        editarB.setTag(position);

        editarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClickListenerEditar != null)
                    mClickListenerEditar.onBtnClickEdit((Integer) v.getTag());

            }
        });


        ImageButton eliminarB = (ImageButton) view.findViewById(R.id.buttonDelete);

        eliminarB.setTag(position);

        eliminarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClickListenerEditar != null)
                    mClickListenerEditar.onBtnClickDelete((Integer) v.getTag());
            }
        });

        return view;
    }




}
