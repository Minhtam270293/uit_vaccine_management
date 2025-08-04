package com.uit.vaccinemanagement.model;

public class NhaSanXuat {

    private String maNhaSX;
    private String tenNhaSX;
    private String quocGia;
    private java.sql.Timestamp ngayTao;

    public NhaSanXuat() {
    }

    public NhaSanXuat(String maNhaSX, String tenNhaSX, String quocGia, java.sql.Timestamp ngayTao) {
        this.maNhaSX = maNhaSX;
        this.tenNhaSX = tenNhaSX;
        this.quocGia = quocGia;
        this.ngayTao = ngayTao;
    }

    public String getMaNhaSX() {
        return maNhaSX;
    }

    public void setMaNhaSX(String maNhaSX) {
        this.maNhaSX = maNhaSX;
    }

    public String getTenNhaSX() {
        return tenNhaSX;
    }

    public void setTenNhaSX(String tenNhaSX) {
        this.tenNhaSX = tenNhaSX;
    }

    public String getQuocGia() {
        return quocGia;
    }

    public void setQuocGia(String quocGia) {
        this.quocGia = quocGia;
    }

    public java.sql.Timestamp getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(java.sql.Timestamp ngayTao) {
        this.ngayTao = ngayTao;
    }
}
