# Tienda Virtual

## ¿De qué trata el proyecto?

La idea fue construir una tienda que funcione mediante la consola que permita agregar productos a un carrito, aplicar descuentos, elegir cómo pagar y notificar a distintos sistemas cuando se confirma una compra. El reto era que cada funcionalidad está modelada con un patrón de diseño distinto, de forma que el código sea extensible sin necesidad de modificar lo que ya funciona.

---

## Patrones aplicados

### Strategy — Descuentos

El carrito necesita calcular el total con distintos tipos de descuento, pero no debería conocer los detalles de cada uno. La solución fue definir una interfaz `DiscountStrategy` y hacer que el carrito simplemente llame a `applyDiscount(total)` sin importarle quién la implementa.

Cambiar de "sin descuento" a "10% de descuento" o a "S/ 100 fijos" es solo cuestión de pasar otra estrategia al carrito, sin tocar `Cart` en absoluto.

```java
carrito.setDescuento(new PercentageDiscountStrategy(10));
// Cart no cambia, solo cambia la estrategia que usa
```

Las tres estrategias implementadas son `NoDiscountStrategy`, `PercentageDiscountStrategy` y `FixedAmountDiscountStrategy`.

---

### Adapter — Métodos de pago

El problema fue que quería usar PayPal, pero el servicio externo (`ExternalPayPalService`) tiene un método `makePayment(String currency, double amount)` que no encaja con la interfaz que usa el resto del sistema (`PaymentProcessor`).

En lugar de modificar el servicio externo, creé `PayPalAdapter`, que implementa `PaymentProcessor` y por dentro traduce la llamada al formato que PayPal espera.

```java
PaymentProcessor paypal = new PayPalAdapter(new ExternalPayPalService());
paypal.pagar(2720.00);
// Internamente hace: payPalService.makePayment("PEN", 2720.00)
```

También agregué `CreditCardPaymentProcessor` y `YapePaymentProcessor` que implementan `PaymentProcessor` directamente, sin necesitar adaptador.

---

### Observer — Notificaciones

Al confirmar una compra, varios sistemas deben reaccionar: se manda un correo, se actualiza el inventario y se avisa al administrador. Con Observer, `OrderService` mantiene una lista de observadores y los notifica a todos cuando la compra se confirma. Cada observador decide qué hacer con el mensaje.

```java
orderService.agregarObservador(new EmailNotificationObserver());
orderService.agregarObservador(new InventoryObserver());
orderService.agregarObservador(new AdminNotificationObserver());

orderService.confirmarOrden(carrito, paypal);
// notifica automáticamente a los tres
```

Si mañana se necesita agregar un cuarto observador, solo se registra sin tocar `OrderService`.

---

## Estructura del proyecto

```
tienda-virtual/
├── src/
│   ├── Main.java
│   ├── model/
│   │   ├── Product.java
│   │   └── Cart.java
│   ├── strategy/
│   │   ├── DiscountStrategy.java
│   │   ├── NoDiscountStrategy.java
│   │   ├── PercentageDiscountStrategy.java
│   │   └── FixedAmountDiscountStrategy.java
│   ├── adapter/
│   │   ├── PaymentProcessor.java
│   │   ├── ExternalPayPalService.java
│   │   ├── PayPalAdapter.java
│   │   ├── CreditCardPaymentProcessor.java
│   │   └── YapePaymentProcessor.java
│   ├── observer/
│   │   ├── OrderObserver.java
│   │   ├── EmailNotificationObserver.java
│   │   ├── InventoryObserver.java
│   │   └── AdminNotificationObserver.java
│   └── service/
│       └── OrderService.java
└── README.md
```

---

## Cómo compilar y ejecutar

```bash
# Compilar
javac -d out -sourcepath src src/Main.java

# Ejecutar
java -Dfile.encoding=UTF-8 -cp out Main
```

---

## Salida en consola

```
     TIENDA VIRTUAL — PATRONES DE DISEÑO
     Strategy  |  Adapter  |  Observer


  1. CATÁLOGO DE PRODUCTOS

  Productos registrados:
    • Laptop Lenovo        S/ 2500.0  x1
    • Mouse Inalámbrico    S/  85.0  x2
    • Teclado Mecánico     S/ 150.0  x1


  2. CARRITO DE COMPRAS

  Agregando productos al carrito:
  [+] Agregado: Laptop Lenovo  (S/ 2500.00)
  [+] Agregado: Mouse Inalámbrico  (S/ 85.00)
  [+] Agregado: Teclado Mecánico  (S/ 150.00)


  3. PATRÓN STRATEGY — DESCUENTOS

  Estrategia activa: Sin descuento
  Total (sin descuento):    S/ 2820.00

  Estrategia activa: 10.0% de descuento porcentual
  Total (10% descuento):   S/ 2538.00

  Estrategia activa: Descuento fijo de S/ 100.00

  ┌─────────────────────────────────────────┐
  │           RESUMEN DEL CARRITO           │
  ├─────────────────────────────────────────┤
  │  Laptop Lenovo         S/ 2500.00       │
  │  Mouse Inalámbrico     S/  170.00       │
  │  Teclado Mecánico      S/  150.00       │
  ├─────────────────────────────────────────┤
  │  Subtotal:                  S/ 2820.00  │
  │  Descuento:                -S/  100.00  │
  │  TOTAL A PAGAR:             S/ 2720.00  │
  └─────────────────────────────────────────┘


  4. PATRÓN ADAPTER — MÉTODOS DE PAGO

  Procesadores disponibles:
    [1] PayPal (via Adapter)
    [2] Tarjeta de Crédito
    [3] Yape

  Método seleccionado: PayPal (via Adapter)


  5. PATRÓN OBSERVER — OBSERVADORES

  Observadores registrados:
    • EmailNotificationObserver
    • InventoryObserver
    • AdminNotificationObserver


  6. CONFIRMACIÓN DE COMPRA

  ── Procesando pago con PayPal (via Adapter) ──
  [Adapter] Convirtiendo llamada pagar() → makePayment("PEN", monto)
  [PayPal API] Transacción procesada: PEN 2720.00
  [PayPal API] Confirmación enviada a la cuenta PayPal registrada.

  ✔  Compra confirmada por S/ 2720.00 con PayPal (via Adapter).

  ── Notificando observadores ──
  [Email]  Enviando correo al cliente...
           Asunto: Confirmación de compra
           Detalle: Compra confirmada por S/ 2720.00 con PayPal (via Adapter).
  [Inventario]  Actualizando stock de productos...
                Compra confirmada por S/ 2720.00 con PayPal (via Adapter).
  [Admin]  Notificando al administrador del sistema...
           Evento registrado: Compra confirmada por S/ 2720.00 con PayPal (via Adapter).

  Flujo completado. Todos los patrones aplicados.
```
