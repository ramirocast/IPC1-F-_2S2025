package model;

public class cliente extends usuario {
    private String cumpleaños;

    public cliente(String codigo, String nombre, String genero, String cumpleaños, String contraseña) {
        super(codigo, nombre, genero, contraseña);
        this.cumpleaños = cumpleaños;
    }

    public String getCumpleaños() {
        return cumpleaños;
    }

    public void setCumpleaños(String cumpleaños) {
        this.cumpleaños = cumpleaños;
    }

    @Override
    public String getRol() {
        return "Cliente";
    }
}
