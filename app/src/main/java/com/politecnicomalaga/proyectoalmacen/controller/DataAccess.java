package controller;
import model.*;
import java.util.List;
import java.util.ArrayList;

public class DataAccess
{
    private static final String data = "Clase=Producto;codigoProducto=TECL5678X;descripcion=Teclado mecánico RGB con switches rojos;precio=89.99;stock=45\n" +
        "Clase=Producto;codigoProducto=RATN9012K;descripcion=Ratón inalámbrico ergonómico con 5 botones;precio=34.50;stock=67\n"+
        "Clase=Producto;codigoProducto=AURC3456L;descripcion=Auriculaes inalámbricos con cancelación de ruido;precio=129.99;stock=23\n"+
        "Clase=Producto;codigoProducto=WEBC7890P;descripcion=Webcam Full HD 1080p con micrófono integrado;precio=59.90;stock=32\n"+
        "Clase=Producto;codigoProducto=HUBB2345M;descripcion=Hub USB 3.0 de 4 puertos con alimentación;precio=24.75;stock=56\n"+
        "Clase=Producto;codigoProducto=DISK1234R;descripcion=Disco duro externo 1TB USB-C resistente al agua;precio=79.99;stock=18\n"+
        "Clase=Producto;codigoProducto=MONS4567T;descripcion=Monitor portátil 15.6 pulgadas Full HD;precio=189.50;stock=12\n"+
        "Clase=Producto;codigoProducto=PADT8901Y;descripcion=Alfombrilla de ratón XXL con base de goma;precio=19.99;stock=89\n"+
        "Clase=Producto;codigoProducto=MICR2345U;descripcion=Micrófono USB de condensador para streaming;precio=65.30;stock=27\n"+
        "Clase=Producto;codigoProducto=COOL6789I;descripcion=Base refrigeradora para portátil con 3 ventiladores;precio=29.95;stock=41\n"+
        "Clase=Producto;codigoProducto=CARG5678O;descripcion=Cargador rápido USB-C 65W con 2 puertos;precio=45.80;stock=34\n"+
        "Clase=Producto;codigoProducto=LAPD9012P;descripcion=Soporte ajustable para portátil de aluminio;precio=39.99;stock=50\n"+
        "Clase=ProductoPerecedero;codigoProducto=INK55665F;descripcion=Toner b/w genérico HP 8750;precio=79.99;stock=18;fechaCaducidad=20260713\n" +
        "Clase=ProductoPerecedero;codigoProducto=LECH1234A;descripcion=Leche entera brick 1L;precio=1.20;stock=50;fechaCaducidad=20250101\n";
    
    public static List<Producto> loadData() {
        List<Producto> listaProductosBase = new ArrayList<Producto>();
        //Hay que importar los datos externos a local al inicializar el programa
        //Separamos por lineas los datos
        String[] lineas = data.split("\n");
        for (String linea : lineas) {
            String clase = linea.split(";")[0].split("=")[1]; // Nos quedamos con la primera parte para verificar que tipo de producto es
            
            //En un función del tipo llamamos a su respectivo cargarDesdeCSVPlus que mete cada linea en el modelo.
            switch (clase) {
                case "ProductoPerecedero":
                    listaProductosBase.add(ProductoPerecedero.cargarDesdeCSVPlus(linea));
                    break;
                case "Producto":
                    listaProductosBase.add(Producto.cargarDesdeCSVPlus(linea));
                    break;
                default:
                    System.out.println("Tipo de producto desconocido: " + clase);
                    break;
            }
        }
        return listaProductosBase;
    }
}