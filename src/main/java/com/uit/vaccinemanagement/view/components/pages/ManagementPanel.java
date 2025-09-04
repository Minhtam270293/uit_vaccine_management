package com.uit.vaccinemanagement.view.components.pages;

import com.uit.vaccinemanagement.controller.AdminController;
import com.uit.vaccinemanagement.view.components.common.PaginationPanel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public abstract class ManagementPanel<T> extends JPanel {
    protected static final int PAGE_SIZE = 20;
    protected final JTable table;
    protected final PaginationPanel paginationPanel;
    protected final AdminController controller;
    protected final String title;
    protected int currentPage = 1;
    protected int totalRecords = 0;
    
    protected ManagementPanel(AdminController controller, String title) {
        this.controller = controller;
        this.title = title;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Initialize components
        table = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        
        paginationPanel = new PaginationPanel();
        
        // Setup UI
        setupComponents();
        setupPagination();
    }
    
    private void setupComponents() {
        // Header panel with title and buttons
        JPanel headerPanel = new JPanel(new BorderLayout(0, 10));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Title
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        // Action buttons panel
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        
        JButton btnAdd = new JButton("Thêm mới");
        btnAdd.setPreferredSize(new Dimension(100, 30));
        btnAdd.setFocusPainted(false);
        btnAdd.addActionListener(e -> handleAdd());
        
        actionPanel.add(btnAdd);
        
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(actionPanel, BorderLayout.CENTER);
        
        // Create table model and configure table
        setupTable();
        
        // Add all components to panel
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(paginationPanel, BorderLayout.SOUTH);
    }
    
    private void setupPagination() {
        paginationPanel.setPaginationListener(page -> {
            currentPage = page;
            loadTableData(page);
        });
        paginationPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
    }
    
    protected void updatePagination() throws SQLException {
        totalRecords = getTotalRecords();
        int totalPages = (totalRecords + PAGE_SIZE - 1) / PAGE_SIZE;
        paginationPanel.setTotalPages(totalPages);
        paginationPanel.setCurrentPage(currentPage);
        paginationPanel.setTotalRecords(totalRecords);
    }
    
    protected boolean confirmDelete() {
        return JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa mục này không?", 
            "Xác nhận xóa", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION;
    }
    
    protected void showError(String message) {
        JOptionPane.showMessageDialog(this, 
            message, 
            "Lỗi", 
            JOptionPane.ERROR_MESSAGE);
    }
    
    protected void handleError(String message, Exception e) {
        showError(message + ": " + e.getMessage());
    }
    
    protected void initialize() {
        loadTableData(1);
        try {
            updatePagination();
        } catch (SQLException e) {
            handleError("Lỗi khi tải dữ liệu", e);
        }
    }

    /**
     * Setup the table model and column configuration.
     * This should be called once during initialization.
     */
    protected abstract void setupTable();

    /**
     * Load table data for the specified page.
     * @param page The page number to load (1-based)
     */
    protected abstract void loadTableData(int page);

    /**
     * Get the column names for the table.
     * @return Array of column names
     */
    protected abstract String[] getColumnNames();

    /**
     * Handle adding a new item.
     */
    protected abstract void handleAdd();

    /**
     * Handle editing the selected item.
     * @param selectedRow The index of the selected row
     */
    protected abstract void handleEdit(int selectedRow);

    /**
     * Handle deleting the selected item.
     * @param selectedRow The index of the selected row
     */
    protected abstract void handleDelete(int selectedRow);

    /**
     * Get the total number of records for pagination.
     * @return Total number of records
     * @throws SQLException if there's a database error
     */
    protected abstract int getTotalRecords() throws SQLException;
}

