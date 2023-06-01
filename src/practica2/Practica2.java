package practica2;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
public class Practica2 {
    
    static ArrayList<String>  lineas = new ArrayList<>();     // Líneas de mi archivo
    static ArrayList<Integer> indices = new ArrayList<>();    // Caracteres por línea
    static ArrayList<Integer> idxs = new ArrayList<>();       // Caracteres acumulados
    static ArrayList<Integer> errores = new ArrayList<>();    // Errores en el archivo
    static long indiceGlobal = -1;                            // Empiezan en -1 porque son sumados de inmediato
    static int indiceLocal = -1;
    static int lineaActual = 0;
    
    /*
        Para encontrar índice en línea i, llamémosle índice local:
        
        ÍndiceLocal = indiceGlobal - idxs.at(lineaActual)
    */
    
    public static boolean symbols(char c) {
        if(Character.isWhitespace(c)) return true;
        if(c == ';' || c == ',') return true;
        if(c == '.' || c == ':') return true;
//        if(c == '(' || c == ')' || c == '[' || c == ']' || c == '{' || c == '}') return true;
        if(c == '\\') return true;
        
        return false;
    }
    
    public static boolean q0() {
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) return true;
        if(lineaActual > lineas.size()) return true;
        indiceGlobal++;
        indiceLocal++;
        if(indiceLocal >= indices.get(lineaActual)) {
            indiceLocal = -1;
            indiceGlobal--;
            lineaActual++;
            return q0();
        }
        
        if(symbols(lineas.get(lineaActual - 1).charAt(indiceLocal))) return q0();
        
        // Constantes numéricas
        if(lineas.get(lineaActual - 1).charAt(indiceLocal) == '+' || lineas.get(lineaActual - 1).charAt(indiceLocal) == '-') return qN1();
        if(lineas.get(lineaActual - 1).charAt(indiceLocal) == '0') return qN2();
        if(lineas.get(lineaActual - 1).charAt(indiceLocal) - '0' >= 1 && lineas.get(lineaActual - 1).charAt(indiceLocal) - '0' <= 9) return qN4();
        
        // Palabras reservadas e identificadores válidos
        if(lineas.get(lineaActual - 1).charAt(indiceLocal) - 'a' >= 0 && lineas.get(lineaActual - 1).charAt(indiceLocal) - 'a' <= 25)          {
            int lIdx = indiceLocal;
            while(lIdx < lineas.get(lineaActual - 1).length() && !(symbols(lineas.get(lineaActual - 1).charAt(lIdx)))) {
                lIdx++;
            }
            String temp = lineas.get(lineaActual - 1).substring(indiceLocal, lIdx);
//            System.out.println("TEMP: " + temp);
            if(qReserved(temp)) {
                indiceLocal = lIdx;
                indiceGlobal = indiceLocal + idxs.get(lineaActual - 1);
                return q0();
            }
            else {
                return qId();
            }
        }
        
        if((lineas.get(lineaActual - 1).charAt(indiceLocal) - 'A' >= 0 && lineas.get(lineaActual - 1).charAt(indiceLocal) - 'A' <= 25) || lineas.get(lineaActual - 1).charAt(indiceLocal) == '_' || lineas.get(lineaActual - 1).charAt(indiceLocal) == '$') {
            return qId();
        }
        
        if(lineas.get(lineaActual - 1).charAt(indiceLocal) == '/') return qC1();
        
        
        indiceGlobal = idxs.get(lineaActual) - 1;
        indiceLocal = -1;
        errores.add(lineaActual);
        lineaActual++;
        return q0();
    }
    
    // CONSTANTES NUMÉRICAS
    public static boolean qN1() {
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) {errores.add(lineaActual); return false;}
        indiceGlobal++;
        indiceLocal++;
        if(indiceLocal >= indices.get(lineaActual)) {
            indiceLocal = -1;
            indiceGlobal--;
            errores.add(lineaActual);
            lineaActual++;
            return q0();
        }
        
        if(lineas.get(lineaActual - 1).charAt(indiceLocal) == '0') return qN2();
        if(lineas.get(lineaActual - 1).charAt(indiceLocal) - '0' >= 1 && lineas.get(lineaActual - 1).charAt(indiceLocal) - '0' <= 9) return qN4();
        
        indiceGlobal = idxs.get(lineaActual) - 1;
        indiceLocal = -1;
        errores.add(lineaActual);
        lineaActual++;
        return q0();
    }
    
    public static boolean qN2() {
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) return true;
        indiceGlobal++;
        indiceLocal++;
        if(indiceLocal >= indices.get(lineaActual)) {
            indiceLocal = -1;
            indiceGlobal--;
            lineaActual++;
            return q0();
        }
        
        if(lineas.get(lineaActual - 1).charAt(indiceLocal) - '0' >= 0 && lineas.get(lineaActual - 1).charAt(indiceLocal) - '0' <= 7) return qN15();
        if(lineas.get(lineaActual - 1).charAt(indiceLocal) == 'x') return qN3();
        if(symbols(lineas.get(lineaActual - 1).charAt(indiceLocal))) return q0();
        
        indiceGlobal = idxs.get(lineaActual) - 1;
        indiceLocal = -1;
        errores.add(lineaActual);
        lineaActual++;
        return q0();
    }
    
    public static boolean qN3() {
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) return true;
        indiceGlobal++;
        indiceLocal++;
        if(indiceLocal >= indices.get(lineaActual)) {
            indiceLocal = -1;
            indiceGlobal--;
            lineaActual++;
            return q0();
        }
        
        if(lineas.get(lineaActual - 1).charAt(indiceLocal) - '0' >= 0 && lineas.get(lineaActual - 1).charAt(indiceLocal) - '0' <= 9) return qN3();
        if(lineas.get(lineaActual - 1).charAt(indiceLocal) - 'A' >= 0 && lineas.get(lineaActual - 1).charAt(indiceLocal) - 'A' <= 5) return qN3();
        if(symbols(lineas.get(lineaActual - 1).charAt(indiceLocal))) return q0();
        
        indiceGlobal = idxs.get(lineaActual) - 1;
        indiceLocal = -1;
        errores.add(lineaActual);
        lineaActual++;
        return q0();
    }
    
    public static boolean qN4() {
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) return true;
        indiceGlobal++;
        indiceLocal++;
        if(indiceLocal >= indices.get(lineaActual)) {
            indiceLocal = -1;
            indiceGlobal--;
            lineaActual++;
            return q0();
        }
        
        if(lineas.get(lineaActual - 1).charAt(indiceLocal) - '0' >= 0 && lineas.get(lineaActual - 1).charAt(indiceLocal) - '0' <= 9) return qN4();
        if(lineas.get(lineaActual - 1).charAt(indiceLocal) == '.') return qN5();
        if(symbols(lineas.get(lineaActual - 1).charAt(indiceLocal))) return q0();
        
        indiceGlobal = idxs.get(lineaActual) - 1;
        indiceLocal = -1;
        errores.add(lineaActual);
        lineaActual++;
        return q0();
    }
    
    public static boolean qN5() {
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) {errores.add(lineaActual); return false;}
        indiceGlobal++;
        indiceLocal++;
        if(indiceLocal >= indices.get(lineaActual)) {
            indiceLocal = -1;
            indiceGlobal--;
            errores.add(lineaActual);
            lineaActual++;
            return q0();
        }
        
        if(lineas.get(lineaActual - 1).charAt(indiceLocal) - '0' >= 0 && lineas.get(lineaActual - 1).charAt(indiceLocal) - '0' <= 9) return qN6();
        indiceGlobal = idxs.get(lineaActual) - 1;
        indiceLocal = -1;
        errores.add(lineaActual);
        lineaActual++;
        return q0();
    }
    
    public static boolean qN6() {
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) return true;
        indiceGlobal++;
        indiceLocal++;
        if(indiceLocal >= indices.get(lineaActual)) {
            indiceLocal = -1;
            indiceGlobal--;
            lineaActual++;
            return q0();
        }
        
        if(lineas.get(lineaActual - 1).charAt(indiceLocal) - '0' >= 0 && lineas.get(lineaActual - 1).charAt(indiceLocal) - '0' <= 9) return qN6();
        if(lineas.get(lineaActual - 1).charAt(indiceLocal) == 'E') return qN7();
        if(symbols(lineas.get(lineaActual - 1).charAt(indiceLocal))) return q0();
        
        indiceGlobal = idxs.get(lineaActual) - 1;
        indiceLocal = -1;
        errores.add(lineaActual);
        lineaActual++;
        return q0();
    }
    
    public static boolean qN7() {
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) {errores.add(lineaActual); return false;}
        indiceGlobal++;
        indiceLocal++;
        if(indiceLocal >= indices.get(lineaActual)) {
            indiceLocal = -1;
            indiceGlobal--;
            errores.add(lineaActual);
            lineaActual++;
            return q0();
        }
        
        if(lineas.get(lineaActual - 1).charAt(indiceLocal) - '0' >= 1 && lineas.get(lineaActual - 1).charAt(indiceLocal) - '0' <= 9) return qN8();
        if(lineas.get(lineaActual - 1).charAt(indiceLocal) == '+' || lineas.get(lineaActual - 1).charAt(indiceLocal) == '-') return qN9();
        
        indiceGlobal = idxs.get(lineaActual) - 1;
        indiceLocal = -1;
        errores.add(lineaActual);
        lineaActual++;
        return q0();
    }
    
    public static boolean qN8() {
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) {errores.add(lineaActual); return false;}
        indiceGlobal++;
        indiceLocal++;
        if(indiceLocal >= indices.get(lineaActual)) {
            indiceLocal = -1;
            indiceGlobal--;
            errores.add(lineaActual);
            lineaActual++;
            return q0();
        }
        
        if(lineas.get(lineaActual - 1).charAt(indiceLocal) - '0' >= 0 && lineas.get(lineaActual - 1).charAt(indiceLocal) - '0' <= 9) return qN10();
        indiceGlobal = idxs.get(lineaActual) - 1;
        indiceLocal = -1;
        errores.add(lineaActual);
        lineaActual++;
        return q0();
    }
    
    public static boolean qN9() {
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) {errores.add(lineaActual); return false;}
        indiceGlobal++;
        indiceLocal++;
        if(indiceLocal >= indices.get(lineaActual)) {
            indiceLocal = -1;
            indiceGlobal--;
            errores.add(lineaActual);
            lineaActual++;
            return q0();
        }
        
        if(lineas.get(lineaActual - 1).charAt(indiceLocal) - '0' >= 1 && lineas.get(lineaActual - 1).charAt(indiceLocal) - '0' <= 9) return qN8();
        indiceGlobal = idxs.get(lineaActual) - 1;
        indiceLocal = -1;
        errores.add(lineaActual);
        lineaActual++;
        return q0();
    }
    
    public static boolean qN10() {
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) return true;
        indiceGlobal++;
        indiceLocal++;
        if(indiceLocal >= indices.get(lineaActual)) {
            indiceLocal = -1;
            indiceGlobal--;
            lineaActual++;
            return q0();
        }
        
        if(lineas.get(lineaActual - 1).charAt(indiceLocal) - '0' >= 0 && lineas.get(lineaActual - 1).charAt(indiceLocal) - '0' <= 9) return qN11();
        if(symbols(lineas.get(lineaActual - 1).charAt(indiceLocal))) return q0();
        
        indiceGlobal = idxs.get(lineaActual) - 1;
        indiceLocal = -1;
        errores.add(lineaActual);
        lineaActual++;
        return q0();
    }
    
    public static boolean qN11() {
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) return true;
        indiceGlobal++;
        indiceLocal++;
        if(indiceLocal >= indices.get(lineaActual)) {
            indiceLocal = -1;
            indiceGlobal--;
            lineaActual++;
            return q0();
        }
        
        if(lineas.get(lineaActual - 1).charAt(indiceLocal) - '0' >= 0 && lineas.get(lineaActual - 1).charAt(indiceLocal) - '0' <= 9) return qN12();
        if(symbols(lineas.get(lineaActual - 1).charAt(indiceLocal))) return q0();
        
        indiceGlobal = idxs.get(lineaActual) - 1;
        indiceLocal = -1;
        errores.add(lineaActual);
        lineaActual++;
        return q0();
    }
    
    public static boolean qN12() {
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) return true;
        indiceGlobal++;
        indiceLocal++;
        if(indiceLocal >= indices.get(lineaActual)) {
            indiceLocal = -1;
            indiceGlobal--;
            lineaActual++;
            return q0();
        }
        
        if(lineas.get(lineaActual - 1).charAt(indiceLocal) - '0' >= 0 && lineas.get(lineaActual - 1).charAt(indiceLocal) - '0' <= 9) return qN13();
        if(symbols(lineas.get(lineaActual - 1).charAt(indiceLocal))) return q0();
        
        indiceGlobal = idxs.get(lineaActual) - 1;
        indiceLocal = -1;
        errores.add(lineaActual);
        lineaActual++;
        return q0();
    }
    
    public static boolean qN13() {
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) return true;
        indiceGlobal++;
        indiceLocal++;
        if(indiceLocal >= indices.get(lineaActual)) {
            indiceLocal = -1;
            indiceGlobal--;
            lineaActual++;
            return q0();
        }
        
        if(symbols(lineas.get(lineaActual - 1).charAt(indiceLocal))) return q0();
        
        indiceGlobal = idxs.get(lineaActual) - 1;
        indiceLocal = -1;
        errores.add(lineaActual);
        lineaActual++;
        return q0();
    }
    
    public static boolean qN15() {
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) return true;
        indiceGlobal++;
        indiceLocal++;
        if(indiceLocal >= indices.get(lineaActual)) {
            indiceLocal = -1;
            indiceGlobal--;
            lineaActual++;
            return q0();
        }
        
        if(lineas.get(lineaActual - 1).charAt(indiceLocal) - '0' >= 0 && lineas.get(lineaActual - 1).charAt(indiceLocal) - '0' <= 7) return qN15();
        if(symbols(lineas.get(lineaActual - 1).charAt(indiceLocal))) return q0();
        
        indiceGlobal = idxs.get(lineaActual) - 1;
        indiceLocal = -1;
        errores.add(lineaActual);
        lineaActual++;
        return q0();
    }
    
    public static boolean qReserved(String s) {
        String[] res = {"abstract","assert","boolean","break","byte","case","catch","char","class","continue","const","default","do","double","else","enum","exports","extends","final","finally","float","for","goto","if","implements","import","instanceof","int","interface","long","module","native","new","package","private","protected","public","requires","return","short","static","strictfp","super","switch","synchronized","this","throw","throws","transient","try","var","void","volatile","while"};
        
        for (String reservada : res) {
            if (s.equals(reservada)) {
                return true;
            }
        }
        return false;
    }
    
    
    
    
    public static boolean qId() {
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) return true;
        indiceGlobal++;
        indiceLocal++;
        if(indiceLocal >= indices.get(lineaActual)) {
            indiceLocal = -1;
            indiceGlobal--;
            lineaActual++;
            return q0();
        }
        char c = lineas.get(lineaActual - 1).charAt(indiceLocal);
        if((c - 'a' >= 0 && c - 'a' <= 25) || (c - 'A' >= 0 && c - 'A' <= 25) || c == '_' || c == '$' || (c - '0' >= 0 && c - '0' <= 9)) {
            return qId();
        }
        
        if(Character.isWhitespace(c))
        {
            return qwsp();
        }
        if(c=='<'||c=='>'||c=='=')
        {
            return comps();
        }
        if(c=='!')
            return qa3();
        
        if(c=='+'||c=='-')
            return qa11();
        
        if(c=='*'||c=='/'||c=='%')
            return qa4();
        
        
        if(symbols(c)) return q0();
        
        
        indiceGlobal = idxs.get(lineaActual) - 1;
        indiceLocal = -1;
        errores.add(lineaActual);
        lineaActual++;
        
        return q0();
    }
    
    
    public static boolean qwsp() {
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) {
//            errores.add(lineaActual);
            return true;
        }
        if(lineaActual > lineas.size()) {
//            errores.add(lineaActual);
            return true;
        }
        indiceGlobal++;
        indiceLocal++;
        if(indiceLocal >= indices.get(lineaActual)) {
            indiceLocal = -1;
            indiceGlobal--;
//            errores.add(lineaActual);
            lineaActual++;
            return q0();
        }
        
        char c = lineas.get(lineaActual - 1).charAt(indiceLocal);
        if(Character.isWhitespace(c))
            return qwsp();
        
        if(c=='<'||c=='>'||c=='=')
        {
            return comps();
        }
        
        if(c=='!')
            return qa3();
        
        if(c=='+'||c=='-')
            return qa11();
        
        if(c=='*'||c=='/'||c=='%')
            return qa4();
        
       
//        indiceGlobal = idxs.get(lineaActual) - 1;
//        indiceLocal = -1;
//        errores.add(lineaActual);
//        lineaActual++;
        
        return q0();
    }
    
    
    
    
    public static boolean comps() {
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) {
            errores.add(lineaActual);
            return false;
        }
        if(lineaActual > lineas.size()) {
            errores.add(lineaActual);
            return false;
        }
        indiceGlobal++;
        indiceLocal++;
        if(indiceLocal >= indices.get(lineaActual)) {
            indiceLocal = -1;
            indiceGlobal--;
            errores.add(lineaActual);
            lineaActual++;
            return q0();
        }
        
        char c = lineas.get(lineaActual - 1).charAt(indiceLocal);
        if(Character.isWhitespace(c))
        {
            return  qa5();
        }
        
//        if(c - 'a'>= 0 && c - 'a' <= 25)
//            return qa6();
        
        if(c=='=')
            return qa2();
        //aceptacion qa10
        // Valores numéricos
        if(c == '+' || c == '-') return qN1();
        if(c == '0') return qN2();
        if(c- '0' >= 1 && c- '0' <= 9) return qN4();
        // Identificador
        if((c - 'a' >= 0 && c - 'a' <= 25) || (c - 'A' >= 0 && c - 'A' <= 25) || c == '_' || c == '$' || (c - '0' >= 0 && c - '0' <= 9)) {
          return qId();
        }
        
        
       
//        System.out.println("Error en comps, c:"+c);
        indiceGlobal = idxs.get(lineaActual) - 1;
        indiceLocal = -1;
        errores.add(lineaActual);
        lineaActual++;
        return q0();
    }
    
    
    
    
    public static boolean qa3() {
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) {
            errores.add(lineaActual);
            return false;
        }
        if(lineaActual > lineas.size()) {
            errores.add(lineaActual);
            return false;
        }
        indiceGlobal++;
        indiceLocal++;
        if(indiceLocal >= indices.get(lineaActual)) {
            indiceLocal = -1;
            indiceGlobal--;
            errores.add(lineaActual);
            lineaActual++;
            return q0();
        }
        
        
        char c = lineas.get(lineaActual - 1).charAt(indiceLocal);
        if(c=='=')
            return qa2();
        
        indiceGlobal = idxs.get(lineaActual) - 1;
        indiceLocal = -1;
        errores.add(lineaActual);
        lineaActual++;
        return q0();
    }
    
    
     public static boolean qa2() {
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) {
            errores.add(lineaActual);
            return false;
        }
        if(lineaActual > lineas.size()) {
            errores.add(lineaActual);
            return false;
        }
        indiceGlobal++;
        indiceLocal++;
        if(indiceLocal >= indices.get(lineaActual)) {
            indiceLocal = -1;
            indiceGlobal--;
            errores.add(lineaActual);
            lineaActual++;
            return q0();
        }
        
        char c = lineas.get(lineaActual - 1).charAt(indiceLocal);
//        if(c=='(')
//            return qa6();
        if(Character.isWhitespace(c))
        {
            return  qa5();
        }
        
        
        //qa10
        // Valores numéricos
        if(c == '+' || c == '-') return qN1();
        if(c == '0') return qN2();
        if(c- '0' >= 1 && c- '0' <= 9) return qN4();
        // Identificador
        if((c - 'a' >= 0 && c - 'a' <= 25) || (c - 'A' >= 0 && c - 'A' <= 25) || c == '_' || c == '$' || (c - '0' >= 0 && c - '0' <= 9)) {
          return qId();
        }
        
//        System.out.println("Error en a2, c:"+c);
        indiceGlobal = idxs.get(lineaActual) - 1;
        indiceLocal = -1;
        errores.add(lineaActual);
        lineaActual++;
        return q0();
    }
     
     
      public static boolean qa4() {
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) {
            errores.add(lineaActual);
            return false;
        }
        if(lineaActual > lineas.size()) {
            errores.add(lineaActual);
            return false;
        }
        indiceGlobal++;
        indiceLocal++;
        if(indiceLocal >= indices.get(lineaActual)) {
            indiceLocal = -1;
            indiceGlobal--;
            errores.add(lineaActual);
            lineaActual++;
            return q0();
        }
        
        char c = lineas.get(lineaActual - 1).charAt(indiceLocal);
        if(Character.isWhitespace(c))
            return qa5();
        
//        if(c=='(')
//            return qa6();
        if(c=='=')
            return comps();
        //qa10
        // Valores numéricos
        if(c == '+' || c == '-') return qN1();
        if(c == '0') return qN2();
        if(c- '0' >= 1 && c- '0' <= 9) return qN4();
        // Identificador
        if((c - 'a' >= 0 && c - 'a' <= 25) || (c - 'A' >= 0 && c - 'A' <= 25) || c == '_' || c == '$' || (c - '0' >= 0 && c - '0' <= 9)) {
          return qId();
        }
//        System.out.println("Error en qa4, c:"+c);
        indiceGlobal = idxs.get(lineaActual) - 1;
        indiceLocal = -1;
        errores.add(lineaActual);
        lineaActual++;
        return q0();
    }
      
      
    public static boolean qa5() {
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) {
            errores.add(lineaActual);
            return false;
        }
        if(lineaActual > lineas.size()) {
            errores.add(lineaActual);
            return false;
        }
        indiceGlobal++;
        indiceLocal++;
        if(indiceLocal >= indices.get(lineaActual)) {
            indiceLocal = -1;
            indiceGlobal--;
            errores.add(lineaActual);
            lineaActual++;
            return q0();
        }
        
        char c = lineas.get(lineaActual - 1).charAt(indiceLocal);
//        if(c=='(')
//            return qa6();
        if(Character.isWhitespace(c))
            return qa5();
        
        //qa10
        // Valores numéricos
        if(c == '+' || c == '-') return qN1();
        if(c == '0') return qN2();
        if(c- '0' >= 1 && c- '0' <= 9) return qN4();
        // Identificador
        if((c - 'a' >= 0 && c - 'a' <= 25) || (c - 'A' >= 0 && c - 'A' <= 25) || c == '_' || c == '$' || (c - '0' >= 0 && c - '0' <= 9)) {
          return qId();
        }
//        System.out.println("Error en qa5, c:"+c);
        indiceGlobal = idxs.get(lineaActual) - 1;
        indiceLocal = -1;
        errores.add(lineaActual);
        lineaActual++;
        return q0();
    }
    
    public static boolean qa7(){
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) {
            errores.add(lineaActual);
            return false;
        }
        if(lineaActual > lineas.size()) {
            errores.add(lineaActual);
            return false;
        }
        indiceGlobal++;
        indiceLocal++;
        if(indiceLocal >= indices.get(lineaActual)) {
            indiceLocal = -1;
            indiceGlobal--;
            errores.add(lineaActual);
            lineaActual++;
            return q0();
        }
        
        char c = lineas.get(lineaActual - 1).charAt(indiceLocal-1);
        if(c==')')
        {
            return qa8();
        }
        if(Character.isWhitespace(c))
            return qa7();
        
        indiceGlobal = idxs.get(lineaActual) - 1;
        indiceLocal = -1;
        errores.add(lineaActual);
        lineaActual++;
        
//        System.out.println("Error en q7, caracter:"+c);
        return q0();
    }
        
    public static boolean qa8() {
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) {
            errores.add(lineaActual);
            return false;
        }
        if(lineaActual > lineas.size()) {
            errores.add(lineaActual);
            return false;
        }
        indiceGlobal++;
        indiceLocal++;
        if(indiceLocal >= indices.get(lineaActual)) {
            indiceLocal = -1;
            indiceGlobal--;
            errores.add(lineaActual);
            lineaActual++;
            return q0();
        }
        
        char c = lineas.get(lineaActual - 1).charAt(indiceLocal);

        if(Character.isWhitespace(c))
            return qa9();
        
        //qa10
        // Valores numéricos
        if(c == '+' || c == '-') return qN1();
        if(c == '0') return qN2();
        if(c- '0' >= 1 && c- '0' <= 9) return qN4();
        // Identificador
        if((c - 'a' >= 0 && c - 'a' <= 25) || (c - 'A' >= 0 && c - 'A' <= 25) || c == '_' || c == '$' || (c - '0' >= 0 && c - '0' <= 9)) {
          return qId();
        }
        
        
        
        indiceGlobal = idxs.get(lineaActual) - 1;
        indiceLocal = -1;
        errores.add(lineaActual);
        lineaActual++;
//        System.out.println("Error en q8, caracter:"+c);
        return q0();
    }
    
         
    public static boolean qa9() {
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) {
            errores.add(lineaActual);
            return false;
        }
        if(lineaActual > lineas.size()) {
            errores.add(lineaActual);
            return false;
        }
        indiceGlobal++;
        indiceLocal++;
        if(indiceLocal >= indices.get(lineaActual)) {
            indiceLocal = -1;
            indiceGlobal--;
            errores.add(lineaActual);
            lineaActual++;
            return q0();
        }
        
        char c = lineas.get(lineaActual - 1).charAt(indiceLocal);
        if(Character.isWhitespace(c))
            return qa9();
        
        
        //qa10
        // Valores numéricos
        if(c == '+' || c == '-') return qN1();
        if(c == '0') return qN2();
        if(c- '0' >= 1 && c- '0' <= 9) return qN4();
        // Identificador
        if((c - 'a' >= 0 && c - 'a' <= 25) || (c - 'A' >= 0 && c - 'A' <= 25) || c == '_' || c == '$' || (c - '0' >= 0 && c - '0' <= 9)) {
          return qId();
        }
        indiceGlobal = idxs.get(lineaActual) - 1;
        indiceLocal = -1;
        errores.add(lineaActual);
        lineaActual++;
        return q0();
    }
    
    public static boolean qa11() {
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) {
            errores.add(lineaActual);
            return false;
        }
        if(lineaActual >= lineas.size()) {
            errores.add(lineaActual);
            return false;
        }
        indiceGlobal++;
        indiceLocal++;
        if(indiceLocal >= indices.get(lineaActual)) {
            indiceLocal = -1;
            indiceGlobal--;
            errores.add(lineaActual); //SÓLO SI NO ES DE ACEPTACIÓN
            lineaActual++;
            return q0();
        }
        
        char c = lineas.get(lineaActual - 1).charAt(indiceLocal);
        
        if(lineas.get(lineaActual - 1).charAt(indiceLocal - 1) == '+') {
            if(c == '+') return q0();
        }
        
        if(lineas.get(lineaActual - 1).charAt(indiceLocal - 1) == '-') {
            if(c == '-') return q0();
        }
        
        if(Character.isWhitespace(c))
            return qa5();
        
//        if(c=='(')
//            return qa6();
        if(c=='=')
            return comps();
        //qa10
        // Valores numéricos
        if(c == '0') return qN2();
        if(c- '0' >= 1 && c- '0' <= 9) return qN4();
        // Identificador
        if((c - 'a' >= 0 && c - 'a' <= 25) || (c - 'A' >= 0 && c - 'A' <= 25) || c == '_' || c == '$' || (c - '0' >= 0 && c - '0' <= 9)) {
          return qId();
        }
        
        
        indiceGlobal = idxs.get(lineaActual) - 1;
        indiceLocal = -1;
        errores.add(lineaActual);
        lineaActual++;
        return q0();
    }
         
         
      
      
       
       
       
        /*
    PLANTILLA NODO
    En los return vacío pones
        Si es de aceptación: true
        Si no: false
    
    public static boolean q() {
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) return ;
        if(lineaActual >= lineas.size()) {
            // errores.add(lineaActual); SÓLO SI NO ES DE ACEPTACIÓN
            return ;
        }
        indiceGlobal++;
        indiceLocal++;
        if(indiceLocal >= indices.get(lineaActual)) {
            indiceLocal = -1;
            indiceGlobal--;
            // errores.add(lineaActual); SÓLO SI NO ES DE ACEPTACIÓN
            lineaActual++;
            return q0();
        }
        
        
        indiceGlobal = idxs.get(lineaActual) - 1;
        indiceLocal = -1;
        errores.add(lineaActual);
        lineaActual++;
        return q0();
    }
    */
    public static boolean qC1() {
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) {errores.add(lineaActual); return false;}
        indiceGlobal++;
        indiceLocal++;
        if(indiceLocal >= indices.get(lineaActual)) {
            indiceLocal = -1;
            indiceGlobal--;
            errores.add(lineaActual);
            lineaActual++;
            return q0();
        }
        
        if(lineas.get(lineaActual - 1).charAt(indiceLocal) == '/') return qC2();
        if(lineas.get(lineaActual - 1).charAt(indiceLocal) == '*') return qC3();
        
        indiceGlobal = idxs.get(lineaActual) - 1;
        indiceLocal = -1;
        errores.add(lineaActual);
        lineaActual++;
        return q0();
    }
    
    public static boolean qC2() {
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) return true;
        indiceGlobal = idxs.get(lineaActual) - 1;
        indiceLocal = indices.get(lineaActual) + 1;
        return q0();
    }
    
    public static boolean qC3() {
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) {errores.add(lineaActual); return false;}
        indiceGlobal++;
        indiceLocal++;
        if(indiceLocal >= indices.get(lineaActual)) {
            indiceLocal = -1;
            indiceGlobal--;
            lineaActual++;
            return qC3();
        }
        
        if(lineas.get(lineaActual - 1).charAt(indiceLocal) == '*') return qC5();
        else return qC4();
    }
    
    public static boolean qC4() {
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) {errores.add(lineaActual); return false;}
        if(lineaActual > lineas.size()) {errores.add(lineaActual); return false;}
        indiceGlobal++;
        indiceLocal++;
        if(indiceLocal >= indices.get(lineaActual)) {
            indiceLocal = -1;
            indiceGlobal--;
            lineaActual++;
            return qC4();
        }
        
        if(lineas.get(lineaActual - 1).charAt(indiceLocal) == '*') return qC5();
        else return qC4();
    }
    
    public static boolean qC5() {
        if(indiceGlobal >= idxs.get(idxs.size() - 1)) {errores.add(lineaActual); return false;}
        if(lineaActual > lineas.size()) {errores.add(lineaActual); return false;}
        indiceGlobal++;
        indiceLocal++;
        if(indiceLocal >= indices.get(lineaActual)) {
            indiceLocal = -1;
            indiceGlobal--;
            errores.add(lineaActual);
            lineaActual++;
            return q0();
        }
        
        if(lineas.get(lineaActual - 1).charAt(indiceLocal) == '/') return q0();
        else return qC4();
    }
    
    
   
    
    
    public static void main(String[] args) {
        indices.add(0);
        
        try {
//            System.out.println("Lectura del archivo");
            FileReader lector=new FileReader("C:\\Users\\Asus\\Desktop\\PRUEBAS AUTOMATA\\pruebas.java");
            BufferedReader bf=new BufferedReader(lector);
            String linea;
            while((linea=bf.readLine())!=null) {
                linea = linea.replace('(', ' ');
                linea = linea.replace(')', ' ');
                linea = linea.replace('[', ' ');
                linea = linea.replace(']', ' ');
                linea = linea.replace('{', ' ');
                linea = linea.replace('}', ' ');
                lineas.add(linea);
            }
        } catch(IOException e) {
            System.out.println("ERROR DE ARCHIVO");
            return;
        }
        
        for(int i=0;i<lineas.size();i++) {
            indices.add(lineas.get(i).length());
        }
        idxs.add(0);
        for(int i=1;i<=lineas.size();i++) {
            idxs.add(idxs.get(i-1)+indices.get(i));
        }
        
        for(String s : lineas)
            System.out.println(s);
        
//        System.out.println("Locales");
//        for(int i : indices) {
//            System.out.print(i + ", ");
//        }
//        System.out.println("");
//        System.out.println("Acumulados");
//        for(int i : idxs) {
//            System.out.print(i + ", ");
//        }
//        System.out.println("");
        
        q0();
        if(errores.isEmpty()) {
            System.out.println("No hay errores de análisis léxico del archivo");
        } else {
            for(int i : errores) {
                System.out.println("Error en línea " + (i));
            }
        }
    }
}