package controlador;

import model.*;
import vista.*;

public class controlador {
    private administradorDatos data;

    public controlador(administradorDatos data) {
        this.data = data;
    }

    public void iniciarSesion(String codigo, String contrasena) {
        usuario u = data.buscarUsuario(codigo);
        if (u == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }
        if (!u.getContrasena().equals(contrasena)) {
            System.out.println("Contraseña incorrecta.");
            return;
        }

        data.agregarBitacora(new bitacora(u.getCodigo(), "Login", "Inicio de sesión exitoso"));

        switch (u.getTipo()) {
            case "ADMIN": new vistaAdmin(new controlAdmin(data, (administrador) u)); break;
            case "VENDEDOR": new vistaVendedor(new controlVendedor(data, (vendedor) u)); break;
            case "CLIENTE": new vistaCliente(new controlCliente(data, (cliente) u)); break;
        }
    }
}
