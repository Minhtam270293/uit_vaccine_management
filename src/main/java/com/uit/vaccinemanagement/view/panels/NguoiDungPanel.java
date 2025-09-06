package com.uit.vaccinemanagement.view.panels;

import com.uit.vaccinemanagement.controller.AdminController;
import com.uit.vaccinemanagement.model.NguoiDung;
import com.uit.vaccinemanagement.util.Role;
import com.uit.vaccinemanagement.view.dialogs.NguoiDungAddDialog;
import com.uit.vaccinemanagement.view.dialogs.NguoiDungEditDialog;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class NguoiDungPanel extends JPanel {

    private final AdminController adminController;
    private final JFrame parentFrame;
    private int currentPage = 1;
    private final int pageSize = 20;
    private JTable table;
    private JLabel lblPageInfo;
    private JLabel lblTotalRows;
    private JButton btnPrev;
    private JButton btnNext;

    public NguoiDungPanel(JFrame parentFrame, AdminController adminController) {
        this.parentFrame = parentFrame;
        this.adminController = adminController;
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
        JLabel tableTitle = new JLabel("QUẢN LÝ NGƯỜI DÙNG", SwingConstants.CENTER);
        tableTitle.setFont(new Font("Arial", Font.BOLD, 18));
        tableTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Search panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        JTextField searchField = new JTextField() {
            private boolean showingPlaceholder = true;

            {
                setLayout(new BorderLayout());
                JLabel placeholder = new JLabel("🔍 Tìm kiếm người dùng", SwingConstants.LEFT);
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
            new RoundedBorder(Color.LIGHT_GRAY, 1, 16), // sử dụng custom border bo góc 16px
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        JButton btnSearch = new JButton("Tìm kiếm");
        JButton btnAdd = new JButton("Thêm");
        JButton btnDownload = new JButton("Tải xuống");

       btnAdd.addActionListener(e -> {
           JButton refreshButton = new JButton();
           refreshButton.addActionListener(ev -> loadData());
           NguoiDungAddDialog addDialog = new NguoiDungAddDialog(parentFrame, adminController, refreshButton);
           addDialog.setVisible(true);
       });

        // Nút tìm kiếm
        btnSearch.addActionListener(e -> {
            String keyword = searchField.getText().trim().toLowerCase();
            if (keyword.isEmpty()) {
                loadData(); // nếu để trống thì load lại toàn bộ
                return;
            }

            List<NguoiDung> filteredList = adminController.getAllNguoiDung().stream()
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
        
        searchPanel.add(Box.createHorizontalStrut(5));
        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(btnSearch);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(btnAdd);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(btnDownload);
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
            int totalRows = adminController.getAllNguoiDung().size();
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
        List<NguoiDung> userList = adminController.getNguoiDungPage(currentPage, pageSize);
        updateTable(userList);
        updatePaginationInfo();
    }

    private void updatePaginationInfo() {
        int totalRows = adminController.getAllNguoiDung().size();
        int totalPages = (int) Math.ceil((double) totalRows / pageSize);
        if (currentPage > totalPages) {
            currentPage = totalPages;
        }
        if (currentPage < 1) {
            currentPage = 1;
        }
        lblPageInfo.setText("Trang " + currentPage + "/" + (totalPages == 0 ? 1 : totalPages));
        lblTotalRows.setText("Tổng số: " + totalRows);
        btnPrev.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage < totalPages);
    }

    private void updateTable(List<NguoiDung> userList) {
        String[] columns = {"Mã ND", "Họ tên", "Tên đăng nhập", "Email", "Vai trò", "Ngày tạo", "Ngày sinh", "Giới tính", "Thao tác"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

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
                "Thao tác"
            });
        }

        table.setModel(model);
        setupTableProperties();
    }

    private void setupTableProperties() {
        table.setRowHeight(30);

        // Set up the action column
        TableColumn actionColumn = table.getColumnModel().getColumn(8);
        actionColumn.setCellRenderer(new ButtonRenderer());
        actionColumn.setCellEditor(new ButtonEditor(new JCheckBox()));

        // Setup column widths
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(120);
        columnModel.getColumn(3).setPreferredWidth(200);
        columnModel.getColumn(4).setPreferredWidth(80);
        columnModel.getColumn(5).setPreferredWidth(100);
        columnModel.getColumn(6).setPreferredWidth(100);
        columnModel.getColumn(7).setPreferredWidth(80);

        // Center align specific columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        int[] centerColumns = {0, 1, 4, 6, 7};
        for (int col : centerColumns) {
            table.getColumnModel().getColumn(col).setCellRenderer(centerRenderer);
        }

        // Header customization
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setReorderingAllowed(false);
        header.setResizingAllowed(true);
    }

    // Custom renderer for the action column
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

    // Custom editor for the action column
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

            btnEdit.addActionListener(e -> editUser());
            btnDelete.addActionListener(e -> deleteUser());
        }

        private void editUser() {
            int row = table.getSelectedRow();
            if (row != -1) {
                String maNguoiDung = table.getValueAt(row, 0).toString();
                NguoiDung nd = adminController.getAllNguoiDung().stream()
                        .filter(u -> u.getMaNguoiDung().equals(maNguoiDung))
                        .findFirst().orElse(null);

                if (nd != null) {
                    showEditDialog(nd);
                }
            }
        }

        private void deleteUser() {
            int row = table.getSelectedRow();
            if (row != -1) {
                String maNguoiDung = table.getValueAt(row, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(parentFrame,
                        "Xóa người dùng " + maNguoiDung + " ?",
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    if (adminController.deleteNguoiDung(maNguoiDung)) {
                        JOptionPane.showMessageDialog(parentFrame, "Xóa thành công!");
                        loadData();
                    } else {
                        JOptionPane.showMessageDialog(parentFrame, "Xóa thất bại!");
                    }
                }
            }
        }

        private void showEditDialog(NguoiDung nd) {
            JButton refreshButton = new JButton();
            refreshButton.addActionListener(e -> loadData());
            NguoiDungEditDialog editDialog =
                new NguoiDungEditDialog(parentFrame, nd, adminController, refreshButton);
            editDialog.setVisible(true);
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
    }

    // Thêm class RoundedBorder vào cuối file (nếu chưa có)
    class RoundedBorder extends javax.swing.border.AbstractBorder {
        private final Color color;
        private final int thickness;
        private final int arc;

        public RoundedBorder(Color color, int thickness, int arc) {
            this.color = color;
            this.thickness = thickness;
            this.arc = arc;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(thickness));
            g2.drawRoundRect(x, y, width - 1, height - 1, arc, arc);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(thickness, thickness, thickness, thickness);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = insets.top = insets.bottom = thickness;
            return insets;
        }
    }
}
