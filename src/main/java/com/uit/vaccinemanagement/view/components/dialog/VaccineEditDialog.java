package com.uit.vaccinemanagement.view.components.dialog;

import com.uit.vaccinemanagement.model.Vaccine;
import javax.swing.*;
import java.sql.Date;

public class VaccineEditDialog extends BaseEditDialog {
    private final Vaccine vaccine;
    private JTextField tfMaVaccine;
    private JTextField tfTenVaccine;
    private JTextField tfHinhAnhUrl;
    private JTextField tfSoLo;
    private JTextField tfNgaySX;
    private JTextField tfNgayHetHan;
    private JTextField tfTongSL;
    private JTextField tfSLConLai;
    private JTextField tfGiaNhap;
    private JTextField tfGiaBan;
    private JTextField tfMoTa;
    private JTextField tfMaBenh;
    private JTextField tfMaNhaSX;

    public VaccineEditDialog(JFrame parent, Vaccine vaccine) {
        super(parent, "Edit Vaccine");
        this.vaccine = vaccine;
        initializeFields();
        loadVaccineData();
        setSize(400, 600); // Larger size for more fields
    }

    private void initializeFields() {
        tfMaVaccine = new JTextField();
        tfTenVaccine = new JTextField();
        tfHinhAnhUrl = new JTextField();
        tfSoLo = new JTextField();
        tfNgaySX = new JTextField();
        tfNgayHetHan = new JTextField();
        tfTongSL = new JTextField();
        tfSLConLai = new JTextField();
        tfGiaNhap = new JTextField();
        tfGiaBan = new JTextField();
        tfMoTa = new JTextField();
        tfMaBenh = new JTextField();
        tfMaNhaSX = new JTextField();

        addFormField("Mã vaccine:", tfMaVaccine);
        addFormField("Tên vaccine:", tfTenVaccine);
        addFormField("Hình ảnh URL:", tfHinhAnhUrl);
        addFormField("Số lô:", tfSoLo);
        addFormField("Ngày SX (yyyy-mm-dd):", tfNgaySX);
        addFormField("Ngày hết hạn (yyyy-mm-dd):", tfNgayHetHan);
        addFormField("Tổng SL:", tfTongSL);
        addFormField("SL còn lại:", tfSLConLai);
        addFormField("Giá nhập:", tfGiaNhap);
        addFormField("Giá bán:", tfGiaBan);
        addFormField("Mô tả:", tfMoTa);
        addFormField("Mã bệnh:", tfMaBenh);
        addFormField("Mã NSX:", tfMaNhaSX);
    }

    private void loadVaccineData() {
        tfMaVaccine.setText(vaccine.getMaVaccine());
        tfTenVaccine.setText(vaccine.getTenVaccine());
        tfHinhAnhUrl.setText(vaccine.getHinhAnhUrl());
        tfSoLo.setText(vaccine.getSoLo());
        tfNgaySX.setText(vaccine.getNgaySX() != null ? vaccine.getNgaySX().toString() : "");
        tfNgayHetHan.setText(vaccine.getNgayHetHan() != null ? vaccine.getNgayHetHan().toString() : "");
        tfTongSL.setText(String.valueOf(vaccine.getTongSL()));
        tfSLConLai.setText(String.valueOf(vaccine.getSlConLai()));
        tfGiaNhap.setText(String.valueOf(vaccine.getGiaNhap()));
        tfGiaBan.setText(String.valueOf(vaccine.getGiaBan()));
        tfMoTa.setText(vaccine.getMoTa());
        tfMaBenh.setText(vaccine.getMaBenh());
        tfMaNhaSX.setText(vaccine.getMaNhaSX());
    }

    @Override
    protected boolean validateInput() {
        if (tfTenVaccine.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên vaccine không được để trống");
            return false;
        }
        try {
            if (!tfNgaySX.getText().trim().isEmpty()) {
                Date.valueOf(tfNgaySX.getText().trim());
            }
            if (!tfNgayHetHan.getText().trim().isEmpty()) {
                Date.valueOf(tfNgayHetHan.getText().trim());
            }
            if (!tfTongSL.getText().trim().isEmpty()) {
                Integer.parseInt(tfTongSL.getText().trim());
            }
            if (!tfGiaNhap.getText().trim().isEmpty()) {
                Double.parseDouble(tfGiaNhap.getText().trim());
            }
            if (!tfGiaBan.getText().trim().isEmpty()) {
                Double.parseDouble(tfGiaBan.getText().trim());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi định dạng dữ liệu: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    protected boolean saveData() {
        try {
            vaccine.setMaVaccine(tfMaVaccine.getText().trim());
            vaccine.setTenVaccine(tfTenVaccine.getText().trim());
            vaccine.setHinhAnhUrl(tfHinhAnhUrl.getText().trim());
            vaccine.setSoLo(tfSoLo.getText().trim());
            vaccine.setNgaySX(tfNgaySX.getText().isEmpty() ? null : 
                            Date.valueOf(tfNgaySX.getText().trim()));
            vaccine.setNgayHetHan(tfNgayHetHan.getText().isEmpty() ? null : 
                                Date.valueOf(tfNgayHetHan.getText().trim()));
            vaccine.setTongSL(tfTongSL.getText().isEmpty() ? 0 : 
                            Integer.parseInt(tfTongSL.getText().trim()));
            vaccine.setSlConLai(tfSLConLai.getText().isEmpty() ? 0 : 
                              Integer.parseInt(tfSLConLai.getText().trim()));
            vaccine.setGiaNhap(tfGiaNhap.getText().isEmpty() ? 0 : 
                             Double.parseDouble(tfGiaNhap.getText().trim()));
            vaccine.setGiaBan(tfGiaBan.getText().isEmpty() ? 0 : 
                            Double.parseDouble(tfGiaBan.getText().trim()));
            vaccine.setMoTa(tfMoTa.getText().trim());
            vaccine.setMaBenh(tfMaBenh.getText().trim());
            vaccine.setMaNhaSX(tfMaNhaSX.getText().trim());
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu dữ liệu: " + e.getMessage());
            return false;
        }
    }

    public Vaccine getVaccine() {
        return vaccine;
    }
}
