package com.uit.vaccinemanagement.view;

import com.uit.vaccinemanagement.dao.NguoiDungDAO;
import com.uit.vaccinemanagement.dao.VaccineDAO;
import com.uit.vaccinemanagement.dao.NhaSanXuatDAO;
import com.uit.vaccinemanagement.model.NguoiDung;

import javax.swing.*;
import com.uit.vaccinemanagement.view.SharedComponents;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class AdminView {

    private final NguoiDung currentUser;
    private final NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
    private final VaccineDAO vaccineDAO = new VaccineDAO();
    private final NhaSanXuatDAO nhaSanXuatDAO = new NhaSanXuatDAO();

    public AdminView(NguoiDung currentUser) {
        this.currentUser = currentUser;
    }

    public void showAdminUI() {
        JFrame frame = new JFrame("Admin Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);

        // Left panel (column 1)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(200, 500));

        leftPanel.add(new JLabel("Tên: " + currentUser.getHoTen()));
        leftPanel.add(new JLabel("Vai trò: " + currentUser.getVaiTro()));

        JButton btnNguoiDung = new JButton("Người Dùng");
        JButton btnVaccine = new JButton("Vaccine");
        JButton btnNhaSanXuat = new JButton("Nhà sản xuất");
        JButton btnDangXuat = SharedComponents.createLogoutButton(e -> {
            frame.dispose();
            com.uit.vaccinemanagement.VaccineManagement.showLogin();
        });

        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(btnNguoiDung);
        leftPanel.add(btnVaccine);
        leftPanel.add(btnNhaSanXuat);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(btnDangXuat);

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
        btnNguoiDung.addActionListener((ActionEvent e) -> {
            tableTitle.setText("Danh sách người dùng");
            List<NguoiDung> userList = nguoiDungDAO.getAllNguoiDung();
            String[] columns = {"ma_nguoi_dung", "ho_ten", "ten_dang_nhap", "email", "vai_tro", "ngay_tao", "ngay_sinh", "gioi_tinh"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);
            for (NguoiDung nd : userList) {
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
            table.setModel(model);
        });

        btnVaccine.addActionListener((ActionEvent e) -> {
            tableTitle.setText("Danh sách vaccine");
            List<Object[]> vaccineList = vaccineDAO.getAllVaccineAsObjectArray();
            String[] columns = {"ma_vaccine", "ten_vaccine", "so_lo", "ngay_sx", "ngay_het_han", "tong_sl", "sl_con_lai", "gia_nhap", "gia_ban", "ma_benh", "ma_nha_sx", "ngay_tao"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);
            for (Object[] row : vaccineList) {
                model.addRow(row);
            }
            table.setModel(model);
        });

        btnNhaSanXuat.addActionListener((ActionEvent e) -> {
            tableTitle.setText("Danh sách nhà sản xuất");
            List<Object[]> nsxList = nhaSanXuatDAO.getAllNhaSanXuatAsObjectArray();
            String[] columns = {"ma_nha_sx", "ten_nha_sx", "quoc_gia", "ngay_tao"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);
            for (Object[] row : nsxList) {
                model.addRow(row);
            }
            table.setModel(model);
        });

        frame.setVisible(true);
    }
}
