package vista;

import controlador.controlador;

import javax.swing.*;
import java.awt.*;

public class vistaLogin extends JFrame {
    private controlador controller;

    public vistaLogin(controlador controller) {
        this.controller = controller;
        setTitle("Login - Sancarlista Shop");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Código:"));
        JTextField codigoField = new JTextField();
        add(codigoField);

        add(new JLabel("Contraseña:"));
        JPasswordField contraseñaField = new JPasswordField();
        add(contraseñaField);

        JButton loginButton = new JButton("Iniciar Sesión");
        loginButton.addActionListener(e -> controller.login(codigoField.getText(), new String(contraseñaField.getPassword())));
        add(loginButton);

        setVisible(true);
    }
}