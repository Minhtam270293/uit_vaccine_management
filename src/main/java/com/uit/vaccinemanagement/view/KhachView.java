package com.uit.vaccinemanagement.view;

import javax.swing.*;

public class KhachView {

    public void showKhachUI() {
        JFrame frame = new JFrame("Khách Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 250);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("Chức năng khách hàng:"));

        JButton btnMuaVaccine = new JButton("Mua vaccine");
        JButton btnLichTiem = new JButton("Xem lịch tiêm");
        JButton btnLichSuMua = new JButton("Xem lịch sử mua");

        panel.add(btnMuaVaccine);
        panel.add(btnLichTiem);
        panel.add(btnLichSuMua);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
}
