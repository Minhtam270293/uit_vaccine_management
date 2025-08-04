package com.uit.vaccinemanagement.model;

import com.uit.vaccinemanagement.util.Role;

public class NguoiDung {

    private String maNguoiDung;
    private String hoTen;
    private String tenDangNhap;
    private String email;
    private String matKhau;
    private Role vaiTro;
    private java.sql.Date ngaySinh;
    private String gioiTinh;
    private java.sql.Timestamp ngayTao;

    // Getters and setters
    public NguoiDung() {
    }

    public NguoiDung(String maNguoiDung, String hoTen, String tenDangNhap, String email, String matKhau, Role vaiTro, java.sql.Date ngaySinh, String gioiTinh, java.sql.Timestamp ngayTao) {
        this.maNguoiDung = maNguoiDung;
        this.hoTen = hoTen;
        this.tenDangNhap = tenDangNhap;
        this.email = email;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.ngayTao = ngayTao;
    }

    public String getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(String maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public Role getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(Role vaiTro) {
        this.vaiTro = vaiTro;
    }

    public java.sql.Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(java.sql.Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public java.sql.Timestamp getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(java.sql.Timestamp ngayTao) {
        this.ngayTao = ngayTao;
    }
}
