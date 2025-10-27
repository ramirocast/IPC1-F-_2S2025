package model;

import java.io.Serializable;

public class administradorDatos implements Serializable {
    private usuario[] usuarios;
    private producto[] productos;
    private pedido[] historialGlobal;
    private bitacora[] bitacoras;
    private stockMovimiento[] movimientos;

    public administradorDatos() {
        usuarios = new usuario[0];
        productos = new producto[0];
        historialGlobal = new pedido[0];
        bitacoras = new bitacora[0];
        movimientos = new stockMovimiento[0];
    }

    // --- getters (devuelven copias para seguridad) ---
    public usuario[] getAllUsuarios() { return copiarUsuarios(usuarios); }
    public producto[] getProductos() { return copiarProductos(productos); }
    public pedido[] getHistorialGlobal() { return copiarPedidos(historialGlobal); }
    public bitacora[] getBitacoras() { return copiarBitacoras(bitacoras); }
    public stockMovimiento[] getMovimientos() { return copiarMovimientos(movimientos); }

    // --- adders (expandir arreglos) ---
    public void agregarUsuario(usuario u) { usuarios = pushUsuario(usuarios, u); }
    public void agregarProducto(producto p) { productos = pushProducto(productos, p); }
    public void agregarPedido(pedido p) { historialGlobal = pushPedido(historialGlobal, p); }
    public void agregarBitacora(bitacora b) { bitacoras = pushBitacora(bitacoras, b); }
    public void agregarMovimiento(stockMovimiento m) { movimientos = pushMovimiento(movimientos, m); }

    // --- búsquedas ---
    public usuario buscarUsuario(String codigo) {
        for (usuario u : usuarios) if (u.getCodigo().equalsIgnoreCase(codigo)) return u;
        return null;
    }
    public producto buscarProducto(String codigo) {
        for (producto p : productos) if (p.getCodigo().equalsIgnoreCase(codigo)) return p;
        return null;
    }

    // --- helpers de copia y push ---
    private usuario[] pushUsuario(usuario[] arr, usuario x) {
        usuario[] n = new usuario[arr.length + 1];
        for (int i = 0; i < arr.length; i++) n[i] = arr[i];
        n[arr.length] = x; return n;
    }
    private producto[] pushProducto(producto[] arr, producto x) {
        producto[] n = new producto[arr.length + 1];
        for (int i = 0; i < arr.length; i++) n[i] = arr[i];
        n[arr.length] = x; return n;
    }
    private pedido[] pushPedido(pedido[] arr, pedido x) {
        pedido[] n = new pedido[arr.length + 1];
        for (int i = 0; i < arr.length; i++) n[i] = arr[i];
        n[arr.length] = x; return n;
    }
    private bitacora[] pushBitacora(bitacora[] arr, bitacora x) {
        bitacora[] n = new bitacora[arr.length + 1];
        for (int i = 0; i < arr.length; i++) n[i] = arr[i];
        n[arr.length] = x; return n;
    }
    private stockMovimiento[] pushMovimiento(stockMovimiento[] arr, stockMovimiento x) {
        stockMovimiento[] n = new stockMovimiento[arr.length + 1];
        for (int i = 0; i < arr.length; i++) n[i] = arr[i];
        n[arr.length] = x; return n;
    }

    private usuario[] copiarUsuarios(usuario[] src) {
        usuario[] n = new usuario[src.length];
        for (int i = 0; i < src.length; i++) n[i] = src[i];
        return n;
    }
    private producto[] copiarProductos(producto[] src) {
        producto[] n = new producto[src.length];
        for (int i = 0; i < src.length; i++) n[i] = src[i];
        return n;
    }
    private pedido[] copiarPedidos(pedido[] src) {
        pedido[] n = new pedido[src.length];
        for (int i = 0; i < src.length; i++) n[i] = src[i];
        return n;
    }
    private bitacora[] copiarBitacoras(bitacora[] src) {
        bitacora[] n = new bitacora[src.length];
        for (int i = 0; i < src.length; i++) n[i] = src[i];
        return n;
    }
    private stockMovimiento[] copiarMovimientos(stockMovimiento[] src) {
        stockMovimiento[] n = new stockMovimiento[src.length];
        for (int i = 0; i < src.length; i++) n[i] = src[i];
        return n;
    }
    // --------------------------------------------------------------
// Método para obtener todas las categorías únicas del inventario
// --------------------------------------------------------------
public String[] obtenerCategorias() {
    String[] categorias = new String[0];
    for (int i = 0; i < productos.length; i++) {
        if (productos[i] == null) continue;
        boolean existe = false;

        // Verificar si la categoría ya está en la lista
        for (int j = 0; j < categorias.length; j++) {
            if (productos[i].getCategoria().equalsIgnoreCase(categorias[j])) {
                existe = true;
                break;
            }
        }

        // Si no existe, agregarla
        if (!existe) {
            String[] nuevo = new String[categorias.length + 1];
            for (int k = 0; k < categorias.length; k++) {
                nuevo[k] = categorias[k];
            }
            nuevo[categorias.length] = productos[i].getCategoria();
            categorias = nuevo;
        }
    }
    return categorias;
}

}
