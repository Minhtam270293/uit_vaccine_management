package com.uit.vaccinemanagement.view.dialogs;

import com.uit.vaccinemanagement.controller.AdminController;
import com.uit.vaccinemanagement.model.Vaccine;
import com.uit.vaccinemanagement.dao.VaccineDAO;
import com.uit.vaccinemanagement.model.Benh;
import com.uit.vaccinemanagement.model.NhaSanXuat;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import com.uit.vaccinemanagement.view.AuthButton; // Thêm dòng này
import com.uit.vaccinemanagement.view.SharedComponents;

public class VaccineEditDialog extends JDialog {

    public VaccineEditDialog(JFrame parent, Vaccine vc, AdminController controller, JButton btnRefresh) {
        super(parent, "Sửa vắc xin", true);
        setSize(600, 850);
        setMinimumSize(new Dimension(600, 850));
        setMaximumSize(new Dimension(600, 850));
        setPreferredSize(new Dimension(600, 850));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));
        mainPanel.setBackground(new Color(250, 252, 255)); // màu nền sáng hơn

        Font uiFont = new Font("Arial", Font.PLAIN, 15);
        Font labelFont = uiFont;
        Font btnFont = new Font("Arial", Font.BOLD, 15);
        Dimension textFieldSize = new Dimension(220, 36); // giống ChiDinhTiemDialog

        javax.swing.border.Border roundedBorder = BorderFactory.createCompoundBorder(
            new SharedComponents.RoundedBorder(Color.LIGHT_GRAY, 1, 16), // sử dụng custom border bo góc 16px
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        );

        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setOpaque(false); // giữ nền sáng cho mainPanel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Các trường với kích thước giống "Ngày tiêm"
        // Để bo góc nền dưới giống SignUpView, cần setOpaque(false) cho JTextField
        // và chỉ dùng Border bo góc, không override paintComponent
        JLabel lblMaVaccine = new JLabel("Mã vắc xin:");
        lblMaVaccine.setFont(labelFont);
        JTextField tfMaVaccine = new JTextField(vc.getMaVaccine());
        tfMaVaccine.setFont(uiFont);
        tfMaVaccine.setBorder(roundedBorder);
        tfMaVaccine.setPreferredSize(textFieldSize);
        tfMaVaccine.setMinimumSize(textFieldSize);
        tfMaVaccine.setMaximumSize(textFieldSize);
        tfMaVaccine.setOpaque(false);

        JLabel lblTenVaccine = new JLabel("Tên vắc xin:");
        lblTenVaccine.setFont(labelFont);
        JTextField tfTenVaccine = new JTextField(vc.getTenVaccine());
        tfTenVaccine.setFont(uiFont);
        tfTenVaccine.setBorder(roundedBorder);
        tfTenVaccine.setPreferredSize(textFieldSize);
        tfTenVaccine.setMinimumSize(textFieldSize);
        tfTenVaccine.setMaximumSize(textFieldSize);
        tfTenVaccine.setOpaque(false);

        JLabel lblHinhAnhUrl = new JLabel("Hình ảnh URL:");
        lblHinhAnhUrl.setFont(labelFont);
        JTextField tfHinhAnhUrl = new JTextField(vc.getHinhAnhUrl());
        tfHinhAnhUrl.setFont(uiFont);
        tfHinhAnhUrl.setBorder(roundedBorder);
        tfHinhAnhUrl.setPreferredSize(textFieldSize);
        tfHinhAnhUrl.setMinimumSize(textFieldSize);
        tfHinhAnhUrl.setMaximumSize(textFieldSize);
        tfHinhAnhUrl.setOpaque(false);

        JLabel lblSoLo = new JLabel("Số lô:");
        lblSoLo.setFont(labelFont);
        JTextField tfSoLo = new JTextField(vc.getSoLo());
        tfSoLo.setFont(uiFont);
        tfSoLo.setBorder(roundedBorder);
        tfSoLo.setPreferredSize(textFieldSize);
        tfSoLo.setMinimumSize(textFieldSize);
        tfSoLo.setMaximumSize(textFieldSize);
        tfSoLo.setOpaque(false);

        JLabel lblNgaySX = new JLabel("Ngày SX:");
        lblNgaySX.setFont(labelFont);
        JTextField tfNgaySX = new JTextField(vc.getNgaySX() != null ? vc.getNgaySX().toString() : "");
        tfNgaySX.setFont(uiFont);
        tfNgaySX.setBorder(roundedBorder);
        tfNgaySX.setPreferredSize(textFieldSize);
        tfNgaySX.setMinimumSize(textFieldSize);
        tfNgaySX.setMaximumSize(textFieldSize);
        tfNgaySX.setOpaque(false);

        JLabel lblNgayHetHan = new JLabel("Ngày hết hạn:");
        lblNgayHetHan.setFont(labelFont);
        JTextField tfNgayHetHan = new JTextField(vc.getNgayHetHan() != null ? vc.getNgayHetHan().toString() : "");
        tfNgayHetHan.setFont(uiFont);
        tfNgayHetHan.setBorder(roundedBorder);
        tfNgayHetHan.setPreferredSize(textFieldSize);
        tfNgayHetHan.setMinimumSize(textFieldSize);
        tfNgayHetHan.setMaximumSize(textFieldSize);
        tfNgayHetHan.setOpaque(false);

        JLabel lblTongSL = new JLabel("Tổng SL:");
        lblTongSL.setFont(labelFont);
        JTextField tfTongSL = new JTextField(String.valueOf(vc.getTongSL()));
        tfTongSL.setFont(uiFont);
        tfTongSL.setBorder(roundedBorder);
        tfTongSL.setPreferredSize(textFieldSize);
        tfTongSL.setMinimumSize(textFieldSize);
        tfTongSL.setMaximumSize(textFieldSize);
        tfTongSL.setOpaque(false);

        JLabel lblSLConLai = new JLabel("SL còn lại:");
        lblSLConLai.setFont(labelFont);
        JTextField tfSLConLai = new JTextField(String.valueOf(vc.getSlConLai()));
        tfSLConLai.setFont(uiFont);
        tfSLConLai.setBorder(roundedBorder);
        tfSLConLai.setPreferredSize(textFieldSize);
        tfSLConLai.setMinimumSize(textFieldSize);
        tfSLConLai.setMaximumSize(textFieldSize);
        tfSLConLai.setOpaque(false);

        JLabel lblGiaNhap = new JLabel("Giá nhập:");
        lblGiaNhap.setFont(labelFont);
        JTextField tfGiaNhap = new JTextField(String.valueOf(vc.getGiaNhap()));
        tfGiaNhap.setFont(uiFont);
        tfGiaNhap.setBorder(roundedBorder);
        tfGiaNhap.setPreferredSize(textFieldSize);
        tfGiaNhap.setMinimumSize(textFieldSize);
        tfGiaNhap.setMaximumSize(textFieldSize);
        tfGiaNhap.setOpaque(false);

        JLabel lblGiaBan = new JLabel("Giá bán:");
        lblGiaBan.setFont(labelFont);
        JTextField tfGiaBan = new JTextField(String.valueOf(vc.getGiaBan()));
        tfGiaBan.setFont(uiFont);
        tfGiaBan.setBorder(roundedBorder);
        tfGiaBan.setPreferredSize(textFieldSize);
        tfGiaBan.setMinimumSize(textFieldSize);
        tfGiaBan.setMaximumSize(textFieldSize);
        tfGiaBan.setOpaque(false);

        JLabel lblMoTa = new JLabel("Mô tả:");
        lblMoTa.setFont(labelFont);
        JTextField tfMoTa = new JTextField(vc.getMoTa());
        tfMoTa.setFont(uiFont);
        tfMoTa.setBorder(roundedBorder);
        tfMoTa.setPreferredSize(textFieldSize);
        tfMoTa.setMinimumSize(textFieldSize);
        tfMoTa.setMaximumSize(textFieldSize);
        tfMoTa.setOpaque(false);

        JLabel lblMaBenh = new JLabel("Bệnh:");
        lblMaBenh.setFont(labelFont);
        JComboBox<String> cbBenh = new JComboBox<>();
        cbBenh.setFont(uiFont);
        cbBenh.setPreferredSize(textFieldSize);
        cbBenh.setMinimumSize(textFieldSize);
        cbBenh.setMaximumSize(textFieldSize);

        // Populate diseases
        List<Benh> benhList = controller.getAllBenh();
        String currentBenh = vc.getMaBenh();
        for (Benh benh : benhList) {
            String item = benh.getMaBenh() + " - " + benh.getTenBenh();
            cbBenh.addItem(item);
            if (benh.getMaBenh().equals(currentBenh)) {
                cbBenh.setSelectedItem(item);
            }
        }

        JLabel lblMaNhaSX = new JLabel("Nhà sản xuất:");
        lblMaNhaSX.setFont(labelFont);
        JComboBox<String> cbNhaSX = new JComboBox<>();
        cbNhaSX.setFont(uiFont);
        cbNhaSX.setPreferredSize(textFieldSize);
        cbNhaSX.setMinimumSize(textFieldSize);
        cbNhaSX.setMaximumSize(textFieldSize);

        // Populate manufacturers
        List<NhaSanXuat> nsxList = controller.getAllNhaSanXuat();
        String currentNhaSX = vc.getMaNhaSX();
        for (NhaSanXuat nsx : nsxList) {
            String item = nsx.getMaNhaSX() + " - " + nsx.getTenNhaSX();
            cbNhaSX.addItem(item);
            if (nsx.getMaNhaSX().equals(currentNhaSX)) {
                cbNhaSX.setSelectedItem(item);
            }
        }

        JLabel lblNgayTao = new JLabel("Ngày tạo:");
        lblNgayTao.setFont(labelFont);
        JTextField tfNgayTao = new JTextField(vc.getNgayTao() != null ? vc.getNgayTao().toString() : "");
        tfNgayTao.setFont(uiFont);
        tfNgayTao.setBorder(roundedBorder);
        tfNgayTao.setPreferredSize(textFieldSize);
        tfNgayTao.setMinimumSize(textFieldSize);
        tfNgayTao.setMaximumSize(textFieldSize);
        tfNgayTao.setOpaque(false);

        // Thêm các trường vào fieldsPanel
        int row = 0;
        gbc.gridx = 0;
        gbc.gridy = row;
        fieldsPanel.add(lblMaVaccine, gbc);
        gbc.gridx = 1;
        gbc.gridy = row++;
        fieldsPanel.add(tfMaVaccine, gbc);
        gbc.gridx = 0;
        gbc.gridy = row;
        fieldsPanel.add(lblTenVaccine, gbc);
        gbc.gridx = 1;
        gbc.gridy = row++;
        fieldsPanel.add(tfTenVaccine, gbc);
        gbc.gridx = 0;
        gbc.gridy = row;
        fieldsPanel.add(lblHinhAnhUrl, gbc);
        gbc.gridx = 1;
        gbc.gridy = row++;
        fieldsPanel.add(tfHinhAnhUrl, gbc);
        gbc.gridx = 0;
        gbc.gridy = row;
        fieldsPanel.add(lblSoLo, gbc);
        gbc.gridx = 1;
        gbc.gridy = row++;
        fieldsPanel.add(tfSoLo, gbc);
        gbc.gridx = 0;
        gbc.gridy = row;
        fieldsPanel.add(lblNgaySX, gbc);
        gbc.gridx = 1;
        gbc.gridy = row++;
        fieldsPanel.add(tfNgaySX, gbc);
        gbc.gridx = 0;
        gbc.gridy = row;
        fieldsPanel.add(lblNgayHetHan, gbc);
        gbc.gridx = 1;
        gbc.gridy = row++;
        fieldsPanel.add(tfNgayHetHan, gbc);
        gbc.gridx = 0;
        gbc.gridy = row;
        fieldsPanel.add(lblTongSL, gbc);
        gbc.gridx = 1;
        gbc.gridy = row++;
        fieldsPanel.add(tfTongSL, gbc);
        gbc.gridx = 0;
        gbc.gridy = row;
        fieldsPanel.add(lblSLConLai, gbc);
        gbc.gridx = 1;
        gbc.gridy = row++;
        fieldsPanel.add(tfSLConLai, gbc);
        gbc.gridx = 0;
        gbc.gridy = row;
        fieldsPanel.add(lblGiaNhap, gbc);
        gbc.gridx = 1;
        gbc.gridy = row++;
        fieldsPanel.add(tfGiaNhap, gbc);
        gbc.gridx = 0;
        gbc.gridy = row;
        fieldsPanel.add(lblGiaBan, gbc);
        gbc.gridx = 1;
        gbc.gridy = row++;
        fieldsPanel.add(tfGiaBan, gbc);
        gbc.gridx = 0;
        gbc.gridy = row;
        fieldsPanel.add(lblMoTa, gbc);
        gbc.gridx = 1;
        gbc.gridy = row++;
        fieldsPanel.add(tfMoTa, gbc);
        gbc.gridx = 0;
        gbc.gridy = row;
        fieldsPanel.add(lblMaBenh, gbc);
        gbc.gridx = 1;
        gbc.gridy = row++;
        fieldsPanel.add(cbBenh, gbc);
        gbc.gridx = 0;
        gbc.gridy = row;
        fieldsPanel.add(lblMaNhaSX, gbc);
        gbc.gridx = 1;
        gbc.gridy = row++;
        fieldsPanel.add(cbNhaSX, gbc);
        gbc.gridx = 0;
        gbc.gridy = row;
        fieldsPanel.add(lblNgayTao, gbc);
        gbc.gridx = 1;
        gbc.gridy = row++;
        fieldsPanel.add(tfNgayTao, gbc);

        // Panel cho nút
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

        // Add button panel
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        fieldsPanel.add(Box.createVerticalStrut(12), gbc);
        gbc.gridy = ++row;
        fieldsPanel.add(buttonPanel, gbc);

        btnSave.addActionListener(saveEv -> {
            try {
                vc.setTenVaccine(tfTenVaccine.getText().trim());
                vc.setHinhAnhUrl(tfHinhAnhUrl.getText().trim());
                vc.setSoLo(tfSoLo.getText().trim());
                vc.setNgaySX(tfNgaySX.getText().isEmpty() ? null : java.sql.Date.valueOf(tfNgaySX.getText().trim()));
                vc.setNgayHetHan(tfNgayHetHan.getText().isEmpty() ? null : java.sql.Date.valueOf(tfNgayHetHan.getText().trim()));
                vc.setTongSL(tfTongSL.getText().isEmpty() ? 0 : Integer.parseInt(tfTongSL.getText().trim()));
                vc.setSlConLai(tfSLConLai.getText().isEmpty() ? 0 : Integer.parseInt(tfSLConLai.getText().trim()));
                vc.setGiaNhap(tfGiaNhap.getText().isEmpty() ? 0 : Double.parseDouble(tfGiaNhap.getText().trim()));
                vc.setGiaBan(tfGiaBan.getText().isEmpty() ? 0 : Double.parseDouble(tfGiaBan.getText().trim()));
                vc.setMoTa(tfMoTa.getText().trim());

                String selectedBenh = cbBenh.getSelectedItem().toString();
                String maBenh = selectedBenh.split(" - ")[0];
                vc.setMaBenh(maBenh);

                String selectedNSX = cbNhaSX.getSelectedItem().toString();
                String maNhaSX = selectedNSX.split(" - ")[0];
                vc.setMaNhaSX(maNhaSX);
                
                vc.setNgayTao(new java.sql.Timestamp(System.currentTimeMillis()));

                if (controller.updateVaccine(vc)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                    dispose();
                    btnRefresh.doClick();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi dữ liệu: " + ex.getMessage());
                return;
            }
        });
        btnCancel.addActionListener(saveEv -> dispose());
        mainPanel.add(fieldsPanel);
        setContentPane(mainPanel); // Thêm dòng này để hiển thị nội dung
        setLocationRelativeTo(parent);
    }

    // Đảm bảo class RoundedBorder nằm ngoài VaccineEditDialog, không lồng bên trong bất kỳ phương thức nào
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