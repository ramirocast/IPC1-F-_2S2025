package model;

import java.io.Serializable;

public class cliente extends usuario implements Serializable {
    private pedido[] historial;
    private itemPedido[] carrito;

    public cliente(String codigo, String nombre, String contrasena) {
        super(codigo, nombre, contrasena, "CLIENTE");
        this.historial = new pedido[0];
        this.carrito = new itemPedido[0];
    }

    // --- Historial ---
    public pedido[] getHistorial() { return historial; }
    public void agregarPedido(pedido p) {
        pedido[] nuevo = new pedido[historial.length + 1];
        for (int i = 0; i < historial.length; i++) nuevo[i] = historial[i];
        nuevo[historial.length] = p;
        historial = nuevo;
    }

    // --- Carrito ---
    public itemPedido[] getCarrito() { return carrito; }

    public void agregarItem(String codigoProducto, int cantidad) {
        itemPedido[] nuevo = new itemPedido[carrito.length + 1];
        for (int i = 0; i < carrito.length; i++) nuevo[i] = carrito[i];
        nuevo[carrito.length] = new itemPedido(codigoProducto, cantidad);
        carrito = nuevo;
    }

    public void eliminarItem(int index) {
        if (index < 0 || index >= carrito.length) return;
        itemPedido[] nuevo = new itemPedido[carrito.length - 1];
        int j = 0;
        for (int i = 0; i < carrito.length; i++) {
            if (i == index) continue;
            nuevo[j++] = carrito[i];
        }
        carrito = nuevo;
    }

    public void limpiarCarrito() { carrito = new itemPedido[0]; }
}
