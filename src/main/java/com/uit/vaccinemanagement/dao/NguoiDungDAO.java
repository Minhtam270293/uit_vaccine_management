package com.uit.vaccinemanagement.dao;

import com.uit.vaccinemanagement.model.NguoiDung;
import com.uit.vaccinemanagement.util.DBConnection;
import com.uit.vaccinemanagement.util.Role;
import java.sql.*;
import java.util.*;

public class NguoiDungDAO {
    // DAO-level pagination
    public List<NguoiDung> getNguoiDungPage(int page, int pageSize) {
        if (page <= 0) page = 1;
        if (pageSize <= 0) pageSize = 20;
        List<NguoiDung> list = new ArrayList<>();
        String sql = "SELECT * FROM Nguoi_Dung ORDER BY ma_nguoi_dung LIMIT ?, ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, (page - 1) * pageSize);
            ps.setInt(2, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    NguoiDung nd = new NguoiDung(
                            rs.getString("ma_nguoi_dung"),
                            rs.getString("ho_ten"),
                            rs.getString("ten_dang_nhap"),
                            rs.getString("email"),
                            rs.getString("mat_khau"),
                            Role.valueOf(rs.getString("vai_tro").toUpperCase()),
                            rs.getDate("ngay_sinh"),
                            rs.getString("gioi_tinh"),
                            rs.getTimestamp("ngay_tao")
                    );
                    list.add(nd);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Overload for default page/pageSize
    public List<NguoiDung> getNguoiDungPage() {
        return getNguoiDungPage(1, 20);
    }

    public List<NguoiDung> getAllKhach() {
        List<NguoiDung> list = new ArrayList<>();
        String sql = "SELECT * FROM `Nguoi_Dung` WHERE `vai_tro` = 'khach' ORDER BY `ho_ten`";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                NguoiDung nd = new NguoiDung(
                    rs.getString("ma_nguoi_dung"),
                    rs.getString("ho_ten"),
                    rs.getString("ten_dang_nhap"),
                    rs.getString("email"),
                    rs.getString("mat_khau"),
                    Role.valueOf(rs.getString("vai_tro").toUpperCase()),
                    rs.getDate("ngay_sinh"),
                    rs.getString("gioi_tinh"),
                    rs.getTimestamp("ngay_tao")
                );
                list.add(nd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<NguoiDung> getAllNguoiDung() {
        List<NguoiDung> list = new ArrayList<>();
        String sql = "SELECT * FROM `Nguoi_Dung`";
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                NguoiDung nd = new NguoiDung(
                        rs.getString("ma_nguoi_dung"),
                        rs.getString("ho_ten"),
                        rs.getString("ten_dang_nhap"),
                        rs.getString("email"),
                        rs.getString("mat_khau"),
                        Role.valueOf(rs.getString("vai_tro").toUpperCase()),
                        rs.getDate("ngay_sinh"),
                        rs.getString("gioi_tinh"),
                        rs.getTimestamp("ngay_tao")
                );
                list.add(nd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addNguoiDung(NguoiDung nd) {
        String sql = "INSERT INTO Nguoi_Dung(ma_nguoi_dung, ho_ten, ten_dang_nhap, email, mat_khau, vai_tro, ngay_sinh, gioi_tinh, ngay_tao) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nd.getMaNguoiDung());
            ps.setString(2, nd.getHoTen());
            ps.setString(3, nd.getTenDangNhap());
            ps.setString(4, nd.getEmail());
            ps.setString(5, nd.getMatKhau());
            ps.setString(6, nd.getVaiTro().name().toLowerCase());
            ps.setDate(7, nd.getNgaySinh());
            ps.setString(8, nd.getGioiTinh());
            ps.setTimestamp(9, nd.getNgayTao());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateNguoiDung(NguoiDung nd) {
        String sql = "UPDATE Nguoi_Dung SET ho_ten=?, ten_dang_nhap=?, email=?, mat_khau=?, vai_tro=?, ngay_sinh=?, gioi_tinh=?, ngay_tao=? WHERE ma_nguoi_dung=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nd.getHoTen());
            ps.setString(2, nd.getTenDangNhap());
            ps.setString(3, nd.getEmail());
            ps.setString(4, nd.getMatKhau());
            ps.setString(5, nd.getVaiTro().name().toLowerCase());
            ps.setDate(6, nd.getNgaySinh());
            ps.setString(7, nd.getGioiTinh());
            ps.setTimestamp(8, nd.getNgayTao());
            ps.setString(9, nd.getMaNguoiDung());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteNguoiDung(String maNguoiDung) {
        String sql = "DELETE FROM Nguoi_Dung WHERE ma_nguoi_dung=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNguoiDung);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ===== Patient (Khach) specific methods =====
    public List<NguoiDung> getKhachHangPage(int page, int pageSize) {
        if (page <= 0) page = 1;
        if (pageSize <= 0) pageSize = 20;
        List<NguoiDung> list = new ArrayList<>();
        String sql = "SELECT * FROM Nguoi_Dung WHERE vai_tro = 'khach' ORDER BY ma_nguoi_dung LIMIT ?, ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, (page - 1) * pageSize);
            ps.setInt(2, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    NguoiDung nd = new NguoiDung(
                            rs.getString("ma_nguoi_dung"),
                            rs.getString("ho_ten"),
                            rs.getString("ten_dang_nhap"),
                            rs.getString("email"),
                            rs.getString("mat_khau"),
                            Role.valueOf(rs.getString("vai_tro").toUpperCase()),
                            rs.getDate("ngay_sinh"),
                            rs.getString("gioi_tinh"),
                            rs.getTimestamp("ngay_tao")
                    );
                    list.add(nd);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Overload for default page/pageSize
    public List<NguoiDung> getKhachHangPage() {
        return getKhachHangPage(1, 20);
    }

    public int getKhachHangCount() {
        String sql = "SELECT COUNT(*) FROM Nguoi_Dung WHERE vai_tro = 'khach'";
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

    public List<NguoiDung> searchKhachHang(String searchTerm, int page, int pageSize) {
        if (page <= 0) page = 1;
        if (pageSize <= 0) pageSize = 20;
        List<NguoiDung> list = new ArrayList<>();
        String sql = """
            SELECT * FROM Nguoi_Dung 
            WHERE vai_tro = 'khach' 
            AND (ho_ten LIKE ? OR ten_dang_nhap LIKE ? OR email LIKE ? OR ma_nguoi_dung LIKE ?)
            ORDER BY ma_nguoi_dung 
            LIMIT ?, ?
            """;
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String searchPattern = "%" + searchTerm + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);
            ps.setString(4, searchPattern);
            ps.setInt(5, (page - 1) * pageSize);
            ps.setInt(6, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    NguoiDung nd = new NguoiDung(
                            rs.getString("ma_nguoi_dung"),
                            rs.getString("ho_ten"),
                            rs.getString("ten_dang_nhap"),
                            rs.getString("email"),
                            rs.getString("mat_khau"),
                            Role.valueOf(rs.getString("vai_tro").toUpperCase()),
                            rs.getDate("ngay_sinh"),
                            rs.getString("gioi_tinh"),
                            rs.getTimestamp("ngay_tao")
                    );
                    list.add(nd);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getSearchKhachHangCount(String searchTerm) {
        String sql = """
            SELECT COUNT(*) FROM Nguoi_Dung 
            WHERE vai_tro = 'khach' 
            AND (ho_ten LIKE ? OR ten_dang_nhap LIKE ? OR email LIKE ? OR ma_nguoi_dung LIKE ?)
            """;
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String searchPattern = "%" + searchTerm + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);
            ps.setString(4, searchPattern);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public NguoiDung getKhachById(String maNguoiDung) {
        String sql = "SELECT * FROM Nguoi_Dung WHERE ma_nguoi_dung = ? AND vai_tro = 'khach'";
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNguoiDung);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new NguoiDung(
                        rs.getString("ma_nguoi_dung"),
                        rs.getString("ho_ten"),
                        rs.getString("ten_dang_nhap"),
                        rs.getString("email"),
                        rs.getString("mat_khau"),
                        Role.valueOf(rs.getString("vai_tro").toUpperCase()),
                        rs.getDate("ngay_sinh"),
                        rs.getString("gioi_tinh"),
                        rs.getTimestamp("ngay_tao")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
