package com.uit.vaccinemanagement.view.dialogs;

import com.uit.vaccinemanagement.controller.BacSiController;
import com.uit.vaccinemanagement.model.NguoiDung;
import com.uit.vaccinemanagement.model.TiemChung;
import com.uit.vaccinemanagement.view.AuthButton;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ChiDinhTiemDialog extends JDialog {
    private final BacSiController bacSiController;

    public ChiDinhTiemDialog(JFrame parent, NguoiDung currentUser, BacSiController bacSiController) {
        super(parent, "Tạo chỉ định tiêm", true);
        this.bacSiController = bacSiController;
        setSize(500, 450);

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
        Dimension textFieldSize = new Dimension(220, 36);

        javax.swing.border.Border roundedBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 8, 4, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Khách hàng
        JLabel lblKhach = new JLabel("Khách hàng:");
        lblKhach.setFont(uiFont);
        JComboBox<String> cbKhach = new JComboBox<>();
        cbKhach.setFont(uiFont);
        cbKhach.setPreferredSize(textFieldSize);
        List<NguoiDung> khachList = bacSiController.getAllCustomers();
        DefaultComboBoxModel<String> khachModel = new DefaultComboBoxModel<>();
        for (NguoiDung khach : khachList) {
            khachModel.addElement(khach.getMaNguoiDung() + " - " + khach.getHoTen());
        }
        cbKhach.setModel(khachModel);

        // Vaccine
        JLabel lblVaccine = new JLabel("Vắc xin:");
        lblVaccine.setFont(uiFont);
        JComboBox<String> cbVaccine = new JComboBox<>();
        cbVaccine.setFont(uiFont);
        cbVaccine.setPreferredSize(textFieldSize);
        List<Object[]> vaccines = bacSiController.getAvailableVaccines();
        for (Object[] v : vaccines) {
            String maVaccine = (String) v[0];
            String tenVaccine = (String) v[1];
            cbVaccine.addItem(maVaccine + " - " + tenVaccine);
        }

        // Ngày tiêm
        JLabel lblNgayTiem = new JLabel("Ngày tiêm (yyyy-mm-dd):");
        lblNgayTiem.setFont(uiFont);
        JTextField tfNgayTiem = new JTextField();
        tfNgayTiem.setFont(uiFont);
        tfNgayTiem.setPreferredSize(textFieldSize);
        tfNgayTiem.setBorder(
            BorderFactory.createCompoundBorder(
                new RoundedBorder(Color.LIGHT_GRAY, 1, 12),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)
            )
        );

        // Trạng thái
        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setFont(uiFont);
        JComboBox<String> cbTrangThai = new JComboBox<>(new String[]{
            "cho_tiem", "da_tiem", "huy"
        });
        cbTrangThai.setFont(uiFont);
        cbTrangThai.setPreferredSize(textFieldSize);

        // Ghi chú
        JLabel lblGhiChu = new JLabel("Ghi chú:");
        lblGhiChu.setFont(uiFont);
        JTextArea taGhiChu = new JTextArea(3, 1);
        taGhiChu.setFont(uiFont);
        taGhiChu.setLineWrap(true);
        taGhiChu.setWrapStyleWord(true);
        taGhiChu.setBorder(
            BorderFactory.createCompoundBorder(
                new RoundedBorder(Color.LIGHT_GRAY, 1, 12),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)
            )
        );
        JScrollPane ghiChuScroll = new JScrollPane(taGhiChu) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.LIGHT_GRAY);
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                g2.dispose();
            }
        };
        ghiChuScroll.setPreferredSize(new Dimension(textFieldSize.width, textFieldSize.height * 3));
        ghiChuScroll.setBorder(BorderFactory.createEmptyBorder());

        // Buttons
        Dimension buttonSizeChiDinh = new Dimension(120, 36);
        AuthButton btnTaoChiDinh = new AuthButton("Tạo chỉ định", new Color(210, 90, 22));
        btnTaoChiDinh.setPreferredSize(buttonSizeChiDinh);
        btnTaoChiDinh.setFont(btnFont);

        AuthButton btnHuy = new AuthButton("Hủy", new Color(120, 120, 120));
        btnHuy.setPreferredSize(buttonSizeChiDinh);
        btnHuy.setFont(btnFont);

        JPanel panelButtonChiDinh = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        panelButtonChiDinh.setOpaque(false);
        panelButtonChiDinh.add(btnTaoChiDinh);
        panelButtonChiDinh.add(btnHuy);

        // Add to layout
        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; panel.add(lblKhach, gbc);
        gbc.gridx = 1; gbc.gridy = row++; panel.add(cbKhach, gbc);
        gbc.gridx = 0; gbc.gridy = row; panel.add(lblVaccine, gbc);
        gbc.gridx = 1; gbc.gridy = row++; panel.add(cbVaccine, gbc);
        gbc.gridx = 0; gbc.gridy = row; panel.add(lblNgayTiem, gbc);
        gbc.gridx = 1; gbc.gridy = row++; panel.add(tfNgayTiem, gbc);
        gbc.gridx = 0; gbc.gridy = row; panel.add(lblTrangThai, gbc);
        gbc.gridx = 1; gbc.gridy = row++; panel.add(cbTrangThai, gbc);
        gbc.gridx = 0; gbc.gridy = row; panel.add(lblGhiChu, gbc);
        gbc.gridx = 1; gbc.gridy = row++; panel.add(ghiChuScroll, gbc);

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        panel.add(Box.createVerticalStrut(12), gbc);
        gbc.gridy = ++row;
        panel.add(panelButtonChiDinh, gbc);

        mainPanel.add(panel, BorderLayout.CENTER);
        setContentPane(mainPanel);

        // Actions
        btnHuy.addActionListener(ev -> dispose());

        btnTaoChiDinh.addActionListener(ev -> {
            try {
                String vaccineStr = (String) cbVaccine.getSelectedItem();
                String maVaccine = vaccineStr.split(" - ")[0];
                String khachStr = (String) cbKhach.getSelectedItem();
                String maKhach = khachStr.split(" - ")[0];
                String ngayTiemStr = tfNgayTiem.getText().trim();
                java.sql.Date ngayTiem = java.sql.Date.valueOf(ngayTiemStr);
                String trangThai = (String) cbTrangThai.getSelectedItem();
                String ghiChu = taGhiChu.getText().trim();

                java.sql.Date ngayChiDinh = new java.sql.Date(System.currentTimeMillis());

                TiemChung tc = new TiemChung(
                    null,
                    maVaccine,
                    currentUser.getMaNguoiDung(),
                    maKhach,
                    ngayChiDinh,
                    ngayTiem,
                    trangThai,
                    ghiChu
                );
                try {
                    boolean success = bacSiController.createChiDinhTiem(tc);
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Tạo chỉ định tiêm thành công!");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Tạo chỉ định tiêm thất bại!");
                    }
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi nhập liệu hoặc hệ thống!");
            }
        });

        setLocationRelativeTo(parent);
    }

    // Rounded border helper class
    static class RoundedBorder extends javax.swing.border.AbstractBorder {
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
