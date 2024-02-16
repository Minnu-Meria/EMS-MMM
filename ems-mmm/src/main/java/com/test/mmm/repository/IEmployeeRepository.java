package com.test.mmm.repository;

import java.util.List;

import com.test.mmm.data.Employee;

public interface IEmployeeRepository {

	List<Employee> getAllEmployees();

	Employee findById(String empId);

	Employee addEmployee(Employee employee);

	List<Employee> getEmployeesByName(String name);

	List<Employee> getEmployeesByFromSalary(int fromSalary);

	List<Employee> getEmployeesByToSalary(int toSalary);

	List<Employee> getEmployeesBySalaryRange(int fromSalary, int toSalary);
}
