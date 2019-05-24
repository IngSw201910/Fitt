package co.edu.javeriana.bittus.fitt.Vista;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import co.edu.javeriana.bittus.fitt.Adapters.FotoParquesAdapter;
import co.edu.javeriana.bittus.fitt.Adapters.ReseñaAdaptador;
import co.edu.javeriana.bittus.fitt.Modelo.LocalizacionUsuario;
import co.edu.javeriana.bittus.fitt.Modelo.Parque;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;
import co.edu.javeriana.bittus.fitt.Utilidades.UtilsJhonny;

import static co.edu.javeriana.bittus.fitt.Utilidades.Permisos.requestPermission;

public class MapaUsuariosActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    public static final String TAG = "Mapa_usuarios_activity";
    public static final int ID_PERMISSION_LOCATION = 1;
    private static final float DEFAULT_ZOOM = 15f;
    private FusedLocationProviderClient mFusedLocationClient;
    private GoogleMap mMap;
    private ImageView imagGPS;
    private LatLng miPosicion;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_usuarios);
        imagGPS = (ImageView) findViewById(R.id.ic_gps2);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        requestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION, "El permiso es necesario para acceder a la localización", ID_PERMISSION_LOCATION);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            requestLocation();
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            init();
        }
    }

    private void init(){
        Log.d(TAG, "init: initializing");

        imagGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked gps icon");
                requestLocation();
                buscarUsuarios();
            }
        });

        buscarUsuarios();
    }

    private void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        hideSoftKeyboard();
    }

    private void requestPermission(Activity context, String permiso, String justificacion, int idCode){
        if(ContextCompat.checkSelfPermission(context, permiso) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permiso)){
                Toast.makeText(context, justificacion, Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(context, new String[]{permiso}, idCode);
        }
        else{
            initMap();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case ID_PERMISSION_LOCATION : {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Ya hay permiso para acceder a loa localización", Toast.LENGTH_LONG).show();
                    initMap();
                } else {
                    Toast.makeText(this, "No hay Permiso", Toast.LENGTH_LONG).show();
                }
                return;
            }

        }
    }

    private void hideSoftKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapParqueUsuariosSeguidos);
        mapFragment.getMapAsync(MapaUsuariosActivity.this);
    }

    private void requestLocation () {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)){
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    Log.i(TAG, "localizaciÃ³n obtenida!");
                    if (location != null) {
                        /*Log.i(TAG, "longitud: " + location.getLongitude());
                        Log.i(TAG, "latitud: " + location.getLatitude());
                        Toast.makeText(getBaseContext(), "longitud: " + location.getLongitude() + "latitud: " + location.getLatitude(), Toast.LENGTH_SHORT).show();*/
                        moveCamera(new LatLng(location.getLatitude(), location.getLongitude()), DEFAULT_ZOOM);
                        miPosicion = new LatLng(location.getLatitude(), location.getLongitude());

                        mMap.clear();
                            CircleOptions circleOptions = new CircleOptions()
                                    .center(miPosicion)
                                    .radius(1000)
                                    .strokeWidth(10)
                                    .strokeColor(Color.argb(0, 64, 224, 208))
                                    .fillColor(Color.argb(135, 0, 128, 128))
                                    .clickable(true);
                            mMap.addCircle(circleOptions);
                            Log.i(TAG, "llega");
                    }
                }
            });
        }
    }


    public void buscarUsuarios() {
        myRef = database.getReference(RutasBaseDeDatos.getRutaUsuariosLocalizacion());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean alguno= false;
                double distanciaKM;
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    LocalizacionUsuario localizacionUsuario = singleSnapshot.getValue(LocalizacionUsuario.class);
                    distanciaKM = UtilsJhonny.distance(miPosicion.latitude,miPosicion.longitude,localizacionUsuario.getLatitud(), localizacionUsuario.getLongitud());
                    if (distanciaKM < 1){
                        //añadir marcadores con la posicion
                        alguno = true;
                        Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(localizacionUsuario.getLatitud(), localizacionUsuario.getLongitud()))
                                .snippet("Distancia: "+ distanciaKM)
                                .title(localizacionUsuario.getUsuario().getNombre())
                                .alpha(0.5f)); //Transparencia
                    }
                }
                if (alguno == false) {
                    Toast.makeText(MapaUsuariosActivity.this, "No hay usuarios cercanos", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Aiuda", "error en la consulta", databaseError.toException());
            }
        });
    }

}
