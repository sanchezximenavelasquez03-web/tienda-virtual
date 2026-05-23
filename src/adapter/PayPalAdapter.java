package adapter;

public class PayPalAdapter implements PaymentProcessor {
    private final ExternalPayPalService payPalService;

    public PayPalAdapter(ExternalPayPalService payPalService){
        this.payPalService = payPalService;
    }

    @Override
    public void pagar(double monto){
        System.out.println(" [Adapter] Convirtiendo llamada ");
        payPalService.makePayment("PEN", monto);
    }

    @Override
    public String getNombreMetodo() {
        return "PayPal (via Adapter)";
    }

}