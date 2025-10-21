package controlador;

import model.cliente;
import model.ingresoStock;
import model.pedido;
import model.producto;
import util.CSVUtil;
import util.administradorDatos;

public class controlVendedor implements CRUDInterface<cliente> {
    private DataManager data;
    private String vendedorCode;

    public controlVendedor(administradorDatos data, String vendedorCode) {
        this.data = data;
        this.vendedorCode = vendedorCode;
    }

    public producto[] getProductos() {
        return data.getProductos();
    }

    public void agregarStock(String codigoProducto, int cantidad) {
        producto producto = data.buscarProducto(codigoProducto);
        if (producto != null) {
            producto.agregarStock(cantidad);
            data.agregarIngresoStock(new ingresoStock(vendedorCode, codigoProducto, cantidad));
            data.agregarBitacora(new bitacora("Vendedor", vendedorCode, "AGREGAR_STOCK", "EXITOSA", "Stock agregado: " + codigoProducto + ", Cantidad: " + cantidad));
        }
    }

    public void cargarStockCSV(String fileName) {
        String[][] lines = CSVUtil.leerCSV(fileName);
        for (String[] line : lines) {
            if (line.length == 2) {
                agregarStock(line[0], Integer.parseInt(line[1]));
            }
        }
        data.agregarBitacora(new bitacora("Vendedor", vendedorCode, "CARGAR_STOCK_CSV", "EXITOSA", "Carga masiva desde " + fileName));
    }

    public void generarHistorialStockCSV(String fileName) {
        String[][] lines = new String[data.getIngresosStock().length + 1][5];
        lines[0] = new String[]{"Fecha", "Hora", "Usuario", "Producto", "Cantidad"};
       ingresoStock[] ingresos = data.getIngresosStock();
        for (int i = 0; i < ingresos.length; i++) {
            lines[i + 1] = new String[]{new java.text.SimpleDateFormat("dd/MM/yyyy").format(ingresos[i].getFecha()), ingresos[i].getHora(), ingresos[i].getCodigoVendedor(), ingresos[i].getCodigoProducto(), String.valueOf(ingresos[i].getCantidad())};
        }
        CSVUtil.escribirCSV(fileName, lines);
        data.agregarBitacora(new bitacora("Vendedor", vendedorCode, "GENERAR_HISTORIAL_STOCK", "EXITOSA", "Historial generado en " + fileName));
    }

    @Override
    public void crear(cliente cliente) {
        data.agregarUsuario(cliente);
        data.agregarBitacora(new bitacora("Vendedor", vendedorCode, "CREAR_CLIENTE", "EXITOSA", "Cliente creado: " + cliente.getCodigo()));
    }

    @Override
    public cliente leer(String codigo) {
        usuario user = data.buscarUsuario(codigo);
        if (user instanceof cliente) return (cliente) user;
        return null;
    }

    @Override
    public void actualizar(Cliente cliente) {
        data.actualizarUsuario(cliente);
        data.agregarBitacora(new BitacoraEntry("Vendedor", vendedorCode, "ACTUALIZAR_CLIENTE", "EXITOSA", "Cliente actualizado: " + cliente.getCodigo()));
    }

    @Override
    public void eliminar(String codigo) {
        data.eliminarUsuario(codigo);
        data.agregarBitacora(new bitacora("Vendedor", vendedorCode, "ELIMINAR_CLIENTE", "EXITOSA", "Cliente eliminado: " + codigo));
    }

    public cliente[] getClientes() {
        return data.getClientes(vendedorCode);
    }

    public void cargarClientesCSV(String fileName) {
        String[][] lines = CSVUtil.leerCSV(fileName);
        for (String[] line : lines) {
            if (line.length == 5) {
                crear(new cliente(line[0], line[1], line[2], line[3], line[4]));
            }
        }
        data.agregarBitacora(new bitacora("Vendedor", vendedorCode, "CARGAR_CLIENTES_CSV", "EXITOSA", "Carga masiva desde " + fileName));
    }

    public pedido[] getPedidosPendientes() {
        return data.getPedidosPendientes();
    }

    public void confirmarPedido(pedido pedido) {
        boolean stockOk = true;
        for (itemPedido item : pedido.getItems()) {
            producto prod = data.buscarProducto(item.getCodigoProducto());
            if (prod != null && !prod.reducirStock(item.getCantidad())) {
                stockOk = false;
                break;
            }
        }
        if (stockOk) {
            data.confirmarPedido(pedido, vendedorCode);
            data.agregarBitacora(new bitacora("Vendedor", vendedorCode, "CONFIRMAR_PEDIDO", "EXITOSA", "Pedido de cliente " + pedido.getCodigoCliente() + " confirmado"));
        } else {
            data.agregarBitacora(new bitacora("Vendedor", vendedorCode, "CONFIRMAR_PEDIDO", "FALLIDA", "Stock insuficiente para pedido de " + pedido.getCodigoCliente()));
            JOptionPane.showMessageDialog(null, "Stock insuficiente", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}