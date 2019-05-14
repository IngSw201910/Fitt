package co.edu.javeriana.bittus.fitt.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import java.util.List;

import co.edu.javeriana.bittus.fitt.Modelo.Usuario;
import co.edu.javeriana.bittus.fitt.R;

public class MensajeUsuarioAdapter extends RecyclerView.Adapter<MensajeUsuarioAdapter.ViewHolder>{

    private Context mContext;
    private List<Usuario> mUsuarios;

    public MensajeUsuarioAdapter(Context mContext, List<Usuario> mUsuarios){
        this.mUsuarios = mUsuarios;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Usuario usuario = mUsuarios.get(i);
        viewHolder.username.setText(usuario.getNombre());
        viewHolder.profile_image.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount() {
        return mUsuarios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public ImageView profile_image;

        public ViewHolder(View itemView){
            super(itemView);

            username = itemView.findViewById(R.id.nombreUser);
            profile_image = itemView.findViewById(R.id.imagenPerfilUser);
        }
    }
}
