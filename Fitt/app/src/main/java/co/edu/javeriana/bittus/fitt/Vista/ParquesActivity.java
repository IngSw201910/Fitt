package co.edu.javeriana.bittus.fitt.Vista;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;

import co.edu.javeriana.bittus.fitt.Adapters.CustomInfoWindowAdapter;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.GetNearbyPlaces;

public class ParquesActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final String TAG = "LOCATION_APP";
    public static final int ID_PERMISSION_LOCATION = 1;
    private static final float DEFAULT_ZOOM = 15f;
    private FusedLocationProviderClient mFusedLocationClient;
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private ImageView imagGPS;
    private EditText buscador;
    private LatLng miPosicion;
    private LatLng lugarBuscado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parques);
        buscador = (EditText) findViewById(R.id.input_search);
        imagGPS = (ImageView) findViewById(R.id.ic_gps);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        requestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION, "El permiso es necesario para acceder a la localización", ID_PERMISSION_LOCATION);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            requestLocation();
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    String lines[] = marker.getSnippet().split("\n");
                    String direccion[] = lines[0].split(":");
                    Intent intent = new Intent(ParquesActivity.this, ParqueInformacionDetalladaActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putDouble("latitud", marker.getPosition().latitude);
                    bundle.putDouble("longitud", marker.getPosition().longitude);
                    bundle.putString("titulo", marker.getTitle());
                    bundle.putString("direccion", direccion[1].trim());
                    intent.putExtra("bundle", bundle);
                    startActivity(intent);
                }
            });
            init();
        }
    }

    private void init(){
        Log.d(TAG, "init: initializing");

        buscador.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER){

                    //execute our method for searching
                    geoLocate();
                }

                return false;
            }
        });

        imagGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked gps icon");
                requestLocation();
            }
        });

    }

    private void geoLocate (){
        Geocoder mGeocoder = new Geocoder(getBaseContext());
        String addressString = buscador.getText().toString();
        if (!addressString.isEmpty()) {
            try {
                List<Address> addresses = mGeocoder.getFromLocationName(addressString, 2);
                if (addresses != null && !addresses.isEmpty()) {
                    Address addressResult = addresses.get(0);
                    LatLng position = new LatLng(addressResult.getLatitude(), addressResult.getLongitude());
                    Toast.makeText(ParquesActivity.this, addressResult.getFeatureName(), Toast.LENGTH_SHORT).show();
                    if (mMap != null) {
                        moveCamera(position, DEFAULT_ZOOM);
                        lugarBuscado = position;
                        mMap.clear();
                        findParks(lugarBuscado);
                    }
                } else {Toast.makeText(ParquesActivity.this, "Dirección no encontrada", Toast.LENGTH_SHORT).show();}
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {Toast.makeText(ParquesActivity.this, "La dirección esta vacía", Toast.LENGTH_SHORT).show();}
    }
    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapParques);
        mapFragment.getMapAsync(ParquesActivity.this);
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
                        findParks(miPosicion);
                        Log.i(TAG, "llega");
                    }
                }
            });
        }
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

    public void findParks(LatLng posicion){

        StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        stringBuilder.append("location="+posicion.latitude+","+posicion.longitude);
        stringBuilder.append("&radius="+1000);
        stringBuilder.append("&keyword="+"park");
        stringBuilder.append("&key="+getResources().getString(R.string.google_places_key));

        String url = stringBuilder.toString();
        Log.d(TAG, "url"+url );
        Toast.makeText(this, url, Toast.LENGTH_LONG).show();

        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(ParquesActivity.this));

        Object dataTransfer[] = new Object[3];
        dataTransfer[0] = mMap;
        dataTransfer[1] = url;
        dataTransfer[2] = miPosicion;

        GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces(this);
        getNearbyPlaces.execute(dataTransfer);


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
}