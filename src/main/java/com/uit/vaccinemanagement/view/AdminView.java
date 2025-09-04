package com.uit.vaccinemanagement.view;

import com.uit.vaccinemanagement.controller.AdminController;
import java.awt.event.ActionEvent;
import java.util.List;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
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
import com.uit.vaccinemanagement.model.Vaccine;
import com.uit.vaccinemanagement.view.panels.*;

import javax.swing.*;
import java.awt.*;

public class AdminView {

    private static final String NGUOIDUNG_PANEL = "NGUOIDUNG_PANEL";
    private static final String VACCINE_PANEL = "VACCINE_PANEL";
    private static final String NHASX_PANEL = "NHASX_PANEL";

    private final NguoiDung currentUser;
    private final AdminController adminController;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public AdminView(NguoiDung currentUser) {
        this.currentUser = currentUser;
        this.adminController = new AdminController();
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

        // Khung 1: Tên và Vai trò (bo tròn, căn giữa, có icon)
        JPanel infoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(245, 245, 245)); // nền xám nhạt
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

        // Sử dụng emoji Unicode thay cho icon hệ thống để tránh lỗi
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

        // Khung 2: Các nút bấm
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));  // Căn giữa với khoảng cách dọc là 10px
        // Đảm bảo bạn dùng RoundedButton cho 3 nút sidebar như sau:
        RoundedButton btnNguoiDung = new RoundedButton("Người dùng");
        RoundedButton btnVaccine = new RoundedButton("Vắc xin");
        RoundedButton btnNhaSanXuat = new RoundedButton("Nhà sản xuất");
        // Đặt kích thước đồng đều cho các nút
        Dimension buttonSize = new Dimension(200, 40);
        btnNguoiDung.setPreferredSize(buttonSize);
        btnVaccine.setPreferredSize(buttonSize);
        btnNhaSanXuat.setPreferredSize(buttonSize);

        // Thêm các nút vào buttonPanel
        buttonPanel.add(btnNguoiDung);
        buttonPanel.add(btnVaccine);
        buttonPanel.add(btnNhaSanXuat);
        btnNguoiDung.addActionListener(e -> cardLayout.show(cardPanel, NGUOIDUNG_PANEL));
        btnVaccine.addActionListener(e -> cardLayout.show(cardPanel, VACCINE_PANEL));
        btnNhaSanXuat.addActionListener(e -> cardLayout.show(cardPanel, NHASX_PANEL));
        // Thêm buttonPanel vào leftPanel
        leftPanel.add(buttonPanel);
        // Thêm khoảng cách 20px giữa khung 2 và khung 3
        leftPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        // Khung 3: Nút Đăng Xuất (panel riêng, đưa xuống cuối sidebar)
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

        // Create and setup CardLayout for the right panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Add panels
        cardPanel.add(new NguoiDungPanel(frame, adminController), NGUOIDUNG_PANEL);
        cardPanel.add(new VaccinePanel(frame, adminController), VACCINE_PANEL);
        cardPanel.add(new NhaSanXuatPanel(frame, adminController), NHASX_PANEL);

        // Create split pane and add panels
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(220);
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(cardPanel);

        frame.getContentPane().add(splitPane);

        // -------------------------------------------------------------------------------------------------------------------------
        // // Người dùng table
        // btnNguoiDung.addActionListener(e -> cardLayout.show(cardPanel, NGUOIDUNG_PANEL));
        //     String[] columns = {"Mã ND", "Họ tên", "Tên đăng nhập", "Email", "Vai trò", "Ngày tạo", "Ngày sinh", "Giới tính", "Thao tác"};
        //     DefaultTableModel model = new DefaultTableModel(columns, 0);
        //     // Đổ dữ liệu vào model
        //     for (NguoiDung nd : userList) {
        //         model.addRow(new Object[]{
        //             nd.getMaNguoiDung(),
        //             nd.getHoTen(),
        //             nd.getTenDangNhap(),
        //             nd.getEmail(),
        //             nd.getVaiTro(),
        //             nd.getNgayTao(),
        //             nd.getNgaySinh(),
        //             nd.getGioiTinh(),
        //             "Thao tác" // placeholder
        //         });
        //     }
        //     JTable newTable = new JTable(model);
        //     newTable.setRowHeight(30);
        //     // ===== Custom renderer + editor cho cột thao tác =====
        //     TableColumn actionColumn = newTable.getColumnModel().getColumn(8);
        //     actionColumn.setCellRenderer(new TableCellRenderer() {
        //         @Override
        //         public Component getTableCellRendererComponent(JTable table, Object value,
        //                 boolean isSelected, boolean hasFocus, int row, int column) {
        //             JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        //             JButton btnEdit = new JButton("✎");
        //             JButton btnDelete = new JButton("🗑");
        //             btnEdit.setPreferredSize(new Dimension(50, 25));
        //             btnDelete.setPreferredSize(new Dimension(50, 25));
        //             panel.add(btnEdit);
        //             panel.add(btnDelete);
        //             return panel;
        //         }
        //     });
        //     // Editor (để bắt sự kiện click)
        //     actionColumn.setCellEditor(new DefaultCellEditor(new JCheckBox()) {
        //         JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        //         JButton btnEdit = new JButton("✎");
        //         JButton btnDelete = new JButton("🗑");
        //         {
        //             btnEdit.setPreferredSize(new Dimension(50, 25));
        //             btnDelete.setPreferredSize(new Dimension(50, 25));
        //             panel.add(btnEdit);
        //             panel.add(btnDelete);
        //             // Sự kiện Edit
        //             btnEdit.addActionListener(e -> {
        //                 int row = newTable.getSelectedRow();
        //                 if (row != -1) {
        //                     String maNguoiDung = newTable.getValueAt(row, 0).toString();
        //                     NguoiDung nd = adminController.getAllNguoiDung().stream()
        //                             .filter(u -> u.getMaNguoiDung().equals(maNguoiDung))
        //                             .findFirst().orElse(null);
        //                     // TODO: mở form edit user
        //                     JDialog editDialog = new JDialog(frame, "Sửa người dùng", true);
        //                     editDialog.setSize(400, 300);
        //                     editDialog.setLayout(new GridLayout(0, 2, 5, 5));
        //                     JTextField tfHoTen = new JTextField(nd.getHoTen());
        //                     JTextField tfEmail = new JTextField(nd.getEmail());
        //                     JTextField tfTenDangNhap = new JTextField(nd.getTenDangNhap());
        //                     JTextField tfNgaySinh = new JTextField(nd.getNgaySinh() != null ? nd.getNgaySinh().toString() : "");
        //                     JComboBox<String> cbVaiTro = new JComboBox<>(new String[]{"ADMIN", "BACSI", "KHACH"});
        //                     cbVaiTro.setSelectedItem(nd.getVaiTro().name());
        //                     editDialog.add(new JLabel("Họ tên:"));
        //                     editDialog.add(tfHoTen);
        //                     editDialog.add(new JLabel("Email:"));
        //                     editDialog.add(tfEmail);
        //                     editDialog.add(new JLabel("Tên đăng nhập:"));
        //                     editDialog.add(tfTenDangNhap);
        //                     editDialog.add(new JLabel("Ngày sinh:"));
        //                     editDialog.add(tfNgaySinh);
        //                     editDialog.add(new JLabel("Vai trò:"));
        //                     editDialog.add(cbVaiTro);
        //                     JButton btnSave = new JButton("Lưu");
        //                     JButton btnCancel = new JButton("Hủy");
        //                     editDialog.add(btnSave);
        //                     editDialog.add(btnCancel);
        //                     btnSave.addActionListener(ev -> {
        //                         nd.setHoTen(tfHoTen.getText().trim());
        //                         nd.setEmail(tfEmail.getText().trim());
        //                         nd.setTenDangNhap(tfTenDangNhap.getText().trim());
        //                         nd.setNgaySinh(tfNgaySinh.getText().isEmpty() ? null : java.sql.Date.valueOf(tfNgaySinh.getText().trim()));
        //                         nd.setVaiTro(com.uit.vaccinemanagement.util.Role.valueOf(cbVaiTro.getSelectedItem().toString()));
        //                         boolean success = adminController.updateNguoiDung(nd);
        //                         if (success) {
        //                             JOptionPane.showMessageDialog(editDialog, "Cập nhật thành công!");
        //                             editDialog.dispose();
        //                             // Refresh lại table
        //                             btnNguoiDung.doClick();
        //                         } else {
        //                             JOptionPane.showMessageDialog(editDialog, "Cập nhật thất bại!");
        //                         }
        //                     });
        //                     btnCancel.addActionListener(ev -> editDialog.dispose());
        //                     editDialog.setLocationRelativeTo(frame);
        //                     editDialog.setVisible(true);
        //                 }
        //             });
        //             // Sự kiện Delete
        //             btnDelete.addActionListener(e -> {
        //                 int row = newTable.getSelectedRow();
        //                 if (row != -1) {
        //                     String maNguoiDung = newTable.getValueAt(row, 0).toString();
        //                     int confirm = JOptionPane.showConfirmDialog(frame, "Xóa người dùng " + maNguoiDung + " ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        //                     if (confirm == JOptionPane.YES_OPTION) {
        //                         boolean success = adminController.deleteNguoiDung(maNguoiDung);
        //                         if (success) {
        //                             JOptionPane.showMessageDialog(frame, "Xóa thành công!");
        //                             btnNguoiDung.doClick();
        //                         } else {
        //                             JOptionPane.showMessageDialog(frame, "Xóa thất bại!");
        //                         }
        //                     }
        //                 }
        //             });
        //         }
        //         @Override
        //         public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        //             return panel;
        //         }
        //         @Override
        //         public Object getCellEditorValue() {
        //             return "";
        //         }
        //     });
        //     JTableHeader header = newTable.getTableHeader();
        //     header.setFont(new Font("Arial", Font.BOLD, 14));
        //     header.setReorderingAllowed(false);
        //     header.setResizingAllowed(true);
        //     TableColumnModel columnModel = newTable.getColumnModel();
        //     columnModel.getColumn(0).setPreferredWidth(50);
        //     columnModel.getColumn(1).setPreferredWidth(100);
        //     columnModel.getColumn(2).setPreferredWidth(120);
        //     columnModel.getColumn(3).setPreferredWidth(200);
        //     columnModel.getColumn(4).setPreferredWidth(80);
        //     columnModel.getColumn(5).setPreferredWidth(100);
        //     columnModel.getColumn(6).setPreferredWidth(100);
        //     columnModel.getColumn(7).setPreferredWidth(80);
        //     // Renderer căn giữa cho cột 0, 1, 4
        //     DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        //     centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        //     newTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        //     newTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        //     newTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        //     newTable.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        //     newTable.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
        //     JScrollPane newScrollPane = new JScrollPane(newTable);
        //     rightPanel.removeAll();
        //     rightPanel.add(newScrollPane, BorderLayout.CENTER);
        //     rightPanel.revalidate();
        //     rightPanel.repaint();
        //     rightPanel.add(headerPanel, BorderLayout.NORTH);
        //     rightPanel.add(paginationPanel, BorderLayout.SOUTH);
        //     // Update pagination info after table update
        //     updatePaginationInfo.run();
        // });
        //     // Vaccine
        //     btnVaccine.addActionListener((ActionEvent e) -> {
        //         tableTitle.setText("QUẢN LÝ VẮC XIN");
        //         List<Object[]> vaccineList = adminController.getAllVaccineAsObjectArray();
        //         String[] columns = {"Mã vắc xin", "Tên vắc xin", "Số lô", "Ngày SX", "Ngày hết hạn",
        //             "Tổng SL", "SL còn lại", "Giá nhập", "Giá bán",
        //             "Mã bệnh", "Mã NSX", "Ngày tạo", "Thao tác"}; // thêm cột Thao tác
        //         DefaultTableModel model = new DefaultTableModel(columns, 0);
        //         // Đổ dữ liệu vào model
        //         for (Object[] row : vaccineList) {
        //             Object[] newRow = new Object[row.length + 1];
        //             System.arraycopy(row, 0, newRow, 0, row.length);
        //             newRow[row.length] = "Thao tác"; // placeholder
        //             model.addRow(newRow);
        //         }
        //         JTable newTable = new JTable(model);
        //         // Tùy chỉnh bảng
        //         newTable.setFillsViewportHeight(true);
        //         newTable.setRowHeight(30);
        //         newTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //         newTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //         JTableHeader header = newTable.getTableHeader();
        //         header.setFont(new Font("Arial", Font.BOLD, 14));
        //         header.setReorderingAllowed(false);
        //         TableColumnModel columnModel = newTable.getColumnModel();
        //         columnModel.getColumn(0).setPreferredWidth(100);  // Mã Vaccine
        //         columnModel.getColumn(1).setPreferredWidth(150);  // Tên Vaccine
        //         columnModel.getColumn(2).setPreferredWidth(100);  // Số lô
        //         columnModel.getColumn(3).setPreferredWidth(100);  // Ngày SX
        //         columnModel.getColumn(4).setPreferredWidth(100);  // Ngày hết hạn
        //         columnModel.getColumn(5).setPreferredWidth(80);   // Tổng SL
        //         columnModel.getColumn(6).setPreferredWidth(80);   // SL còn lại
        //         columnModel.getColumn(7).setPreferredWidth(120);  // Giá nhập
        //         columnModel.getColumn(8).setPreferredWidth(120);  // Giá bán
        //         columnModel.getColumn(9).setPreferredWidth(80);   // Mã bệnh
        //         columnModel.getColumn(10).setPreferredWidth(80);  // Mã NSX
        //         columnModel.getColumn(11).setPreferredWidth(100); // Ngày tạo
        //         columnModel.getColumn(12).setPreferredWidth(120); // Thao tác
        //         // Renderer căn giữa
        //         DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        //         centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        //         int[] centerCols = {0, 2, 5, 6, 7, 8, 9, 10};
        //         for (int col : centerCols) {
        //             newTable.getColumnModel().getColumn(col).setCellRenderer(centerRenderer);
        //         }
        //         // ===== Custom renderer + editor cho cột thao tác =====
        //         TableColumn actionColumn = newTable.getColumnModel().getColumn(12);
        //         actionColumn.setCellRenderer(new TableCellRenderer() {
        //             @Override
        //             public Component getTableCellRendererComponent(JTable table, Object value,
        //                     boolean isSelected, boolean hasFocus, int row, int column) {
        //                 JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        //                 JButton btnEdit = new JButton("✎");
        //                 JButton btnDelete = new JButton("🗑");
        //                 btnEdit.setPreferredSize(new Dimension(50, 25));
        //                 btnDelete.setPreferredSize(new Dimension(50, 25));
        //                 panel.add(btnEdit);
        //                 panel.add(btnDelete);
        //                 return panel;
        //             }
        //         });
        //         // Editor (bắt sự kiện click)
        //         actionColumn.setCellEditor(new DefaultCellEditor(new JCheckBox()) {
        //             JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        //             JButton btnEdit = new JButton("✎");
        //             JButton btnDelete = new JButton("🗑");
        //             {
        //                 btnEdit.setPreferredSize(new Dimension(50, 25));
        //                 btnDelete.setPreferredSize(new Dimension(50, 25));
        //                 panel.add(btnEdit);
        //                 panel.add(btnDelete);
        //                 // Sự kiện Edit
        //                 btnEdit.addActionListener(ev -> {
        //                     int row = newTable.getSelectedRow();
        //                     if (row != -1) {
        //                         String maVaccine = newTable.getValueAt(row, 0).toString();
        //                         Vaccine vc = adminController.getAllVaccine().stream()
        //                                 .filter(v -> v.getMaVaccine().equals(maVaccine))
        //                                 .findFirst().orElse(null);
        //                         if (vc == null) {
        //                             JOptionPane.showMessageDialog(frame, "Không tìm thấy vắc xin!");
        //                             return;
        //                         }
        //                         // Gọi module mới VaccineEditDialog
        //                         VaccineEditDialog editDialog = new VaccineEditDialog(frame, vc, adminController, btnVaccine);
        //                         editDialog.setVisible(true);
        //                     }
        //                 });
        //                 // Sự kiện Delete
        //                 btnDelete.addActionListener(ev -> {
        //                     int row = newTable.getSelectedRow();
        //                     if (row != -1) {
        //                         String maVaccine = newTable.getValueAt(row, 0).toString();
        //                         int confirm = JOptionPane.showConfirmDialog(frame, "Xóa vắc xin " + maVaccine + " ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        //                         if (confirm == JOptionPane.YES_OPTION) {
        //                             try {
        //                                 boolean success = adminController.deleteVaccine(maVaccine);
        //                                 if (success) {
        //                                     JOptionPane.showMessageDialog(frame, "Xóa thành công!");
        //                                     btnVaccine.doClick();
        //                                 } else {
        //                                     JOptionPane.showMessageDialog(frame, "Xóa thất bại!");
        //                                 }
        //                             } catch (IllegalArgumentException ex) {
        //                                 JOptionPane.showMessageDialog(frame, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        //                             }
        //                         }
        //                     }
        //                 });
        //             }
        //             @Override
        //             public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        //                 return panel;
        //             }
        //             @Override
        //             public Object getCellEditorValue() {
        //                 return "";
        //             }
        //         });
        //         // ======= Sự kiện click vào Tên Vaccine để hiển thị ảnh =======
        //         newTable.addMouseListener(new MouseAdapter() {
        //             @Override
        //             public void mouseClicked(MouseEvent e) {
        //                 int row = newTable.rowAtPoint(e.getPoint());
        //                 int col = newTable.columnAtPoint(e.getPoint());
        //                 if (row != -1 && col == 1) { // cột Tên Vaccine
        //                     String maVaccine = newTable.getValueAt(row, 0).toString();
        //                     Vaccine vc = adminController.getAllVaccine().stream()
        //                             .filter(v -> v.getMaVaccine().equals(maVaccine))
        //                             .findFirst().orElse(null);
        //                     if (vc != null && vc.getHinhAnhUrl() != null && !vc.getHinhAnhUrl().isEmpty()) {
        //                         try {
        //                             URL url = new URL(vc.getHinhAnhUrl());
        //                             ImageIcon icon = new ImageIcon(url);
        //                             Image img = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        //                             JLabel lblImg = new JLabel(new ImageIcon(img));
        //                             JOptionPane.showMessageDialog(frame, lblImg, vc.getTenVaccine(), JOptionPane.PLAIN_MESSAGE);
        //                         } catch (Exception ex) {
        //                             JOptionPane.showMessageDialog(frame, "Không tải được hình ảnh!");
        //                         }
        //                     } else {
        //                         JOptionPane.showMessageDialog(frame, "Không có hình ảnh cho vắc xin này!");
        //                     }
        //                 }
        //             }
        //         });
        //         // Đưa vào ScrollPane
        //         JScrollPane newScrollPane = new JScrollPane(newTable);
        //         rightPanel.removeAll();
        //         rightPanel.add(headerPanel, BorderLayout.NORTH);
        //         rightPanel.add(newScrollPane, BorderLayout.CENTER);
        //         rightPanel.revalidate();
        //         rightPanel.repaint();
        //         rightPanel.add(paginationPanel, BorderLayout.SOUTH);
        //     });
        //     // Nhà Sản Xuất table
        //     btnNhaSanXuat.addActionListener((ActionEvent e) -> {
        //         tableTitle.setText("QUẢN LÝ NHÀ SẢN XUẤT");
        //         List<Object[]> nsxList = adminController.getAllNhaSanXuatAsObjectArray();
        //         String[] columns = {"Mã NSX", "Tên nhà sản xuất", "Quốc gia", "Ngày tạo", "Thao tác"}; // thêm cột Thao tác
        //         DefaultTableModel model = new DefaultTableModel(columns, 0);
        //         for (Object[] row : nsxList) {
        //             Object[] newRow = new Object[row.length + 1];
        //             System.arraycopy(row, 0, newRow, 0, row.length);
        //             newRow[row.length] = "Thao tác"; // placeholder
        //             model.addRow(newRow);
        //         }
        //         JTable newTable = new JTable(model);
        //         // Tuỳ chỉnh chung
        //         newTable.setFillsViewportHeight(true);
        //         newTable.setRowHeight(30);
        //         newTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //         newTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //         JTableHeader header = newTable.getTableHeader();
        //         header.setFont(new Font("Arial", Font.BOLD, 14));
        //         header.setReorderingAllowed(false);
        //         // Set width cột
        //         TableColumnModel columnModel = newTable.getColumnModel();
        //         columnModel.getColumn(0).setPreferredWidth(80);   // Mã NSX
        //         columnModel.getColumn(1).setPreferredWidth(200);  // Tên NSX
        //         columnModel.getColumn(2).setPreferredWidth(120);  // Quốc gia
        //         columnModel.getColumn(3).setPreferredWidth(100);  // Ngày tạo
        //         columnModel.getColumn(4).setPreferredWidth(120);  // Thao tác
        //         // Renderer căn giữa
        //         DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        //         centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        //         // Căn giữa các cột: Mã NSX, Quốc gia
        //         int[] centerCols = {0, 2};
        //         for (int col : centerCols) {
        //             newTable.getColumnModel().getColumn(col).setCellRenderer(centerRenderer);
        //         }
        //         // ===== Custom renderer + editor cho cột thao tác =====
        //         TableColumn actionColumn = newTable.getColumnModel().getColumn(4);
        //         actionColumn.setCellRenderer(new TableCellRenderer() {
        //             @Override
        //             public Component getTableCellRendererComponent(JTable table, Object value,
        //                     boolean isSelected, boolean hasFocus, int row, int column) {
        //                 JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        //                 JButton btnEdit = new JButton("✎");
        //                 JButton btnDelete = new JButton("🗑");
        //                 btnEdit.setPreferredSize(new Dimension(50, 25));
        //                 btnDelete.setPreferredSize(new Dimension(50, 25));
        //                 panel.add(btnEdit);
        //                 panel.add(btnDelete);
        //                 return panel;
        //             }
        //         });
        //         // Editor (bắt sự kiện click)
        //         actionColumn.setCellEditor(new DefaultCellEditor(new JCheckBox()) {
        //             JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        //             JButton btnEdit = new JButton("✎");
        //             JButton btnDelete = new JButton("🗑");
        //             {
        //                 btnEdit.setPreferredSize(new Dimension(50, 25));
        //                 btnDelete.setPreferredSize(new Dimension(50, 25));
        //                 panel.add(btnEdit);
        //                 panel.add(btnDelete);
        //                 // Sự kiện Edit
        //                 btnEdit.addActionListener(e -> {
        //                     int row = newTable.getSelectedRow();
        //                     if (row != -1) {
        //                         String maNSX = newTable.getValueAt(row, 0).toString();
        //                         NhaSanXuat nsx = adminController.getAllNhaSanXuat().stream()
        //                                 .filter(n -> n.getMaNhaSX().equals(maNSX))
        //                                 .findFirst().orElse(null);
        //                         // TODO: lấy NhaSanXuat từ DAO theo maNSX và mở form chỉnh sửa
        //                         JDialog editDialog = new JDialog(frame, "Sửa nhà sản xuất", true);
        //                         editDialog.setSize(400, 300);
        //                         editDialog.setLayout(new GridLayout(0, 2, 5, 5));
        //                         JTextField tfMaNhaSX = new JTextField(nsx.getMaNhaSX());
        //                         JTextField tfTenNhaSX = new JTextField(nsx.getTenNhaSX());
        //                         JTextField tfQuocGia = new JTextField(nsx.getQuocGia());
        //                         JTextField tfNgayTao = new JTextField(nsx.getNgayTao() != null ? nsx.getNgayTao().toString() : "");
        //                         editDialog.add(new JLabel("Mã NSX:"));
        //                         editDialog.add(tfMaNhaSX);
        //                         editDialog.add(new JLabel("Tên nhà sản xuất:"));
        //                         editDialog.add(tfTenNhaSX);
        //                         editDialog.add(new JLabel("Quốc gia:"));
        //                         editDialog.add(tfQuocGia);
        //                         editDialog.add(new JLabel("Ngày tạo (yyyy-mm-dd hh:mm:ss):"));
        //                         editDialog.add(tfNgayTao);
        //                         JButton btnSave = new JButton("Lưu");
        //                         JButton btnCancel = new JButton("Hủy");
        //                         editDialog.add(btnSave);
        //                         editDialog.add(btnCancel);
        //                         btnSave.addActionListener(ev -> {
        //                             try {
        //                                 nsx.setTenNhaSX(tfTenNhaSX.getText().trim());
        //                                 nsx.setQuocGia(tfQuocGia.getText().trim());
        //                                 nsx.setNgayTao(tfNgayTao.getText().isEmpty() ? null : java.sql.Timestamp.valueOf(tfNgayTao.getText().trim()));
        //                             } catch (Exception ex) {
        //                                 JOptionPane.showMessageDialog(editDialog, "Lỗi dữ liệu: " + ex.getMessage());
        //                                 return;
        //                             }
        //                             try {
        //                                 boolean success = adminController.updateNhaSanXuat(nsx);
        //                                 if (success) {
        //                                     JOptionPane.showMessageDialog(editDialog, "Cập nhật thành công!");
        //                                     editDialog.dispose();
        //                                     btnNhaSanXuat.doClick();
        //                                 } else {
        //                                     JOptionPane.showMessageDialog(editDialog, "Cập nhật thất bại!");
        //                                 }
        //                             } catch (IllegalArgumentException ex) {
        //                                 JOptionPane.showMessageDialog(editDialog, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        //                             }
        //                         });
        //                         btnCancel.addActionListener(ev -> editDialog.dispose());
        //                         editDialog.setLocationRelativeTo(frame);
        //                         editDialog.setVisible(true);
        //                     }
        //                 });
        //                 // Sự kiện Delete
        //                 btnDelete.addActionListener(ev -> {
        //                     int row = newTable.getSelectedRow();
        //                     if (row != -1) {
        //                         String maNSX = newTable.getValueAt(row, 0).toString();
        //                         int confirm = JOptionPane.showConfirmDialog(frame, "Xóa nhà sản xuất " + maNSX + " ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        //                         if (confirm == JOptionPane.YES_OPTION) {
        //                             try {
        //                                 boolean success = adminController.deleteNhaSanXuat(maNSX);
        //                                 if (success) {
        //                                     JOptionPane.showMessageDialog(frame, "Xóa thành công!");
        //                                     btnNhaSanXuat.doClick();
        //                                 } else {
        //                                     JOptionPane.showMessageDialog(frame, "Xóa thất bại!");
        //                                 }
        //                             } catch (IllegalArgumentException ex) {
        //                                 JOptionPane.showMessageDialog(frame, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        //                             }
        //                         }
        //                     }
        //                 });
        //             }
        //             @Override
        //             public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        //                 return panel;
        //             }
        //             @Override
        //             public Object getCellEditorValue() {
        //                 return "";
        //             }
        //         });
        //         // Đưa vào ScrollPane
        //         JScrollPane newScrollPane = new JScrollPane(newTable);
        //         rightPanel.removeAll();
        //         rightPanel.add(headerPanel, BorderLayout.NORTH);
        //         rightPanel.add(newScrollPane, BorderLayout.CENTER);
        //         rightPanel.revalidate();
        //         rightPanel.repaint();
        //         rightPanel.add(paginationPanel, BorderLayout.SOUTH);
        // Show the frame
        frame.setVisible(true);
    }
}
