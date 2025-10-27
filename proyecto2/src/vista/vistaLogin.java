package vista;

import controlador.controlador;
import model.*;
import javax.swing.*;
import java.awt.*;

public class vistaLogin extends JFrame {
    private JTextField txtCodigo;
    private JPasswordField txtContrasena;
    private controlador controller;

    public vistaLogin(administradorDatos data) {
        this.controller = new controlador(data);
        setTitle("Login - Sancarlista Shop");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initUI();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        JLabel lblTitulo = new JLabel("Iniciar Sesión", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));

        txtCodigo = new JTextField();
        txtContrasena = new JPasswordField();

        JButton btnLogin = new JButton("Ingresar");
        btnLogin.addActionListener(e -> controller.iniciarSesion(txtCodigo.getText(), new String(txtContrasena.getPassword())));

        panel.add(lblTitulo);
        panel.add(txtCodigo);
        panel.add(txtContrasena);
        panel.add(btnLogin);

        add(panel, BorderLayout.CENTER);
    }
}
