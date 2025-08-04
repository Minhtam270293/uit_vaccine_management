package com.uit.vaccinemanagement.dao;

import com.uit.vaccinemanagement.model.Benh;
import com.uit.vaccinemanagement.util.DBConnection;
import java.sql.*;
import java.util.*;

public class BenhDAO {

    public List<Benh> getAllBenh() {
        List<Benh> list = new ArrayList<>();
        String sql = "SELECT * FROM Benh";
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Benh b = new Benh(
                        rs.getString("ma_benh"),
                        rs.getString("ten_benh"),
                        rs.getString("mo_ta"),
                        rs.getTimestamp("ngay_tao")
                );
                list.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addBenh(Benh benh) {
        String sql = "INSERT INTO Benh(ma_benh, ten_benh, mo_ta, ngay_tao) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, benh.getMaBenh());
            ps.setString(2, benh.getTenBenh());
            ps.setString(3, benh.getMoTa());
            ps.setTimestamp(4, benh.getNgayTao());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateBenh(Benh benh) {
        String sql = "UPDATE Benh SET ten_benh=?, mo_ta=?, ngay_tao=? WHERE ma_benh=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, benh.getTenBenh());
            ps.setString(2, benh.getMoTa());
            ps.setTimestamp(3, benh.getNgayTao());
            ps.setString(4, benh.getMaBenh());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteBenh(String maBenh) {
        String sql = "DELETE FROM Benh WHERE ma_benh=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maBenh);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
