package com.uit.vaccinemanagement.view.panels;

import com.uit.vaccinemanagement.controller.BacSiController;
import com.uit.vaccinemanagement.model.NguoiDung;
import com.uit.vaccinemanagement.view.dialogs.KhachHangEditDialog;
import com.uit.vaccinemanagement.view.SharedComponents;
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
        JTextField searchField = new JTextField() {
            private boolean showingPlaceholder = true;

            {
                setLayout(new BorderLayout());
                JLabel placeholder = new JLabel("🔍 Tìm kiếm khách hàng", SwingConstants.LEFT);
                placeholder.setForeground(Color.GRAY);
                placeholder.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
                add(placeholder, BorderLayout.CENTER);

                addFocusListener(new java.awt.event.FocusAdapter() {
                    @Override
                    public void focusGained(java.awt.event.FocusEvent e) {
                        if (showingPlaceholder) {
                            remove(placeholder);
                            showingPlaceholder = false;
                            repaint();
                        }
                    }

                    @Override
                    public void focusLost(java.awt.event.FocusEvent e) {
                        if (getText().isEmpty()) {
                            add(placeholder, BorderLayout.CENTER);
                            showingPlaceholder = true;
                            repaint();
                        }
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE); // Đảm bảo nền luôn màu trắng
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        searchField.setOpaque(false); // Đảm bảo nền không bị ghi đè
        searchField.setBackground(Color.WHITE); // Đặt màu nền trắng
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            new SharedComponents.RoundedBorder(Color.LIGHT_GRAY, 1, 16), // sử dụng custom border bo góc 16px
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
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

            JButton refreshButton = new JButton();
            refreshButton.addActionListener(e -> loadData());
            KhachHangEditDialog editDialog =
                new KhachHangEditDialog(parentFrame, khach, bacSiController, refreshButton);
            editDialog.setVisible(true);
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
