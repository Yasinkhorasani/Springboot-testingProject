package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Employee saveEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Optional<Employee> getEmployeeById(Long id);
    Employee updeteEmployee(Employee updeteEmployee);
    void deleteEmployee(Long id);

}