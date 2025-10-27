package controlador;

import model.*;
import javax.swing.*;

public class controlCliente {
    private administradorDatos data;
    private cliente user;

    public controlCliente(administradorDatos data, cliente user) {
        this.data = data; this.user = user;
    }

    public administradorDatos getData() { return data; }

    public producto[] getProductosDisponibles() { return data.getProductos(); }

    public itemPedido[] getCarritoItems() { return user.getCarrito(); }

    public void agregarAlCarrito(String codigo, int cantidad) {
        producto p = data.buscarProducto(codigo);
        if (p == null) { JOptionPane.showMessageDialog(null, "Producto no encontrado."); return; }
        if (cantidad <= 0) { JOptionPane.showMessageDialog(null, "Cantidad > 0"); return; }
        if (cantidad > p.getStock()) {
            JOptionPane.showMessageDialog(null, "Stock insuficiente para " + p.getNombre() + " (Stock: " + p.getStock() + ")");
            return;
        }
        user.agregarItem(codigo, cantidad);
        data.agregarBitacora(new bitacora(user.getCodigo(), "Agregar al carrito", "Agregó " + cantidad + " de " + p.getNombre()));
    }

    public void eliminarDelCarrito(int index) {
        itemPedido[] items = user.getCarrito();
        if (index < 0 || index >= items.length) return;
        String code = items[index].getCodigoProducto();
        user.eliminarItem(index);
        data.agregarBitacora(new bitacora(user.getCodigo(), "Eliminar del carrito", "Eliminó " + code));
    }

    public void realizarPedido() {
        itemPedido[] items = user.getCarrito();
        if (items.length == 0) { JOptionPane.showMessageDialog(null, "El carrito está vacío."); return; }

        // Verificar stock
        for (itemPedido it : items) {
            producto p = data.buscarProducto(it.getCodigoProducto());
            if (p == null) { JOptionPane.showMessageDialog(null, "No existe: " + it.getCodigoProducto()); return; }
            if (p.getStock() < it.getCantidad()) {
                JOptionPane.showMessageDialog(null, "Stock insuficiente de " + p.getNombre());
                return;
            }
        }
        // Descontar stock
        for (itemPedido it : items) {
            producto p = data.buscarProducto(it.getCodigoProducto());
            if (p.reducirStock(it.getCantidad())) {
                data.agregarMovimiento(new stockMovimiento(p.getCodigo(), "Salida", it.getCantidad()));
            }
        }

        // Crear pedido
        pedido nuevo = new pedido(user.getCodigo(), items);
        data.agregarPedido(nuevo);
        user.agregarPedido(nuevo);
        data.agregarBitacora(new bitacora(user.getCodigo(), "Pedido", "Nuevo pedido con " + items.length + " items"));
        user.limpiarCarrito();
        JOptionPane.showMessageDialog(null, "Pedido realizado correctamente.");
    }

    public pedido[] getHistorialCompras() { return user.getHistorial(); }
}
