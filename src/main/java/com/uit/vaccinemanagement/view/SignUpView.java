package com.uit.vaccinemanagement.view;

import javax.swing.*;
import java.awt.*;

public class SignUpView {

    public interface SignUpCallback {
        void onRegister(String username, String hoTen, String email, String password, String dob, String gender);
        void onBack();
    }

    public void showSignUpUI(SignUpCallback callback) {
        JFrame frame = new JFrame("Đăng ký tài khoản mới");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 650); // tăng size form lớn hơn

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2.dispose();
            }
        };
        panel.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));

        Font uiFont = new Font("Arial", Font.PLAIN, 15);
        Font btnFont = new Font("Arial", Font.BOLD, 15);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 8, 4, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Dimension textFieldSize = new Dimension(200, 36); // chiều dài các ô nhập

        // Sử dụng border bo góc cho JTextField, không override paintComponent/paintBorder
        javax.swing.border.Border roundedBorder = BorderFactory.createCompoundBorder(
            new RoundedBorder(Color.LIGHT_GRAY, 1, 12),
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        );

        JLabel lblUser = new JLabel("Tên đăng nhập:");
        lblUser.setFont(uiFont);
        JTextField txtUser = new JTextField();
        txtUser.setFont(uiFont);
        txtUser.setPreferredSize(textFieldSize);
        txtUser.setBorder(roundedBorder);

        JLabel lblHoTen = new JLabel("Họ tên:");
        lblHoTen.setFont(uiFont);
        JTextField txtHoTen = new JTextField();
        txtHoTen.setFont(uiFont);
        txtHoTen.setPreferredSize(textFieldSize);
        txtHoTen.setBorder(roundedBorder);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(uiFont);
        JTextField txtEmail = new JTextField();
        txtEmail.setFont(uiFont);
        txtEmail.setPreferredSize(textFieldSize);
        txtEmail.setBorder(roundedBorder);

        JLabel lblPass = new JLabel("Mật khẩu:");
        lblPass.setFont(uiFont);
        JPasswordField txtPass = new JPasswordField();
        txtPass.setFont(uiFont);
        txtPass.setPreferredSize(textFieldSize);
        txtPass.setBorder(roundedBorder);

        JLabel lblDob = new JLabel("Ngày sinh (yyyy-mm-dd):");
        lblDob.setFont(uiFont);
        JTextField txtDob = new JTextField();
        txtDob.setFont(uiFont);
        txtDob.setPreferredSize(textFieldSize);
        txtDob.setBorder(roundedBorder);

        JLabel lblGender = new JLabel("Giới tính:");
        lblGender.setFont(uiFont);
        String[] genders = {"Nam", "Nữ", "Khác"};
        JComboBox<String> cbGender = new JComboBox<>(genders);
        cbGender.setFont(uiFont);
        cbGender.setPreferredSize(textFieldSize);

        AuthButton btnRegister = new AuthButton("Đăng ký", new Color(210, 90, 22));
        btnRegister.setFont(btnFont);
        AuthButton btnBack = new AuthButton("Quay lại", new Color(120, 120, 120));
        btnBack.setFont(btnFont);
        Dimension buttonSize = new Dimension(120, 36);
        btnRegister.setPreferredSize(buttonSize);
        btnBack.setPreferredSize(buttonSize);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 12, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnBack);

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; panel.add(lblUser, gbc);
        gbc.gridx = 1; gbc.gridy = row++; panel.add(txtUser, gbc);
        gbc.gridx = 0; gbc.gridy = row; panel.add(lblHoTen, gbc);
        gbc.gridx = 1; gbc.gridy = row++; panel.add(txtHoTen, gbc);
        gbc.gridx = 0; gbc.gridy = row; panel.add(lblEmail, gbc);
        gbc.gridx = 1; gbc.gridy = row++; panel.add(txtEmail, gbc);
        gbc.gridx = 0; gbc.gridy = row; panel.add(lblPass, gbc);
        gbc.gridx = 1; gbc.gridy = row++; panel.add(txtPass, gbc);
        gbc.gridx = 0; gbc.gridy = row; panel.add(lblDob, gbc);
        gbc.gridx = 1; gbc.gridy = row++; panel.add(txtDob, gbc);
        gbc.gridx = 0; gbc.gridy = row; panel.add(lblGender, gbc);
        gbc.gridx = 1; gbc.gridy = row++; panel.add(cbGender, gbc);

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        panel.add(Box.createVerticalStrut(12), gbc);
        gbc.gridy = ++row;
        panel.add(buttonPanel, gbc);

        btnRegister.addActionListener(e -> {
            String username = txtUser.getText();
            String hoTen = txtHoTen.getText();
            String email = txtEmail.getText();
            String password = new String(txtPass.getPassword());
            String dob = txtDob.getText();
            String gender = (String) cbGender.getSelectedItem();
            frame.dispose();
            callback.onRegister(username, hoTen, email, password, dob, gender);
        });

        btnBack.addActionListener(e -> {
            frame.dispose();
            callback.onBack();
        });

        mainPanel.add(panel, BorderLayout.CENTER);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Bo góc cho JPasswordField
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