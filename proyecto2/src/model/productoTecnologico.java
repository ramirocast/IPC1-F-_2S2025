package model;

public class productoTecnologico extends producto {
    private int mesesGarantia;

    public productoTecnologico(String codigo, String nombre, int mesesGarantia) {
        super(codigo, nombre, "Tecnologia");
        this.mesesGarantia = mesesGarantia;
    }

    @Override
    public String getAtributoEspecifico() {
        return mesesGarantia + " meses de garantía";
    }

    @Override
    public void setAtributoEspecifico(String valor) {
        this.mesesGarantia = Integer.parseInt(valor);
    }
}