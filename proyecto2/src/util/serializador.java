package util;

import java.io.*;

public class serializador {
    public static void guardarEstado(Object obj, String archivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object cargarEstado(String archivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}