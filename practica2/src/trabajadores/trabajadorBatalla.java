   package trabajadores;

import model.batalla;
import model.personaje;

import javax.swing.*;
import java.util.Random;

public class trabajadorBatalla extends SwingWorker<Void, String> {

    private final personaje p1;
    private final personaje p2;
    private final batalla batalla;
    private final Random rand = new Random();

    public trabajadorBatalla(personaje p1, personaje p2, batalla b) {
        this.p1 = p1;
        this.p2 = p2;
        this.batalla = b;
    }

    @Override
    protected Void doInBackground() throws Exception {
        // loop principal de la batalla por turnos
        while (!isCancelled() && p1.estaVivo() && p2.estaVivo()) {

            // ataque de p1
            atacar(p1, p2);
            if (!p2.estaVivo() || isCancelled()) break;

            // ataque de p2
            atacar(p2, p1);
            if (!p1.estaVivo() || isCancelled()) break;

            // retraso entre rondas para que no saturen los logs
            Thread.sleep(100);
        }

        // determinar ganador
        personaje winner = p1.estaVivo() ? p1 : (p2.estaVivo() ? p2 : null);

        if (winner != null) {
            winner.addWin();
            if (winner == p1) p2.addLoss(); else p1.addLoss();
            batalla.setGanador(winner);
            batalla.addLog("La batalla ha finalizado. Ganador: " + winner.getNombre());
            publish("Resultado: Ganador -> " + winner.getNombre());
        } else {
            batalla.addLog("La batalla ha finalizado sin ganador (empate).");
            publish("Resultado: Empate");
        }

        batalla.addLog("=== FIN DE BATALLA ===");
        publish("=== FIN DE BATALLA ===");
        return null;
    }

    private void atacar(personaje atacante, personaje defensor) throws InterruptedException {
        if (!atacante.estaVivo() || !defensor.estaVivo() || isCancelled()) return;

        // tiempo proporcional a velocidad
        long delay = Math.max(20L, 1000L / Math.max(1, atacante.getVelocidad()));
        Thread.sleep(delay);

        // esquiva
        int dodgeChance = Math.min(100, defensor.getAgilidad() * 10);
        int roll = rand.nextInt(100) + 1;
        if (roll <= dodgeChance) {
            String msg = atacante.getNombre() + " atacó a " + defensor.getNombre() + " - Falló (esquiva)";
            batalla.addLog(msg);
            publish(msg);
            return;
        }

        // aplicar daño
        int daño = defensor.recibirDano(atacante.getAtaque());
        String msg = atacante.getNombre() + " atacó a " + defensor.getNombre()
                + " - Daño: " + daño + " HP restante: " + defensor.getHp();
        batalla.addLog(msg);
        publish(msg);

        if (!defensor.estaVivo()) {
            String fin = defensor.getNombre() + " ha sido derrotado por " + atacante.getNombre();
            batalla.addLog(fin);
            publish(fin);
        }
    }

    @Override
    protected void process(java.util.List<String> chunks) {
        // los logs se publican al UI desde aquí
        // la UI debe agregar un PropertyChangeListener o usar publish/process
    }
}
