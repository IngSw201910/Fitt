package co.edu.javeriana.bittus.fitt.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import co.edu.javeriana.bittus.fitt.Modelo.Reseña;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.PersistenciaFirebase;

public class FotoParquesAdapter extends ArrayAdapter {
    private static LayoutInflater inflater = null;

    Context context;
    List<String> imagenes;

    public FotoParquesAdapter(@NonNull Context context, int resource, List<String> imagenes) {
        super(context, resource, imagenes);
        this.context = context;
        this.imagenes = imagenes;

    }



    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View vista = convertView;
        if(vista == null){
            vista = LayoutInflater.from(context).inflate(R.layout.single_item, null);
        }

        ImageView fotoParque = (ImageView) vista.findViewById(R.id.ImagenParque);

        if (!imagenes.isEmpty()) {
            PersistenciaFirebase.descargarFotoYPonerEnImageView(imagenes.get(i), fotoParque);
        }
        return vista;
    }
}

