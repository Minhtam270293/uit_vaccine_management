package com.uit.vaccinemanagement.view;

import javax.swing.*;

public class BacSiView {

    public void showBacSiUI() {
        JFrame frame = new JFrame("Bác sĩ Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 250);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("Chức năng bác sĩ:"));

        JButton btnListTiemChung = new JButton("Xem danh sách chỉ định đã thực hiện");
        JButton btnCreateTiemChung = new JButton("Tạo chỉ định tiêm mới cho khách");

        panel.add(btnListTiemChung);
        panel.add(btnCreateTiemChung);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
}
