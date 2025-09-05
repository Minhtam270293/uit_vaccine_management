package com.uit.vaccinemanagement.dao;

import com.uit.vaccinemanagement.model.Benh;
import com.uit.vaccinemanagement.util.DBConnection;
import java.sql.*;
import java.util.*;

public class BenhDAO {

    // DAO-level pagination
    public List<Benh> getBenhPage(int page, int pageSize) {
        if (page <= 0) page = 1;
        if (pageSize <= 0) pageSize = 20;
        List<Benh> list = new ArrayList<>();
        String sql = "SELECT * FROM `Benh` ORDER BY `ma_benh` LIMIT ?, ?";
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, (page - 1) * pageSize);
            ps.setInt(2, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToBenh(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving disease page", e);
        }
        return list;
    }

    // Get total count for pagination
    public int getTotalRecords() {
        String sql = "SELECT COUNT(*) FROM `Benh`";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting total disease count", e);
        }
        return 0;
    }

    // Default page size overload
    public List<Benh> getBenhPage() {
        return getBenhPage(1, 20);
    }

    // Get all diseases
    public List<Benh> getAllBenh() {
        List<Benh> list = new ArrayList<>();
        String sql = "SELECT * FROM `Benh` ORDER BY `ten_benh`";
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSetToBenh(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving all diseases", e);
        }
        return list;
    }

    // Helper method for mapping ResultSet to Benh object
    private Benh mapResultSetToBenh(ResultSet rs) throws SQLException {
        return new Benh(
                rs.getString("ma_benh"),
                rs.getString("ten_benh"),
                rs.getString("mo_ta"),
                rs.getTimestamp("ngay_tao")
        );
    }

    // Add new disease
    public boolean addBenh(Benh benh) {
        String sql = "INSERT INTO `Benh`(`ma_benh`, `ten_benh`, `mo_ta`, `ngay_tao`) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, benh.getMaBenh());
            ps.setString(2, benh.getTenBenh());
            ps.setString(3, benh.getMoTa());
            ps.setTimestamp(4, benh.getNgayTao());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding new disease", e);
        }
    }

    // Update existing disease
    public boolean updateBenh(Benh benh) {
        String sql = "UPDATE `Benh` SET `ten_benh`=?, `mo_ta`=?, `ngay_tao`=? WHERE `ma_benh`=?";
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, benh.getTenBenh());
            ps.setString(2, benh.getMoTa());
            ps.setTimestamp(3, benh.getNgayTao());
            ps.setString(4, benh.getMaBenh());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating disease", e);
        }
    }

    // Delete disease by ID
    public boolean deleteBenh(String maBenh) {
        String sql = "DELETE FROM `Benh` WHERE `ma_benh`=?";
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maBenh);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting disease", e);
        }
    }

    // Find disease by ID
    public Benh findById(String maBenh) {
        String sql = "SELECT * FROM `Benh` WHERE `ma_benh` = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maBenh);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBenh(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error finding disease by ID", e);
        }
        return null;
    }

    // Search diseases by name (with pagination)
    public List<Benh> searchByTenBenh(String tenBenh, int page, int pageSize) {
        if (page <= 0) page = 1;
        if (pageSize <= 0) pageSize = 20;
        List<Benh> list = new ArrayList<>();
        String sql = "SELECT * FROM `Benh` WHERE `ten_benh` LIKE ? ORDER BY `ten_benh` LIMIT ?, ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + tenBenh + "%");
            ps.setInt(2, (page - 1) * pageSize);
            ps.setInt(3, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToBenh(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error searching diseases by name", e);
        }
        return list;
    }

    // Get total count for search results
    public int getTotalSearchResults(String tenBenh) {
        String sql = "SELECT COUNT(*) FROM `Benh` WHERE `ten_benh` LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + tenBenh + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting search results count", e);
        }
        return 0;
    }
}
