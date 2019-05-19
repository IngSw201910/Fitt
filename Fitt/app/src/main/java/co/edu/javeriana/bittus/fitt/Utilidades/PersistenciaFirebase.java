package co.edu.javeriana.bittus.fitt.Utilidades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

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


    //Guarda la información en la ruta+/+key (la key se genera automaticamente)
    public static String almacenarInformacionConKey (String ruta, Object objeto){
        myRef=database.getReference(ruta);
        String key = myRef.push().getKey();
        myRef=database.getReference(ruta+key);
        myRef.setValue(objeto);
        return key;
    }

    //Guarda la información en la ruta
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

    


    public static void subirArchivoFirebase(String carpeta, String nombre, Uri uri){
        StorageReference filepPath = mStorageRef.child(carpeta).child(nombre);


        filepPath.putFile(uri);
    }

    public static void descargarFotoYPonerEnImageView(String ruta, final ImageView imageView){
        StorageReference foto = mStorageRef.child(ruta);
        File localFile = null;
        try {
            localFile = File.createTempFile("images", "jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        final File finalLocalFile = localFile;
        foto.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Successfully downloaded data to local file
                        // ...
                        String filePath = finalLocalFile.getPath();
                        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                        imageView.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
            }
        });
    }

}
