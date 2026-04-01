package model;
import java.util.Comparator;

//Clase producto

public class Producto implements Comparable<Producto>{
    //Atributos
    private String codigoProducto;
    private String descripcion;
    private double precio;
    private int stock;
    
    // Constructor
    public Producto(String codigoProducto, String descripcion, double precio, int stock) {
        setCodigoProducto(codigoProducto); // Usamos el setter para validar
        this.descripcion = descripcion;
        setPrecio(precio); // Usamos el setter para validar
        
        if (precio > 0){
            this.precio = precio;
        } else this.precio =-precio;
        setStock(stock); // Usamos el setter para validar
    }

    // Getters y Setters
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
        if (precio>=0.0) this.precio = precio; //No podemos tener precios negativos
        
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
    
    // Mostrar la información del producto. CSV Plus
    @Override
    public String toString() {
        return "Clase=" + getClase() + ";" + //Para que en el Super de subclases no nos diga que es un prodcuto si no la clase que es.
                "codigoProducto=" + codigoProducto + ";" +
                "descripcion=" + descripcion + ";" +
                "precio=" + precio + ";" +
                "stock=" + stock;
    }
    public String getClase() { //Las subclases sobreescriben este método para identificar que tipo de clase son en el toString 
        return "Producto";
    }
    
    //Para obtener si esta caduca o no, nunca caducan asi que false siempre
    public boolean getCaducado(){
        return false; 
    }

    //Importación de datos a local desde siguiendo las reglas de nuestro CSVPlus 
    public static Producto cargarDesdeCSVPlus(String data) {
        //Nos llegan las lineas de datos de tipo Producto
        String[] campos = data.split(";");
        
        //Spliteamos por "=" y nos quedamos con el segundo campo el primero no nos interesa.
        //,2 splitea en solo 2 partes dividiendo por el primer = que encuentre, 
        // es decir si hay un = por lo que sea en algun sitio por ejemplo la descripción USB=C, no splitea ni rompe nada
        String[] campoCodigo = campos[1].split("=", 2); 
        String codigoProducto = campoCodigo[1];
        
        String[] campoDescripcion = campos[2].split("=", 2);
        String descripcion = campoDescripcion[1];
        
        String[] campoPrecio = campos[3].split("=", 2);
        double precio = Double.parseDouble(campoPrecio[1]);
        
        String[] campoStock = campos[4].split("=", 2);
        int stock = Integer.parseInt(campoStock[1]);
        
        return new Producto(codigoProducto, descripcion, precio, stock);
    }
    
    @Override
    public int compareTo(Producto otro){ // Ordenacion por descripcion
        String producto = this.getDescripcion();
        String otroProducto = otro.getDescripcion();
        
        return producto.compareTo(otroProducto);
    }
}
