package com.politecnicomalaga.proyectoalmacen.view;
import java.util.Scanner;
import com.politecnicomalaga.proyectoalmacen.controller.*;


/**
 * Descripción del examen:
 * 
 * Hay que terminar un prototipo para gestión del stock de productos de un almacén
 * El proyecto tiene una serie de clases incompletas añadidas, y tendrás que crear también 
 * una clase ProductoPerecedero que será un producto que tendrá fecha de caducidad (AAAAMMDD)
 * Tu objetivo: implementar el prototipo hasta hacer funcionales todas las opciones del menú, 
 * teniendo en cuenta que:
 *  - El controlador tendrá una colección List de productos, donde estarán todos los productos
 *  activos
 *  - El controlador tendrá otra colección List de productos con los productos retirados (por 
 *  obsoletos, caducados, o lo que sea)
 *  - Cuando añadimos un producto puede ser normal o perecedero
 *  - Cuando modificamos el stock de un producto, se solicita el código del producto y el stock
 *  - Cuando queremos retirar un producto, pedimos su código
 *  - Cuando queremos mostrar productos entre dos precios pedimos el mínimo y el máximo precio
 *  que usaremos para obtener de la lista de productos activos, los que cumplan con el filtro
 *  - En las demás opciones realizamos lo solicitado sin necesidad de obtener ningún dato por 
 *  teclado
 *  - El controlador es de tipo Singleton
 *  - La vista NO usa objetos ni clases del modelo. El proyecto es MVC con capas puras
 *  - Hay que implementar los listados como tablas, con una cabecera y los datos en forma de filas
 *  pero sin tener la información de los atributos. Ejemplo:
 *  Clase,Código Producto,Descripción,Precio,Stock,Caducidad
    Producto,TECL5678X,Teclado mecánico RGB con switches rojos,89.99,45
    Producto,RATN9012K,Ratón inalámbrico ergonómico con 5 botones,34.50,67
    Producto,AURC3456L,Auriculares inalámbricos con cancelación de ruido,129.99,23
    Producto,WEBC7890P,Webcam Full HD 1080p con micrófono integrado,59.90,32
    Producto,HUBB2345M,Hub USB 3.0 de 4 puertos con alimentación,24.75,56
    Producto,INK55665F,Toner b/w genérico HP 8750,79.99,18,20260713
    
    - En el ejemplo anterior, la última línea es una producto perecedero
    
 *  - Podéis reutilizar código del Taller mecánico, el que queráis
 *  - El examen dura unas 2 horas y media (se cerrará la entrega a las 10:55)
 *  
 *  - Antes de esa hora, hay que entregar el proyecto en ZIP en la classroom
 *  - Después, se puede seguir trabajando en el proyecto, que completo, se defenderá
 *  a la vuelta de semana blanca.
 *  
 *  Evaluación: 
 *  1ª Oportunidad: ZIP entregado en la classroom
 *  2ª Oportunidad: Repositorio depués de semana blanca
 *  
 *  Cada opción de menú: 1.25 puntos. Total: 10p. Aprobar: 5p.
 *  
 */
public class Main
{
    // Clase principal de la vista
    private static Scanner sc = new Scanner(System.in);
    public static Controlador c = Controlador.getSingleton();
    
    //Main
    public static void main(String[] args) {
        //Poner aquí el bucle para ejecutar mostrar menú, escoger opción y ejecutarla la opción
        //Hasta pulsar opción 0 que es salir
        int opcion;
        
        do{
            mostrarMenu();
            
            opcion = getEntradaInt("Elige una opción");
            realizarSeleccion(opcion);
        }
        while(opcion != 0 );
    }
    public static void mostrarMenu() {
        System.out.println("--------------------------------------------------------");
        System.out.println("-                                                      -");
        System.out.println("- Menú del proyecto: Almacén de productos              -");
        System.out.println("-                                                      -");
        System.out.println("- 0. Salir                                             -");
        System.out.println("- 1. Añadir nuevo producto                             -");
        System.out.println("- 2. Añadir/quitar stock a un producto                 -");
        System.out.println("- 3. Listar todo                                       -");
        System.out.println("- 4. Retirar un producto y su stock                    -");
        System.out.println("- 5. Mostrar productos con stock 0                     -");
        System.out.println("- 6. Mostrar productos caducados                       -");
        System.out.println("- 7. Mostrar productos entre dos precios               -");
        System.out.println("- 8. Mostrar productos retirados                       -");
        System.out.println("- 9. Comparar Productos                                -");
        System.out.println("-                                                      -");
        System.out.println("-                                                      -");
        System.out.println("--------------------------------------------------------");
    }
    //Switch de la selección
    public static void realizarSeleccion(int opcion){
        switch(opcion){
            case 0: break;
            case 1: addProducto();
                break;
            case 2: cambiarStock();
                break;
            case 3: listarTodo();
                break;
            case 4: retirarProducto();
                break;
            case 5: listarProductosSinStock();
                break;
            case 6: listarProductosCaducados();
                break;
            case 7: listarProductosEnRango();
                break;
            case 8: listarProductosRetirados();
                break;
            default: System.out.println("Opción no valida");
                break;
        }
    }
    //Añadir producto
    public static void addProducto (){
        //Submenu de añadir prodcuto
        String menu = 
        "----------------------------------\n" +
        "- Seleccione el tipo de producto -\n" +
        "-                                -\n" +
        "- 1. Producto Normal             -\n" +
        "- 2. Producto Perecedero         -\n" +
        "-                                -\n" +
        "- 0. Volver al menú principal    -\n" +
        "----------------------------------";
        //Variables necesarias para recoger datos
        String codigoProducto, descripcion, precio, stock, productoCSV;
        int opcion;
        //Bucle 
        do{
            System.out.println(menu);
            opcion = getEntradaInt("Elige una opción");
            switch (opcion) {
                case 1: 
                    procesarProductoNormal();
                    break;
                case 2: 
                    procesarProductoPerecedero();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }
        while(opcion != 0 );
    }
    //Unificmaos los datos comunes para no repetir 
    private static String pedirDatosBase() {
        System.out.println("");
        String codigo = getEntrada("Introduce el codigo del producto");
        String descripcion = getEntrada("Introduce la descripción");
        String precio = getEntrada("Introduce el precio del producto"); 
        String stock = getEntrada("Introduce el stock del producto");
        
        return "codigoProducto=" + codigo + 
               ";descripcion=" + descripcion + 
               ";precio=" + precio + 
               ";stock=" + stock;
    }
    private static void procesarProductoNormal() { //En caso de ser un producto normal no debemos de pedir nada mas
        System.out.println("");
        String baseCSV = pedirDatosBase();
        String productoCSV = "Clase=Producto;" + baseCSV; //Especificamos la clase producto
            
        if (c.addProducto(productoCSV)) System.out.println("Producto normal añadido con éxito");
        else System.out.println("Error al añadir: el producto ya existe");
        
    }
    private static void procesarProductoPerecedero() { //Al ser perecedero solo pedimos un dato mas
        System.out.println("");
        String baseCSV = pedirDatosBase();
        String fechaCaducidad = getEntrada("Introduce la fecha de caducidad del producto"); //Pedimos la fecha
        
        String productoCSV = "Clase=ProductoPerecedero;" + baseCSV + ";fechaCaducidad=" + fechaCaducidad; //Especificamos la clase prouducto y al final la fecha
            
        if (c.addProductoPerecedero(productoCSV)) System.out.println("Producto perecedero añadido con éxito");
        else System.out.println("Error al añadir: el producto ya existe");
    }
    //Añadir/quitar stock a un producto 
    public static void cambiarStock (){ 
        System.out.println("");
        //Cuando modificamos el stock de un producto, se solicita el código del producto y el stock
        String codigoProducto = getEntrada("Introduce el codigo del producto que quieres actualizar el stock");
        String nuevoStock = getEntrada("Introduce cuantas unidades nuevas de stock quieres añadir");
        
        String datosActualizacion = codigoProducto + ";" + nuevoStock; //Lo enviamos todo junto pero con algun separador para poder desmunazarlo
        
        if (c.cambiarStock(datosActualizacion)) System.out.println ("Stock añadido con exito"); 
        else System.out.println("Error producto no encontrado");
        
    }
    public static void listarTodo (){
        //
        int opcion;
        String menu = 
        "--------------------------------------\n" +
        "- Seleccione el tipo de listado      -\n" +
        "-                                    -\n" +
        "- 1. Listar Ordenado por Descripcion -\n" +
        "- 2. Listar Ordenado por Precio      -\n" +
        "- 3. Listar Ordenado por Stock       -\n" +
        "- 4. Listar Ordenado por Codigo      -\n" +
        "-                                    -\n" +
        "- 0. Volver al menú principal        -\n" +
        "--------------------------------------";
        
        do{
            System.out.println(menu);
            opcion = getEntradaInt("Elige una opción");
            
            imprimirDatos(c.listarProductos(opcion));
        } while (opcion != 0);
    }
    public static void retirarProducto (){
        //Retirar un producto y su stock
        
        String codigoProducto = getEntrada("Introduce el codigo del producto que quieres eliminar, si te has equivocado y quieres salir pulsa 0");
        
        if (codigoProducto.equals("0")){ //Preguntamos por si acaso a entrado sin querer
           System.out.println("Abortado saliendo...");
           return; 
        }
        
        String confirmar = getEntrada("¿Estás seguro de que quieres eliminar el producto " + codigoProducto + "? (S/N)"); //Confirmación de quieres borrar el producto
        if (confirmar.equalsIgnoreCase("S")){
            if (c.retirarProducto(codigoProducto)) System.out.println("Producto con codigo " + codigoProducto + " Eliminado con exito");
            else System.out.println("Producto con codigo  " + codigoProducto + " no existe");
        }
        else System.out.println("Abortado saliendo...");
        
    }
    public static void listarProductosSinStock (){
        //Mostrar productos con stock 0  
        imprimirDatos(c.listarProductosSinStock());
    }
    public static void listarProductosCaducados (){
        //Mostrar productos caducados 
        imprimirDatos(c.listarProductosCaducados());
    }
    public static void listarProductosEnRango (){
        //7. Mostrar productos entre dos precios 
        
        //Es necesario pedir el rango para poder listarlo y se lo pasasmos al controlador
        try {
            String rangoMin = getEntrada("Introduce el rango minimo");
            String rangoMax = getEntrada("Introduce el rango maximo");
            
            String resultado = c.listarProductosEnRango(rangoMin,rangoMax);
            
            if (resultado == null || resultado.isEmpty()) System.out.println("No existen productos en ese rango");
            else imprimirDatos(resultado);

        }
         catch (NumberFormatException e) {
            System.out.println("Error: introduce valores numéricos para el rango de precios.");
        }
    }
    public static void listarProductosRetirados (){
        //Mostrar productos retirados  
        imprimirDatos(c.listarProductosRetirados());
    }
    //Imprimimos el csv y su cabecera todo bien bonito
    public static void imprimirDatos(String datos){
        if (datos == null || datos.isEmpty()){//Si esta vacio directamente no hacemos nada
            System.out.println("No hay datos para mostrar.");
        }
        else{
            String datosLimpios= ""; //Para mostrar las lineas limpias
            System.out.println("Clase,Código Producto,Descripción,Precio,Stock,Caducidad"); //La cabecera que es siempre la misma no cambia
            
            String[] lineas = datos.split("\n"); // Partimos cada linea
            for (String l : lineas) {
                String lineaLimpia= ""; //En cada vuelta la linea la limpiamos
                String[] trozos = l.split(";"); //dividimos por ;
                
                for (int i = 0; i < trozos.length; i++){ //Luego dentro de los trozos dividimos por =
                    String[] partes = trozos[i].split("=", 2); //Protección por si recibimos algun dato con "=" como contenido
                    lineaLimpia += partes[1]; //Nos quedamos solo con la segunda parte del split
                    
                    if (i < trozos.length - 1) { //Añadimos una coma a no ser que sea el ultimo de la fila
                        lineaLimpia += ",";
                    }
                }
                
                datosLimpios += lineaLimpia + "\n"; //La linea limpiada la guardamos en datos limpios y un salto
            }
            System.out.println(datosLimpios); //Al terminar mostramos todos los datos limpios
        }
    }
    
    //Para facilitar mostrar un mensaje y coger la salida
    public static String getEntrada(String texto){
        System.out.print(texto + ": ");
        return leerTexto("");
    }
    public static int getEntradaInt(String texto){
        System.out.print(texto + ": ");
        return leerEntero("");
    }
    
    //Funciones Leer
    //Leemos el texto introducido con el escaner
    public static int leerEntero(String info){
        System.out.print(info);
        int resultado = -1;
        boolean valido = false;
        
        do {
            try {
                String leido = sc.nextLine();
                resultado = Integer.parseInt(leido);
                valido = true;
            } catch (NumberFormatException nfe){
                System.out.print("Entrada no válida, introduce un número: ");
            }
        } while (!valido);

        return resultado;        
    }
    public static String leerTexto(String mensaje){
        System.out.print(mensaje);
        return sc.nextLine();
    }
}