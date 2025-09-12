/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ramirito
 */
import java.awt.Desktop;
import java.io.File;

public class abrirPdf {
    public static void abrir(String ruta) {
        try {
            File file = new File(ruta);
            if (file.exists()) {
                Desktop.getDesktop().open(file);
            } else {
                System.out.println("El archivo no existe: " + ruta);
            }
        } catch (Exception e) {
            System.out.println("Error al abrir PDF: " + e.getMessage());
        }
    }
}

