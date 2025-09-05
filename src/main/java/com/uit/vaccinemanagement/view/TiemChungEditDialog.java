package com.uit.vaccinemanagement.view;

import com.uit.vaccinemanagement.controller.BacSiController;
import javax.swing.*;
import java.awt.*;

public class TiemChungEditDialog extends JDialog {

    public TiemChungEditDialog(JFrame parent, Object[] tiemChungData, BacSiController controller, JButton btnRefresh) {
        super(parent, "Sửa thông tin tiêm chủng", true);
        setSize(500, 400);
        setMinimumSize(new Dimension(500, 400));
        setMaximumSize(new Dimension(500, 400));
        setPreferredSize(new Dimension(500, 400));

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

        // Text fields
        JLabel lblMaTiemChung = new JLabel("Mã tiêm chủng:");
        lblMaTiemChung.setFont(labelFont);
        JTextField tfMaTiemChung = new JTextField(tiemChungData[0].toString());
        tfMaTiemChung.setFont(uiFont);
        tfMaTiemChung.setBorder(roundedBorder);
        tfMaTiemChung.setPreferredSize(textFieldSize);
        tfMaTiemChung.setMinimumSize(textFieldSize);
        tfMaTiemChung.setMaximumSize(textFieldSize);
        tfMaTiemChung.setOpaque(false);
        tfMaTiemChung.setEditable(false);

        JLabel lblNgayTiem = new JLabel("Ngày tiêm (yyyy-mm-dd):");
        lblNgayTiem.setFont(labelFont);
        JTextField tfNgayTiem = new JTextField(tiemChungData[1].toString());
        tfNgayTiem.setFont(uiFont);
        tfNgayTiem.setBorder(roundedBorder);
        tfNgayTiem.setPreferredSize(textFieldSize);
        tfNgayTiem.setMinimumSize(textFieldSize);
        tfNgayTiem.setMaximumSize(textFieldSize);
        tfNgayTiem.setOpaque(false);

        JLabel lblTenVaccine = new JLabel("Tên vaccine:");
        lblTenVaccine.setFont(labelFont);
        JTextField tfTenVaccine = new JTextField(tiemChungData[2].toString());
        tfTenVaccine.setFont(uiFont);
        tfTenVaccine.setBorder(roundedBorder);
        tfTenVaccine.setPreferredSize(textFieldSize);
        tfTenVaccine.setMinimumSize(textFieldSize);
        tfTenVaccine.setMaximumSize(textFieldSize);
        tfTenVaccine.setOpaque(false);
        tfTenVaccine.setEditable(false);

        JLabel lblTenKhach = new JLabel("Tên khách:");
        lblTenKhach.setFont(labelFont);
        JTextField tfTenKhach = new JTextField(tiemChungData[3].toString());
        tfTenKhach.setFont(uiFont);
        tfTenKhach.setBorder(roundedBorder);
        tfTenKhach.setPreferredSize(textFieldSize);
        tfTenKhach.setMinimumSize(textFieldSize);
        tfTenKhach.setMaximumSize(textFieldSize);
        tfTenKhach.setOpaque(false);
        tfTenKhach.setEditable(false);

        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setFont(labelFont);
        JTextField tfTrangThai = new JTextField(tiemChungData[4].toString());
        tfTrangThai.setFont(uiFont);
        tfTrangThai.setBorder(roundedBorder);
        tfTrangThai.setPreferredSize(textFieldSize);
        tfTrangThai.setMinimumSize(textFieldSize);
        tfTrangThai.setMaximumSize(textFieldSize);
        tfTrangThai.setOpaque(false);

        JLabel lblGhiChu = new JLabel("Ghi chú:");
        lblGhiChu.setFont(labelFont);
        JTextField tfGhiChu = new JTextField(tiemChungData[5] != null ? tiemChungData[5].toString() : "");
        tfGhiChu.setFont(uiFont);
        tfGhiChu.setBorder(roundedBorder);
        tfGhiChu.setPreferredSize(textFieldSize);
        tfGhiChu.setMinimumSize(textFieldSize);
        tfGhiChu.setMaximumSize(textFieldSize);
        tfGhiChu.setOpaque(false);

        // Add fields to panel
        int row = 0;
        gbc.gridx = 0;
        gbc.gridy = row;
        fieldsPanel.add(lblMaTiemChung, gbc);
        gbc.gridx = 1;
        gbc.gridy = row++;
        fieldsPanel.add(tfMaTiemChung, gbc);

        gbc.gridx = 0;
        gbc.gridy = row;
        fieldsPanel.add(lblNgayTiem, gbc);
        gbc.gridx = 1;
        gbc.gridy = row++;
        fieldsPanel.add(tfNgayTiem, gbc);

        gbc.gridx = 0;
        gbc.gridy = row;
        fieldsPanel.add(lblTenVaccine, gbc);
        gbc.gridx = 1;
        gbc.gridy = row++;
        fieldsPanel.add(tfTenVaccine, gbc);

        gbc.gridx = 0;
        gbc.gridy = row;
        fieldsPanel.add(lblTenKhach, gbc);
        gbc.gridx = 1;
        gbc.gridy = row++;
        fieldsPanel.add(tfTenKhach, gbc);

        gbc.gridx = 0;
        gbc.gridy = row;
        fieldsPanel.add(lblTrangThai, gbc);
        gbc.gridx = 1;
        gbc.gridy = row++;
        fieldsPanel.add(tfTrangThai, gbc);

        gbc.gridx = 0;
        gbc.gridy = row;
        fieldsPanel.add(lblGhiChu, gbc);
        gbc.gridx = 1;
        gbc.gridy = row++;
        fieldsPanel.add(tfGhiChu, gbc);

        mainPanel.add(fieldsPanel);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        buttonPanel.setOpaque(false);

        // Button styling
        Dimension buttonSize = new Dimension(120, 36);
        AuthButton btnSave = new AuthButton("Lưu", new Color(210, 90, 22));
        AuthButton btnCancel = new AuthButton("Hủy", new Color(120, 120, 120));
        btnSave.setFont(btnFont);
        btnCancel.setFont(btnFont);
        btnSave.setPreferredSize(buttonSize);
        btnCancel.setPreferredSize(buttonSize);

        // Fix button panel sizing
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, buttonSize.height + 16));
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        mainPanel.add(Box.createVerticalStrut(18));
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalGlue()); // Push buttons up, avoid being cut off

        btnSave.addActionListener(e -> {
            try {
                Object[] updatedData = {
                    tfMaTiemChung.getText().trim(),
                    tfNgayTiem.getText().trim(),
                    tfTenVaccine.getText().trim(),
                    tfTenKhach.getText().trim(),
                    tfTrangThai.getText().trim(),
                    tfGhiChu.getText().trim()
                };

                if (controller.updateTiemChung(updatedData)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                    if (btnRefresh != null) {
                        btnRefresh.doClick();
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi dữ liệu: " + ex.getMessage());
            }
        });

        btnCancel.addActionListener(e -> dispose());

        add(mainPanel);
        setLocationRelativeTo(parent);
    }

    // Custom rounded border class
    private static class RoundedBorder extends javax.swing.border.AbstractBorder {
        private final Color color;
        private final int thickness;
        private final int radius;
        private final Insets insets;

        RoundedBorder(Color color, int thickness, int radius) {
            this.color = color;
            this.thickness = thickness;
            this.radius = radius;
            this.insets = new Insets(thickness, thickness, thickness, thickness);
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(thickness));
            g2d.drawRoundRect(x + thickness / 2, y + thickness / 2,
                    width - thickness, height - thickness, radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return insets;
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            return getBorderInsets(c);
        }
    }
}
