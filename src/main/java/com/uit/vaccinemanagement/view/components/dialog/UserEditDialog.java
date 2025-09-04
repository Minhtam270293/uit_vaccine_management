package com.uit.vaccinemanagement.view.components.dialog;

import com.uit.vaccinemanagement.model.NguoiDung;
import com.uit.vaccinemanagement.util.Role;

import javax.swing.*;
import java.sql.Date;

public class UserEditDialog extends BaseEditDialog {
    private final NguoiDung user;
    private JTextField tfHoTen;
    private JTextField tfEmail;
    private JTextField tfTenDangNhap;
    private JTextField tfNgaySinh;
    private JComboBox<String> cbVaiTro;

    public UserEditDialog(JFrame parent, NguoiDung user) {
        super(parent, "Edit User");
        this.user = user;
        initializeFields();
        loadUserData();
    }

    private void initializeFields() {
        tfHoTen = new JTextField();
        tfEmail = new JTextField();
        tfTenDangNhap = new JTextField();
        tfNgaySinh = new JTextField();
        cbVaiTro = new JComboBox<>(new String[]{"ADMIN", "BACSI", "KHACH"});

        addFormField("Họ tên:", tfHoTen);
        addFormField("Email:", tfEmail);
        addFormField("Tên đăng nhập:", tfTenDangNhap);
        addFormField("Ngày sinh (yyyy-mm-dd):", tfNgaySinh);
        addFormField("Vai trò:", cbVaiTro);

        pack(); // Resize dialog to fit all components
    }

    private void loadUserData() {
        tfHoTen.setText(user.getHoTen());
        tfEmail.setText(user.getEmail());
        tfTenDangNhap.setText(user.getTenDangNhap());
        tfNgaySinh.setText(user.getNgaySinh() != null ? user.getNgaySinh().toString() : "");
        cbVaiTro.setSelectedItem(user.getVaiTro().name());
    }

    @Override
    protected boolean validateInput() {
        if (tfHoTen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Họ tên không được để trống");
            return false;
        }
        if (tfEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email không được để trống");
            return false;
        }
        if (tfTenDangNhap.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập không được để trống");
            return false;
        }
        try {
            if (!tfNgaySinh.getText().trim().isEmpty()) {
                Date.valueOf(tfNgaySinh.getText().trim());
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ (yyyy-mm-dd)");
            return false;
        }
        return true;
    }

    @Override
    protected boolean saveData() {
        try {
            user.setHoTen(tfHoTen.getText().trim());
            user.setEmail(tfEmail.getText().trim());
            user.setTenDangNhap(tfTenDangNhap.getText().trim());
            user.setNgaySinh(tfNgaySinh.getText().isEmpty() ? null : 
                            Date.valueOf(tfNgaySinh.getText().trim()));
            user.setVaiTro(Role.valueOf(cbVaiTro.getSelectedItem().toString()));
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu dữ liệu: " + e.getMessage());
            return false;
        }
    }

    public NguoiDung getUser() {
        return user;
    }
}
