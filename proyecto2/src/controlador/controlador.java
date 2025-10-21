package com.sancarlista.shop.controller;

import com.sancarlista.shop.model.Usuario;
import com.sancarlista.shop.thread.MonitorEstadisticas;
import com.sancarlista.shop.thread.MonitorPedidos;
import com.sancarlista.shop.thread.MonitorSesiones;
import util.administradorDatos;
import com.sancarlista.shop.view.AdminView;
import com.sancarlista.shop.view.ClienteView;
import com.sancarlista.shop.view.VendedorView;

import javax.swing.JOptionPane;

public class AuthController {
    private administradorDatos data;
    private MonitorSesiones monitorSesiones;
    private MonitorPedidos monitorPedidos;
    private MonitorEstadisticas monitorEstadisticas;

    public AuthController(DataManager data) {
        this.data = data;
    }

    public void login(String codigo, String contraseña) {
        Usuario user = data.buscarUsuario(codigo);
        if (user != null && user.getContraseña().equals(contraseña)) {
            data.incrementarUsuariosActivos();
            data.agregarBitacora(new BitacoraEntry(user.getRol(), codigo, "LOGIN", "EXITOSA", "Usuario inició sesión"));

            // Iniciar hilos si no están
            if (monitorSesiones == null) {
                monitorSesiones = new MonitorSesiones(data);
                monitorSesiones.start();
                monitorPedidos = new MonitorPedidos(data);
                monitorPedidos.start();
                monitorEstadisticas = new MonitorEstadisticas(data);
                monitorEstadisticas.start();
            }

            if (user instanceof Administrador) {
                new AdminView(new AdminController(data, user.getCodigo()));
            } else if (user instanceof Vendedor) {
                new VendedorView(new VendedorController(data, user.getCodigo()));
            } else if (user instanceof Cliente) {
                new ClienteView(new ClienteController(data, user.getCodigo()));
            }
        } else {
            data.agregarBitacora(new BitacoraEntry("DESCONOCIDO", codigo, "LOGIN", "FALLIDA", "Intento de login fallido"));
            JOptionPane.showMessageDialog(null, "Credenciales inválidas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void logout(String codigo, JFrame frame) {
        data.decrementarUsuariosActivos();
        data.agregarBitacora(new BitacoraEntry(data.buscarUsuario(codigo).getRol(), codigo, "LOGOUT", "EXITOSA", "Usuario cerró sesión"));
        frame.dispose();
        // No detener hilos, siguen corriendo hasta salida total
    }
}