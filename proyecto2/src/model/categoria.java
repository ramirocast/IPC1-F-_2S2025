package model;

import java.io.Serializable;

public class categoria implements Serializable {
    private String nombre;
    private String descripcion;

    public categoria(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
    public String toString() {
        return nombre + " - " + descripcion;
    }
}
