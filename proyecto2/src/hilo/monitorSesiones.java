package hilo;

public class monitorSesiones extends Thread {
    private boolean activo = true;

    @Override
    public void run() {
        while (activo) {
            System.out.println("[MonitorSesiones] Sesión activa...");
            try {
                Thread.sleep(15000);
            } catch (InterruptedException ignored) {}
        }
    }

    public void detener() {
        activo = false;
    }
}
