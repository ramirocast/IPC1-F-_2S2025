package vista;

import controlador.controlVendedor;
import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class vistaVendedor extends JFrame {
    private controlVendedor controller;
    private DefaultTableModel productosModel, pedidosModel;
    private JTable productosTable, pedidosTable;

    public vistaVendedor(controlVendedor controller) {
        this.controller = controller;
        setTitle("Vendedor - Sancarlista Shop");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initUI();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initUI() {
        JTabbedPane tabs = new JTabbedPane();

        JPanel prodPanel = new JPanel(new BorderLayout());
        productosModel = new DefaultTableModel(new String[]{"Codigo", "Nombre", "Categoria", "Stock"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        productosTable = new JTable(productosModel);
        prodPanel.add(new JScrollPane(productosTable), BorderLayout.CENTER);

        JPanel stockPanel = new JPanel();
        JTextField codigoField = new JTextField(8), cantidadField = new JTextField(5);
        JButton agregarStock = new JButton("Agregar Stock");
        agregarStock.addActionListener(e -> {
            try {
                controller.agregarStock(codigoField.getText().trim(), Integer.parseInt(cantidadField.getText().trim()));
                refreshProductos();
            } catch (Exception ignored) {}
        });
        JButton cargarCSV = new JButton("Cargar CSV");
        cargarCSV.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                controller.cargarStockCSV(fc.getSelectedFile().getAbsolutePath());
                refreshProductos();
            }
        });
        stockPanel.add(new JLabel("Codigo:")); stockPanel.add(codigoField);
        stockPanel.add(new JLabel("Cantidad:")); stockPanel.add(cantidadField);
        stockPanel.add(agregarStock); stockPanel.add(cargarCSV);
        prodPanel.add(stockPanel, BorderLayout.SOUTH);
        tabs.addTab("Productos/Stock", prodPanel);

        JPanel pedidosPanel = new JPanel(new BorderLayout());
        pedidosModel = new DefaultTableModel(new String[]{"Cliente", "Fecha", "Estado"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        pedidosTable = new JTable(pedidosModel);
        pedidosPanel.add(new JScrollPane(pedidosTable), BorderLayout.CENTER);

        JButton confirmar = new JButton("Confirmar Pedido");
        confirmar.addActionListener(e -> {
            int r = pedidosTable.getSelectedRow();
            if (r < 0) return;
            pedido p = controller.getPedidosPendientes()[r];
            controller.confirmarPedido(p);
            refreshPedidos();
            refreshProductos();
        });
        pedidosPanel.add(confirmar, BorderLayout.SOUTH);
        tabs.addTab("Pedidos", pedidosPanel);

        add(tabs, BorderLayout.CENTER);
        refreshProductos();
        refreshPedidos();
    }

    private void refreshProductos() {
        productosModel.setRowCount(0);
        for (producto p : controller.getProductos())
            productosModel.addRow(new Object[]{p.getCodigo(), p.getNombre(), p.getCategoria(), p.getStock()});
    }

    private void refreshPedidos() {
        pedidosModel.setRowCount(0);
        pedido[] pendientes = controller.getPedidosPendientes();
        for (pedido p : pendientes)
            pedidosModel.addRow(new Object[]{p.getCodigoCliente(), p.getFecha(), p.getEstado()});
    }
}
