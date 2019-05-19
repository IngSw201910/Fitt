package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import co.edu.javeriana.bittus.fitt.Adapters.GridAdapter;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;
import co.edu.javeriana.bittus.fitt.Utilidades.UtilsMiguel;
import co.edu.javeriana.bittus.fitt.Vista.PopUps.PopResenarParque;

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


        Bundle bundle = getIntent().getBundleExtra("bundle");
        nombreParque.setText(bundle.getString("titulo"));
        direccion.setText(bundle.getString("direccion"));
        longitud = bundle.getDouble("longitud");
        latitud = bundle.getDouble("latitud");

        if (!imagenes.isEmpty()) {
            GridAdapter gridAdapter = new GridAdapter(this, imagenes);
            gridView.setAdapter(gridAdapter);
        }
        else{
            Toast.makeText(ParqueInformacionDetalladaActivity.this, "No hay imagenes disponibles", Toast.LENGTH_LONG).show();
        }

        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils utils = new Utils();
                utils.tomarFotoDesdeCamara(ParqueInformacionDetalladaActivity.this,REQUEST_CODE_TAKE_PHOTO);
            }
        });

        btnAñadirFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils utils = new Utils();
                utils.cargarFotoDesdeCamara(ParqueInformacionDetalladaActivity.this, REQUEST_CODE_UPLOAD_PHOTO);
            }
        });

        añadirReseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ParqueInformacionDetalladaActivity.this, PopResenarParque.class);
                Bundle bundle = new Bundle();
                bundle.putDouble("latitud", latitud);
                bundle.putDouble("longitud", longitud);
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

}
