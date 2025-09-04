package com.uit.vaccinemanagement.view.components.common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ActionButtonPanel extends JPanel {
    private final JButton btnEdit;
    private final JButton btnDelete;

    public ActionButtonPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        
        btnEdit = new JButton("✎");
        btnDelete = new JButton("🗑");

        btnEdit.setPreferredSize(new Dimension(50, 25));
        btnDelete.setPreferredSize(new Dimension(50, 25));

        add(btnEdit);
        add(btnDelete);
    }

    public void setEditAction(ActionListener listener) {
        for (ActionListener al : btnEdit.getActionListeners()) {
            btnEdit.removeActionListener(al);
        }
        btnEdit.addActionListener(listener);
    }

    public void setDeleteAction(ActionListener listener) {
        for (ActionListener al : btnDelete.getActionListeners()) {
            btnDelete.removeActionListener(al);
        }
        btnDelete.addActionListener(listener);
    }
}
