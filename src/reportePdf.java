/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
  import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author ramirito
 */
public class reportePdf {
  


    // Método para generar nombre del archivo con fecha y hora
    private static String generarNombre(String tipo) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
        String fecha = LocalDateTime.now().format(dtf);
        return fecha + "_" + tipo + ".pdf";
    }

    // Reporte de inventario (stock)
    public static void generarReporteStock(inventario inventario) {
        Document doc = new Document();
        try {
            String archivo = generarNombre("Stock");
            PdfWriter.getInstance(doc, new FileOutputStream(archivo));
            doc.open();

            doc.add(new Paragraph("Reporte de Inventario (Stock)\n\n"));

            PdfPTable tabla = new PdfPTable(5); // columnas: código, nombre, categoría, precio, cantidad
            tabla.addCell("Código");
            tabla.addCell("Nombre");
            tabla.addCell("Categoría");
            tabla.addCell("Precio");
            tabla.addCell("Cantidad");

            for (int i = 0; i < inventario.contador; i++) {
                producto p = inventario.productos[i];
                tabla.addCell(p.codigo);
                tabla.addCell(p.nombre);
                tabla.addCell(p.categoria);
                tabla.addCell("Q" + p.precio);
                tabla.addCell(String.valueOf(p.cantidad));
            }

            doc.add(tabla);
            doc.close();
            System.out.println("Reporte de Stock generado: " + archivo);
            abrirPdf.abrir(archivo);

        } catch (Exception e) {
            System.out.println("Error al generar PDF de stock: " + e.getMessage());
        }
    }

    // Reporte de ventas
    public static void generarReporteVentas(String archivoVentas) {
        Document doc = new Document();
        try {
            String archivo = generarNombre("Venta");
            PdfWriter.getInstance(doc, new FileOutputStream(archivo));
            doc.open();

            doc.add(new Paragraph("Reporte de Ventas\n\n"));

            PdfPTable tabla = new PdfPTable(4); // columnas: producto, cantidad, total, fecha
            tabla.addCell("Producto");
            tabla.addCell("Cantidad");
            tabla.addCell("Total");
            tabla.addCell("Fecha");

            java.io.File file = new java.io.File(archivoVentas);
            if (file.exists()) {
                java.util.Scanner sc = new java.util.Scanner(file);
                while (sc.hasNextLine()) {
                    String linea = sc.nextLine();
                    // Separar con el formato que guardaste en ventas.txt
                    String[] partes = linea.split("\\|");
                    for (String parte : partes) {
                        tabla.addCell(parte.trim());
                    }
                }
                sc.close();
            } else {
                doc.add(new Paragraph("No hay ventas registradas."));
            }

            doc.add(tabla);
            doc.close();
            System.out.println("Reporte de Ventas generado: " + archivo);
            abrirPdf.abrir(archivo);


        } catch (Exception e) {
            System.out.println("Error al generar PDF de ventas: " + e.getMessage());
        }
    }
}

    

