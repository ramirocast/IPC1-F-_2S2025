package vista;

import controlador.controlCliente;
import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class vistaCliente extends JFrame {
    private controlCliente controller;
    private JTable productosTable, carritoTable;
    private DefaultTableModel productosModel, carritoModel;

    public vistaCliente(controlCliente controller) {
        this.controller = controller;
        setTitle("Cliente - Sancarlista Shop");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initUI();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initUI() {
        JTabbedPane tabs = new JTabbedPane();

        JPanel catalogo = new JPanel(new BorderLayout());
        productosModel = new DefaultTableModel(new String[]{"Codigo", "Nombre", "Categoria", "Precio", "Stock"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        productosTable = new JTable(productosModel);
        catalogo.add(new JScrollPane(productosTable), BorderLayout.CENTER);

        JPanel catButtons = new JPanel();
        JTextField cantidadField = new JTextField(4);
        JButton agregarBtn = new JButton("Agregar al carrito");
        agregarBtn.addActionListener(e -> {
            int row = productosTable.getSelectedRow();
            if (row < 0) return;
            String codigo = (String) productosModel.getValueAt(row, 0);
            int cantidad;
            try { cantidad = Integer.parseInt(cantidadField.getText()); } catch (Exception ex) { cantidad = 1; }
            controller.agregarAlCarrito(codigo, cantidad);
            refreshCarrito();
        });
        catButtons.add(new JLabel("Cantidad:")); catButtons.add(cantidadField); catButtons.add(agregarBtn);
        catalogo.add(catButtons, BorderLayout.SOUTH);
        tabs.addTab("Catálogo", catalogo);

        JPanel carritoPanel = new JPanel(new BorderLayout());
        carritoModel = new DefaultTableModel(new String[]{"Codigo", "Cantidad"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        carritoTable = new JTable(carritoModel);
        carritoPanel.add(new JScrollPane(carritoTable), BorderLayout.CENTER);

        JPanel cartButtons = new JPanel();
        JButton eliminarBtn = new JButton("Eliminar seleccionado");
        eliminarBtn.addActionListener(e -> {
            int r = carritoTable.getSelectedRow();
            if (r < 0) return;
            controller.eliminarDelCarrito(r);
            refreshCarrito();
        });
        JButton realizar = new JButton("Realizar Pedido");
        realizar.addActionListener(e -> {
            controller.realizarPedido();
            JOptionPane.showMessageDialog(this, "Pedido enviado.");
            refreshCarrito();
        });
        cartButtons.add(eliminarBtn); cartButtons.add(realizar);
        carritoPanel.add(cartButtons, BorderLayout.SOUTH);
        tabs.addTab("Carrito", carritoPanel);

        add(tabs, BorderLayout.CENTER);
        refreshProductos();
        refreshCarrito();
    }

    private void refreshProductos() {
        productosModel.setRowCount(0);
        for (producto p : controller.getProductosDisponibles())
            productosModel.addRow(new Object[]{p.getCodigo(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getStock()});
    }

    private void refreshCarrito() {
        carritoModel.setRowCount(0);
        for (itemPedido it : controller.getCarritoItems())
            carritoModel.addRow(new Object[]{it.getCodigoProducto(), it.getCantidad()});
    }
}
