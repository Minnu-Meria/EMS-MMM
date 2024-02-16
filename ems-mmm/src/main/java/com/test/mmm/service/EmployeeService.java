package com.test.mmm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.mmm.data.Employee;
import com.test.mmm.repository.IEmployeeRepository;

@Service
public class EmployeeService implements IEmployeeService {

	@Autowired
	IEmployeeRepository iEmployeeRepository;

	@Override
	public List<Employee> getAllEmployees() {
		return iEmployeeRepository.getAllEmployees();
	}

	@Override
	public Employee findById(String empId) {
		return iEmployeeRepository.findById(empId);
	}

	@Override
	public Employee addEmployee(Employee employee) {
		return iEmployeeRepository.addEmployee(employee);
	}

	@Override
	public List<Employee> getEmployeesByName(String name) {
		return iEmployeeRepository.getEmployeesByName(name);
	}

	@Override
	public List<Employee> getEmployeesByFromSalary(int fromSalary) {
		return iEmployeeRepository.getEmployeesByFromSalary(fromSalary);
	}

	@Override
	public List<Employee> getEmployeesByToSalary(int toSalary) {
		return iEmployeeRepository.getEmployeesByToSalary(toSalary);
	}

	@Override
	public List<Employee> getEmployeesBySalaryRange(int fromSalary, int toSalary) {
		return iEmployeeRepository.getEmployeesBySalaryRange(fromSalary, toSalary);
	}
}
