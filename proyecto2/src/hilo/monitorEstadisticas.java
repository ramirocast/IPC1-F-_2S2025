package hilo;

import model.administradorDatos;

public class monitorEstadisticas extends Thread {
    private administradorDatos data;
    private boolean activo = true;

    public monitorEstadisticas(administradorDatos data) {
        this.data = data;
    }

    @Override
    public void run() {
        while (activo) {
            int totalUsuarios = data.getAllUsuarios().length;
int totalProductos = data.getProductos().length;
int totalPedidos = data.getHistorialGlobal().length;

            System.out.println("[MonitorEstadisticas] Usuarios: " + totalUsuarios +
                    " | Productos: " + totalProductos +
                    " | Pedidos: " + totalPedidos);
            try {
                Thread.sleep(30000);
            } catch (InterruptedException ignored) {}
        }
    }

    public void detener() {
        activo = false;
    }
}
