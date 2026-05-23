package observer;

public class EmailNotificationObserver implements OrderObserver {

    @Override
    public void actualizar(String mensaje) {
        System.out.println("  [Email]  Enviando correo al cliente...");
        System.out.println("           Asunto: Confirmación de compra");
        System.out.println("           Detalle: " + mensaje);
    }
}