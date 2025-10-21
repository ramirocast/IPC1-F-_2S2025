package model;

public class productoAlimento extends producto {
    private String fechaCaducidad;

    public productoAlimento(String codigo, String nombre, String fechaCaducidad) {
        super(codigo, nombre, "Alimento");
        this.fechaCaducidad = fechaCaducidad;
    }

    @Override
    public String getAtributoEspecifico() {
        return "Fecha de caducidad: " + fechaCaducidad;
    }

    @Override
    public void setAtributoEspecifico(String valor) {
        this.fechaCaducidad = valor;
    }
}
