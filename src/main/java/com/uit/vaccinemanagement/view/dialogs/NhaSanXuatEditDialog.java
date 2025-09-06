package com.uit.vaccinemanagement.view.dialogs;

import com.uit.vaccinemanagement.model.NhaSanXuat;
import com.uit.vaccinemanagement.controller.AdminController;
import com.uit.vaccinemanagement.view.AuthButton;

import javax.swing.*;
import java.awt.*;

public class NhaSanXuatEditDialog extends JDialog {

    public NhaSanXuatEditDialog(JFrame parent, NhaSanXuat nsx, AdminController controller, JButton btnRefresh) {
        super(parent, "Sửa nhà sản xuất", true);
        setSize(500, 350);
        setMinimumSize(new Dimension(500, 350));
        setMaximumSize(new Dimension(500, 350));
        setPreferredSize(new Dimension(500, 350));

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

        JLabel lblMaNhaSX = new JLabel("Mã NSX:");
        lblMaNhaSX.setFont(labelFont);
        JTextField tfMaNhaSX = new JTextField(nsx.getMaNhaSX());
        tfMaNhaSX.setFont(uiFont);
        tfMaNhaSX.setBorder(roundedBorder);
        tfMaNhaSX.setPreferredSize(textFieldSize);
        tfMaNhaSX.setOpaque(false);
        tfMaNhaSX.setEditable(false);

        JLabel lblTenNhaSX = new JLabel("Tên nhà sản xuất:");
        lblTenNhaSX.setFont(labelFont);
        JTextField tfTenNhaSX = new JTextField(nsx.getTenNhaSX());
        tfTenNhaSX.setFont(uiFont);
        tfTenNhaSX.setBorder(roundedBorder);
        tfTenNhaSX.setPreferredSize(textFieldSize);
        tfTenNhaSX.setOpaque(false);

        JLabel lblQuocGia = new JLabel("Quốc gia:");
        lblQuocGia.setFont(labelFont);
        JTextField tfQuocGia = new JTextField(nsx.getQuocGia());
        tfQuocGia.setFont(uiFont);
        tfQuocGia.setBorder(roundedBorder);
        tfQuocGia.setPreferredSize(textFieldSize);
        tfQuocGia.setOpaque(false);

        JLabel lblNgayTao = new JLabel("Ngày tạo:");
        lblNgayTao.setFont(labelFont);
        JTextField tfNgayTao = new JTextField(nsx.getNgayTao() != null ? nsx.getNgayTao().toString() : "");
        tfNgayTao.setFont(uiFont);
        tfNgayTao.setBorder(roundedBorder);
        tfNgayTao.setPreferredSize(textFieldSize);
        tfNgayTao.setOpaque(false);
        tfNgayTao.setEditable(false);

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; fieldsPanel.add(lblMaNhaSX, gbc);
        gbc.gridx = 1; gbc.gridy = row++; fieldsPanel.add(tfMaNhaSX, gbc);
        gbc.gridx = 0; gbc.gridy = row; fieldsPanel.add(lblTenNhaSX, gbc);
        gbc.gridx = 1; gbc.gridy = row++; fieldsPanel.add(tfTenNhaSX, gbc);
        gbc.gridx = 0; gbc.gridy = row; fieldsPanel.add(lblQuocGia, gbc);
        gbc.gridx = 1; gbc.gridy = row++; fieldsPanel.add(tfQuocGia, gbc);
        gbc.gridx = 0; gbc.gridy = row; fieldsPanel.add(lblNgayTao, gbc);
        gbc.gridx = 1; gbc.gridy = row++; fieldsPanel.add(tfNgayTao, gbc);

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
                String tenNhaSX = tfTenNhaSX.getText().trim();
                String quocGia = tfQuocGia.getText().trim();

                nsx.setTenNhaSX(tenNhaSX);
                nsx.setQuocGia(quocGia);

                boolean success = controller.updateNhaSanXuat(nsx);
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