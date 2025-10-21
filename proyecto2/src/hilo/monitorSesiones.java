package hilo;

import util.administradorDatos;

public class monitorSesiones extends Thread {
    private administradorDatos data;
    private boolean running = true;

    public monitorSesiones(administradorDatos data) {
        this.data = data;
    }

    @Override
    public void run() {
        while (running) {
            System.out.println("Usuarios Activos: " + data.getUsuariosActivos() + " - Última actividad: " + new Date());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void detener() {
        running = false;
    }
}