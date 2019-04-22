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

import co.edu.javeriana.bittus.fitt.Modelo.Sesion;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.BtnClickListenerRow;
import co.edu.javeriana.bittus.fitt.Vista.CrearRutinaSesionesActivity;

public class SesionesAdapter extends ArrayAdapter<Sesion> {


    private List<Sesion> listSesion;
    private Context context;
    private int resource;
    private Sesion sesion;
    private BtnClickListenerRow mClickListener = null;

    public SesionesAdapter(@NonNull Context context, int resource, List<Sesion> objects, BtnClickListenerRow listenerRow) {
        super(context, resource, objects);
        this.listSesion = objects;
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

        sesion = listSesion.get(position);

        TextView nombre = view.findViewById(R.id.textNombreSesion);
        nombre.setText(sesion.getNombre());

        TextView duracion = view.findViewById(R.id.textDuracion);
        duracion.setText(sesion.getDuracion()+ " minutos");

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

    private void editarSesion() {

        CrearRutinaSesionesActivity crearRutinaSesionesActivity = (CrearRutinaSesionesActivity) context;
        crearRutinaSesionesActivity.editarSesion(sesion, getPosition(sesion));

    }
}
