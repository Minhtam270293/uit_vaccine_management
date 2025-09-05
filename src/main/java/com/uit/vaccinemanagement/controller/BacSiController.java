package com.uit.vaccinemanagement.controller;

import com.uit.vaccinemanagement.dao.TiemChungDAO;
import com.uit.vaccinemanagement.dao.NguoiDungDAO;
import com.uit.vaccinemanagement.dao.VaccineDAO;
import com.uit.vaccinemanagement.model.TiemChung;
import com.uit.vaccinemanagement.model.NguoiDung;
import com.uit.vaccinemanagement.model.Vaccine;
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
        // Check if record exists
        if (getTiemChungById(maTiemChung) == null) {
            throw new IllegalArgumentException("Vaccination record does not exist");
        }

        return tiemChungDAO.deleteTiemChung(maTiemChung);
    }

    public boolean updateTiemChung(Object[] updatedData) {
        if (updatedData == null || updatedData.length < 6) {
            throw new IllegalArgumentException("Invalid update data");
        }

        String maTiemChung = (String) updatedData[0];
        if (maTiemChung == null || maTiemChung.trim().isEmpty()) {
            throw new IllegalArgumentException("Vaccination ID cannot be null or empty");
        }

        // Parse and validate date
        String ngayTiemStr = (String) updatedData[1];
        java.sql.Date ngayTiem;
        try {
            java.sql.Date.valueOf(ngayTiemStr);
            ngayTiem = java.sql.Date.valueOf(ngayTiemStr);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd");
        }

        String trangThai = (String) updatedData[4];
        if (trangThai == null || trangThai.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be empty");
        }

        String ghiChu = (String) updatedData[5];

        return tiemChungDAO.updateTiemChung(maTiemChung.trim(), ngayTiem, trangThai.trim(), ghiChu);
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

    // ===== Vaccine Management =====
    public List<Object[]> getAvailableVaccines() {
        return vaccineDAO.getAvailableVaccines();
    }
}
