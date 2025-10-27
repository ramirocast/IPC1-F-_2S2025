package model;

public class vendedor extends usuario {
    private int ventasConfirmadas;

    public vendedor(String codigo, String nombre, String contrasena) {
        super(codigo, nombre, contrasena, "VENDEDOR");
        this.ventasConfirmadas = 0;
    }

    public int getVentasConfirmadas() { return ventasConfirmadas; }
    public void incrementarVentas() { ventasConfirmadas++; }
}
