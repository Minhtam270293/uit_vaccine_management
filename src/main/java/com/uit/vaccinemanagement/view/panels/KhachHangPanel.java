package com.uit.vaccinemanagement.view.panels;

import com.uit.vaccinemanagement.controller.BacSiController;
import com.uit.vaccinemanagement.model.NguoiDung;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class KhachHangPanel extends JPanel {

    private final BacSiController bacSiController;
    private final JFrame parentFrame;
    private final NguoiDung currentUser;
    private int currentPage = 1;
    private final int pageSize = 20;
    private JTable table;
    private JLabel lblPageInfo;
    private JLabel lblTotalRows;
    private JButton btnPrev;
    private JButton btnNext;
    private JLabel tableTitle;
    private JTextField searchField;
    private String currentSearchTerm = "";
    private boolean isSearching = false;

    public KhachHangPanel(JFrame parentFrame, BacSiController bacSiController, NguoiDung currentUser) {
        this.parentFrame = parentFrame;
        this.bacSiController = bacSiController;
        this.currentUser = currentUser;
        setLayout(new BorderLayout());
        initializeComponents();
        loadData();
    }

    private void initializeComponents() {
        // Header panel with title and search
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Table
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Pagination panel
        JPanel paginationPanel = createPaginationPanel();
        add(paginationPanel, BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());

        // Title
        tableTitle = new JLabel("QUẢN LÝ KHÁCH HÀNG", SwingConstants.CENTER);
        tableTitle.setFont(new Font("Arial", Font.BOLD, 18));
        tableTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Search panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        JButton btnSearch = new JButton("Tìm kiếm");
        JButton btnRefresh = new JButton("Tải lại");

        btnSearch.addActionListener(e -> {
            String keyword = searchField.getText().trim().toLowerCase();
            if (keyword.isEmpty()) {
                loadData();
                return;
            }

            List<NguoiDung> filteredList = bacSiController.getKhachHangPage(currentPage, pageSize).stream()
                    .filter(nd -> nd.getHoTen().toLowerCase().contains(keyword)
                    || nd.getTenDangNhap().toLowerCase().contains(keyword)
                    || nd.getEmail().toLowerCase().contains(keyword))
                    .toList();

            updateTable(filteredList);
            lblTotalRows.setText("Tìm thấy: " + filteredList.size() + " kết quả");
            lblPageInfo.setText("Trang 1/1");
            btnPrev.setEnabled(false);
            btnNext.setEnabled(false);
        });

        btnRefresh.addActionListener(e -> {
            searchField.setText("");
            currentPage = 1;
            loadData();
        });

        searchPanel.add(new JLabel("Tìm kiếm khách hàng"));
        searchPanel.add(Box.createHorizontalStrut(5));
        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(btnSearch);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(btnRefresh);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        headerPanel.add(tableTitle, BorderLayout.NORTH);
        headerPanel.add(searchPanel, BorderLayout.SOUTH);

        return headerPanel;
    }

    private JPanel createPaginationPanel() {
        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPrev = new JButton("<<");
        btnNext = new JButton(">>");
        lblPageInfo = new JLabel();
        lblTotalRows = new JLabel();

        btnPrev.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                loadData();
            }
        });

        btnNext.addActionListener(e -> {
            int totalRows = bacSiController.getKhachHangCount();
            int totalPages = (int) Math.ceil((double) totalRows / pageSize);
            if (currentPage < totalPages) {
                currentPage++;
                loadData();
            }
        });

        paginationPanel.add(lblTotalRows);
        paginationPanel.add(btnPrev);
        paginationPanel.add(lblPageInfo);
        paginationPanel.add(btnNext);

        return paginationPanel;
    }

    private void loadData() {
        List<NguoiDung> data;
        int totalRows;

        if (isSearching) {
            data = bacSiController.searchKhachHang(currentSearchTerm, currentPage, pageSize);
            totalRows = bacSiController.getSearchKhachHangCount(currentSearchTerm);
        } else {
            data = bacSiController.getKhachHangPage(currentPage, pageSize);
            totalRows = bacSiController.getKhachHangCount();
        }

        updateTable(data);
        updatePagination(totalRows);
    }

    private void updateTable(List<NguoiDung> data) {
        String[] columns = {
            "Mã người dùng", "Họ tên", "Tên đăng nhập", "Email",
            "Ngày sinh", "Giới tính", "Thao tác"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (NguoiDung khach : data) {
            model.addRow(new Object[]{
                khach.getMaNguoiDung(),
                khach.getHoTen(),
                khach.getTenDangNhap(),
                khach.getEmail(),
                khach.getNgaySinh(),
                khach.getGioiTinh(),
                "Thao tác"
            });
        }

        table.setModel(model);
        setupTableProperties();
    }

    private void setupTableProperties() {
        table.setRowHeight(30);

        // Set up the action column
        TableColumn actionColumn = table.getColumnModel().getColumn(6);
        actionColumn.setCellRenderer(new ButtonRenderer());
        actionColumn.setCellEditor(new ButtonEditor(new JCheckBox()));

        // Setup column widths
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(80);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(120);
        columnModel.getColumn(3).setPreferredWidth(200);
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(5).setPreferredWidth(80);
        columnModel.getColumn(6).setPreferredWidth(120);
    }

    private void updatePagination(int totalRows) {
        int totalPages = (int) Math.ceil((double) totalRows / pageSize);
        lblPageInfo.setText(String.format("Trang %d/%d", currentPage, totalPages));
        lblTotalRows.setText(String.format("Tổng số: %d", totalRows));
        btnPrev.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage < totalPages);
    }

    private class ButtonRenderer implements TableCellRenderer {

        private final JPanel panel;
        private final JButton btnEdit;
        private final JButton btnDelete;

        public ButtonRenderer() {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            btnEdit = new JButton("✎");
            btnDelete = new JButton("🗑");

            btnEdit.setPreferredSize(new Dimension(50, 25));
            btnDelete.setPreferredSize(new Dimension(50, 25));

            panel.add(btnEdit);
            panel.add(btnDelete);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            return panel;
        }
    }

    private class ButtonEditor extends DefaultCellEditor {

        private final JPanel panel;
        private final JButton btnEdit;
        private final JButton btnDelete;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            btnEdit = new JButton("✎");
            btnDelete = new JButton("🗑");

            btnEdit.setPreferredSize(new Dimension(50, 25));
            btnDelete.setPreferredSize(new Dimension(50, 25));

            panel.add(btnEdit);
            panel.add(btnDelete);

            btnEdit.addActionListener(e -> {
                int row = table.getSelectedRow();
                if (row != -1) {
                    String maNguoiDung = table.getValueAt(row, 0).toString();
                    editKhach(maNguoiDung);
                }
                fireEditingStopped();
            });

            btnDelete.addActionListener(e -> deleteKhach(table.getValueAt(table.getSelectedRow(), 0).toString()));
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }

        private void editKhach(String maNguoiDung) {
            NguoiDung khach = bacSiController.getKhachById(maNguoiDung);
            if (khach == null) {
                JOptionPane.showMessageDialog(parentFrame, "Không tìm thấy thông tin khách hàng!");
                return;
            }

            JDialog editDialog = new JDialog(parentFrame, "Sửa thông tin khách hàng", true);
            editDialog.setSize(400, 320);
            editDialog.setLayout(new GridLayout(0, 2, 5, 5));

            // Add form fields (no ID, role, or password fields since they can't be edited by BacSi)
            addFormField(editDialog, "Họ tên:", khach.getHoTen());
            addFormField(editDialog, "Email:", khach.getEmail());
            addFormField(editDialog, "Tên đăng nhập:", khach.getTenDangNhap());
            addFormField(editDialog, "Ngày sinh (yyyy-mm-dd):",
                    khach.getNgaySinh() != null ? khach.getNgaySinh().toString() : "");

            String[] genders = {"Nam", "Nữ"};
            JComboBox<String> cbGioiTinh = new JComboBox<>(genders);
            cbGioiTinh.setSelectedItem(khach.getGioiTinh());
            editDialog.add(new JLabel("Giới tính:"));
            editDialog.add(cbGioiTinh);

            // Add buttons
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton btnSave = new JButton("Lưu");
            JButton btnCancel = new JButton("Hủy");
            buttonPanel.add(btnSave);
            buttonPanel.add(btnCancel);
            editDialog.add(buttonPanel);

            btnSave.addActionListener(e -> {
                try {
                    Component[] components = editDialog.getContentPane().getComponents();
                    String hoTen = ((JTextField) components[1]).getText().trim();
                    String email = ((JTextField) components[3]).getText().trim();
                    String tenDangNhap = ((JTextField) components[5]).getText().trim();
                    String birthDateStr = ((JTextField) components[7]).getText().trim();
                    String gioiTinh = cbGioiTinh.getSelectedItem().toString();

                    // Validate required fields
                    if (hoTen.isEmpty()) {
                        throw new IllegalArgumentException("Họ tên không được để trống");
                    }

                    // Convert date string to java.sql.Date
                    java.sql.Date ngaySinh = null;
                    if (!birthDateStr.isEmpty()) {
                        try {
                            ngaySinh = java.sql.Date.valueOf(birthDateStr);
                        } catch (IllegalArgumentException ex) {
                            throw new IllegalArgumentException("Ngày sinh không hợp lệ (định dạng yyyy-mm-dd)");
                        }
                    }

                    if (bacSiController.updateKhach(maNguoiDung, hoTen, tenDangNhap, email,
                            ngaySinh, gioiTinh)) {
                        JOptionPane.showMessageDialog(editDialog, "Cập nhật thành công!");
                        editDialog.dispose();
                        loadData();
                    } else {
                        JOptionPane.showMessageDialog(editDialog, "Cập nhật thất bại!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(editDialog, "Lỗi: " + ex.getMessage());
                }
            });

            btnCancel.addActionListener(e -> editDialog.dispose());

            editDialog.setLocationRelativeTo(parentFrame);
            editDialog.setVisible(true);
        }

        private void addFormField(JDialog dialog, String label, String value) {
            dialog.add(new JLabel(label));
            JTextField textField = new JTextField(value);
            dialog.add(textField);
        }

        private void deleteKhach(String maNguoiDung) {
            int confirm = JOptionPane.showConfirmDialog(parentFrame,
                    "Xóa khách hàng này?", "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (bacSiController.deleteKhach(maNguoiDung)) {
                    JOptionPane.showMessageDialog(parentFrame, "Xóa thành công!");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(parentFrame, "Xóa thất bại!");
                }
            }
        }
    }
}
