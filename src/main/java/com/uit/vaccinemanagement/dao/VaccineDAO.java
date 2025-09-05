package com.uit.vaccinemanagement.dao;

import com.uit.vaccinemanagement.model.Vaccine;
import com.uit.vaccinemanagement.util.DBConnection;
import java.sql.*;
import java.util.*;

public class VaccineDAO {
    public List<Vaccine> getVaccinePage(int page, int pageSize) {
        if (page <= 0) page = 1;
        if (pageSize <= 0) pageSize = 20;
        List<Vaccine> list = new ArrayList<>();
        String sql = "SELECT * FROM Vaccine ORDER BY ma_vaccine LIMIT ?, ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, (page - 1) * pageSize);
            ps.setInt(2, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Vaccine vc = new Vaccine(
                        rs.getString("ma_vaccine"),
                        rs.getString("ten_vaccine"),
                        rs.getString("hinh_anh_url"),
                        rs.getString("so_lo"),
                        rs.getDate("ngay_sx"),
                        rs.getDate("ngay_het_han"),
                        rs.getInt("tong_sl"),
                        rs.getInt("sl_con_lai"),
                        rs.getDouble("gia_nhap"),
                        rs.getDouble("gia_ban"),
                        rs.getString("mo_ta"),
                        rs.getString("ma_benh"),
                        rs.getString("ma_nha_sx"),
                        rs.getTimestamp("ngay_tao")
                    );
                    list.add(vc);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean decreaseQuantity(String maVaccine) {
        String sql = "UPDATE `Vaccine` SET `sl_con_lai` = `sl_con_lai` - 1 WHERE `ma_vaccine` = ? AND `sl_con_lai` > 0";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maVaccine);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Object[]> getAvailableVaccines() {
        List<Object[]> result = new ArrayList<>();
        String sql = """
            SELECT 
                v.`ma_vaccine`, 
                v.`ten_vaccine`
            FROM `Vaccine` v
            WHERE v.`sl_con_lai` > 0 
            AND v.`ngay_het_han` > CURRENT_DATE()
            ORDER BY v.`ngay_het_han` ASC
            """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Object[] row = {
                    rs.getString("ma_vaccine"),
                    rs.getString("ten_vaccine")
                };
                result.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int getVaccineCount() {
        String sql = "SELECT COUNT(*) FROM Vaccine";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Vaccine> getAllVaccine() {
        List<Vaccine> list = new ArrayList<>();
        String sql = "SELECT * FROM Vaccine";
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Vaccine v = new Vaccine(
                        rs.getString("ma_vaccine"),
                        rs.getString("ten_vaccine"),
                        rs.getString("hinh_anh_url"),
                        rs.getString("so_lo"),
                        rs.getDate("ngay_sx"),
                        rs.getDate("ngay_het_han"),
                        rs.getInt("tong_sl"),
                        rs.getInt("sl_con_lai"),
                        rs.getDouble("gia_nhap"),
                        rs.getDouble("gia_ban"),
                        rs.getString("mo_ta"),
                        rs.getString("ma_benh"),
                        rs.getString("ma_nha_sx"),
                        rs.getTimestamp("ngay_tao")
                );
                list.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addVaccine(Vaccine v) {
        String sql = "INSERT INTO Vaccine(ma_vaccine, ten_vaccine, hinh_anh_url, so_lo, ngay_sx, ngay_het_han, tong_sl, sl_con_lai, gia_nhap, gia_ban, mo_ta, ma_benh, ma_nha_sx, ngay_tao) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, v.getMaVaccine());
            ps.setString(2, v.getTenVaccine());
            ps.setString(3, v.getHinhAnhUrl());
            ps.setString(4, v.getSoLo());
            ps.setDate(5, v.getNgaySX());
            ps.setDate(6, v.getNgayHetHan());
            ps.setInt(7, v.getTongSL());
            ps.setInt(8, v.getSlConLai());
            ps.setDouble(9, v.getGiaNhap());
            ps.setDouble(10, v.getGiaBan());
            ps.setString(11, v.getMoTa());
            ps.setString(12, v.getMaBenh());
            ps.setString(13, v.getMaNhaSX());
            ps.setTimestamp(14, v.getNgayTao());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateVaccine(Vaccine v) {
        String sql = "UPDATE Vaccine SET ten_vaccine=?, hinh_anh_url=?, so_lo=?, ngay_sx=?, ngay_het_han=?, tong_sl=?, sl_con_lai=?, gia_nhap=?, gia_ban=?, mo_ta=?, ma_benh=?, ma_nha_sx=?, ngay_tao=? WHERE ma_vaccine=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, v.getTenVaccine());
            ps.setString(2, v.getHinhAnhUrl());
            ps.setString(3, v.getSoLo());
            ps.setDate(4, v.getNgaySX());
            ps.setDate(5, v.getNgayHetHan());
            ps.setInt(6, v.getTongSL());
            ps.setInt(7, v.getSlConLai());
            ps.setDouble(8, v.getGiaNhap());
            ps.setDouble(9, v.getGiaBan());
            ps.setString(10, v.getMoTa());
            ps.setString(11, v.getMaBenh());
            ps.setString(12, v.getMaNhaSX());
            ps.setTimestamp(13, v.getNgayTao());
            ps.setString(14, v.getMaVaccine());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteVaccine(String maVaccine) {
        String sql = "DELETE FROM Vaccine WHERE ma_vaccine=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maVaccine);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Object[]> getAllVaccineAsObjectArray() {
        List<Object[]> result = new ArrayList<>();
        String sql = "SELECT * FROM Vaccine";
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Object[] row = {
                    rs.getString("ma_vaccine"),
                    rs.getString("ten_vaccine"),
                    rs.getString("so_lo"),
                    rs.getDate("ngay_sx"),
                    rs.getDate("ngay_het_han"),
                    rs.getInt("tong_sl"),
                    rs.getInt("sl_con_lai"),
                    rs.getDouble("gia_nhap"),
                    rs.getDouble("gia_ban"),
                    rs.getString("ma_benh"),
                    rs.getString("ma_nha_sx"),
                    rs.getTimestamp("ngay_tao")
                };
                result.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
