package co.edu.javeriana.bittus.fitt.Vista.PopUps;

import android.app.Activity;
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

import co.edu.javeriana.bittus.fitt.Modelo.Parque;
import co.edu.javeriana.bittus.fitt.Modelo.ReseñaParque;
import co.edu.javeriana.bittus.fitt.Modelo.Usuario;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.PersistenciaFirebase;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;

public class PopResenarParque extends Activity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseUser mAuth;
    private Usuario usuario;

    private TextView nombreUsuario;
    private ImageView fotoUsuario;
    private RatingBar calificacion;
    private EditText reseña;
    private Button botonCancelar;
    private Button botonPublicar;

    private double longitud;
    private double latitud;
    private Parque park;
    private Date fecha;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_resenar_parque);

        nombreUsuario = (TextView) findViewById(R.id.textViewNombreUsuario);
        fotoUsuario = (ImageView) findViewById(R.id.imageViewFP);
        calificacion = (RatingBar) findViewById(R.id.ratingBarResena);
        reseña = (EditText) findViewById(R.id.editTextReseña);
        botonCancelar = (Button) findViewById(R.id.buttonCancelarReseña);
        botonPublicar = (Button) findViewById(R.id.buttonPublicarReseña);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        longitud = bundle.getDouble("longitud");
        latitud = bundle.getDouble("latitud");
        park = null;
        fecha = Calendar.getInstance().getTime();

        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(RutasBaseDeDatos.getRutaUsuarios()).child(mAuth.getUid());

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

                buscarParque();
                if (park!=null){
                    ReseñaParque reseñaParque = new ReseñaParque(usuario, reseña.getText().toString(),fecha.toString(), calificacion.getRating());
                    park.getReseñas().add(reseñaParque);
                    agregarReseñaParque();
                }
                else{
                    Parque nuevoParque = new Parque(latitud+" "+longitud, (float) 2.0,latitud, longitud);
                    subirParque(nuevoParque);
                    park = nuevoParque;
                    ReseñaParque reseñaParque = new ReseñaParque(usuario, reseña.getText().toString(),fecha.toString(), calificacion.getRating());
                    park.getReseñas().add(reseñaParque);
                    agregarReseñaParque();

                }

            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuario = dataSnapshot.getValue(Usuario.class);
                nombreUsuario.setText(usuario.getNombre());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void subirParque (Parque parque){
        almacenarInformacionParque(RutasBaseDeDatos.getRutaParques(),parque);

    }

    //Guarda la información en la ruta+/+key (la key se genera automaticamente)
    public String almacenarInformacionParque (String ruta, Parque parque){
        database = FirebaseDatabase.getInstance();
        myRef=database.getReference(ruta);
        String key = parque.getLatYLong();
        myRef=database.getReference(ruta+key);
        myRef.setValue(parque);
        return key;
    }

    public void buscarParque() {
        myRef = database.getReference(RutasBaseDeDatos.getRutaParques());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Parque parque = singleSnapshot.getValue(Parque.class);
                    Log.i("aiuda", "Encontró usuario: " + parque.getLatYLong());
                    if (longitud == parque.getLongitud() &&  latitud == parque.getLatitud()) {
                        Toast.makeText(PopResenarParque.this, "El parque si existe", Toast.LENGTH_SHORT).show();
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

    public void agregarReseñaParque() {
        myRef = database.getReference(RutasBaseDeDatos.getRutaParques());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Parque parque = singleSnapshot.getValue(Parque.class);
                    Log.i("aiuda", "Encontró usuario: " + parque.getLatYLong());
                    if (longitud == parque.getLongitud() &&  latitud == parque.getLatitud()) {
                        DatabaseReference refi = singleSnapshot.getRef();
                        refi.setValue(park);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Aiuda", "error en la consulta", databaseError.toException());
            }
        });
    }
}