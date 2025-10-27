package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class producto implements Serializable {
    private String codigo;
    private String nombre;
    private String categoria;
    private double precio;
    private int stock;
    private String atributoEspecifico; // fecha/material/meses etc.
    private String lastUpdate;         // NUEVO: última actualización de stock

    public producto(String codigo, String nombre, String categoria, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = 0;
        this.atributoEspecifico = "";
        touchUpdate();
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }
    public String getAtributoEspecifico() { return atributoEspecifico == null ? "" : atributoEspecifico; }
    public String getLastUpdate() { return lastUpdate; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setAtributoEspecifico(String valor) { this.atributoEspecifico = valor; }

    public void agregarStock(int cantidad) {
        if (cantidad > 0) {
            this.stock += cantidad;
            touchUpdate();
        }
    }

    public boolean reducirStock(int cantidad) {
        if (cantidad <= 0) return false;
        if (cantidad > stock) return false;
        stock -= cantidad;
        touchUpdate();
        return true;
    }

    private void touchUpdate() {
        this.lastUpdate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
    }

    @Override
    public String toString() { return codigo + " - " + nombre + " (" + categoria + ") Stock: " + stock; }
}
