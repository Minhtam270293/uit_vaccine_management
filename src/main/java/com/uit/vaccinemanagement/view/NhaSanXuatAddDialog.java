package com.uit.vaccinemanagement.view;

import com.uit.vaccinemanagement.controller.AdminController;
import com.uit.vaccinemanagement.model.NhaSanXuat;

import javax.swing.*;
import java.awt.*;

public class NhaSanXuatAddDialog extends JDialog {

    public NhaSanXuatAddDialog(JFrame parent, AdminController controller, JButton btnRefresh) {
        super(parent, "Thêm nhà sản xuất", true);
        setSize(1000, 650);
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

        Dimension textFieldSize = new Dimension(200, 36);

        // Create rounded border
        javax.swing.border.Border roundedBorder = BorderFactory.createCompoundBorder(
            new RoundedBorder(Color.LIGHT_GRAY, 1, 12),
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        );

        // Create input fields with rounded borders
        JLabel lblTenNSX = new JLabel("Tên nhà sản xuất:");
        lblTenNSX.setFont(uiFont);
        JTextField tfTenNSX = new JTextField();
        tfTenNSX.setFont(uiFont);
        tfTenNSX.setPreferredSize(textFieldSize);
        tfTenNSX.setBorder(roundedBorder);

        JLabel lblQuocGia = new JLabel("Quốc gia:");
        lblQuocGia.setFont(uiFont);
        JTextField tfQuocGia = new JTextField();
        tfQuocGia.setFont(uiFont);
        tfQuocGia.setPreferredSize(textFieldSize);
        tfQuocGia.setBorder(roundedBorder);

        // Add components to panel using GridBagLayout
        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; panel.add(lblTenNSX, gbc);
        gbc.gridx = 1; gbc.gridy = row++; panel.add(tfTenNSX, gbc);
        gbc.gridx = 0; gbc.gridy = row; panel.add(lblQuocGia, gbc);
        gbc.gridx = 1; gbc.gridy = row++; panel.add(tfQuocGia, gbc);

        // Button Panel
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

        // Add button panel
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        panel.add(Box.createVerticalStrut(12), gbc);
        gbc.gridy = ++row;
        panel.add(buttonPanel, gbc);

        btnAdd.addActionListener(e -> {
            try {
                String tenNSX = tfTenNSX.getText().trim();
                String quocGia = tfQuocGia.getText().trim();

                if (tenNSX.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập tên nhà sản xuất!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                NhaSanXuat nsx = new NhaSanXuat();
                nsx.setTenNhaSX(tenNSX);
                nsx.setQuocGia(quocGia);
                nsx.setNgayTao(new java.sql.Timestamp(System.currentTimeMillis()));

                if (controller.addNhaSanXuat(nsx)) {
                    JOptionPane.showMessageDialog(this, "Thêm nhà sản xuất thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    btnRefresh.doClick();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm nhà sản xuất thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> dispose());

        mainPanel.add(panel, BorderLayout.CENTER);
        getContentPane().add(mainPanel);
        
        pack();
        setLocationRelativeTo(parent);
    }

    // Rounded border class
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
