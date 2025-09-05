package com.uit.vaccinemanagement.view.panels;

import com.uit.vaccinemanagement.controller.BacSiController;
import com.uit.vaccinemanagement.model.NguoiDung;
import com.uit.vaccinemanagement.view.TiemChungEditDialog;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class TiemChungPanel extends JPanel {

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

    public TiemChungPanel(JFrame parentFrame, BacSiController bacSiController, NguoiDung currentUser) {
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
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);

        // Pagination Panel
        JPanel paginationPanel = createPaginationPanel();

        // Add all components to the main panel
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(paginationPanel, BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());

        // Title
        JLabel tableTitle = new JLabel("QUẢN LÝ TIÊM CHỦNG", SwingConstants.CENTER);
        tableTitle.setFont(new Font("Arial", Font.BOLD, 18));
        tableTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Search panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        JButton btnSearch = new JButton("Tìm kiếm");
        JButton btnDownload = new JButton("Tải xuống");

        // Add action listeners
        btnSearch.addActionListener(e -> {
            currentPage = 1;
            String searchTerm = searchField.getText().trim();
            loadSearchResults(searchTerm);
        });

        btnDownload.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file");
            fileChooser.setSelectedFile(new java.io.File("danh_sach_tiem_chung.xlsx"));

            int userSelection = fileChooser.showSaveDialog(parentFrame);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                java.io.File fileToSave = fileChooser.getSelectedFile();
                JOptionPane.showMessageDialog(parentFrame,
                        "Tính năng xuất Excel sẽ được cài đặt sau\nFile sẽ được lưu tại: "
                        + fileToSave.getAbsolutePath());
            }
        });

        searchPanel.add(new JLabel("Tìm kiếm tiêm chủng"));
        searchPanel.add(Box.createHorizontalStrut(5));
        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(5));
        searchPanel.add(btnSearch);
        searchPanel.add(Box.createHorizontalStrut(5));
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
        lblPageInfo = new JLabel("Trang 1/1");
        lblTotalRows = new JLabel("Tổng số: 0");

        paginationPanel.add(lblTotalRows);
        paginationPanel.add(btnPrev);
        paginationPanel.add(lblPageInfo);
        paginationPanel.add(btnNext);

        // Add pagination button listeners
        btnPrev.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                loadData();
            }
        });

        btnNext.addActionListener(e -> {
            currentPage++;
            loadData();
        });

        return paginationPanel;
    }

    private void loadData() {
        // Get data for current page
        int offset = (currentPage - 1) * pageSize;
        List<Object[]> data = bacSiController.getTiemChungByBacSiPaginated(
                currentUser.getMaNguoiDung(),
                offset,
                pageSize
        );
        updateTable(data);

        // Update pagination
        int totalRows = bacSiController.getTotalTiemChungByBacSi(currentUser.getMaNguoiDung());
        updatePaginationInfo(totalRows);
    }

    private void updateTable(List<Object[]> data) {
        String[] columns = {"Mã tiêm chủng", "Ngày tiêm", "Tên vaccine", "Tên khách", "Trạng thái", "Ghi chú", "Thao tác"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only action column is editable
            }
        };

        for (Object[] row : data) {
            Object[] rowWithAction = new Object[7];
            System.arraycopy(row, 0, rowWithAction, 0, row.length);
            rowWithAction[6] = "Thao tác"; // Placeholder for action buttons
            model.addRow(rowWithAction);
        }

        table.setModel(model);

        // Set column widths
        TableColumnModel columnModel = table.getColumnModel();
        int[] columnWidths = {100, 100, 150, 150, 100, 200, 120}; // Added width for action column
        for (int i = 0; i < columnWidths.length; i++) {
            columnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        // Center align specific columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        int[] centerCols = {0, 1, 4}; // Mã tiêm chủng, Ngày tiêm, Trạng thái
        for (int col : centerCols) {
            table.getColumnModel().getColumn(col).setCellRenderer(centerRenderer);
        }

        // Action column
        TableColumn actionColumn = table.getColumnModel().getColumn(6);
        actionColumn.setCellRenderer(new ButtonRenderer());
        actionColumn.setCellEditor(new ButtonEditor(new JCheckBox()));

        // Style the header
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setReorderingAllowed(false);
        header.setResizingAllowed(true);
    }

    private void updatePaginationInfo(int totalRows) {
        int totalPages = (int) Math.ceil((double) totalRows / pageSize);
        lblPageInfo.setText(String.format("Trang %d/%d", currentPage, totalPages));
        lblTotalRows.setText(String.format("Tổng số: %d", totalRows));
        btnPrev.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage < totalPages);
    }

    private void loadSearchResults(String searchTerm) {
        int offset = (currentPage - 1) * pageSize;
        if (searchTerm.isEmpty()) {
            loadData();
        } else {
            List<Object[]> searchResults = bacSiController.searchTiemChungByBacSi(
                    currentUser.getMaNguoiDung(),
                    searchTerm,
                    offset,
                    pageSize
            );
            updateTable(searchResults);

            int totalRows = bacSiController.getTotalSearchTiemChungByBacSi(
                    currentUser.getMaNguoiDung(),
                    searchTerm
            );
            updatePaginationInfo(totalRows);
        }
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

            btnEdit.addActionListener(e -> editTiemChung());
            btnDelete.addActionListener(e -> deleteTiemChung());
        }

        private void editTiemChung() {
            int row = table.getSelectedRow();
            if (row != -1) {
                String maTiemChung = table.getValueAt(row, 0).toString();
                showEditDialog(maTiemChung);
            }
        }

        private void deleteTiemChung() {
            int row = table.getSelectedRow();
            if (row != -1) {
                String maTiemChung = table.getValueAt(row, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(parentFrame,
                        "Xóa tiêm chủng " + maTiemChung + " ?",
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        if (bacSiController.deleteTiemChung(maTiemChung)) {
                            JOptionPane.showMessageDialog(parentFrame, "Xóa thành công!");
                            loadData();
                        } else {
                            JOptionPane.showMessageDialog(parentFrame, "Xóa thất bại!");
                        }
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(parentFrame, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }

        private void showEditDialog(String maTiemChung) {
            Object[] tiemChungData = bacSiController.getTiemChungById(maTiemChung);
            if (tiemChungData == null) {
                JOptionPane.showMessageDialog(parentFrame, "Không tìm thấy thông tin tiêm chủng!");
                return;
            }

            JButton btnRefresh = new JButton();
            btnRefresh.addActionListener(e -> loadData());
            TiemChungEditDialog editDialog = new TiemChungEditDialog(parentFrame, tiemChungData, bacSiController, btnRefresh);
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
}
