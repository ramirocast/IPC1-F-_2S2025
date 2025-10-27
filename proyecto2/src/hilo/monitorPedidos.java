package hilo;

import model.administradorDatos;
import model.pedido;

public class monitorPedidos extends Thread {
    private administradorDatos data;
    private boolean activo = true;

    public monitorPedidos(administradorDatos data) {
        this.data = data;
    }

    @Override
    public void run() {
        while (activo) {
            pedido[] pedidos = data.getHistorialGlobal();
long pendientes = 0;
for (int i = 0; i < pedidos.length; i++) {
    if (pedidos[i] != null && "Pendiente".equals(pedidos[i].getEstado())) {
        pendientes++;
    }
}

            System.out.println("[MonitorPedidos] Pedidos pendientes: " + pendientes);
            try {
                Thread.sleep(20000);
            } catch (InterruptedException ignored) {}
        }
    }

    public void detener() {
        activo = false;
    }
}
