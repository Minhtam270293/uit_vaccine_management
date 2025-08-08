package com.uit.vaccinemanagement.dao;

import com.uit.vaccinemanagement.model.NhaSanXuat;
import com.uit.vaccinemanagement.util.DBConnection;
import java.sql.*;
import java.util.*;

public class NhaSanXuatDAO {

    public List<NhaSanXuat> getAllNhaSanXuat() {
        List<NhaSanXuat> list = new ArrayList<>();
        String sql = "SELECT * FROM Nha_San_Xuat";
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                NhaSanXuat n = new NhaSanXuat(
                        rs.getString("ma_nha_sx"),
                        rs.getString("ten_nha_sx"),
                        rs.getString("quoc_gia"),
                        rs.getTimestamp("ngay_tao")
                );
                list.add(n);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addNhaSanXuat(NhaSanXuat n) {
        String sql = "INSERT INTO Nha_San_Xuat(ma_nha_sx, ten_nha_sx, quoc_gia, ngay_tao) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, n.getMaNhaSX());
            ps.setString(2, n.getTenNhaSX());
            ps.setString(3, n.getQuocGia());
            ps.setTimestamp(4, n.getNgayTao());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateNhaSanXuat(NhaSanXuat n) {
        String sql = "UPDATE Nha_San_Xuat SET ten_nha_sx=?, quoc_gia=?, ngay_tao=? WHERE ma_nha_sx=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, n.getTenNhaSX());
            ps.setString(2, n.getQuocGia());
            ps.setTimestamp(3, n.getNgayTao());
            ps.setString(4, n.getMaNhaSX());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteNhaSanXuat(String maNhaSX) {
        String sql = "DELETE FROM Nha_San_Xuat WHERE ma_nha_sx=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNhaSX);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Object[]> getAllNhaSanXuatAsObjectArray() {
        List<Object[]> result = new ArrayList<>();
        String sql = "SELECT * FROM Nha_San_Xuat";
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Object[] row = {
                    rs.getString("ma_nha_sx"),
                    rs.getString("ten_nha_sx"),
                    rs.getString("quoc_gia"),
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
