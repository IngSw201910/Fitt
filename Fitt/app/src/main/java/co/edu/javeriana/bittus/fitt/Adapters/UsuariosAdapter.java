package co.edu.javeriana.bittus.fitt.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import co.edu.javeriana.bittus.fitt.Filtros.UsuarioFiltro;
import co.edu.javeriana.bittus.fitt.Modelo.Usuario;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.BtnClickListenerSeguir;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;
import co.edu.javeriana.bittus.fitt.Vista.BuscarUsuarioActivity;
import co.edu.javeriana.bittus.fitt.Vista.MostrarUsuarioActivity;
import co.edu.javeriana.bittus.fitt.Vista.MostrarUsuarioSeguidoActivity;

import static co.edu.javeriana.bittus.fitt.Utilidades.PersistenciaFirebase.descargarFotoYPonerEnImageView;


public class UsuariosAdapter extends ArrayAdapter<Usuario> {

    protected List<Usuario> listUsuarios;
    protected Context context;
    private int resource;
    private Usuario usuarioSeleccionado;
    private UsuarioFiltro usuariosFiltro;
    private BtnClickListenerSeguir mClickListenerSeguir = null;




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
        final Usuario usuario = usuarioSeleccionado;

        TextView nombre = view.findViewById(R.id.textNombreBusqueda);
        nombre.setText(usuario.getNombre());

        TextView seguidores =view.findViewById(R.id.textSeguidoresBusqueda);
        seguidores.setText(usuario.getSeguidoresList().size() + " Seguidores");

        TextView seguidos = view.findViewById(R.id.textViewSeguidosBusqueda);
        seguidos.setText(usuario.getSeguidosList().size()+" Seguidos");

        ImageView foto = view.findViewById(R.id.imageViewPerfilBusqueda);
        descargarFotoYPonerEnImageView(usuario.getDireccionFoto(),foto);







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
