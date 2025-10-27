package util;

import java.io.*;

public class serializador {
    public static void guardarEstado(Object data, String rutaArchivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(data);
            System.out.println("[Serializador] Estado guardado correctamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar estado: " + e.getMessage());
        }
    }

    public static Object cargarEstado(String rutaArchivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            System.out.println("[Serializador] Estado cargado correctamente.");
            return ois.readObject();
        } catch (Exception e) {
            System.out.println("[Serializador] No se pudo cargar estado, se iniciará nuevo.");
            return null;
        }
    }
}
