package com.uit.vaccinemanagement.controller;

import com.uit.vaccinemanagement.model.*;
import com.uit.vaccinemanagement.dao.*;
import java.sql.SQLException;
import java.util.*;

public class AdminController {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 20;

    private final NguoiDungDAO nguoiDungDAO;
    private final VaccineDAO vaccineDAO;
    private final NhaSanXuatDAO nhaSanXuatDAO;

    public AdminController() {
        this.nguoiDungDAO = new NguoiDungDAO();
        this.vaccineDAO = new VaccineDAO();
        this.nhaSanXuatDAO = new NhaSanXuatDAO();
    }

    // User Management Methods
    public List<NguoiDung> getNguoiDungPage(int page, int pageSize) {
        // Business logic: Handle pagination validation
        if (page <= 0) {
            page = 1;
        }
        if (pageSize <= 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        return nguoiDungDAO.getNguoiDungPage(page, pageSize);
    }

    // NhaSanXuat Management Methods
    public List<NhaSanXuat> getNhaSanXuatPage(int page, int pageSize) {
        // Business logic: Handle pagination validation
        if (page <= 0) {
            page = DEFAULT_PAGE;
        }
        if (pageSize <= 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        return nhaSanXuatDAO.getNhaSanXuatPage(page, pageSize);
    }

    // Default page handling
    public List<NhaSanXuat> getDefaultNhaSanXuatPage() {
        return getNhaSanXuatPage(DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
    }

    public int getNhaSanXuatCount() {
        return nhaSanXuatDAO.getNhaSanXuatCount();
    }

    // Vaccine Management Methods
    public List<Vaccine> getVaccinePage(int page, int pageSize) {
        // Business logic: Handle pagination validation
        if (page <= 0) page = DEFAULT_PAGE;
        if (pageSize <= 0) pageSize = DEFAULT_PAGE_SIZE;
        
        return vaccineDAO.getVaccinePage(page, pageSize);
    }

    public List<Vaccine> getDefaultVaccinePage() {
        return getVaccinePage(DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
    }

    public int getVaccineCount() {
        return vaccineDAO.getVaccineCount();
    }

    // Default page handling moved from DAO
    public List<NguoiDung> getDefaultNguoiDungPage() {
        return getNguoiDungPage(DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
    }

    // Get all users with basic validation
    public List<NguoiDung> getAllNguoiDung() {
        // Retrieve all users from DAO
        List<NguoiDung> users = nguoiDungDAO.getAllNguoiDung();

        // Basic validation and filtering if needed
        return users.stream()
                .filter(u -> u != null && u.getMaNguoiDung() != null)
                .toList();
    }

    // User validation and error handling
    public boolean addNguoiDung(NguoiDung nguoiDung) {
        // Business validation
        if (nguoiDung == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (nguoiDung.getHoTen() == null || nguoiDung.getHoTen().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (nguoiDung.getTenDangNhap() == null || nguoiDung.getTenDangNhap().trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }
        if (nguoiDung.getEmail() == null || nguoiDung.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (nguoiDung.getMatKhau() == null || nguoiDung.getMatKhau().trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (nguoiDung.getVaiTro() == null) {
            throw new IllegalArgumentException("Role is required");
        }
        if (nguoiDung.getNgaySinh() == null) {
            throw new IllegalArgumentException("Birth date is required");
        }
        if (nguoiDung.getGioiTinh() == null || nguoiDung.getGioiTinh().trim().isEmpty()) {
            throw new IllegalArgumentException("Gender is required");
        }
        if (nguoiDung.getNgayTao() == null) {
            nguoiDung.setNgayTao(new java.sql.Timestamp(System.currentTimeMillis()));
        }

        // Additional validations for data format/quality
        if (!nguoiDung.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (!nguoiDung.getGioiTinh().matches("^(Nam|Nữ)$")) {
            throw new IllegalArgumentException("Gender must be either 'Nam' or 'Nữ'");
        }
        if (nguoiDung.getNgaySinh().after(new java.sql.Date(System.currentTimeMillis()))) {
            throw new IllegalArgumentException("Birth date cannot be in the future");
        }

        return nguoiDungDAO.addNguoiDung(nguoiDung);
    }

    public boolean updateNguoiDung(NguoiDung nguoiDung) {
        // Business validation
        if (nguoiDung == null || nguoiDung.getMaNguoiDung() == null) {
            throw new IllegalArgumentException("Invalid user data");
        }
        if (nguoiDung.getHoTen() == null || nguoiDung.getHoTen().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }

        return nguoiDungDAO.updateNguoiDung(nguoiDung);
    }

    public boolean deleteNguoiDung(String maNguoiDung) {
        // Business validation
        if (maNguoiDung == null || maNguoiDung.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID is required");
        }

        return nguoiDungDAO.deleteNguoiDung(maNguoiDung);
    }

    // Vaccine Management Methods
    public List<Vaccine> getAllVaccine() {
        // Retrieve all vaccines from DAO
        List<Vaccine> vaccines = vaccineDAO.getAllVaccine();

        // Basic validation and filtering
        return vaccines.stream()
                .filter(v -> v != null && v.getMaVaccine() != null)
                .toList();
    }

    public List<Object[]> getAllVaccineAsObjectArray() {
        // Business validation if needed
        return vaccineDAO.getAllVaccineAsObjectArray();
    }

    public boolean addVaccine(Vaccine vaccine) {
        // Business validation
        if (vaccine == null) {
            throw new IllegalArgumentException("Vaccine cannot be null");
        }
        if (vaccine.getTenVaccine() == null || vaccine.getTenVaccine().trim().isEmpty()) {
            throw new IllegalArgumentException("Vaccine name is required");
        }
        if (vaccine.getNgayHetHan() != null && vaccine.getNgaySX() != null
                && vaccine.getNgayHetHan().before(vaccine.getNgaySX())) {
            throw new IllegalArgumentException("Expiry date must be after manufacture date");
        }
        if (vaccine.getTongSL() < 0 || vaccine.getSlConLai() < 0) {
            throw new IllegalArgumentException("Quantities cannot be negative");
        }
        if (vaccine.getSlConLai() > vaccine.getTongSL()) {
            throw new IllegalArgumentException("Remaining quantity cannot exceed total quantity");
        }
        if (vaccine.getGiaNhap() < 0 || vaccine.getGiaBan() < 0) {
            throw new IllegalArgumentException("Prices cannot be negative");
        }

        return vaccineDAO.addVaccine(vaccine);
    }

    public boolean updateVaccine(Vaccine vaccine) {
        // Business validation
        if (vaccine == null || vaccine.getMaVaccine() == null) {
            throw new IllegalArgumentException("Invalid vaccine data");
        }
        if (vaccine.getTenVaccine() == null || vaccine.getTenVaccine().trim().isEmpty()) {
            throw new IllegalArgumentException("Vaccine name is required");
        }
        if (vaccine.getNgayHetHan() != null && vaccine.getNgaySX() != null
                && vaccine.getNgayHetHan().before(vaccine.getNgaySX())) {
            throw new IllegalArgumentException("Expiry date must be after manufacture date");
        }
        if (vaccine.getTongSL() < 0 || vaccine.getSlConLai() < 0) {
            throw new IllegalArgumentException("Quantities cannot be negative");
        }
        if (vaccine.getSlConLai() > vaccine.getTongSL()) {
            throw new IllegalArgumentException("Remaining quantity cannot exceed total quantity");
        }
        if (vaccine.getGiaNhap() < 0 || vaccine.getGiaBan() < 0) {
            throw new IllegalArgumentException("Prices cannot be negative");
        }

        return vaccineDAO.updateVaccine(vaccine);
    }

    public boolean deleteVaccine(String maVaccine) {
        // Business validation
        if (maVaccine == null || maVaccine.trim().isEmpty()) {
            throw new IllegalArgumentException("Vaccine ID is required");
        }

        return vaccineDAO.deleteVaccine(maVaccine);
    }

    // NhaSanXuat Management Methods
    public List<NhaSanXuat> getAllNhaSanXuat() {
        // Retrieve all manufacturers from DAO
        List<NhaSanXuat> manufacturers = nhaSanXuatDAO.getAllNhaSanXuat();

        // Basic validation and filtering
        return manufacturers.stream()
                .filter(n -> n != null && n.getMaNhaSX() != null)
                .toList();
    }

    public List<Object[]> getAllNhaSanXuatAsObjectArray() {
        // Business validation if needed
        return nhaSanXuatDAO.getAllNhaSanXuatAsObjectArray();
    }

    public boolean addNhaSanXuat(NhaSanXuat nhaSanXuat) {
        // Business validation
        if (nhaSanXuat == null) {
            throw new IllegalArgumentException("Manufacturer cannot be null");
        }
        if (nhaSanXuat.getTenNhaSX() == null || nhaSanXuat.getTenNhaSX().trim().isEmpty()) {
            throw new IllegalArgumentException("Manufacturer name is required");
        }
        if (nhaSanXuat.getQuocGia() == null || nhaSanXuat.getQuocGia().trim().isEmpty()) {
            throw new IllegalArgumentException("Country is required");
        }

        return nhaSanXuatDAO.addNhaSanXuat(nhaSanXuat);
    }

    public boolean updateNhaSanXuat(NhaSanXuat nhaSanXuat) {
        // Business validation
        if (nhaSanXuat == null || nhaSanXuat.getMaNhaSX() == null) {
            throw new IllegalArgumentException("Invalid manufacturer data");
        }
        if (nhaSanXuat.getTenNhaSX() == null || nhaSanXuat.getTenNhaSX().trim().isEmpty()) {
            throw new IllegalArgumentException("Manufacturer name is required");
        }
        if (nhaSanXuat.getQuocGia() == null || nhaSanXuat.getQuocGia().trim().isEmpty()) {
            throw new IllegalArgumentException("Country is required");
        }

        return nhaSanXuatDAO.updateNhaSanXuat(nhaSanXuat);
    }

    public boolean deleteNhaSanXuat(String maNhaSX) {
        // Business validation
        if (maNhaSX == null || maNhaSX.trim().isEmpty()) {
            throw new IllegalArgumentException("Manufacturer ID is required");
        }

        // Additional business logic: Check if any vaccines are using this manufacturer
        List<Vaccine> relatedVaccines = getAllVaccine().stream()
                .filter(v -> maNhaSX.equals(v.getMaNhaSX()))
                .toList();
        if (!relatedVaccines.isEmpty()) {
            throw new IllegalArgumentException("Cannot delete manufacturer: There are vaccines associated with it");
        }

        return nhaSanXuatDAO.deleteNhaSanXuat(maNhaSX);
    }
}
