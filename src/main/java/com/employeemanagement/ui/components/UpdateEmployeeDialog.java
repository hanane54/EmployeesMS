package com.employeemanagement.ui.components;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateEmployeeDialog extends JDialog {
    private JTextField fullNameField, employeeIdField, jobTitleField, departmentField, hireDateField, statusField, contactInfoField, addressField;
    private JButton saveButton, cancelButton;
    private String employeeId;

    public UpdateEmployeeDialog(JFrame parent, String employeeId) {
        super(parent, "Update Employee", true);
        this.employeeId = employeeId;
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

        // Pre-fill the form with employee data
        fetchEmployeeData();

        // Event Listeners
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEmployee();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void fetchEmployeeData() {
        // Call the backend API to fetch employee data by ID
        String apiUrl = "http://localhost:8080/api/employees/" + employeeId;
        try {
            String response = sendGetRequest(apiUrl);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error fetching employee data: " + ex.getMessage());
        }
    }

    private void updateEmployee() {
        String apiUrl = "http://localhost:8080/api/employees/" + employeeId;
        try {
            String requestBody = buildUpdateRequestBody();
            sendPutRequest(apiUrl, requestBody);
            JOptionPane.showMessageDialog(this, "Employee updated successfully.");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating employee: " + ex.getMessage());
        }
    }

    private String buildUpdateRequestBody() {
        return String.format(
            "{\"fullName\": \"%s\", \"jobTitle\": \"%s\", \"department\": \"%s\", \"employmentStatus\": \"%s\", \"contactInfo\": \"%s\", \"address\": \"%s\"}",
            fullNameField.getText(),
            jobTitleField.getText(),
            departmentField.getText(),
            statusField.getText(),
            contactInfoField.getText(),
            addressField.getText()
        );
    }

    private String sendGetRequest(String url) throws Exception {
        java.net.HttpURLConnection connection = (java.net.HttpURLConnection) new java.net.URL(url).openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            throw new Exception("HTTP error code: " + responseCode);
        }
    }

    private void sendPutRequest(String url, String requestBody) throws Exception {
        java.net.HttpURLConnection connection = (java.net.HttpURLConnection) new java.net.URL(url).openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (java.io.OutputStream os = connection.getOutputStream()) {
            byte[] input = requestBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new Exception("HTTP error code: " + responseCode);
        }
    }
}