package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import co.edu.javeriana.bittus.fitt.R;

public class registroPrueba extends AppCompatActivity {

    EditText pUsername, pEmail, pPassword;
    Button buttonAccept;

    private FirebaseAuth mAuth;
    private static final String TAG = "Registro";
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_prueba);

        pUsername = findViewById(R.id.pUsername);
        pEmail = findViewById(R.id.pEmail);
        pPassword = findViewById(R.id.pPassword);
        buttonAccept = findViewById(R.id.buttonAccept);


        mAuth = FirebaseAuth.getInstance();

        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_username = pUsername.getText().toString();
                String txt_email = pEmail.getText().toString();
                String password = pPassword.getText().toString();
                registerNewUserPrueba(txt_username, txt_email, password);
            }
        });

    }

    private void registerNewUserPrueba(final String username, final String email, final String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(registroPrueba.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
