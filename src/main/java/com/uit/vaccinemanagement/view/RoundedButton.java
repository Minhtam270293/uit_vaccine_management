package com.uit.vaccinemanagement.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Nút bo góc cho menu
public class RoundedButton extends JButton {
    public RoundedButton(String text) {
        super(text);
        setFocusPainted(false);
        setOpaque(false);
        setFont(new Font("Arial", Font.BOLD, 15));
        setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
        setForeground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color bg = new Color(60, 130, 220);
        if (getModel().isPressed()) {
            bg = new Color(40, 110, 200);
        } else if (getModel().isRollover()) {
            bg = new Color(80, 150, 240);
        }
        g2.setColor(bg);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);

        // Vẽ text
        FontMetrics fm = g2.getFontMetrics();
        String text = getText();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() + textHeight) / 2 - 2;
        g2.setColor(getForeground());
        g2.drawString(text, x, y);

        g2.dispose();
    }
}

// Nút bo góc cho Đăng xuất (cam đậm, bo 16px)
class LogoutButton extends JButton {
    public LogoutButton(String text) {
        super(text);
        setFocusPainted(false);
        setOpaque(false);
        setFont(new Font("Arial", Font.BOLD, 15));
        setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
        setForeground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color bg = new Color(230, 120, 30); // cam đậm
        if (getModel().isPressed()) {
            bg = new Color(200, 90, 0);
        } else if (getModel().isRollover()) {
            bg = new Color(255, 140, 40);
        }
        g2.setColor(bg);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);

        // Vẽ text
        FontMetrics fm = g2.getFontMetrics();
        String text = getText();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() + textHeight) / 2 - 2;
        g2.setColor(getForeground());
        g2.drawString(text, x, y);

        g2.dispose();
    }
}

// Nút bo góc cho Đăng nhập (xanh dương) và Đăng ký (cam đậm), bo 16px
class AuthButton extends JButton {
    private final Color baseColor;
    private final int arc;

    public AuthButton(String text, Color color) {
        super(text);
        this.baseColor = color;
        this.arc = 16;
        setFocusPainted(false);
        setOpaque(false);
        setFont(new Font("Arial", Font.BOLD, 15));
        setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
        setForeground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color bg = baseColor;
        if (getModel().isPressed()) {
            bg = bg.darker();
        } else if (getModel().isRollover()) {
            bg = bg.brighter();
        }
        g2.setColor(bg);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        // Vẽ text
        FontMetrics fm = g2.getFontMetrics();
        String text = getText();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() + textHeight) / 2 - 2;
        g2.setColor(getForeground());
        g2.drawString(text, x, y);

        g2.dispose();
    }
}

