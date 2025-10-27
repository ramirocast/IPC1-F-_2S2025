package controlador;

import model.*;
import javax.swing.*;
import java.io.*;

public class controlVendedor {
    private administradorDatos data;
    private vendedor user;

    public controlVendedor(administradorDatos data, vendedor user) {
        this.data = data; this.user = user;
    }

    public administradorDatos getData() { return data; }

    public producto[] getProductos() { return data.getProductos(); }

    public pedido[] getPedidosPendientes() {
        // contar
        pedido[] all = data.getHistorialGlobal();
        int c = 0;
        for (pedido p : all) if ("Pendiente".equals(p.getEstado())) c++;
        // crear
        pedido[] pend = new pedido[c];
        int j = 0;
        for (pedido p : all) if ("Pendiente".equals(p.getEstado())) pend[j++] = p;
        return pend;
    }

    public void confirmarPedido(pedido p) {
       p.confirmar(user.getCodigo());
        user.incrementarVentas();
        data.agregarBitacora(new bitacora(user.getCodigo(), "Confirmar Pedido", "Confirmó un pedido de " + p.getCodigoCliente()));
    }

    public void agregarStock(String codigo, int cantidad) {
        producto pr = data.buscarProducto(codigo);
        if (pr == null) { JOptionPane.showMessageDialog(null, "Producto no encontrado."); return; }
        if (cantidad <= 0) { JOptionPane.showMessageDialog(null, "Cantidad > 0"); return; }
        pr.agregarStock(cantidad);
        data.agregarMovimiento(new stockMovimiento(codigo, "Entrada", cantidad));
        data.agregarBitacora(new bitacora(user.getCodigo(), "Agregar Stock", "Agregó " + cantidad + " a " + codigo));
    }

    public void cargarStockCSV(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                producto p = data.buscarProducto(parts[0]);
                if (p != null) {
                    int cant = Integer.parseInt(parts[1]);
                    if (cant > 0) {
                        p.agregarStock(cant);
                        data.agregarMovimiento(new stockMovimiento(p.getCodigo(), "Entrada", cant));
                    }
                }
            }
            data.agregarBitacora(new bitacora(user.getCodigo(), "Carga CSV", "Actualizó stock desde CSV."));
        } catch (Exception e) { JOptionPane.showMessageDialog(null, "Error al cargar CSV: " + e.getMessage()); }
    }

    // NUEVO: Agregar Producto
    public void agregarNuevoProducto(String codigo, String nombre, String categoria, double precio) {
        if (codigo == null || nombre == null || categoria == null) { JOptionPane.showMessageDialog(null, "Datos inválidos."); return; }
        if (codigo.isBlank() || nombre.isBlank() || categoria.isBlank() || precio < 0) { JOptionPane.showMessageDialog(null, "Datos inválidos."); return; }
        if (data.buscarProducto(codigo) != null) { JOptionPane.showMessageDialog(null, "Ya existe un producto con ese código."); return; }

        producto nuevo = new producto(codigo, nombre, categoria, precio);
        data.agregarProducto(nuevo);
        data.agregarBitacora(new bitacora(user.getCodigo(), "Nuevo Producto", "Agregó " + nombre + " (" + codigo + ")"));
        JOptionPane.showMessageDialog(null, "Producto agregado correctamente.");
    }
}
