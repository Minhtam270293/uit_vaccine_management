package com.uit.vaccinemanagement.view.components.dialog;

import com.uit.vaccinemanagement.model.NhaSanXuat;
import javax.swing.*;
import java.sql.Timestamp;

public class NhaSanXuatEditDialog extends BaseEditDialog {
    private final NhaSanXuat nhaSanXuat;
    private JTextField tfMaNhaSX;
    private JTextField tfTenNhaSX;
    private JTextField tfQuocGia;
    private JTextField tfNgayTao;

    public NhaSanXuatEditDialog(JFrame parent, NhaSanXuat nhaSanXuat) {
        super(parent, "Edit Nhà Sản Xuất");
        this.nhaSanXuat = nhaSanXuat;
        initializeFields();
        loadNhaSanXuatData();
    }

    private void initializeFields() {
        tfMaNhaSX = new JTextField();
        tfTenNhaSX = new JTextField();
        tfQuocGia = new JTextField();
        tfNgayTao = new JTextField();

        // Set Mã NSX field to read-only
        tfMaNhaSX.setEditable(false);

        addFormField("Mã NSX:", tfMaNhaSX);
        addFormField("Tên nhà sản xuất:", tfTenNhaSX);
        addFormField("Quốc gia:", tfQuocGia);
        addFormField("Ngày tạo (yyyy-mm-dd hh:mm:ss):", tfNgayTao);

        // Adjust dialog size for fewer fields
        setSize(400, 250);
    }

    private void loadNhaSanXuatData() {
        tfMaNhaSX.setText(nhaSanXuat.getMaNhaSX());
        tfTenNhaSX.setText(nhaSanXuat.getTenNhaSX());
        tfQuocGia.setText(nhaSanXuat.getQuocGia());
        tfNgayTao.setText(nhaSanXuat.getNgayTao() != null ? nhaSanXuat.getNgayTao().toString() : "");
    }

    @Override
    protected boolean validateInput() {
        if (tfTenNhaSX.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên nhà sản xuất không được để trống");
            return false;
        }
        if (tfQuocGia.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Quốc gia không được để trống");
            return false;
        }
        try {
            if (!tfNgayTao.getText().trim().isEmpty()) {
                Timestamp.valueOf(tfNgayTao.getText().trim());
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, 
                "Ngày tạo không hợp lệ (định dạng: yyyy-mm-dd hh:mm:ss)");
            return false;
        }
        return true;
    }

    @Override
    protected boolean saveData() {
        try {
            nhaSanXuat.setTenNhaSX(tfTenNhaSX.getText().trim());
            nhaSanXuat.setQuocGia(tfQuocGia.getText().trim());
            nhaSanXuat.setNgayTao(tfNgayTao.getText().isEmpty() ? null : 
                                 Timestamp.valueOf(tfNgayTao.getText().trim()));
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu dữ liệu: " + e.getMessage());
            return false;
        }
    }

    public NhaSanXuat getNhaSanXuat() {
        return nhaSanXuat;
    }
}
