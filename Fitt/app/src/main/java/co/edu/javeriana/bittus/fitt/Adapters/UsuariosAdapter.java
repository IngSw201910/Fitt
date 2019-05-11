package co.edu.javeriana.bittus.fitt.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.List;

import co.edu.javeriana.bittus.fitt.Filtros.UsuarioFiltro;
import co.edu.javeriana.bittus.fitt.Modelo.Usuario;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;
import pl.droidsonroids.gif.GifImageView;

public class UsuariosAdapter extends ArrayAdapter<Usuario> {

    protected List<Usuario> listUsuarios;
    protected Context context;
    private int resource;
    private Usuario usuarioSeleccionado;
    private UsuarioFiltro usuariosFiltro;


    public UsuariosAdapter(@NonNull Context context, int resource, List<Usuario> objects) {
        super(context, resource, objects);
        this.listUsuarios = objects;
        this.context = context;
        this.resource = resource;

    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(resource, null);
        }
        usuarioSeleccionado = listUsuarios.get(position);
        Usuario usuario = usuarioSeleccionado;






        return  view;
    }
    @NonNull
    @Override
    public Filter getFilter() {
        if(usuariosFiltro ==null){
            usuariosFiltro = new UsuarioFiltro(listUsuarios, this);
        }


        return usuariosFiltro;


    }



}
