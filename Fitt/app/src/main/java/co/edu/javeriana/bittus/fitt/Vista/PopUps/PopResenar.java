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
import co.edu.javeriana.bittus.fitt.Modelo.Reseña;
import co.edu.javeriana.bittus.fitt.Modelo.Usuario;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;

public class PopResenar extends Activity {

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
    private Bundle bundleParques;


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
        bundleParques = getIntent().getBundleExtra("bundle");

        //con esto me aseguro que si lo llaman desde lo del parque va a ser una reseña de un parque, pero no deberia ser así, todos esos metodos que instancian un parque deberia hacerlos desde donde los llamos, esta clase solo instancia reseñas y nada mas
        if(bundleParques!=null){
            longitud = bundleParques.getDouble("longitud");
            latitud = bundleParques.getDouble("latitud");
        }


        park = null;
        fecha = Calendar.getInstance().getTime();

        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        //es RUTA_USUARIOS
        myRef = database.getReference(RutasBaseDeDatos.RUTA_USUARIOS).child(mAuth.getUid());



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
                //lo mismo que dije arriba
                if(bundleParques!=null){
                    buscarParque();
                    if (park!=null){
                        Reseña reseña = new Reseña(usuario, PopResenar.this.reseña.getText().toString(),fecha.toString(), calificacion.getRating());
                        park.getReseñas().add(reseña);
                        agregarReseñaParque();
                    }
                    else{
                        Parque nuevoParque = new Parque(latitud+" "+longitud, (float) 2.0,latitud, longitud);
                        subirParque(nuevoParque);
                        park = nuevoParque;
                        Reseña reseña = new Reseña(usuario, PopResenar.this.reseña.getText().toString(),fecha.toString(), calificacion.getRating());
                        park.getReseñas().add(reseña);
                        agregarReseñaParque();

                    }
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
        //esto no deberia ir aca
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
        //por dos
    public void buscarParque() {
        myRef = database.getReference(RutasBaseDeDatos.getRutaParques());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Parque parque = singleSnapshot.getValue(Parque.class);
                    Log.i("aiuda", "Encontró usuario: " + parque.getLatYLong());
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