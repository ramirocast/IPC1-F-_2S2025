package workers;

import model.batalla;
import model.personaje;

import javax.swing.*;
import java.util.Random;

public class trabajadorBatalla extends SwingWorker<Void, String> {
    private final personaje p1;
    private final personaje p2;
    private final batalla batalla;
    private volatile boolean running = true;
    private final Random rand = new Random();

    public trabajadorBatalla(personaje p1, personaje p2, batalla b) {
        this.p1 = p1;
        this.p2 = p2;
        this.batalla = b;
    }

    @Override
    protected Void doInBackground() throws Exception {
        // cada personaje ataca en su propio "ritmo" basado en velocidad.
        Thread t1 = new Thread(() -> ataqueLoop(p1, p2));
        Thread t2 = new Thread(() -> ataqueLoop(p2, p1));
        t1.start();
        t2.start();

        // esperar a que uno muera
        while (running && p1.estaVivo() && p2.estaVivo()) {
            if (isCancelled()) { running = false; break; }
            Thread.sleep(50);
        }

        // finalizar
        running = false;
        t1.interrupt();
        t2.interrupt();

        personaje winner = p1.estaVivo() ? p1 : (p2.estaVivo() ? p2 : null);
        if (winner != null) {
            winner.addWin();
            if (winner == p1) p2.addLoss(); else p1.addLoss();
            batalla.setGanador(winner);
            publish("Resultado: ganador -> " + winner.getNombre());
        } else {
            publish("Resultado: Empate");
        }

        return null;
    }

    private void ataqueLoop(personaje atacante, personaje defensor) {
        try {
            while (running && atacante.estaVivo() && defensor.estaVivo()) {
                // tiempo entre ataques proporcional a velocidad (más velocidad => menos espera)
                long delay = Math.max(20L, 1000L / Math.max(1, atacante.getVelocidad()));
                Thread.sleep(delay);

                if (!running || !atacante.estaVivo() || !defensor.estaVivo()) break;

                // determinar esquiva
                int dodgeChance = Math.min(100, defensor.getAgilidad() * 10); // 1..10 -> 10..100%
                int roll = rand.nextInt(100) + 1;
                if (roll <= dodgeChance) {
                    String msg = atacante.getNombre() + " atacó a " + defensor.getNombre() + " - Falló (esquiva)";
                    batalla.addLog(msg);
                    publish(msg);
                    continue;
                }

                // calcular daño bruto y aplicar
                int raw = atacante.getAtaque();
                int applied = defensor.recibirDanio(raw);

                String msg = atacante.getNombre() + " atacó a " + defensor.getNombre()
                        + " - Daño: " + applied + " HP restante: " + defensor.getHp();
                batalla.addLog(msg);
                publish(msg);

                if (!defensor.estaVivo()) {
                    String fin = defensor.getNombre() + " ha sido derrotado por " + atacante.getNombre();
                    batalla.addLog(fin);
                    publish(fin);
                    running = false;
                    break;
                }
            }
        } catch (InterruptedException e) {
            // salir
        }
    }

    @Override
    protected void process(java.util.List<String> chunks) {
        // subir actualizaciones al UI (el UI debe suscribirse con un PropertyChangeListener o pasar un Consumer)
        // En este diseño, quien llame a execute() debe anidar un PropertyChangeListener para mostrar logs;
        // también se puede conectar con publish/process: aquí publicamos strings, y la UI obtiene publish -> process
        // quien cree la instancia debe acceder a get() si necesita esperar al final.
        // nada adicional aquí; Swing llamará a process() en EDT y esto es suficiente.
    }
}
