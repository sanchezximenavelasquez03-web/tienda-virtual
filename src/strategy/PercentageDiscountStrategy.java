package strategy;

public class PercentageDiscountStrategy implements DiscountStrategy {
    private final double porcentaje;

    public PercentageDiscountStrategy(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    @Override
    public double applyDiscount(double total) {
        return total * (1 - porcentaje / 100.00);
    }

    @Override
    public String getDescripcion() {
        return porcentaje + "% de descuento porcentual";
    }

}