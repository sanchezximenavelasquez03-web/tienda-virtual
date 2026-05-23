package strategy;

public class NoDiscountStrategy implements DiscountStrategy {

    @Override
    public double applyDiscount(double total) {
        return total;
    }

    @Override
    public String getDescripcion() {
        return "Sin descuento";
    }
}