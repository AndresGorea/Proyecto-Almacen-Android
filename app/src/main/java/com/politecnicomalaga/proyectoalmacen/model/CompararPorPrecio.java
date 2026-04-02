package com.politecnicomalaga.proyectoalmacen.model;
import java.util.Comparator;

/**
 * Write a description of class CompararPorPrecio here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class CompararPorPrecio implements Comparator<Producto> {
    @Override
    public int compare(Producto a, Producto b) {
        double datoA = a.getPrecio();
        double datoB = b.getPrecio();
        
        return Double.compare(datoA, datoB);
    }

    
    
    /* 
     * 
     * @Override
        public int compareTo(Producto otro) {
            double misDatos = this.getPrecio();
            double otrosDatos = otro.getPrecio();
            
            return Double.compare(misDatos, otrosDatos); //Para datos primitivos
        }
     * 
     * 
     * Otras formas 
     * 
     * Mas corto
     * @Override
        public int compareTo(Producto otro) {
            return Double.compare(this.getPrecio(), otro.getPrecio());
        }
     * 
     * 
     * Con Wrappers
     * @Override
        public int compareTo(Producto otro) {
            Double misDatos = this.getPrecio();
            Double otrosDatos = otro.getPrecio();
            
            return misDatos.compareTo(otrosDatos); 
        }
     * 
     * 
     * Logica manual
     * @Override
        public int compareTo(Producto otro) {
            if (this.getPrecio() < otro.getPrecio()) {
                return -1;
            } else if (this.getPrecio() > otro.getPrecio()) {
                return 1;
            } else {
                return 0;
            }
        }
     */
}

