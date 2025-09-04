package com.uit.vaccinemanagement.view.components.pages;

import com.uit.vaccinemanagement.controller.AdminController;
import com.uit.vaccinemanagement.model.NguoiDung;

public class UserProfilePanel extends ManagementPanel<NguoiDung> {
    private final NguoiDung user;

    public UserProfilePanel(AdminController controller, NguoiDung user) {
        super(controller, "Thông tin cá nhân");
        this.user = user;
        initialize();
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"Mã ND", "Họ Tên", "Email", "Ngày sinh", "Giới tính", "Vai trò"};
    }

    @Override
    protected void setupTable() {
        // TODO: Implement user profile view
    }

    @Override
    protected void loadTableData(int page) {
        // TODO: Load user profile data
    }

    @Override
    protected void handleAdd() {
        // Not applicable for profile view
    }

    @Override
    protected void handleEdit(int selectedRow) {
        // TODO: Implement edit profile
    }

    @Override
    protected void handleDelete(int selectedRow) {
        // Not applicable for profile view
    }

    @Override
    protected int getTotalRecords() {
        return 1; // Only one record for profile
    }
}
