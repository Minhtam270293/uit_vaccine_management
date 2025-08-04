package com.uit.vaccinemanagement.model;

public class TiemChung {

    private String maTiemChung;
    private String maVaccine;
    private String maBacSi;
    private String maKhach;
    private java.sql.Date ngayChiDinh;
    private java.sql.Date ngayTiem;
    private String trangThaiTiem;
    private String ghiChu;

    public TiemChung() {
    }

    public TiemChung(String maTiemChung, String maVaccine, String maBacSi, String maKhach, java.sql.Date ngayChiDinh, java.sql.Date ngayTiem, String trangThaiTiem, String ghiChu) {
        this.maTiemChung = maTiemChung;
        this.maVaccine = maVaccine;
        this.maBacSi = maBacSi;
        this.maKhach = maKhach;
        this.ngayChiDinh = ngayChiDinh;
        this.ngayTiem = ngayTiem;
        this.trangThaiTiem = trangThaiTiem;
        this.ghiChu = ghiChu;
    }

    public String getMaTiemChung() {
        return maTiemChung;
    }

    public void setMaTiemChung(String maTiemChung) {
        this.maTiemChung = maTiemChung;
    }

    public String getMaVaccine() {
        return maVaccine;
    }

    public void setMaVaccine(String maVaccine) {
        this.maVaccine = maVaccine;
    }

    public String getMaBacSi() {
        return maBacSi;
    }

    public void setMaBacSi(String maBacSi) {
        this.maBacSi = maBacSi;
    }

    public String getMaKhach() {
        return maKhach;
    }

    public void setMaKhach(String maKhach) {
        this.maKhach = maKhach;
    }

    public java.sql.Date getNgayChiDinh() {
        return ngayChiDinh;
    }

    public void setNgayChiDinh(java.sql.Date ngayChiDinh) {
        this.ngayChiDinh = ngayChiDinh;
    }

    public java.sql.Date getNgayTiem() {
        return ngayTiem;
    }

    public void setNgayTiem(java.sql.Date ngayTiem) {
        this.ngayTiem = ngayTiem;
    }

    public String getTrangThaiTiem() {
        return trangThaiTiem;
    }

    public void setTrangThaiTiem(String trangThaiTiem) {
        this.trangThaiTiem = trangThaiTiem;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
