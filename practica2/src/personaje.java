package model;

import java.util.concurrent.atomic.AtomicInteger;

public class personaje {
    private static final AtomicInteger ID_GEN = new AtomicInteger(1);

    private final int id;
    private String nombre;
    private String arma;
    private int hp;
    private int ataque;
    private int velocidad;
    private int agilidad;
    private int defensa;

    // estadísticas
    private int wins = 0;
    private int losses = 0;

    public personaje(String nombre, String arma, int hp, int ataque, int velocidad, int agilidad, int defensa) {
        this.id = ID_GEN.getAndIncrement();
        this.nombre = nombre.trim();
        this.arma = arma.trim();
        this.hp = hp;
        this.ataque = ataque;
        this.velocidad = velocidad;
        this.agilidad = agilidad;
        this.defensa = defensa;
    }

    // constructor para cargar con id específico
    public personaje(int id, String nombre, String arma, int hp, int ataque, int velocidad, int agilidad, int defensa, int wins, int losses) {
        this.id = id;
        this.nombre = nombre;
        this.arma = arma;
        this.hp = hp;
        this.ataque = ataque;
        this.velocidad = velocidad;
        this.agilidad = agilidad;
        this.defensa = defensa;
        this.wins = wins;
        this.losses = losses;
        // asegurar que ID_GEN avanza
        int cur;
        do {
            cur = ID_GEN.get();
            if (id >= cur) {}
        } while (!ID_GEN.compareAndSet(cur, Math.max(cur, id + 1)));
    }

    public int getId() { return id; }
    public synchronized String getNombre() { return nombre; }
    public synchronized void setNombre(String nombre) { this.nombre = nombre; }
    public synchronized String getArma() { return arma; }
    public synchronized void setArma(String arma) { this.arma = arma; }
    public synchronized int getHp() { return hp; }
    public synchronized void setHp(int hp) { this.hp = hp; }
    public synchronized int getAtaque() { return ataque; }
    public synchronized void setAtaque(int ataque) { this.ataque = ataque; }
    public synchronized int getVelocidad() { return velocidad; }
    public synchronized void setVelocidad(int velocidad) { this.velocidad = velocidad; }
    public synchronized int getAgilidad() { return agilidad; }
    public synchronized void setAgilidad(int agilidad) { this.agilidad = agilidad; }
    public synchronized int getDefensa() { return defensa; }
    public synchronized void setDefensa(int defensa) { this.defensa = defensa; }

    public synchronized boolean estaVivo() { return hp > 0; }

    // recibe daño; retorna daño real aplicado
    public synchronized int recibirDanio(int rawDamage) {
        // la defensa reduce el daño; siempre al menos 1 de daño si rawDamage > 0
        int reducido = Math.max(1, rawDamage - (defensa / 2));
        hp = Math.max(0, hp - reducido);
        return reducido;
    }

    public synchronized void addWin() { wins++; }
    public synchronized void addLoss() { losses++; }
    public synchronized int getWins() { return wins; }
    public synchronized int getLosses() { return losses; }

    @Override
    public String toString() {
        return id + " | " + nombre + " | " + arma + " | HP:" + hp + " | ATK:" + ataque + " | DEF:" + defensa + " | AGI:" + agilidad + " | SPD:" + velocidad;
    }

    // formato para persistencia
    public String toPersistString() {
        return String.format("PERSON|%d|%s|%s|%d|%d|%d|%d|%d|%d", id, escape(nombre), escape(arma), hp, ataque, velocidad, agilidad, defensa, wins, losses);
    }

    private String escape(String s) {
        return s.replace("|", "/|");
    }

    public static String unescape(String s) {
        return s.replace("/|", "|");
    }
}

