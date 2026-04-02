package com.politecnicomalaga.proyectoalmacen.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;


/**
 * Class Producto.
 *
 * Representa el elemento hijo de Producto que implementa un atributo nuevo de fecha de caducidad
 * Utilizamos Gson para la gestión de los datos
 *
 * @author Andrés Gorea Olari, Politecnico Málaga
 * @version abril (2026)
 *
 */

public class ProductoPerecedero extends Producto
{
    //Atributos
    private String fechaCaducidad; // Formato "AAAAMMDD" según enunciado
    private static final Gson gson = new Gson();
    
    //Constructor
    public ProductoPerecedero(String codigoProducto, String descripcion, double precio, int stock, String fechaCaducidad) {
        super(codigoProducto, descripcion, precio, stock);
        this.clase = "ProductoPerecedero";
        this.fechaCaducidad = fechaCaducidad;
    }
    
    //Getters y Setters
    public String getFechaCaducidad() {
        return fechaCaducidad;
    }
    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }
    
    //Verificar si esta caducado
    @Override
    public boolean getCaducado() { //Usaremos la fecha de nuestro sistema
        String hoy = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")); //Obtenemos la fecha con nuestro formato
        return this.fechaCaducidad.compareTo(hoy) < 0; //Si fecha hoy mayor, negativo false, else 0 o positivo true
    }
}