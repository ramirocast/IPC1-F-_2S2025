package model;

import java.io.Serializable;
import java.util.Date;

public class pedido implements Serializable {
    private String codigoCliente;
    private String codigoVendedor;
    private itemPedido[] items;
    private String estado;
    private Date fecha;

    // Constructor principal
    public pedido(String codigoCliente, itemPedido[] items) {
        this.codigoCliente = codigoCliente;
        this.codigoVendedor = ""; // se asigna al confirmar el pedido
        this.items = items;
        this.estado = "Pendiente";
        this.fecha = new Date();
    }

    // Constructor vacío (para uso interno o serialización)
    public pedido() {
        this.codigoCliente = "";
        this.codigoVendedor = "";
        this.items = new itemPedido[0];
        this.estado = "Pendiente";
        this.fecha = new Date();
    }

    // ------------------ GETTERS ------------------
    public String getCodigoCliente() {
        return codigoCliente;
    }

    public String getCodigoVendedor() {
        return codigoVendedor;
    }

    public itemPedido[] getItems() {
        return items;
    }

    public String getEstado() {
        return estado;
    }

    public Date getFecha() {
        return fecha;
    }

    // ------------------ SETTERS ------------------
    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public void setCodigoVendedor(String codigoVendedor) {
        this.codigoVendedor = codigoVendedor;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setItems(itemPedido[] items) {
        this.items = items;
    }

    // ------------------ FUNCIONES AUXILIARES ------------------

    // Agrega un nuevo ItemPedido al arreglo dinámicamente
    public void agregarItem(itemPedido nuevo) {
        itemPedido[] nuevoArray = new itemPedido[items.length + 1];
        for (int i = 0; i < items.length; i++) {
            nuevoArray[i] = items[i];
        }
        nuevoArray[items.length] = nuevo;
        items = nuevoArray;
    }

    // Calcula el total del pedido
    public double calcularTotal(administradorDatos data) {
        double total = 0;
        for (int i = 0; i < items.length; i++) {
            producto p = data.buscarProducto(items[i].getCodigoProducto());
            if (p != null) total += p.getPrecio() * items[i].getCantidad();
        }
        return total;
    }

    // Devuelve una representación en texto del pedido
    @Override
    public String toString() {
        return "Pedido de Cliente: " + codigoCliente + 
               " | Vendedor: " + (codigoVendedor.isEmpty() ? "Sin asignar" : codigoVendedor) + 
               " | Estado: " + estado + 
               " | Fecha: " + new java.text.SimpleDateFormat("dd/MM/yyyy").format(fecha);
    }
    // --------------------------------------------------------------
// Método para confirmar el pedido (cambia estado y fecha)
// --------------------------------------------------------------
// Confirmar sin vendedor (básico)
public void confirmar() {
    this.estado = "Confirmado";
    this.fecha = new java.util.Date();
}

// Confirmar asignando vendedor
public void confirmar(String codigoVendedor) {
    this.estado = "Confirmado";
    this.codigoVendedor = codigoVendedor;
    this.fecha = new java.util.Date();
}


}
