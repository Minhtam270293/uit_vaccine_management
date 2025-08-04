package com.uit.vaccinemanagement.model;

public class ThongTinMua {

    private String maGiaoDich;
    private String maVaccine;
    private String maKhach;
    private int soLuongMua;
    private double tongTien;
    private java.sql.Timestamp ngayGiaoDich;

    public ThongTinMua() {
    }

    public ThongTinMua(String maGiaoDich, String maVaccine, String maKhach, int soLuongMua, double tongTien, java.sql.Timestamp ngayGiaoDich) {
        this.maGiaoDich = maGiaoDich;
        this.maVaccine = maVaccine;
        this.maKhach = maKhach;
        this.soLuongMua = soLuongMua;
        this.tongTien = tongTien;
        this.ngayGiaoDich = ngayGiaoDich;
    }

    public String getMaGiaoDich() {
        return maGiaoDich;
    }

    public void setMaGiaoDich(String maGiaoDich) {
        this.maGiaoDich = maGiaoDich;
    }

    public String getMaVaccine() {
        return maVaccine;
    }

    public void setMaVaccine(String maVaccine) {
        this.maVaccine = maVaccine;
    }

    public String getMaKhach() {
        return maKhach;
    }

    public void setMaKhach(String maKhach) {
        this.maKhach = maKhach;
    }

    public int getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(int soLuongMua) {
        this.soLuongMua = soLuongMua;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public java.sql.Timestamp getNgayGiaoDich() {
        return ngayGiaoDich;
    }

    public void setNgayGiaoDich(java.sql.Timestamp ngayGiaoDich) {
        this.ngayGiaoDich = ngayGiaoDich;
    }
}
