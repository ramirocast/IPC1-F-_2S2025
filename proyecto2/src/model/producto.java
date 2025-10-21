package model;

import java.io.Serializable;

public abstract class producto implements Serializable {
    protected String codigo;
    protected String nombre;
    protected String categoria;
    protected int stock = 0;

    public producto(String codigo, String nombre, String categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getStock() {
        return stock;
    }

    public void agregarStock(int cantidad) {
        stock += cantidad;
    }

    public boolean reducirStock(int cantidad) {
        if (stock >= cantidad) {
            stock -= cantidad;
            return true;
        }
        return false;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public abstract String getAtributoEspecifico();

    public abstract void setAtributoEspecifico(String valor);
}