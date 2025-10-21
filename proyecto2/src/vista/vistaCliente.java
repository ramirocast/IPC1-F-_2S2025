package vista;

import controlador.controlCliente;
import controlador.controlador;
import model.itemPedido;
import model.pedido;
import model.producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class vistaCliente extends JFrame {
    private controlCliente controller;
    private controlador authController;
    private String clienteCode;

    public vistaCliente(controlCliente controller) {
        this.controller = controller;
        this.clienteCode = controller.clienteCode;
        authController = new controlador(controller.data);

        setTitle("Cliente - Sancarlista Shop");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab Productos
        JPanel productosPanel = new JPanel(new BorderLayout());
        JTable productosTable = new JTable();
        actualizarTablaProductos(productosTable);
        productosPanel.add(new JScrollPane(productosTable), BorderLayout.CENTER);

        JPanel productosButtons = new JPanel();
        JButton agregarCarrito = new JButton("Agregar al Carrito");
        agregarCarrito.addActionListener(e -> agregarCarritoDialog(productosTable));
        productosButtons.add(agregarCarrito);

        productosPanel.add(productosButtons, BorderLayout.SOUTH);
        tabbedPane.addTab("Productos", productosPanel);

        // Tab Carrito
        JPanel carritoPanel = new JPanel(new BorderLayout());
        JTable carritoTable = new JTable();
        actualizarTablaCarrito(carritoTable);
        carritoPanel.add(new JScrollPane(carritoTable), BorderLayout.CENTER);

        JPanel carritoButtons = new JPanel();
        JButton actualizarCantidad = new JButton("Actualizar Cantidad");
        actualizarCantidad.addActionListener(e -> actualizarCantidadDialog(carritoTable));
        carritoButtons.add(actualizarCantidad);

        JButton eliminarCarrito = new JButton("Eliminar");
        eliminarCarrito.addActionListener(e -> eliminarCarrito(carritoTable));
        carritoButtons.add(eliminarCarrito);

        JButton realizarPedido = new JButton("Realizar Pedido");
        realizarPedido.addActionListener(e -> controller.realizarPedido());
        carritoButtons.add(realizarPedido);

        carritoPanel.add(carritoButtons, BorderLayout.SOUTH);
        tabbedPane.addTab("Carrito", carritoPanel);

        // Tab Historial
        JPanel historialPanel = new JPanel(new BorderLayout());
        JTable historialTable = new JTable();
        actualizarTablaHistorial(historialTable);
        historialPanel.add(new JScrollPane(historialTable), BorderLayout.CENTER);
        tabbedPane.addTab("Historial", historialPanel);

        add(tabbedPane);

        JButton logout = new JButton("Logout");
        logout.addActionListener(e -> authController.logout(clienteCode, this));
        add(logout, BorderLayout.NORTH);

        setVisible(true);
    }

    // Implement actualizarTablaProductos, carrito, historial

    // Dialogs for agregar, actualizar, eliminar
}
