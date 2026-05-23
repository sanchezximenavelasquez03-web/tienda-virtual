import adapter.*;
import model.*;
import observer.*;
import service.OrderService;
import strategy.*;

public class Main {

    public static void main(String[] args) {

        printEncabezado();

        seccion("1. CATÁLOGO DE PRODUCTOS");
        Product laptop  = new Product("Laptop Lenovo",     2500.00, 1);
        Product mouse   = new Product("Mouse Inalámbrico",   85.00, 2);
        Product teclado = new Product("Teclado Mecánico",   150.00, 1);
        System.out.println("  Productos registrados:");
        System.out.println("    • " + laptop);
        System.out.println("    • " + mouse);
        System.out.println("    • " + teclado);

        seccion("2. CARRITO DE COMPRAS");
        Cart carrito = new Cart();
        System.out.println("  Agregando productos al carrito:");
        carrito.agregarProducto(laptop);
        carrito.agregarProducto(mouse);
        carrito.agregarProducto(teclado);

        seccion("3. PATRÓN STRATEGY — DESCUENTOS");

        carrito.setDescuento(new NoDiscountStrategy());
        System.out.println("  Estrategia activa: " + new NoDiscountStrategy().getDescripcion());
        System.out.printf("  Total (sin descuento):    S/ %.2f%n", carrito.getTotalFinal());

        DiscountStrategy porcentual = new PercentageDiscountStrategy(10);
        carrito.setDescuento(porcentual);
        System.out.println("\n  Estrategia activa: " + porcentual.getDescripcion());
        System.out.printf("  Total (10%% descuento):   S/ %.2f%n", carrito.getTotalFinal());

        DiscountStrategy fijo = new FixedAmountDiscountStrategy(100.00);
        carrito.setDescuento(fijo);
        System.out.println("\n  Estrategia activa: " + fijo.getDescripcion());
        carrito.mostrarResumen();

        seccion("4. PATRÓN ADAPTER — MÉTODOS DE PAGO");
        PaymentProcessor paypal     = new PayPalAdapter(new ExternalPayPalService());
        PaymentProcessor tarjeta    = new CreditCardPaymentProcessor();
        PaymentProcessor yape       = new YapePaymentProcessor();

        System.out.println("  Procesadores disponibles:");
        System.out.println("    [1] " + paypal.getNombreMetodo());
        System.out.println("    [2] " + tarjeta.getNombreMetodo());
        System.out.println("    [3] " + yape.getNombreMetodo());
        System.out.println("\n  Método seleccionado: " + paypal.getNombreMetodo());

        seccion("5. PATRÓN OBSERVER — OBSERVADORES");
        OrderService orderService = new OrderService();
        orderService.agregarObservador(new EmailNotificationObserver());
        orderService.agregarObservador(new InventoryObserver());
        orderService.agregarObservador(new AdminNotificationObserver());
        System.out.println("  Observadores registrados:");
        System.out.println("    • EmailNotificationObserver");
        System.out.println("    • InventoryObserver");
        System.out.println("    • AdminNotificationObserver");

        seccion("6. CONFIRMACIÓN DE COMPRA");
        orderService.confirmarOrden(carrito, paypal);

        printPie();
    }

    private static void seccion(String titulo) {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║  " + titulo);
        System.out.println("╚══════════════════════════════════════════════╝");
    }

    private static void printEncabezado() {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║       TIENDA VIRTUAL — PATRONES DE DISEÑO        ║");
        System.out.println("║     Strategy  |  Adapter  |  Observer            ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
    }

    private static void printPie() {
        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  Flujo completado. Todos los patrones aplicados. ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
    }
}