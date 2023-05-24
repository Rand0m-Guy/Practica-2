package practica2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
public class Practica2 {
    
    public static boolean symbols(char c) {
        if(Character.isWhitespace(c)) return true;
        if(c == ';' || c == ',') return true;
        if(c == '(' || c == ')' || c == '[' || c == ']' || c == '{' || c == '}') return true;
        
        return false;
    }
    
    public static boolean q0(String linea, int idx) {
        if(idx >= linea.length()) return true;
        
        if(symbols(linea.charAt(idx))) return q0(linea, idx + 1);
        
        // Constantes numéricas
        if(linea.charAt(idx) == '+' | linea.charAt(idx) == '-') return qN1(linea, idx + 1);
        if(linea.charAt(idx) == '0') return qN2(linea, idx + 1);
        if(linea.charAt(idx) - '0' >= 1 && linea.charAt(idx) - '0' <= 9) return qN4(linea, idx + 1);
        
        // Palabras reservadas e identificadores válidos
        if(linea.charAt(idx) - 'a' >= 0 && linea.charAt(idx) - 'a' <= 25) {
            int lIdx = idx;
            while(lIdx < linea.length() && !(Character.isWhitespace(linea.charAt(lIdx)) || linea.charAt(lIdx) == ';')) {
                lIdx++;
            }
            String temp = linea.substring(idx, lIdx);
            if(qReserved(temp)) return q0(linea, lIdx);
            else {
                return qId(linea, idx + 1);
            }
        }
        
        if((linea.charAt(idx) - 'A' >= 0 && linea.charAt(idx) - 'Z' <= 25) || linea.charAt(idx) == '_' || linea.charAt(idx) == '$') {
            return qId(linea, idx + 1);
        }
        
        
        
        return false;
    }
    
    // CONSTANTES NUMÉRICAS
    public static boolean qN1(String linea, int idx) {
        if(idx >= linea.length()) return false;
        
        if(linea.charAt(idx) == '0') return qN2(linea, idx + 1);
        if(linea.charAt(idx) - '0' >= 1 && linea.charAt(idx) - '0' <= 9) return qN4(linea, idx + 1);
        
        return false;
    }
    
    public static boolean qN2(String linea, int idx) {
        if(idx >= linea.length()) return true;
        
        if(linea.charAt(idx) - '0' >= 0 && linea.charAt(idx) - '0' <= 7) return qN15(linea, idx + 1);
        if(linea.charAt(idx) == 'x') return qN3(linea, idx + 1);
        if(symbols(linea.charAt(idx))) return q0(linea, idx + 1);
        
        return false;
    }
    
    public static boolean qN3(String linea, int idx) {
        if(idx >= linea.length()) return true;
        
        if(linea.charAt(idx) - '0' >= 0 && linea.charAt(idx) - '0' <= 9) return qN3(linea, idx + 1);
        if(linea.charAt(idx) - 'A' >= 0 && linea.charAt(idx) - 'A' <= 5) return qN3(linea, idx + 1);
        if(symbols(linea.charAt(idx))) return q0(linea, idx + 1);
        
        return false;
    }
    
    public static boolean qN4(String linea, int idx) {
        if(idx >= linea.length()) return true;
        
        if(linea.charAt(idx) - '0' >= 0 && linea.charAt(idx) - '0' <= 9) return qN4(linea, idx + 1);
        if(linea.charAt(idx) == '.') return qN5(linea, idx + 1);
        if(symbols(linea.charAt(idx))) return q0(linea, idx + 1);
        
        return false;
    }
    
    public static boolean qN5(String linea, int idx) {
        if(idx >= linea.length()) return false;
        if(linea.charAt(idx) - '0' >= 0 && linea.charAt(idx) - '0' <= 9) return qN6(linea, idx + 1);
        return false;
    }
    
    public static boolean qN6(String linea, int idx) {
        if(idx >= linea.length()) return true;
        
        if(linea.charAt(idx) - '0' >= 0 && linea.charAt(idx) - '0' <= 9) return qN6(linea, idx + 1);
        if(linea.charAt(idx) == 'E') return qN7(linea, idx + 1);
        if(symbols(linea.charAt(idx))) return q0(linea, idx + 1);
        
        return false;
    }
    
    public static boolean qN7(String linea, int idx) {
        if(idx >= linea.length()) return false;
        
        if(linea.charAt(idx) - '0' >= 1 && linea.charAt(idx) - '0' <= 9) return qN8(linea, idx + 1);
        if(linea.charAt(idx) == '+' || linea.charAt(idx) == '-') return qN9(linea, idx + 1);
        
        return false;
    }
    
    public static boolean qN8(String linea, int idx) {
        if(idx >= linea.length()) return false;
        if(linea.charAt(idx) - '0' >= 0 && linea.charAt(idx) - '0' <= 9) return qN10(linea, idx + 1);
        return false;
    }
    
    public static boolean qN9(String linea, int idx) {
        if(idx >= linea.length()) return false;
        if(linea.charAt(idx) - '0' >= 1 && linea.charAt(idx) - '0' <= 9) return qN8(linea, idx + 1);
        return false;
    }
    
    public static boolean qN10(String linea, int idx) {
        if(idx >= linea.length()) return true;
        if(linea.charAt(idx) - '0' >= 0 && linea.charAt(idx) - '0' <= 9) return qN11(linea, idx + 1);
        if(symbols(linea.charAt(idx))) return q0(linea, idx + 1);
        return false;
    }
    
    public static boolean qN11(String linea, int idx) {
        if(idx >= linea.length()) return true;
        if(linea.charAt(idx) - '0' >= 0 && linea.charAt(idx) - '0' <= 9) return qN12(linea, idx + 1);
        if(symbols(linea.charAt(idx))) return q0(linea, idx + 1);
        return false;
    }
    
    public static boolean qN12(String linea, int idx) {
        if(idx >= linea.length()) return true;
        if(linea.charAt(idx) - '0' >= 0 && linea.charAt(idx) - '0' <= 9) return qN13(linea, idx + 1);
        if(symbols(linea.charAt(idx))) return q0(linea, idx + 1);
        return false;
    }
    
    public static boolean qN13(String linea, int idx) {
        if(idx >= linea.length()) return true;
        if(symbols(linea.charAt(idx))) return q0(linea, idx + 1);
        return false;
    }
    
    public static boolean qN15(String linea, int idx) {
        if(idx >= linea.length()) return true;
        
        if(linea.charAt(idx) - '0' >= 0 && linea.charAt(idx) - '0' <= 7) return qN15(linea, idx + 1);
        if(symbols(linea.charAt(idx))) return q0(linea, idx + 1);
        
        return false;
    }
    
    public static boolean qReserved(String s) {
        String[] res = {"abstract","assert","boolean","break","byte","case","catch","char","class","continue","const","default","do","double","else","enum","exports","extends","final","finally","float","for","goto","if","implements","import","instanceof","int","interface","long","module","native","new","package","private","protected","public","requires","return","short","static","strictfp","super","switch","synchronized","this","throw","throws","transient","try","var","void","volatile","while"};
        
        for(int i = 0; i < res.length; i++) {
            if(s.equals(res[i])) return true;
        }
        return false;
    }
    
    public static boolean qId(String linea, int idx) {
        if(idx >= linea.length()) return true;
        
        if((linea.charAt(idx) - 'a' >= 0 && linea.charAt(idx) - 'a' <= 25) || (linea.charAt(idx) - 'A' >= 0 && linea.charAt(idx) - 'Z' <= 25) || linea.charAt(idx) == '_' || linea.charAt(idx) == '$' || (linea.charAt(idx) - '0' >= 0 && linea.charAt(idx) - '0' <= 9)) {
            return qId(linea, idx + 1);
        }
        
        if(symbols(linea.charAt(idx))) return q0(linea, idx + 1);
        
        return false;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    /*
    PLANTILLA NODO
    public static boolean q(String linea, int idx) {
        if(idx >= linea.length()) return ;
        
        return false;
    }
    */
    
    
    public static void main(String[] args) {
        
        String cadena = "int num 0652; int pArsEr0 123; int holaPerr123_12 -0xA12;   ";
        
        Pattern parteUno = Pattern.compile("[+|-]?((0(([0-7]*)|x(([0-9]|[A-F])*)))|([1-9](([0-9]*)|(([0-9]*)([.])([0-9]+)(((E([+|-]?)([1-9]([0-9]?[0-9]?[0-9]?))))?)))))([;]?)");
        
        Pattern c7 = Pattern.compile("(abstract)|(assert)|(boolean)|(break)|(byte)|(case)|(catch)|(char)|(class)|(continue)|(const)|(default)|(do)|(double)|(else)|(enum)|(exports)|(extends)|(final)|(finally)|(float)|(for)|(goto)|(if)|(implements)|(import)|(instanceof)|(int)|(interface)|(long)|(module)|(native)|(new)|(package)|(private)|(protected)|(public)|(requires)|(return)|(short)|(static)|(strictfp)|(super)|(switch)|(synchronized)|(this)|(throw)|(throws)|(transient)|(try)|(var)|(void)|(volatile)|(while)");
        
        Pattern c9 = Pattern.compile("\\/((\\/(.+))|((((\\*)(([^/])((([^*])|(\\*[^/]))*)))\\*\\/)))");
        
        
        Matcher matcherParteUno = parteUno.matcher(cadena);
        Matcher matcherC7 = c7.matcher(cadena);
        Matcher matcherC9 = c9.matcher(cadena);
        System.out.println("PARA " + cadena + "\n");
        System.out.println("ERRADO (checar AFD) Parte Uno: " + matcherParteUno.matches());
        System.out.println("Parte Uno MANUAL: " + q0(cadena, 0));
        System.out.println("Parte Siete: " + matcherC7.matches());
        System.out.println("Parte Nueve: " + matcherC9.matches());
        
        ArrayList<String>lineas=new ArrayList<>();
        ArrayList<Integer>indices=new ArrayList<>();
        ArrayList<Integer>idxs=new ArrayList<>();
        indices.add(0);
        
        try{
            System.out.println("Lectura del archivo");
            FileReader lector=new FileReader("C:\\ArchivosPrueba\\ficheroprueba.java");
            BufferedReader bf=new BufferedReader(lector);
            String linea;
            while((linea=bf.readLine())!=null)
            {
                lineas.add(linea);
                System.out.println(" "+ linea);
                
            }

        }catch(IOException e)
        {
            System.out.println("Error");
        }
        int i=0;
        System.out.println("Impresión del ArrayList");
        for(String s:lineas)
        {
            String aux="";
            System.out.println(""+lineas.get(i));
            i++;
        
        }
        System.out.println("Numero de lineas del codigo: "+lineas.size());
        
        for(i=0;i<lineas.size();i++)
        {
            indices.add(lineas.get(i).length());
        }
         idxs.add(0);
        for(i=1;i<=lineas.size();i++)
        {
            idxs.add(idxs.get(i-1)+indices.get(i));
        }
        i=0;
        System.out.println("Indices individuales:");
        for(int j:indices)
        {
            
            System.out.println(""+indices.get(i));
            i++;
        }
        i=0;
        System.out.println("Indices sumados:");
        for(int k:idxs)
        {
            System.out.println(""+idxs.get(i));
            i++;
        }
        
        
        
    }
    
}