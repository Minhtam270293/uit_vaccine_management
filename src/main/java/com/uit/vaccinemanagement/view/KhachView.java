package com.uit.vaccinemanagement.view;

import com.uit.vaccinemanagement.model.NguoiDung;
import com.uit.vaccinemanagement.dao.TiemChungDAO;

import javax.swing.*;
import com.uit.vaccinemanagement.view.SharedComponents;
import com.uit.vaccinemanagement.view.RoundedButton;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class KhachView {

    private final NguoiDung currentUser;
    private final TiemChungDAO tiemChungDAO = new TiemChungDAO();

    public KhachView(NguoiDung currentUser) {
        this.currentUser = currentUser;
    }

    public void showKhachUI() {
        JFrame frame = new JFrame("Khách Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);

        // ===== Left panel =====
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

        // Khung 2: Các nút bấm
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        Dimension buttonSize = new Dimension(180, 35);
        RoundedButton btnLichTiem = new RoundedButton("Xem lịch sử tiêm");
        btnLichTiem.setPreferredSize(buttonSize);
        buttonPanel.add(btnLichTiem);
        leftPanel.add(buttonPanel);

        // khoảng cách với logout
        leftPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        // Khung 3: Nút Đăng Xuất (panel riêng, đưa xuống cuối sidebar)
        JPanel buttonPanelLogout = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
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

        // ===== Right panel =====
        JPanel rightPanel = new JPanel(new BorderLayout());

        // 1. Title
        JLabel tableTitle = new JLabel("Thông tin", SwingConstants.CENTER);
        tableTitle.setFont(new Font("Arial", Font.BOLD, 18));
        tableTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // 2. Search bar
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        JTextField searchField = new JTextField() {
            private boolean showingPlaceholder = true;

            {
                setLayout(new BorderLayout());
                JLabel placeholder = new JLabel("🔍 Tìm kiếm lịch sử tiêm", SwingConstants.LEFT);
                placeholder.setForeground(Color.GRAY);
                placeholder.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
                add(placeholder, BorderLayout.CENTER);

                addFocusListener(new java.awt.event.FocusAdapter() {
                    @Override
                    public void focusGained(java.awt.event.FocusEvent e) {
                        if (showingPlaceholder) {
                            remove(placeholder);
                            showingPlaceholder = false;
                            repaint();
                        }
                    }

                    @Override
                    public void focusLost(java.awt.event.FocusEvent e) {
                        if (getText().isEmpty()) {
                            add(placeholder, BorderLayout.CENTER);
                            showingPlaceholder = true;
                            repaint();
                        }
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE); // Đảm bảo nền luôn màu trắng
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        searchField.setOpaque(false); // Đảm bảo nền không bị ghi đè
        searchField.setBackground(Color.WHITE); // Đặt màu nền trắng
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            new SharedComponents.RoundedBorder(Color.LIGHT_GRAY, 1, 16), // sử dụng custom border bo góc 16px
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        topPanel.add(Box.createHorizontalStrut(5));
        topPanel.add(searchField);
        topPanel.add(Box.createHorizontalStrut(20));
        JButton btnRefresh = new JButton("Làm mới");
        topPanel.add(btnRefresh);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Gói Title + Search
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(tableTitle, BorderLayout.NORTH);
        headerPanel.add(topPanel, BorderLayout.SOUTH);
        rightPanel.add(headerPanel, BorderLayout.NORTH);

        // 3. Table dữ liệu
        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // 4. Phân trang
        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnPrev = new JButton("<<");
        JButton btnNext = new JButton(">>");
        JLabel lblPageInfo = new JLabel("Trang 1/1");
        paginationPanel.add(btnPrev);
        paginationPanel.add(lblPageInfo);
        paginationPanel.add(btnNext);
        rightPanel.add(paginationPanel, BorderLayout.SOUTH);

        // ===== Split pane =====
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(220);
        frame.getContentPane().add(splitPane);

        frame.setVisible(true);

        // Event handling
        // Logout button event is now handled by SharedComponents
        // ====== Xử lý button ở panel trái ======
        btnLichTiem.addActionListener((ActionEvent e) -> {
            tableTitle.setText("LỊCH SỬ TIÊM CHỦNG");

            // Lấy dữ liệu từ DAO
            List<Object[]> data = tiemChungDAO.getByMaKhach(currentUser.getMaNguoiDung());
            String[] columns = {"Mã tiêm chủng", "Ngày tiêm", "Tên vắc xin", "Bác sĩ phụ trách", "Trạng thái tiêm", "Ghi chú"};

            // Tạo model mới
            DefaultTableModel model = new DefaultTableModel(columns, 0);
            for (Object[] row : data) {
                model.addRow(row);
            }
            table.setModel(model);

            table.setRowHeight(30); // Đặt chiều cao dòng giống bảng quản lý người dùng

            // Căn giữa cho các cột số (ví dụ: 0, 2, 3)
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
            table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
            table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        });

        frame.setVisible(true);
    }
}
