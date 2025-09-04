package com.uit.vaccinemanagement.view.components.pages;

import com.uit.vaccinemanagement.controller.AdminController;
import com.uit.vaccinemanagement.model.NguoiDung;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.EventObject;

public class UserManagementPanel extends ManagementPanel<NguoiDung> {

    private DefaultTableModel model;

    private static final String[] COLUMN_NAMES = {
        "Mã ND", "Họ Tên", "Tên đăng nhập", "Email",
        "Vai trò", "Ngày sinh", "Giới tính", "Thao tác"
    };

    public UserManagementPanel(AdminController controller) {
        super(controller, "Danh sách người dùng");
        initialize();
    }

    @Override
    protected String[] getColumnNames() {
        return COLUMN_NAMES;
    }

    @Override
    protected void setupTable() {
        // Create table model
        model = new DefaultTableModel(getColumnNames(), 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7; // Only action column is editable
            }
        };
        table.setModel(model);

        // Set table properties
        table.setFillsViewportHeight(true);
        table.setRowHeight(35);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Setup column widths
        int[] widths = {80, 150, 120, 200, 80, 100, 80, 120};
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < widths.length; i++) {
            columnModel.getColumn(i).setPreferredWidth(widths[i]);
        }

        // Setup header
        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        header.setResizingAllowed(true);

        // Center align columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // ID
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Role
        table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer); // Birth date
        table.getColumnModel().getColumn(6).setCellRenderer(centerRenderer); // Gender

        // Setup action column
        setupActionColumn(7); // Action column
    }

    private void setupActionColumn(int columnIndex) {
        TableColumn actionColumn = table.getColumnModel().getColumn(columnIndex);

        // Create a reusable panel for the renderer
        class ActionPanel extends JPanel {

            private final JButton btnEdit;
            private final JButton btnDelete;

            ActionPanel() {
                setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
                btnEdit = new JButton("✎");
                btnDelete = new JButton("🗑");

                btnEdit.setPreferredSize(new Dimension(50, 25));
                btnDelete.setPreferredSize(new Dimension(50, 25));

                add(btnEdit);
                add(btnDelete);
            }
        }

        // Set up the renderer
        actionColumn.setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
            ActionPanel panel = new ActionPanel();
            if (isSelected) {
                panel.setBackground(table.getSelectionBackground());
            }
            return panel;
        });

        // Set up the editor (to handle button clicks)
        actionColumn.setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            private final ActionPanel panel = new ActionPanel();

            {
                // Edit button action
                panel.btnEdit.addActionListener(e -> {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        String userId = table.getValueAt(row, 0).toString();
                        fireEditingStopped();
                        controller.showUserEditDialog(userId, () -> loadTableData(currentPage));
                    }
                });

                // Delete button action
                panel.btnDelete.addActionListener(e -> {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        String userId = table.getValueAt(row, 0).toString();
                        fireEditingStopped();
                        if (confirmDelete()) {
                            try {
                                if (controller.deleteNguoiDung(userId)) {
                                    loadTableData(currentPage);
                                }
                            } catch (SQLException ex) {
                                handleError("Lỗi khi xóa người dùng", ex);
                            }
                        }
                    }
                });
            }

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value,
                    boolean isSelected, int row, int column) {
                panel.setBackground(table.getSelectionBackground());
                return panel;
            }

            @Override
            public Object getCellEditorValue() {
                return "";
            }

            @Override
            public boolean shouldSelectCell(EventObject anEvent) {
                return true;
            }
        });
    }

    @Override
    protected void loadTableData(int page) {
        try {
            List<NguoiDung> users = controller.getNguoiDungPage(page, PAGE_SIZE);
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            for (NguoiDung user : users) {
                model.addRow(new Object[]{
                    user.getMaNguoiDung(),
                    user.getHoTen(),
                    user.getTenDangNhap(),
                    user.getEmail(),
                    user.getVaiTro(),
                    user.getNgaySinh(),
                    user.getGioiTinh(),
                    "" // Action column placeholder
                });
            }

            updatePagination();
        } catch (SQLException e) {
            handleError("Lỗi khi tải dữ liệu người dùng", e);
        }
    }

    @Override
    protected void handleAdd() {
        controller.showUserEditDialog(null, () -> loadTableData(currentPage));
    }

    @Override
    protected void handleEdit(int selectedRow) {
        if (selectedRow != -1) {
            String userId = table.getValueAt(selectedRow, 0).toString();
            controller.showUserEditDialog(userId, () -> loadTableData(currentPage));
        }
    }

    @Override
    protected void handleDelete(int selectedRow) {
        if (selectedRow != -1) {
            String userId = table.getValueAt(selectedRow, 0).toString();
            if (confirmDelete()) {
                try {
                    if (controller.deleteNguoiDung(userId)) {
                        loadTableData(currentPage);
                    }
                } catch (SQLException e) {
                    handleError("Error deleting user", e);
                }
            }
        }
    }

    @Override
    protected int getTotalRecords() throws SQLException {
        return controller.getTotalNguoiDung();
    }
}
