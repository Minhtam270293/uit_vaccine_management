package com.uit.vaccinemanagement.view;

import com.uit.vaccinemanagement.dao.TiemChungDAO;
import com.uit.vaccinemanagement.dao.NguoiDungDAO;
import com.uit.vaccinemanagement.model.NguoiDung;
import com.uit.vaccinemanagement.controller.BacSiController;

import javax.swing.*;
import com.uit.vaccinemanagement.view.SharedComponents;
import com.uit.vaccinemanagement.view.RoundedButton;
import com.uit.vaccinemanagement.view.panels.TiemChungPanel;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class BacSiView {

    private final NguoiDung currentUser;
    private final BacSiController bacSiController;

    public BacSiView(NguoiDung currentUser) {
        this.currentUser = currentUser;
        this.bacSiController = new BacSiController();
    }

    public void showBacSiUI() {
        JFrame frame = new JFrame("Bác sĩ Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1150, 500);

        // Left panel (column 1)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(220, 500));

        // Khung 1: Tên và Vai trò (bo tròn, căn giữa, có icon)
        JPanel infoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(245, 245, 245));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2.setColor(new Color(200, 200, 200));
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 18, 18);
                g2.dispose();
            }
        };
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(18, 0, 18, 0));

        JLabel lblTen = new JLabel("\uD83D\uDC64 Tên: " + currentUser.getHoTen());
        lblTen.setFont(new Font("Roboto", Font.BOLD, 16));
        lblTen.setForeground(new Color(33, 150, 243));
        lblTen.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblVaiTro = new JLabel("\uD83D\uDD12 Vai trò: " + currentUser.getVaiTro());
        lblVaiTro.setFont(new Font("Roboto", Font.BOLD, 16));
        lblVaiTro.setForeground(new Color(244, 67, 54));
        lblVaiTro.setAlignmentX(Component.CENTER_ALIGNMENT);

        infoPanel.add(lblTen);
        infoPanel.add(Box.createVerticalStrut(6));
        infoPanel.add(lblVaiTro);

        leftPanel.add(Box.createVerticalStrut(16));
        leftPanel.add(infoPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Khung 2: Các nút chức năng (dọc, căn giữa, chiều dài đều, cố định vị trí trên)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension buttonSize = new Dimension(200, 40);

        RoundedButton btnTiemChung = new RoundedButton("Tiêm chủng");
        RoundedButton btnKhachHang = new RoundedButton("Khách hàng");
        RoundedButton btnChiDinhTiem = new RoundedButton("Chỉ định tiêm");

        btnTiemChung.setPreferredSize(buttonSize);
        btnKhachHang.setPreferredSize(buttonSize);
        btnChiDinhTiem.setPreferredSize(buttonSize);

        btnTiemChung.setMaximumSize(buttonSize);
        btnKhachHang.setMaximumSize(buttonSize);
        btnChiDinhTiem.setMaximumSize(buttonSize);

        btnTiemChung.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnKhachHang.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnChiDinhTiem.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Không dùng glue, chỉ dùng strut để giữ nút ở trên
        buttonPanel.add(btnTiemChung);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(btnKhachHang);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(btnChiDinhTiem);

        leftPanel.add(buttonPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        // Khung 3: Đăng xuất (panel riêng, đưa xuống cuối sidebar)
        JPanel buttonPanelLogout = new JPanel();
        buttonPanelLogout.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        buttonPanelLogout.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        buttonPanelLogout.setOpaque(false);

        JButton btnDangXuat = SharedComponents.createLogoutButton(e -> {
            frame.dispose();
            com.uit.vaccinemanagement.VaccineManagement.showLogin();
        });
        btnDangXuat.setPreferredSize(buttonSize);
        buttonPanelLogout.add(btnDangXuat);

        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(buttonPanelLogout);

        btnChiDinhTiem.addActionListener((ActionEvent e) -> {
            ChiDinhTiemDialog dialog = new ChiDinhTiemDialog(frame, currentUser, bacSiController);
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

        // -----------------------------------------------------------------------------------------------------------
        // Danh sách tiêm chủng
        btnTiemChung.addActionListener((ActionEvent e) -> {
            frame.setSize(1150, 500);
            TiemChungPanel tiemChungPanel = new TiemChungPanel(frame, bacSiController, currentUser);

            // Replace content in right panel
            rightPanel.removeAll();
            rightPanel.add(tiemChungPanel, BorderLayout.CENTER);
            rightPanel.revalidate();
            rightPanel.repaint();
        });

        // Danh sách khách hàng
        btnKhachHang.addActionListener((ActionEvent e) -> {
            frame.setSize(1250, 500);
            tableTitle.setText("QUẢN LÝ KHÁCH HÀNG");
            List<NguoiDung> khachList = bacSiController.getDefaultKhachHangPage();

            String[] columns = {"Mã KH", "Họ Tên", "Tên đăng nhập", "Email", "Vai trò", "Ngày tạo", "Ngày sinh", "Giới tính"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);

            for (NguoiDung nd : khachList) {
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

            JTable newTable = new JTable(model);
            newTable.setFillsViewportHeight(true);
            newTable.setRowHeight(30);
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

            // Renderer căn giữa cho mấy cột mã + vai trò + ngày sinh + giới tính
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
