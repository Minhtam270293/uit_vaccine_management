package com.uit.vaccinemanagement.view;

import javax.swing.*;
import java.awt.*;

public class AuthView {

    public interface AuthCallback {

        void onLogin(String email, String password);

        void onSignUpClicked();
    }

    public void showAuthUI(AuthCallback callback) {
        JFrame frame = new JFrame("Đăng nhập");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 180);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 5, 5));

        JLabel lblEmail = new JLabel("Email:");
        // JTextField txtEmail = new JTextField("admin@example.com");
        // JTextField txtEmail = new JTextField("bacsiA@example.com");
        JTextField txtEmail = new JTextField("khachB@example.com");
        JLabel lblPass = new JLabel("Mật khẩu:");
        JPasswordField txtPass = new JPasswordField("123456");

        JButton btnLogin = new JButton("Đăng nhập");
        JButton btnSignUp = new JButton("Đăng ký");

        panel.add(lblEmail);
        panel.add(txtEmail);
        panel.add(lblPass);
        panel.add(txtPass);
        panel.add(btnLogin);
        panel.add(btnSignUp);

        btnLogin.addActionListener(e -> {
            String email = txtEmail.getText();
            String password = new String(txtPass.getPassword());
            frame.dispose();
            callback.onLogin(email, password);
        });
        btnSignUp.addActionListener(e -> {
            frame.dispose();
            callback.onSignUpClicked();
        });

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
}
