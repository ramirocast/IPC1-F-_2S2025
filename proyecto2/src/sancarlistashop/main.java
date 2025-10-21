package sancarlistashop;

import com.sancarlista.shop.controller.AuthController;
import com.sancarlista.shop.model.Administrador;
import com.sancarlista.shop.util.Serializador;
import com.sancarlista.shop.view.LoginView;
import com.sancarlista.shop.util.DataManager;

import javax.swing.*;

public class main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DataManager data = (DataManager) Serializador.cargarEstado("data.ser");
            if (data == null) {
                data = new DataManager();
                // Inicializa admin
                data.agregarUsuario(new Administrador("admin", "Admin Inicial", "M", "IPC1A")); // Ajusta sección
            }
            AuthController authController = new AuthController(data);
            new LoginView(authController);
        });
    }
}