package com.uit.vaccinemanagement.view.components.pages;

import com.uit.vaccinemanagement.controller.AdminController;
import com.uit.vaccinemanagement.model.NhaSanXuat;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class NhaSanXuatManagementPanel extends ManagementPanel<NhaSanXuat> {

    private static final String[] COLUMN_NAMES = {
        "Mã NSX", "Tên NSX", "Quốc gia", "Ngày tạo"
    };

    public NhaSanXuatManagementPanel(AdminController controller) {
        super(controller, "Danh sách Nhà sản xuất");
        initialize();
    }

    @Override
    protected String[] getColumnNames() {
        return COLUMN_NAMES;
    }

    protected void setupTable() {
        DefaultTableModel model = new DefaultTableModel(getColumnNames(), 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(model);

        // Set column widths
        int[] widths = {80, 200, 150, 100};
        for (int i = 0; i < widths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }

        // Center align columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // ID
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // Date
    }

    @Override
    protected void loadTableData(int page) {
        try {
            List<NhaSanXuat> manufacturers = controller.getNhaSanXuatPage(page, PAGE_SIZE);
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            for (NhaSanXuat nsx : manufacturers) {
                model.addRow(new Object[]{
                    nsx.getMaNhaSX(),
                    nsx.getTenNhaSX(),
                    nsx.getQuocGia(),
                    nsx.getNgayTao()
                });
            }

            updatePagination();
        } catch (SQLException e) {
            handleError("Error loading manufacturer data", e);
        }
    }

    @Override
    protected void handleAdd() {
        controller.showNhaSanXuatEditDialog(null, () -> loadTableData(currentPage));
    }

    @Override
    protected void handleEdit(int selectedRow) {
        if (selectedRow != -1) {
            String manufacturerId = table.getValueAt(selectedRow, 0).toString();
            controller.showNhaSanXuatEditDialog(manufacturerId, () -> loadTableData(currentPage));
        }
    }

    @Override
    protected void handleDelete(int selectedRow) {
        if (selectedRow != -1) {
            String manufacturerId = table.getValueAt(selectedRow, 0).toString();
            if (confirmDelete()) {
                try {
                    if (controller.deleteNhaSanXuat(manufacturerId)) {
                        loadTableData(currentPage);
                    }
                } catch (SQLException e) {
                    handleError("Error deleting manufacturer", e);
                }
            }
        }
    }

    @Override
    protected int getTotalRecords() throws SQLException {
        return controller.getTotalNhaSanXuat();
    }

    protected void handleDelete() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String maNSX = (String) table.getValueAt(selectedRow, 0);
            if (confirmDelete()) {
                try {
                    if (controller.deleteNhaSanXuat(maNSX)) {
                        loadTableData(currentPage);
                    }
                } catch (SQLException e) {
                    handleError("Error deleting manufacturer", e);
                }
            }
        }
    }
}
