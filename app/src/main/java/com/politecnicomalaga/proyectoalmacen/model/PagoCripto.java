package model;

public class PagoCripto implements MetodoPago {
    private String billeteraId;

    public PagoCripto(String billeteraId) {
        this.billeteraId = billeteraId;
    }

    @Override
    public void procesarPago(double monto) {
        System.out.println("Validando transacción en la Blockchain...");
        System.out.println("Transferencia enviada a: " + billeteraId);
    }

    // AQUÍ REDEFINIMOS EL MÉTODO QUE YA ESTABA DEFINIDO, puede ser asi.
    @Override
    public void mostrarRecibo(double monto) {
        System.out.println("--- RECIBO BLOCKCHAIN ---");
        System.out.println("Monto: " + monto + " BTC");
        System.out.println("TXID: 0x9a8b7c6d5e4f..."); // Algo específico de Cripto
    }
}