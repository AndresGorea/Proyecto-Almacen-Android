package model;
import java.time.format.DateTimeFormatter;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

/**
 * The test class ProductoPerecederoTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class ProductoPerecederoTest
{
    private Random r = new Random();
    
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("yyyyMMdd");
    
    private String codigo = "ABCD1234";
    private String descripcion = "Leche entera";
    private double precio = 1.50;
    private int Stock = 10;
    private String FechaCaducidad = "20991231";
    
    private ProductoPerecedero p;
    
    
    /**
     * Default constructor for test class ProductoPerecederoTest
     */
    public ProductoPerecederoTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        p = new ProductoPerecedero(codigo, descripcion, precio, Stock, FechaCaducidad);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
        p = null;
    }    
    
    //Test Getters, heredados nuevos
    @Test
    public void testGetFechaCaducidad() {
        for (int i = 0; i < 1000; i++) {
            //Add 
            int anyo = 2000 + r.nextInt(100); //Fechas random
            int mes  = 1 + r.nextInt(12);
            int dia  = 1 + r.nextInt(28); //hasta 28 por si sale febrero y no complicarnos de mas

            String fechaAleatoria = String.format("%04d%02d%02d", anyo, mes, dia);
            
            //Act
            p = new ProductoPerecedero(codigo, descripcion, precio, Stock, fechaAleatoria);
            
            String fechaObtenida = p.getFechaCaducidad();
            
            //Assert
            assertTrue(fechaAleatoria.equals(fechaObtenida));
        }
    }
    
    
    //Test Setters, heredados nuevos
    @Test
    public void testSetFechaCaducidad() {
        for (int i = 0; i < 1000; i++) {
            //Add 
            int anyo = 2000 + r.nextInt(100); //Fechas random
            int mes  = 1 + r.nextInt(12);
            int dia  = 1 + r.nextInt(28); //hasta 28 por si sale febrero y no complicarnos de mas

            String fechaAleatoria = String.format("%04d%02d%02d", anyo, mes, dia);
            
            //Act
            p.setFechaCaducidad(fechaAleatoria);

            String fechaObtenida = p.getFechaCaducidad();
            
            //Assert
            assertTrue(fechaAleatoria.equals(fechaObtenida));
        }
    }
    
    
    //Test metodos 
    @Test
    public void testGetCaducado_NoCaducado() {
        
    }
    @Test
    public void testGetCaducado_SiCaducado() {
        
    }

    
    @Test
    public void testToString() {
        
    }
    
    
    @Test
    public void testGetClase() {
        
    }
    
    @Test
    public void testCargarDesdeCSVPlus() {
        
    }
    
}
