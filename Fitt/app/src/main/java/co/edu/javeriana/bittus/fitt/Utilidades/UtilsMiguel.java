package co.edu.javeriana.bittus.fitt.Utilidades;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;

public class UtilsMiguel {

    public final static int REQUEST_CODE_REGISTRAR_USUARIO = 10;
    public static final int REQUEST_CODE_TAKE_PHOTO = 11;
    public static final int REQUEST_CODE_UPLOAD_PHOTO = 12;
    public static final int REQUEST_CODE_PERMISSION = 13;



    public static Uri getImageUri(Context inContext, Bitmap inImage, String title) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, title, null);
        return Uri.parse(path);
    }

}
