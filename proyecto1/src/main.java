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
       if (inventario.buscarPorCodigo(codigo) != null) {
           System.out.println("Error: el código ya existe.");
           bitacora.registrarAccion("Agregar producto " + codigo, false, usuario);
           break;
       }

       String nombre = "";
       while (nombre.trim().isEmpty()) {
           System.out.print("Nombre: ");
           nombre = sc.nextLine();
           if (nombre.trim().isEmpty()) {
               System.out.println("El nombre no puede estar vacío.");
           }
       }

       String categoria = "";
       while (categoria.trim().isEmpty()) {
           System.out.print("Categoría: ");
           categoria = sc.nextLine();
           if (categoria.trim().isEmpty()) {
               System.out.println("La categoría no puede estar vacía.");
           }
       }

       double precio = -1;
       while (precio <= 0) {
           System.out.print("Precio (mayor que 0): ");
           try {
               precio = Double.parseDouble(sc.nextLine());
               if (precio <= 0) {
                   System.out.println("El precio debe ser mayor que 0.");
               }
           } catch (NumberFormatException e) {
               System.out.println("Ingrese un número válido.");
           }
       }

       int cantidad = -1;
       while (cantidad < 0) {
           System.out.print("Cantidad (0 o más): ");
           try {
               cantidad = Integer.parseInt(sc.nextLine());
               if (cantidad < 0) {
                   System.out.println("La cantidad no puede ser negativa.");
               }
           } catch (NumberFormatException e) {
               System.out.println("Ingrese un número entero válido.");
           }
       }

       producto nuevo = new producto(codigo, nombre, categoria, precio, cantidad);
       if (inventario.agregarProducto(nuevo)) {
           System.out.println("Producto agregado con éxito.");
           bitacora.registrarAccion("Agregar producto " + codigo, true, usuario);
       } else {
           System.out.println("Error al agregar producto.");
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

       producto p = inventario.buscarPorCodigo(codVenta);
       if (p == null) {
           System.out.println("Producto no encontrado.");
           bitacora.registrarAccion("Venta producto " + codVenta, false, usuario);
           break;
       }

       int cantVenta = 0;
       while (cantVenta <= 0) {
           System.out.print("Cantidad vendida (mayor que 0): ");
           try {
               cantVenta = Integer.parseInt(sc.nextLine());
               if (cantVenta <= 0) {
                   System.out.println("La cantidad debe ser mayor que 0.");
               }
           } catch (NumberFormatException e) {
               System.out.println("Ingrese un número entero válido.");
           }
       }

       if (p.cantidad < cantVenta) {
           System.out.println("Stock insuficiente. Stock actual: " + p.cantidad);
           bitacora.registrarAccion("Venta producto " + codVenta, false, usuario);
           break;
       }

       if (inventario.registrarVenta(codVenta, cantVenta)) {
           System.out.println("Venta registrada. Total: Q" + (p.precio * cantVenta));
           bitacora.registrarAccion("Venta producto " + codVenta + " x" + cantVenta, true, usuario);
       } else {
           System.out.println("Error al registrar la venta.");
           bitacora.registrarAccion("Venta producto " + codVenta, false, usuario);
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
