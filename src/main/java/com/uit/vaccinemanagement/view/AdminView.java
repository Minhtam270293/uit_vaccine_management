package com.uit.vaccinemanagement.view;

import java.awt.event.ActionEvent;
import java.util.List;
import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import com.uit.vaccinemanagement.dao.NguoiDungDAO;
import com.uit.vaccinemanagement.dao.NhaSanXuatDAO;
import com.uit.vaccinemanagement.dao.VaccineDAO;
import com.uit.vaccinemanagement.model.NguoiDung;

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
        frame.setSize(1600, 500);

        // Left panel (column 1)
        // Side Navigator
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(600, 200));

        // Khung 1: Tên và Vai trò
        JPanel userInfoPanel = new JPanel(new GridLayout(2, 1, 0, 2));
        // 2 hàng, 1 cột, khoảng cách ngang = 0, dọc = 2px
        JLabel lblTen = new JLabel("Tên: " + currentUser.getHoTen());
        JLabel lblVaiTro = new JLabel("Vai trò: " + currentUser.getVaiTro());
        // Căn trái
        lblTen.setHorizontalAlignment(SwingConstants.LEFT);
        lblVaiTro.setHorizontalAlignment(SwingConstants.LEFT);
        // Thêm label vào panel
        userInfoPanel.add(lblTen);
        userInfoPanel.add(lblVaiTro);
        // Bóp chiều cao của panel lại (chỉ vừa cho 2 dòng chữ)
        userInfoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        // Thêm vào leftPanel
        leftPanel.add(userInfoPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Khung 2: Các nút bấm
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));  // Căn giữa với khoảng cách dọc là 10px
        // Tạo các nút bấm
        JButton btnNguoiDung = new JButton("Người Dùng");
        JButton btnVaccine = new JButton("Vaccine");
        JButton btnNhaSanXuat = new JButton("Nhà sản xuất");
        // Đặt kích thước đồng đều cho các nút
        Dimension buttonSize = new Dimension(200, 40);
        btnNguoiDung.setPreferredSize(buttonSize);
        btnVaccine.setPreferredSize(buttonSize);
        btnNhaSanXuat.setPreferredSize(buttonSize);
        // Thêm các nút vào buttonPanel
        buttonPanel.add(btnNguoiDung);
        buttonPanel.add(btnVaccine);
        buttonPanel.add(btnNhaSanXuat);
        // Thêm buttonPanel vào leftPanel
        leftPanel.add(buttonPanel);
        // Thêm khoảng cách 20px giữa khung 2 và khung 3
        leftPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        // Khung 3: Nút Đăng Xuất
        JPanel buttonPanelLogout = new JPanel();
        buttonPanelLogout.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        // Tạo các nút bấm
        JButton btnDangXuat = SharedComponents.createLogoutButton(e -> {
            frame.dispose();
            com.uit.vaccinemanagement.VaccineManagement.showLogin();
        });
        btnDangXuat.setPreferredSize(buttonSize);
        buttonPanelLogout.add(btnDangXuat);
        // Thêm Khung 3 vào leftPanel
        leftPanel.add(buttonPanelLogout);

        // Right panel (column 2)
        JPanel rightPanel = new JPanel(new BorderLayout());
        // head right pannel
        // 1. Title
        JLabel tableTitle = new JLabel("Thông tin", SwingConstants.CENTER);
        tableTitle.setFont(new Font("Arial", Font.BOLD, 18));
        tableTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // padding trên dưới
        //  2. Search bar
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        // search field
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        topPanel.add(new JLabel("Tìm kiếm người dùng"));
        topPanel.add(Box.createHorizontalStrut(5));
        topPanel.add(searchField);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        // Buttons download + search
        JButton btnDownload = new JButton("Tìm kiếm");
        JButton btnAdd = new JButton("Download");
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

        // Người dùng table
        btnNguoiDung.addActionListener((ActionEvent e) -> {
            tableTitle.setText("Danh sách người dùng");
            tableTitle.setFont(new Font("Arial", Font.BOLD, 18));
            List<NguoiDung> userList = nguoiDungDAO.getAllNguoiDung();

            String[] columns = {"Mã ND", "Họ Tên", "Tên đăng nhập", "Email", "Vai trò", "Ngày tạo", "Ngày sinh", "Giới tính", "Thao tác"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);

            // Đổ dữ liệu vào model
            for (NguoiDung nd : userList) {
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

            JTable newTable = new JTable(model);
            newTable.setRowHeight(30);

            // ===== Custom renderer + editor cho cột thao tác =====
            TableColumn actionColumn = newTable.getColumnModel().getColumn(8);

            actionColumn.setCellRenderer(new TableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value,
                        boolean isSelected, boolean hasFocus, int row, int column) {

                    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
                    JButton btnEdit = new JButton("✎");   // icon edit
                    JButton btnDelete = new JButton("🗑"); // icon delete

                    btnEdit.setPreferredSize(new Dimension(50, 25));
                    btnDelete.setPreferredSize(new Dimension(50, 25));

                    panel.add(btnEdit);
                    panel.add(btnDelete);

                    return panel;
                }
            });

            // Editor (để bắt sự kiện click)
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
                    btnEdit.addActionListener(e -> {
                        int row = newTable.getSelectedRow();
                        if (row != -1) {
                            String maNguoiDung = newTable.getValueAt(row, 0).toString();
                            NguoiDung nd = nguoiDungDAO.getAllNguoiDung().stream()
                                    .filter(u -> u.getMaNguoiDung().equals(maNguoiDung))
                                    .findFirst().orElse(null);
                            // JOptionPane.showMessageDialog(frame, "Edit user: " + maNguoiDung);

                            // TODO: mở form edit user
                            JDialog editDialog = new JDialog(frame, "Edit User", true);
                            editDialog.setSize(400, 300);
                            editDialog.setLayout(new GridLayout(0, 2, 5, 5));

                            JTextField tfHoTen = new JTextField(nd.getHoTen());
                            JTextField tfEmail = new JTextField(nd.getEmail());
                            JTextField tfTenDangNhap = new JTextField(nd.getTenDangNhap());
                            JTextField tfNgaySinh = new JTextField(nd.getNgaySinh() != null ? nd.getNgaySinh().toString() : "");
                            JComboBox<String> cbVaiTro = new JComboBox<>(new String[]{"ADMIN", "BACSI", "KHACH"});
                            cbVaiTro.setSelectedItem(nd.getVaiTro().name());

                            editDialog.add(new JLabel("Họ tên:"));
                            editDialog.add(tfHoTen);
                            editDialog.add(new JLabel("Email:"));
                            editDialog.add(tfEmail);
                            editDialog.add(new JLabel("Tên đăng nhập:"));
                            editDialog.add(tfTenDangNhap);
                            editDialog.add(new JLabel("Ngày sinh:"));
                            editDialog.add(tfNgaySinh);
                            editDialog.add(new JLabel("Vai trò:"));
                            editDialog.add(cbVaiTro);

                            JButton btnSave = new JButton("Lưu");
                            JButton btnCancel = new JButton("Hủy");
                            editDialog.add(btnSave);
                            editDialog.add(btnCancel);

                            btnSave.addActionListener(ev -> {
                                nd.setHoTen(tfHoTen.getText().trim());
                                nd.setEmail(tfEmail.getText().trim());
                                nd.setTenDangNhap(tfTenDangNhap.getText().trim());
                                nd.setNgaySinh(tfNgaySinh.getText().isEmpty() ? null : java.sql.Date.valueOf(tfNgaySinh.getText().trim()));
                                nd.setVaiTro(com.uit.vaccinemanagement.util.Role.valueOf(cbVaiTro.getSelectedItem().toString()));

                                boolean success = nguoiDungDAO.updateNguoiDung(nd);
                                if (success) {
                                    JOptionPane.showMessageDialog(editDialog, "Cập nhật thành công!");
                                    editDialog.dispose();
                                    // Refresh table by reusing the button's action
                                    btnNguoiDung.doClick();
                                } else {
                                    JOptionPane.showMessageDialog(editDialog, "Cập nhật thất bại!");
                                }
                            });
                            btnCancel.addActionListener(ev -> editDialog.dispose());
                            editDialog.setLocationRelativeTo(frame);
                            editDialog.setVisible(true);
                        }
                    });

                    // Sự kiện Delete
                    btnDelete.addActionListener(e -> {
                        int row = newTable.getSelectedRow();
                        if (row != -1) {
                            String maNguoiDung = newTable.getValueAt(row, 0).toString();
                            int confirm = JOptionPane.showConfirmDialog(frame, "Xóa user " + maNguoiDung + " ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                boolean success = nguoiDungDAO.deleteNguoiDung(maNguoiDung);
                                if (success) {
                                    JOptionPane.showMessageDialog(frame, "Xóa thành công!");
                                    btnNguoiDung.doClick();
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

            JTableHeader header = newTable.getTableHeader();
            header.setFont(new Font("Arial", Font.BOLD, 14));
            header.setReorderingAllowed(false);
            header.setResizingAllowed(true);

            TableColumnModel columnModel = newTable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(50);
            columnModel.getColumn(1).setPreferredWidth(100);
            columnModel.getColumn(2).setPreferredWidth(120);
            columnModel.getColumn(3).setPreferredWidth(200);
            columnModel.getColumn(4).setPreferredWidth(80);
            columnModel.getColumn(5).setPreferredWidth(100);
            columnModel.getColumn(6).setPreferredWidth(100);
            columnModel.getColumn(7).setPreferredWidth(80);

            // Renderer căn giữa cho cột 0, 1, 4
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            newTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
            newTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
            newTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
            newTable.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
            newTable.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);

            JScrollPane newScrollPane = new JScrollPane(newTable);
            rightPanel.removeAll();
            rightPanel.add(newScrollPane, BorderLayout.CENTER);
            rightPanel.revalidate();
            rightPanel.repaint();
            rightPanel.add(headerPanel, BorderLayout.NORTH);
            rightPanel.add(paginationPanel, BorderLayout.SOUTH);
        });

        // Vaccine
        btnVaccine.addActionListener((ActionEvent e) -> {
            tableTitle.setText("Danh sách vaccine");
            List<Object[]> vaccineList = vaccineDAO.getAllVaccineAsObjectArray();
            String[] columns = {"Mã Vaccine", "Tên Vaccine", "Số lô", "Ngày SX", "Ngày hết hạn",
                "Tổng SL", "SL còn lại", "Giá nhập", "Giá bán",
                "Mã bệnh", "Mã NSX", "Ngày tạo"};

            DefaultTableModel model = new DefaultTableModel(columns, 0);
            for (Object[] row : vaccineList) {
                model.addRow(row);
            }

            JTable newTable = new JTable(model);

            // Tùy chỉnh bảng
            newTable.setFillsViewportHeight(true);
            newTable.setRowHeight(25);
            newTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            newTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            JTableHeader header = newTable.getTableHeader();
            header.setFont(new Font("Arial", Font.BOLD, 14));
            header.setReorderingAllowed(false);

            TableColumnModel columnModel = newTable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(100);  // Mã Vaccine
            columnModel.getColumn(1).setPreferredWidth(150);  // Tên Vaccine
            columnModel.getColumn(2).setPreferredWidth(100);  // Số lô
            columnModel.getColumn(3).setPreferredWidth(100);  // Ngày SX
            columnModel.getColumn(4).setPreferredWidth(100);  // Ngày hết hạn
            columnModel.getColumn(5).setPreferredWidth(80);   // Tổng SL
            columnModel.getColumn(6).setPreferredWidth(80);   // SL còn lại
            columnModel.getColumn(7).setPreferredWidth(120);  // Giá nhập
            columnModel.getColumn(8).setPreferredWidth(120);  // Giá bán
            columnModel.getColumn(9).setPreferredWidth(80);   // Mã bệnh
            columnModel.getColumn(10).setPreferredWidth(80);  // Mã NSX
            columnModel.getColumn(11).setPreferredWidth(100); // Ngày tạo

            // Renderer căn giữa
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

            // Căn giữa các cột số
            int[] centerCols = {0, 2, 5, 6, 7, 8, 9, 10};
            for (int col : centerCols) {
                newTable.getColumnModel().getColumn(col).setCellRenderer(centerRenderer);
            }

            // Đưa vào ScrollPane
            JScrollPane newScrollPane = new JScrollPane(newTable);
            rightPanel.removeAll();
            rightPanel.add(headerPanel, BorderLayout.NORTH);
            rightPanel.add(newScrollPane, BorderLayout.CENTER);
            rightPanel.revalidate();
            rightPanel.repaint();
            rightPanel.add(paginationPanel, BorderLayout.SOUTH);
        });

        // Nhà Sản Xuất table
        btnNhaSanXuat.addActionListener((ActionEvent e) -> {
            tableTitle.setText("Danh sách nhà sản xuất");
            List<Object[]> nsxList = nhaSanXuatDAO.getAllNhaSanXuatAsObjectArray();
            String[] columns = {"Mã NSX", "Tên nhà sản xuất", "Quốc gia", "Ngày tạo"};

            DefaultTableModel model = new DefaultTableModel(columns, 0);
            for (Object[] row : nsxList) {
                model.addRow(row);
            }

            JTable newTable = new JTable(model);

            // Tuỳ chỉnh chung
            newTable.setFillsViewportHeight(true);
            newTable.setRowHeight(25);
            newTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            newTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            JTableHeader header = newTable.getTableHeader();
            header.setFont(new Font("Arial", Font.BOLD, 14));
            header.setReorderingAllowed(false);

            // Set width cột
            TableColumnModel columnModel = newTable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(80);   // Mã NSX
            columnModel.getColumn(1).setPreferredWidth(200);  // Tên NSX
            columnModel.getColumn(2).setPreferredWidth(120);  // Quốc gia
            columnModel.getColumn(3).setPreferredWidth(100);  // Ngày tạo

            // Renderer căn giữa
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

            // Căn giữa các cột: Mã NSX, Quốc gia
            int[] centerCols = {0, 2};
            for (int col : centerCols) {
                newTable.getColumnModel().getColumn(col).setCellRenderer(centerRenderer);
            }

            // Đưa vào ScrollPane
            JScrollPane newScrollPane = new JScrollPane(newTable);
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
