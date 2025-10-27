package model;

import java.io.Serializable;
import java.util.List;

public class detallePedido implements Serializable {
    private pedido pedidoBase;
    private double total;
    private String clienteNombre;

    public detallePedido(pedido pedidoBase, cliente cliente, List<producto> productos) {
        this.pedidoBase = pedidoBase;
        this.clienteNombre = cliente.getNombre();
        this.total = calcularTotal(productos);
    }

    private double calcularTotal(List<producto> productos) {
        double total = 0;
        for (itemPedido ip : pedidoBase.getItems()) {
            for (producto p : productos) {
                if (p.getCodigo().equals(ip.getCodigoProducto())) {
                    total += p.getPrecio() * ip.getCantidad();
                }
            }
        }
        return total;
    }

    public double getTotal() { return total; }
    public String getClienteNombre() { return clienteNombre; }
    public pedido getPedidoBase() { return pedidoBase; }

    @Override
    public String toString() {
        return "Pedido de " + clienteNombre + " - Total: Q" + total;
    }
}
