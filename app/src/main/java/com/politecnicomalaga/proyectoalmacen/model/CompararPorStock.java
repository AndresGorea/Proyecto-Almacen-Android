package model;
import java.util.Comparator;

/**
 * Write a description of class CompararPorStock here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CompararPorStock implements Comparator<Producto> {  
    @Override
    public int compare(Producto a, Producto b) {
        double datoA = a.getStock();
        double datoB = b.getStock();
        
        return Double.compare(datoA, datoB);
    }
}
