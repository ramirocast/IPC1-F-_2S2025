package model;

import java.io.Serializable;

public class itemCarrito implements Serializable {
    private producto producto;
    private int cantidad;

    public itemCarrito(producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getSubtotal() { return producto.getPrecio() * cantidad; }

    @Override
    public String toString() {
        return producto.getNombre() + " x" + cantidad + " (Q" + getSubtotal() + ")";
    }
}
