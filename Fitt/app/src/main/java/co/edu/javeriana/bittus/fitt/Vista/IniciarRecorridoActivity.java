package co.edu.javeriana.bittus.fitt.Vista;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.Permisos;
import co.edu.javeriana.bittus.fitt.Utilidades.UtilsJhonny;

public class IniciarRecorridoActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private FusedLocationProviderClient mFusedLocationClient;


    private ImageButton iniciarRecorrido;
    private ImageButton finalizarRecorrido;

    private TextView distanciaRecorrido;
    private double distanciaRecorridoN = 0;
    private TextView pasosRecorridos;
    private int pasosRecorridosN;
    private TextView velocidadPromedioRecorrido;
    private double velocidadPromedioRecorridoN;
    private TextView caloriasRecorrido;
    private double caloriasRecorridoN;
    private TextView tiempoRecorrido;

    private Chronometer chrono;




    private double latitudUltimaUbicacion;
    private double longitudUltimaUbicacion;

    private boolean inicioColocado = false;

    private static final int ID_PERMISSION_LOCATION = Permisos.getIdPermissionLocation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_recorrido);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mLocationRequest = createLocationRequest();

        distanciaRecorrido = findViewById(R.id.distanciaRecorrido);
        pasosRecorridos = findViewById(R.id.pasosRecorridos);
        velocidadPromedioRecorrido = findViewById(R.id.velocidadPromedioRecorrido);
        caloriasRecorrido = findViewById(R.id.caloriasRecorrido);
        tiempoRecorrido = findViewById(R.id.tiempoRecorrido);




        //Lo que se hace cada 20 segs que se recibe una nueva ubicación
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();

                if (location != null) {
                    if (!inicioColocado){
                        colocarMarcadorInicio (location);
                        inicioColocado = true;
                    }
                    actualizarUbicacion (location);
                    Toast.makeText(getApplicationContext(),String.valueOf(location.getLatitude()) , Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),String.valueOf(location.getLatitude()) , Toast.LENGTH_LONG).show();
                }
            }
        };
        inicializarCronometro();
        mapFragment.getMapAsync(this);

    }

    private void inicializarCronometro(){
        chrono  = (Chronometer) findViewById(R.id.tiempoRecorrido);
        chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                int h   = (int)(time /3600000);
                int m = (int)(time - h*3600000)/60000;
                int s= (int)(time - h*3600000- m*60000)/1000 ;
                String t = (h < 10 ? "0"+h: h)+":"+(m < 10 ? "0"+m: m)+":"+ (s < 10 ? "0"+s: s);
                chronometer.setText(t);
            }
        });
        chrono.setBase(SystemClock.elapsedRealtime());
        chrono.setText("00:00:00");
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera


        //Los botones adquieren funcionalidad únicamente cuando el mapa está listo:

        iniciarRecorrido = (ImageButton) findViewById(R.id.btnIniciarRecorrido);
        finalizarRecorrido = (ImageButton) findViewById(R.id.btnFinalizarRecorrido);

        iniciarRecorrido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Permisos.requestPermission((Activity) v.getContext(), Manifest.permission.ACCESS_FINE_LOCATION, "El permiso es necesario para acceder a la localización.", ID_PERMISSION_LOCATION);
                startLocationUpdates();
                chrono.start();
            }
        });


    }


    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                    mLocationCallback, null);
        }
    }

    protected LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(20000); //cada 20 seg se obtiene la ubicación del usuario
        mLocationRequest.setFastestInterval(20000); //máxima tasa de refresco
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }

    private void colocarMarcadorInicio (Location location){
        LatLng inicio = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(inicio).title("Inicio"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(inicio));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
        latitudUltimaUbicacion = inicio.latitude;
        longitudUltimaUbicacion = inicio.longitude;
    }

    private void actualizarUbicacion (Location location){
        dibujarRuta (location);
        calcularCampos (location);
        latitudUltimaUbicacion = location.getLatitude();
        longitudUltimaUbicacion = location.getLongitude();
    }

    private void dibujarRuta (Location location){
        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
        options.add(new LatLng(latitudUltimaUbicacion, longitudUltimaUbicacion));
        options.add(new LatLng(location.getLatitude(), location.getLongitude()));
        mMap.addPolyline(options);
    }

    private void calcularCampos (Location location){
        //DISTANCIA
        distanciaRecorridoN += UtilsJhonny.distance (location.getLatitude(), location.getLongitude(), latitudUltimaUbicacion, longitudUltimaUbicacion);
        distanciaRecorrido.setText("Distancia: "+distanciaRecorridoN+" km");
    }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == ID_PERMISSION_LOCATION ){
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Ya hay permiso para acceder a la localización", Toast.LENGTH_LONG).show();
                    startLocationUpdates();
                } else {
                    Toast.makeText(this, "No hay Permiso", Toast.LENGTH_LONG).show();
                }
                return;
        }
    }

}
