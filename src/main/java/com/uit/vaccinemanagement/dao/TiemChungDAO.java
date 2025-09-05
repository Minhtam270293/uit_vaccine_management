
package com.uit.vaccinemanagement.dao;

import com.uit.vaccinemanagement.model.TiemChung;
import com.uit.vaccinemanagement.util.DBConnection;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

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

    public List<Object[]> getByBacSiPaginated(String maBacSi, int offset, int limit) {
        List<Object[]> list = new ArrayList<>();
        String sql = """
            SELECT 
                tc.`ma_tiem_chung`,
                tc.`ngay_tiem`,
                v.`ten_vaccine`,
                nd.`ho_ten` as ten_khach,
                tc.`trang_thai_tiem`,
                tc.`ghi_chu`
            FROM `Tiem_Chung` tc
            JOIN `Vaccine` v ON tc.`ma_vaccine` = v.`ma_vaccine`
            JOIN `Nguoi_Dung` nd ON tc.`ma_khach` = nd.`ma_nguoi_dung`
            WHERE tc.ma_bac_si = ?
            ORDER BY tc.ngay_tiem DESC
            LIMIT ?, ?
        """;
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, maBacSi);
            ps.setInt(2, offset);
            ps.setInt(3, limit);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] row = {
                        rs.getString("ma_tiem_chung"),
                        rs.getDate("ngay_tiem"),
                        rs.getString("ten_vaccine"),
                        rs.getString("ten_khach"),
                        rs.getString("trang_thai_tiem"),
                        rs.getString("ghi_chu")
                    };
                    list.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Object[] getTiemChungById(String maTiemChung) {
        String sql = """
            SELECT 
                tc.`ma_tiem_chung`,
                tc.`ngay_tiem`,
                v.`ten_vaccine`,
                nd.`ho_ten` as ten_khach,
                tc.`trang_thai_tiem`,
                tc.`ghi_chu`,
                tc.`ma_vaccine`,
                tc.`ma_khach`
            FROM `Tiem_Chung` tc
            JOIN `Vaccine` v ON tc.`ma_vaccine` = v.`ma_vaccine`
            JOIN `Nguoi_Dung` nd ON tc.`ma_khach` = nd.`ma_nguoi_dung`
            WHERE tc.`ma_tiem_chung` = ?
        """;
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, maTiemChung);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Object[] {
                        rs.getString("ma_tiem_chung"),
                        rs.getDate("ngay_tiem"),
                        rs.getString("ten_vaccine"),
                        rs.getString("ten_khach"),
                        rs.getString("trang_thai_tiem"),
                        rs.getString("ghi_chu"),
                        rs.getString("ma_vaccine"),
                        rs.getString("ma_khach")
                    };
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteTiemChung(String maTiemChung) {
        String sql = "DELETE FROM `Tiem_Chung` WHERE `ma_tiem_chung` = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, maTiemChung);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateTiemChung(String maTiemChung, java.sql.Date ngayTiem, String trangThai, String ghiChu) {
        String sql = "UPDATE `Tiem_Chung` SET `ngay_tiem` = ?, `trang_thai_tiem` = ?, `ghi_chu` = ? WHERE `ma_tiem_chung` = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setDate(1, ngayTiem);
            ps.setString(2, trangThai);
            ps.setString(3, ghiChu);
            ps.setString(4, maTiemChung);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getTotalByBacSi(String maBacSi) {
        String sql = "SELECT COUNT(*) FROM `Tiem_Chung` WHERE `ma_bac_si` = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, maBacSi);
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

    public List<Object[]> searchByBacSi(String maBacSi, String searchTerm, int offset, int limit) {
        List<Object[]> list = new ArrayList<>();
        String sql = """
            SELECT 
                tc.`ma_tiem_chung`,
                tc.`ngay_tiem`,
                v.`ten_vaccine`,
                nd.`ho_ten` as ten_khach,
                tc.`trang_thai_tiem`,
                tc.`ghi_chu`
            FROM `Tiem_Chung` tc
            JOIN `Vaccine` v ON tc.`ma_vaccine` = v.`ma_vaccine`
            JOIN `Nguoi_Dung` nd ON tc.`ma_khach` = nd.`ma_nguoi_dung`
            WHERE tc.ma_bac_si = ?
            AND (
                tc.ma_tiem_chung LIKE ? OR
                v.ten_vaccine LIKE ? OR
                nd.ho_ten LIKE ? OR
                tc.trang_thai_tiem LIKE ?
            )
            ORDER BY tc.ngay_tiem DESC
            LIMIT ?, ?
        """;
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + searchTerm + "%";
            ps.setString(1, maBacSi);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);
            ps.setString(4, searchPattern);
            ps.setString(5, searchPattern);
            ps.setInt(6, offset);
            ps.setInt(7, limit);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] row = {
                        rs.getString("ma_tiem_chung"),
                        rs.getDate("ngay_tiem"),
                        rs.getString("ten_vaccine"),
                        rs.getString("ten_khach"),
                        rs.getString("trang_thai_tiem"),
                        rs.getString("ghi_chu")
                    };
                    list.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getTotalSearchByBacSi(String maBacSi, String searchTerm) {
        String sql = """
            SELECT COUNT(*)
            FROM `Tiem_Chung` tc
            JOIN `Vaccine` v ON tc.`ma_vaccine` = v.`ma_vaccine`
            JOIN `Nguoi_Dung` nd ON tc.`ma_khach` = nd.`ma_nguoi_dung`
            WHERE tc.ma_bac_si = ?
            AND (
                tc.ma_tiem_chung LIKE ? OR
                v.ten_vaccine LIKE ? OR
                nd.ho_ten LIKE ? OR
                tc.trang_thai_tiem LIKE ?
            )
        """;
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + searchTerm + "%";
            ps.setString(1, maBacSi);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);
            ps.setString(4, searchPattern);
            ps.setString(5, searchPattern);
            
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

    public List<Object[]> getByMaKhach(String maKhach) {
        List<Object[]> result = new ArrayList<>();
        String sql = """
            SELECT 
                tc.ma_tiem_chung,
                tc.ngay_tiem,
                v.ten_vaccine,
                nd.ho_ten as ten_bac_si,
                tc.trang_thai_tiem,
                tc.ghi_chu
            FROM Tiem_Chung tc
            JOIN Vaccine v ON tc.ma_vaccine = v.ma_vaccine
            JOIN Nguoi_Dung nd ON tc.ma_bac_si = nd.ma_nguoi_dung
            WHERE tc.ma_khach = ?
        """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maKhach);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] row = {
                        rs.getString("ma_tiem_chung"),
                        rs.getDate("ngay_tiem"),
                        rs.getString("ten_vaccine"),
                        rs.getString("ten_bac_si"),
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

        public List<Object[]> getByMaBacSi(String maBacSi, int page, int pageSize) {
        if (page <= 0) page = 1;
        if (pageSize <= 0) pageSize = 20;
        
        List<Object[]> result = new ArrayList<>();
        String sql = """
            SELECT tc.ma_tiem_chung, tc.ngay_tiem, 
                   v.ten_vaccine, 
                   nd.ho_ten as ten_bac_si,
                   k.ho_ten as ten_khach,
                   tc.trang_thai_tiem, tc.ghi_chu
            FROM Tiem_Chung tc
            LEFT JOIN Vaccine v ON tc.ma_vaccine = v.ma_vaccine
            LEFT JOIN Nguoi_Dung nd ON tc.ma_bac_si = nd.ma_nguoi_dung
            LEFT JOIN Nguoi_Dung k ON tc.ma_khach = k.ma_nguoi_dung
            WHERE tc.ma_bac_si = ?
            ORDER BY tc.ngay_tiem DESC
            LIMIT ?, ?
            """;
            
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maBacSi);
            ps.setInt(2, (page - 1) * pageSize);
            ps.setInt(3, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] row = {
                        rs.getString("ma_tiem_chung"),
                        rs.getDate("ngay_tiem"),
                        rs.getString("ten_vaccine"),
                        rs.getString("ten_bac_si"),
                        rs.getString("ten_khach"),
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
    
    // Overload for default page/pageSize
    public List<Object[]> getByMaBacSi(String maBacSi) {
        return getByMaBacSi(maBacSi, 1, 20);
    }

    public int getCountByMaBacSi(String maBacSi) {
        String sql = "SELECT COUNT(*) FROM Tiem_Chung WHERE ma_bac_si = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maBacSi);
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
    
    public TiemChung getByMaTiemChung(String maTiemChung) {
        String sql = "SELECT * FROM Tiem_Chung WHERE ma_tiem_chung = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maTiemChung);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new TiemChung(
                        rs.getString("ma_tiem_chung"),
                        rs.getString("ma_vaccine"),
                        rs.getString("ma_bac_si"),
                        rs.getString("ma_khach"),
                        rs.getDate("ngay_chi_dinh"),
                        rs.getDate("ngay_tiem"),
                        rs.getString("trang_thai_tiem"),
                        rs.getString("ghi_chu")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

