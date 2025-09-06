package com.uit.vaccinemanagement.view.dialogs;

import com.uit.vaccinemanagement.controller.AdminController;
import com.uit.vaccinemanagement.model.Benh;
import com.uit.vaccinemanagement.view.AuthButton;

import javax.swing.*;
import java.awt.*;

public class BenhAddDialog extends JDialog {

    public interface RefreshCallback {
        void refresh();
    }

    public BenhAddDialog(JFrame parent, AdminController controller, RefreshCallback refreshCallback) {
        super(parent, "Thêm bệnh mới", true);
        setSize(500, 400);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

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

        Dimension textFieldSize = new Dimension(220, 36);

        javax.swing.border.Border roundedBorder = BorderFactory.createCompoundBorder(
            new RoundedBorder(Color.LIGHT_GRAY, 1, 12),
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        );

        JLabel lblMaBenh = new JLabel("Mã bệnh:");
        lblMaBenh.setFont(uiFont);
        JTextField tfMaBenh = new JTextField();
        tfMaBenh.setFont(uiFont);
        tfMaBenh.setPreferredSize(textFieldSize);
        tfMaBenh.setBorder(roundedBorder);

        JLabel lblTenBenh = new JLabel("Tên bệnh:");
        lblTenBenh.setFont(uiFont);
        JTextField tfTenBenh = new JTextField();
        tfTenBenh.setFont(uiFont);
        tfTenBenh.setPreferredSize(textFieldSize);
        tfTenBenh.setBorder(roundedBorder);

        JLabel lblMoTa = new JLabel("Mô tả:");
        lblMoTa.setFont(uiFont);
        JTextField tfMoTa = new JTextField();
        tfMoTa.setFont(uiFont);
        tfMoTa.setPreferredSize(textFieldSize);
        tfMoTa.setBorder(roundedBorder);

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; panel.add(lblMaBenh, gbc);
        gbc.gridx = 1; gbc.gridy = row++; panel.add(tfMaBenh, gbc);
        gbc.gridx = 0; gbc.gridy = row; panel.add(lblTenBenh, gbc);
        gbc.gridx = 1; gbc.gridy = row++; panel.add(tfTenBenh, gbc);
        gbc.gridx = 0; gbc.gridy = row; panel.add(lblMoTa, gbc);
        gbc.gridx = 1; gbc.gridy = row++; panel.add(tfMoTa, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 12, 0));
        buttonPanel.setOpaque(false);

        AuthButton btnAdd = new AuthButton("Thêm", new Color(210, 90, 22));
        AuthButton btnCancel = new AuthButton("Hủy", new Color(120, 120, 120));

        btnAdd.setFont(btnFont);
        btnCancel.setFont(btnFont);

        Dimension buttonSize = new Dimension(120, 36);
        btnAdd.setPreferredSize(buttonSize);
        btnCancel.setPreferredSize(buttonSize);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnCancel);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        panel.add(Box.createVerticalStrut(12), gbc);
        gbc.gridy = ++row;
        panel.add(buttonPanel, gbc);

        btnAdd.addActionListener(e -> {
            String maBenh = tfMaBenh.getText().trim();
            String tenBenh = tfTenBenh.getText().trim();
            String moTa = tfMoTa.getText().trim();

            if (maBenh.isEmpty() || tenBenh.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            Benh benh = new Benh(maBenh, tenBenh, moTa, new java.sql.Timestamp(System.currentTimeMillis()));

            if (controller.addBenh(benh)) {
                JOptionPane.showMessageDialog(this, "Thêm bệnh thành công!");
                dispose();
                if (refreshCallback != null) refreshCallback.refresh();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm bệnh thất bại!");
            }
        });

        btnCancel.addActionListener(e -> dispose());

        mainPanel.add(panel, BorderLayout.CENTER);
        getContentPane().add(mainPanel);

        pack();
        setLocationRelativeTo(parent);
    }

    public static class RoundedBorder extends javax.swing.border.AbstractBorder {
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
