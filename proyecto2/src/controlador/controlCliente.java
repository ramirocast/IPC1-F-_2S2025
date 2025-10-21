package controlador;

import model.itemPedido;
import model.pedido;
import model.producto;
import util.administradorDatos;

public class controlCliente {
    private administradorDatos data;
    private String clienteCode;
    private pedido carrito = new pedido(null); // Carrito temporal

    public controlCliente(administradorDatos data, String clienteCode) {
        this.data = data;
        this.clienteCode = clienteCode;
        carrito = new pedido(clienteCode);
    }

    public producto[] getProductosDisponibles() {
        return data.getProductosConStock();
    }

    public void agregarAlCarrito(String codigoProducto, int cantidad) {
        producto prod = data.buscarProducto(codigoProducto);
        if (prod != null && prod.getStock() >= cantidad) {
            carrito.agregarItem(codigoProducto, cantidad);
            data.agregarBitacora(new bitacora("Cliente", clienteCode, "AGREGAR_A_CARRITO", "EXITOSA", "Producto " + codigoProducto + " agregado, cantidad " + cantidad));
        } else {
            JOptionPane.showMessageDialog(null, "Stock insuficiente", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void eliminarDelCarrito(int index) {
        ItemPedido[] items = carrito.getItems();
        System.arraycopy(items, index + 1, items, index, items.length - index - 1);
        carrito.numItems--;
    }

    public void actualizarCantidadCarrito(int index, int nuevaCantidad) {
        itemPedido[] items = carrito.getItems();
        String code = items[index].getCodigoProducto();
        producto prod = data.buscarProducto(code);
        if (prod != null && prod.getStock() >= nuevaCantidad) {
            items[index].cantidad = nuevaCantidad;
        } else {
            JOptionPane.showMessageDialog(null, "Stock insuficiente", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public itemPedido[] getCarritoItems() {
        return carrito.getItems();
    }

    public void realizarPedido() {
        if (carrito.numItems > 0) {
            data.agregarPedido(carrito);
            data.agregarBitacora(new bitacora("Cliente", clienteCode, "REALIZAR_PEDIDO", "EXITOSA", "Pedido creado con " + carrito.numItems + " items"));
            carrito = new pedido(clienteCode); // Limpiar carrito
        }
    }

    public pedido[] getHistorialCompras() {
        return data.getHistorialCompras(clienteCode);
    }
}
