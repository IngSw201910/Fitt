package co.edu.javeriana.bittus.fitt.Utilidades;

import com.google.firebase.auth.FirebaseAuth;

public class Utils {





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


}
