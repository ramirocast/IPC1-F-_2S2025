/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        inventario inventario = new inventario();
        gestorBitacora bitacora = new gestorBitacora();

        String usuario = "Estudiante"; // se puede personalizar
        int opcion;

        do {
            System.out.println("\n--- Sistema de Inventario ---");
            System.out.println("1. Agregar Producto");
            System.out.println("2. Buscar Producto");
            System.out.println("3. Eliminar Producto");
            System.out.println("4. Registrar Venta");
            System.out.println("5. Ver Inventario");
            System.out.println("6. Ver datos del estudiante");
            System.out.println("7. Ver Bitácora");
            System.out.println("8. Generar reporte de stock");
            System.out.println("9. Generar reporte de inventario");
            System.out.print("Opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch(opcion) {
                case 1:
                    System.out.print("Código: ");
                    String codigo = sc.nextLine();
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Categoría: ");
                    String categoria = sc.nextLine();
                    System.out.print("Precio: ");
                    double precio = sc.nextDouble();
                    System.out.print("Cantidad: ");
                    int cantidad = sc.nextInt();
                    sc.nextLine();

                    producto nuevo = new producto(codigo, nombre, categoria, precio, cantidad);
                    if (inventario.agregarProducto(nuevo)) {
                        System.out.println("Producto agregado con éxito.");
                        bitacora.registrarAccion("Agregar producto " + codigo, true, usuario);
                    } else {
                        System.out.println("Error: Código ya existe.");
                        bitacora.registrarAccion("Agregar producto " + codigo, false, usuario);
                    }
                    break;

                case 2:
                    System.out.print("Código a buscar: ");
                    String codBuscar = sc.nextLine();
                    producto encontrado = inventario.buscarPorCodigo(codBuscar);
                    if (encontrado != null) {
                        encontrado.mostrarInfo();
                        bitacora.registrarAccion("Buscar producto " + codBuscar, true, usuario);
                    } else {
                        System.out.println("Producto no encontrado.");
                        bitacora.registrarAccion("Buscar producto " + codBuscar, false, usuario);
                    }
                    break;

                case 3:
                    System.out.print("Código a eliminar: ");
                    String codEliminar = sc.nextLine();
                    if (inventario.eliminarProducto(codEliminar)) {
                        System.out.println("Producto eliminado.");
                        bitacora.registrarAccion("Eliminar producto " + codEliminar, true, usuario);
                    } else {
                        System.out.println("Producto no existe.");
                        bitacora.registrarAccion("Eliminar producto " + codEliminar, false, usuario);
                    }
                    break;

                case 4:
                    System.out.print("Código del producto: ");
                    String codVenta = sc.nextLine();
                    System.out.print("Cantidad vendida: ");
                    int cantVenta = sc.nextInt();
                    sc.nextLine();

                    if (inventario.registrarVenta(codVenta, cantVenta)) {
                        System.out.println("Venta registrada con éxito.");
                        bitacora.registrarAccion("Venta de producto " + codVenta, true, usuario);
                    } else {
                        System.out.println("Error en la venta.");
                        bitacora.registrarAccion("Venta de producto " + codVenta, false, usuario);
                    }
                    break;

                case 5:
                    inventario.mostrarProductos();
                    bitacora.registrarAccion("Ver inventario", true, usuario);
                    break;

                case 6:
                    System.out.println("Nombre: Rsmiro Castellanos");
                    System.out.println("Carnet: 202302574");
                    bitacora.registrarAccion("Ver datos estudiante", true, usuario);
                    break;

                case 7:
                    System.out.println("--- Bitácora de acciones ---");
                    bitacora.mostrarBitacora();
                    break;
                case 8: // Generar Reporte Stock
                    reportePdf.generarReporteStock(inventario);
                    break;

                case 9: // Generar Reporte Ventas
                    reportePdf.generarReporteVentas("ventas.txt");
                    break;

            }

        } while(opcion != 10);

        sc.close();
    }
}
