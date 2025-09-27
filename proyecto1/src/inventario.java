/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
    import java.io.FileWriter;
/**
 *
 *
 */
public class inventario {


// Clase Inventario
// Maneja las operaciones sobre el vector de productos

    producto[] productos = new producto[100]; // vector de productos
    int contador = 0;

    // Agregar producto validando código único
    public boolean agregarProducto(producto p) {
        for (int i = 0; i < contador; i++) {
            if (productos[i].codigo.equals(p.codigo)) {
                return false; // código repetido
            }
        }
        productos[contador] = p;
        contador++;
        return true;
    }

    // Buscar producto por código
    public producto buscarPorCodigo(String codigo) {
        for (int i = 0; i < contador; i++) {
            if (productos[i].codigo.equals(codigo)) {
                return productos[i];
            }
        }
        return null;
    }

    // Eliminar producto del vector
    public boolean eliminarProducto(String codigo) {
        for (int i = 0; i < contador; i++) {
            if (productos[i].codigo.equals(codigo)) {
                productos[i] = productos[contador - 1];
                productos[contador - 1] = null;
                contador--;
                return true;
            }
        }
        return false;
    }

    // Mostrar todos los productos
    public void mostrarProductos() {
        for (int i = 0; i < contador; i++) {
            productos[i].mostrarInfo();
        }
    }

    // Registrar una venta: valida stock, descuenta y guarda en archivo
    public boolean registrarVenta(String codigo, int cantidad) {
        producto p = buscarPorCodigo(codigo);
        if (p != null && p.cantidad >= cantidad) {
            p.cantidad -= cantidad;
            double total = p.precio * cantidad;
            String fechaHora = java.time.LocalDateTime.now().toString();

            try (FileWriter fw = new FileWriter("ventas.txt", true)) {
                fw.write("Producto: " + p.nombre + " | Cantidad: " + cantidad +
                         " | Total: Q" + total + " | Fecha: " + fechaHora + "\n");
            } catch (Exception e) {
                System.out.println("Error al guardar venta en archivo.");
            }
            return true;
        }
        return false;
    }
}


