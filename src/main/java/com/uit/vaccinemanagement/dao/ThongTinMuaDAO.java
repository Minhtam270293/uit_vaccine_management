package com.uit.vaccinemanagement.dao;

import com.uit.vaccinemanagement.model.ThongTinMua;
import com.uit.vaccinemanagement.util.DBConnection;
import java.sql.*;
import java.util.*;

public class ThongTinMuaDAO {

    public List<ThongTinMua> getAllThongTinMua() {
        List<ThongTinMua> list = new ArrayList<>();
        String sql = "SELECT * FROM Thong_Tin_Mua";
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ThongTinMua t = new ThongTinMua(
                        rs.getString("ma_giao_dich"),
                        rs.getString("ma_vaccine"),
                        rs.getString("ma_khach"),
                        rs.getInt("so_luong_mua"),
                        rs.getDouble("tong_tien"),
                        rs.getTimestamp("ngay_giao_dich")
                );
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addThongTinMua(ThongTinMua t) {
        String sql = "INSERT INTO Thong_Tin_Mua(ma_giao_dich, ma_vaccine, ma_khach, so_luong_mua, tong_tien, ngay_giao_dich) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t.getMaGiaoDich());
            ps.setString(2, t.getMaVaccine());
            ps.setString(3, t.getMaKhach());
            ps.setInt(4, t.getSoLuongMua());
            ps.setDouble(5, t.getTongTien());
            ps.setTimestamp(6, t.getNgayGiaoDich());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateThongTinMua(ThongTinMua t) {
        String sql = "UPDATE Thong_Tin_Mua SET ma_vaccine=?, ma_khach=?, so_luong_mua=?, tong_tien=?, ngay_giao_dich=? WHERE ma_giao_dich=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t.getMaVaccine());
            ps.setString(2, t.getMaKhach());
            ps.setInt(3, t.getSoLuongMua());
            ps.setDouble(4, t.getTongTien());
            ps.setTimestamp(5, t.getNgayGiaoDich());
            ps.setString(6, t.getMaGiaoDich());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteThongTinMua(String maGiaoDich) {
        String sql = "DELETE FROM Thong_Tin_Mua WHERE ma_giao_dich=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maGiaoDich);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Object[]> getByMaKhach(String maKhach) {
        List<Object[]> result = new ArrayList<>();
        String sql = "SELECT * FROM thong_tin_mua WHERE ma_khach = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maKhach);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] row = {
                        rs.getInt("ma_giao_dich"),
                        rs.getInt("ma_vaccine"),
                        rs.getInt("ma_khach"),
                        rs.getInt("so_luong_mua"),
                        rs.getDouble("tong_tien"),
                        rs.getTimestamp("ngay_giao_dich")
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
