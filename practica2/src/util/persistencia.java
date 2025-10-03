package util;

import model.batalla;
import model.historial;
import model.personaje;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class persistencia {

    // Guarda personajes y batallas en formato simple
    public static void save(String path, List<personaje> personajes, historial historial) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(Path.of(path))) {
            for (personaje p : personajes) {
                // PERSON|id|nombre|arma|hp|ataque|velocidad|agilidad|defensa|wins|losses
                bw.write(String.format("PERSON|%d|%s|%s|%d|%d|%d|%d|%d|%d\n",
                        p.getId(),
                        escape(p.getNombre()),
                        escape(p.getArma()),
                        p.getHp(), p.getAtaque(), p.getVelocidad(), p.getAgilidad(), p.getDefensa(),
                        p.getWins(), p.getLosses()
                ));
            }
            for (batalla b : historial.getAll()) {
                bw.write(b.toPersistHeader() + "\n");
                bw.write(b.toPersistLog(b.getId()) + "\n");
            }
        }
    }

    // Carga personajes y batallas; retorna map con personajes y historial
    public static Map<String, Object> load(String path) throws IOException {
        List<personaje> personajes = new ArrayList<>();
        historial historial = new historial();

        if (!Files.exists(Path.of(path))) return Map.of("personajes", personajes, "historial", historial);

        List<String> lines = Files.readAllLines(Path.of(path));
        Map<Integer, personaje> byId = new HashMap<>();
        Map<Integer, batalla> byBattleId = new HashMap<>();

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            String[] parts = splitPreservingEscapes(line, "\\|");

// Validación: asegurarse de que haya suficientes campos
if (parts.length < 11) {  // necesitamos al menos 11 elementos para PERSON
    System.out.println("Linea invalida, faltan campos: " + line);
    continue;  // saltar esta línea y continuar con la siguiente
}

            if (parts.length == 0) continue;
            String type = parts[0];
            if (type.equals("PERSON")) {
                // PERSON|id|nombre|arma|hp|ataque|velocidad|agilidad|defensa|wins|losses
                int id = Integer.parseInt(parts[1]);
                String nombre = unescape(parts[2]);
                String arma = unescape(parts[3]);
                int hp = Integer.parseInt(parts[4]);
                int ataque = Integer.parseInt(parts[5]);
                int vel = Integer.parseInt(parts[6]);
                int agi = Integer.parseInt(parts[7]);
                int def = Integer.parseInt(parts[8]);
                int wins = Integer.parseInt(parts[9]);
                int losses = Integer.parseInt(parts[10]);
                personaje p = new personaje(id, nombre, arma, hp, ataque, vel, agi, def, wins, losses);
                personajes.add(p);
                byId.put(id, p);
            } else if (type.equals("BATTLE")) {
                // BATTLE|id|fechaIso|nombreA|nombreB|winnerName
                int bid = Integer.parseInt(parts[1]);
                String fecha = parts[2];
                String nombreA = parts[3];
                String nombreB = parts[4];
                String winner = parts.length > 5 ? parts[5] : "";
                // buscar personajes por nombre (si existen) o crear "fantasma" mínimal
                personaje a = encontrarNombre(personajes, nombreA);
                personaje b = encontrarNombre(personajes, nombreB);
                batalla bat = new batalla(bid, fecha, a, b, winner);
                byBattleId.put(bid, bat);
                historial.add(bat);
            } else if (type.equals("LOG")) {
                // LOG|battleId|line1::line2::...
                int bid = Integer.parseInt(parts[1]);
                String rest = line.substring(line.indexOf(parts[2])); // safer
                // but we used parts split; easier: reconstruct after second token
                int idx = nthIndexOf(line, '|', 2);
                if (idx >= 0) {
                    String logs = line.substring(idx + 1);
                    String[] items = logs.split("::");
                    batalla bat = byBattleId.get(bid);
                    if (bat != null) {
                        for (String it : items) {
                            if (it.trim().isEmpty()) continue;
                            bat.addLog(unescape(it));
                        }
                    }
                }
            }
        }

        return Map.of("personajes", personajes, "historial", historial);
    }

    private static personaje encontrarNombre(List<personaje> ps, String name) {
        for (personaje p : ps) if (p.getNombre().equalsIgnoreCase(name)) return p;
        // si no existe, crear un "fantasma" con valores neutros (no se recomienda; mejor mantener nombre en historial)
        return new personaje(name, "Desconocido", 100, 10, 1, 1, 1);
    }

    private static String escape(String s) { return s.replace("|", "/|").replace("::", "/::"); }
    private static String unescape(String s) { return s.replace("/|", "|").replace("/::", "::"); }

    private static String[] splitPreservingEscapes(String s, String delimRegex) {
        // split simple por | (no manejo de escapes complejos)
        return s.split("\\|");
    }

    private static int nthIndexOf(String s, char c, int n) {
        int pos = -1;
        for (int i = 0; i < n; i++) {
            pos = s.indexOf(c, pos + 1);
            if (pos == -1) return -1;
        }
        return pos;
    }
}

