package com.politecnicomalaga.proyectoalmacen.view;

import com.politecnicomalaga.proyectoalmacen.controller.*;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.politecnicomalaga.proyectoalmacen.R;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public static Controlador c = Controlador.getSingleton();
    public enum TipoProducto {
        NORMAL, PERECEDERO
    }


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
    public void menuAnadir(){
        setContentView(R.layout.activity_anadir); //Mostramos el activity correspondiente
        findViewById(R.id.btVolver).setOnClickListener(v -> mainMenu()); //Volver

        ///Mostramos las opciones para el spinner
        ArrayAdapter<TipoProducto> adapter = new ArrayAdapter<>( // Arrayadapter coge una colección de datos y le pasa cada dato a un list view o spinner para que lo muestren
                this, //Le damos el contexto (esta clase this)
                android.R.layout.simple_spinner_item, //Le damos el recurso al cual le pasamos los datos
                TipoProducto.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Le damos un diseño bonito al desplegable
        ((Spinner) findViewById(R.id.spTipo)).setAdapter(adapter); //Buscamos el spinner y le damos los valores del adapter

        ///Verificamos qué tipo de "sp" hay seleccionado para mostrar o no el etFechaCaducidad
        Spinner spTipo = (Spinner) findViewById(R.id.spTipo);
        EditText etFechaCaducidad = (EditText) findViewById(R.id.etFechaCaducidad);

        spTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Interfaz comprueba el spinner cada vez que se toca
            @Override //Cuando se toca verificamos el valor de tipoP y mostramos o no la fecha
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TipoProducto tipoP = (TipoProducto) spTipo.getSelectedItem();
                switch (tipoP) {
                    case NORMAL:
                        etFechaCaducidad.setVisibility(View.GONE);
                        break;
                    case PERECEDERO:
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
                TipoProducto tipoP = (TipoProducto) spTipo.getSelectedItem();

                //Mapas
                HashMap<String, Object> datosProducto = new HashMap<>();
                HashMap<String, Object> atributos = new HashMap<>();

                //Guardamos los atributos en el mapa de atributos
                atributos.put("descripcion", descripcion);
                atributos.put("precio", precioDouble);
                atributos.put("stock", stock);
                atributos.put("tipo", tipoP);

                //Otros datos diferentes
                switch (tipoP) { //Utilizamos un switch por si ampliamos los tipos de productos que sea facil
                    case NORMAL:
                        //No necesitamos más datos
                        break;
                    case PERECEDERO:
                        String fecha = ((EditText) findViewById(R.id.etFechaCaducidad)).getText().toString();
                        atributos.put("fecha", fecha);
                        break;
                }

                //Guardamos los datos con el mapa de datosProducto que el ID sea codigo producto
                datosProducto.put(codigo, atributos);

                if (c.addProducto(datosProducto)){ // Le pasamos los dátos al controlador
                    String resultado = ("Producto añadido con éxito");
                    ((TextView) findViewById(R.id.tvMostrarResultado)).setText(resultado);
                };

            }
            catch (IllegalArgumentException iae) {
                String resultado = ("Error de validación: " + iae.getMessage());
                ((TextView) findViewById(R.id.tvMostrarResultado)).setText(resultado);
            }
            catch (NumberFormatException nfe) {
                String resultado = "Error: El precio y el stock deben ser números válidos";
                ((TextView) findViewById(R.id.tvMostrarResultado)).setText(resultado);
            }
            catch (Exception e) {
                String resultado = "Error Desconocido";
                ((TextView) findViewById(R.id.tvMostrarResultado)).setText(resultado);
            }
        });
    }
    public void menuModificar(){
        setContentView(R.layout.activity_modificar); //Mostramos el activity correspondiente
        findViewById(R.id.btVolver).setOnClickListener(v -> mainMenu()); //Volver
    }
    public void menuListar(){
        setContentView(R.layout.activity_listar); //Mostramos el activity correspondiente
        findViewById(R.id.btVolver).setOnClickListener(v -> mainMenu()); //Volver
    }
    public void menuGestionarDatos(){
        setContentView(R.layout.activity_gestionar_datos); //Mostramos el activity correspondiente
        findViewById(R.id.btVolver).setOnClickListener(v -> mainMenu()); //Volver
    }

}