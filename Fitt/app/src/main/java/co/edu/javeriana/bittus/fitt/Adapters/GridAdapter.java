package co.edu.javeriana.bittus.fitt.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import co.edu.javeriana.bittus.fitt.R;

public class GridAdapter extends BaseAdapter {

    Context context;
    private final List<Bitmap> imagenes;
    View view;
    LayoutInflater layoutInflater;

    public GridAdapter(Context context, List<Bitmap> imagenes) {
        this.context = context;
        this.imagenes = imagenes;
    }

    @Override
    public int getCount() {
        return imagenes.size();
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

        if (layoutInflater == null){
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null){
            convertView=layoutInflater.inflate(R.layout.single_item, null);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.ImagenParque);
        imageView.setImageBitmap(imagenes.get(i));
        return convertView;
    }
}
