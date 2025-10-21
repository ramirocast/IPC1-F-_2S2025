package util;

import model.*;

import java.io.Serializable;

public class administradorDatos implements Serializable {
    private usuario[] usuarios = new usuario[100];
    private int numUsuarios = 0;

    private producto[] productos = new producto[100];
    private int numProductos = 0;

    private pedido[] pedidos = new pedido[100];
    private int numPedidos = 0;

    private ingresoStock[] ingresosStock = new ingresoStock[100];
    private int numIngresosStock = 0;

    private bitacora[] bitacora = new bitacora[1000];
    private int numBitacora = 0;

    private int usuariosActivos = 0;

    public void agregarUsuario(usuario usuario) {
        if (buscarUsuario(usuario.getCodigo()) == null) {
            if (numUsuarios == usuarios.length) resizeUsuarios();
            usuarios[numUsuarios++] = usuario;
        }
    }

    public usuario buscarUsuario(String codigo) {
        for (int i = 0; i < numUsuarios; i++) {
            if (usuarios[i].getCodigo().equals(codigo)) return usuarios[i];
        }
        return null;
    }

    public void actualizarUsuario(usuario updated) {
        for (int i = 0; i < numUsuarios; i++) {
            if (usuarios[i].getCodigo().equals(updated.getCodigo())) {
                usuarios[i].setNombre(updated.getNombre());
                usuarios[i].setContraseña(updated.getContraseña());
                if (updated instanceof cliente) ((cliente) usuarios[i]).setCumpleaños(((cliente) updated).getCumpleaños());
                break;
            }
        }
    }

    public void eliminarUsuario(String codigo) {
        for (int i = 0; i < numUsuarios; i++) {
            if (usuarios[i].getCodigo().equals(codigo)) {
                System.arraycopy(usuarios, i + 1, usuarios, i, numUsuarios - i - 1);
                numUsuarios--;
                break;
            }
        }
    }

    public vendedor[] getVendedores() {
        vendedor[] vendedores = new vendedor[numUsuarios];
        int count = 0;
        for (int i = 0; i < numUsuarios; i++) {
            if (usuarios[i] instanceof vendedor) vendedores[count++] = (vendedor) usuarios[i];
        }
        vendedor[] result = new vendedor[count];
        System.arraycopy(vendedores, 0, result, 0, count);
        return result;
    }

    public cliente[] getClientes(String vendedorCode) {
        cliente[] clientes = new cliente[numUsuarios];
        int count = 0;
        for (int i = 0; i < numUsuarios; i++) {
            if (usuarios[i] instanceof cliente) clientes[count++] = (cliente) usuarios[i];
        }
        cliente[] result = new cliente[count];
        System.arraycopy(clientes, 0, result, 0, count);
        return result; // Filtro por vendedor si se asocia, pero spec no lo indica, así que todos
    }

    public void agregarProducto(producto producto) {
        if (buscarProducto(producto.getCodigo()) == null) {
            if (numProductos == productos.length) resizeProductos();
            productos[numProductos++] = producto;
        }
    }

    public producto buscarProducto(String codigo) {
        for (int i = 0; i < numProductos; i++) {
            if (productos[i].getCodigo().equals(codigo)) return productos[i];
        }
        return null;
    }

    public void actualizarProducto(producto updated) {
        for (int i = 0; i < numProductos; i++) {
            if (productos[i].getCodigo().equals(updated.getCodigo())) {
                productos[i].setNombre(updated.getNombre());
                productos[i].setAtributoEspecifico(updated.getAtributoEspecifico());
                break;
            }
        }
    }

    public void eliminarProducto(String codigo) {
        for (int i = 0; i < numProductos; i++) {
            if (productos[i].getCodigo().equals(codigo)) {
                System.arraycopy(productos, i + 1, productos, i, numProductos - i - 1);
                numProductos--;
                break;
            }
        }
    }

    public producto[] getProductos() {
        producto[] copy = new producto[numProductos];
        System.arraycopy(productos, 0, copy, 0, numProductos);
        return copy;
    }

    public producto[] getProductosConStock() {
        producto[] conStock = new producto[numProductos];
        int count = 0;
        for (int i = 0; i < numProductos; i++) {
            if (productos[i].getStock() > 0) conStock[count++] = productos[i];
        }
        producto[] result = new producto[count];
        System.arraycopy(conStock, 0, result, 0, count);
        return result;
    }

    public void agregarIngresoStock(ingresoStock ingreso) {
        if (numIngresosStock == ingresosStock.length) resizeIngresosStock();
        ingresosStock[numIngresosStock++] = ingreso;
    }

    public ingresoStock[] getIngresosStock() {
        ingresoStock[] copy = new ingresoStock[numIngresosStock];
        System.arraycopy(ingresosStock, 0, copy, 0, numIngresosStock);
        return copy;
    }

    public void agregarPedido(pedido pedido) {
        if (numPedidos == pedidos.length) resizePedidos();
        pedidos[numPedidos++] = pedido;
    }

    public pedido[] getPedidosPendientes() {
        pedido[] pendientes = new pedido[numPedidos];
        int count = 0;
        for (int i = 0; i < numPedidos; i++) {
            if ("Pendiente".equals(pedidos[i].getEstado())) pendientes[count++] = pedidos[i];
        }
        pedido[] result = new pedido[count];
        System.arraycopy(pendientes, 0, result, 0, count);
        return result;
    }

    public pedido[] getHistorialCompras(String codigoCliente) {
        pedido[] historial = new pedido[numPedidos];
        int count = 0;
        for (int i = 0; i < numPedidos; i++) {
            if (pedidos[i].getCodigoCliente().equals(codigoCliente) && "Confirmado".equals(pedidos[i].getEstado())) historial[count++] = pedidos[i];
        }
        pedido[] result = new pedido[count];
        System.arraycopy(historial, 0, result, 0, count);
        return result;
    }

    public void confirmarPedido(pedido pedido, String vendedorCode) {
        pedido.confirmar();
        vendedor vendedor = (vendedor) buscarUsuario(vendedorCode);
        if (vendedor != null) vendedor.incrementarVentas();
    }

    public void agregarBitacora(bitacora entry) {
        if (numBitacora == bitacora.length) resizeBitacora();
        bitacora[numBitacora++] = entry;
    }

    public bitacora[] getBitacora() {
        bitacora[] copy = new bitacora[numBitacora];
        System.arraycopy(bitacora, 0, copy, 0, numBitacora);
        return copy;
    }

    public bitacora[] filtrarBitacora(String fechaDesde, String fechaHasta, String tipoUser, String operacion, String codeUser) {
        // Implementar filtro simple (comparar strings)
        bitacora[] filtered = new bitacora[numBitacora];
        int count = 0;
        for (int i = 0; i < numBitacora; i++) {
            if ((fechaDesde == null || bitacora[i].getFechaHora().compareTo(fechaDesde) >= 0) &&
                (fechaHasta == null || bitacora[i].getFechaHora().compareTo(fechaHasta) <= 0) &&
                (tipoUser == null || bitacora[i].getTipoUsuario().equals(tipoUser)) &&
                (operacion == null || bitacora[i].getOperacion().equals(operacion)) &&
                (codeUser == null || bitacora[i].getCodigoUsuario().equals(codeUser))) {
                filtered[count++] = bitacora[i];
            }
        }
        bitacora[] result = new bitacora[count];
        System.arraycopy(filtered, 0, result, 0, count);
        return result;
    }

    public void incrementarUsuariosActivos() {
        usuariosActivos++;
    }

    public void decrementarUsuariosActivos() {
        usuariosActivos--;
    }

    public int getUsuariosActivos() {
        return usuariosActivos;
    }

    public int getPedidosPendientesCount() {
        int count = 0;
        for (int i = 0; i < numPedidos; i++) {
            if ("Pendiente".equals(pedidos[i].getEstado())) count++;
        }
        return count;
    }

    public int getVentasDelDia() {
        // Simple, count confirmed today (implement with date comparison)
        return numPedidos; // Placeholder, implement properly
    }

    public int getProductosRegistrados() {
        return numProductos;
    }

    // Resize methods
    private void resizeUsuarios() {
        usuario[] newArray = new usuario[usuarios.length * 2];
        System.arraycopy(usuarios, 0, newArray, 0, usuarios.length);
        usuarios = newArray;
    }

    private void resizeProductos() {
        producto[] newArray = new producto[productos.length * 2];
        System.arraycopy(productos, 0, newArray, 0, productos.length);
        productos = newArray;
    }

    private void resizePedidos() {
        pedido[] newArray = new pedido[pedidos.length * 2];
        System.arraycopy(pedidos, 0, newArray, 0, pedidos.length);
        pedidos = newArray;
    }

    private void resizeIngresosStock() {
        ingresoStock[] newArray = new ingresoStock[ingresosStock.length * 2];
        System.arraycopy(ingresosStock, 0, newArray, 0, ingresosStock.length);
        ingresosStock = newArray;
    }

    private void resizeBitacora() {
        bitacora[] newArray = new bitacora[bitacora.length * 2];
        System.arraycopy(bitacora, 0, newArray, 0, bitacora.length);
        bitacora = newArray;
    }
}