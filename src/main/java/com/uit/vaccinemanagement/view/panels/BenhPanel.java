package com.uit.vaccinemanagement.view.panels;

import com.uit.vaccinemanagement.controller.AdminController;
import com.uit.vaccinemanagement.model.Benh;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class BenhPanel extends JPanel {

    private final AdminController adminController;
    private final JFrame parentFrame;
    private int currentPage = 1;
    private final int pageSize = 20;
    private JTable table;
    private JLabel lblPageInfo;
    private JLabel lblTotalRows;
    private JButton btnPrev;
    private JButton btnNext;
    private String currentSearchTerm = "";

    public BenhPanel(JFrame parentFrame, AdminController adminController) {
        this.parentFrame = parentFrame;
        this.adminController = adminController;
        setLayout(new BorderLayout());
        initializeComponents();
        loadData();
    }

    private void initializeComponents() {
        // Header panel with title and controls
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
        JLabel tableTitle = new JLabel("QUẢN LÝ BỆNH", SwingConstants.CENTER);
        tableTitle.setFont(new Font("Arial", Font.BOLD, 18));
        tableTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Search panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        JButton btnSearch = new JButton("Tìm kiếm");
        JButton btnAdd = new JButton("Thêm");
        JButton btnDownload = new JButton("Tải xuống");

        btnSearch.addActionListener(e -> {
            currentPage = 1;  // Reset to first page when searching
            String searchTerm = searchField.getText().trim();
            if (searchTerm.isEmpty()) {
                loadData();
            } else {
                List<Benh> searchResults = adminController.searchBenhByName(currentPage, pageSize, searchTerm);
                updateTable(searchResults);
            }
        });

        btnAdd.addActionListener(e -> showAddDialog());

        btnDownload.addActionListener(e -> {
            // File chooser for saving Excel file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file");
            fileChooser.setSelectedFile(new java.io.File("danh_sach_benh.xlsx"));

            int userSelection = fileChooser.showSaveDialog(parentFrame);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                java.io.File fileToSave = fileChooser.getSelectedFile();
                JOptionPane.showMessageDialog(parentFrame, 
                    "Tính năng xuất Excel sẽ được cài đặt sau\nFile sẽ được lưu tại: " + 
                    fileToSave.getAbsolutePath());
            }
        });

        searchPanel.add(new JLabel("Tìm kiếm bệnh"));
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
            int totalRows;
            if (currentSearchTerm != null && !currentSearchTerm.trim().isEmpty()) {
                totalRows = adminController.getTotalBenhSearchResults(currentSearchTerm);
            } else {
                totalRows = adminController.getTotalBenh();
            }
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
        List<Benh> list;
        if (currentSearchTerm != null && !currentSearchTerm.trim().isEmpty()) {
            list = adminController.searchBenhByName(currentPage, pageSize, currentSearchTerm);
        } else {
            list = adminController.getBenhPage(currentPage, pageSize);
        }
        updateTable(list);
        updatePaginationInfo();
    }

    private void updatePaginationInfo() {
        int totalRows;
        if (currentSearchTerm != null && !currentSearchTerm.trim().isEmpty()) {
            totalRows = adminController.getTotalBenhSearchResults(currentSearchTerm);
        } else {
            totalRows = adminController.getTotalBenh();
        }
        
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

    private void updateTable(List<Benh> benhList) {
        String[] columns = {"Mã bệnh", "Tên bệnh", "Mô tả", "Ngày tạo", "Thao tác"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Benh benh : benhList) {
            model.addRow(new Object[]{
                benh.getMaBenh(),
                benh.getTenBenh(),
                benh.getMoTa(),
                benh.getNgayTao(),
                "Thao tác"
            });
        }

        table.setModel(model);
        setupTableProperties();
    }

    private void setupTableProperties() {
        table.setRowHeight(30);

        // Set up the action column
        TableColumn actionColumn = table.getColumnModel().getColumn(4);
        actionColumn.setCellRenderer(new ButtonRenderer());
        actionColumn.setCellEditor(new ButtonEditor(new JCheckBox()));

        // Setup column widths
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(80);  // Mã bệnh
        columnModel.getColumn(1).setPreferredWidth(150); // Tên bệnh
        columnModel.getColumn(2).setPreferredWidth(200); // Mô tả
        columnModel.getColumn(3).setPreferredWidth(120); // Ngày tạo
        columnModel.getColumn(4).setPreferredWidth(100); // Thao tác

        // Center align specific columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        int[] centerColumns = {0, 3};
        for (int col : centerColumns) {
            table.getColumnModel().getColumn(col).setCellRenderer(centerRenderer);
        }

        // Header customization
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setReorderingAllowed(false);
        header.setResizingAllowed(true);
    }

    private void showAddDialog() {
        JDialog dialog = new JDialog(parentFrame, "Thêm bệnh mới", true);
        dialog.setLayout(new GridLayout(0, 2, 5, 5));
        dialog.setSize(400, 200);

        JTextField tfMaBenh = new JTextField();
        JTextField tfTenBenh = new JTextField();
        JTextField tfMoTa = new JTextField();

        dialog.add(new JLabel("Mã bệnh:"));
        dialog.add(tfMaBenh);
        dialog.add(new JLabel("Tên bệnh:"));
        dialog.add(tfTenBenh);
        dialog.add(new JLabel("Mô tả:"));
        dialog.add(tfMoTa);

        JButton btnSave = new JButton("Lưu");
        JButton btnCancel = new JButton("Hủy");

        btnSave.addActionListener(e -> {
            String maBenh = tfMaBenh.getText().trim();
            String tenBenh = tfTenBenh.getText().trim();
            String moTa = tfMoTa.getText().trim();

            if (maBenh.isEmpty() || tenBenh.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            Benh benh = new Benh(maBenh, tenBenh, moTa, new java.sql.Timestamp(System.currentTimeMillis()));

            if (adminController.addBenh(benh)) {
                JOptionPane.showMessageDialog(dialog, "Thêm bệnh thành công!");
                dialog.dispose();
                loadData();
            } else {
                JOptionPane.showMessageDialog(dialog, "Thêm bệnh thất bại!");
            }
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        dialog.add(btnSave);
        dialog.add(btnCancel);

        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
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
        private String maBenh;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            btnEdit = new JButton("✎");
            btnDelete = new JButton("🗑");

            btnEdit.setPreferredSize(new Dimension(50, 25));
            btnDelete.setPreferredSize(new Dimension(50, 25));

            btnEdit.addActionListener(e -> {
                fireEditingStopped();
                showEditDialog(adminController.getAllBenh().stream()
                    .filter(b -> b.getMaBenh().equals(maBenh))
                    .findFirst()
                    .orElse(null));
            });

            btnDelete.addActionListener(e -> {
                fireEditingStopped();
                int confirm = JOptionPane.showConfirmDialog(
                    parentFrame,
                    "Bạn có chắc chắn muốn xóa bệnh này?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    if (adminController.deleteBenh(maBenh)) {
                        JOptionPane.showMessageDialog(parentFrame, "Xóa thành công!");
                        loadData();
                    } else {
                        JOptionPane.showMessageDialog(parentFrame, "Xóa thất bại!");
                    }
                }
            });

            panel.add(btnEdit);
            panel.add(btnDelete);
        }

        private void showEditDialog(Benh benh) {
            if (benh == null) return;

            JDialog editDialog = new JDialog(parentFrame, "Sửa thông tin bệnh", true);
            editDialog.setLayout(new GridLayout(0, 2, 5, 5));
            editDialog.setSize(400, 200);

            JTextField tfTenBenh = new JTextField(benh.getTenBenh());
            JTextField tfMoTa = new JTextField(benh.getMoTa());

            editDialog.add(new JLabel("Tên bệnh:"));
            editDialog.add(tfTenBenh);
            editDialog.add(new JLabel("Mô tả:"));
            editDialog.add(tfMoTa);

            JButton btnSave = new JButton("Lưu");
            JButton btnCancel = new JButton("Hủy");

            btnSave.addActionListener(e -> {
                benh.setTenBenh(tfTenBenh.getText().trim());
                benh.setMoTa(tfMoTa.getText().trim());

                if (benh.getTenBenh().isEmpty()) {
                    JOptionPane.showMessageDialog(editDialog, "Tên bệnh không được để trống!");
                    return;
                }

                if (adminController.updateBenh(benh)) {
                    JOptionPane.showMessageDialog(editDialog, "Cập nhật thành công!");
                    editDialog.dispose();
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(editDialog, "Cập nhật thất bại!");
                }
            });

            btnCancel.addActionListener(e -> editDialog.dispose());

            editDialog.add(btnSave);
            editDialog.add(btnCancel);
            editDialog.setLocationRelativeTo(parentFrame);
            editDialog.setVisible(true);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            maBenh = table.getValueAt(row, 0).toString();
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }
}
