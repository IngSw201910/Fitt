package co.edu.javeriana.bittus.fitt.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import co.edu.javeriana.bittus.fitt.Modelo.Parque;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.PersistenciaFirebase;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;
import co.edu.javeriana.bittus.fitt.Utilidades.StringsSebastian;

public class GridAdapter extends BaseAdapter {

    Context context;
    private  List<String> imagenes;
    private LayoutInflater thisInflater;

    public GridAdapter(Context context, List<String> imagenes) {
        this.context = context;
        this.thisInflater = LayoutInflater.from(context);
        this.imagenes = imagenes;
    }

    @Override
    public int getCount() {
        return imagenes.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        if (convertView == null){
            convertView=thisInflater.inflate(R.layout.single_item, viewGroup, false);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.ImagenParque);
            PersistenciaFirebase.descargarFotoYPonerEnImageView(imagenes.get(i),imageView);
        }

        return convertView;
    }
}
