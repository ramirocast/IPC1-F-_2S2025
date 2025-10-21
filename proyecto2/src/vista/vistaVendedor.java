package vista;

import controlador.controlVendedor;
import controlador.controlador;
import model.cliente;
import model.pedido;
import model.producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class vistaVendedor extends JFrame {
    private controlVendedor controller;
    private controlador authController;
    private String vendedorCode;

    public vistaVendedor(controlVendedor controller) {
        this.controller = controller;
        this.vendedorCode = controller.vendedorCode;
        authController = new controlador(controller.data);

        setTitle("Vendedor - Sancarlista Shop");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab Productos (vista)
        JPanel productosPanel = new JPanel(new BorderLayout());
        JTable productosTable = new JTable();
        actualizarTablaProductos(productosTable);
        productosPanel.add(new JScrollPane(productosTable), BorderLayout.CENTER);
        tabbedPane.addTab("Productos", productosPanel);

        // Tab Stock
        JPanel stockPanel = new JPanel(new BorderLayout());
        JTable stockTable = new JTable();
        actualizarTablaStock(stockTable);
        stockPanel.add(new JScrollPane(stockTable), BorderLayout.CENTER);

        JPanel stockButtons = new JPanel();
        JButton agregarStock = new JButton("Agregar Stock");
        agregarStock.addActionListener(e -> agregarStockDialog(stockTable));
        stockButtons.add(agregarStock);

        JButton cargarStockCSV = new JButton("Cargar CSV");
        cargarStockCSV.addActionListener(e -> cargarStockCSVDialog());
        stockButtons.add(cargarStockCSV);

        JButton historialStock = new JButton("Historial CSV");
        historialStock.addActionListener(e -> generarHistorialStockCSVDialog());
        stockButtons.add(historialStock);

        stockPanel.add(stockButtons, BorderLayout.SOUTH);
        tabbedPane.addTab("Stock", stockPanel);

        // Tab Clientes
        JPanel clientesPanel = new JPanel(new BorderLayout());
        JTable clientesTable = new JTable();
        actualizarTablaClientes(clientesTable);
        clientesPanel.add(new JScrollPane(clientesTable), BorderLayout.CENTER);

        JPanel clientesButtons = new JPanel();
        JButton crearCliente = new JButton("Crear");
        crearCliente.addActionListener(e -> crearClienteDialog());
        clientesButtons.add(crearCliente);

        // Similar for actualizar, eliminar, cargar CSV

        clientesPanel.add(clientesButtons, BorderLayout.SOUTH);
        tabbedPane.addTab("Clientes", clientesPanel);

        // Tab Pedidos
        JPanel pedidosPanel = new JPanel(new BorderLayout());
        JTable pedidosTable = new JTable();
        actualizarTablaPedidos(pedidosTable);
        pedidosPanel.add(new JScrollPane(pedidosTable), BorderLayout.CENTER);

        JPanel pedidosButtons = new JPanel();
        JButton confirmarPedido = new JButton("Confirmar");
        confirmarPedido.addActionListener(e -> confirmarPedido(pedidosTable));
        pedidosButtons.add(confirmarPedido);

        pedidosPanel.add(pedidosButtons, BorderLayout.SOUTH);
        tabbedPane.addTab("Pedidos", pedidosPanel);

        add(tabbedPane);

        JButton logout = new JButton("Logout");
        logout.addActionListener(e -> authController.logout(vendedorCode, this));
        add(logout, BorderLayout.NORTH);

        setVisible(true);
    }

    // Implement actualizarTablaProductos, stock, clientes, pedidos similar a AdminView

    // Dialogs for actions
}