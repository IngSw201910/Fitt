package co.edu.javeriana.bittus.fitt.Utilidades;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import co.edu.javeriana.bittus.fitt.Modelo.Ejercicio;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class Utils {


    private static StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference myRef;
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseUser user = mAuth.getCurrentUser();



    public final static int REQUEST_CODE_CREAR_ENTRENAMIENTO_EJERCICIOS = 0;
    public final static int REQUEST_CODE_EJERCICIO_DISTANCIA = 1;
    public final static int REQUEST_CODE_EJERCICIO_TIEMPO = 2;
    public final static int REQUEST_CODE_EJERCICIO_REPETICION = 3;
    public final static int REQUEST_CODE_BUSCAR_EJERCICIO = 4;
    public final static int REQUEST_CODE_EJERCICIO_DESCANSO_EDITAR = 5;
    public final static int REQUEST_CODE_EJERCICIO_DESCANSO=6;
    public final static int REQUEST_CODE_EJERCICIO_REPETICION_EDITAR = 7;
    public final static int REQUEST_CODE_EJERCICIO_TIEMPO_EDITAR = 8;
    public final static int REQUEST_CODE_EJERCICIO_DISTANCIA_EDITAR = 9;
    public final static Ejercicio EJERCICIO_DESCANSO = new Ejercicio("Descanso","Ninguno","Descanso","Baja",0,"Descanso entre ejercicios");




    public static  void tomarFotoDesdeCamara(Activity context, int requestCode){

        //Codigo de request recomendado UtilsMiguel.REQUEST_CODE_TAKE_PHOTO

        Permisos.requestPermission(context, Manifest.permission.CAMERA,"Es necesario para tomar fotos", UtilsMiguel.REQUEST_CODE_PERMISSION);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivityForResult(intent, requestCode);

        }

    }
    public  static void cargarFotoDesdeCamara(Activity context, int requestCode){
        //Codigo de request recomendado UtilsMiguel.REQUEST_CODE_UPLOAD_PHOTO
        Permisos.requestPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE,"Es necesario para carga una foto", UtilsMiguel.REQUEST_CODE_PERMISSION);

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.createChooser(intent, StringsMiguel.SELECCIONAR_APLICACION);
        context.startActivityForResult(intent, requestCode);


    }

    /*
    Metodo sobre escrito en su activity

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if(requestCode== UtilsMiguel.REQUEST_CODE_TAKE_PHOTO && resultCode==RESULT_OK){
            Bundle extras = data.getExtras();
            bitmapFoto = (Bitmap) extras.get("data");
            imageViewFotoPerfil.setImageBitmap(bitmapFoto);
        }else if(requestCode == UtilsMiguel.REQUEST_CODE_UPLOAD_PHOTO  && resultCode==RESULT_OK){
            Uri path = data.getData();
            try {
                bitmapFoto = MediaStore.Images.Media.getBitmap(this.getContentResolver(),path);
                imageViewFotoPerfil.setImageBitmap(bitmapFoto);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


     */

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

}



