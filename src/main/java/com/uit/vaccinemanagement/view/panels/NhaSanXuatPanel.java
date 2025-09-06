package com.uit.vaccinemanagement.view.panels;

import com.uit.vaccinemanagement.controller.AdminController;
import com.uit.vaccinemanagement.model.NhaSanXuat;
import com.uit.vaccinemanagement.view.SharedComponents;
import com.uit.vaccinemanagement.view.dialogs.NhaSanXuatAddDialog;
import com.uit.vaccinemanagement.view.dialogs.NhaSanXuatEditDialog;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class NhaSanXuatPanel extends JPanel {

    private final AdminController adminController;
    private final JFrame parentFrame;
    private int currentPage = 1;
    private final int pageSize = 20;
    private JTable table;
    private JLabel lblPageInfo;
    private JLabel lblTotalRows;
    private JButton btnPrev;
    private JButton btnNext;

    public NhaSanXuatPanel(JFrame parentFrame, AdminController adminController) {
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
        JLabel tableTitle = new JLabel("QUẢN LÝ NHÀ SẢN XUẤT", SwingConstants.CENTER);
        tableTitle.setFont(new Font("Arial", Font.BOLD, 18));
        tableTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Search panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        JTextField searchField = new JTextField() {
            private boolean showingPlaceholder = true;

            {
                setLayout(new BorderLayout());
                JLabel placeholder = new JLabel("🔍 Tìm kiếm nhà sản xuất", SwingConstants.LEFT);
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
        JButton btnAdd = new JButton("Thêm");
        JButton btnDownload = new JButton("Tải xuống");

        // xử lý tìm kiếm
        btnSearch.addActionListener(e -> {
            String keyword = searchField.getText().trim().toLowerCase();
            if (keyword.isEmpty()) {
                loadData(); // nếu để trống thì load lại toàn bộ
                return;
            }

            List<NhaSanXuat> filteredList = adminController.getAllNhaSanXuat().stream()
                    .filter(nsx -> nsx.getMaNhaSX().toLowerCase().contains(keyword)
                            || nsx.getTenNhaSX().toLowerCase().contains(keyword)
                            || nsx.getQuocGia().toLowerCase().contains(keyword))
                    .toList();

            updateTable(filteredList);
            lblTotalRows.setText("Tìm thấy: " + filteredList.size() + " kết quả");
            lblPageInfo.setText("Trang 1/1");
            btnPrev.setEnabled(false);
            btnNext.setEnabled(false);
        });

        btnAdd.addActionListener(e -> {
            JButton refreshButton = new JButton();
            refreshButton.addActionListener(ev -> loadData());
            NhaSanXuatAddDialog addDialog = new NhaSanXuatAddDialog(parentFrame, adminController, refreshButton);
            addDialog.setVisible(true);
        });

        btnDownload.addActionListener(e -> {
            String downloadUrl = "https://example.com/file.xlsx"; // link cố định
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file");
            fileChooser.setSelectedFile(new java.io.File("nha_SX.xlsx"));

            int userSelection = fileChooser.showSaveDialog(parentFrame);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                java.io.File saveFile = fileChooser.getSelectedFile();
                try (java.io.BufferedInputStream in = new java.io.BufferedInputStream(new java.net.URL(downloadUrl).openStream());
                    java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(saveFile)) {

                    byte[] dataBuffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                        fileOutputStream.write(dataBuffer, 0, bytesRead);
                    }

                    JOptionPane.showMessageDialog(parentFrame, "Tải xuống thành công: " + saveFile.getAbsolutePath());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(parentFrame, "Lỗi khi tải file: " + ex.getMessage());
                }
            }
        });

        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(5));
        searchPanel.add(btnSearch);
        searchPanel.add(Box.createHorizontalStrut(5));
        searchPanel.add(btnAdd);
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
        lblPageInfo = new JLabel();
        lblTotalRows = new JLabel();

        btnPrev.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                loadData();
            }
        });

        btnNext.addActionListener(e -> {
            int totalRows = adminController.getNhaSanXuatCount();
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

    private void updatePaginationInfo() {
        int totalRows = adminController.getNhaSanXuatCount();
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

    private void loadData() {
        List<NhaSanXuat> nsxList = adminController.getNhaSanXuatPage(currentPage, pageSize);
        updateTable(nsxList);
        updatePaginationInfo();
    }

    private void updateTable(List<NhaSanXuat> nsxList) {
        String[] columns = {"Mã NSX", "Tên nhà sản xuất", "Quốc gia", "Ngày tạo", "Thao tác"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (NhaSanXuat nsx : nsxList) {
            model.addRow(new Object[]{
                nsx.getMaNhaSX(),
                nsx.getTenNhaSX(),
                nsx.getQuocGia(),
                nsx.getNgayTao(),
                "Thao tác"
            });
        }

        table.setModel(model);
        setupTableProperties();
    }

    private void setupTableProperties() {
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Header customization
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setReorderingAllowed(false);

        // Column widths
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(80);   // Mã NSX
        columnModel.getColumn(1).setPreferredWidth(200);  // Tên NSX
        columnModel.getColumn(2).setPreferredWidth(120);  // Quốc gia
        columnModel.getColumn(3).setPreferredWidth(100);  // Ngày tạo
        columnModel.getColumn(4).setPreferredWidth(120);  // Thao tác

        // Center alignment for specific columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        int[] centerCols = {0, 2};
        for (int col : centerCols) {
            table.getColumnModel().getColumn(col).setCellRenderer(centerRenderer);
        }

        // Action column
        TableColumn actionColumn = table.getColumnModel().getColumn(4);
        actionColumn.setCellRenderer(new ButtonRenderer());
        actionColumn.setCellEditor(new ButtonEditor(new JCheckBox()));
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

            btnEdit.addActionListener(e -> editNhaSanXuat());
            btnDelete.addActionListener(e -> deleteNhaSanXuat());
        }

        private void editNhaSanXuat() {
            int row = table.getSelectedRow();
            if (row != -1) {
                String maNSX = table.getValueAt(row, 0).toString();
                NhaSanXuat nsx = adminController.getAllNhaSanXuat().stream()
                        .filter(n -> n.getMaNhaSX().equals(maNSX))
                        .findFirst().orElse(null);

                if (nsx != null) {
                    showEditDialog(nsx);
                }
            }
        }

        private void deleteNhaSanXuat() {
            int row = table.getSelectedRow();
            if (row != -1) {
                String maNSX = table.getValueAt(row, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(parentFrame,
                        "Xóa nhà sản xuất " + maNSX + " ?",
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        if (adminController.deleteNhaSanXuat(maNSX)) {
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

        private void showEditDialog(NhaSanXuat nsx) {
            JButton refreshButton = new JButton();
            refreshButton.addActionListener(e -> loadData());
            NhaSanXuatEditDialog editDialog =
                new NhaSanXuatEditDialog(parentFrame, nsx, adminController, refreshButton);
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

