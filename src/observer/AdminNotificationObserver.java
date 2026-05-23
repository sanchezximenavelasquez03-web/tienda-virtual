package observer;

public class AdminNotificationObserver implements OrderObserver {

    @Override
    public void actualizar(String mensaje) {
        System.out.println("  [Admin]  Notificando al administrador del sistema...");
        System.out.println("           Evento registrado: " + mensaje);
    }
}