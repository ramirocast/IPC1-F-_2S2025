package model;

import java.util.ArrayList;
import java.util.List;

public class historial {
    private final List<batalla> batallas = new ArrayList<>();

    public synchronized void add(batalla b) { batallas.add(b); }
    public synchronized List<batalla> getAll() { return new ArrayList<>(batallas); }
}
