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
        panel.setLayout(new GridLayout(4, 2, 5, 5));

        // Email label
        JLabel lblEmail = new JLabel("Email:");

        // ComboBox chọn email
        String[] emails = {
            "admin@example.com",
            "bacsiA@example.com",
            "khachB@example.com",
            "Nhập email khác..."
        };
        JComboBox<String> cbEmail = new JComboBox<>(emails);

        // TextField nhập email khác (ẩn mặc định)
        JTextField txtEmailCustom = new JTextField();
        txtEmailCustom.setVisible(false);

        // Lắng nghe khi chọn trong ComboBox
        cbEmail.addActionListener(e -> {
            String selected = (String) cbEmail.getSelectedItem();
            txtEmailCustom.setVisible("Nhập email khác...".equals(selected));
            frame.pack(); // tự co giãn frame khi ẩn/hiện
        });

        // Password
        JLabel lblPass = new JLabel("Mật khẩu:");
        JPasswordField txtPass = new JPasswordField("123456");

        // Buttons
        JButton btnLogin = new JButton("Đăng nhập");
        JButton btnSignUp = new JButton("Đăng ký");

        // Thêm vào panel
        panel.add(lblEmail);
        panel.add(cbEmail);
        panel.add(new JLabel(""));   // placeholder cho layout
        panel.add(txtEmailCustom);
        panel.add(lblPass);
        panel.add(txtPass);
        panel.add(btnLogin);
        panel.add(btnSignUp);

        // Sự kiện login
        btnLogin.addActionListener(e -> {
            String loginEmail;
            if (txtEmailCustom.isVisible()) {
                loginEmail = txtEmailCustom.getText().trim();
            } else {
                loginEmail = (String) cbEmail.getSelectedItem();
            }
            String password = new String(txtPass.getPassword());
            frame.dispose();
            callback.onLogin(loginEmail, password);
        });

        // Sự kiện sign up
        btnSignUp.addActionListener(e -> {
            frame.dispose();
            callback.onSignUpClicked();
        });

        frame.getContentPane().add(panel);
        frame.pack(); // điều chỉnh kích thước khớp nội dung
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
