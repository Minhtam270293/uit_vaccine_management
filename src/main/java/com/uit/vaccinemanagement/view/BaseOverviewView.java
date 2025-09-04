package com.uit.vaccinemanagement.view;

import java.awt.*;
import javax.swing.*;

import com.uit.vaccinemanagement.controller.AdminController;
import com.uit.vaccinemanagement.model.NguoiDung;
import com.uit.vaccinemanagement.util.Role;
import com.uit.vaccinemanagement.view.components.pages.*;

public class BaseOverviewView {
    protected final NguoiDung currentUser;
    protected final AdminController controller;
    protected JFrame frame;
    protected JPanel mainPanel;
    protected CardLayout cardLayout;
    
    protected static final String USER_PANEL = "UserPanel";
    protected static final String VACCINE_PANEL = "VaccinePanel";
    protected static final String MANUFACTURER_PANEL = "ManufacturerPanel";
    protected static final String PURCHASE_PANEL = "PurchasePanel";
    protected static final String VACCINATION_PANEL = "VaccinationPanel";

    public BaseOverviewView(NguoiDung currentUser) {
        this.currentUser = currentUser;
        this.controller = new AdminController();
        initializeUI();
    }

    protected void initializeUI() {
        // Create and setup main frame
        frame = new JFrame(getFrameTitle());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 900);

        // Left panel with user info and navigation
        JPanel leftPanel = createNavigationPanel();
        
        // Main content panel with CardLayout
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        // Initialize management panels based on role
        initializeManagementPanels();

        // Create split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, mainPanel);
        splitPane.setDividerLocation(200);
        
        frame.add(splitPane);
        frame.setLocationRelativeTo(null);
    }

    protected String getFrameTitle() {
        return switch (currentUser.getVaiTro()) {
            case ADMIN -> "Admin Dashboard";
            case BACSI -> "Doctor Dashboard";
            case KHACH -> "User Dashboard";
            default -> "Dashboard";
        };
    }

    protected JPanel createNavigationPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // User Info Panel
        addUserInfoPanel(leftPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Navigation Buttons based on role
        addNavigationButtons(leftPanel);
        
        // Add logout at bottom
        leftPanel.add(Box.createVerticalGlue());
        addLogoutButton(leftPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        return leftPanel;
    }

    private void addUserInfoPanel(JPanel leftPanel) {
        JPanel userInfoPanel = new JPanel(new GridLayout(2, 1, 0, 2));
        JLabel lblTen = new JLabel("Tên: " + currentUser.getHoTen());
        JLabel lblVaiTro = new JLabel("Vai trò: " + currentUser.getVaiTro());
        
        lblTen.setHorizontalAlignment(SwingConstants.LEFT);
        lblVaiTro.setHorizontalAlignment(SwingConstants.LEFT);
        
        userInfoPanel.add(lblTen);
        userInfoPanel.add(lblVaiTro);
        userInfoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        leftPanel.add(userInfoPanel);
    }

    protected void addNavigationButtons(JPanel leftPanel) {
        Dimension buttonSize = new Dimension(180, 30);
        Role role = currentUser.getVaiTro();

        // Common buttons for all roles
        if (role == Role.ADMIN) {
            addButton(leftPanel, "Người Dùng", USER_PANEL, buttonSize);
            addButton(leftPanel, "Vaccine", VACCINE_PANEL, buttonSize);
            addButton(leftPanel, "Nhà sản xuất", MANUFACTURER_PANEL, buttonSize);
        } else if (role == Role.BACSI) {
            addButton(leftPanel, "Vaccine", VACCINE_PANEL, buttonSize);
            addButton(leftPanel, "Tiêm chủng", VACCINATION_PANEL, buttonSize);
        } else if (role == Role.KHACH) {
            addButton(leftPanel, "Thông tin cá nhân", USER_PANEL, buttonSize);
            addButton(leftPanel, "Đặt mua", PURCHASE_PANEL, buttonSize);
            addButton(leftPanel, "Lịch sử tiêm", VACCINATION_PANEL, buttonSize);
        }
    }

    private void addButton(JPanel panel, String text, String cardName, Dimension size) {
        JButton button = new JButton(text);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(e -> cardLayout.show(mainPanel, cardName));
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void addLogoutButton(JPanel panel) {
        JPanel logoutPanel = new JPanel();
        logoutPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        
        JButton btnDangXuat = new JButton("Đăng xuất");
        btnDangXuat.setPreferredSize(new Dimension(180, 30));
        btnDangXuat.addActionListener(e -> {
            frame.dispose();
            com.uit.vaccinemanagement.VaccineManagement.showLogin();
        });
        
        logoutPanel.add(btnDangXuat);
        panel.add(logoutPanel);
    }

    protected void initializeManagementPanels() {
        Role role = currentUser.getVaiTro();

        if (role == Role.ADMIN) {
            mainPanel.add(new UserManagementPanel(controller), USER_PANEL);
            mainPanel.add(new VaccineManagementPanel(controller), VACCINE_PANEL);
            mainPanel.add(new NhaSanXuatManagementPanel(controller), MANUFACTURER_PANEL);
        } else if (role == Role.BACSI) {
            mainPanel.add(new VaccineManagementPanel(controller), VACCINE_PANEL);
            // TODO: Add VaccinationManagementPanel
        } else if (role == Role.KHACH) {
            mainPanel.add(new UserProfilePanel(controller, currentUser), USER_PANEL);
            // TODO: Add PurchaseManagementPanel and VaccinationHistoryPanel
        }
    }

    public void show() {
        if (!frame.isVisible()) {
            frame.setVisible(true);
            // Show appropriate default panel based on role
            String defaultPanel = switch (currentUser.getVaiTro()) {
                case ADMIN -> USER_PANEL;
                case BACSI -> VACCINE_PANEL;
                case KHACH -> USER_PANEL;
            };
            cardLayout.show(mainPanel, defaultPanel);
        }
    }
}
