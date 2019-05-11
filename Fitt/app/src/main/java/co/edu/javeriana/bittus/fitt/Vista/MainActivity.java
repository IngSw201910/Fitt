package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;
import co.edu.javeriana.bittus.fitt.Utilidades.UtilsMiguel;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Button iniciarSesionB;
    private TextView registrarUsuarioB;
    private EditText correoET;
    private EditText passET;

    private String TAG = "AUTENTICACIÓN";


    //prueba comentario
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        correoET = (EditText) findViewById(R.id.correo);
        passET = (EditText) findViewById(R.id.pass);


        iniciarSesionB = (Button) findViewById(R.id.buttonIniciarSesion);
        registrarUsuarioB = (TextView) findViewById(R.id.textViewRegistrarseUsuario);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
// User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    startActivity(new Intent(MainActivity.this, MenuPrincipalUsuarioFragment.class));
                } else {
// User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        registrarUsuarioB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registrarUsuario();
            }


        });


        iniciarSesionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInUser();
            }
        });
    }


    private void registrarUsuario() {
        startActivity(new Intent(MainActivity.this, registroPrueba.class));

        //Intent intent = new Intent(MainActivity.this, RegistroUsuarioActivity.class);
        //startActivityForResult(intent, UtilsMiguel.REQUEST_CODE_REGISTRAR_USUARIO);



    }
    protected void signInUser(){
        if(validateForm()){
            String email = correoET.getText().toString();
            String password = passET.getText().toString();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "signInWithEmail:failed", task.getException());
                                Toast.makeText(MainActivity.this, R.string.auth_failed,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        correoET.setText("");
        passET.setText("");
    }

    private boolean validateForm() {
        boolean valid = true;
        String email = correoET.getText().toString();
        if (TextUtils.isEmpty(email)) {
            correoET.setError("Requerido.");
            valid = false;
        } else {
            if (!Utils.isEmailValid(email)){
                correoET.setError("Email inválido.");
                valid = false;
            }
            else {
                correoET.setError(null);
            }
        }
        String password = passET.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passET.setError("Requerido.");
            valid = false;
        } else {
            passET.setError(null);
        }
        return valid;
    }



    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
