package com.uit.vaccinemanagement.view;

import com.uit.vaccinemanagement.model.NguoiDung;
import com.uit.vaccinemanagement.controller.BacSiController;

import javax.swing.*;
import com.uit.vaccinemanagement.view.SharedComponents;
import com.uit.vaccinemanagement.view.dialogs.ChiDinhTiemDialog;
import com.uit.vaccinemanagement.view.RoundedButton;
import com.uit.vaccinemanagement.view.panels.TiemChungPanel;
import com.uit.vaccinemanagement.view.panels.KhachHangPanel;

import java.awt.*;
import java.awt.event.ActionEvent;

public class BacSiView {

    private final NguoiDung currentUser;
    private final BacSiController bacSiController;

    public BacSiView(NguoiDung currentUser) {
        this.currentUser = currentUser;
        this.bacSiController = new BacSiController();
    }

    public void showBacSiUI() {
        JFrame frame = new JFrame("Bác sĩ Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1150, 500);

        // Left panel (column 1)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(220, 500));

        // Khung 1: Tên và Vai trò (bo tròn, căn giữa, có icon)
        JPanel infoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(245, 245, 245));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2.setColor(new Color(200, 200, 200));
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 18, 18);
                g2.dispose();
            }
        };
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(18, 0, 18, 0));

        JLabel lblTen = new JLabel("\uD83D\uDC64 Tên: " + currentUser.getHoTen());
        lblTen.setFont(new Font("Roboto", Font.BOLD, 16));
        lblTen.setForeground(new Color(33, 150, 243));
        lblTen.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblVaiTro = new JLabel("\uD83D\uDD12 Vai trò: " + currentUser.getVaiTro());
        lblVaiTro.setFont(new Font("Roboto", Font.BOLD, 16));
        lblVaiTro.setForeground(new Color(244, 67, 54));
        lblVaiTro.setAlignmentX(Component.CENTER_ALIGNMENT);

        infoPanel.add(lblTen);
        infoPanel.add(Box.createVerticalStrut(6));
        infoPanel.add(lblVaiTro);

        leftPanel.add(Box.createVerticalStrut(16));
        leftPanel.add(infoPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Khung 2: Các nút chức năng (dọc, căn giữa, chiều dài đều, cố định vị trí trên)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension buttonSize = new Dimension(200, 40);

        RoundedButton btnTiemChung = new RoundedButton("Tiêm chủng");
        RoundedButton btnKhachHang = new RoundedButton("Khách hàng");
        RoundedButton btnChiDinhTiem = new RoundedButton("Chỉ định tiêm");

        btnTiemChung.setPreferredSize(buttonSize);
        btnKhachHang.setPreferredSize(buttonSize);
        btnChiDinhTiem.setPreferredSize(buttonSize);

        btnTiemChung.setMaximumSize(buttonSize);
        btnKhachHang.setMaximumSize(buttonSize);
        btnChiDinhTiem.setMaximumSize(buttonSize);

        btnTiemChung.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnKhachHang.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnChiDinhTiem.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Không dùng glue, chỉ dùng strut để giữ nút ở trên
        buttonPanel.add(btnTiemChung);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(btnKhachHang);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(btnChiDinhTiem);

        leftPanel.add(buttonPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        // Khung 3: Đăng xuất (panel riêng, đưa xuống cuối sidebar)
        JPanel buttonPanelLogout = new JPanel();
        buttonPanelLogout.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        buttonPanelLogout.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        buttonPanelLogout.setOpaque(false);

        JButton btnDangXuat = SharedComponents.createLogoutButton(e -> {
            frame.dispose();
            com.uit.vaccinemanagement.VaccineManagement.showLogin();
        });
        btnDangXuat.setPreferredSize(buttonSize);
        buttonPanelLogout.add(btnDangXuat);

        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(buttonPanelLogout);

        btnChiDinhTiem.addActionListener((ActionEvent e) -> {
            ChiDinhTiemDialog dialog = new ChiDinhTiemDialog(frame, currentUser, bacSiController);
            dialog.setVisible(true);
        });

        // Right panel (column 2) with CardLayout
        JPanel rightPanel = new JPanel(new CardLayout());
        CardLayout cardLayout = (CardLayout) rightPanel.getLayout();

        // Create TiemChungPanel and KhachHangPanel
        TiemChungPanel tiemChungPanel = new TiemChungPanel(frame, bacSiController, currentUser);
        KhachHangPanel khachHangPanel = new KhachHangPanel(frame, bacSiController, currentUser);

        // Add panels to cardlayout with unique names
        rightPanel.add(tiemChungPanel, "TIEMCHUNG");
        rightPanel.add(khachHangPanel, "KHACHHANG");

        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(220);
        frame.getContentPane().add(splitPane);

        // Show TiemChungPanel by default
        cardLayout.show(rightPanel, "TIEMCHUNG");
        frame.setSize(1180, 500);

        // Button action listeners
        btnTiemChung.addActionListener((ActionEvent e) -> {
            cardLayout.show(rightPanel, "TIEMCHUNG");
            frame.setSize(1180, 500);
        });

        btnKhachHang.addActionListener((ActionEvent e) -> {
            cardLayout.show(rightPanel, "KHACHHANG");
            frame.setSize(1250, 500);
        });

        frame.setVisible(true);
    }
}
