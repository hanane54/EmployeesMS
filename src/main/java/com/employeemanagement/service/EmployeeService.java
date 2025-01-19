package com.employeemanagement.service;

import com.employeemanagement.entity.Employee;
import com.employeemanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee != null) {
            employee.setFullName(employeeDetails.getFullName());
            employee.setJobTitle(employeeDetails.getJobTitle());
            employee.setDepartment(employeeDetails.getDepartment());
            employee.setEmploymentStatus(employeeDetails.getEmploymentStatus());
            employee.setContactInfo(employeeDetails.getContactInfo());
            employee.setAddress(employeeDetails.getAddress());
            return employeeRepository.save(employee);
        }
        return null;
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> searchEmployees(String searchTerm, String department, String status) {
        if (department != null && !department.equals("All Departments")) {
            if (status != null && !status.equals("All Statuses")) {
                return employeeRepository.findByDepartmentAndEmploymentStatus(department, status);
            }
            return employeeRepository.findByDepartment(department);
        } else if (status != null && !status.equals("All Statuses")) {
            return employeeRepository.findByEmploymentStatus(status);
        } else if (searchTerm != null && !searchTerm.isEmpty()) {
            return employeeRepository.findByFullNameContainingIgnoreCase(searchTerm);
        }
        return employeeRepository.findAll();
    }
}