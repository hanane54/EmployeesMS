package com.employeemanagement.repository;

import com.employeemanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByFullNameContainingIgnoreCase(String fullName);

    List<Employee> findByDepartment(String department);

    List<Employee> findByEmploymentStatus(String employmentStatus);

    List<Employee> findByDepartmentAndEmploymentStatus(String department, String employmentStatus);

    Employee findByEmployeeId(String employeeId);
}