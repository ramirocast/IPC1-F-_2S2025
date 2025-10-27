package sancarlistashop;

import model.*;
import util.serializador;
import vista.vistaLogin;
import hilo.*;

public class main {
    public static void main(String[] args) {
        administradorDatos temp = (administradorDatos) serializador.cargarEstado("data.ser");
if (temp == null) {
    temp = new administradorDatos();
    temp.agregarUsuario(new administrador("A001", "Admin General", "1234"));
    temp.agregarUsuario(new vendedor("V001", "Carlos", "123"));
    temp.agregarUsuario(new cliente("C001", "Ana", "123"));
    temp.agregarProducto(new producto("P001", "Camiseta", "Ropa", 75.00));
    temp.agregarProducto(new producto("P002", "Laptop", "Electrónica", 5800.00));
    temp.agregarProducto(new producto("P003", "Pan Integral", "Alimento", 10.00));
    System.out.println("Datos iniciales cargados.");
}
final administradorDatos data = temp;

        // Iniciar hilos de monitoreo
        monitorSesiones mSes = new monitorSesiones();
        monitorPedidos mPed = new monitorPedidos(data);
        monitorEstadisticas mEst = new monitorEstadisticas(data);
        mSes.start();
        mPed.start();
        mEst.start();

        // Lanzar ventana principal de Login
        new vistaLogin(data);

        // Guardar estado al finalizar la ejecución
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            serializador.guardarEstado(data, "data.ser");
            mSes.detener();
            mPed.detener();
            mEst.detener();
        }));
    }
}
