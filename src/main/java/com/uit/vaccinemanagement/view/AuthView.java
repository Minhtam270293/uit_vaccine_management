package com.uit.vaccinemanagement.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class AuthView {

    public interface AuthCallback {

        void onLogin(String email, String password);

        void onSignUpClicked();
    }

    public void showAuthUI(AuthCallback callback) {
        JFrame frame = new JFrame("Đăng nhập");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        // Tiêu đề
        JLabel lblTitle = new JLabel("Đăng nhập hệ thống", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(18, 0, 12, 0));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 8, 4, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

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

        JTextField txtEmail = new JTextField();
        txtEmail.setFont(uiFont);
        txtEmail.setVisible(false);
        txtEmail.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(Color.LIGHT_GRAY, 1, 12),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));

        int fieldWidth = 220, fieldHeight = 28;
        int refreshWidth = 32;
        int totalWidth = fieldWidth + refreshWidth;

        // Panel Email + Refresh
        JPanel emailInputPanel = new JPanel(new BorderLayout());
        emailInputPanel.setPreferredSize(new Dimension(totalWidth, fieldHeight));
        emailInputPanel.setOpaque(false);

        JPanel cardPanel = new JPanel(new CardLayout());
        cardPanel.setOpaque(false);
        cardPanel.setPreferredSize(new Dimension(fieldWidth, fieldHeight));

        cbEmail.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        txtEmail.setPreferredSize(new Dimension(fieldWidth, fieldHeight));

        cardPanel.add(cbEmail, "combo");
        cardPanel.add(txtEmail, "text");

        // Icon refresh
        int iconW = 20, iconH = 20;
        BufferedImage refreshImg = new BufferedImage(iconW, iconH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2icon = refreshImg.createGraphics();
        g2icon.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2icon.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2icon.setColor(new Color(22, 110, 210));
        g2icon.drawArc(3, 3, 14, 14, 40, 270);
        g2icon.drawLine(15, 7, 18, 4);
        g2icon.drawLine(15, 7, 13, 4);
        g2icon.dispose();
        Icon refreshIcon = new ImageIcon(refreshImg);

        JButton btnRefresh = new JButton(refreshIcon);
        btnRefresh.setPreferredSize(new Dimension(refreshWidth, fieldHeight));
        btnRefresh.setFocusable(false);
        btnRefresh.setBorder(BorderFactory.createEmptyBorder());
        btnRefresh.setContentAreaFilled(false);
        btnRefresh.setVisible(false);

        emailInputPanel.add(cardPanel, BorderLayout.CENTER);
        emailInputPanel.add(btnRefresh, BorderLayout.EAST);

        // Thêm Email
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblEmail, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(emailInputPanel, gbc);

        // Mật khẩu
        JLabel lblPass = new JLabel("Mật khẩu:");
        lblPass.setFont(uiFont);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblPass, gbc);

        JPasswordField txtPass = new JPasswordField("123456");
        txtPass.setFont(uiFont);
        txtPass.setPreferredSize(new Dimension(totalWidth, fieldHeight));
        txtPass.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(Color.LIGHT_GRAY, 1, 12),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(txtPass, gbc);

        // Khoảng cách
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(Box.createVerticalStrut(18), gbc);

        // Nút đăng nhập / đăng ký
        AuthButton btnLogin = new AuthButton("Đăng nhập", new Color(22, 110, 210));
        AuthButton btnSignUp = new AuthButton("Đăng ký", new Color(210, 90, 22));
        btnLogin.setFont(btnFont);
        btnSignUp.setFont(btnFont);
        Dimension buttonSize = new Dimension(140, 36);
        btnLogin.setPreferredSize(buttonSize);
        btnSignUp.setPreferredSize(buttonSize);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 12, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnSignUp);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        // CardLayout cho Email
        CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
        cbEmail.addActionListener(e -> {
            String selected = (String) cbEmail.getSelectedItem();
            if ("Nhập email khác...".equals(selected)) {
                cardLayout.show(cardPanel, "text");
                btnRefresh.setVisible(true);
                txtEmail.setText("");
                txtEmail.requestFocus();
            } else {
                cardLayout.show(cardPanel, "combo");
                btnRefresh.setVisible(false);
                txtEmail.setText("");
            }
            emailInputPanel.revalidate();
            emailInputPanel.repaint();
        });

        btnRefresh.addActionListener(e -> {
            cardLayout.show(cardPanel, "combo");
            btnRefresh.setVisible(false);
            cbEmail.setSelectedIndex(0);
            emailInputPanel.revalidate();
            emailInputPanel.repaint();
        });

        // Login
        btnLogin.addActionListener(e -> {
            String loginEmail = txtEmail.isVisible() ? txtEmail.getText().trim()
                    : (String) cbEmail.getSelectedItem();
            String password = new String(txtPass.getPassword());
            frame.dispose();
            callback.onLogin(loginEmail, password);
        });

        // Sign Up
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

    // Border bo góc
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
