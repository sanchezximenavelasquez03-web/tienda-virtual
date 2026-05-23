package model;

import strategy.DiscountStrategy;
import strategy.NoDiscountStrategy;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<Product> items = new ArrayList<>();
    private DiscountStrategy descuento = new NoDiscountStrategy();

    public void agregarProducto(Product producto) {
        items.add(producto);
        System.out.println("  [+] Agregado: " + producto.getNombre() +
                           "  (S/ " + String.format("%.2f", producto.getPrecio()) + ")");
    }

    public void setDescuento(DiscountStrategy estrategia) {
        this.descuento = estrategia;
    }

    public double getTotalBruto() {
        return items.stream()
                .mapToDouble(p -> p.getPrecio() * p.getCantidad())
                .sum();
    }

    public double getTotalFinal() {
        return descuento.applyDiscount(getTotalBruto());
    }

    public List<Product> getItems() { return items; }

    public void mostrarResumen() {
        System.out.println("\n  ┌─────────────────────────────────────────┐");
        System.out.println("  │           RESUMEN DEL CARRITO           │");
        System.out.println("  ├─────────────────────────────────────────┤");
        for (Product p : items) {
            System.out.printf("  │  %-20s  S/ %7.2f    │%n",
                    p.getNombre(), p.getPrecio() * p.getCantidad());
        }
        System.out.println("  ├─────────────────────────────────────────┤");
        System.out.printf("  │  Subtotal:                  S/ %7.2f   │%n", getTotalBruto());
        double descuentoAplicado = getTotalBruto() - getTotalFinal();
        if (descuentoAplicado > 0) {
            System.out.printf("  │  Descuento:                -S/ %7.2f   │%n", descuentoAplicado);
        }
        System.out.printf("  │  TOTAL A PAGAR:             S/ %7.2f   │%n", getTotalFinal());
        System.out.println("  └─────────────────────────────────────────┘");
    }
}