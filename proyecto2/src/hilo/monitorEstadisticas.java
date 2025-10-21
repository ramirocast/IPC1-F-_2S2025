package hilo;

import util.administradorDatos;

import java.util.Date;

public class monitorEstadisticas extends Thread {
    private administradorDatos data;
    private boolean running = true;

    public monitorEstadisticas(administradorDatos data) {
        this.data = data;
    }

    @Override
    public void run() {
        while (running) {
            System.out.println("Ventas del día: " + data.getVentasDelDia() + " | Productos registrados: " + data.getProductosRegistrados() + " | " + new Date());
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void detener() {
        running = false;
    }
}