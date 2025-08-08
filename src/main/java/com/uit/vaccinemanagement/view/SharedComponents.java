package com.uit.vaccinemanagement.view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class SharedComponents {
    public static JButton createLogoutButton(ActionListener logoutAction) {
        JButton btnDangXuat = new JButton("Đăng Xuất");
        btnDangXuat.addActionListener(logoutAction);
        return btnDangXuat;
    }
    // Add more shared UI components here as needed
}
