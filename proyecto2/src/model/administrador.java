package model;

public class administrador extends usuario {
    public administrador(String codigo, String nombre, String genero, String contraseña) {
        super(codigo, nombre, genero, contraseña);
    }

    @Override
    public String getRol() {
        return "Administrador";
    }
}
