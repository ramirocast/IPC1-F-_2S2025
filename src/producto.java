/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author ramirito
 */
public class producto {
    // Clase Producto
// Representa un producto de la tienda (camisa, pantalón, etc.)

    String codigo;
    String nombre;
    String categoria;
    double precio;
    int cantidad;

    // Constructor
    public producto(String codigo, String nombre, String categoria, double precio, int cantidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    // Método para mostrar la información del producto
    public void mostrarInfo() {
        System.out.println(codigo + " | " + nombre + " | " + categoria +
                           " | Q" + precio + " | Stock: " + cantidad);
    }
}


