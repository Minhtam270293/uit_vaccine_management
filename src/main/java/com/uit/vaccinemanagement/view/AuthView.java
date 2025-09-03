package com.uit.vaccinemanagement.view;

import com.uit.vaccinemanagement.view.AuthButton;

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
        frame.setSize(430, 230);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE); // hoặc màu nền khác
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18); // bo góc 18px
                g2.dispose();
            }
        };
        panel.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));

        // Tiêu đề in đậm
        JLabel lblTitle = new JLabel("Đăng nhập hệ thống", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(18, 0, 12, 0)); // tăng lề trên (top=18)
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 8, 4, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;

        Font uiFont = new Font("Arial", Font.PLAIN, 15);
        Font btnFont = new Font("Arial", Font.BOLD, 15);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(uiFont);

        String[] emails = {
            "admin@example.com",
            "bacsiA@example.com",
            "khachB@example.com",
            "Nhập email khác..."
        };
        JComboBox<String> cbEmail = new JComboBox<>(emails);
        cbEmail.setFont(uiFont);

        JTextField txtEmailCustom = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.dispose();
            }
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.LIGHT_GRAY);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 12, 12);
                g2.dispose();
            }
        };
        txtEmailCustom.setFont(uiFont);
        txtEmailCustom.setVisible(false);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblEmail, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(cbEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel(""), gbc); // placeholder

        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(txtEmailCustom, gbc);

        JLabel lblPass = new JLabel("Mật khẩu:");
        lblPass.setFont(uiFont);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(lblPass, gbc);

        // Ô mật khẩu bo góc 12px, hiển thị text mặc định
        JPasswordField txtPass = new JPasswordField("123456");
        txtPass.setFont(uiFont);
        txtPass.setPreferredSize(cbEmail.getPreferredSize()); // tăng chiều cao cho bằng email

        // Sử dụng border bo góc 12px cho JPasswordField
        txtPass.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(Color.LIGHT_GRAY, 1, 12),
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));

        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(txtPass, gbc);

        // Dòng trống tăng khoảng cách
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(Box.createVerticalStrut(18), gbc);

        // Nút đăng nhập và đăng ký (đặt vào một panel để chiều dài bằng nhau)
        AuthButton btnLogin = new AuthButton("Đăng nhập", new Color(22, 110, 210));
        AuthButton btnSignUp = new AuthButton("Đăng ký", new Color(210, 90, 22));
        btnLogin.setFont(btnFont);
        btnSignUp.setFont(btnFont);
        Dimension buttonSize = new Dimension(140, 36);
        btnLogin.setPreferredSize(buttonSize);
        btnSignUp.setPreferredSize(buttonSize);

        // Sửa lại: dùng JPanel thường, không override paintComponent
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 12, 0));
        buttonPanel.setOpaque(false); // Đảm bảo không vẽ nền
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnSignUp);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

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

        mainPanel.add(panel, BorderLayout.CENTER);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Thêm class RoundedBorder vào cuối file (hoặc vào file riêng nếu muốn dùng lại)
    class RoundedBorder extends javax.swing.border.AbstractBorder {
        private final Color color;
        private final int thickness;
        private final int arc;

        public RoundedBorder(Color color, int thickness, int arc) {
            this.color = color;
            this.thickness = thickness;
            this.arc = arc;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(thickness));
            g2.drawRoundRect(x, y, width - 1, height - 1, arc, arc);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(thickness, thickness, thickness, thickness);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = insets.top = insets.bottom = thickness;
            return insets;
        }
    }
}