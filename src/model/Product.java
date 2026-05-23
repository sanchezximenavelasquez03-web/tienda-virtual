package model;

public class Product {
    private final String nombre;
    private final double precio;
    private final int cantidad;

    public Product(String nombre, double precio, int cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    @Override
    public String toString() {
        return nombre + " S/ " + precio + " x" + cantidad;
    }
}