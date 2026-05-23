package adapter;

public class CreditCardPaymentProcessor implements PaymentProcessor {

    @Override
    public void pagar(double monto) {
        System.out.printf("  [Tarjeta de Crédito] Cargo procesado: S/ %.2f%n", monto);
        System.out.println("  [Tarjeta de Crédito] Transacción aprobada. Código: TXN-" +
                           (int)(Math.random() * 900000 + 100000));
    }

    @Override
    public String getNombreMetodo() {
        return "Tarjeta de Crédito";
    }
}