package practica2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Practica2 {

    public static void main(String[] args) {
        
        String cadena = "";
        
        Pattern parteUno = Pattern.compile("[+|-]?((0(([0-7]*)|x(([0-9]|[A-F])*)))|([1-9](([0-9]*)|(([0-9]*)([.])([0-9]+)(((E([+|-]?)([1-9]([0-9]?[0-9]?[0-9]?))))?)))))([;]?)");
        
        Pattern c7 = Pattern.compile("(abstract)|(assert)|(boolean)|(break)|(byte)|(case)|(catch)|(char)|(class)|(continue)|(const)|(default)|(do)|(double)|(else)|(enum)|(exports)|(extends)|(final)|(finally)|(float)|(for)|(goto)|(if)|(implements)|(import)|(instanceof)|(int)|(interface)|(long)|(module)|(native)|(new)|(package)|(private)|(protected)|(public)|(requires)|(return)|(short)|(static)|(strictfp)|(super)|(switch)|(synchronized)|(this)|(throw)|(throws)|(transient)|(try)|(var)|(void)|(volatile)|(while)");
        
        Pattern c9 = Pattern.compile("\\/((\\/(.+))|((((\\*)(([^/])((([^*])|(\\*[^/]))*)))\\*\\/)))");
        
        
        Matcher matcherParteUno = parteUno.matcher(cadena);
        Matcher matcherC7 = c7.matcher(cadena);
        Matcher matcherC9 = c9.matcher(cadena);
        System.out.println("Parte Uno: " + matcherParteUno.matches());
        System.out.println("Parte Uno: " + matcherC7.matches());
        System.out.println("Parte Uno: " + matcherC9.matches());
        
    }
}