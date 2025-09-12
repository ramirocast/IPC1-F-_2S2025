/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ramirito
 */
public class venta {
    // Clase Venta
// Guarda la información de una transacción realizada
    String codigoProducto;
    int cantidadVendida;
    String fechaHora;
    double total;

    public venta(String codigoProducto, int cantidadVendida, String fechaHora, double total) {
        this.codigoProducto = codigoProducto;
        this.cantidadVendida = cantidadVendida;
        this.fechaHora = fechaHora;
        this.total = total;
    }

    public void mostrarInfo() {
        System.out.println("Producto: " + codigoProducto + " | Cantidad: " + cantidadVendida +
                           " | Total: Q" + total + " | Fecha: " + fechaHora);
    }
}

    

