package sancarlistashop;

import controlador.controlador;
import model.administrador;
import util.serializador;
import vista.vistaLogin;
import util.administradorDatos;

import javax.swing.*;

public class main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            administradorDatos data = (administradorDatos) serializador.cargarEstado("data.ser");
            if (data == null) {
                data = new administradorDatos();
                // Inicializa admin
                data.agregarUsuario(new administrador("admin", "Admin Inicial", "M", "IPC1A")); // Ajusta sección
            }
            controlador controlador = new controlador(data);
            new vistaLogin(controlador);
        });
    }
}