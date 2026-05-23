package adapter;

public class YapePaymentProcessor implements PaymentProcessor {

    @Override
    public void pagar(double monto) {
        System.out.printf("  [Yape] Pago enviado: S/ %.2f%n", monto);
        System.out.println("  [Yape] ¡Yapeo exitoso! Notificación enviada al número registrado.");
    }

    @Override
    public String getNombreMetodo() {
        return "Yape";
    }
}