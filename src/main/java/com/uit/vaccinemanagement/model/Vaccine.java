package com.uit.vaccinemanagement.model;

public class Vaccine {

    private String maVaccine;
    private String tenVaccine;
    private String hinhAnhUrl;
    private String soLo;
    private java.sql.Date ngaySX;
    private java.sql.Date ngayHetHan;
    private int tongSL;
    private int slConLai;
    private double giaNhap;
    private double giaBan;
    private String moTa;
    private String maBenh;
    private String maNhaSX;
    private java.sql.Timestamp ngayTao;

    public Vaccine() {
    }

    public Vaccine(String maVaccine, String tenVaccine, String hinhAnhUrl, String soLo, java.sql.Date ngaySX, java.sql.Date ngayHetHan, int tongSL, int slConLai, double giaNhap, double giaBan, String moTa, String maBenh, String maNhaSX, java.sql.Timestamp ngayTao) {
        this.maVaccine = maVaccine;
        this.tenVaccine = tenVaccine;
        this.hinhAnhUrl = hinhAnhUrl;
        this.soLo = soLo;
        this.ngaySX = ngaySX;
        this.ngayHetHan = ngayHetHan;
        this.tongSL = tongSL;
        this.slConLai = slConLai;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.moTa = moTa;
        this.maBenh = maBenh;
        this.maNhaSX = maNhaSX;
        this.ngayTao = ngayTao;
    }

    public String getMaVaccine() {
        return maVaccine;
    }

    public void setMaVaccine(String maVaccine) {
        this.maVaccine = maVaccine;
    }

    public String getTenVaccine() {
        return tenVaccine;
    }

    public void setTenVaccine(String tenVaccine) {
        this.tenVaccine = tenVaccine;
    }

    public String getHinhAnhUrl() {
        return hinhAnhUrl;
    }

    public void setHinhAnhUrl(String hinhAnhUrl) {
        this.hinhAnhUrl = hinhAnhUrl;
    }

    public String getSoLo() {
        return soLo;
    }

    public void setSoLo(String soLo) {
        this.soLo = soLo;
    }

    public java.sql.Date getNgaySX() {
        return ngaySX;
    }

    public void setNgaySX(java.sql.Date ngaySX) {
        this.ngaySX = ngaySX;
    }

    public java.sql.Date getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(java.sql.Date ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }

    public int getTongSL() {
        return tongSL;
    }

    public void setTongSL(int tongSL) {
        this.tongSL = tongSL;
    }

    public int getSlConLai() {
        return slConLai;
    }

    public void setSlConLai(int slConLai) {
        this.slConLai = slConLai;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getMaBenh() {
        return maBenh;
    }

    public void setMaBenh(String maBenh) {
        this.maBenh = maBenh;
    }

    public String getMaNhaSX() {
        return maNhaSX;
    }

    public void setMaNhaSX(String maNhaSX) {
        this.maNhaSX = maNhaSX;
    }

    public java.sql.Timestamp getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(java.sql.Timestamp ngayTao) {
        this.ngayTao = ngayTao;
    }
}
