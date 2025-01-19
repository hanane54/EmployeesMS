package com.employeemanagement.ui.components;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class EmployeeForm extends JPanel {
    private JTextField fullNameField;
    private JTextField employeeIdField;
    private JTextField jobTitleField;
    private JTextField departmentField;
    private JTextField hireDateField;
    private JTextField employmentStatusField;
    private JTextField contactInfoField;
    private JTextField addressField;

    public EmployeeForm() {
        setLayout(new MigLayout("wrap 2", "[right]10[grow]"));

        fullNameField = new JTextField(20);
        employeeIdField = new JTextField(20);
        jobTitleField = new JTextField(20);
        departmentField = new JTextField(20);
        hireDateField = new JTextField(20);
        employmentStatusField = new JTextField(20);
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
        add(new JLabel("Employment Status:"));
        add(employmentStatusField, "growx, wrap");
        add(new JLabel("Contact Info:"));
        add(contactInfoField, "growx, wrap");
        add(new JLabel("Address:"));
        add(addressField, "growx, wrap");
    }

    public String getFullName() {
        return fullNameField.getText();
    }

    public String getEmployeeId() {
        return employeeIdField.getText();
    }

    public String getJobTitle() {
        return jobTitleField.getText();
    }

    public String getDepartment() {
        return departmentField.getText();
    }

    public String getHireDate() {
        return hireDateField.getText();
    }

    public String getEmploymentStatus() {
        return employmentStatusField.getText();
    }

    public String getContactInfo() {
        return contactInfoField.getText();
    }

    public String getAddress() {
        return addressField.getText();
    }
}