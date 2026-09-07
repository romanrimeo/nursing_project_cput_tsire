package za.ac.cput.util;

public final class Helper {
    private Helper(){}

    public static boolean isNullorEmpty(String s){
        return s == null || s.trim().isEmpty();
    }
}
