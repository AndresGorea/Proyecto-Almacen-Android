package com.politecnicomalaga.proyectoalmacen.controller;

import com.politecnicomalaga.proyectoalmacen.model.Producto;
import com.politecnicomalaga.proyectoalmacen.model.ProductoPerecedero;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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


    public boolean addProducto(HashMap<String, String> datosProducto) {

        String codigo = (String) Objects.requireNonNull(datosProducto.get("codigo"));
        String descripcion = (String) Objects.requireNonNull(datosProducto.get("descripcion"));
        double precio = Double.parseDouble((String) Objects.requireNonNull(datosProducto.get("precio")));
        int stock = Integer.parseInt((String) Objects.requireNonNull(datosProducto.get("stock")));
        String tipo = (String) Objects.requireNonNull(datosProducto.get("tipo"));

        if (codigo.isBlank()) return false;

        for (Producto p : misProductos){
            if (codigo.equals(p.getCodigoProducto())) return false;
        }

        Producto productoNuevo = null;
        switch (tipo){
            case "Normal":
                productoNuevo = new Producto(codigo, descripcion, precio, stock);
                break;
            case "Perecedero":
                String fecha = (String) datosProducto.get("fecha");
                productoNuevo = new ProductoPerecedero(codigo, descripcion, precio, stock, fecha);
                break;
        }
        if (productoNuevo != null) {
            return misProductos.add(productoNuevo);
        }
        return false;
    }

    public boolean modificarProducto(HashMap<String, String> datosProducto) {
        String codigo = (String) Objects.requireNonNull(datosProducto.get("codigo"));
        String descripcion = (String) Objects.requireNonNull(datosProducto.get("descripcion"));
        double precio = Double.parseDouble((String) Objects.requireNonNull(datosProducto.get("precio")));
        int stock = Integer.parseInt((String) Objects.requireNonNull(datosProducto.get("stock")));
        String tipo = (String) Objects.requireNonNull(datosProducto.get("tipo"));

        if (codigo.isBlank()) return false;

        //Búsqueda y remplazo del producto en la misma posición
        for (int i = 0 ; i < misProductos.size(); i++){
            if (codigo.equals(misProductos.get(i).getCodigoProducto())){
                Producto productoNuevo = null;
                switch (tipo){
                    case "Normal":
                        productoNuevo = new Producto(codigo, descripcion, precio, stock);
                        break;
                    case "Perecedero":
                        String fecha = (String) datosProducto.get("fecha");
                        productoNuevo = new ProductoPerecedero(codigo, descripcion, precio, stock, fecha);
                        break;
                }
                if (productoNuevo != null) {
                    misProductos.set(i, productoNuevo); // Remplazo con iterator
                    return true;
                }

            }
        }
        return false;
    }

    public boolean retirarProducto(String codigo) {
        for (Producto p: misProductos){
            if (p.getCodigoProducto().equals(codigo)){
                misProductos.remove(p); //Lo quitamos de lista de activos
                productosRetirados.add(p); //Lo añadimos a la lista de retirados
                return true;
            }
        }
        return false;
    }

    public boolean sumarStock(String codigo, int cantidad) {
        for (Producto p: misProductos){
            if (p.getCodigoProducto().equals(codigo)) {
                p.setStock(p.getStock() + cantidad);
                return true;
            }
        }
        return false;
    }

    public boolean restarStock(String codigo, int cantidad) {
        for (Producto p: misProductos){
            if (p.getCodigoProducto().equals(codigo)) {
                p.setStock(p.getStock() - cantidad);
                return true;
            }
        }
        return false;
    }

    public boolean cambiarStockTotal(String codigo, int nuevaCantidad) {
        for (Producto p: misProductos){
            if (p.getCodigoProducto().equals(codigo)) {
                p.setStock(nuevaCantidad);
                return true;
            }
        }
        return false;
    }

    public Map<String,String> listarTodo() {
        Map<String,String> datos = new HashMap<>();
        for (Producto p: misProductos){
            datos.put(p.getCodigoProducto(), p.toString());
        }
        return datos;
    }

    public Map<String, String> listarSinStock() {
        Map<String,String> datos = new HashMap<>();
        for (Producto p : misProductos) {
            if (p.getStock() == 0) {
                datos.put(p.getCodigoProducto(), p.toString());
            }
        }
        return datos;
    }

    public Map<String, String> listarCaducados() {
        Map<String,String> datos = new HashMap<>();
        for (Producto p : misProductos) {
            if (p.getCaducado()) {
                datos.put(p.getCodigoProducto(), p.toString());
            }
        }
        return datos;
    }

    public Map<String, String> listarRango(Map<String, Double> rangoPrecios) {
        Map<String,String> datos = new HashMap<>();
        double min = (Double) Objects.requireNonNull(rangoPrecios.get("precioMin"));
        double max = (Double) Objects.requireNonNull(rangoPrecios.get("precioMax"));

        for (Producto p : misProductos) {
            if (p.getPrecio() >= min && p.getPrecio() <= max) {
                datos.put(p.getCodigoProducto(), p.toString());
            }
        }
        return datos;
    }

    public Map<String, String> listarRetirados() {
        Map<String,String> datos = new HashMap<>();
        for (Producto p : productosRetirados) {
            datos.put(p.getCodigoProducto(), p.toString());
        }
        return datos;
    }

    public Map<String, String> compararProductos(Map<String, String> productoComparar) {
        Map<String,String> datos = new HashMap<>();
        String codigo = (String) Objects.requireNonNull(productoComparar.get("codigo"));

        for (Producto p : misProductos) {
            if (codigo.equals(p.getCodigoProducto())) {
                datos.put(p.getCodigoProducto(), p.toString());
                break; //no seguir buscando para nada
            }
        }
        return datos;
    }
}
