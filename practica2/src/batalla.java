
 package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class batalla {
    private final int id;
    private static int nextId = 1;
    private final LocalDateTime fechaInicio;
    private final personaje a;
    private final personaje b;
    private personaje ganador; // puede ser null si empate
    private final List<String> bitacora = new ArrayList<>();

    public batalla(personaje a, personaje b) {
        this.id = nextId++;
        this.fechaInicio = LocalDateTime.now();
        this.a = a;
        this.b = b;
    }

    // constructor para cargar
    public batalla(int id, String fechaIso, personaje a, personaje b, String ganadorNombre) {
        this.id = id;
        this.fechaInicio = LocalDateTime.parse(fechaIso);
        this.a = a;
        this.b = b;
        // ganador se asocia por nombre en persistencia
        this.ganador = null;
        if (ganadorNombre != null && ganadorNombre.length() > 0) {
            if (ganadorNombre.equalsIgnoreCase(a.getNombre())) this.ganador = a;
            if (ganadorNombre.equalsIgnoreCase(b.getNombre())) this.ganador = b;
        }
    }

    public int getId() { return id; }
    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public personaje getA() { return a; }
    public personaje getB() { return b; }
    public personaje getGanador() { return ganador; }
    public void setGanador(personaje p) { this.ganador = p; }

    public synchronized void addLog(String s) { bitacora.add("[" + fecha() + "] " + s); }
    public synchronized List<String> getBitacora() { return new ArrayList<>(bitacora); }

    private String fecha() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return df.format(LocalDateTime.now());
    }

    public String toPersistHeader() {
        DateTimeFormatter df = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String winner = ganador == null ? "" : ganador.getNombre();
        return String.format("BATTLE|%d|%s|%s|%s|%s", id, df.format(fechaInicio), a.getNombre(), b.getNombre(), winner);
    }

    public String toPersistLog(int battleId) {
        // join logs as single line separada por :: para evitar conflicto de pipes
        StringBuilder sb = new StringBuilder();
        sb.append("LOG|").append(battleId).append("|");
        for (String l : bitacora) {
            sb.append(escape(l)).append("::");
        }
        return sb.toString();
    }

    private String escape(String s) { return s.replace("|", "/|").replace("::", "/::"); }
}
