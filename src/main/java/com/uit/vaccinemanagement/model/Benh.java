package com.uit.vaccinemanagement.model;

public class Benh {

    private String maBenh;
    private String tenBenh;
    private String moTa;
    private java.sql.Timestamp ngayTao;

    public Benh() {
    }

    public Benh(String maBenh, String tenBenh, String moTa, java.sql.Timestamp ngayTao) {
        this.maBenh = maBenh;
        this.tenBenh = tenBenh;
        this.moTa = moTa;
        this.ngayTao = ngayTao;
    }

    public String getMaBenh() {
        return maBenh;
    }

    public void setMaBenh(String maBenh) {
        this.maBenh = maBenh;
    }

    public String getTenBenh() {
        return tenBenh;
    }

    public void setTenBenh(String tenBenh) {
        this.tenBenh = tenBenh;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public java.sql.Timestamp getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(java.sql.Timestamp ngayTao) {
        this.ngayTao = ngayTao;
    }
}
