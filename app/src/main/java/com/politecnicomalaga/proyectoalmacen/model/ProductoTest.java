package model;



import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Random;

/**
 * The test class ProductoTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class ProductoTest
{
    private Random r = new Random();
    private char randomChar(){
        return (char) (r.nextInt(256));
    }
    private char randomCharAZ(){
        return (char) (r.nextInt(90 - 65) + 65); //Va de la A a la Z
    }
    private int randomIntSin0() {
        int numeroRandom;
        do {
            numeroRandom = r.nextInt();
        } while (numeroRandom == 0);
        return numeroRandom;
    }
    private double randomDoubleSin0(){
        double numeroRandom;
        do {
            numeroRandom = r.nextDouble();
        } while (numeroRandom == 0);
        return numeroRandom;
    }
    private int randomInt() {
        return r.nextInt();
    }
    private double randomDouble(){
        return r.nextDouble();
    }
    
    /**
     * Default constructor for test class ProductoTest
     */
    public ProductoTest()
    {
        //Antes de cada test inicializa lo que le pidamos aqui
        //Producto p = new Producto("BASE123", "Producto Inicial", 10.0, 5);
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        //Tras terminar cada test hace lo que le pidamos aqui por ejemplop borrar el objeto creado
        //Producto p = null;
    }
    
    //Podemos probar todos, pero no sabremos cual es el que falla
    @Test
    public void testGetters(){
        
    }
    
    //Si probamos de uno en uno si sabemos cual es el que falla
    //Ademas podemos ver mas facilmente que es loq ue falta por probar
    @Test
    public void testGetCodigo(){
        //Triple AAA
        String codigoProducto;
        String descripcion = "Portatil cecoteq";
        double precio = 30.5;
        int stock = 11;
        
        
        for (int i = 0; i<1000;i++){
            //Add- Activar, añadir, crear
            codigoProducto = "";
                        
            int numeroRango = r.nextInt(16 - 8 + 1) + 8; //Generamos un codigo entre 8 - 16
            for (int j = 0; j < numeroRango ;j++){ //Creamos un codigo random entre 8 y 16 caracteres
                codigoProducto+=randomCharAZ();
            }
            
            Producto p = new Producto(codigoProducto, descripcion, precio, stock);
            
            //Act - Actuar, llamar metodo, funcion, asignar...
            String ResultadoCodigoProducto = p.getCodigoProducto();
            
            //Assert - Asegurar, aseguro que el resultado es el esperado...
            assertTrue(codigoProducto.equals(ResultadoCodigoProducto));
        }
    }
    @Test
    public void testGetDescripcion(){
        //Triple AAA
        String codigoProducto = "ABCD1234";
        String descripcion = "";
        double precio = 30.5;
        int stock = 11;
        
        
        for (int i = 0; i<100;i++){
            //Add- Activar, añadir, crear
            descripcion+=randomCharAZ(); //Vamos sumando a la descripcion random char para que no nos timen
            
            Producto p = new Producto(codigoProducto, descripcion, precio, stock);
            
            //Act - Actuar, llamar metodo, funcion, asignar...
            String ResultadoDescripcion = p.getDescripcion();
            
            //Assert - Asegurar, aseguro que el resultado es el esperado...
            assertTrue(descripcion.equals(ResultadoDescripcion));
        }
    }
    @Test
    public void testGetPrecio(){
        //Triple AAA
        String codigoProducto = "DHDO823JF8J";
        String descripcion = "Portatil cecoteq";
        double precio;
        int stock = 11;
        
        for (int i = 0; i<1000;i++){
            precio = Math.abs(randomDoubleSin0()); //Numero random no permitimos negativos ni 0
            
            Producto p = new Producto(codigoProducto, descripcion, precio, stock);
            
            double precioResultado = p.getPrecio();
            
            assertTrue(precioResultado==precio); 
            assertTrue(precioResultado>=0);
        }
    }
    @Test
    public void testGetStock(){
        //Triple AAA
        String codigoProducto = "ABCD1234";
        String descripcion = "Portatil cecoteq";
        double precio = 30.5;
        int stock;
        
        
        for (int i = 0; i<1000;i++){
            //Add- Activar, añadir, crear
            stock=Math.abs(randomIntSin0());//Numero random no permitimos negativos ni 0
            
            Producto p = new Producto(codigoProducto, descripcion, precio, stock);
            
            //Act - Actuar, llamar metodo, funcion, asignar...
            int ResultadoStock = p.getStock();
            
            //Assert - Asegurar, aseguro que el resultado es el esperado...
            assertTrue(stock==ResultadoStock);
            assertTrue(ResultadoStock>0);
        }
    }
    
    
    @Test
    public void testSetCodigo(){
        //Triple AAA
        
        // Add / Arrange
        String codigoProducto = "JESUCRISTO1234";
        String descripcion = "un ser mistico dificl de encontrar";
        double precio = 10.5;
        int stock = 1;
        
        Producto p = new Producto(codigoProducto, descripcion, precio, stock); //Creamos el producto nuevo
        for (int i = 0; i<1000; i++){
            // Act
            String codigoProductoNuevo = "";
            int rangoProducto = r.nextInt(16 - 8 + 1) + 8; //Generamos un numero random entre 8 y 16
            for (int j = 0; j < rangoProducto ;j++){ //Creamos un codigo random entre 8 y 16 caracteres usando nuestro rango de arriba
                codigoProductoNuevo+=randomCharAZ();
            }
            
            p.setCodigoProducto(codigoProductoNuevo);
            
            String codigoProductoObtenido = p.getCodigoProducto();
            
            // Assert
            
            assertTrue(codigoProductoNuevo.equals(codigoProductoObtenido));
        }
        
    }
    @Test
    public void testSetDescripcion(){
        //Triple AAA 
        
        //Add / Arrange
        String codigoProducto = "JESUCRISTO1234";
        String descripcion = "un ser mistico dificl de encontrar";
        double precio = 10.5;
        int stock = 1;
        
        Producto p = new Producto(codigoProducto, descripcion, precio, stock); //Creamos el producto nuevo
        
        for (int i = 0; i < 10; i++){
            //Act
            
            //generamos una descripcion random entre 0 y 300 caracteres, mas que suficiente
            String descripcionNueva = "";
            int rangoDescripcion = r.nextInt(300) + 1; 
            for (int j = 0; j < rangoDescripcion ;j++){
                descripcionNueva+=randomChar();
            }
            
            p.setDescripcion(descripcionNueva);
            
            String descripcionObtenida = p.getDescripcion();
            
            //Assert
            assertTrue(descripcionNueva.equals(descripcionObtenida));
        }
    }
    @Test
    public void testSetPrecio(){
        //Triple AAA 
        
        //Add / Arrange
        String codigoProducto = "JESUCRISTO1234";
        String descripcion = "un ser mistico dificl de encontrar";
        double precio = 10.5;
        int stock = 1;
        
        Producto p = new Producto(codigoProducto, descripcion, precio, stock); //Creamos el producto nuevo
        
        for (int i = 0; i < 100; i++){
            //Act
            double nuevoPrecio = Math.abs(randomDoubleSin0()); //Generamos un precio random nuevo, positivo y no 0
            p.setPrecio(nuevoPrecio);
            
            double precioObtenido = p.getPrecio();
            
            //Assert
            assertTrue(nuevoPrecio==precioObtenido);
        }
    }
    @Test
    public void testSetStock(){
        //Add / Arrange
        String codigoProducto = "JESUCRISTO1234";
        String descripcion = "un ser mistico dificl de encontrar";
        double precio = 10.5;
        int stock = 1;
        
        Producto p = new Producto(codigoProducto, descripcion, precio, stock); //Creamos el producto nuevo
        
        
        for (int i = 0; i < 1000; i++){
            //Act
            int stockNuevo = Math.abs(randomIntSin0());
            p.setStock(stockNuevo);
            
            int stockObtenido = p.getStock();
            
            //Assert
            assertTrue(stockNuevo==stockObtenido);
        }
    }
}
