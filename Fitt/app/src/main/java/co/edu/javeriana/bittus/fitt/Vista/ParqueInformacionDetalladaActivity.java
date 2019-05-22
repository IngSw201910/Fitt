package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import co.edu.javeriana.bittus.fitt.Adapters.GridAdapter;
import co.edu.javeriana.bittus.fitt.Adapters.ReseñaAdaptador;
import co.edu.javeriana.bittus.fitt.Modelo.Parque;
import co.edu.javeriana.bittus.fitt.Modelo.Reseña;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;
import co.edu.javeriana.bittus.fitt.Vista.PopUps.PopResenar;

public class ParqueInformacionDetalladaActivity extends AppCompatActivity {

    private TextView nombreParque;
    private TextView direccion;
    private ImageButton btnTomarFoto;
    private ImageButton btnAñadirFotos;
    private GridView gridView;
    private RatingBar calificacion;
    private Button añadirReseña;
    private ListView reseñas;
    private List<Bitmap> imagenes;
    private float rating;
    private double longitud;
    private double latitud;
    private Parque park;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser mAuth;


    public static final int REQUEST_CODE_TAKE_PHOTO = 11;
    public static final int REQUEST_CODE_UPLOAD_PHOTO = 12;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parque_informacion_detallada);
        nombreParque = (TextView) findViewById(R.id.textViewNombreParque);
        direccion = (TextView) findViewById(R.id.textViewDireccion);
        btnTomarFoto = (ImageButton) findViewById(R.id.imageButtonTomarFoto);
        btnAñadirFotos = (ImageButton) findViewById(R.id.imageButtonAnadirFotos);
        gridView= (GridView) findViewById(R.id.grid_view);
        calificacion = (RatingBar) findViewById(R.id.ratingBarDetalle);
        añadirReseña = (Button) findViewById(R.id.buttonAgregarReseña);
        reseñas = (ListView) findViewById(R.id.ListViewReseñas);
        imagenes = new ArrayList<Bitmap>();

        mAuth = FirebaseAuth.getInstance().getCurrentUser();

        Bundle bundle = getIntent().getBundleExtra("bundle");
        nombreParque.setText(bundle.getString("titulo"));
        direccion.setText(bundle.getString("direccion"));
        longitud = bundle.getDouble("longitud");
        latitud = bundle.getDouble("latitud");

        park = null;
        database = FirebaseDatabase.getInstance();
        buscarParque();
        if (park!=null){
            //sacar imagenes de la base de datos
            /*GridAdapter gridAdapter = new GridAdapter(this, park.getImagenes());*/
            GridAdapter gridAdapter = new GridAdapter(this, imagenes);
            gridView.setAdapter(gridAdapter);
            Toast.makeText(ParqueInformacionDetalladaActivity.this, park.getReseñas().size(), Toast.LENGTH_LONG).show();
            Toast.makeText(ParqueInformacionDetalladaActivity.this, "ok", Toast.LENGTH_LONG).show();
            ReseñaAdaptador reseñaAdaptador = new ReseñaAdaptador(this, park.getReseñas());
            reseñas.setAdapter(reseñaAdaptador);
            calificacion.setRating(obtenercalificacion());
            System.out.println(obtenercalificacion());
        }
        else{
            Toast.makeText(ParqueInformacionDetalladaActivity.this, "No hay informacion disponible de este parque", Toast.LENGTH_LONG).show();
        }

        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils utils = new Utils();
                if (park == null) {
                    Parque nuevoParque = new Parque(nombreParque.getText().toString(), (float) 2.0, latitud, longitud);
                    subirParque(nuevoParque);
                    park = nuevoParque;
                }
                utils.tomarFotoDesdeCamara(ParqueInformacionDetalladaActivity.this,REQUEST_CODE_TAKE_PHOTO);
            }
        });

        btnAñadirFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils utils = new Utils();
                if (park == null) {
                    Parque nuevoParque = new Parque(nombreParque.getText().toString(), (float) 2.0, latitud, longitud);
                    subirParque(nuevoParque);
                    park = nuevoParque;
                }
                utils.cargarFotoDesdeCamara(ParqueInformacionDetalladaActivity.this, REQUEST_CODE_UPLOAD_PHOTO);
            }
        });

        añadirReseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ParqueInformacionDetalladaActivity.this, PopResenar.class);
                Bundle bundle = new Bundle();
                bundle.putDouble("latitud", latitud);
                bundle.putDouble("longitud", longitud);

                bundle.putString("nombreParque", nombreParque.getText().toString());
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        Bitmap bitmapFoto;

        if(requestCode== REQUEST_CODE_TAKE_PHOTO && resultCode==RESULT_OK){
            Bundle extras = data.getExtras();
            bitmapFoto = (Bitmap) extras.get("data");
            //Subir foto a firebase y descargarlas fotos en imagenes, borrar linea de abajo
            imagenes.add(bitmapFoto);
            GridAdapter gridAdapter = new GridAdapter(this,  imagenes);
            gridView.setAdapter(gridAdapter);
        }else if(requestCode == REQUEST_CODE_UPLOAD_PHOTO  && resultCode==RESULT_OK){
            Uri path = data.getData();
            try {
                bitmapFoto = MediaStore.Images.Media.getBitmap(this.getContentResolver(),path);
                //Subir foto a firebase y descargarlas fotos en imagenes, borrar linea de abajo
                imagenes.add(bitmapFoto);
                GridAdapter gridAdapter = new GridAdapter(this,  imagenes);
                gridView.setAdapter(gridAdapter);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void buscarParque() {
        myRef = database.getReference(RutasBaseDeDatos.getRutaParques());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Parque parque = singleSnapshot.getValue(Parque.class);
                    Log.i("aiuda", "Encontró usuario: " + parque.getNombreParque());
                    if (longitud == parque.getLongitud() &&  latitud == parque.getLatitud()) {
                        Toast.makeText(ParqueInformacionDetalladaActivity.this, "El parque si existe"+parque.getCalificación(),Toast.LENGTH_SHORT).show();
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

    public void subirParque (Parque parque){
        almacenarInformacionParque(RutasBaseDeDatos.getRutaParques(),parque);

    }

    //Guarda la información en la ruta+/+key
    public String almacenarInformacionParque (String ruta, Parque parque){
        database = FirebaseDatabase.getInstance();
        myRef=database.getReference(ruta);
        String key = parque.getNombreParque();
        myRef=database.getReference(ruta+key);
        myRef.setValue(parque);
        return key;
    }

    public float obtenercalificacion() {
        float promedio = 0;
        if (park!= null){
            for (Reseña reseña : park.getReseñas()){
                promedio = promedio + reseña.getCalificacion();
            }
            promedio = promedio/park.getReseñas().size();
            return promedio;
        }
        else{
            return -1;
        }
    }

}
