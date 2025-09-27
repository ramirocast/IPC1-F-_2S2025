/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ramirito
 */
import java.time.LocalDateTime;

public class bitacora {
    String fechaHora;
    String accion;
    boolean correcta;
    String usuario;

    public bitacora(String accion, boolean correcta, String usuario) {
        this.fechaHora = LocalDateTime.now().toString(); // guarda fecha y hora actual
        this.accion = accion;
        this.correcta = correcta;
        this.usuario = usuario;
    }

    public void mostrarInfo() {
        System.out.println("[" + fechaHora + "] Usuario: " + usuario +
                           " | Acción: " + accion +
                           " | Resultado: " + (correcta ? "Correcta" : "Errónea"));
    }
}
