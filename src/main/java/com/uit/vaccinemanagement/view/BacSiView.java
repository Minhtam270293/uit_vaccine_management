package com.uit.vaccinemanagement.view;

import com.uit.vaccinemanagement.dao.TiemChungDAO;
import com.uit.vaccinemanagement.dao.NguoiDungDAO;
import com.uit.vaccinemanagement.model.NguoiDung;

import javax.swing.*;
import com.uit.vaccinemanagement.view.SharedComponents;
import com.uit.vaccinemanagement.view.RoundedButton;

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
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 18, 18);
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
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10))); // khoảng cách dưới khung 1

        // ================= Khung 2: Các nút chức năng =================
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10)); // căn giữa, cách nhau dọc 10px

        // tạo các nút
        RoundedButton btnTiemChung = new RoundedButton("Tiêm chủng");
        RoundedButton btnKhachHang = new RoundedButton("Khách hàng");
        RoundedButton btnChiDinhTiem = new RoundedButton("Chỉ định tiêm");

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

        // ================= Khung 3: Đăng xuất (panel riêng, đưa xuống cuối sidebar) =================
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

        leftPanel.add(Box.createVerticalGlue()); // đẩy logout xuống cuối
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
            JLabel lblVaccine = new JLabel("Vắc xin:");
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



        // -----------------------------------------------------------------------------------------------------------
        // Danh sách tiêm chủng
        btnTiemChung.addActionListener((ActionEvent e) -> {
            tableTitle.setText("QUẢN LÝ TIÊM CHỦNG");
            List<Object[]> data = tiemChungDAO.getByMaBacSi(currentUser.getMaNguoiDung());

            // Cột
            String[] columns = {"Mã TC", "Ngày tiêm", "Mã vắc xin", "Mã bác sĩ", "Mã khách", "Trạng thái", "Ghi chú", "Thao tác"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);

            // Đổ dữ liệu
            for (Object[] row : data) {
                Object[] newRow = new Object[row.length + 1];
                System.arraycopy(row, 0, newRow, 0, row.length);
                newRow[row.length] = "Thao tác"; // placeholder
                model.addRow(newRow);
            }

            JTable newTable = new JTable(model);
            newTable.setFillsViewportHeight(true);
            newTable.setRowHeight(30);
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
            columnModel.getColumn(7).setPreferredWidth(120);  // Thao tác

            // Renderer căn giữa cho mấy cột mã
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            newTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // Mã TC
            newTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Mã vaccine
            newTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // Mã bác sĩ
            newTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Mã khách

            // ===== Custom renderer + editor cho cột thao tác =====
            TableColumn actionColumn = newTable.getColumnModel().getColumn(7);

            actionColumn.setCellRenderer(new TableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value,
                                                            boolean isSelected, boolean hasFocus, int row, int column) {
                    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
                    JButton btnEdit = new JButton("✎");
                    JButton btnDelete = new JButton("🗑");

                    btnEdit.setPreferredSize(new Dimension(50, 25));
                    btnDelete.setPreferredSize(new Dimension(50, 25));

                    panel.add(btnEdit);
                    panel.add(btnDelete);
                    return panel;
                }
            });

            actionColumn.setCellEditor(new DefaultCellEditor(new JCheckBox()) {
                JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
                JButton btnEdit = new JButton("✎");
                JButton btnDelete = new JButton("🗑");

                {
                    btnEdit.setPreferredSize(new Dimension(50, 25));
                    btnDelete.setPreferredSize(new Dimension(50, 25));

                    panel.add(btnEdit);
                    panel.add(btnDelete);

                    // Sự kiện Edit
                    btnEdit.addActionListener(ev -> {
                        int row = newTable.getSelectedRow();
                        if (row != -1) {
                            String maTC = newTable.getValueAt(row, 0).toString();
                            // TODO: Lấy đối tượng TiemChung từ DAO theo maTC và mở form edit
                            JOptionPane.showMessageDialog(frame, "Sửa tiêm chủng: " + maTC);
                        }
                    });

                    // Sự kiện Delete
                    btnDelete.addActionListener(ev -> {
                        int row = newTable.getSelectedRow();
                        if (row != -1) {
                            String maTC = newTable.getValueAt(row, 0).toString();
                            int confirm = JOptionPane.showConfirmDialog(frame, "Xóa tiêm chủng " + maTC + " ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                boolean success = tiemChungDAO.deleteTiemChung(maTC);
                                if (success) {
                                    JOptionPane.showMessageDialog(frame, "Xóa thành công!");
                                    btnTiemChung.doClick();
                                } else {
                                    JOptionPane.showMessageDialog(frame, "Xóa thất bại!");
                                }
                            }
                        }
                    });

                }

                @Override
                public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                    return panel;
                }

                @Override
                public Object getCellEditorValue() {
                    return "";
                }
            });

            
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
            tableTitle.setText("QUẢN LÝ KHÁCH HÀNG");
            List<NguoiDung> khachList = nguoiDungDAO.getAllNguoiDung();

            String[] columns = {"Mã KH", "Họ Tên", "Tên đăng nhập", "Email", "Vai trò", "Ngày tạo", "Ngày sinh", "Giới tính", "Thao tác"};
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
                        nd.getGioiTinh(),
                        "Thao tác" // placeholder
                    });
                }
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
            columnModel.getColumn(8).setPreferredWidth(120);  // Thao tác

            // Renderer căn giữa cho mấy cột mã + vai trò + ngày sinh + giới tính
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            newTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // Mã KH
            newTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Vai trò
            newTable.getColumnModel().getColumn(6).setCellRenderer(centerRenderer); // Ngày sinh
            newTable.getColumnModel().getColumn(7).setCellRenderer(centerRenderer); // Giới tính

            // ===== Custom renderer + editor cho cột thao tác =====
            TableColumn actionColumn = newTable.getColumnModel().getColumn(8);

            actionColumn.setCellRenderer(new TableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value,
                                                            boolean isSelected, boolean hasFocus, int row, int column) {
                    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
                    JButton btnEdit = new JButton("✎");
                    JButton btnDelete = new JButton("🗑");

                    btnEdit.setPreferredSize(new Dimension(50, 25));
                    btnDelete.setPreferredSize(new Dimension(50, 25));

                    panel.add(btnEdit);
                    panel.add(btnDelete);
                    return panel;
                }
            });

            actionColumn.setCellEditor(new DefaultCellEditor(new JCheckBox()) {
                JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
                JButton btnEdit = new JButton("✎");
                JButton btnDelete = new JButton("🗑");

                {
                    btnEdit.setPreferredSize(new Dimension(50, 25));
                    btnDelete.setPreferredSize(new Dimension(50, 25));

                    panel.add(btnEdit);
                    panel.add(btnDelete);

                    // Sự kiện Edit
                    btnEdit.addActionListener(ev -> {
                        int row = newTable.getSelectedRow();
                        if (row != -1) {
                            String maKH = newTable.getValueAt(row, 0).toString();
                            NguoiDung khach = nguoiDungDAO.getAllNguoiDung().stream()
                                    .filter(u -> u.getMaNguoiDung().equals(maKH))
                                    .findFirst().orElse(null);

                            if (khach != null) {
                                // TODO: mở dialog edit khách hàng (giống edit người dùng)
                                JOptionPane.showMessageDialog(frame, "Sửa khách hàng: " + maKH);
                            }
                        }
                    });

                    // Sự kiện Delete
                    btnDelete.addActionListener(ev -> {
                        int row = newTable.getSelectedRow();
                        if (row != -1) {
                            String maKH = newTable.getValueAt(row, 0).toString();
                            int confirm = JOptionPane.showConfirmDialog(frame, "Xóa khách hàng " + maKH + " ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                boolean success = nguoiDungDAO.deleteNguoiDung(maKH);
                                if (success) {
                                    JOptionPane.showMessageDialog(frame, "Xóa thành công!");
                                    btnKhachHang.doClick();
                                } else {
                                    JOptionPane.showMessageDialog(frame, "Xóa thất bại!");
                                }
                            }
                        }
                    });
                }

                @Override
                public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                    return panel;
                }

                @Override
                public Object getCellEditorValue() {
                    return "";
                }
            });

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


