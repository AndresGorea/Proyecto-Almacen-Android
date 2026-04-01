package model;

public class PagoPayPal implements MetodoPago {
    private String correo;

    public PagoPayPal(String correo) {
        this.correo = correo;
    }

    // AQUÍ DEFINIMOS EL MÉTODO QUE ESTABA VACÍO
    @Override
    public void procesarPago(double monto) {
        System.out.println("Conectando con servidores de PayPal para " + correo);
        System.out.println("Pago de $" + monto + " autorizado con éxito.");
    }
    
    // No escribimos 'mostrarRecibo' porque usaremos el que ya viene en la interfaz.
}