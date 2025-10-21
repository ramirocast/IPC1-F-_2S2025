package model;

public class vendedor extends usuario {
    private int ventasConfirmadas = 0;

    public vendedor(String codigo, String nombre, String genero, String contraseña) {
        super(codigo, nombre, genero, contraseña);
    }

    public int getVentasConfirmadas() {
        return ventasConfirmadas;
    }

    public void incrementarVentas() {
        ventasConfirmadas++;
    }

    @Override
    public String getRol() {
        return "Vendedor";
    }
}
