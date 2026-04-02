package com.politecnicomalaga.proyectoalmacen.model;
import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Class Producto.
 *
 * Representa el elemento padre en nuestro sistema de almacén
 * Utilizamos Gson para la gestión de los datos
 *
 * @author Andrés Gorea Olari, Politecnico Málaga
 * @version abril (2026)
 *
 */

public class Producto implements Comparable<Producto>{
    //Atributos
    protected String clase = "Producto";
    private String codigoProducto;
    private String descripcion;
    private double precio;
    private int stock;
    protected static final Gson gson = new Gson();
    protected static final JsonObject jsonObject = new JsonObject();

    // Constructor
    public Producto(String codigoProducto, String descripcion, double precio, int stock) {
        setCodigoProducto(codigoProducto); // Usamos el setter para validar
        this.descripcion = descripcion;
        setPrecio(precio); // Usamos el setter para validar
        setStock(stock); // Usamos el setter para validar
    }

    // Getters y Setters

    //Código
    public String getCodigoProducto() {
        return codigoProducto;
    }
    
    public void setCodigoProducto(String codigoProducto) {
        if (codigoProducto == null || codigoProducto.length() < 8 || codigoProducto.length() > 16) {
            throw new IllegalArgumentException("El código de producto debe ser alfanumérico y tener entre 8 y 16 caracteres.");
        }
        this.codigoProducto = codigoProducto;
    }
    //Descripción
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    //Precio
    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {
        this.precio = (precio > 0.0) ? precio : - precio;
    }
    //Stock
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) { 
        if (stock>=0) this.stock = stock; //No podemos tener stock negativo
    }

    //Métodos
    public void changeStock(int newStock) {
        if (this.stock + newStock < 0) return; //nunca tenemos stock negativo
        this.stock += newStock;  //si newStock es negativo, se quita al almacén unidades del producto
    }

    //Para obtener si está caducado, estos productos no caducan asi que siempre false, y no necesitamos el atributo con la clase es suficiente.
    public boolean getCaducado(){
        return false; 
    }

    @Override
    public int compareTo(@NonNull Producto otro){ // Ordenación por descripcion por defecto
        String producto = this.getDescripcion();
        String otroProducto = otro.getDescripcion();

        return producto.compareTo(otroProducto);
    }

    // Mostrar la información del producto. Json
    @NonNull
    @Override
    public String toString() {
        return gson.toJson(this);
    }

    //Importación de datos a local
    public static Producto cargarDatos(String data) {
        return gson.fromJson(data, Producto.class);
    }

    public static Producto cargarDatos2(String data) {
        JsonObject jsonBusqueda = gson.fromJson(data, JsonObject.class); //Convertimos a un objeto json

        String tipo = jsonBusqueda.get("clase").getAsString(); //Buscamos el valor el tipo de producto en la etiqueta clase

        if (tipo.equals("ProductoPerecedero")) {
            return gson.fromJson(data, ProductoPerecedero.class);
        }
        return gson.fromJson(data, Producto.class);
    }
}
