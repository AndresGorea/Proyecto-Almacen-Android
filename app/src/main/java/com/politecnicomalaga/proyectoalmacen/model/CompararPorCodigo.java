package model;
import java.util.Comparator;

/**
 * Write a description of class CompararPorCodigo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CompararPorCodigo implements Comparator<Producto> {
    @Override
    public int compare(Producto a, Producto b) {
        String datoA = a.getCodigoProducto();
        String datoB = b.getCodigoProducto();
        
        return datoA.compareTo(datoB);
    }
}
