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
    private Usuario usuarioConectado;
    FirebaseDatabase database;
    FirebaseDatabase copia;
    DatabaseReference myRef;
    DatabaseReference myRef2;
    private FirebaseUser mAuth;


    public UsuariosAdapter(@NonNull Context context, int resource, List<Usuario> objects) {
        super(context, resource, objects);
        this.listUsuarios = objects;
        this.context = context;
        this.resource = resource;


    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(RutasBaseDeDatos.RUTA_USUARIOS).child(mAuth.getUid());


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                usuarioConectado = dataSnapshot.getValue(Usuario.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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


        Button bSeguir =view.findViewById(R.id.buttonSeguirBusqueda);

        /*if(usuario.getId().compareTo(usuarioConectado.getId())==0){
            bSeguir.setVisibility(View.GONE);
        }*/

        bSeguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usuarioConectado.getSeguidosList().add(usuarioSeleccionado);
                myRef.setValue(usuarioConectado);

                myRef2 = database.getReference(RutasBaseDeDatos.RUTA_USUARIOS).child(usuarioSeleccionado.getId());
                usuarioSeleccionado.getSeguidoresList().add(usuarioConectado);
                myRef2.setValue(usuarioSeleccionado);

            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*if(usuarioSeleccionado.getId().compareTo(usuarioConectado.getId())==0){

                }else{*/
                    boolean seguido = usuarioSeguido(usuarioSeleccionado);
                    if(!seguido){
                        //Si el usuario no lo sigue
                        Intent intent = new Intent(context, MostrarUsuarioActivity.class);
                        intent.putExtra("objectData",usuarioSeleccionado);
                        context.startActivity(intent);
                    } else{
                        //Si el usuario lo sigue
                        Intent intent = new Intent(context, MostrarUsuarioSeguidoActivity.class);
                        intent.putExtra("objectData",usuarioSeleccionado);
                        context.startActivity(intent);
                    }
                }

            //}
        });

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

    public boolean usuarioSeguido(Usuario usuarioS){
        for(Usuario usr: usuarioConectado.getSeguidosList()){
            if(usuarioS.getId().compareTo(usr.getId())==0){
                return true;
            }
        }
        return false;
    }



}
