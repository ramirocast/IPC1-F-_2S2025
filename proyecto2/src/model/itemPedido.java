package model;

import java.io.Serializable;

public class itemPedido implements Serializable {
    private String codigoProducto;
    private int cantidad;

    public itemPedido(String codigoProducto, int cantidad) {
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
    }

    public String getCodigoProducto() { return codigoProducto; }
    public int getCantidad() { return cantidad; }

    @Override
    public String toString() {
        return codigoProducto + " x" + cantidad;
    }
}
