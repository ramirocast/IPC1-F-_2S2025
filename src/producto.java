/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */


/**
 *
 * @author ramirito
 */
public class producto {
    
    
    String codigo;
    String nombre;
    String categoria;
    double precio;
    int cantidad;

    public producto(String codigo, String nombre, String categoria, double precio, int cantidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
    }
}

public class Venta {
    String codigoProducto;
    int cantidadVendida;
    String fechaHora;
    double total;

    public Venta(String codigoProducto, int cantidadVendida, String fechaHora, double total) {
        this.codigoProducto = codigoProducto;
        this.cantidadVendida = cantidadVendida;
        this.fechaHora = fechaHora;
        this.total = total;
    }
}

public class Inventario {
    producto[] productos = new producto[100]; // vector, no ArrayList
    int contador = 0;

    public void agregarProducto(producto p) {
        productos[contador] = p;
        contador++;
    }

    public producto buscarPorCodigo(String codigo) {
        for (int i = 0; i < contador; i++) {
            if (productos[i].codigo.equals(codigo)) {
                return productos[i];
            }
        }
        return null;
    }

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
}



    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    

