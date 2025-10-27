package model;

import java.io.Serializable;

public abstract class usuario implements Serializable {
    protected String codigo;
    protected String nombre;
    protected String contrasena;
    protected String tipo;

    public usuario(String codigo, String nombre, String contrasena, String tipo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.tipo = tipo;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getContrasena() { return contrasena; }
    public String getTipo() { return tipo; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    @Override
    public String toString() {
        return "[" + tipo + "] " + codigo + " - " + nombre;
    }
}
