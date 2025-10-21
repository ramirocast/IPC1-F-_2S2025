package model;

public class productoGeneral extends producto {
    private String material;

    public productoGeneral(String codigo, String nombre, String material) {
        super(codigo, nombre, "General");
        this.material = material;
    }

    @Override
    public String getAtributoEspecifico() {
        return "Material: " + material;
    }

    @Override
    public void setAtributoEspecifico(String valor) {
        this.material = valor;
    }
}