package com.test.mmm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.test.mmm.data.Employee;

@Service
public interface IEmployeeService {

	List<Employee> getAllEmployees();

	Employee findById(String empId);

	Employee addEmployee(Employee employee);

	List<Employee> getEmployeesByName(String name);

	List<Employee> getEmployeesByFromSalary(int fromSalary);

	List<Employee> getEmployeesByToSalary(int toSalary);

	List<Employee> getEmployeesBySalaryRange(int fromSalary, int toSalary);

}
