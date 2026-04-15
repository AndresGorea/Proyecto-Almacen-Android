package com.politecnicomalaga.proyectoalmacen.view;

import com.politecnicomalaga.proyectoalmacen.controller.*;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.politecnicomalaga.proyectoalmacen.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static Controlador c = Controlador.getSingleton();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mainMenu();
    }

    /**
     * Menu:
     *
     * 1. Añadir Producto
     *     Tipo de Producto (Lista)
     *     Código Válido
     *     Descripción
     *     Precio
     *     Stock
     *
     * 2. Modificar un Producto
     *     Introducir el código válido y buscar un producto
     *
     *     a. Modificar datos
     *         Tipo de producto, (Puede haber perdida de datos / Confirmación)
     *         Un código válido Nuevo
     *         Descripción nueva
     *         Stock total (Redundante), es necesario?, mejor dejarlo por si se quiere modificar varios datos.
     *
     *     b. Modificar Stock
     *         Añadir Stock
     *         Restar Stock
     *         Cambiar Stock total.
     *
     *     c. Retirar Producto y su Stock (Más una Confirmación)
     *
     * 3. Listar Productos (Seriá interesante poder exportar a json todos cuando muestre la salida)
     *
     *     a. Listar Todos los productos
     *     b. Mostrar Productos sin Stock
     *     c. Mostrar productos caducados
     *     d. Mostrar productos en rango de precio
     *     e. Mostrar productos retirados
     *     f. Comparar Productos
     *
     * 4. Gestión de Datos
     *     a. Importar Datos desde Fichero Json
     *     b. Exportar Datos a Fichero Json
     */

    public void mainMenu() {
        setContentView(R.layout.activity_main); //Mostramos el activity correspondiente

        findViewById(R.id.btAnadir).setOnClickListener(v -> menuAnadir());
        findViewById(R.id.btModificar).setOnClickListener(v -> menuModificar());
        findViewById(R.id.btListar).setOnClickListener(v-> menuListar());
        findViewById(R.id.btGestionarDatos).setOnClickListener(v -> menuGestionarDatos());
    }

    public void gestionarFormularios(boolean esModificacion, String codigoRecibido) {
        setContentView(R.layout.activity_anadir); //Mostramos el activity correspondiente
        findViewById(R.id.btVolver).setOnClickListener(v -> mainMenu()); //Volver

        if (esModificacion){ //Si es uin modificación usamos la misma plantilla cambiando alunos datos
            ((TextView) findViewById(R.id.tvTitulo)).setText("Modificar Producto");
            ((Button) findViewById(R.id.btAnadir)).setText("Guardar Cambios");

            EditText etCodigo = findViewById(R.id.etCodigo);
            etCodigo.setText(codigoRecibido); // Rellenamos el código automáticamente
            etCodigo.setEnabled(false); // bloqueamos el campo para no poder tocar el código.

            findViewById(R.id.btVolver).setOnClickListener(v -> menuModificar()); // Que vuelva al menú de modificar
        }

        ///Mostramos las opciones para el spinner
        String[] tipoProducto = {"Normal","Perecedero"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>( // Arrayadapter coge una colección de datos y le pasa cada dato a un list view o spinner para que lo muestren
                this, //Le damos el contexto (esta clase this)
                android.R.layout.simple_spinner_item, //Le damos el recurso al cual le pasamos los datos
                tipoProducto);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Le damos un diseño bonito al desplegable
        ((Spinner) findViewById(R.id.spTipo)).setAdapter(adapter); //Buscamos el spinner y le damos los valores del adapter

        ///Verificamos qué tipo de "sp" hay seleccionado para mostrar o no el etFechaCaducidad
        Spinner spTipo = (Spinner) findViewById(R.id.spTipo);
        EditText etFechaCaducidad = (EditText) findViewById(R.id.etFechaCaducidad);

        spTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Interfaz comprueba el spinner cada vez que se toca
            @Override //Cuando se toca verificamos el valor de tipoP y mostramos o no la fecha
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tipoP = spTipo.getSelectedItem().toString();
                switch (tipoP) {
                    case "Normal":
                        etFechaCaducidad.setVisibility(View.GONE);
                        break;
                    case "Perecedero":
                        etFechaCaducidad.setVisibility(View.VISIBLE);
                        break;
                }
            }
            @Override //Implementa estos dos métodos obligatoriamente, pero este no lo usamos para nada
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /// Almacenamos los datos al pulsar el botón

        findViewById(R.id.btAnadir).setOnClickListener(v -> {
            try {
                //Almacenamos los datos básicos
                String codigo = ((EditText) findViewById(R.id.etCodigo)).getText().toString();
                String descripcion = ((EditText) findViewById(R.id.etDescripcion)).getText().toString();
                Double precioDouble = Double.parseDouble(((EditText) findViewById(R.id.etPrecio)).getText().toString());
                int stock = Integer.parseInt(((EditText) findViewById(R.id.etStock)).getText().toString());
                String tipoP = spTipo.getSelectedItem().toString();

                //Mapas
                HashMap<String, Object> datosProducto = new HashMap<>();

                //Guardamos los atributos en el mapa de atributos
                datosProducto.put("codigo", codigo);
                datosProducto.put("descripcion", descripcion);
                datosProducto.put("precio", precioDouble);
                datosProducto.put("stock", stock);
                datosProducto.put("tipo", tipoP);

                //Otros datos diferentes
                switch (tipoP) { //Utilizamos un switch por si ampliamos los tipos de productos que sea facil
                    case "Normal":
                        //No necesitamos más datos
                        break;
                    case "Perecedero":
                        String fecha = ((EditText) findViewById(R.id.etFechaCaducidad)).getText().toString();
                        datosProducto.put("fecha", fecha);
                        break;
                }

                if (esModificacion){ //Si es una modificación
                    if (c.modificarProducto(datosProducto)){ // Le pasamos los dátos al controlador
                        String resultado = ("Producto módificado con éxito");
                        ((TextView) findViewById(R.id.tvMostrarResultado)).setText(resultado);
                    };
                }
                else {
                    if (c.addProducto(datosProducto)){ // Le pasamos los dátos al controlador
                        String resultado = ("Producto añadido con éxito");
                        ((TextView) findViewById(R.id.tvMostrarResultado)).setText(resultado);
                    };
                }
            }
            catch (NumberFormatException nfe) {
                String resultado = "Error: El precio y el stock deben ser números válidos";
                ((TextView) findViewById(R.id.tvMostrarResultado)).setText(resultado);
            }
            catch (IllegalArgumentException iae) {
                String resultado = ("Error de validación: " + iae.getMessage());
                ((TextView) findViewById(R.id.tvMostrarResultado)).setText(resultado);
            }
            catch (Exception e) {
                String resultado = "Error: " + e.getMessage();
                ((TextView) findViewById(R.id.tvMostrarResultado)).setText(resultado);
            }
        });
    }

    public void menuModificar(){
        setContentView(R.layout.activity_modificar); //Mostramos el activity correspondiente
        findViewById(R.id.btVolver).setOnClickListener(v -> mainMenu()); //Volver

        findViewById(R.id.btModificarDatos).setOnClickListener(v -> { //Modificar varios datos de un producto
            String codigo = ((EditText) findViewById(R.id.etCodigo)).getText().toString();
            menuModificarDatos(codigo);
        });
        findViewById(R.id.btModificarStock).setOnClickListener(v -> { // Modificar el stock
            String codigo = ((EditText) findViewById(R.id.etCodigo)).getText().toString();
            menuModificarStock(codigo);
        });
        findViewById(R.id.btRetirarProducto).setOnClickListener(v -> { //Retirar producto
            String codigo = ((EditText) findViewById(R.id.etCodigo)).getText().toString();
            retirarProducto(codigo);
        });
    }

    public void menuModificarDatos(String codigo) {
        gestionarFormularios(true, codigo);
    }

    public void menuAnadir(){
        gestionarFormularios(false, null);
    }

    public void menuModificarStock(String codigo) {
        setContentView(R.layout.activity_modificar_stock);
        findViewById(R.id.btVolverStock).setOnClickListener(v -> menuModificar());

        findViewById(R.id.btAnadirStock).setOnClickListener(v -> { //Botón Sumar (+)
            try {
                int cantidad = Integer.parseInt(((EditText) findViewById(R.id.etCantidadStock)).getText().toString());

                if (c.sumarStock(codigo, cantidad)) {
                    ((TextView) findViewById(R.id.tvMostrarResultado)).setText("Stock añadido correctamente.");
                } else {
                    ((TextView) findViewById(R.id.tvMostrarResultado)).setText("Error: Producto no encontrado.");
                }
            } catch (NumberFormatException e) {
                ((TextView) findViewById(R.id.tvMostrarResultado)).setText("Error: Introduce un número válido.");
            }
        });


        findViewById(R.id.btRestarStock).setOnClickListener(v -> { //Botón Restar (-)
            try {
                int cantidad = Integer.parseInt(((EditText) findViewById(R.id.etCantidadStock)).getText().toString());

                if (c.restarStock(codigo, cantidad)) {
                    ((TextView) findViewById(R.id.tvMostrarResultado)).setText("Stock restado correctamente.");
                } else {
                    ((TextView) findViewById(R.id.tvMostrarResultado)).setText("Error: Producto no encontrado o stock insuficiente.");
                }
            } catch (NumberFormatException e) {
                ((TextView) findViewById(R.id.tvMostrarResultado)).setText("Error: Introduce un número válido.");
            }
        });

        findViewById(R.id.btCambiarStock).setOnClickListener(v -> { // Botón Cambiar (=)
            try {
                int nuevaCantidad = Integer.parseInt(((EditText) findViewById(R.id.etCantidadStock)).getText().toString());

                if (c.cambiarStockTotal(codigo, nuevaCantidad)) {
                    ((TextView) findViewById(R.id.tvMostrarResultado)).setText("Stock actualizado al nuevo total.");
                } else {
                    ((TextView) findViewById(R.id.tvMostrarResultado)).setText("Error: Producto no encontrado.");
                }
            } catch (NumberFormatException e) {
                ((TextView) findViewById(R.id.tvMostrarResultado)).setText("Error: Introduce un número válido.");
            }
        });
    }

    public void retirarProducto(String codigo) {
        try {
            if (codigo.isEmpty()) throw new IllegalArgumentException("El código no puede estar vacío");

            if (c.retirarProducto(codigo)) {
                // Si sale bien, informamos en el TV y limpiamos el campo
                ((TextView) findViewById(R.id.tvMostrarResultado)).setText("Producto '" + codigo + "' retirado con éxito");
                ((EditText) findViewById(R.id.etCodigo)).setText("");
            } else {
                ((TextView) findViewById(R.id.tvMostrarResultado)).setText("Error: El producto no existe");
            }
        } catch (Exception e) {
            ((TextView) findViewById(R.id.tvMostrarResultado)).setText("Error: " + e.getMessage());
        }
    }

    public void menuListar(){
        setContentView(R.layout.activity_listar); //Mostramos el activity correspondiente
        findViewById(R.id.btVolver).setOnClickListener(v -> mainMenu()); //Volver

        findViewById(R.id.btListarTodos).setOnClickListener(v -> listarDatos(c.listarTodo()));
        findViewById(R.id.btListarSinStock).setOnClickListener(v -> listarDatos(c.listarSinStock()));
        findViewById(R.id.btListarCaducados).setOnClickListener(v -> listarDatos(c.listarCaducados()));
        findViewById(R.id.btListarRetirados).setOnClickListener(v -> listarDatos(c.listarRetirados()));

        findViewById(R.id.btListarRango).setOnClickListener(v -> {
            setContentView(R.layout.activity_listar_rango);
            findViewById(R.id.btVolver).setOnClickListener(v2 -> menuListar()); //Volver


            findViewById(R.id.btBuscarRango).setOnClickListener(v2 -> {
                //Guardamos los dos 2 datos cuando pulse el botón
                try {

                    Map<String,Double> rangoPrecios = new HashMap<>();

                    Double precioMin= Double.parseDouble(((EditText) findViewById(R.id.etPrecioMin)).getText().toString());
                    Double precioMax= Double.parseDouble(((EditText) findViewById(R.id.etPrecioMax)).getText().toString());

                    rangoPrecios.put("precioMin",precioMin);
                    rangoPrecios.put("precioMax",precioMax);

                    listarDatos(c.listarRango(rangoPrecios));
                }
                catch (NumberFormatException nfe) {
                    String resultado = "Error: El precio mínimo y máximo deben ser números válidos";
                    ((TextView) findViewById(R.id.tvMostrarResultado)).setText(resultado);
                }
                catch (Exception e) {
                    String resultado = "Error: " + e.getMessage();
                    ((TextView) findViewById(R.id.tvMostrarResultado)).setText(resultado);
                }
            });
        });

        findViewById(R.id.btComparar).setOnClickListener(v -> {
            setContentView(R.layout.activity_listar_comparar);
            findViewById(R.id.btVolver).setOnClickListener(v2 -> menuListar()); //Volver

            findViewById(R.id.btCompararAccion).setOnClickListener(v2 -> {
                //Guardamos los dos 2 datos cuando pulse el botón
                try {

                    Map<String,String> productos = new HashMap<>();

                    String producto1= ((EditText) findViewById(R.id.etCodigo1)).getText().toString();
                    String producto2= ((EditText) findViewById(R.id.etCodigo2)).getText().toString();

                    productos.put("producto1",producto1);
                    productos.put("producto2",producto2);

                    listarDatos(c.compararProductos(productos));
                }
                catch (IllegalArgumentException iae) {
                    String resultado = ("Error de validación: " + iae.getMessage());
                    ((TextView) findViewById(R.id.tvMostrarResultado)).setText(resultado);
                }
                catch (Exception e) {
                    String resultado = "Error: " + e.getMessage();
                    ((TextView) findViewById(R.id.tvMostrarResultado)).setText(resultado);
                }
            });

        });

        //Botones
        findViewById(R.id.btVolver).setOnClickListener(v -> mainMenu());
    }

    public void listarDatos(Map<String,Object> datos) {
        setContentView(R.layout.activity_resultado_listar); //Mostramos el activity correspondiente
        findViewById(R.id.btVolver).setOnClickListener(v -> mainMenu()); //Volver

        List<String> listaParaMostrar = new ArrayList<>();

        for (Map.Entry<String, Object> entrada : datos.entrySet()) { //Tipo de dato, iterator, contenedor, .entrySet() nos devuelva una lista de los valores
            listaParaMostrar.add(entrada.getKey() + "= " + entrada.getValue()); //Le damos un formato legible y lo añadimos a la lista
        }

        ArrayAdapter<String> adaptador  = new ArrayAdapter<String>( //Array Adapter al igual que en el spinner
                this,
                android.R.layout.simple_list_item_1,
                listaParaMostrar);

        ((ListView) findViewById(R.id.lvContenidoResultado)).setAdapter(adaptador); //Caste necesario si se hace un paso
    }

    public void menuGestionarDatos(){
        setContentView(R.layout.activity_gestionar_datos); //Mostramos el activity correspondiente
        findViewById(R.id.btVolver).setOnClickListener(v -> mainMenu()); //Volver
    }

}