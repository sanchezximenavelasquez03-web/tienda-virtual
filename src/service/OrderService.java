package service;

import adapter.PaymentProcessor;
import java.util.ArrayList;
import java.util.List;
import model.Cart;
import observer.OrderObserver;

public class OrderService {

    private final List<OrderObserver> observadores = new ArrayList<>();

    public void agregarObservador(OrderObserver observador) {
        observadores.add(observador);
    }

    public void eliminarObservador(OrderObserver observador) {
        observadores.remove(observador);
    }

    private void notificarObservadores(String mensaje) {
        System.out.println("\n  ── Notificando observadores ──");
        for (OrderObserver observador : observadores) {
            observador.actualizar(mensaje);
        }
    }

    public void confirmarOrden(Cart carrito, PaymentProcessor procesadorPago) {
        double total = carrito.getTotalFinal();

        System.out.println("\n  ── Procesando pago con " + procesadorPago.getNombreMetodo() + " ──");
        procesadorPago.pagar(total);

        String confirmacion = String.format(
                "Compra confirmada por S/ %.2f con %s.",
                total, procesadorPago.getNombreMetodo()
        );

        System.out.println("\n  " + confirmacion);

        notificarObservadores(confirmacion);
    }
}