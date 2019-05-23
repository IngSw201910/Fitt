package co.edu.javeriana.bittus.fitt.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
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
    private LatLng posicion;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser mAuth;
    private Parque parqueEncontrado;


    public CustomInfoWindowAdapter(Context context, LatLng posicion) {
        mContext = context;
        this.posicion = posicion;
        this.parqueEncontrado = null;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);
    }

    private void rendowWindowText(Marker marker, View view){

        buscarParque();
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

        if (!parqueEncontrado.getImagenes().isEmpty()) {
            ImageView fotoParque = (ImageView) view.findViewById(R.id.imageViewParque);
            PersistenciaFirebase.descargarFotoYPonerEnImageView(parqueEncontrado.getImagenes().get(0), fotoParque);
        }
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

    public void buscarParque() {
        myRef = database.getReference(RutasBaseDeDatos.getRutaParques());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean encontrado= false;
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Parque parque = singleSnapshot.getValue(Parque.class);
                    Log.i("aiuda", "Encontró usuario: " + parque.getNombreParqueFire());
                    if (posicion.longitude == parque.getLongitud() &&  posicion.longitude == parque.getLatitud()) {
                        parqueEncontrado = parque;
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
}