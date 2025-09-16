
/**
 *
 * @author ramirito
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */


/**
 *

 */

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;

public class actividad6 {

    public static void main(String[] args) {
        try {
            // Crear documento
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("ejercicio_6.pdf"));
            document.open();

            // Fuentes
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLACK);
            Font fontEncabezado = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
            Font fontCelda = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

            // Título del documento
            Paragraph titulo = new Paragraph("Listado de paises", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(25);
            document.add(titulo);

            // Crear tabla con 3 columnas
            PdfPTable tabla = new PdfPTable(3);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{3, 1, 2});

            // Colores
            BaseColor colorEncabezado = new BaseColor(33, 150, 243); // Azul corporativo
            BaseColor colorFila1 = new BaseColor(227, 242, 253);     // Azul muy claro
            BaseColor colorFila2 = BaseColor.WHITE;
            BaseColor bordeCelda = new BaseColor(200, 200, 200);     // Gris para bordes

            // Encabezados con bordes redondeados simulados
            String[] encabezados = {"Pais", "Capital", "Poblacion(Millones)"};
            for (String encabezado : encabezados) {
                PdfPCell celda = new PdfPCell(new Paragraph(encabezado, fontEncabezado));
                celda.setBackgroundColor(colorEncabezado);
                celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celda.setPadding(8);
                celda.setBorderWidth(1.5f);
                celda.setBorderColor(bordeCelda);
                celda.setUseVariableBorders(true); // Permite bordes separados
                tabla.addCell(celda);
            }

            // Datos de la matriz
            String[][] peliculas = {
                    {"Guatemala", "Ciudad de Guatemala", "17"},
                    {"México", "Ciudad de México", "126"},
                    {"España", "Madrid", "47"},
                   
            };

            // Agregar filas con colores alternos y bordes
            for (int i = 0; i < peliculas.length; i++) {
                BaseColor fondo = (i % 2 == 0) ? colorFila1 : colorFila2;
                for (String dato : peliculas[i]) {
                    PdfPCell celda = new PdfPCell(new Paragraph(dato, fontCelda));
                    celda.setBackgroundColor(fondo);
                    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    celda.setPadding(6);
                    celda.setBorderWidth(1f);
                    celda.setBorderColor(bordeCelda);
                    tabla.addCell(celda);
                }
            }

            // Agregar tabla al documento
            document.add(tabla);

            // Cerrar documento
            document.close();
            System.out.println("PDF premium creado: ejercicio_matriz.pdf");

        } catch (DocumentException | java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
  
    

