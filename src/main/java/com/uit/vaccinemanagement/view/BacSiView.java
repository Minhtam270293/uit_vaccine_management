package com.uit.vaccinemanagement.view;

import com.uit.vaccinemanagement.dao.TiemChungDAO;
import com.uit.vaccinemanagement.dao.NguoiDungDAO;
import com.uit.vaccinemanagement.model.NguoiDung;

import javax.swing.*;
import com.uit.vaccinemanagement.view.SharedComponents;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

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

        // ================= Khung 1: Thông tin User =================
        JPanel userInfoPanel = new JPanel(new GridLayout(2, 1, 0, 2));
        JLabel lblTen = new JLabel("Tên: " + currentUser.getHoTen());
        JLabel lblVaiTro = new JLabel("Vai trò: " + currentUser.getVaiTro());
        // căn trái
        lblTen.setHorizontalAlignment(SwingConstants.LEFT);
        lblVaiTro.setHorizontalAlignment(SwingConstants.LEFT);

        userInfoPanel.add(lblTen);
        userInfoPanel.add(lblVaiTro);
        // bóp chiều cao panel chỉ vừa cho 2 dòng chữ
        userInfoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        leftPanel.add(userInfoPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10))); // khoảng cách dưới khung 1

        // ================= Khung 2: Các nút chức năng =================
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10)); // căn giữa, cách nhau dọc 10px

        // tạo các nút
        JButton btnTiemChung = new JButton("Tiêm chủng");
        JButton btnKhachHang = new JButton("Khách hàng");
        JButton btnChiDinhTiem = new JButton("Chỉ định tiêm");

        // đặt kích thước đồng đều
        Dimension buttonSize = new Dimension(160, 40);
        btnTiemChung.setPreferredSize(buttonSize);
        btnKhachHang.setPreferredSize(buttonSize);
        btnChiDinhTiem.setPreferredSize(buttonSize);

        // thêm nút vào panel
        buttonPanel.add(btnTiemChung);
        buttonPanel.add(btnKhachHang);
        buttonPanel.add(btnChiDinhTiem);

        leftPanel.add(buttonPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 50))); // cách với khung 3

        // ================= Khung 3: Đăng xuất =================
        JPanel buttonPanelLogout = new JPanel();
        buttonPanelLogout.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));

        JButton btnDangXuat = SharedComponents.createLogoutButton(e -> {
            frame.dispose();
            com.uit.vaccinemanagement.VaccineManagement.showLogin();
        });
        btnDangXuat.setPreferredSize(buttonSize);

        buttonPanelLogout.add(btnDangXuat);
        leftPanel.add(buttonPanelLogout);

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
        // ======= 1. Title =======
        JLabel tableTitle = new JLabel("Thông tin", SwingConstants.CENTER);
        tableTitle.setFont(new Font("Arial", Font.BOLD, 18));
        tableTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // padding trên dưới
        // ======= 2. Search bar =======
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        //
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        topPanel.add(new JLabel("Tìm kiếm người dùng"));
        topPanel.add(Box.createHorizontalStrut(5));
        topPanel.add(searchField);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Action buttons
        JButton btnDownload = new JButton("Tải danh sách");
        JButton btnAdd = new JButton("Thêm mới");
        topPanel.add(btnDownload);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(btnAdd);

        // ======= Wrapper chứa Title + Search =======
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(tableTitle, BorderLayout.NORTH);
        headerPanel.add(topPanel, BorderLayout.SOUTH);

        // Add vào NORTH
        rightPanel.add(headerPanel, BorderLayout.NORTH);

        // ======= 3. Table dữ liệu =======
        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // ======= 4. Phân trang =======
        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnPrev = new JButton("<<");
        JButton btnNext = new JButton(">>");
        JLabel lblPageInfo = new JLabel("Trang 1/1");
        JLabel lblTotalRows = new JLabel("Tổng số: 1");

        paginationPanel.add(lblTotalRows);
        paginationPanel.add(btnPrev);
        paginationPanel.add(lblPageInfo);
        paginationPanel.add(btnNext);

        rightPanel.add(paginationPanel, BorderLayout.SOUTH);

        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(220);
        frame.getContentPane().add(splitPane);



        // Danh sách tiêm chủng
        btnTiemChung.addActionListener((ActionEvent e) -> {
        tableTitle.setText("Danh sách tiêm chủng");
        List<Object[]> data = tiemChungDAO.getByMaBacSi(currentUser.getMaNguoiDung());

        // Cột
        String[] columns = {"Mã TC", "Ngày tiêm", "Mã vaccine", "Mã bác sĩ", "Mã khách", "Trạng thái", "Ghi chú"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        // Đổ dữ liệu
        for (Object[] row : data) {
            model.addRow(row);
        }

        JTable newTable = new JTable(model);
        newTable.setFillsViewportHeight(true);
        newTable.setRowHeight(25);
        newTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        newTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Header
        JTableHeader header = newTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setReorderingAllowed(false);
        header.setResizingAllowed(true);

        // Set width từng cột
        TableColumnModel columnModel = newTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(60);   // Mã TC
        columnModel.getColumn(1).setPreferredWidth(100);  // Ngày tiêm
        columnModel.getColumn(2).setPreferredWidth(100);  // Mã vaccine
        columnModel.getColumn(3).setPreferredWidth(100);  // Mã bác sĩ
        columnModel.getColumn(4).setPreferredWidth(100);  // Mã khách
        columnModel.getColumn(5).setPreferredWidth(120);  // Trạng thái
        columnModel.getColumn(6).setPreferredWidth(200);  // Ghi chú

        // Renderer căn giữa cho mấy cột mã
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        newTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // Mã TC
        newTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Mã vaccine
        newTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // Mã bác sĩ
        newTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Mã khách

        // Thay bảng mới vào rightPanel
        JScrollPane newScrollPane = new JScrollPane(newTable);
        rightPanel.removeAll();
        rightPanel.add(headerPanel, BorderLayout.NORTH);
        rightPanel.add(newScrollPane, BorderLayout.CENTER);
        rightPanel.revalidate();
        rightPanel.repaint();
        rightPanel.add(paginationPanel, BorderLayout.SOUTH);
    });



        // Danh sách khách hàng
        btnKhachHang.addActionListener((ActionEvent e) -> {
        tableTitle.setText("Danh sách khách hàng");
        List<NguoiDung> khachList = nguoiDungDAO.getAllNguoiDung();

        String[] columns = {"Mã KH", "Họ Tên", "Tên đăng nhập", "Email", "Vai trò", "Ngày tạo", "Ngày sinh", "Giới tính"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        // Chỉ lấy những người có vai trò = KHÁCH
        for (NguoiDung nd : khachList) {
            if (nd.getVaiTro().name().equalsIgnoreCase("khach")) {
                model.addRow(new Object[]{
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

        JTable newTable = new JTable(model);
        newTable.setFillsViewportHeight(true);
        newTable.setRowHeight(25);
        newTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        newTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Header style
        JTableHeader header = newTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setReorderingAllowed(false);
        header.setResizingAllowed(true);

        // Set độ rộng cột
        TableColumnModel columnModel = newTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(60);   // Mã KH
        columnModel.getColumn(1).setPreferredWidth(120);  // Họ Tên
        columnModel.getColumn(2).setPreferredWidth(120);  // Tên đăng nhập
        columnModel.getColumn(3).setPreferredWidth(200);  // Email
        columnModel.getColumn(4).setPreferredWidth(80);   // Vai trò
        columnModel.getColumn(5).setPreferredWidth(100);  // Ngày tạo
        columnModel.getColumn(6).setPreferredWidth(100);  // Ngày sinh
        columnModel.getColumn(7).setPreferredWidth(80);   // Giới tính

        // Renderer căn giữa cho mấy cột mã + vai trò + giới tính
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        newTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // Mã KH
        newTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Vai trò
        newTable.getColumnModel().getColumn(6).setCellRenderer(centerRenderer); // Ngày sinh
        newTable.getColumnModel().getColumn(7).setCellRenderer(centerRenderer); // Giới tính

        JScrollPane newScrollPane = new JScrollPane(newTable);

        // Replace table trên giao diện
        rightPanel.removeAll();
        rightPanel.add(headerPanel, BorderLayout.NORTH);
        rightPanel.add(newScrollPane, BorderLayout.CENTER);
        rightPanel.revalidate();
        rightPanel.repaint();
        rightPanel.add(paginationPanel, BorderLayout.SOUTH);
    });


        frame.setVisible(true);
    }
}
