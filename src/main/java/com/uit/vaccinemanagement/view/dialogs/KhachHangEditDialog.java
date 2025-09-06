package com.uit.vaccinemanagement.view.dialogs;

import com.uit.vaccinemanagement.model.NguoiDung;
import com.uit.vaccinemanagement.controller.BacSiController;
import com.uit.vaccinemanagement.view.AuthButton;

import javax.swing.*;
import java.awt.*;

public class KhachHangEditDialog extends JDialog {

    public KhachHangEditDialog(JFrame parent, NguoiDung khach, BacSiController controller, JButton btnRefresh) {
        super(parent, "Sửa thông tin khách hàng", true);
        setSize(500, 450);
        setMinimumSize(new Dimension(500, 450));
        setMaximumSize(new Dimension(500, 450));
        setPreferredSize(new Dimension(500, 450));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));
        mainPanel.setBackground(new Color(250, 252, 255));

        Font uiFont = new Font("Arial", Font.PLAIN, 15);
        Font labelFont = uiFont;
        Font btnFont = new Font("Arial", Font.BOLD, 15);
        Dimension textFieldSize = new Dimension(220, 36);

        javax.swing.border.Border roundedBorder = BorderFactory.createCompoundBorder(
            new RoundedBorder(Color.LIGHT_GRAY, 1, 12),
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        );

        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Các trường thông tin khách hàng
        JLabel lblHoTen = new JLabel("Họ tên:");
        lblHoTen.setFont(labelFont);
        JTextField tfHoTen = new JTextField(khach.getHoTen());
        tfHoTen.setFont(uiFont);
        tfHoTen.setBorder(roundedBorder);
        tfHoTen.setPreferredSize(textFieldSize);
        tfHoTen.setOpaque(false);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(labelFont);
        JTextField tfEmail = new JTextField(khach.getEmail());
        tfEmail.setFont(uiFont);
        tfEmail.setBorder(roundedBorder);
        tfEmail.setPreferredSize(textFieldSize);
        tfEmail.setOpaque(false);

        JLabel lblTenDangNhap = new JLabel("Tên đăng nhập:");
        lblTenDangNhap.setFont(labelFont);
        JTextField tfTenDangNhap = new JTextField(khach.getTenDangNhap());
        tfTenDangNhap.setFont(uiFont);
        tfTenDangNhap.setBorder(roundedBorder);
        tfTenDangNhap.setPreferredSize(textFieldSize);
        tfTenDangNhap.setOpaque(false);

        JLabel lblNgaySinh = new JLabel("Ngày sinh (yyyy-MM-dd):");
        lblNgaySinh.setFont(labelFont);
        JTextField tfNgaySinh = new JTextField(khach.getNgaySinh() != null ? khach.getNgaySinh().toString() : "");
        tfNgaySinh.setFont(uiFont);
        tfNgaySinh.setBorder(roundedBorder);
        tfNgaySinh.setPreferredSize(textFieldSize);
        tfNgaySinh.setOpaque(false);

        JLabel lblGioiTinh = new JLabel("Giới tính:");
        lblGioiTinh.setFont(labelFont);
        JComboBox<String> cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});
        cbGioiTinh.setFont(uiFont);
        cbGioiTinh.setPreferredSize(textFieldSize);
        cbGioiTinh.setSelectedItem(khach.getGioiTinh());

        // Thêm các trường vào fieldsPanel
        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; fieldsPanel.add(lblHoTen, gbc);
        gbc.gridx = 1; gbc.gridy = row++; fieldsPanel.add(tfHoTen, gbc);
        gbc.gridx = 0; gbc.gridy = row; fieldsPanel.add(lblEmail, gbc);
        gbc.gridx = 1; gbc.gridy = row++; fieldsPanel.add(tfEmail, gbc);
        gbc.gridx = 0; gbc.gridy = row; fieldsPanel.add(lblTenDangNhap, gbc);
        gbc.gridx = 1; gbc.gridy = row++; fieldsPanel.add(tfTenDangNhap, gbc);
        gbc.gridx = 0; gbc.gridy = row; fieldsPanel.add(lblNgaySinh, gbc);
        gbc.gridx = 1; gbc.gridy = row++; fieldsPanel.add(tfNgaySinh, gbc);
        gbc.gridx = 0; gbc.gridy = row; fieldsPanel.add(lblGioiTinh, gbc);
        gbc.gridx = 1; gbc.gridy = row++; fieldsPanel.add(cbGioiTinh, gbc);

        // Panel cho nút
        JPanel buttonPanelWrapper = new JPanel();
        buttonPanelWrapper.setLayout(new BoxLayout(buttonPanelWrapper, BoxLayout.Y_AXIS));
        buttonPanelWrapper.setOpaque(false);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        buttonPanel.setOpaque(false);

        AuthButton btnSave = new AuthButton("Lưu", new Color(210, 90, 22));
        AuthButton btnCancel = new AuthButton("Hủy", new Color(120, 120, 120));
        btnSave.setFont(btnFont);
        btnCancel.setFont(btnFont);
        Dimension buttonSize = new Dimension(120, 36);
        btnSave.setPreferredSize(buttonSize);
        btnCancel.setPreferredSize(buttonSize);

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        buttonPanelWrapper.add(Box.createVerticalStrut(18));
        buttonPanelWrapper.add(buttonPanel);
        buttonPanelWrapper.add(Box.createVerticalStrut(18));

        mainPanel.add(fieldsPanel);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(buttonPanelWrapper);

        setContentPane(mainPanel);

        btnSave.addActionListener(e -> {
            try {
                String hoTen = tfHoTen.getText().trim();
                String email = tfEmail.getText().trim();
                String tenDangNhap = tfTenDangNhap.getText().trim();
                String ngaySinhStr = tfNgaySinh.getText().trim();
                String gioiTinh = cbGioiTinh.getSelectedItem().toString();

                if (hoTen.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Họ tên không được để trống!");
                    return;
                }

                java.sql.Date ngaySinh = null;
                if (!ngaySinhStr.isEmpty()) {
                    try {
                        ngaySinh = java.sql.Date.valueOf(ngaySinhStr);
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ (yyyy-MM-dd)!");
                        return;
                    }
                }

                boolean success = controller.updateKhach(
                    khach.getMaNguoiDung(),
                    hoTen,
                    tenDangNhap,
                    email,
                    ngaySinh,
                    gioiTinh
                );
                if (success) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                    dispose();
                    if (btnRefresh != null) btnRefresh.doClick();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        });

        btnCancel.addActionListener(e -> dispose());
        setLocationRelativeTo(parent);
    }

    // Border bo góc giống VaccineEditDialog
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