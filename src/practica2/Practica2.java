package practica2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Practica2 {
    
    public static boolean puntoUno(String linea) {
        int idx = 0;
        int longitud = linea.length();
        if(longitud == 0) return false;
        
        if(linea.charAt(0) == '+' || linea.charAt(0) == '-') idx++; // (+|-)?
        if(idx >= longitud) return false;
        
        if(linea.charAt(idx) == '0') {
            idx++;
            if(idx >= longitud) return true; // (+|-)?0
            if(linea.charAt(idx) == ';') {
                if(idx + 1 == longitud) {
                    return true;
                }
                
                return false; // Checamos ; final
            }
            
            // Hexagesimal
            if(linea.charAt(idx) == 'x') {
                idx++;
                if(idx >= longitud || linea.charAt(idx) == ';') return false; //(+|-)?0x(;?)   es inválido
                
                // Checamos 0|1|2|3|4|5|6|7|8|9|A|B|C|D|E|F
                while(idx < longitud) {
                    if(!(linea.charAt(idx) - '0' >= 0 && linea.charAt(idx) - '0' <= 9)) {
                        if(!(linea.charAt(idx) - 'A' >= 0 && linea.charAt(idx) - 'A' <= 5)) {
                            if(linea.charAt(idx) == ';' && idx + 1 == longitud) { // Checamos ; final
                                // No hacemos nada
                            } else {
                                return false;
                            }
                        }
                    }
                    idx++;
                }
                
                return true;
            } else {
                
                // Octal
                
                while(idx < longitud) {
                    if(!(linea.charAt(idx) - '0' >= 0 && linea.charAt(idx) - '0' <= 7)) {
                        if(linea.charAt(idx) == ';' && idx + 1 == longitud) { // Checamos ; final
                            // No hacemos nada
                        } else {
                            return false;
                        }
                    }
                    
                    idx++;
                }
                
                return true;
            }
            
        } else if(linea.charAt(idx) - '0' >= 1 && linea.charAt(idx) - '0' <= 9) {
            while(idx < longitud && linea.charAt(idx) != '.') {
                if(!(linea.charAt(idx) - '0' >= 0 && linea.charAt(idx) - '0' <= 9)) {
                    if(linea.charAt(idx) == ';' && idx + 1 >= longitud) {}
                    else {
                        return false;
                    }
                }
                idx++;
            }
            if(idx >= longitud) return true;
            
            idx++; // Leímos un punto, y ahora va todo lo de decimales
            if(idx >= longitud) return false;
            
            if(!(linea.charAt(idx) - '0' >= 0 && linea.charAt(idx) - '0' <= 9)) return false;
            idx++;
            
            while(idx < longitud && linea.charAt(idx) != 'E') {
                if(!(linea.charAt(idx) - '0' >= 0 && linea.charAt(idx) - '0' <= 9)) {
                    if(linea.charAt(idx) == ';' && idx + 1 == longitud) {}
                    else {return false;}
                }
                idx++;
            }
            if(idx >= longitud) return true; // Flotante normal
            
            idx++; // Leí exponente
            if(idx >= longitud) return false;
            
            if(linea.charAt(0) == '+' || linea.charAt(0) == '-') idx++; // (+|-)?
            if(idx >= longitud) return false;
            
            if(!(linea.charAt(idx) - '0' >= 1 && linea.charAt(idx) - '0' <= 9)) return false; // Decimal 1
            idx++;
            if(idx >= longitud) return false;
            
            if(!(linea.charAt(idx) - '0' >= 0 && linea.charAt(idx) - '0' <= 9)) return false; // Decimal 2
            idx++;
            
            // Resto de decimales
            for(int i = 0; i < 3; i++, idx++) {
                if(idx >= longitud) return true;
                
                if(!(linea.charAt(idx) - '0' >= 0 && linea.charAt(idx) - '0' <= 9)) {
                    if(linea.charAt(idx) == ';' && idx + 1 == longitud) {}
                    else {return false;}
                }
            }
            
            if(idx < longitud) {
                if(linea.charAt(idx) == ';' && idx + 1 == longitud) {}
                else {return false;}
            }
            
            return true;
        }
        
        return false;
    }
    
    
    public static void main(String[] args) {
        
        String cadena = "+123456789.012345678909876543E-71030;";
        
        Pattern parteUno = Pattern.compile("[+|-]?((0(([0-7]*)|x(([0-9]|[A-F])*)))|([1-9](([0-9]*)|(([0-9]*)([.])([0-9]+)(((E([+|-]?)([1-9]([0-9]?[0-9]?[0-9]?))))?)))))([;]?)");
        
        Pattern c7 = Pattern.compile("(abstract)|(assert)|(boolean)|(break)|(byte)|(case)|(catch)|(char)|(class)|(continue)|(const)|(default)|(do)|(double)|(else)|(enum)|(exports)|(extends)|(final)|(finally)|(float)|(for)|(goto)|(if)|(implements)|(import)|(instanceof)|(int)|(interface)|(long)|(module)|(native)|(new)|(package)|(private)|(protected)|(public)|(requires)|(return)|(short)|(static)|(strictfp)|(super)|(switch)|(synchronized)|(this)|(throw)|(throws)|(transient)|(try)|(var)|(void)|(volatile)|(while)");
        
        Pattern c9 = Pattern.compile("\\/((\\/(.+))|((((\\*)(([^/])((([^*])|(\\*[^/]))*)))\\*\\/)))");
        
        
        Matcher matcherParteUno = parteUno.matcher(cadena);
        Matcher matcherC7 = c7.matcher(cadena);
        Matcher matcherC9 = c9.matcher(cadena);
        System.out.println("PARA " + cadena + "\n");
        System.out.println("ERRADO (checar AFD) Parte Uno: " + matcherParteUno.matches());
        System.out.println("Parte Uno MANUAL: " + puntoUno(cadena));
        System.out.println("Parte Siete: " + matcherC7.matches());
        System.out.println("Parte Nueve: " + matcherC9.matches());
        
    }
}