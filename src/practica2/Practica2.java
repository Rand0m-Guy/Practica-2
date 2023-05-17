package practica2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Practica2 {

    public static void main(String[] args) {
        
        String cadena = "";
    
        Pattern pattern = Pattern.compile("[+|-]?((0(([0-7]*)|x(([0-9]|[A-F])*)))|([1-9](([0-9]*)|(([0-9]*)([.])([0-9]+)(((E([+|-]?)([1-9]([0-9]?[0-9]?[0-9]?))))?)))))([;]?)");
        
        Matcher matcher = pattern.matcher(cadena);
        System.out.println(matcher.matches());
        
    }
}