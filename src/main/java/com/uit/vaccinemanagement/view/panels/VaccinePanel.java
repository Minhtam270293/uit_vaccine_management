package com.uit.vaccinemanagement.view.panels;

import com.uit.vaccinemanagement.controller.AdminController;
import com.uit.vaccinemanagement.model.Vaccine;
import com.uit.vaccinemanagement.view.dialogs.VaccineAddDialog;
import com.uit.vaccinemanagement.view.dialogs.VaccineEditDialog;
import com.uit.vaccinemanagement.view.SharedComponents;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.List;

public class VaccinePanel extends JPanel {

    private final AdminController adminController;
    private final JFrame parentFrame;
    private int currentPage = 1;
    private final int pageSize = 20;
    private JTable table;
    private JLabel lblPageInfo;
    private JLabel lblTotalRows;
    private JButton btnPrev;
    private JButton btnNext;
    private MouseAdapter tableMouseListener;

    public VaccinePanel(JFrame parentFrame, AdminController adminController) {
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

        // Initialize table mouse listener
        // tableMouseListener = new MouseAdapter() {
        //     @Override
        //     public void mouseClicked(MouseEvent e) {
        //         int row = table.rowAtPoint(e.getPoint());
        //         int col = table.columnAtPoint(e.getPoint());
        //         // Only show image when clicking the name cell and it's not being edited
        //         if (row != -1 && col == 1 && table.getSelectedRow() == row && table.getSelectedColumn() == col) {
        //             showVaccineImage(row);
        //         }
        //     }
        // };
        // Pagination panel
        JPanel paginationPanel = createPaginationPanel();
        add(paginationPanel, BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());

        // Title
        JLabel tableTitle = new JLabel("QUẢN LÝ VẮC XIN", SwingConstants.CENTER);
        tableTitle.setFont(new Font("Arial", Font.BOLD, 18));
        tableTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Search panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        JTextField searchField = new JTextField() {
            private boolean showingPlaceholder = true;

            {
                setLayout(new BorderLayout());
                JLabel placeholder = new JLabel("🔍 Tìm kiếm vắc xin", SwingConstants.LEFT);
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

        // ====== Sự kiện nút Tìm kiếm ======
        btnSearch.addActionListener(e -> {
            String keyword = searchField.getText().trim().toLowerCase();
            List<Vaccine> vaccineList;

            if (keyword.isEmpty()) {
                // Nếu không nhập gì thì load lại phân trang bình thường
                loadData();
                return;
            }

            // Lọc danh sách theo keyword
            vaccineList = adminController.getAllVaccine().stream()
                    .filter(v -> v.getMaVaccine().toLowerCase().contains(keyword)
                    || v.getTenVaccine().toLowerCase().contains(keyword)
                    || v.getSoLo().toLowerCase().contains(keyword))
                    .toList();

            // Cập nhật bảng với danh sách đã lọc
            updateTable(vaccineList);

            // Tắt phân trang khi search
            lblPageInfo.setText("Kết quả tìm kiếm");
            lblTotalRows.setText("Tìm thấy: " + vaccineList.size());
            btnPrev.setEnabled(false);
            btnNext.setEnabled(false);
        });

        btnAdd.addActionListener(e -> {
            JButton refreshButton = new JButton();
            refreshButton.addActionListener(ev -> loadData());
            VaccineAddDialog addDialog = new VaccineAddDialog(parentFrame, adminController, refreshButton);
            addDialog.setVisible(true);
        });

        btnDownload.addActionListener(e -> {
            String downloadUrl = "https://example.com/file.xlsx"; // link cố định
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file");
            fileChooser.setSelectedFile(new java.io.File("vaccine.xlsx"));

            int userSelection = fileChooser.showSaveDialog(parentFrame);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                java.io.File saveFile = fileChooser.getSelectedFile();
                try (java.io.BufferedInputStream in = new java.io.BufferedInputStream(new java.net.URL(downloadUrl).openStream()); java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(saveFile)) {

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
            int totalRows = adminController.getVaccineCount();
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
        int totalRows = adminController.getVaccineCount();
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
        List<Vaccine> vaccineList = adminController.getVaccinePage(currentPage, pageSize);
        updateTable(vaccineList);
        updatePaginationInfo();
    }

    private void updateTable(List<Vaccine> vaccineList) {
        String[] columns = {"Mã vắc xin", "Tên vắc xin", "Số lô", "Ngày SX", "Ngày hết hạn",
            "Tổng SL", "SL còn lại", "Giá nhập", "Giá bán",
            "Mã bệnh", "Mã NSX", "Ngày tạo", "Thao tác"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Vaccine vc : vaccineList) {
            model.addRow(new Object[]{
                vc.getMaVaccine(),
                vc.getTenVaccine(),
                vc.getSoLo(),
                vc.getNgaySX(),
                vc.getNgayHetHan(),
                vc.getTongSL(),
                vc.getSlConLai(),
                vc.getGiaNhap(),
                vc.getGiaBan(),
                vc.getMaBenh(),
                vc.getMaNhaSX(),
                vc.getNgayTao(),
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
        int[] columnWidths = {80, 130, 80, 80, 80, 80, 80, 100, 100, 80, 80, 80, 120};
        for (int i = 0; i < columnWidths.length; i++) {
            columnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        // Center alignment for specific columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        int[] centerCols = {0, 2, 5, 6, 7, 8, 9, 10};
        for (int col : centerCols) {
            table.getColumnModel().getColumn(col).setCellRenderer(centerRenderer);
        }

        // Action column
        TableColumn actionColumn = table.getColumnModel().getColumn(12);
        actionColumn.setCellRenderer(new ButtonRenderer());
        actionColumn.setCellEditor(new ButtonEditor(new JCheckBox()));

        // ======= Sự kiện click vào "Tên vắc xin" để hiển thị ảnh =======
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (row != -1 && col == 1) { // cột Tên Vaccine
                    showVaccineImage(row);
                }
            }
        });
    }

    private void showVaccineImage(int row) {
        String maVaccine = table.getValueAt(row, 0).toString();
        Vaccine vc = adminController.getAllVaccine().stream()
                .filter(v -> v.getMaVaccine().equals(maVaccine))
                .findFirst().orElse(null);

        if (vc != null && vc.getHinhAnhUrl() != null && !vc.getHinhAnhUrl().isEmpty()) {
            try {
                URL url = new URL(vc.getHinhAnhUrl());
                ImageIcon icon = new ImageIcon(url);
                Image img = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                JLabel lblImg = new JLabel(new ImageIcon(img));
                JOptionPane.showMessageDialog(parentFrame, lblImg, vc.getTenVaccine(), JOptionPane.PLAIN_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parentFrame, "Không tải được hình ảnh!");
            }
        } else {
            JOptionPane.showMessageDialog(parentFrame, "Không có hình ảnh cho vắc xin này!");
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

            btnEdit.addActionListener(e -> {
                fireEditingStopped();
                editVaccine();
            });
            btnDelete.addActionListener(e -> {
                fireEditingStopped();
                deleteVaccine();
            });

            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    fireEditingStopped();
                }
            });
        }

        private void editVaccine() {
            int row = table.getSelectedRow();
            if (row != -1) {
                String maVaccine = table.getValueAt(row, 0).toString();
                Vaccine vc = adminController.getAllVaccine().stream()
                        .filter(v -> v.getMaVaccine().equals(maVaccine))
                        .findFirst().orElse(null);

                if (vc != null) {
                    JButton refreshButton = new JButton();
                    refreshButton.addActionListener(e -> loadData());
                    VaccineEditDialog editDialog = new VaccineEditDialog(
                            parentFrame,
                            vc,
                            adminController,
                            refreshButton
                    );
                    editDialog.setVisible(true);
                }
            }
        }

        private void deleteVaccine() {
            int row = table.getSelectedRow();
            if (row != -1) {
                String maVaccine = table.getValueAt(row, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(parentFrame,
                        "Xóa vắc xin " + maVaccine + " ?",
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        if (adminController.deleteVaccine(maVaccine)) {
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

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }

        @Override
        public boolean stopCellEditing() {
            fireEditingStopped();
            return super.stopCellEditing();
        }
    }
}
