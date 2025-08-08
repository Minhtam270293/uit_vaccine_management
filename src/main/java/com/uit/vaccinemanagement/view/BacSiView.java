package com.uit.vaccinemanagement.view;

import com.uit.vaccinemanagement.dao.TiemChungDAO;
import com.uit.vaccinemanagement.dao.NguoiDungDAO;
import com.uit.vaccinemanagement.model.NguoiDung;

import javax.swing.*;
import com.uit.vaccinemanagement.view.SharedComponents;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class BacSiView {

    private final NguoiDung currentUser;
    private final TiemChungDAO tiemChungDAO = new TiemChungDAO();
    private final NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();

    public BacSiView(NguoiDung currentUser) {
        this.currentUser = currentUser;
    }

    public void showBacSiUI() {
        JFrame frame = new JFrame("Bác sĩ Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);

        // Left panel (column 1)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(200, 500));

        leftPanel.add(new JLabel("Tên: " + currentUser.getHoTen()));
        leftPanel.add(new JLabel("Vai trò: " + currentUser.getVaiTro()));

        JButton btnTiemChung = new JButton("Tiêm chủng");
        JButton btnKhachHang = new JButton("Khách hàng");
        JButton btnChiDinhTiem = new JButton("Chỉ định tiêm");
        JButton btnDangXuat = SharedComponents.createLogoutButton(e -> {
            frame.dispose();
            com.uit.vaccinemanagement.VaccineManagement.showLogin();
        });

        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(btnTiemChung);
        leftPanel.add(btnKhachHang);
        leftPanel.add(btnChiDinhTiem);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(btnDangXuat);
        btnChiDinhTiem.addActionListener((ActionEvent e) -> {
            JDialog dialog = new JDialog(frame, "Tạo chỉ định tiêm", true);
            dialog.setSize(400, 300);
            JPanel mainPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // Khach selection
            JLabel lblKhach = new JLabel("Khách hàng:");
            JComboBox<String> cbKhach = new JComboBox<>();
            for (com.uit.vaccinemanagement.model.NguoiDung nd : nguoiDungDAO.getAllNguoiDung()) {
                if (nd.getVaiTro().name().equalsIgnoreCase("khach")) {
                    cbKhach.addItem(nd.getMaNguoiDung() + " - " + nd.getHoTen());
                }
            }

            // Vaccine selection
            JLabel lblVaccine = new JLabel("Vaccine:");
            JComboBox<String> cbVaccine = new JComboBox<>();
            com.uit.vaccinemanagement.dao.VaccineDAO vaccineDAO = new com.uit.vaccinemanagement.dao.VaccineDAO();
            for (Object[] v : vaccineDAO.getAllVaccineAsObjectArray()) {
                cbVaccine.addItem(v[0] + " - " + v[1]); // ma_vaccine - ten_vaccine
            }

            // Ngay tiêm
            JLabel lblNgayTiem = new JLabel("Ngày tiêm (yyyy-mm-dd):");
            JTextField tfNgayTiem = new JTextField();

            // Ghi chú
            JLabel lblGhiChu = new JLabel("Ghi chú:");
            JTextArea taGhiChu = new JTextArea(3, 20);
            JScrollPane ghiChuScroll = new JScrollPane(taGhiChu);

            // Buttons
            JPanel btnPanel = new JPanel();
            JButton btnHuy = new JButton("Hủy");
            JButton btnTaoChiDinh = new JButton("Tạo chỉ định");
            btnPanel.add(btnHuy);
            btnPanel.add(btnTaoChiDinh);

            int row = 0;
            gbc.gridx = 0; gbc.gridy = row; mainPanel.add(lblKhach, gbc);
            gbc.gridx = 1; gbc.gridy = row++; mainPanel.add(cbKhach, gbc);
            gbc.gridx = 0; gbc.gridy = row; mainPanel.add(lblVaccine, gbc);
            gbc.gridx = 1; gbc.gridy = row++; mainPanel.add(cbVaccine, gbc);
            gbc.gridx = 0; gbc.gridy = row; mainPanel.add(lblNgayTiem, gbc);
            gbc.gridx = 1; gbc.gridy = row++; mainPanel.add(tfNgayTiem, gbc);
            gbc.gridx = 0; gbc.gridy = row; mainPanel.add(lblGhiChu, gbc);
            gbc.gridx = 1; gbc.gridy = row++; mainPanel.add(ghiChuScroll, gbc);
            gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2; mainPanel.add(btnPanel, gbc);

            dialog.setContentPane(mainPanel);

            btnHuy.addActionListener(ev -> dialog.dispose());

            btnTaoChiDinh.addActionListener(ev -> {
                try {
                    String vaccineStr = (String) cbVaccine.getSelectedItem();
                    String maVaccine = vaccineStr.split(" - ")[0];
                    String khachStr = (String) cbKhach.getSelectedItem();
                    String maKhach = khachStr.split(" - ")[0];
                    String ngayTiemStr = tfNgayTiem.getText().trim();
                    java.sql.Date ngayTiem = java.sql.Date.valueOf(ngayTiemStr);
                    String ghiChu = taGhiChu.getText().trim();

                    // Set ngay_chi_dinh to current timestamp
                    java.sql.Date ngayChiDinh = new java.sql.Date(System.currentTimeMillis());

                    // Create tiem_chung record
                    com.uit.vaccinemanagement.dao.TiemChungDAO tcDAO = new com.uit.vaccinemanagement.dao.TiemChungDAO();
                    com.uit.vaccinemanagement.model.TiemChung tc = new com.uit.vaccinemanagement.model.TiemChung(
                        null, // ma_tiem_chung (auto-increment)
                        maVaccine,
                        currentUser.getMaNguoiDung(),
                        maKhach,
                        ngayChiDinh,
                        ngayTiem,
                        "cho_tiem", // trang_thai_tiem
                        ghiChu
                    );
                    boolean success = tcDAO.addTiemChung(tc);
                    if (success) {
                        JOptionPane.showMessageDialog(dialog, "Tạo chỉ định tiêm thành công!");
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Tạo chỉ định tiêm thất bại!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Lỗi nhập liệu hoặc hệ thống!");
                }
            });

            dialog.setLocationRelativeTo(frame);
            dialog.setVisible(true);
        });

        // Right panel (column 2)
        JPanel rightPanel = new JPanel(new BorderLayout());
        JLabel tableTitle = new JLabel("Thông tin", SwingConstants.CENTER);
        rightPanel.add(tableTitle, BorderLayout.NORTH);

        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(220);

        frame.getContentPane().add(splitPane);

        // Event handling
        btnTiemChung.addActionListener((ActionEvent e) -> {
            tableTitle.setText("Danh sách tiêm chủng");
            List<Object[]> data = tiemChungDAO.getByMaBacSi(currentUser.getMaNguoiDung());
            String[] columns = {"ma_tiem_chung", "ngay_tiem", "ma_vaccine", "ma_bac_si", "ma_khach", "trang_thai_tiem", "ghi_chu"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);
            for (Object[] row : data) {
                model.addRow(row);
            }
            table.setModel(model);
        });

        btnKhachHang.addActionListener((ActionEvent e) -> {
            tableTitle.setText("Danh sách khách hàng");
            List<NguoiDung> khachList = nguoiDungDAO.getAllNguoiDung();
            String[] columns = {"ma_nguoi_dung", "ho_ten", "ten_dang_nhap", "email", "vai_tro", "ngay_tao", "ngay_sinh", "gioi_tinh"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);
            for (NguoiDung nd : khachList) {
                if (nd.getVaiTro().name().equalsIgnoreCase("khach")) {
                    model.addRow(new Object[] {
                        nd.getMaNguoiDung(),
                        nd.getHoTen(),
                        nd.getTenDangNhap(),
                        nd.getEmail(),
                        nd.getVaiTro(),
                        nd.getNgayTao(),
                        nd.getNgaySinh(),
                        nd.getGioiTinh()
                    });
                }
            }
            table.setModel(model);
        });

        frame.setVisible(true);
    }
}
