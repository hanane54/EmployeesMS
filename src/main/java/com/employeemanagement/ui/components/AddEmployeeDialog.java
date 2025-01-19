package com.employeemanagement.ui.components;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class AddEmployeeDialog extends JDialog {
    private JTextField fullNameField, employeeIdField, jobTitleField, departmentField, hireDateField, statusField, contactInfoField, addressField;
    private JButton saveButton, cancelButton;

    public AddEmployeeDialog(JFrame parent) {
        super(parent, "Add Employee", true);
        setSize(400, 400);
        setLocationRelativeTo(parent);

        setLayout(new MigLayout("wrap 2", "[right]10[grow]"));

        fullNameField = new JTextField(20);
        employeeIdField = new JTextField(20);
        jobTitleField = new JTextField(20);
        departmentField = new JTextField(20);
        hireDateField = new JTextField(20);
        statusField = new JTextField(20);
        contactInfoField = new JTextField(20);
        addressField = new JTextField(20);

        add(new JLabel("Full Name:"));
        add(fullNameField, "growx, wrap");
        add(new JLabel("Employee ID:"));
        add(employeeIdField, "growx, wrap");
        add(new JLabel("Job Title:"));
        add(jobTitleField, "growx, wrap");
        add(new JLabel("Department:"));
        add(departmentField, "growx, wrap");
        add(new JLabel("Hire Date:"));
        add(hireDateField, "growx, wrap");
        add(new JLabel("Status:"));
        add(statusField, "growx, wrap");
        add(new JLabel("Contact Info:"));
        add(contactInfoField, "growx, wrap");
        add(new JLabel("Address:"));
        add(addressField, "growx, wrap");

        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        add(saveButton, "split 2, right");
        add(cancelButton);

        saveButton.addActionListener(e -> saveEmployee());
        cancelButton.addActionListener(e -> dispose());
    }

    private void saveEmployee() {
        JOptionPane.showMessageDialog(this, "Employee saved successfully.");
        dispose();
    }
}