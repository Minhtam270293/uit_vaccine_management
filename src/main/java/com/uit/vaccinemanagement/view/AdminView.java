package com.uit.vaccinemanagement.view;

import javax.swing.*;
import java.awt.event.*;
import com.uit.vaccinemanagement.controller.AdminController;

public class AdminView {

    private AdminController controller;

    public AdminView(AdminController controller) {
        this.controller = controller;
        showAdminUI();
    }

    public void showAdminUI() {
        JFrame frame = new JFrame("Admin Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("Chức năng quản trị viên:"));

        JButton btnVaccine = new JButton("Quản lý Vaccine");
        panel.add(btnVaccine);

        JButton btnBenh = new JButton("Quản lý Bệnh");
        panel.add(btnBenh);

        JButton btnNhaSanXuat = new JButton("Quản lý Nhà Sản Xuất");
        panel.add(btnNhaSanXuat);

        JButton btnThongTinTiemChung = new JButton("Thông tin tiêm chủng");
        panel.add(btnThongTinTiemChung);

        JButton btnGiaoDich = new JButton("Giao dịch mua vaccine");
        panel.add(btnGiaoDich);

        JButton btnEditInfo = new JButton("Chỉnh sửa thông tin cá nhân");
        panel.add(btnEditInfo);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
}
