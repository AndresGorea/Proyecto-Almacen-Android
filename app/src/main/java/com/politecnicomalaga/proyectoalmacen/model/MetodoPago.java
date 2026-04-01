package model;

public interface MetodoPago {
    
    // MÉTODO SIN DEFINIR (Cada uno tiene su lógica)
    void procesarPago(double monto);

    // MÉTODO DEFAULT 1:
    default void mostrarRecibo(double monto) {
        System.out.println("--- RECIBO OFICIAL ---");
        System.out.println("Monto total: $" + monto);
        System.out.println("Gracias por su compra.");
    }

    // MÉTODO DEFAULT 2:
    // Todas las clases lo heredan y así evitan procesar pagos negativos.
    default boolean esMontoValido(double monto) {
        if (monto <= 0) {
            System.out.println("Error: El monto debe ser mayor a 0.");
            return false;
        }
        return true;
    }
}