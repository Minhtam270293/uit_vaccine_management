package com.uit.vaccinemanagement.controller;

import com.uit.vaccinemanagement.dao.TiemChungDAO;
import com.uit.vaccinemanagement.dao.NguoiDungDAO;
import com.uit.vaccinemanagement.dao.VaccineDAO;
import com.uit.vaccinemanagement.model.TiemChung;
import com.uit.vaccinemanagement.model.NguoiDung;
import com.uit.vaccinemanagement.model.Vaccine;
import com.uit.vaccinemanagement.util.Role;
import java.util.*;

public class BacSiController {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 20;

    private final TiemChungDAO tiemChungDAO;
    private final NguoiDungDAO nguoiDungDAO;
    private final VaccineDAO vaccineDAO;

    public BacSiController() {
        this.tiemChungDAO = new TiemChungDAO();
        this.nguoiDungDAO = new NguoiDungDAO();
        this.vaccineDAO = new VaccineDAO();
    }

    // ===== Vaccination Records Management =====
    public List<Object[]> getTiemChungByBacSiPaginated(String maBacSi, int offset, int limit) {
        // Validate parameters
        if (offset < 0) {
            offset = 0;
        }
        if (limit <= 0) {
            limit = DEFAULT_PAGE_SIZE;
        }

        return tiemChungDAO.getByBacSiPaginated(maBacSi, offset, limit);
    }

    public int getTotalTiemChungByBacSi(String maBacSi) {
        return tiemChungDAO.getTotalByBacSi(maBacSi);
    }

    public List<Object[]> searchTiemChungByBacSi(String maBacSi, String searchTerm, int offset, int limit) {
        // Validate parameters
        if (offset < 0) {
            offset = 0;
        }
        if (limit <= 0) {
            limit = DEFAULT_PAGE_SIZE;
        }
        if (searchTerm == null) {
            searchTerm = "";
        }

        return tiemChungDAO.searchByBacSi(maBacSi, searchTerm, offset, limit);
    }

    public int getTotalSearchTiemChungByBacSi(String maBacSi, String searchTerm) {
        if (searchTerm == null) {
            searchTerm = "";
        }
        return tiemChungDAO.getTotalSearchByBacSi(maBacSi, searchTerm);
    }

    // ===== Customer Management =====
    public List<NguoiDung> getAllCustomers() {
        return nguoiDungDAO.getAllKhach();
    }

    public List<Object[]> getDefaultTiemChungByBacSi(String maBacSi) {
        if (maBacSi == null || maBacSi.trim().isEmpty()) {
            throw new IllegalArgumentException("Doctor ID cannot be null or empty");
        }
        return getTiemChungByBacSiPaginated(maBacSi, DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
    }

    public int getTiemChungCountByBacSi(String maBacSi) {
        if (maBacSi == null || maBacSi.trim().isEmpty()) {
            throw new IllegalArgumentException("Doctor ID cannot be null or empty");
        }
        return tiemChungDAO.getCountByMaBacSi(maBacSi);
    }

    // ===== Customer Management =====
    public List<NguoiDung> getKhachHangPage(int page, int pageSize) {
        return nguoiDungDAO.getKhachHangPage(
                Math.max(1, page),
                Math.max(1, pageSize)
        );
    }

    public List<NguoiDung> getDefaultKhachHangPage() {
        return nguoiDungDAO.getKhachHangPage();
    }

    public int getKhachHangCount() {
        return nguoiDungDAO.getKhachHangCount();
    }

    public Object[] getTiemChungById(String maTiemChung) {
        if (maTiemChung == null || maTiemChung.trim().isEmpty()) {
            throw new IllegalArgumentException("Vaccination ID cannot be null or empty");
        }
        return tiemChungDAO.getTiemChungById(maTiemChung.trim());
    }

    public boolean deleteTiemChung(String maTiemChung) {
        if (maTiemChung == null || maTiemChung.trim().isEmpty()) {
            throw new IllegalArgumentException("Vaccination ID cannot be null or empty");
        }

        maTiemChung = maTiemChung.trim();
        
        // Check if record exists and get its vaccine ID
        Object[] record = getTiemChungById(maTiemChung);
        if (record == null) {
            throw new IllegalArgumentException("Vaccination record does not exist");
        }
        
        // Get the full record to get the vaccine ID
        TiemChung existingRecord = tiemChungDAO.getByMaTiemChung(maTiemChung);
        if (existingRecord == null) {
            throw new IllegalArgumentException("Vaccination record does not exist");
        }

        String maVaccine = existingRecord.getMaVaccine();
        
        // Delete the record first
        if (!tiemChungDAO.deleteTiemChung(maTiemChung)) {
            return false;
        }
        
        // After successful deletion, increase the vaccine quantity
        if (!vaccineDAO.increaseQuantity(maVaccine)) {
            // Log the error but don't rollback the deletion
            System.err.println("Warning: Could not update vaccine quantity after deletion for vaccine " + maVaccine);
        }
        
        return true;
    }

    public boolean updateTiemChung(Object[] updatedData) {
        if (updatedData == null || updatedData.length < 6) {
            throw new IllegalArgumentException("Invalid update data");
        }

        // Extract and validate fields
        String maTiemChung = (String) updatedData[0];
        if (maTiemChung == null || maTiemChung.trim().isEmpty()) {
            throw new IllegalArgumentException("Vaccination ID cannot be null or empty");
        }

        // Parse and validate date
        String ngayTiemStr = (String) updatedData[1];
        java.sql.Date ngayTiem;
        try {
            ngayTiem = java.sql.Date.valueOf(ngayTiemStr);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd");
        }

        // Get vaccine ID
        String maVaccine = (String) updatedData[2];
        if (maVaccine == null || maVaccine.trim().isEmpty()) {
            throw new IllegalArgumentException("Vaccine ID cannot be null or empty");
        }

        // Get customer ID
        String maKhach = (String) updatedData[3];
        if (maKhach == null || maKhach.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be null or empty");
        }

        // Get status
        String trangThai = (String) updatedData[4];
        if (trangThai == null || trangThai.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be empty");
        }

        String ghiChu = (String) updatedData[5];
        
        // Get existing record to preserve non-editable fields
        TiemChung existingRecord = tiemChungDAO.getByMaTiemChung(maTiemChung.trim());
        if (existingRecord == null) {
            throw new IllegalArgumentException("Vaccination record not found");
        }

        // Handle vaccine quantity if vaccine is changed
        String oldMaVaccine = existingRecord.getMaVaccine();
        String newMaVaccine = maVaccine.trim();
        
        if (!oldMaVaccine.equals(newMaVaccine)) {
            // Return old vaccine to inventory (+1)
            if (!vaccineDAO.increaseQuantity(oldMaVaccine)) {
                throw new IllegalArgumentException("Could not update old vaccine quantity");
            }
            // Take new vaccine from inventory (-1)
            if (!vaccineDAO.decreaseQuantity(newMaVaccine)) {
                // If we can't decrease new vaccine, revert old vaccine change
                vaccineDAO.decreaseQuantity(oldMaVaccine);
                throw new IllegalArgumentException("Could not update new vaccine quantity");
            }
        }

        // Create updated record
        TiemChung updatedRecord = new TiemChung();
        updatedRecord.setMaTiemChung(maTiemChung.trim());
        updatedRecord.setMaVaccine(newMaVaccine);
        updatedRecord.setMaKhach(maKhach.trim());
        updatedRecord.setMaBacSi(existingRecord.getMaBacSi()); // preserve doctor
        updatedRecord.setNgayChiDinh(existingRecord.getNgayChiDinh()); // preserve prescription date
        updatedRecord.setNgayTiem(ngayTiem);
        updatedRecord.setTrangThaiTiem(trangThai.trim());
        updatedRecord.setGhiChu(ghiChu);

        if (!tiemChungDAO.updateTiemChung(updatedRecord)) {
            // If update fails, revert the vaccine quantity changes
            if (!oldMaVaccine.equals(newMaVaccine)) {
                vaccineDAO.decreaseQuantity(oldMaVaccine);
                vaccineDAO.increaseQuantity(newMaVaccine);
            }
            return false;
        }
        
        return true;
    }

    public NguoiDung getKhachHangByMa(String maKhach) {
        if (maKhach == null || maKhach.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be null or empty");
        }
        return nguoiDungDAO.getKhachById(maKhach);
    }

    // ===== Vaccination Scheduling =====
    public boolean createChiDinhTiem(TiemChung chiDinh) {
        // Basic validation
        if (chiDinh == null) {
            throw new IllegalArgumentException("Vaccination record cannot be null");
        }

        // Validate required fields
        if (chiDinh.getMaVaccine() == null || chiDinh.getMaKhach() == null
                || chiDinh.getMaBacSi() == null || chiDinh.getNgayChiDinh() == null) {
            throw new IllegalArgumentException("Required fields (vaccine, customer, doctor, prescription date) cannot be null");
        }

        boolean success = tiemChungDAO.addTiemChung(chiDinh);
        if (success) {
            // If vaccination record is created successfully, decrease the vaccine quantity
            boolean decreaseSuccess = vaccineDAO.decreaseQuantity(chiDinh.getMaVaccine());
            if (!decreaseSuccess) {
                // If we couldn't decrease the quantity, log it or handle the error
                System.err.println("Warning: Could not decrease vaccine quantity for " + chiDinh.getMaVaccine());
            }
            return true; // Return true if at least the vaccination record was created
        }
        return false;
    }

    public boolean updateTrangThaiTiem(String maTiemChung, String trangThai) {
        // Validate input
        if (maTiemChung == null || trangThai == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }

        // Validate status value
        if (!Arrays.asList("da_tiem", "cho_tiem", "huy").contains(trangThai.toLowerCase())) {
            throw new IllegalArgumentException("Invalid vaccination status");
        }

        TiemChung tc = tiemChungDAO.getByMaTiemChung(maTiemChung);
        if (tc == null) {
            throw new IllegalArgumentException("Vaccination record not found");
        }

        tc.setTrangThaiTiem(trangThai);
        return tiemChungDAO.updateTiemChung(tc);
    }

    // ===== Customer Search =====
    public List<NguoiDung> searchKhachHang(String searchTerm, int page, int pageSize) {
        if (searchTerm == null) {
            throw new IllegalArgumentException("Search term cannot be null");
        }
        return nguoiDungDAO.searchKhachHang(
                searchTerm.trim(),
                Math.max(1, page),
                Math.max(1, pageSize)
        );
    }

    public int getSearchKhachHangCount(String searchTerm) {
        if (searchTerm == null) {
            throw new IllegalArgumentException("Search term cannot be null");
        }
        return nguoiDungDAO.getSearchKhachHangCount(searchTerm.trim());
    }

    public NguoiDung getKhachById(String maNguoiDung) {
        if (maNguoiDung == null || maNguoiDung.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be null or empty");
        }
        return nguoiDungDAO.getKhachById(maNguoiDung.trim());
    }

    public boolean deleteKhach(String maNguoiDung) {
        if (maNguoiDung == null || maNguoiDung.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be null or empty");
        }

        // Verify it's actually a customer before deletion
        NguoiDung khach = getKhachById(maNguoiDung.trim());
        if (khach == null) {
            throw new IllegalArgumentException("Customer not found or not a customer account");
        }

        return nguoiDungDAO.deleteNguoiDung(maNguoiDung.trim());
    }

    public boolean updateKhach(String maKhach, String hoTen, String tenDangNhap, String email,
            java.sql.Date ngaySinh, String gioiTinh) {
        // Validate required fields
        if (maKhach == null || maKhach.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be null or empty");
        }
        if (hoTen == null || hoTen.trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }

        // Get existing customer
        NguoiDung existingKhach = getKhachById(maKhach.trim());
        if (existingKhach == null) {
            throw new IllegalArgumentException("Customer not found or not a customer account");
        }

        // Create updated customer while preserving ID, role, password and creation date
        NguoiDung updatedKhach = new NguoiDung();
        // Keep unchangeable fields
        updatedKhach.setMaNguoiDung(existingKhach.getMaNguoiDung());  // ID cannot be changed
        updatedKhach.setVaiTro(Role.KHACH);  // Role must stay as KHACH
        updatedKhach.setMatKhau(existingKhach.getMatKhau());  // Password cannot be changed by BacSi
        updatedKhach.setNgayTao(existingKhach.getNgayTao());  // Creation date preserved

        // Set allowed updatable fields
        updatedKhach.setHoTen(hoTen.trim());
        updatedKhach.setTenDangNhap(tenDangNhap);
        updatedKhach.setEmail(email);
        updatedKhach.setNgaySinh(ngaySinh);
        updatedKhach.setGioiTinh(gioiTinh);

        return nguoiDungDAO.updateNguoiDung(updatedKhach);
    }

    // ===== Vaccine Management =====
    public List<Object[]> getAvailableVaccines() {
        return vaccineDAO.getAvailableVaccines();
    }
}
