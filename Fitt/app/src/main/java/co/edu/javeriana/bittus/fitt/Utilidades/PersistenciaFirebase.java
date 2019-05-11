package co.edu.javeriana.bittus.fitt.Utilidades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import co.edu.javeriana.bittus.fitt.Modelo.Entrenamiento;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class PersistenciaFirebase {

    private static StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference myRef;
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseUser user = mAuth.getCurrentUser();


    public static void descargarYMostrarGIF (String ruta, final GifImageView gifImageView) {
        Log.i("Ruta:", ruta);
        final StorageReference gif = mStorageRef.child(ruta);
        File localFile = null;
        try {
            localFile = File.createTempFile("images", "gif");
        } catch (IOException e) {
            e.printStackTrace();
        }

        final File finalLocalFile = localFile;
        gif.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Successfully downloaded data to local file
                        // ...
                        String filePath = finalLocalFile.getPath();
                        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                        try {
                            GifDrawable gif = new GifDrawable(finalLocalFile);
                            gifImageView.setImageDrawable(gif);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
            }
        });
    }

    public static void almacenarInformacionConKey (String ruta, Object objeto){
        myRef=database.getReference(ruta);
        String key = myRef.push().getKey();
        myRef=database.getReference(ruta+key);
        myRef.setValue(objeto);
    }

    public static void almacenarInformacionConRuta(String ruta, Object objeto){
        myRef=database.getReference(ruta);
        myRef.setValue(objeto);
    }


    /*
    private void descargarInformacionSinSuscripcionACambios (String ruta) {

        DatabaseReference myRef;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();


        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        myRef = database.getReference(ruta);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //DataSnapshot.getValue();
                //AQUÍ HACEN LO QUE QUIEREN HACER CON LA INFORMACIÓN DESCARGADA - ES ASINCRONO
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Error:", "Error en la consulta", databaseError.toException());
            }
        });
    }*/

    





}
