package model;

import java.io.Serializable;
import java.util.Date;

public class pedido implements Serializable {
    private String codigoCliente;
    private ItemPedido[] items = new ItemPedido[50];
    private int numItems = 0;
    private String estado = "Pendiente";
    private Date fecha = new Date();

    public pedido(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public String getEstado() {
        return estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void agregarItem(String codigoProducto, int cantidad) {
        if (numItems < items.length) {
            items[numItems++] = new ItemPedido(codigoProducto, cantidad);
        }
    }

    public ItemPedido[] getItems() {
        ItemPedido[] copy = new ItemPedido[numItems];
        System.arraycopy(items, 0, copy, 0, numItems);
        return copy;
    }

    public void confirmar() {
        estado = "Confirmado";
    }
}

class ItemPedido implements Serializable {
    String codigoProducto;
    int cantidad;

    public ItemPedido(String codigoProducto, int cantidad) {
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public int getCantidad() {
        return cantidad;
    }
}