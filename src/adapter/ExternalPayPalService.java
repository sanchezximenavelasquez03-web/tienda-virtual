package adapter;

/**
 * Paypal es un servicio externo
 */

public class ExternalPayPalService {
    public void makePayment(String currency, double amount) {
        System.out.printf("  [PayPal API] Transacción procesada: %s %.2f%n", currency, amount);
        System.out.println("  [PayPal API] Confirmación enviada a la cuenta PayPal registrada.");
    }

}