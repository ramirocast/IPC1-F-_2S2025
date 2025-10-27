package controlador;

import model.*;
import util.PDFUtil;
import javax.swing.*;
import java.io.FileWriter;
import java.util.List;

public class controlAdmin {
    private administradorDatos data;
    private administrador admin;

    public controlAdmin(administradorDatos data, administrador admin) {
        this.data = data;
        this.admin = admin;
    }

    public bitacora[] getBitacoras() {
    return data.getBitacoras();
}


    public void exportarBitacoraCSV() {
        try (FileWriter fw = new FileWriter("bitacora.csv")) {
            for (bitacora b : data.getBitacoras()) {
                fw.write(b.getFecha() + "," + b.getUsuario() + "," + b.getOperacion() + "," + b.getDescripcion() + "\n");
            }
            JOptionPane.showMessageDialog(null, "Bitácora exportada correctamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al exportar la bitácora: " + e.getMessage());
        }
    }

    public void generarReporte(String tipo) {
       PDFUtil.generarReporte("MasVendidos", data);

    }

    public void mostrarDatosEstudiante() {
        JOptionPane.showMessageDialog(null, """
                Estudiante: Ramiro Andres Castellanos Davila
                Carnet: 202302574
                Curso: IPC1 - Sección F
                Proyecto: Sancarlista Shop
                """, "Datos del Estudiante", JOptionPane.INFORMATION_MESSAGE);
    }
}
