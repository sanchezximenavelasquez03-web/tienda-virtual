package adapter;
public interface PaymentProcessor {
    public void pagar(double monto);
    public String getNombreMetodo();
}