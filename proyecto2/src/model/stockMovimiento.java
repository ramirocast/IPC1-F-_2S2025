package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class stockMovimiento implements Serializable {
    private String codigoProducto;
    private String tipoMovimiento; // "Entrada" o "Salida"
    private int cantidad;
    private String fecha;

    public stockMovimiento(String codigoProducto, String tipoMovimiento, int cantidad) {
        this.codigoProducto = codigoProducto;
        this.tipoMovimiento = tipoMovimiento;
        this.cantidad = cantidad;
        this.fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
    }

    public String getCodigoProducto() { return codigoProducto; }
    public String getTipoMovimiento() { return tipoMovimiento; }
    public int getCantidad() { return cantidad; }
    public String getFecha() { return fecha; }

    @Override
    public String toString() {
        return "[" + fecha + "] " + tipoMovimiento + " " + cantidad + " unidades de " + codigoProducto;
    }
}
