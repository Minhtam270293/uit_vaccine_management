package com.uit.vaccinemanagement.view.components.common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PaginationPanel extends JPanel {
    private final JButton btnPrev;
    private final JButton btnNext;
    private final JLabel lblPageInfo;
    private final JLabel lblTotalRows;
    
    public interface PaginationListener {
        void onPageChange(int newPage);
    }

    private PaginationListener listener;
    private int currentPage = 1;
    private int totalPages = 1;

    public PaginationPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        
        btnPrev = new JButton("<<");
        btnNext = new JButton(">>");
        lblPageInfo = new JLabel();
        lblTotalRows = new JLabel();

        btnPrev.setFocusPainted(false);
        btnNext.setFocusPainted(false);

        add(lblTotalRows);
        add(Box.createHorizontalStrut(20));
        add(btnPrev);
        add(lblPageInfo);
        add(btnNext);

        setupListeners();
        updateLabels();
    }

    private void setupListeners() {
        btnPrev.addActionListener(e -> {
            if (currentPage > 1 && listener != null) {
                currentPage--;
                updateLabels();
                listener.onPageChange(currentPage);
            }
        });

        btnNext.addActionListener(e -> {
            if (currentPage < totalPages && listener != null) {
                currentPage++;
                updateLabels();
                listener.onPageChange(currentPage);
            }
        });
    }

    private void updateLabels() {
        lblPageInfo.setText(String.format("Trang %d/%d", currentPage, totalPages));
        btnPrev.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage < totalPages);
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
        updateLabels();
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        updateLabels();
    }

    public void setTotalRecords(int totalRecords) {
        lblTotalRows.setText("Tổng số: " + totalRecords);
    }

    public void setPaginationListener(PaginationListener listener) {
        this.listener = listener;
    }

    public int getCurrentPage() {
        return currentPage;
    }
}
