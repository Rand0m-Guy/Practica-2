package practica2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
public class Practica2 {
    
    public static boolean q0(String linea, int idx) {
        if(idx >= linea.length()) return false;
        
        if(linea.charAt(idx) == '+' | linea.charAt(idx) == '-') return q1(linea, idx + 1);
        if(linea.charAt(idx) == '0') return q2(linea, idx + 1);
        if(linea.charAt(idx) - '0' >= 1 && linea.charAt(idx) - '0' <= 9) return q4(linea, idx + 1);
        return false;
    }
    
    public static boolean q1(String linea, int idx) {
        if(idx >= linea.length()) return false;
        
        if(linea.charAt(idx) == '0') return q2(linea, idx + 1);
        if(linea.charAt(idx) - '0' >= 1 && linea.charAt(idx) - '0' <= 9) return q4(linea, idx + 1);
        
        return false;
    }
    
    public static boolean q2(String linea, int idx) {
        if(idx >= linea.length()) return true;
        
        if(linea.charAt(idx) - '0' >= 0 && linea.charAt(idx) - '0' <= 7) return q15(linea, idx + 1);
        if(linea.charAt(idx) == 'x') return q3(linea, idx + 1);
        if(linea.charAt(idx) == ';') return q14(linea, idx + 1);
        
        return false;
    }
    
    public static boolean q3(String linea, int idx) {
        if(idx >= linea.length()) return true;
        
        if(linea.charAt(idx) - '0' >= 0 && linea.charAt(idx) - '0' <= 9) return q3(linea, idx + 1);
        if(linea.charAt(idx) - 'A' >= 0 && linea.charAt(idx) - 'A' <= 5) return q3(linea, idx + 1);
        if(linea.charAt(idx) == ';') return q14(linea, idx + 1);
        
        return false;
    }
    
    public static boolean q4(String linea, int idx) {
        if(idx >= linea.length()) return true;
        
        if(linea.charAt(idx) - '0' >= 0 && linea.charAt(idx) - '0' <= 9) return q4(linea, idx + 1);
        if(linea.charAt(idx) == '.') return q5(linea, idx + 1);
        if(linea.charAt(idx) == ';') return q14(linea, idx + 1);
        
        return false;
    }
    
    public static boolean q5(String linea, int idx) {
        if(idx >= linea.length()) return false;
        if(linea.charAt(idx) - '0' >= 0 && linea.charAt(idx) - '0' <= 9) return q6(linea, idx + 1);
        return false;
    }
    
    public static boolean q6(String linea, int idx) {
        if(idx >= linea.length()) return true;
        
        if(linea.charAt(idx) - '0' >= 0 && linea.charAt(idx) - '0' <= 9) return q6(linea, idx + 1);
        if(linea.charAt(idx) == 'E') return q7(linea, idx + 1);
        if(linea.charAt(idx) == ';') return q14(linea, idx + 1);
        
        return false;
    }
    
    public static boolean q7(String linea, int idx) {
        if(idx >= linea.length()) return false;
        
        if(linea.charAt(idx) - '0' >= 1 && linea.charAt(idx) - '0' <= 9) return q8(linea, idx + 1);
        if(linea.charAt(idx) == '+' || linea.charAt(idx) == '-') return q9(linea, idx + 1);
        
        return false;
    }
    
    public static boolean q8(String linea, int idx) {
        if(idx >= linea.length()) return false;
        if(linea.charAt(idx) - '0' >= 0 && linea.charAt(idx) - '0' <= 9) return q10(linea, idx + 1);
        return false;
    }
    
    public static boolean q9(String linea, int idx) {
        if(idx >= linea.length()) return false;
        if(linea.charAt(idx) - '0' >= 1 && linea.charAt(idx) - '0' <= 9) return q8(linea, idx + 1);
        return false;
    }
    
    public static boolean q10(String linea, int idx) {
        if(idx >= linea.length()) return true;
        if(linea.charAt(idx) - '0' >= 0 && linea.charAt(idx) - '0' <= 9) return q11(linea, idx + 1);
        if(linea.charAt(idx) == ';') return q14(linea, idx + 1);
        return false;
    }
    
    public static boolean q11(String linea, int idx) {
        if(idx >= linea.length()) return true;
        if(linea.charAt(idx) - '0' >= 0 && linea.charAt(idx) - '0' <= 9) return q12(linea, idx + 1);
        if(linea.charAt(idx) == ';') return q14(linea, idx + 1);
        return false;
    }
    
    public static boolean q12(String linea, int idx) {
        if(idx >= linea.length()) return true;
        if(linea.charAt(idx) - '0' >= 0 && linea.charAt(idx) - '0' <= 9) return q13(linea, idx + 1);
        if(linea.charAt(idx) == ';') return q14(linea, idx + 1);
        return false;
    }
    
    public static boolean q13(String linea, int idx) {
        if(idx >= linea.length()) return true;
        if(linea.charAt(idx) == ';') return q14(linea, idx + 1);
        return false;
    }
    
    public static boolean q14(String linea, int idx) {
        if(idx != linea.length()) return false;
        return true;
    }
    
    public static boolean q15(String linea, int idx) {
        if(idx >= linea.length()) return true;
        
        if(linea.charAt(idx) - '0' >= 0 && linea.charAt(idx) - '0' <= 7) return q15(linea, idx + 1);
        if(linea.charAt(idx) == ';') return q14(linea, idx + 1);
        
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
        
        String cadena = "0xADEDDDDDDDDDDD0123456789999995635435645";
        
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