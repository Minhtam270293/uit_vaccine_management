package com.uit.vaccinemanagement.view;

import javax.swing.*;
import java.awt.event.ActionListener;
import com.uit.vaccinemanagement.view.LogoutButton;
import java.awt.*;  
import java.awt.geom.*;  

public class SharedComponents {
    public static JButton createLogoutButton(ActionListener logoutAction) {
        JButton btnDangXuat = new LogoutButton("Đăng xuất");
        btnDangXuat.addActionListener(logoutAction);
        return btnDangXuat;
    }
    // Add more shared UI components here as needed
    
    public static class RoundedBorder extends javax.swing.border.AbstractBorder {
        private final Color color;
        private final int thickness;
        private final int arc;
    
        public RoundedBorder(Color color, int thickness, int arc) {
            this.color = color;
            this.thickness = thickness;
            this.arc = arc;
        }
    
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(thickness));
            g2.drawRoundRect(x, y, width - 1, height - 1, arc, arc);
            g2.dispose();
        }
    
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(thickness, thickness, thickness, thickness);
        }
    
        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = insets.top = insets.bottom = thickness;
            return insets;
        }
    }
}
