/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
  import java.util.Scanner;
/**

 */
public class main {
  

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        inventario inventario = new inventario();

        int opcion;
        do {
            // Menú principal
            System.out.println("\n--- Sistema de Inventario ---");
            System.out.println("1. Agregar Producto");
            System.out.println("2. Buscar Producto");
            System.out.println("3. Eliminar Producto");
            System.out.println("4. Registrar Venta");
            System.out.println("5. Ver Inventario");
            System.out.println("6. Ver datos del estudiante");
            System.out.println("7. Salir");
            System.out.print("Opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch(opcion) {
                case 1:
                    // Agregar producto
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
                    } else {
                        System.out.println("Error: Código ya existe.");
                    }
                    break;

                case 2:
                    // Buscar producto
                    System.out.print("Código a buscar: ");
                    String codBuscar = sc.nextLine();
                    producto encontrado = inventario.buscarPorCodigo(codBuscar);
                    if (encontrado != null) {
                        encontrado.mostrarInfo();
                    } else {
                        System.out.println("Producto no encontrado.");
                    }
                    break;

                case 3:
                    // Eliminar producto
                    System.out.print("Código a eliminar: ");
                    String codEliminar = sc.nextLine();
                    if (inventario.eliminarProducto(codEliminar)) {
                        System.out.println("Producto eliminado.");
                    } else {
                        System.out.println("Producto no existe.");
                    }
                    break;

                case 4:
                    // Registrar venta
                    System.out.print("Código del producto: ");
                    String codVenta = sc.nextLine();
                    System.out.print("Cantidad vendida: ");
                    int cantVenta = sc.nextInt();
                    sc.nextLine();

                    if (inventario.registrarVenta(codVenta, cantVenta)) {
                        System.out.println("Venta registrada con éxito.");
                    } else {
                        System.out.println("Error en la venta (stock insuficiente o producto no existe).");
                    }
                    break;

                case 5:
                    // Ver inventario
                    inventario.mostrarProductos();
                    break;

                case 6:
                    // Datos del estudiante
                    System.out.println("Nombre: [Tu Nombre]");
                    System.out.println("Carnet: [Tu Carnet]");
                    break;
            }

        } while(opcion != 7);

        sc.close();
    }
}

    

