package model;

import java.io.Serializable;
import java.util.Date;

public class ingresoStock implements Serializable {
    private Date fecha;
    private String hora;
    private String codigoVendedor;
    private String codigoProducto;
    private int cantidad;

    public ingresoStock(String codigoVendedor, String codigoProducto, int cantidad) {
        this.fecha = new Date();
        this.hora = new java.text.SimpleDateFormat("HH:mm:ss").format(fecha);
        this.codigoVendedor = codigoVendedor;
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
    }

    // Getters
    public Date getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public String getCodigoVendedor() {
        return codigoVendedor;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public int getCantidad() {
        return cantidad;
    }
}