package vista;

import controlador.controlAdmin;
import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class vistaAdmin extends JFrame {
    private controlAdmin controller;
    private DefaultTableModel bitacoraModel;
    private JTable tablaBitacora;

    public vistaAdmin(controlAdmin controller) {
        this.controller = controller;
        setTitle("Administrador - Sancarlista Shop");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initUI();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initUI() {
        JTabbedPane tabs = new JTabbedPane();

        // --- Bitácora ---
        JPanel bitacoraPanel = new JPanel(new BorderLayout());
        bitacoraModel = new DefaultTableModel(new String[]{"Fecha", "Usuario", "Operación", "Descripción"}, 0);
        tablaBitacora = new JTable(bitacoraModel);
        bitacoraPanel.add(new JScrollPane(tablaBitacora), BorderLayout.CENTER);

        JPanel botones = new JPanel();
        JButton exportar = new JButton("Exportar CSV");
        exportar.addActionListener(e -> controller.exportarBitacoraCSV());

        JButton reporte = new JButton("Reporte Más Vendidos");
        reporte.addActionListener(e -> controller.generarReporte("MasVendidos"));

        JButton datosEst = new JButton("Datos del Estudiante");
        datosEst.addActionListener(e -> controller.mostrarDatosEstudiante());

        botones.add(exportar);
        botones.add(reporte);
        botones.add(datosEst);

        bitacoraPanel.add(botones, BorderLayout.SOUTH);
        tabs.add("Bitácora", bitacoraPanel);

        add(tabs, BorderLayout.CENTER);
        cargarBitacora();
    }

    private void cargarBitacora() {
        bitacoraModel.setRowCount(0);
        for (bitacora b : controller.getBitacoras())
            bitacoraModel.addRow(new Object[]{b.getFecha(), b.getUsuario(), b.getOperacion(), b.getDescripcion()});
    }
}
