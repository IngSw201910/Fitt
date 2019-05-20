package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import co.edu.javeriana.bittus.fitt.Modelo.Usuario;
import co.edu.javeriana.bittus.fitt.R;

public class MensajeActivity extends AppCompatActivity {


    TextView nombre_usuario_msg;
    ImageView profile_image_msg;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    ImageButton btn_sendMessage;
    EditText text_send;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje);

        nombre_usuario_msg = findViewById(R.id.txt_msg_username);
        profile_image_msg = findViewById(R.id.img_msg_foto);
        btn_sendMessage = findViewById(R.id.btn_sendMessage);
        text_send = findViewById(R.id.text_send);

        intent = getIntent();
        final String userid = intent.getStringExtra("id");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios").child(userid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                nombre_usuario_msg.setText(usuario.getNombre());
                profile_image_msg.setImageResource(R.mipmap.ic_launcher);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = text_send.getText().toString();
                if(!msg.equals("")){
                    sendMessage(firebaseUser.getUid(), userid, msg);
                }else{
                    Toast.makeText(MensajeActivity.this, "No ha escrito nada", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });

    }

    private void sendMessage(String sender, String receiver, String message){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        reference.child("Chats").push().setValue(hashMap);
    }
}