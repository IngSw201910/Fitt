package co.edu.javeriana.bittus.fitt.Vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

import co.edu.javeriana.bittus.fitt.R;

public class ChatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
    }

    public void gotoNewMessage(View view){
        Intent nextActivity = new Intent(this, nuevoMensajeActivity.class);
        startActivity(nextActivity);
    }
}
