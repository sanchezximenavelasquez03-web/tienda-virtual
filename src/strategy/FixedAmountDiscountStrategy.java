package strategy;

public class FixedAmountDiscountStrategy implements DiscountStrategy {
    private final double montoFijo;

    public FixedAmountDiscountStrategy(double montoFijo) {
        if(montoFijo < 0){
            throw new IllegalArgumentException("El monto fijo no puede ser negativo");
        }
        this.montoFijo = montoFijo;
    }

    @Override
    public double applyDiscount(double total) { 
        return Math.max(0, total - montoFijo);
    }

    @Override
    public String getDescripcion() {
        return "Descuento fijo de S/ " + montoFijo;
    }
}