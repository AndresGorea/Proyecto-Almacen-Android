package model;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;


/**
 * Write a description of class ProductoPerecedero here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ProductoPerecedero extends Producto
{
    private String fechaCaducidad; // Formato AAAAMMDD según enunciado
    
    //Constructor
    public ProductoPerecedero(String codigoProducto, String descripcion, double precio, int stock, String fechaCaducidad) {
        super(codigoProducto, descripcion, precio, stock);
        this.fechaCaducidad = fechaCaducidad;
    }
    
    //Getters y Setters
    public String getFechaCaducidad() {
        return fechaCaducidad;
    }
    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }
    
    //Métodos
    //To string 
    @Override
    public String toString() {
        return super.toString() + ";" + "fechaCaducidad=" + fechaCaducidad; //Usamos el del padre y sumamos el atributo nuevo
    }
    @Override //Necesario para reutilizar el to string
    public String getClase() {
        return "ProductoPerecedero";
    }
    
    //Verificar si esta caducado
    @Override
    public boolean getCaducado() {
        //En lugar de dar nosotros una fecha usaremos la fecha de nuestro sistema, mas comodo
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyyMMdd"); // Definimos el patrón de formato AAAAMMDD
        
        String hoy = LocalDate.now().format(formateador);
        
        //Si la fecha de caducidad es mas grande que la fecha de hoy es decir nos da negativo, return false, caso contrario esta caducado true.
        return this.fechaCaducidad.compareTo(hoy) < 0; 
    }
    
    //Importación de datos a local desde siguiendo las reglas de nuestro CSVPlus 
    public static ProductoPerecedero cargarDesdeCSVPlus(String data) {
        //Nos llegan las lineas de datos de tipo ProductoPerecedero
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
        
        String[] campoFecha = campos[5].split("=", 2);
        String fechaCaducidad = campoFecha[1];
        
        return new ProductoPerecedero(codigoProducto, descripcion, precio, stock, fechaCaducidad);
    }
}