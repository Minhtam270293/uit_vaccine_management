package com.uit.vaccinemanagement.view.dialogs;

import com.uit.vaccinemanagement.controller.AdminController;
import com.uit.vaccinemanagement.model.NguoiDung;
import com.uit.vaccinemanagement.util.Role;
import com.uit.vaccinemanagement.view.AuthButton;

import javax.swing.*;
import java.awt.*;

public class NguoiDungAddDialog extends JDialog {

    public NguoiDungAddDialog(JFrame parent, AdminController controller, JButton btnRefresh) {
        super(parent, "Thêm người dùng", true);
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
        JLabel lblTenDangNhap = new JLabel("Tên đăng nhập:");
        lblTenDangNhap.setFont(uiFont);
        JTextField tfTenDangNhap = new JTextField();
        tfTenDangNhap.setFont(uiFont);
        tfTenDangNhap.setPreferredSize(textFieldSize);
        tfTenDangNhap.setBorder(roundedBorder);

        JLabel lblMatKhau = new JLabel("Mật khẩu:");
        lblMatKhau.setFont(uiFont);
        JPasswordField tfMatKhau = new JPasswordField();
        tfMatKhau.setFont(uiFont);
        tfMatKhau.setPreferredSize(textFieldSize);
        tfMatKhau.setBorder(roundedBorder);

        JLabel lblHoTen = new JLabel("Họ tên:");
        lblHoTen.setFont(uiFont);
        JTextField tfHoTen = new JTextField();
        tfHoTen.setFont(uiFont);
        tfHoTen.setPreferredSize(textFieldSize);
        tfHoTen.setBorder(roundedBorder);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(uiFont);
        JTextField tfEmail = new JTextField();
        tfEmail.setFont(uiFont);
        tfEmail.setPreferredSize(textFieldSize);
        tfEmail.setBorder(roundedBorder);

        JLabel lblNgaySinh = new JLabel("Ngày sinh (yyyy-MM-dd):");
        lblNgaySinh.setFont(uiFont);
        JTextField tfNgaySinh = new JTextField();
        tfNgaySinh.setFont(uiFont);
        tfNgaySinh.setPreferredSize(textFieldSize);
        tfNgaySinh.setBorder(roundedBorder);

        JLabel lblVaiTro = new JLabel("Vai trò:");
        lblVaiTro.setFont(uiFont);
        JComboBox<Role> cbVaiTro = new JComboBox<>(Role.values());
        cbVaiTro.setFont(uiFont);
        cbVaiTro.setPreferredSize(textFieldSize);

        JLabel lblGioiTinh = new JLabel("Giới tính:");
        lblGioiTinh.setFont(uiFont);
        JComboBox<String> cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});
        cbGioiTinh.setFont(uiFont);
        cbGioiTinh.setPreferredSize(textFieldSize);

        // Add components to panel using GridBagLayout
        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; panel.add(lblTenDangNhap, gbc);
        gbc.gridx = 1; gbc.gridy = row++; panel.add(tfTenDangNhap, gbc);
        gbc.gridx = 0; gbc.gridy = row; panel.add(lblMatKhau, gbc);
        gbc.gridx = 1; gbc.gridy = row++; panel.add(tfMatKhau, gbc);
        gbc.gridx = 0; gbc.gridy = row; panel.add(lblHoTen, gbc);
        gbc.gridx = 1; gbc.gridy = row++; panel.add(tfHoTen, gbc);
        gbc.gridx = 0; gbc.gridy = row; panel.add(lblEmail, gbc);
        gbc.gridx = 1; gbc.gridy = row++; panel.add(tfEmail, gbc);
        gbc.gridx = 0; gbc.gridy = row; panel.add(lblNgaySinh, gbc);
        gbc.gridx = 1; gbc.gridy = row++; panel.add(tfNgaySinh, gbc);
        gbc.gridx = 0; gbc.gridy = row; panel.add(lblVaiTro, gbc);
        gbc.gridx = 1; gbc.gridy = row++; panel.add(cbVaiTro, gbc);
        gbc.gridx = 0; gbc.gridy = row; panel.add(lblGioiTinh, gbc);
        gbc.gridx = 1; gbc.gridy = row++; panel.add(cbGioiTinh, gbc);

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
                String tenDangNhap = tfTenDangNhap.getText().trim();
                String matKhau = new String(tfMatKhau.getPassword()).trim();
                String hoTen = tfHoTen.getText().trim();
                String email = tfEmail.getText().trim();
                String ngaySinhStr = tfNgaySinh.getText().trim();
                Role vaiTro = (Role) cbVaiTro.getSelectedItem();
                String gioiTinh = (String) cbGioiTinh.getSelectedItem();

                if (tenDangNhap.isEmpty() || matKhau.isEmpty() || hoTen.isEmpty()
                        || email.isEmpty() || ngaySinhStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Parse birth date
                java.sql.Date ngaySinh;
                try {
                    ngaySinh = java.sql.Date.valueOf(ngaySinhStr);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Ngày sinh không hợp lệ! Vui lòng nhập theo định dạng yyyy-MM-dd",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                NguoiDung nguoiDung = new NguoiDung();
                nguoiDung.setTenDangNhap(tenDangNhap);
                nguoiDung.setMatKhau(matKhau);  // Controller will handle password hashing
                nguoiDung.setHoTen(hoTen);
                nguoiDung.setEmail(email);
                nguoiDung.setVaiTro(vaiTro);
                nguoiDung.setNgaySinh(ngaySinh);
                nguoiDung.setGioiTinh(gioiTinh);
                nguoiDung.setNgayTao(new java.sql.Timestamp(System.currentTimeMillis()));

                if (controller.addNguoiDung(nguoiDung)) {
                    JOptionPane.showMessageDialog(this, "Thêm người dùng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    btnRefresh.doClick();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm người dùng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
