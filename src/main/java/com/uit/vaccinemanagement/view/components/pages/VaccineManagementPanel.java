package com.uit.vaccinemanagement.view.components.pages;

import com.uit.vaccinemanagement.controller.AdminController;
import com.uit.vaccinemanagement.model.Vaccine;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class VaccineManagementPanel extends ManagementPanel<Vaccine> {

    private static final String[] COLUMN_NAMES = {
        "Mã Vaccine", "Tên Vaccine", "Mã NSX", "Số lô", "NSX", "HSD",
        "SL Tổng", "SL Còn", "Giá nhập", "Giá bán", "Mô tả"
    };

    public VaccineManagementPanel(AdminController controller) {
        super(controller, "Danh sách Vaccine");
        initialize();
    }

    @Override
    protected String[] getColumnNames() {
        return COLUMN_NAMES;
    }

    @Override
    protected void setupTable() {
        DefaultTableModel model = new DefaultTableModel(getColumnNames(), 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(model);

        // Set column widths
        int[] widths = {80, 150, 80, 80, 100, 100, 80, 80, 100, 100, 200};
        for (int i = 0; i < widths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }

        // Center align numeric and date columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // IDs and numeric columns
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);

        // Dates
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);

        // Currency right align
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        table.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(9).setCellRenderer(rightRenderer);
    }

    @Override
    protected void loadTableData(int page) {
        try {
            List<Vaccine> vaccines = controller.getVaccinePage(page, PAGE_SIZE);
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            for (Vaccine vaccine : vaccines) {
                model.addRow(new Object[]{
                    vaccine.getMaVaccine(),
                    vaccine.getTenVaccine(),
                    vaccine.getMaNhaSX(),
                    vaccine.getSoLo(),
                    vaccine.getNgaySX(),
                    vaccine.getNgayHetHan(),
                    vaccine.getTongSL(),
                    vaccine.getSlConLai(),
                    vaccine.getGiaNhap(),
                    vaccine.getGiaBan(),
                    vaccine.getMoTa()
                });
            }

            updatePagination();
        } catch (SQLException e) {
            handleError("Error loading vaccine data", e);
        }
    }

    @Override
    protected void handleAdd() {
        controller.showVaccineEditDialog(null, () -> loadTableData(currentPage));
    }

    @Override
    protected void handleEdit(int selectedRow) {
        if (selectedRow != -1) {
            String vaccineId = table.getValueAt(selectedRow, 0).toString();
            controller.showVaccineEditDialog(vaccineId, () -> loadTableData(currentPage));
        }
    }

    @Override
    protected void handleDelete(int selectedRow) {
        if (selectedRow != -1) {
            String vaccineId = table.getValueAt(selectedRow, 0).toString();
            if (confirmDelete()) {
                try {
                    if (controller.deleteVaccine(vaccineId)) {
                        loadTableData(currentPage);
                    }
                } catch (SQLException e) {
                    handleError("Error deleting vaccine", e);
                }
            }
        }
    }

    @Override
    protected int getTotalRecords() throws SQLException {
        return controller.getTotalVaccines();
    }

// Content removed - duplicate methods
    protected void handleDelete() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String maVaccine = (String) table.getValueAt(selectedRow, 0);
            if (confirmDelete()) {
                try {
                    if (controller.deleteVaccine(maVaccine)) {
                        loadTableData(currentPage);
                    }
                } catch (SQLException e) {
                    handleError("Error deleting vaccine", e);
                }
            }
        }
    }
}
