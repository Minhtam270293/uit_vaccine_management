package com.uit.vaccinemanagement.view;

import com.uit.vaccinemanagement.controller.AdminController;
import com.uit.vaccinemanagement.model.Vaccine;
import com.uit.vaccinemanagement.model.Benh;
import com.uit.vaccinemanagement.model.NhaSanXuat;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class VaccineAddDialog extends JDialog {

    public VaccineAddDialog(JFrame parent, AdminController controller, JButton btnRefresh) {
        super(parent, "Thêm vaccine", true);
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

        // Get list for combobox
        List<NhaSanXuat> nsxList = controller.getAllNhaSanXuat();

        // Create input fields with rounded borders
        JLabel lblTenVaccine = new JLabel("Tên vaccine:");
        lblTenVaccine.setFont(uiFont);
        JTextField tfTenVaccine = new JTextField();
        tfTenVaccine.setFont(uiFont);
        tfTenVaccine.setPreferredSize(textFieldSize);
        tfTenVaccine.setBorder(roundedBorder);

        JLabel lblHinhAnh = new JLabel("URL Hình ảnh:");
        lblHinhAnh.setFont(uiFont);
        JTextField tfHinhAnh = new JTextField();
        tfHinhAnh.setFont(uiFont);
        tfHinhAnh.setPreferredSize(textFieldSize);
        tfHinhAnh.setBorder(roundedBorder);

        JLabel lblSoLo = new JLabel("Số lô:");
        lblSoLo.setFont(uiFont);
        JTextField tfSoLo = new JTextField();
        tfSoLo.setFont(uiFont);
        tfSoLo.setPreferredSize(textFieldSize);
        tfSoLo.setBorder(roundedBorder);

        JLabel lblNgaySX = new JLabel("Ngày sản xuất (yyyy-MM-dd):");
        lblNgaySX.setFont(uiFont);
        JTextField tfNgaySX = new JTextField();
        tfNgaySX.setFont(uiFont);
        tfNgaySX.setPreferredSize(textFieldSize);
        tfNgaySX.setBorder(roundedBorder);

        JLabel lblNgayHH = new JLabel("Ngày hết hạn (yyyy-MM-dd):");
        lblNgayHH.setFont(uiFont);
        JTextField tfNgayHH = new JTextField();
        tfNgayHH.setFont(uiFont);
        tfNgayHH.setPreferredSize(textFieldSize);
        tfNgayHH.setBorder(roundedBorder);

        JLabel lblTongSL = new JLabel("Tổng số lượng:");
        lblTongSL.setFont(uiFont);
        JTextField tfTongSL = new JTextField();
        tfTongSL.setFont(uiFont);
        tfTongSL.setPreferredSize(textFieldSize);
        tfTongSL.setBorder(roundedBorder);

        JLabel lblGiaNhap = new JLabel("Giá nhập:");
        lblGiaNhap.setFont(uiFont);
        JTextField tfGiaNhap = new JTextField();
        tfGiaNhap.setFont(uiFont);
        tfGiaNhap.setPreferredSize(textFieldSize);
        tfGiaNhap.setBorder(roundedBorder);

        JLabel lblGiaBan = new JLabel("Giá bán:");
        lblGiaBan.setFont(uiFont);
        JTextField tfGiaBan = new JTextField();
        tfGiaBan.setFont(uiFont);
        tfGiaBan.setPreferredSize(textFieldSize);
        tfGiaBan.setBorder(roundedBorder);

        JLabel lblMoTa = new JLabel("Mô tả:");
        lblMoTa.setFont(uiFont);
        JTextArea taMoTa = new JTextArea(3, 20);
        taMoTa.setFont(uiFont);
        JScrollPane spMoTa = new JScrollPane(taMoTa);
        spMoTa.setBorder(roundedBorder);

        JLabel lblBenh = new JLabel("Mã bệnh:");
        lblBenh.setFont(uiFont);
        JTextField tfBenh = new JTextField();
        tfBenh.setFont(uiFont);
        tfBenh.setPreferredSize(textFieldSize);
        tfBenh.setBorder(roundedBorder);

        JLabel lblNSX = new JLabel("Nhà sản xuất:");
        lblNSX.setFont(uiFont);
        JComboBox<NhaSanXuat> cbNSX = new JComboBox<>(nsxList.toArray(new NhaSanXuat[0]));
        cbNSX.setFont(uiFont);
        cbNSX.setPreferredSize(textFieldSize);

        // Add components to panel using GridBagLayout
        int row = 0;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(lblTenVaccine, gbc);
        gbc.gridx = 1;
        gbc.gridy = row++;
        panel.add(tfTenVaccine, gbc);
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(lblHinhAnh, gbc);
        gbc.gridx = 1;
        gbc.gridy = row++;
        panel.add(tfHinhAnh, gbc);
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(lblSoLo, gbc);
        gbc.gridx = 1;
        gbc.gridy = row++;
        panel.add(tfSoLo, gbc);
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(lblNgaySX, gbc);
        gbc.gridx = 1;
        gbc.gridy = row++;
        panel.add(tfNgaySX, gbc);
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(lblNgayHH, gbc);
        gbc.gridx = 1;
        gbc.gridy = row++;
        panel.add(tfNgayHH, gbc);
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(lblTongSL, gbc);
        gbc.gridx = 1;
        gbc.gridy = row++;
        panel.add(tfTongSL, gbc);
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(lblGiaNhap, gbc);
        gbc.gridx = 1;
        gbc.gridy = row++;
        panel.add(tfGiaNhap, gbc);
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(lblGiaBan, gbc);
        gbc.gridx = 1;
        gbc.gridy = row++;
        panel.add(tfGiaBan, gbc);
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(lblBenh, gbc);
        gbc.gridx = 1;
        gbc.gridy = row++;
        panel.add(tfBenh, gbc);
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(lblNSX, gbc);
        gbc.gridx = 1;
        gbc.gridy = row++;
        panel.add(cbNSX, gbc);

        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(lblMoTa, gbc);
        gbc.gridx = 1;
        gbc.gridy = row++;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(spMoTa, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;

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

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        btnAdd.addActionListener(e -> {
            try {
                String tenVaccine = tfTenVaccine.getText().trim();
                String hinhAnh = tfHinhAnh.getText().trim();
                String soLo = tfSoLo.getText().trim();
                String ngaySXStr = tfNgaySX.getText().trim();
                String ngayHHStr = tfNgayHH.getText().trim();
                String tongSLStr = tfTongSL.getText().trim();
                String giaNhapStr = tfGiaNhap.getText().trim();
                String giaBanStr = tfGiaBan.getText().trim();
                String moTa = taMoTa.getText().trim();
                String maBenhStr = tfBenh.getText().trim();
                NhaSanXuat nsx = (NhaSanXuat) cbNSX.getSelectedItem();

                // Validation
                if (tenVaccine.isEmpty() || soLo.isEmpty() || ngaySXStr.isEmpty()
                        || ngayHHStr.isEmpty() || tongSLStr.isEmpty() || giaNhapStr.isEmpty()
                        || giaBanStr.isEmpty() || maBenhStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin bắt buộc!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Parse dates
                Date ngaySX, ngayHH;
                try {
                    ngaySX = new Date(dateFormat.parse(ngaySXStr).getTime());
                    ngayHH = new Date(dateFormat.parse(ngayHHStr).getTime());
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Ngày không hợp lệ! Vui lòng nhập theo định dạng yyyy-MM-dd",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Parse numbers
                int tongSL;
                double giaNhap, giaBan;
                try {
                    tongSL = Integer.parseInt(tongSLStr);
                    giaNhap = Double.parseDouble(giaNhapStr);
                    giaBan = Double.parseDouble(giaBanStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Giá trị số không hợp lệ!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Vaccine vaccine = new Vaccine();
                vaccine.setTenVaccine(tenVaccine);
                vaccine.setHinhAnhUrl(hinhAnh);
                vaccine.setSoLo(soLo);
                vaccine.setNgaySX(ngaySX);
                vaccine.setNgayHetHan(ngayHH);
                vaccine.setTongSL(tongSL);
                vaccine.setSlConLai(tongSL); // Initially same as total
                vaccine.setGiaNhap(giaNhap);
                vaccine.setGiaBan(giaBan);
                vaccine.setMoTa(moTa);
                vaccine.setMaBenh(maBenhStr);
                vaccine.setMaNhaSX(nsx.getMaNhaSX());
                vaccine.setNgayTao(new java.sql.Timestamp(System.currentTimeMillis()));

                if (controller.addVaccine(vaccine)) {
                    JOptionPane.showMessageDialog(this, "Thêm vaccine thành công!",
                            "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    btnRefresh.doClick();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm vaccine thất bại!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
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
