package utils;

public class Validator {
    public static boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    public static boolean isValidPhone(String phone) {
        return phone.matches("\\d{10,15}");
    }
}