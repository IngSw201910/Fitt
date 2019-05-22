package co.edu.javeriana.bittus.fitt.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import co.edu.javeriana.bittus.fitt.Modelo.Reseña;
import co.edu.javeriana.bittus.fitt.R;

public class ReseñaAdaptador extends BaseAdapter {

    private static LayoutInflater inflater = null;

    Context context;
    List<Reseña> reseñas;

    public ReseñaAdaptador(Context context, List<Reseña> reseñas) {
        this.context = context;
        this.reseñas = reseñas;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return reseñas.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final View vista = inflater.inflate(R.layout.elemento_lista_resena_parque, null);
        TextView nombreUsuario = (TextView) vista.findViewById(R.id.textViewNomUsuario);
        TextView fecha = (TextView) vista.findViewById(R.id.textViewFechaReseña);
        TextView comentario = (TextView) vista.findViewById(R.id.textViewComentario);
        RatingBar calificacion = (RatingBar) vista.findViewById(R.id.ratingBarSP);
        ImageView fotoUsuario = (ImageView) vista.findViewById(R.id.imageViewFotoUsuarioRP);

        nombreUsuario.setText(reseñas.get(i).getUsuario().getNombre());
        fecha.setText(reseñas.get(i).getFecha());
        comentario.setText(reseñas.get(i).getReseña());
        calificacion.setRating(reseñas.get(i).getCalificacion());
        /*fotoUsuario.setImageBitmap(reseñas.get(i).getUsuario().);*/
        return null;
    }
}
