package com.employeemanagement.ui;

import javax.swing.*;

import com.employeemanagement.ui.components.AddEmployeeDialog;
import com.employeemanagement.ui.components.AuditTrailDialog;
import com.employeemanagement.ui.components.UpdateEmployeeDialog;

import net.miginfocom.swing.MigLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeManagementUI extends JFrame {
    private JTextField searchField;
    private JButton searchButton, addButton, updateButton, deleteButton, viewAuditButton;
    private JComboBox<String> filterDepartment, filterStatus;
    private JTextArea resultArea;
    private JTable employeeTable;
    private JScrollPane tableScrollPane;

    public EmployeeManagementUI() {
        setTitle("Employee Records Management System");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new MigLayout("fill"));

        // Search and Filter Panel
        JPanel searchFilterPanel = new JPanel();
        searchFilterPanel.setLayout(new MigLayout("insets 10"));
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        filterDepartment = new JComboBox<>(new String[]{"All Departments", "HR", "IT", "Finance", "Operations"});
        filterStatus = new JComboBox<>(new String[]{"All Statuses", "Active", "Inactive"});
        searchFilterPanel.add(new JLabel("Search Employee:"));
        searchFilterPanel.add(searchField, "growx");
        searchFilterPanel.add(searchButton, "wrap");
        searchFilterPanel.add(new JLabel("Filter by Department:"));
        searchFilterPanel.add(filterDepartment, "growx");
        searchFilterPanel.add(new JLabel("Filter by Status:"));
        searchFilterPanel.add(filterStatus, "growx, wrap");
        add(searchFilterPanel, "north, growx");

        String[] columnNames = {"ID", "Full Name", "Employee ID", "Job Title", "Department", "Hire Date", "Status"};
        Object[][] data = {}; // Empty data for now
        employeeTable = new JTable(data, columnNames);
        tableScrollPane = new JScrollPane(employeeTable);
        add(tableScrollPane, "grow, push");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new MigLayout("insets 10"));
        addButton = new JButton("Add Employee");
        updateButton = new JButton("Update Employee");
        deleteButton = new JButton("Delete Employee");
        viewAuditButton = new JButton("View Audit Trail");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewAuditButton, "wrap");
        add(buttonPanel, "south, growx");

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText();
                String department = (String) filterDepartment.getSelectedItem();
                String status = (String) filterStatus.getSelectedItem();
                searchAndFilterEmployees(searchTerm, department, status);
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddEmployeeDialog();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openUpdateEmployeeDialog();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedEmployee();
            }
        });

        viewAuditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAuditTrail();
            }
        });
    }

    private void searchAndFilterEmployees(String searchTerm, String department, String status) {
        String apiUrl = "http://localhost:8080/api/employees?search=" + searchTerm +
                        "&department=" + department +
                        "&status=" + status;
        try {
            String response = sendGetRequest(apiUrl);
            updateEmployeeTable(response);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error fetching employee data: " + ex.getMessage());
        }
    }

    private void updateEmployeeTable(String response) {
        String[][] data = {}; 
        String[] columnNames = {"ID", "Full Name", "Employee ID", "Job Title", "Department", "Hire Date", "Status"};
        employeeTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    private void openAddEmployeeDialog() {
        AddEmployeeDialog dialog = new AddEmployeeDialog(this);
        dialog.setVisible(true);
    }

    private void openUpdateEmployeeDialog() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow >= 0) {
            String employeeId = (String) employeeTable.getValueAt(selectedRow, 2);
            UpdateEmployeeDialog dialog = new UpdateEmployeeDialog(this, employeeId);
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Please select an employee to update.");
        }
    }

    private void deleteSelectedEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow >= 0) {
            String employeeId = (String) employeeTable.getValueAt(selectedRow, 2);
            try {
                String apiUrl = "http://localhost:8080/api/employees/" + employeeId;
                sendDeleteRequest(apiUrl);
                JOptionPane.showMessageDialog(this, "Employee deleted successfully.");
                searchAndFilterEmployees("", "All Departments", "All Statuses"); 
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting employee: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an employee to delete.");
        }
    }

    private void viewAuditTrail() {
        AuditTrailDialog dialog = new AuditTrailDialog(this);
        dialog.setVisible(true);
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

    private void sendDeleteRequest(String url) throws Exception {
        java.net.HttpURLConnection connection = (java.net.HttpURLConnection) new java.net.URL(url).openConnection();
        connection.setRequestMethod("DELETE");

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new Exception("HTTP error code: " + responseCode);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmployeeManagementUI ui = new EmployeeManagementUI();
            ui.setVisible(true);
        });
    }
}