package co.edu.javeriana.bittus.fitt.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import co.edu.javeriana.bittus.fitt.Modelo.Parque;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.PersistenciaFirebase;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;
import co.edu.javeriana.bittus.fitt.Vista.ParqueInformacionDetalladaActivity;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private Context mContext;
    private Double longitud;
    private Double latitud;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser mAuth;
    private Parque parqueEncontrado;
    private ImageView fotoParque;
    private RatingBar ratingBar;


    public CustomInfoWindowAdapter(Context context) {
        database = FirebaseDatabase.getInstance();
        mContext = context;
        this.parqueEncontrado = null;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);
    }

    private void rendowWindowText(Marker marker, View view){


        String title = marker.getTitle();
        TextView tvTitle = (TextView) view.findViewById(R.id.tituloInformacion);

        if(!title.equals("")){
            tvTitle.setText(title);
        }

        String snippet = marker.getSnippet();
        TextView tvSnippet = (TextView) view.findViewById(R.id.snippet);

        if(!snippet.equals("")){
            tvSnippet.setText(snippet);
        }
        fotoParque = (ImageView) view.findViewById(R.id.imageViewParque);
        //fotoParque.setImageResource(R.drawable.parques);

        //ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        //ratingBar.setRating(0.0f);

        String ss = marker.getSnippet();
        String[] lineasPartidas = ss.split("\n");
        String[] longitudP = lineasPartidas[2].split(": ");
        longitud = Double.parseDouble(longitudP[1]);

        String[] latitudP = lineasPartidas[3].split(": ");
        latitud = Double.parseDouble(latitudP[1]);


        buscarParque(view);


      // Aqui se tienen que hacer las consultas para agregar la imagen la calificacion y demas
    }

    @Override
    public View getInfoWindow(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }

    public void buscarParque( final View view) {
        myRef = database.getReference(RutasBaseDeDatos.getRutaParques());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean encontrado= false;
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Parque parque = singleSnapshot.getValue(Parque.class);

                    Log.i("aiuda", "Encontró usuario: " + longitud+ " "+parque.getLongitud());
                    if (longitud== parque.getLongitud()  && latitud == parque.getLatitud()) {

                        parqueEncontrado = parque;
                        if(parqueEncontrado!=null){

                            if (!parqueEncontrado.getImagenes().isEmpty()) {
                                PersistenciaFirebase.descargarFotoYPonerEnImageView(parqueEncontrado.getImagenes().get(0), fotoParque);
                            }

                            ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
                            ratingBar.setRating(parqueEncontrado.obtenercalificacion());
                            //ratingBar.setRating(parqueEncontrado.obtenercalificacion());
                            Log.i("aiuda", "Entra: "+ parqueEncontrado.obtenercalificacion());
                            encontrado = true;
                            break;
                        }
                    }

                }
                if(encontrado == false) {
                    ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
                    ratingBar.setRating(0.0f);
                    fotoParque = (ImageView) view.findViewById(R.id.imageViewParque);
                    fotoParque.setImageResource(R.drawable.parques);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Aiuda", "error en la consulta", databaseError.toException());
            }
        });
    }
}