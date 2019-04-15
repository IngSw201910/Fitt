package co.edu.javeriana.bittus.fitt;

public class Utils {

    public static boolean isEmailValid(String email) {
        boolean isValid = true;
        if (!email.contains("@") || !email.contains(".") || email.length() < 5)
            isValid = false;
        return isValid;
    }

}
