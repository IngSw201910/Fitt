package co.edu.javeriana.bittus.fitt.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import co.edu.javeriana.bittus.fitt.Modelo.Entrenador;
import co.edu.javeriana.bittus.fitt.Modelo.Usuario;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Vista.MensajeActivity;

public class MensajeUsuarioAdapter extends RecyclerView.Adapter<MensajeUsuarioAdapter.ViewHolder>{

    private Context mContext;
    private List<Usuario> mUsuarios;
    FirebaseUser mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    String idNewUser = "";

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
        final Usuario usuario = mUsuarios.get(i);
        viewHolder.username.setText(usuario.getNombre());
        //Falta obtener el enlace de donde esta guardado la imagen en Firebase
        viewHolder.profile_image.setImageResource(R.mipmap.ic_launcher);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myRef = database.getReference("usuarios");
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                            Usuario aux = singleSnapshot.getValue(Usuario.class);
                            if(usuario.getCorreo().equals(aux.getCorreo())){
                                String idTemp = singleSnapshot.getKey();
                                idNewUser = idTemp;
                                Log.d("MATCH", idNewUser);
                                Intent intent = new Intent(mContext, MensajeActivity.class);
                                intent.putExtra("id", idNewUser);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(intent);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
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
