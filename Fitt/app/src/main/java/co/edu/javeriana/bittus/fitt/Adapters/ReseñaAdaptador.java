package co.edu.javeriana.bittus.fitt.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import co.edu.javeriana.bittus.fitt.Modelo.Reseña;
import co.edu.javeriana.bittus.fitt.Modelo.Usuario;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.PersistenciaFirebase;

public class ReseñaAdaptador extends ArrayAdapter<Reseña> {

    private static LayoutInflater inflater = null;

    Context context;
    List<Reseña> reseñas;

    public ReseñaAdaptador(@NonNull Context context, int resource, List<Reseña> reseñas) {
        super(context, resource, reseñas);
        this.context = context;
        this.reseñas = reseñas;

    }



    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View vista = convertView;
        if(vista == null){
            vista = inflater.inflate(R.layout.elemento_lista_resena_parque, null);
        }
        TextView nombreUsuario = (TextView) vista.findViewById(R.id.textViewNomUsuario);
        TextView fecha = (TextView) vista.findViewById(R.id.textViewFechaReseña);
        TextView comentario = (TextView) vista.findViewById(R.id.textViewComentario);
        RatingBar calificacion = (RatingBar) vista.findViewById(R.id.ratingBarSP);
        ImageView fotoUsuario = (ImageView) vista.findViewById(R.id.imageViewFotoUsuarioRP);

        if (!reseñas.isEmpty()) {
            nombreUsuario.setText(reseñas.get(i).getUsuario().getNombre());
            fecha.setText(reseñas.get(i).getFecha().toString());
            comentario.setText(reseñas.get(i).getReseña());
            calificacion.setRating(reseñas.get(i).getCalificacion());
            PersistenciaFirebase.descargarFotoYPonerEnImageView(reseñas.get(i).getUsuario().getDireccionFoto(), fotoUsuario);
        }
        return vista;
    }
}
