package observer;

public class InventoryObserver implements OrderObserver {

    @Override
    public void actualizar(String mensaje) {
        System.out.println("  [Inventario]  Actualizando stock de productos...");
        System.out.println("                " + mensaje);
    }
}