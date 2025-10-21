package hilo;

import util.administradorDatos;

import java.util.Date;

public class monitorPedidos extends Thread {
    private administradorDatos data;
    private boolean running = true;

    public monitorPedidos(administradorDatos data) {
        this.data = data;
    }

    @Override
    public void run() {
        while (running) {
            System.out.println("Pedidos Pendientes: " + data.getPedidosPendientesCount() + " - Procesando... " + new Date());
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void detener() {
        running = false;
    }
}
