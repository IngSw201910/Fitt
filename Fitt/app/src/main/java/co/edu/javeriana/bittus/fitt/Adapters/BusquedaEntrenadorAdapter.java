package co.edu.javeriana.bittus.fitt.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.edu.javeriana.bittus.fitt.Filtros.EntrenadorFiltro;
import co.edu.javeriana.bittus.fitt.Filtros.UsuarioFiltro;
import co.edu.javeriana.bittus.fitt.Modelo.Entrenador;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Vista.Entrenador_miEntrenadorActivity;
import co.edu.javeriana.bittus.fitt.Vista.MostrarUsuarioActivity;

public class BusquedaEntrenadorAdapter extends ArrayAdapter<Entrenador> {

    protected List<Entrenador> listUsuarios;
    protected Context context;
    private int resource;
    private Entrenador usuarioSeleccionado;
    private EntrenadorFiltro usuariosFiltro;


    public BusquedaEntrenadorAdapter(@NonNull Context context, int resource, List<Entrenador> objects) {
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
        Entrenador usuario = usuarioSeleccionado;

        //Datos nombre, clientes, titulo, aniosExperiencia
        TextView nombre = view.findViewById(R.id.nombreEntrenador_E);
        nombre.setText(usuario.getNombre());

        TextView seguidores =view.findViewById(R.id.numClientesEntrenador_E);
        seguidores.setText(usuario.getClientes().size() + " Clientes");

        TextView seguidos = view.findViewById(R.id.estatus_titulo_E);
        seguidos.setText(usuario.getNombreTitulo());

        ImageView foto = view.findViewById(R.id.imagenPerfilUser_E);
        //INSERTAR IMAGEN!!

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Si el usuario no lo sigue
                Intent intent = new Intent(context, Entrenador_miEntrenadorActivity.class);
                intent.putExtra("objectData",usuarioSeleccionado);
                context.startActivity(intent);
            }
        });

        return  view;
    }
    @NonNull
    @Override
    public Filter getFilter() {
        if(usuariosFiltro ==null){
            usuariosFiltro = new EntrenadorFiltro(listUsuarios, this);
        }

        return usuariosFiltro;

    }

}
