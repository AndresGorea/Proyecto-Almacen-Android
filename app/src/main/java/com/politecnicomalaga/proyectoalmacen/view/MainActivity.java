package com.politecnicomalaga.proyectoalmacen.view;

import com.politecnicomalaga.proyectoalmacen.controller.*;
import android.os.Bundle;
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