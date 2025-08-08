
package com.uit.vaccinemanagement.dao;

import com.uit.vaccinemanagement.model.TiemChung;
import com.uit.vaccinemanagement.util.DBConnection;
import java.sql.*;
import java.util.*;

public class TiemChungDAO {

    public List<TiemChung> getAllTiemChung() {
        List<TiemChung> list = new ArrayList<>();
        String sql = "SELECT * FROM Tiem_Chung";
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                TiemChung t = new TiemChung(
                        rs.getString("ma_tiem_chung"),
                        rs.getString("ma_vaccine"),
                        rs.getString("ma_bac_si"),
                        rs.getString("ma_khach"),
                        rs.getDate("ngay_chi_dinh"),
                        rs.getDate("ngay_tiem"),
                        rs.getString("trang_thai_tiem"),
                        rs.getString("ghi_chu")
                );
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addTiemChung(TiemChung t) {
        String sql = "INSERT INTO Tiem_Chung(ma_tiem_chung, ma_vaccine, ma_bac_si, ma_khach, ngay_chi_dinh, ngay_tiem, trang_thai_tiem, ghi_chu) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t.getMaTiemChung());
            ps.setString(2, t.getMaVaccine());
            ps.setString(3, t.getMaBacSi());
            ps.setString(4, t.getMaKhach());
            ps.setDate(5, t.getNgayChiDinh());
            ps.setDate(6, t.getNgayTiem());
            ps.setString(7, t.getTrangThaiTiem());
            ps.setString(8, t.getGhiChu());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateTiemChung(TiemChung t) {
        String sql = "UPDATE Tiem_Chung SET ma_vaccine=?, ma_bac_si=?, ma_khach=?, ngay_chi_dinh=?, ngay_tiem=?, trang_thai_tiem=?, ghi_chu=? WHERE ma_tiem_chung=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t.getMaVaccine());
            ps.setString(2, t.getMaBacSi());
            ps.setString(3, t.getMaKhach());
            ps.setDate(4, t.getNgayChiDinh());
            ps.setDate(5, t.getNgayTiem());
            ps.setString(6, t.getTrangThaiTiem());
            ps.setString(7, t.getGhiChu());
            ps.setString(8, t.getMaTiemChung());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteTiemChung(String maTiemChung) {
        String sql = "DELETE FROM Tiem_Chung WHERE ma_tiem_chung=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maTiemChung);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Object[]> getByMaKhach(String maKhach) {
        List<Object[]> result = new ArrayList<>();
        String sql = "SELECT * FROM Tiem_Chung WHERE ma_khach = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maKhach);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] row = {
                        rs.getString("ma_tiem_chung"),
                        rs.getDate("ngay_tiem"),
                        rs.getString("ma_vaccine"),
                        rs.getString("ma_bac_si"),
                        rs.getString("trang_thai_tiem"),
                        rs.getString("ghi_chu")
                    };
                    result.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

        public List<Object[]> getByMaBacSi(String maBacSi) {
        List<Object[]> result = new ArrayList<>();
        String sql = "SELECT * FROM Tiem_Chung WHERE ma_bac_si = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maBacSi);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] row = {
                        rs.getString("ma_tiem_chung"),
                        rs.getDate("ngay_tiem"),
                        rs.getString("ma_vaccine"),
                        rs.getString("ma_bac_si"),
                        rs.getString("ma_khach"),
                        rs.getString("trang_thai_tiem"),
                        rs.getString("ghi_chu")
                    };
                    result.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}

