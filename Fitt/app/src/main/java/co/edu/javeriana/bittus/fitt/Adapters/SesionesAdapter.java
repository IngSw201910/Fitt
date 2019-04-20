package co.edu.javeriana.bittus.fitt.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import co.edu.javeriana.bittus.fitt.Modelo.Sesion;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Vista.CrearRutinaSesionesActivity;

public class SesionesAdapter extends ArrayAdapter<Sesion> {


    private List<Sesion> listSesion;
    private Context context;
    private int resource;


    public SesionesAdapter(@NonNull Context context, int resource, List<Sesion> objects) {
        super(context, resource, objects);
        this.listSesion = objects;
        this.context = context;
        this.resource = resource;
    }



    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(resource, null);
        }
        final Sesion sesion = listSesion.get(position);

        TextView nombre = view.findViewById(R.id.textNombreSesion);
        nombre.setText(sesion.getNombre());

        TextView duracion = view.findViewById(R.id.textDuracion);
        duracion.setText(sesion.getDuracion()+ " minutos");

        Button editarB = view.findViewById(R.id.buttonEditarSesion);

        editarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrearRutinaSesionesActivity crearRutinaSesionesActivity = (CrearRutinaSesionesActivity) context;
                crearRutinaSesionesActivity.editarSesion(sesion);
            }
        });

        Button eliminarB = view.findViewById(R.id.buttonDelete);

        eliminarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrearRutinaSesionesActivity crearRutinaSesionesActivity = (CrearRutinaSesionesActivity) context;
                crearRutinaSesionesActivity.eliminarSesion(sesion);
            }
        });


        return  view;
    }
}
