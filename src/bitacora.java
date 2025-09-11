/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ramirito
 */
public class bitacora {
    // Clase Bitacora
// Registra las acciones que hace el usuario durante la ejecución

    String fechaHora;
    String accion;
    boolean correcta;

    public bitacora(String fechaHora, String accion, boolean correcta) {
        this.fechaHora = fechaHora;
        this.accion = accion;
        this.correcta = correcta;
    }

    public void mostrarInfo() {
        System.out.println("[" + fechaHora + "] " + accion + 
                           " -> " + (correcta ? "Correcta" : "Errónea"));
    }
}

    
