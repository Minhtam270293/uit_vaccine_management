package com.uit.vaccinemanagement.view;

import com.uit.vaccinemanagement.model.NguoiDung;
import com.uit.vaccinemanagement.dao.TiemChungDAO;
import com.uit.vaccinemanagement.dao.ThongTinMuaDAO;

import javax.swing.*;
import com.uit.vaccinemanagement.view.SharedComponents;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class KhachView {

    private final NguoiDung currentUser;
    private final TiemChungDAO tiemChungDAO = new TiemChungDAO();
    private final ThongTinMuaDAO thongTinMuaDAO = new ThongTinMuaDAO();

    public KhachView(NguoiDung currentUser) {
        this.currentUser = currentUser;
    }

    public void showKhachUI() {
        JFrame frame = new JFrame("Khách Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);

        // Left panel (column 1)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(200, 500));

        leftPanel.add(new JLabel("Tên: " + currentUser.getHoTen()));
        leftPanel.add(new JLabel("Vai trò: " + currentUser.getVaiTro()));

        JButton btnLichTiem = new JButton("Xem lịch tiêm");
        JButton btnLichSuMua = new JButton("Xem lịch sử mua");
        JButton btnDangXuat = SharedComponents.createLogoutButton(e -> {
            frame.dispose();
            com.uit.vaccinemanagement.VaccineManagement.showLogin();
        });

        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(btnLichTiem);
        leftPanel.add(btnLichSuMua);
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
    // Logout button event is now handled by SharedComponents
        btnLichTiem.addActionListener((ActionEvent e) -> {
            tableTitle.setText("Lịch sử tiêm");
            List<Object[]> data = tiemChungDAO.getByMaKhach(currentUser.getMaNguoiDung());
            String[] columns = {"ma_tiem_chung", "ngay_tiem", "ma_vaccine", "ma_bac_si", "trang_thai_tiem", "ghi_chu"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);
            for (Object[] row : data) {
                model.addRow(row);
            }
            table.setModel(model);
        });

        btnLichSuMua.addActionListener((ActionEvent e) -> {
            tableTitle.setText("Lịch sử mua");
            List<Object[]> data = thongTinMuaDAO.getByMaKhach(currentUser.getMaNguoiDung());
            String[] columns = {"ma_giao_dich", "ma_vaccine", "ma_khach", "so_luong_mua", "tong_tien", "ngay_giao_dich"};            DefaultTableModel model = new DefaultTableModel(columns, 0);
            for (Object[] row : data) {
                model.addRow(row);
            }
            table.setModel(model);
        });

        frame.setVisible(true);
    }
}
