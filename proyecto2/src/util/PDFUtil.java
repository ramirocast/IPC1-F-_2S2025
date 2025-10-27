package util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import model.*;

public class PDFUtil {

    public static void generarReporte(String tipo, administradorDatos data) {
        String fileName = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(new Date()) + "_" + tipo + ".pdf";
        try {
            Document doc = new Document(PageSize.A4);
            PdfWriter.getInstance(doc, new FileOutputStream(fileName));
            doc.open();

            // Encabezado del documento
            doc.addTitle("Reporte: " + tipo);
            doc.addAuthor("Sancarlista Shop");

            Paragraph titulo = new Paragraph("REPORTE: " + tipo.toUpperCase(),
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);
            doc.add(new Paragraph("Generado: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())));
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph(" "));

            // Selección del tipo de reporte
            switch (tipo) {
                case "MasVendidos": generarMasVendidos(doc, data); break;
                case "MenosVendidos": generarMenosVendidos(doc, data); break;
                case "Inventario": generarInventario(doc, data); break;
                case "VentasPorVendedor": generarVentasPorVendedor(doc, data); break;
                case "ClientesActivos": generarClientesActivos(doc, data); break;
                case "Financiero": generarFinanciero(doc, data); break;
                default:
                    doc.add(new Paragraph("Tipo de reporte no implementado: " + tipo));
            }

            doc.close();
            System.out.println("Reporte generado: " + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ----------------- REPORTES -----------------

    private static void generarMasVendidos(Document doc, administradorDatos data) throws Exception {
        PdfPTable tabla = new PdfPTable(4);
        tabla.addCell("Código");
        tabla.addCell("Nombre");
        tabla.addCell("Categoría");
        tabla.addCell("Cantidad Vendida");

        producto[] productos = data.getProductos();
        int[] ventas = new int[productos.length];

        for (pedido p : data.getHistorialGlobal()) {
            if (p != null && "Confirmado".equals(p.getEstado())) {
                for (itemPedido it : p.getItems()) {
                    for (int i = 0; i < productos.length; i++) {
                        if (productos[i] != null && productos[i].getCodigo().equals(it.getCodigoProducto())) {
                            ventas[i] += it.getCantidad();
                        }
                    }
                }
            }
        }

        // Ordenar los productos por ventas descendentes
        for (int i = 0; i < productos.length - 1; i++) {
            for (int j = i + 1; j < productos.length; j++) {
                if (ventas[j] > ventas[i]) {
                    int tempV = ventas[i];
                    ventas[i] = ventas[j];
                    ventas[j] = tempV;

                    producto tempP = productos[i];
                    productos[i] = productos[j];
                    productos[j] = tempP;
                }
            }
        }

        for (int i = 0; i < Math.min(5, productos.length); i++) {
            if (productos[i] != null) {
                tabla.addCell(productos[i].getCodigo());
                tabla.addCell(productos[i].getNombre());
                tabla.addCell(productos[i].getCategoria());
                tabla.addCell(String.valueOf(ventas[i]));
            }
        }

        doc.add(tabla);
    }

    private static void generarMenosVendidos(Document doc, administradorDatos data) throws Exception {
        PdfPTable tabla = new PdfPTable(3);
        tabla.addCell("Código");
        tabla.addCell("Nombre");
        tabla.addCell("Stock Disponible");

        producto[] productos = data.getProductos();
        int[] ventas = new int[productos.length];

        for (pedido p : data.getHistorialGlobal()) {
            if (p != null && "Confirmado".equals(p.getEstado())) {
                for (itemPedido it : p.getItems()) {
                    for (int i = 0; i < productos.length; i++) {
                        if (productos[i] != null && productos[i].getCodigo().equals(it.getCodigoProducto())) {
                            ventas[i] += it.getCantidad();
                        }
                    }
                }
            }
        }

        // Ordenar por ventas ascendentes
        for (int i = 0; i < productos.length - 1; i++) {
            for (int j = i + 1; j < productos.length; j++) {
                if (ventas[j] < ventas[i]) {
                    int tempV = ventas[i];
                    ventas[i] = ventas[j];
                    ventas[j] = tempV;

                    producto tempP = productos[i];
                    productos[i] = productos[j];
                    productos[j] = tempP;
                }
            }
        }

        for (int i = 0; i < Math.min(5, productos.length); i++) {
            if (productos[i] != null) {
                tabla.addCell(productos[i].getCodigo());
                tabla.addCell(productos[i].getNombre());
                tabla.addCell(String.valueOf(productos[i].getStock()));
            }
        }

        doc.add(tabla);
    }

    private static void generarInventario(Document doc, administradorDatos data) throws Exception {
        PdfPTable tabla = new PdfPTable(5);
        tabla.addCell("Código");
        tabla.addCell("Nombre");
        tabla.addCell("Categoría");
        tabla.addCell("Stock");
        tabla.addCell("Estado");

        for (producto p : data.getProductos()) {
            if (p == null) continue;
            String estado = p.getStock() < 10 ? "Crítico" :
                            (p.getStock() <= 20 ? "Bajo" : "Normal");
            tabla.addCell(p.getCodigo());
            tabla.addCell(p.getNombre());
            tabla.addCell(p.getCategoria());
            tabla.addCell(String.valueOf(p.getStock()));
            tabla.addCell(estado);
        }

        doc.add(tabla);
    }

    private static void generarVentasPorVendedor(Document doc, administradorDatos data) throws Exception {
        PdfPTable tabla = new PdfPTable(5);
        tabla.addCell("Código");
        tabla.addCell("Nombre");
        tabla.addCell("Pedidos Confirmados");
        tabla.addCell("Ventas Totales");
        tabla.addCell("Comisión (5%)");

        for (usuario u : data.getAllUsuarios()) {
            if (u instanceof vendedor) {
                vendedor v = (vendedor) u;
                int pedidos = 0;
                double ventasTotales = 0;

                for (pedido p : data.getHistorialGlobal()) {
                    if (p != null && "Confirmado".equals(p.getEstado()) && p.getCodigoVendedor().equals(v.getCodigo())) {
                        pedidos++;
                        for (itemPedido i : p.getItems()) {
                            producto pr = data.buscarProducto(i.getCodigoProducto());
                            if (pr != null) ventasTotales += pr.getPrecio() * i.getCantidad();
                        }
                    }
                }

                tabla.addCell(v.getCodigo());
                tabla.addCell(v.getNombre());
                tabla.addCell(String.valueOf(pedidos));
                tabla.addCell("Q" + ventasTotales);
                tabla.addCell("Q" + (ventasTotales * 0.05));
            }
        }

        doc.add(tabla);
    }

    private static void generarClientesActivos(Document doc, administradorDatos data) throws Exception {
        PdfPTable tabla = new PdfPTable(4);
        tabla.addCell("Código Cliente");
        tabla.addCell("Nombre");
        tabla.addCell("Última Compra");
        tabla.addCell("Total Gastado");

        for (usuario u : data.getAllUsuarios()) {
            if (u instanceof cliente) {
                cliente c = (cliente) u;
                pedido[] historial = c.getHistorial();
                if (historial.length == 0) continue;

                pedido ultima = historial[historial.length - 1];
                double total = 0;
                for (pedido p : historial) {
                    if ("Confirmado".equals(p.getEstado())) {
                        for (itemPedido i : p.getItems()) {
                            producto pr = data.buscarProducto(i.getCodigoProducto());
                            if (pr != null) total += pr.getPrecio() * i.getCantidad();
                        }
                    }
                }

                tabla.addCell(c.getCodigo());
                tabla.addCell(c.getNombre());
                tabla.addCell(new SimpleDateFormat("dd/MM/yyyy").format(ultima.getFecha()));
                tabla.addCell("Q" + total);
            }
        }

        doc.add(tabla);
    }

    private static void generarFinanciero(Document doc, administradorDatos data) throws Exception {
        PdfPTable tabla = new PdfPTable(4);
        tabla.addCell("Categoría");
        tabla.addCell("Cantidad Vendida");
        tabla.addCell("Ingresos Totales");
        tabla.addCell("% Participación");

        String[] categorias = data.obtenerCategorias();
        double totalGlobal = 0;
        double[] ingresos = new double[categorias.length];
        int[] cantidades = new int[categorias.length];

        for (pedido p : data.getHistorialGlobal()) {
            if (p != null && "Confirmado".equals(p.getEstado())) {
                for (itemPedido it : p.getItems()) {
                    producto pr = data.buscarProducto(it.getCodigoProducto());
                    if (pr != null) {
                        for (int i = 0; i < categorias.length; i++) {
                            if (pr.getCategoria().equalsIgnoreCase(categorias[i])) {
                                cantidades[i] += it.getCantidad();
                                ingresos[i] += pr.getPrecio() * it.getCantidad();
                                totalGlobal += pr.getPrecio() * it.getCantidad();
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < categorias.length; i++) {
            if (categorias[i] == null) continue;
            double participacion = totalGlobal > 0 ? (ingresos[i] / totalGlobal) * 100 : 0;
            tabla.addCell(categorias[i]);
            tabla.addCell(String.valueOf(cantidades[i]));
            tabla.addCell("Q" + ingresos[i]);
            tabla.addCell(String.format("%.2f%%", participacion));
        }

        doc.add(tabla);
    }
}
