package co.edu.javeriana.bittus.fitt.Vista;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import co.edu.javeriana.bittus.fitt.Adapters.EntrenamientosAdapter;
import co.edu.javeriana.bittus.fitt.Adapters.EntrenamientosHoyAdapter;
import co.edu.javeriana.bittus.fitt.Modelo.Ejercicio;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDescanso;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDistancia;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioEntrenamiento;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioRepeticiones;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioTiempo;
import co.edu.javeriana.bittus.fitt.Modelo.Entrenamiento;
import co.edu.javeriana.bittus.fitt.Modelo.EntrenamientoAdoptado;
import co.edu.javeriana.bittus.fitt.Modelo.LocalizacionUsuario;
import co.edu.javeriana.bittus.fitt.Modelo.Reseña;
import co.edu.javeriana.bittus.fitt.Modelo.Usuario;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;
import co.edu.javeriana.bittus.fitt.Utilidades.StringsMiguel;
import co.edu.javeriana.bittus.fitt.Utilidades.UtilsJhonny;

public class RealizarEntrenamiento_EntrenamientosHoyActivity extends AppCompatActivity {

    //Esta actividad muestra los entrenamientos que tiene programado el usuario para ese día

    private TextView diaSemana;
    private TextView fecha;
    private CheckBox compartirUbicacion;
    private FusedLocationProviderClient mFusedLocationClient;
    private LatLng miPosicion;
    private Usuario usuario;

    private ListView entrenamientosL;
    private List<EntrenamientoAdoptado> listaEntrenamientosAdoptadosHoy = new ArrayList<>();;

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseUser user = mAuth.getCurrentUser();



    private EntrenamientosHoyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_entrenamiento_entrenamientoshoy);


        diaSemana = findViewById(R.id.diaSemanaEntrenamientosTV);
        fecha = findViewById(R.id.fechaMisEntrenamientosTV);
        entrenamientosL = findViewById(R.id.listViewEntrenamientosHoy);
        compartirUbicacion = (CheckBox) findViewById(R.id.checkBox);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        adapter = new EntrenamientosHoyAdapter(this ,R.layout.item_entrenamiento_adoptado_row, listaEntrenamientosAdoptadosHoy);
        entrenamientosL.setAdapter(adapter);

        buscarUsuario();

        entrenamientosL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EntrenamientoAdoptado entrenamientoAdoptado = listaEntrenamientosAdoptadosHoy.get(position);
                Intent intent = new Intent (RealizarEntrenamiento_EntrenamientosHoyActivity.this, RealizarEntrenamientoActivity.class);
                intent.putExtra(StringsMiguel.LLAVE_ENTRENAMIENTO,entrenamientoAdoptado.getEntrenamiento());
                if(compartirUbicacion.isChecked()){
                    subirUbicacion();
                }
                startActivity(intent);
            }
        });

        inicializarVista ();
    }

    private void inicializarVista(){
        Calendar c = Calendar.getInstance();
        diaSemana.setText(UtilsJhonny.diaSemana(c.get(Calendar.DAY_OF_WEEK)));

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String fechaFormateada = df.format(c.getTime());

        fecha.setText(fechaFormateada);

        descargarInformacionSinSuscripcionACambios(RutasBaseDeDatos.getRutaEntrenamientosAdoptados()+user.getUid()+"/"+diaSemana.getText().toString()+"/");

    }

    private void descargarInformacionSinSuscripcionACambios (String ruta) {

        DatabaseReference myRef;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();


        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        myRef = database.getReference(ruta);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {

                    EntrenamientoAdoptado entrenamiento = snapshot.getValue(EntrenamientoAdoptado.class);
                    List<EjercicioEntrenamiento> ejercicioEntrenamientos = new ArrayList<>();
                    for(DataSnapshot singleSnapshotE: snapshot.getChildren()){

                        DataSnapshot singleSnapshotF = singleSnapshotE.child("ejercicioEntrenamientoList");

                            for(DataSnapshot singleSnapshotG: singleSnapshotF.getChildren()) {

                                EjercicioEntrenamiento ejercicioEntrenamiento = singleSnapshotG.getValue(EjercicioEntrenamiento.class);
                                if(ejercicioEntrenamiento.getEjercicio().getTipo().equals(StringsMiguel.EJERCICIO_TIPO_REPETICIÓN)){
                                    EjercicioRepeticiones ejercicioRepeticiones = singleSnapshotG.getValue(EjercicioRepeticiones.class);
                                    ejercicioEntrenamientos.add(ejercicioRepeticiones);
                                }
                                if(ejercicioEntrenamiento.getEjercicio().getTipo().equals(StringsMiguel.EJERCICIO_TIPO_DESCANSO)){
                                    EjercicioDescanso ejercicioDescanso = singleSnapshotG.getValue(EjercicioDescanso.class);
                                    ejercicioEntrenamientos.add(ejercicioDescanso);
                                }
                                if(ejercicioEntrenamiento.getEjercicio().getTipo().equals(StringsMiguel.EJERCICIO_TIPO_TIEMPO)){
                                    EjercicioTiempo ejercicioTiempo = singleSnapshotG.getValue(EjercicioTiempo.class);
                                    ejercicioEntrenamientos.add(ejercicioTiempo);
                                }
                                if(ejercicioEntrenamiento.getEjercicio().getTipo().equals(StringsMiguel.EJERCICIO_TIPO_DISTANCIA)){
                                    EjercicioDistancia ejercicioDistancia = singleSnapshotG.getValue(EjercicioDistancia.class);
                                    ejercicioEntrenamientos.add(ejercicioDistancia);
                                }

                            }


                    }
                    entrenamiento.getEntrenamiento().setEjercicioEntrenamientoList(ejercicioEntrenamientos);
                    Log.i("PRUEBARUTINA:", entrenamiento.getEntrenamiento().getDescripcion());


                    listaEntrenamientosAdoptadosHoy.add(entrenamiento);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Error:", "Error en la consulta", databaseError.toException());
            }
        });
    }

    private void subirUbicacion(){
        String uid;
        requestLocation();
        uid = mAuth.getUid();
        LocalizacionUsuario localizacionUsuario = new LocalizacionUsuario(uid,miPosicion.latitude,miPosicion.longitude,usuario);
        subirLocalizacionUsuario(localizacionUsuario);
    }

    private void requestLocation () {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)){
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    Log.i("prueba:", "tiene permiso");
                    if (location != null) {
                        Log.i("ENTRAAA:", location.getLatitude()+ location.getLongitude()+"");
                        miPosicion = new LatLng(location.getLatitude(), location.getLongitude());
                        Log.i("Location:", "Latitud:"+ location.getLatitude());
                        Log.i("Location:", "Longitud:"+ location.getLongitude());
                    }
                }
            });
        }
    }

    private void buscarUsuario(){
        DatabaseReference myRef;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference(RutasBaseDeDatos.RUTA_USUARIOS).child(mAuth.getUid());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuario = dataSnapshot.getValue(Usuario.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void subirLocalizacionUsuario (LocalizacionUsuario localizacionUsuario){
        DatabaseReference myRef;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef=database.getReference(RutasBaseDeDatos.getRutaUsuariosLocalizacion());
        String key =  localizacionUsuario.getUID();
        myRef=database.getReference(RutasBaseDeDatos.getRutaUsuariosLocalizacion()+key);
        myRef.setValue(localizacionUsuario);
    }



}
