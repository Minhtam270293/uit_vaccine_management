package com.uit.vaccinemanagement.view.components.dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class BaseEditDialog extends JDialog {
    protected final JPanel formPanel;
    protected JButton btnSave;
    protected JButton btnCancel;
    protected boolean success = false;

    protected BaseEditDialog(JFrame parent, String title) {
        super(parent, title, true);
        this.formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        
        initializeComponents();
        setupLayout();
        setupActions();
    }

    private void initializeComponents() {
        btnSave = new JButton("Lưu");
        btnCancel = new JButton("Hủy");
    }

    private void setupLayout() {
        setLayout(new BorderLayout(5, 5));
        
        // Add form panel
        add(formPanel, BorderLayout.CENTER);
        
        // Add buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Set dialog properties
        setSize(400, 300);
        setLocationRelativeTo(getParent());
    }

    private void setupActions() {
        btnCancel.addActionListener(e -> dispose());
        btnSave.addActionListener(e -> {
            if (validateInput()) {
                success = saveData();
                if (success) {
                    dispose();
                }
            }
        });
    }

    protected void addFormField(String label, JComponent field) {
        formPanel.add(new JLabel(label));
        formPanel.add(field);
    }

    protected abstract boolean validateInput();
    protected abstract boolean saveData();
    
    public boolean showDialog() {
        setVisible(true);
        return success;
    }
}
