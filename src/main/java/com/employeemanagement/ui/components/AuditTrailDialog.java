package com.employeemanagement.ui.components;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class AuditTrailDialog extends JDialog {
    private JTextArea auditLogArea;

    public AuditTrailDialog(JFrame parent) {
        super(parent, "Audit Trail", true);
        setSize(600, 400);
        setLocationRelativeTo(parent);

        setLayout(new MigLayout("fill"));

        auditLogArea = new JTextArea();
        auditLogArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(auditLogArea);
        add(scrollPane, "grow, push");

        fetchAuditLogs();
    }

    private void fetchAuditLogs() {
        auditLogArea.setText("Audit logs will be displayed here.");
    }
}