package com.politecnicomalaga.proyectoalmacen.controller;

import com.politecnicomalaga.proyectoalmacen.model.Producto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controlador {

    /// Atributos
    private static Controlador singleton;
    private List<Producto> misProductos;
    private List<Producto> productosRetirados;

    ///Constructor
    private Controlador()
    {
        this.misProductos = new ArrayList<>();
        this.productosRetirados =  new ArrayList<>();
    }

    ///Controlador
    public static Controlador getSingleton(){
        if(singleton == null) singleton = new Controlador() ;
        return singleton;
    }


    public boolean modificarProducto(HashMap<String, Object> datosProducto) {
        return true;
    }

    public boolean addProducto(HashMap<String, Object> datosProducto) {


       // case(){
        //
       // }
        //Producto p = new Producto(datosProducto.get("codigo"),datosProducto.get("descripcion"),)

        //Mandar a la bd
        //BDDacces = miB

        return true;
    }

    public boolean retirarProducto(String codigo) {
        return true;
    }

    public boolean restarStock(String codigo, int cantidad) {
        return true;

    }

    public boolean cambiarStockTotal(String codigo, int nuevaCantidad) {
        return true;
    }

    public boolean sumarStock(String codigo, int cantidad) {
        return true;
    }


    public Map<String,Object> listarTodo() {
        Map<String,Object> datos = new HashMap<>();

        return datos;
    }

    public Map<String, Object> listarSinStock() {
        Map<String,Object> datos = new HashMap<>();

        return datos;
    }

    public Map<String, Object> listarCaducados() {
        Map<String,Object> datos = new HashMap<>();

        return datos;
    }

    public Map<String, Object> listarRango(Map<String, Double> rangoPrecios) {
        Map<String,Object> datos = new HashMap<>();

        return datos;
    }

    public Map<String, Object> listarRetirados() {
        Map<String,Object> datos = new HashMap<>();

        return datos;
    }


    public Map<String, Object> compararProductos(Map<String, String> productos) {
        Map<String,Object> datos = new HashMap<>();

        return datos;
    }
}
