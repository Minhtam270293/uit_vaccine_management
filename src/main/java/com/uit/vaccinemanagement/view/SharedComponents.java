package com.uit.vaccinemanagement.view;

import javax.swing.*;
import java.awt.event.ActionListener;
import com.uit.vaccinemanagement.view.LogoutButton;

public class SharedComponents {
    public static JButton createLogoutButton(ActionListener logoutAction) {
        JButton btnDangXuat = new LogoutButton("Đăng xuất");
        btnDangXuat.addActionListener(logoutAction);
        return btnDangXuat;
    }
    // Add more shared UI components here as needed
}
