package co.edu.javeriana.bittus.fitt.Vista;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import co.edu.javeriana.bittus.fitt.Modelo.Usuario;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.Permisos;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;
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
    private int caloriasRecorridoN;
    private TextView tiempoRecorrido;

    private Chronometer chrono;
    private long timeWhenStopped = 0;

    private TextView tituloRecorrido;

    private Usuario usuario;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser mAuth;

    private double latitudUltimaUbicacion;
    private double longitudUltimaUbicacion;
    private  SupportMapFragment mapFragment;
    private Location ultimaUbiacion;

    private boolean inicioColocado = false;

    private static final int ID_PERMISSION_LOCATION = Permisos.getIdPermissionLocation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_recorrido);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mLocationRequest = createLocationRequest();

        distanciaRecorrido = findViewById(R.id.distanciaRecorrido);
        pasosRecorridos = findViewById(R.id.pasosRecorridos);
        velocidadPromedioRecorrido = findViewById(R.id.velocidadPromedioRecorrido);
        caloriasRecorrido = findViewById(R.id.caloriasRecorrido);
        tiempoRecorrido = findViewById(R.id.tiempoRecorrido);

        tituloRecorrido = findViewById(R.id.tituloRecorridoL);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance().getCurrentUser();

        myRef = database.getReference(RutasBaseDeDatos.RUTA_USUARIOS).child(mAuth.getUid());

        //Lo que se hace cada 20 segs que se recibe una nueva ubicación
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();

                if (location != null) {
                    if (!inicioColocado){
                        colocarMarcador (location, "Inicio recorrido", BitmapDescriptorFactory.HUE_GREEN);
                        inicioColocado = true;
                    }
                    actualizarUbicacion (location);
                }
            }
        };

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuario = (Usuario)dataSnapshot.getValue(Usuario.class);

                inicializarCronometro();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mapFragment.getMapAsync(this);

    }

    private void inicializarCronometro(){
        chrono  = (Chronometer) findViewById(R.id.tiempoRecorrido);
        chrono.stop();
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
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Add a marker in Sydney and move the camera

        mMap.setMapStyle(MapStyleOptions
                .loadRawResourceStyle(this, R.raw.mapa_recorrido_style));


        //Los botones adquieren funcionalidad únicamente cuando el mapa está listo y hay permisos

        iniciarRecorrido = (ImageButton) findViewById(R.id.btnIniciarRecorrido);
        iniciarRecorrido.setClickable(false);
        iniciarRecorrido.setColorFilter(getResources().getColor(R.color.gris));

        finalizarRecorrido = (ImageButton) findViewById(R.id.btnFinalizarRecorrido);

        Permisos.requestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION, "El permiso es necesario para iniciar el recorrido.", ID_PERMISSION_LOCATION);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            iniciarRecorrido.setColorFilter(getResources().getColor(R.color.verdeFitt));
            iniciarRecorrido.setClickable(true);
        }

        iniciarRecorrido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startLocationUpdates();

                chrono.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                chrono.start();

                iniciarRecorrido.setColorFilter(getResources().getColor(R.color.gris));
                iniciarRecorrido.setClickable(false);
                finalizarRecorrido.setColorFilter(getResources().getColor(R.color.verdeFitt));
                finalizarRecorrido.setClickable(true);

            }
        });

        finalizarRecorrido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalizarRecorrido();
            }
        });
        finalizarRecorrido.setClickable(false);


    }

    private void finalizarRecorrido (){
        chrono.stop();
        stopLocationUpdates();
        tituloRecorrido.setText("RESUMEN RECORRIDO");
        colocarMarcador(ultimaUbiacion, "Final recorrido", BitmapDescriptorFactory.HUE_RED);


        finalizarRecorrido.setColorFilter(getResources().getColor(R.color.gris));
        finalizarRecorrido.setClickable(false);

        usuario.setCaloriasQuemadas(usuario.getCaloriasQuemadas()+(float)caloriasRecorridoN);
        usuario.setPasosDados(usuario.getPasosDados() + (float)pasosRecorridosN);
        usuario.setDistanciaRecorrida(usuario.getDistanciaRecorrida() + (float) distanciaRecorridoN);

        myRef.setValue(usuario, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Toast.makeText(getApplicationContext(), "Información del recorrido almacenada!" , Toast.LENGTH_SHORT).show();
                finish();
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
        mLocationRequest.setInterval(10000); //cada 20 seg se obtiene la ubicación del usuario
        mLocationRequest.setFastestInterval(10000); //máxima tasa de refresco
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }

    private void colocarMarcador (Location location, String titulo, float color){
        LatLng inicio = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(inicio).title(titulo)
                .icon(BitmapDescriptorFactory.defaultMarker(color)));


        mMap.moveCamera(CameraUpdateFactory.newLatLng(inicio));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(18));
        latitudUltimaUbicacion = inicio.latitude;
        longitudUltimaUbicacion = inicio.longitude;
        ultimaUbiacion = location;
    }

    private void actualizarUbicacion (Location location){
        dibujarRuta (location);
        calcularCampos (location);
        latitudUltimaUbicacion = location.getLatitude();
        longitudUltimaUbicacion = location.getLongitude();
        ultimaUbiacion = location;
    }

    private void dibujarRuta (Location location){
        PolylineOptions options = new PolylineOptions().width(6).color(R.color.verdeBotones).geodesic(true);
        options.add(new LatLng(latitudUltimaUbicacion, longitudUltimaUbicacion));
        options.add(new LatLng(location.getLatitude(), location.getLongitude()));
        mMap.addPolyline(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(18));
    }

    private void calcularCampos (Location location){
        //DISTANCIA
        distanciaRecorridoN += UtilsJhonny.distance (location.getLatitude(), location.getLongitude(), latitudUltimaUbicacion, longitudUltimaUbicacion);
        distanciaRecorrido.setText("Distancia:\n"+String.format("%.3f", distanciaRecorridoN)+" km");

        //VELOCIDAD PROMEDIO
        int elapsedSeg = (int) (SystemClock.elapsedRealtime() - chrono.getBase())/1000;
        if (elapsedSeg!=0) {
            //Toast.makeText(getApplicationContext(), "tiempo: " + elapsedSeg, Toast.LENGTH_SHORT).show();
            velocidadPromedioRecorridoN = (distanciaRecorridoN / elapsedSeg) * 3600;
            velocidadPromedioRecorrido.setText("Velocidad promedio:\n" + String.format("%.1f", velocidadPromedioRecorridoN) + " km/h");
        }

        //PASOS RECORRIDOS

        //MOCK datos usuario para calcular los pasos
        double distanciaPaso;
        double altura = 1.71;
        String sexo = "m";
        if (sexo.equals ("m"))
            distanciaPaso = 0.415 * altura;
        else //si es mujer
            distanciaPaso = 0.413 * altura;

        //FIN MOCK

        pasosRecorridosN = (int) (distanciaRecorridoN / (distanciaPaso*0.001));
        pasosRecorridos.setText("Pasos:\n"+pasosRecorridosN);



        caloriasRecorridoN = 0;
        //CALORIAS
        if (velocidadPromedioRecorridoN>0 && velocidadPromedioRecorridoN<=8){
            caloriasRecorridoN = (int) ((480*0.00028) * elapsedSeg);
        }

        if (velocidadPromedioRecorridoN>8 && velocidadPromedioRecorridoN<=9.6)
            caloriasRecorridoN = (int) ((600*0.00028) * elapsedSeg);


        if (velocidadPromedioRecorridoN>9.6 && velocidadPromedioRecorridoN<=11.2)
            caloriasRecorridoN = (int) ((680*0.00028) * elapsedSeg);


        if (velocidadPromedioRecorridoN>11.2 && velocidadPromedioRecorridoN<=12.8)
            caloriasRecorridoN = (int) ((830*0.00028) * elapsedSeg);


        if (velocidadPromedioRecorridoN>12.8)
            caloriasRecorridoN = (int) ((890*0.00028) * elapsedSeg);


        caloriasRecorrido.setText("Calorias:\n" + caloriasRecorridoN + " kcal");


    }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == ID_PERMISSION_LOCATION ){
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Ya hay permiso para acceder a la localización.", Toast.LENGTH_LONG).show();
                    iniciarRecorrido.setColorFilter(getResources().getColor(R.color.verdeFitt));
                    iniciarRecorrido.setClickable(true);
                } else {
                    Toast.makeText(this, "No hay permiso, no se puede iniciar el recorrido.", Toast.LENGTH_LONG).show();
                    iniciarRecorrido.setColorFilter(getResources().getColor(R.color.gris));
                    iniciarRecorrido.setClickable(false);
                }
                return;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void stopLocationUpdates(){
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

}
