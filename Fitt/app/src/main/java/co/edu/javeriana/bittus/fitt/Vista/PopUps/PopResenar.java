package co.edu.javeriana.bittus.fitt.Vista.PopUps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import co.edu.javeriana.bittus.fitt.Modelo.Parque;
import co.edu.javeriana.bittus.fitt.Modelo.Reseña;
import co.edu.javeriana.bittus.fitt.Modelo.Usuario;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.PersistenciaFirebase;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;
import co.edu.javeriana.bittus.fitt.Utilidades.StringsMiguel;
import co.edu.javeriana.bittus.fitt.Utilidades.StringsSebastian;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;
import co.edu.javeriana.bittus.fitt.Utilidades.UtilsMiguel;

public class PopResenar extends Activity {

    private Usuario usuario;
    private TextView nombreUsuario;
    private ImageView fotoUsuario;
    private RatingBar calificacion;
    private EditText reseña;
    private Button botonCancelar;
    private Button botonPublicar;

    private Date fecha;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_resenar);

        nombreUsuario = (TextView) findViewById(R.id.textViewNombreUsuario);
        fotoUsuario = (ImageView) findViewById(R.id.imageViewFP);
        calificacion = (RatingBar) findViewById(R.id.ratingBarResena);
        reseña = (EditText) findViewById(R.id.editTextReseña);
        botonCancelar = (Button) findViewById(R.id.buttonCancelarReseña);
        botonPublicar = (Button) findViewById(R.id.buttonPublicarReseña);


        //el bundle deberia tener como llave "parquesReseña" o algo asi, haciendo referencia a lo que pasa, men pa sidoso
        bundle = getIntent().getExtras();
        usuario =(Usuario) bundle.getSerializable(StringsMiguel.LLAVE_USUARIO);
        nombreUsuario.setText(usuario.getNombre());
        PersistenciaFirebase.descargarFotoYPonerEnImageView(usuario.getDireccionFoto(), fotoUsuario);
        //Falta inflar la fecha
        fecha = new Date();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width/1.1), (int) (height/2.4));

        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        botonPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generarReseña();

            }
        });


    }

    public void generarReseña(){
        String comentario = reseña.getText().toString();
        float rating = calificacion.getRating();
        if (comentario.isEmpty()){
            reseña.setError(StringsMiguel.CAMPO_OBLIGATORIO);
        }
        else{
            Reseña reseña = new Reseña(usuario, comentario, fecha, rating);
            Intent intent = getIntent();
            bundle.putSerializable(StringsSebastian.LLAVE_RESENA, reseña);
            intent.putExtras(bundle);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }
        //esto no deberia ir aca
    /*public void subirParque (Parque parque){
        almacenarInformacionParque(RutasBaseDeDatos.getRutaParques(),parque);

    }

    //Guarda la información en la ruta+/+key
    public String almacenarInformacionParque (String ruta, Parque parque){
        database = FirebaseDatabase.getInstance();
        myRef=database.getReference(ruta);
        String key =  parque.getNombreParque() + myRef.push().getKey() ;
        myRef=database.getReference(ruta+key);
        myRef.setValue(parque);
        return key;
    }
        //por dos
    public void buscarParque() {
        myRef = database.getReference(RutasBaseDeDatos.getRutaParques());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Parque parque = singleSnapshot.getValue(Parque.class);
                    Log.i("aiuda", "Encontró usuario: " + parque.getLatitud());
                    if (longitud == parque.getLongitud() &&  latitud == parque.getLatitud()) {
                        Toast.makeText(PopResenar.this, "El parque si existe", Toast.LENGTH_SHORT).show();
                        park = parque;
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Aiuda", "error en la consulta", databaseError.toException());
            }
        });
    }
        //esto hagalo en la clase que llamo, utilizando el metodo onActivityResult sidoso, mire mis popUps
    public void agregarReseñaParque() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.child("Parques").child(nombreParque).setValue(park);
    }*/
}