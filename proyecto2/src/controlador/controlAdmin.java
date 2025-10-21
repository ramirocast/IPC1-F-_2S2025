package controlador;

import model.administrador;
import model.producto;
import model.cliente;
import model.ingresoStock;
import model.pedido;
import model.usuario;
import model.bitacora;
import model.productoAlimento;
import model.productoGeneral;
import model.productoTecnologico;
import model.vendedor;
import util.CSVUtil;
import util.administradorDatos;
import util.PDFUtil;
import javax.swing.JOptionPane;

public class controlAdmin implements CRUDInterface<vendedor> {
    private administradorDatos data;
    private String adminCode;

    public controlAdmin(administradorDatos data, String adminCode) {
        this.data = data;
        this.adminCode = adminCode;
    }

    @Override
    public void crear(vendedor vendedor) {
        data.agregarUsuario(vendedor);
        data.agregarBitacora(new bitacora("Administrador", adminCode, "CREAR_VENDEDOR", "EXITOSA", "Vendedor creado: " + vendedor.getCodigo()));
    }

    @Override
    public vendedor leer(String codigo) {
        usuario user = data.buscarUsuario(codigo);
        if (user instanceof vendedor) return (vendedor) user;
        return null;
    }

    @Override
    public void actualizar(vendedor vendedor) {
        data.actualizarUsuario(vendedor);
        data.agregarBitacora(new bitacora("Administrador", adminCode, "ACTUALIZAR_VENDEDOR", "EXITOSA", "Vendedor actualizado: " + vendedor.getCodigo()));
    }

    @Override
    public void eliminar(String codigo) {
        data.eliminarUsuario(codigo);
        data.agregarBitacora(new bitacora("Administrador", adminCode, "ELIMINAR_VENDEDOR", "EXITOSA", "Vendedor eliminado: " + codigo));
    }

    public vendedor[] getVendedores() {
        return data.getVendedores();
    }

    public void cargarVendedoresCSV(String fileName) {
        String[] [] lines = CSVUtil.leerCSV(fileName);
        for (String[] line : lines) {
            if (line.length == 4) {
                crear(new vendedor(line[0], line[1], line[2], line[3]));
            }
        }
        data.agregarBitacora(new bitacora("Administrador", adminCode, "CARGAR_VENDEDORES_CSV", "EXITOSA", "Carga masiva desde " + fileName));
    }

    // Para productos (no usa interface, pero similar)
    public void crearProducto(String categoria, String codigo, String nombre, String atributo) {
        producto producto;
        if ("Tecnologia".equals(categoria)) {
            producto = new productoTecnologico(codigo, nombre, Integer.parseInt(atributo));
        } else if ("Alimento".equals(categoria)) {
            producto = new productoAlimento(codigo, nombre, atributo);
        } else {
            producto = new productoGeneral(codigo, nombre, atributo);
        }
        data.agregarProducto(producto);
        data.agregarBitacora(new bitacora("Administrador", adminCode, "CREAR_PRODUCTO", "EXITOSA", "Producto creado: " + codigo));
    }

    public void actualizarProducto(producto producto, String nombre, String atributo) {
        producto.setNombre(nombre);
        producto.setAtributoEspecifico(atributo);
        data.actualizarProducto(producto);
        data.agregarBitacora(new bitacora("Administrador", adminCode, "ACTUALIZAR_PRODUCTO", "EXITOSA", "Producto actualizado: " + producto.getCodigo()));
    }

    public void eliminarProducto(String codigo) {
        data.eliminarProducto(codigo);
        data.agregarBitacora(new bitacora("Administrador", adminCode, "ELIMINAR_PRODUCTO", "EXITOSA", "Producto eliminado: " + codigo));
    }

    public void cargarProductosCSV(String fileName) {
        String[][] lines = CSVUtil.leerCSV(fileName);
        for (String[] line : lines) {
            if (line.length == 4) {
                crearProducto(line[2], line[0], line[1], line[3]);
            }
        }
        data.agregarBitacora(new bitacora("Administrador", adminCode, "CARGAR_PRODUCTOS_CSV", "EXITOSA", "Carga masiva desde " + fileName));
    }

    public producto[] getProductos() {
        return data.getProductos();
    }

    public void generarReporte(String tipo) {
        // Usar PDFUtil para cada tipo, placeholder
        PDFUtil.generarReporte(tipo, data);
        data.agregarBitacora(new bitacora("Administrador", adminCode, "GENERAR_REPORTE", "EXITOSA", "Reporte generado: " + tipo));
    }

    public void mostrarDatosEstudiante() {
        JOptionPane.showMessageDialog(null, "Nombre: TuNombre\nID: TuID\nSección: IPC1A", "Datos del Estudiante", JOptionPane.INFORMATION_MESSAGE);
    }
}

interface CRUDInterface<T> {
    void crear(T entidad);
    T leer(String codigo);
    void actualizar(T entidad);
    void eliminar(String codigo);
}