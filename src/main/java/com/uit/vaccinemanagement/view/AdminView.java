package com.uit.vaccinemanagement.view;

import com.uit.vaccinemanagement.controller.AdminController;
import java.awt.event.ActionEvent;
import java.util.List;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import javax.swing.JComboBox;
import javax.swing.JDialog;

import com.uit.vaccinemanagement.dao.NguoiDungDAO;
import com.uit.vaccinemanagement.dao.NhaSanXuatDAO;
import com.uit.vaccinemanagement.dao.VaccineDAO;
import com.uit.vaccinemanagement.model.NguoiDung;
import com.uit.vaccinemanagement.model.Vaccine;
import com.uit.vaccinemanagement.view.panels.*;

import javax.swing.*;
import java.awt.*;

public class AdminView {

    private static final String NGUOIDUNG_PANEL = "NGUOIDUNG_PANEL";
    private static final String VACCINE_PANEL = "VACCINE_PANEL";
    private static final String NHASX_PANEL = "NHASX_PANEL";
    private static final String BENH_PANEL = "BENH_PANEL";

    private final NguoiDung currentUser;
    private final AdminController adminController;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public AdminView(NguoiDung currentUser) {
        this.currentUser = currentUser;
        this.adminController = new AdminController();
    }

    public void showAdminUI() {
        JFrame frame = new JFrame("Admin Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1700, 800);

        // Left panel (column 1)
        // Side Navigator
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(600, 200));

        // Khung 1: Tên và Vai trò (bo tròn, căn giữa, có icon)
        JPanel infoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(245, 245, 245)); // nền xám nhạt
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

        // Sử dụng emoji Unicode thay cho icon hệ thống để tránh lỗi
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

        // Khung 2: Các nút bấm
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));  // Căn giữa với khoảng cách dọc là 10px
        // Đảm bảo bạn dùng RoundedButton cho 3 nút sidebar như sau:
        RoundedButton btnNguoiDung = new RoundedButton("Người dùng");
        RoundedButton btnVaccine = new RoundedButton("Vắc xin");
        RoundedButton btnBenh = new RoundedButton("Bệnh");
        RoundedButton btnNhaSanXuat = new RoundedButton("Nhà sản xuất");
        // Đặt kích thước đồng đều cho các nút
        Dimension buttonSize = new Dimension(200, 40);
        btnNguoiDung.setPreferredSize(buttonSize);
        btnVaccine.setPreferredSize(buttonSize);
        btnBenh.setPreferredSize(buttonSize);
        btnNhaSanXuat.setPreferredSize(buttonSize);

        // Thêm các nút vào buttonPanel
        buttonPanel.add(btnNguoiDung);
        buttonPanel.add(btnVaccine);
        buttonPanel.add(btnBenh);
        buttonPanel.add(btnNhaSanXuat);
        
        btnNguoiDung.addActionListener(e -> cardLayout.show(cardPanel, NGUOIDUNG_PANEL));
        btnVaccine.addActionListener(e -> cardLayout.show(cardPanel, VACCINE_PANEL));
        btnBenh.addActionListener(e -> cardLayout.show(cardPanel, BENH_PANEL));
        btnNhaSanXuat.addActionListener(e -> cardLayout.show(cardPanel, NHASX_PANEL));

        btnNguoiDung.addActionListener(e -> {
            cardLayout.show(cardPanel, NGUOIDUNG_PANEL);
            frame.setSize(1700, 800); // set cố định
        });

        btnVaccine.addActionListener(e -> {
            cardLayout.show(cardPanel, VACCINE_PANEL);
            frame.setSize(1570, 800);
        });

        btnBenh.addActionListener(e -> {
            cardLayout.show(cardPanel, BENH_PANEL);
            frame.setSize(1118, 800);
        });

        btnNhaSanXuat.addActionListener(e -> {
            cardLayout.show(cardPanel, NHASX_PANEL);
            frame.setSize(1018, 800);
        });


        // Thêm buttonPanel vào leftPanel
        leftPanel.add(buttonPanel);
        // Thêm khoảng cách 20px giữa khung 2 và khung 3
        leftPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        // Khung 3: Nút Đăng Xuất (panel riêng, đưa xuống cuối sidebar)
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

        leftPanel.add(Box.createVerticalGlue()); // đẩy logout xuống cuối
        leftPanel.add(buttonPanelLogout);

        // Create and setup CardLayout for the right panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Add panels
        cardPanel.add(new NguoiDungPanel(frame, adminController), NGUOIDUNG_PANEL);
        cardPanel.add(new VaccinePanel(frame, adminController), VACCINE_PANEL);
        cardPanel.add(new NhaSanXuatPanel(frame, adminController), NHASX_PANEL);
        cardPanel.add(new BenhPanel(frame, adminController), BENH_PANEL);

        // Create split pane and add panels
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(220);
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(cardPanel);

        frame.getContentPane().add(splitPane);
    
        frame.setVisible(true);
    }
}
