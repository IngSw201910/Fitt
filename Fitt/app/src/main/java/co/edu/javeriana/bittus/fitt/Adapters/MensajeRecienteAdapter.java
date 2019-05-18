package co.edu.javeriana.bittus.fitt.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import co.edu.javeriana.bittus.fitt.Modelo.Chat;
import co.edu.javeriana.bittus.fitt.Modelo.Usuario;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Vista.MensajeActivity;

public class MensajeRecienteAdapter extends RecyclerView.Adapter<MensajeRecienteAdapter.ViewHolder>{

    private Context mContext;
    private List<Usuario> mUsuarios;

    String theLastMessage;

    public MensajeRecienteAdapter(Context mContext, List<Usuario> mUsuarios){
        this.mUsuarios = mUsuarios;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MensajeRecienteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_user_msg, viewGroup, false);
        return new MensajeRecienteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MensajeRecienteAdapter.ViewHolder viewHolder, int i) {
        final Usuario usuario = mUsuarios.get(i);
        viewHolder.username.setText(usuario.getNombre());
        //Falta obtener el enlace de donde esta guardado la imagen en Firebase
        viewHolder.profile_image.setImageResource(R.mipmap.ic_launcher);
        lastMessage(usuario.getId(), viewHolder.last_msg);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MensajeActivity.class);
                intent.putExtra("id", usuario.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
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
        public TextView last_msg;

        public ViewHolder(View itemView){
            super(itemView);
            username = itemView.findViewById(R.id.nombreUser);
            profile_image = itemView.findViewById(R.id.imagenPerfilUser);
            last_msg = itemView.findViewById(R.id.lastMessage);
        }
    }

    private void lastMessage(final String userid, final TextView lastMessage){
        theLastMessage = "default";
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Chats");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if(chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userid) ||
                        chat.getReceiver().equals(userid) && chat.getSender().equals(firebaseUser.getUid())){
                        theLastMessage = chat.getMessage().toString();
                    }
                }
                switch (theLastMessage){
                    case "default":
                        lastMessage.setText("No Message");
                        break;

                    default:
                        lastMessage.setText(theLastMessage);
                        break;
                }

                theLastMessage = "default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}