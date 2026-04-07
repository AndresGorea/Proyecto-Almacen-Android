package com.politecnicomalaga.proyectoalmacen.controller;
import com.politecnicomalaga.proyectoalmacen.model.*;
import java.util.List;
import java.util.ArrayList;


public class Controlador2
{
    //  instance variables 
    
    //Singleton
    //poner aquí
    private static Controlador2 singleton;
    private List<Producto> misProductos;
    private List<Producto> productosRetirados;
    
    ///Constructor
    private Controlador2()
    {
        this.misProductos = new ArrayList<>();
        this.productosRetirados =  new ArrayList<>();
    }

    ///Controlador
    public static Controlador2 getSingleton(){
        if(singleton == null) singleton = new Controlador2() ;
        return singleton;
    }
    /*
    //Añadir productos
    public boolean addProducto(Map<String, String> producto) {
        return insertarProducto(producto); //Delegamos la tarea de parsear al modelo
    }

    public boolean addProductoPerecedero(Map<String, String> producto) {
        return insertarProducto(ProductoPerecedero.cargarDatos(producto)); //Delegamos la tarea de cargar datos
    }
    private boolean insertarProducto(Map<String, String> producto){ //Bucle de inserción
        for (Producto producto : misProductos) {
            if (producto.getCodigoProducto().equalsIgnoreCase(p.getCodigoProducto())) {
                return false; // Si existe no lo añadimos
            }
        }        
        misProductos.add(p);
        return true;
    }

    public boolean cambiarStock(String datosActualizacion){
        String[] datos = datosActualizacion.split(";");
        
        //Recorremos los productos buscando el producto 
        //Dato 0 es el codigo dato 1 el nuevo stock
        for (Producto p: misProductos){
            if (p.getCodigoProducto().equalsIgnoreCase(datos[0])){
                p.changeStock(Integer.parseInt(datos[1]));
                return true;
            }
        }
        return false; 
    }
    public String listarProductosPorDescripcion(){ //Con la logica de CompareTo Directa en el controlador
        String resultado = "";
        //Nos creamos una lista copiada temporal con la que trabajar para no modificar el modelo.
        List<Producto> copia = new ArrayList<>(misProductos);
        //Ordenamos por descripción utilizando compareTo y un BubbleSort
        for (int i = 0; i < copia.size() - 1; i++) { //Recorremos los prodcutos
            //Comparamos J con J + 1
            for (int j = 0; j < copia.size() - i - 1; j++) { //- i para no comparar nuevamente los ultimos
                
                Producto pActual = copia.get(j);
                Producto pSiguiente = copia.get(j+1);
                
                if (pActual.getDescripcion().compareToIgnoreCase(pSiguiente.getDescripcion()) > 0) {
                    //Realizamos el cambio, si J es mas Grande que J+1
                    Producto temp = copia.get(j);
                    
                    copia.set(j, copia.get(j+1));
                    copia.set(j+1, temp);
                }
            }
        }
        
        for (Producto p: copia){
            resultado += p.toString() + "\n";  
        }
        
        if (resultado.isEmpty()) return null;
        return resultado;
    }

    public String listarProductos(int opcion){
        String resultado = "";
        
        Producto[] arrayOrdenado = misProductos.toArray(new Producto[0]);
        
        //Los .sort necesitan que se les pase un comparador en el caso de las clases
        //Podemos implementarlo en la propia clase un comparable
        //implementarlo en diferentes clases
        //implementarlo de forma anonima 
        
        switch(opcion){
            case 1: java.util.Arrays.sort(arrayOrdenado); //Comparable implementado en la clase
                break;
            case 2: java.util.Arrays.sort(arrayOrdenado, new CompararPorPrecio()); //Comparable implementado en clase externa
                break;
            case 3: java.util.Arrays.sort(arrayOrdenado, new CompararPorStock()); //Comparable implementado en clase externa
                break;
            case 4: java.util.Arrays.sort(arrayOrdenado, new CompararPorCodigo()); //Comparable implementado en clase externa
                break;
            case 5: 
                java.util.Arrays.sort(arrayOrdenado, new Comparator<Producto>() //Comparable clase anonima, implemente interfaz comparable
                { 
                    @Override
                    public int compare (Producto p1, Producto p2) {
                         return p2.getCodigoProducto().compareToIgnoreCase(p1.getCodigoProducto());
                    }
                });  
                break;
            case 6: 
                java.util.Arrays.sort(arrayOrdenado, new Comparator<Producto>() //Comparable clase anonima, implemente interfaz comparable
                { 
                    @Override
                    public int compare (Producto p1, Producto p2) { 
                        double datoA = p1.getPrecio();
                        double datoB = p2.getPrecio();
                        return Double.compare(datoB,datoA); //Orden inverso decreciente
                    }
                });
            default: return null;
        }
        
        for (Producto p: arrayOrdenado){
            resultado += p.toString() + "\n";  
        }
        
        if (resultado.isEmpty()) return null;
        return resultado;
    }
    
    public boolean retirarProducto(String codigoProducto){
        for (Producto p: misProductos){
            if(p.getCodigoProducto().equalsIgnoreCase(codigoProducto)){
                misProductos.remove(p); //Lo quitamos de lista de activos 
                productosRetirados.add(p); //Lo añadimos a la lista de retirados
                return true;
            }
        }
        return false;
    }
    public String listarProductosSinStock(){
        String resultado = "";
        
        for (Producto p: misProductos){ //Bucle buscando Stock 0 y lo convertimos con to string csv para mostrar 
            if (p.getStock() == 0) resultado += p.toString() + "\n";
        }
        
        if (resultado.isEmpty()) return null;
        return resultado;
        
    }
    public String listarProductosCaducados(){
        String resultado = "";
        
        for (Producto p: misProductos){ //Bucle buscando caducado y lo convertimos con to string csv para mostrar 
            if (p.getCaducado()) resultado += p.toString() + "\n";
        }
        
        if (resultado.isEmpty()) return null;
        return resultado;
    }
    public String listarProductosEnRango(String rangoMin, String rangoMax){
        String resultado = "";
        
        double min = Double.parseDouble(rangoMin);
        double max = Double.parseDouble(rangoMax);
        
        for (Producto p: misProductos){ //Bucle buscando en rango y lo convertimos con to string csv para mostrar
            if (p.getPrecio() >= min && p.getPrecio() <= max){ 
                resultado += p.toString() + "\n";
            }
        }
        
        if (resultado.isEmpty()) return null;
        return resultado;
    }
    public String listarProductosRetirados(){
        String resultado = "";
        for (Producto p: productosRetirados){ //Bucle buscando en la lista de retirados y lo convertimos con to string csv para mostrar 
            resultado += p.toString() + "\n";
        }
        if (resultado.isEmpty()) return null;
        return resultado;
    }
    
    public String productosToString(){
        String resultado = "";
        for (Producto p: misProductos){ //Bucle buscando en la lista de retirados y lo convertimos con to string csv para mostrar 
            resultado += p.toString() + "\n";
        }
        
        
        Stream<Producto> miStream = misProductos.stream(); //Stream, api para gestionar colecciones.
        
        Stream<String> miListaStringsCsv = miStream.map((Producto producto) -> producto.toString());
        
        
        if (resultado.isEmpty()) return null;
        return resultado;
    }    
    
    public String productosToStringReduce(){
        String resultado = "";
       
        
        
        Stream<Producto> miStream = misProductos.stream(); //Stream, api para gestionar colecciones.

        Stream<String> miListaStringsCsv = miStream.map((Producto producto) -> producto.toString());
        
        List<String> listaEnTexto = miListaStringsCsv.collect(Collectors.toList());
        
        String result = miListaStringsCsv.reduce("",(dato1,dato2)-> dato2 + ";" + dato1);
 
        //Compacto
        List <String> miListaCSV = misProductos.stream()
        .map((Producto producto) -> producto.toString())
        .collect(Collectors.toList());
        
        //Ejemplo reduce numeros
        
        List<Integer> listaNumero = List.of(3,4,5,6);
        
        int suma = listaNumero.stream().reduce(0,(dato1,dato2)-> dato2 + dato1); //En caso de las sumas no importa el orden obviamente 
        //En string y en restas divisiones etc si...
            
        // Optional <String>, es un tipo de variable cuyo valor asignado puede ser null
        //Por ejemplo realizamos un reduce del valor mas grande una lista vacia, no nos va a devolver 
        //Ningun valor por lo que esta vacio
        
        //if.Present en combinacion de optinal condicional verificando si es null o no
        
        
        
        if (resultado.isEmpty()) return null;
        return resultado;
    }
    */
}


