package model;

public class administrador extends usuario {
    public administrador(String codigo, String nombre, String contrasena) {
        super(codigo, nombre, contrasena, "ADMIN");
    }
}
