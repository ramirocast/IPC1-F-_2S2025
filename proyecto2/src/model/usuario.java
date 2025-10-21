package model;

import java.io.Serializable;

public abstract class usuario implements Serializable {
    protected String codigo;
    protected String nombre;
    protected String genero;
    protected String contraseña;

    public usuario(String codigo, String nombre, String genero, String contraseña) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.genero = genero;
        this.contraseña = contraseña;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getGenero() {
        return genero;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public abstract String getRol();
}