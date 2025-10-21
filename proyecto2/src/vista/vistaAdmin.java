package vista;

import controlador.controlAdmin;
import controlador.controlador;
import model.producto;
import model.vendedor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class vistaAdmin extends JFrame {
    private controlAdmin controller;
    private controlador authController;
    private String adminCode;

    public vistaAdmin(controlAdmin controller) {
        this.controller = controller;
        this.adminCode = controller.adminCode; // Asumir se pasa
        authController = new controlador(controller.data); // For logout

        setTitle("Administrador - Sancarlista Shop");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab Vendedores
        JPanel vendedoresPanel = new JPanel(new BorderLayout());
        JTable vendedoresTable = new JTable();
        actualizarTablaVendedores(vendedoresTable);
        vendedoresPanel.add(new JScrollPane(vendedoresTable), BorderLayout.CENTER);

        JPanel vendedoresButtons = new JPanel();
        JButton crearVendedor = new JButton("Crear");
        crearVendedor.addActionListener(e -> crearVendedorDialog());
        vendedoresButtons.add(crearVendedor);

        JButton actualizarVendedor = new JButton("Actualizar");
        actualizarVendedor.addActionListener(e -> actualizarVendedorDialog(vendedoresTable));
        vendedoresButtons.add(actualizarVendedor);

        JButton eliminarVendedor = new JButton("Eliminar");
        eliminarVendedor.addActionListener(e -> eliminarVendedor(vendedoresTable));
        vendedoresButtons.add(eliminarVendedor);

        JButton cargarVendedoresCSV = new JButton("Cargar CSV");
        cargarVendedoresCSV.addActionListener(e -> cargarVendedoresCSVDIALOG());
        vendedoresButtons.add(cargarVendedoresCSV);

        vendedoresPanel.add(vendedoresButtons, BorderLayout.SOUTH);
        tabbedPane.addTab("Vendedores", vendedoresPanel);

        // Tab Productos
        JPanel productosPanel = new JPanel(new BorderLayout());
        JTable productosTable = new JTable();
        actualizarTablaProductos(productosTable);
        productosPanel.add(new JScrollPane(productosTable), BorderLayout.CENTER);

        JPanel productosButtons = new JPanel();
        JButton crearProducto = new JButton("Crear");
        crearProducto.addActionListener(e -> crearProductoDialog());
        productosButtons.add(crearProducto);

        JButton actualizarProducto = new JButton("Actualizar");
        actualizarProducto.addActionListener(e -> actualizarProductoDialog(productosTable));
        productosButtons.add(actualizarProducto);

        JButton eliminarProducto = new JButton("Eliminar");
        eliminarProducto.addActionListener(e -> eliminarProducto(productosTable));
        productosButtons.add(eliminarProducto);

        JButton cargarProductosCSV = new JButton("Cargar CSV");
        cargarProductosCSV.addActionListener(e -> cargarProductosCSVDIALOG());
        productosButtons.add(cargarProductosCSV);

        JButton verDetalle = new JButton("Ver Detalle");
        verDetalle.addActionListener(e -> verDetalleProducto(productosTable));
        productosButtons.add(verDetalle);

        productosPanel.add(productosButtons, BorderLayout.SOUTH);
        tabbedPane.addTab("Productos", productosPanel);

        // Tab Reportes
        JPanel reportesPanel = new JPanel(new GridLayout(7, 1));
        String[] tipos = {"MasVendidos", "MenosVendidos", "Inventario", "VentasPorVendedor", "ClientesActivos", "Financiero", "PorCaducar"};
        for (String tipo : tipos) {
            JButton btn = new JButton("Generar " + tipo);
            btn.addActionListener(e -> controller.generarReporte(tipo));
            reportesPanel.add(btn);
        }
        tabbedPane.addTab("Reportes", reportesPanel);

        // Tab Bitacora
        JPanel bitacoraPanel = new JPanel(new BorderLayout());
        JTable bitacoraTable = new JTable();
        actualizarTablaBitacora(bitacoraTable);
        bitacoraPanel.add(new JScrollPane(bitacoraTable), BorderLayout.CENTER);

        JPanel bitacoraButtons = new JPanel();
        JButton filtrarBitacora = new JButton("Filtrar");
        filtrarBitacora.addActionListener(e -> filtrarBitacoraDialog(bitacoraTable));
        bitacoraButtons.add(filtrarBitacora);

        JButton exportarBitacoraCSV = new JButton("Exportar CSV");
        exportarBitacoraCSV.addActionListener(e -> exportarBitacoraCSVDialog());
        bitacoraButtons.add(exportarBitacoraCSV);

        JButton exportarBitacoraPDF = new JButton("Exportar PDF");
        exportarBitacoraPDF.addActionListener(e -> exportarBitacoraPDFDialog());
        bitacoraButtons.add(exportarBitacoraPDF);

        bitacoraPanel.add(bitacoraButtons, BorderLayout.SOUTH);
        tabbedPane.addTab("Bitacora", bitacoraPanel);

        // Tab Estudiante
        JPanel estudiantePanel = new JPanel();
        JButton verDatos = new JButton("Ver Datos de Estudiante");
        verDatos.addActionListener(e -> controller.mostrarDatosEstudiante());
        estudiantePanel.add(verDatos);
        tabbedPane.addTab("Estudiante", estudiantePanel);

        add(tabbedPane);

        JButton logout = new JButton("Logout");
        logout.addActionListener(e -> authController.logout(adminCode, this));
        add(logout, BorderLayout.NORTH);

        setVisible(true);
    }

    private void actualizarTablaVendedores(JTable table) {
        Vendedor[] vendedores = controller.getVendedores();
        String[] columns = {"Código", "Nombre", "Género", "Ventas Confirmadas"};
        Object[][] data = new Object[vendedores.length][4];
        for (int i = 0; i < vendedores.length; i++) {
            data[i][0] = vendedores[i].getCodigo();
            data[i][1] = vendedores[i].getNombre();
            data[i][2] = vendedores[i].getGenero();
            data[i][3] = vendedores[i].getVentasConfirmadas();
        }
        table.setModel(new DefaultTableModel(data, columns));
    }

    private void crearVendedorDialog() {
        // Form dialog
        JTextField codigo = new JTextField();
        JTextField nombre = new JTextField();
        JTextField genero = new JTextField();
        JTextField contraseña = new JTextField();
        Object[] message = {
            "Código:", codigo,
            "Nombre:", nombre,
            "Género:", genero,
            "Contraseña:", contraseña
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Crear Vendedor", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            vendedor vendedor = new vendedor(codigo.getText(), nombre.getText(), genero.getText(), contraseña.getText());
            controller.crear(vendedor);
            actualizarTablaVendedores((JTable) ((JScrollPane) ((JPanel) ((JTabbedPane) getComponent(0)).getComponentAt(0)).getComponent(0)).getViewport().getView());
        }
    }

    // Similar for actualizar, eliminar, cargar CSV (use JFileChooser for file)
    // For brevity, implement similar dialogs for other actions.

    private void actualizarTablaProductos(JTable table) {
        producto[] productos = controller.getProductos();
        String[] columns = {"Código", "Nombre", "Categoría"};
        Object[][] data = new Object[productos.length][3];
        for (int i = 0; i < productos.length; i++) {
            data[i][0] = productos[i].getCodigo();
            data[i][1] = productos[i].getNombre();
            data[i][2] = productos[i].getCategoria();
        }
        table.setModel(new DefaultTableModel(data, columns));
    }

    // Similar dialogs for productos, including categoria and atributo.

    private void verDetalleProducto(JTable table) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String codigo = (String) table.getValueAt(row, 0);
            producto prod = controller.buscarProducto(codigo); // Add method to controller if needed
            JOptionPane.showMessageDialog(null, prod.getAtributoEspecifico(), "Detalle", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void actualizarTablaBitacora(JTable table) {
        bitacora[] entries = controller.data.getBitacora();
        String[] columns = {"Fecha/Hora", "Tipo Usuario", "Código Usuario", "Operación", "Estado", "Descripción"};
        Object[][] data = new Object[entries.length][6];
        for (int i = 0; i < entries.length; i++) {
            data[i][0] = entries[i].getFechaHora();
            data[i][1] = entries[i].getTipoUsuario();
            data[i][2] = entries[i].getCodigoUsuario();
            data[i][3] = entries[i].getOperacion();
            data[i][4] = entries[i].getEstado();
            data[i][5] = entries[i].getDescripcion();
        }
        table.setModel(new DefaultTableModel(data, columns));
    }

    private void filtrarBitacoraDialog(JTable table) {
        // Dialog for filters, then call filtrarBitacora and update table
    }

    private void exportarBitacoraCSVDialog() {
        // Use JFileChooser, call CSVUtil.escribirCSV with bitacora data
    }

    private void exportarBitacoraPDFDialog() {
        // Use PDFUtil to generate PDF with bitacora
    }

    // Add JFileChooser for CSV files
}