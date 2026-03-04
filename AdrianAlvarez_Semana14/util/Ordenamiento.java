package util;

import modelo.Comparable;

import java.util.ArrayList;

public class Ordenamiento
{
    public static void ordenar(ArrayList<Comparable> lista){
        boolean huboIntercambio;
        do {
            huboIntercambio = false;
            for (int i = 0; i < lista.size() - 1; i++){
                Comparable a = lista.get(i);
                Comparable b = lista.get(i+1);
                if(!b.menorQue(a)){
                    lista.set(i, b);
                    lista.set(i+1,a);
                    huboIntercambio = true;
                }
            }
        } while(huboIntercambio);
    }
}
