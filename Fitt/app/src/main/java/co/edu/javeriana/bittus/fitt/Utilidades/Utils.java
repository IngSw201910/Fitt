package co.edu.javeriana.bittus.fitt.Utilidades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class Utils {


    private static StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

    public final static int REQUEST_CODE_CREAR_RUTINA_SESIONES = 0;
    public final static int REQUEST_CODE_EJERCICIO_DISTANCIA = 1;
    public final static int REQUEST_CODE_EJERCICIO_DURACION = 2;
    public final static int REQUEST_CODE_EJERCICIO_REPETICION = 3;
    public final static int REQUEST_CODE_BUSCAR_EJERCICIO = 4;
    public final static int REQUEST_CODE_CREAR_SESION = 5;


    public static boolean isEmailValid(String email) {
        boolean isValid = true;
        if (!email.contains("@") || !email.contains(".") || email.length() < 5)
            isValid = false;
        return isValid;
    }


    public static void cerrarSesion(){
        FirebaseAuth mAuth =  FirebaseAuth.getInstance();
        mAuth.signOut();
    }

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
}



