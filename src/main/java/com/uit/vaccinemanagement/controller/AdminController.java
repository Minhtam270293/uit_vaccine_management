package com.uit.vaccinemanagement.controller;

import com.uit.vaccinemanagement.model.*;
import com.uit.vaccinemanagement.model.pagination.PaginationResult;
import com.uit.vaccinemanagement.dao.*;
import com.uit.vaccinemanagement.view.components.dialog.NhaSanXuatEditDialog;
import com.uit.vaccinemanagement.view.components.dialog.UserEditDialog;
import com.uit.vaccinemanagement.view.components.dialog.VaccineEditDialog;
import javax.swing.*;
import java.util.*;
import java.sql.SQLException;

public class AdminController {

    private static final int PAGE_SIZE = 20;

    private final NguoiDungDAO nguoiDungDAO;
    private final VaccineDAO vaccineDAO;
    private final NhaSanXuatDAO nhaSanXuatDAO;

    public AdminController() {
        this.nguoiDungDAO = new NguoiDungDAO();
        this.vaccineDAO = new VaccineDAO();
        this.nhaSanXuatDAO = new NhaSanXuatDAO();
    }

    // User Management
    public List<NguoiDung> getNguoiDungPage(int page, int pageSize) throws SQLException {
        return nguoiDungDAO.getNguoiDungPage(page, pageSize);
    }

    public int getTotalNguoiDung() throws SQLException {
        return nguoiDungDAO.getTotalNguoiDung();
    }

    public boolean addNguoiDung(NguoiDung user) throws SQLException {
        return nguoiDungDAO.addNguoiDung(user);
    }

    public boolean updateNguoiDung(NguoiDung user) throws SQLException {
        return nguoiDungDAO.updateNguoiDung(user);
    }

    public boolean deleteNguoiDung(String userId) throws SQLException {
        return nguoiDungDAO.deleteNguoiDung(userId);
    }

    public void handleUserEdit(String userId) {
        showUserEditDialog(userId, () -> {
            showSuccess("User " + (userId == null ? "added" : "updated") + " successfully");
        });
    }

    public void handleUserDelete(String userId) {
        try {
            if (showDeleteConfirmation("user", userId)) {
                boolean success = nguoiDungDAO.deleteNguoiDung(userId);
                if (success) {
                    showSuccess("User deleted successfully");
                } else {
                    throw new RuntimeException("Failed to delete user");
                }
            }
        } catch (Exception e) {
            handleError("Error deleting user", e);
        }
    }

    // Vaccine Management
    public List<Vaccine> getVaccinePage(int page, int pageSize) throws SQLException {
        return vaccineDAO.getVaccinePage(page, pageSize);
    }

    public int getTotalVaccines() throws SQLException {
        return vaccineDAO.getTotalVaccines();
    }

    public boolean addVaccine(Vaccine vaccine) throws SQLException {
        return vaccineDAO.addVaccine(vaccine);
    }

    public boolean updateVaccine(Vaccine vaccine) throws SQLException {
        return vaccineDAO.updateVaccine(vaccine);
    }

    public boolean deleteVaccine(String vaccineId) throws SQLException {
        return vaccineDAO.deleteVaccine(vaccineId);
    }

    public void handleVaccineEdit(String vaccineId) {
        showVaccineEditDialog(vaccineId, () -> {
            showSuccess("Vaccine " + (vaccineId == null ? "added" : "updated") + " successfully");
        });
    }

    public void handleVaccineDelete(String vaccineId) {
        try {
            if (showDeleteConfirmation("vaccine", vaccineId)) {
                boolean success = vaccineDAO.deleteVaccine(vaccineId);
                if (success) {
                    showSuccess("Vaccine deleted successfully");
                } else {
                    throw new RuntimeException("Failed to delete vaccine");
                }
            }
        } catch (Exception e) {
            handleError("Error deleting vaccine", e);
        }
    }

    // Manufacturer Management
    public List<NhaSanXuat> getNhaSanXuatPage(int page, int pageSize) throws SQLException {
        return nhaSanXuatDAO.getNhaSanXuatPage(page, pageSize);
    }

    public int getTotalNhaSanXuat() throws SQLException {
        return nhaSanXuatDAO.getTotalNhaSanXuat();
    }

    public boolean addNhaSanXuat(NhaSanXuat manufacturer) throws SQLException {
        return nhaSanXuatDAO.addNhaSanXuat(manufacturer);
    }

    public boolean updateNhaSanXuat(NhaSanXuat manufacturer) throws SQLException {
        return nhaSanXuatDAO.updateNhaSanXuat(manufacturer);
    }

    public boolean deleteNhaSanXuat(String manufacturerId) throws SQLException {
        return nhaSanXuatDAO.deleteNhaSanXuat(manufacturerId);
    }

    public void handleNhaSanXuatEdit(String manufacturerId) {
        showNhaSanXuatEditDialog(manufacturerId, () -> {
            showSuccess("Manufacturer " + (manufacturerId == null ? "added" : "updated") + " successfully");
        });
    }

    public void handleNhaSanXuatDelete(String manufacturerId) {
        try {
            if (showDeleteConfirmation("manufacturer", manufacturerId)) {
                boolean success = nhaSanXuatDAO.deleteNhaSanXuat(manufacturerId);
                if (success) {
                    showSuccess("Manufacturer deleted successfully");
                } else {
                    throw new RuntimeException("Failed to delete manufacturer");
                }
            }
        } catch (Exception e) {
            handleError("Error deleting manufacturer", e);
        }
    }

    // Helper methods for UI interactions
    private boolean showDeleteConfirmation(String entityType, String id) {
        return JOptionPane.showConfirmDialog(null,
                "Delete " + entityType + " " + id + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleError(String message, Exception e) {
        JOptionPane.showMessageDialog(null,
                message + ": " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    // Dialog methods
    public void showUserEditDialog(String userId, Runnable onSuccess) {
        try {
            NguoiDung user = userId == null ? new NguoiDung()
                    : nguoiDungDAO.getAllNguoiDung().stream()
                            .filter(u -> u.getMaNguoiDung().equals(userId))
                            .findFirst()
                            .orElse(new NguoiDung());

            UserEditDialog dialog = new UserEditDialog(null, user);
            if (dialog.showDialog()) {
                NguoiDung editedUser = dialog.getUser();
                if (userId == null) {
                    addNguoiDung(editedUser);
                } else {
                    updateNguoiDung(editedUser);
                }
                if (onSuccess != null) {
                    onSuccess.run();
                }
            }
        } catch (SQLException e) {
            handleError("Error in user dialog", e);
        }
    }

    public void showVaccineEditDialog(String vaccineId, Runnable onSuccess) {
        try {
            Vaccine vaccine = vaccineId == null ? new Vaccine()
                    : vaccineDAO.getAllVaccine().stream()
                            .filter(v -> v.getMaVaccine().equals(vaccineId))
                            .findFirst()
                            .orElse(new Vaccine());

            VaccineEditDialog dialog = new VaccineEditDialog(null, vaccine);
            if (dialog.showDialog()) {
                Vaccine editedVaccine = dialog.getVaccine();
                if (vaccineId == null) {
                    addVaccine(editedVaccine);
                } else {
                    updateVaccine(editedVaccine);
                }
                if (onSuccess != null) {
                    onSuccess.run();
                }
            }
        } catch (SQLException e) {
            handleError("Error in vaccine dialog", e);
        }
    }

    public void showNhaSanXuatEditDialog(String manufacturerId, Runnable onSuccess) {
        try {
            NhaSanXuat manufacturer = manufacturerId == null ? new NhaSanXuat()
                    : nhaSanXuatDAO.getAllNhaSanXuat().stream()
                            .filter(m -> m.getMaNhaSX().equals(manufacturerId))
                            .findFirst()
                            .orElse(new NhaSanXuat());

            NhaSanXuatEditDialog dialog = new NhaSanXuatEditDialog(null, manufacturer);
            if (dialog.showDialog()) {
                NhaSanXuat editedManufacturer = dialog.getNhaSanXuat();
                if (manufacturerId == null) {
                    addNhaSanXuat(editedManufacturer);
                } else {
                    updateNhaSanXuat(editedManufacturer);
                }
                if (onSuccess != null) {
                    onSuccess.run();
                }
            }
        } catch (SQLException e) {
            handleError("Error in manufacturer dialog", e);
        }
    }
}
