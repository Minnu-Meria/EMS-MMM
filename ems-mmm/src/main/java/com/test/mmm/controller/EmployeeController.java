package com.test.mmm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.mmm.service.IEmployeeService;

import com.test.mmm.data.Employee;

@RestController
public class EmployeeController {

	@Autowired
	IEmployeeService iEmployeeService;

	@GetMapping("/employeesAll")
	public ResponseEntity<Object> getEmployees() {
		try {
			List<Employee> employee = iEmployeeService.getAllEmployees();
			if (!employee.isEmpty()) {
				return new ResponseEntity<>(employee, HttpStatus.OK);
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			ResponseEntity<Object> responseEntity = new ResponseEntity<>("THERE IS NO DATA", HttpStatus.BAD_REQUEST);
			return responseEntity;
		}
	}

	@GetMapping("/employees/{empId}")
	public ResponseEntity<Object> getEmployeeDetails(@PathVariable String empId) {
		try {
			Employee employee = iEmployeeService.findById(empId);
			if (employee != null) {
				return new ResponseEntity<>(employee, HttpStatus.OK);
			} else {
				throw new Exception();
			}
		} catch (Exception exception) {
			return new ResponseEntity<>("EMPLOYEE ID NOT EXISTS, PLEASE CHECK IT", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/employees")
	public ResponseEntity<Object> addEmployee(@RequestBody Employee emp) {
		try {
			Employee employee = iEmployeeService.addEmployee(emp);
			if (employee != null) {
				return new ResponseEntity<>(employee, HttpStatus.OK);
			} else {
				throw new Exception();
			}
		} catch (Exception exception) {
			return new ResponseEntity<>("Not Added", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employees")
	public ResponseEntity<Object> getEmployees(@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "0") int fromSalary, @RequestParam(defaultValue = "0") int toSalary) {
		List<Employee> employee = new ArrayList<Employee>();
		try {
			System.out.println("name :"+name);
			if (!name.isBlank()) {
				System.out.println("name :"+name);
				employee = iEmployeeService.getEmployeesByName(name);
			} else {

				if (fromSalary != 0 && toSalary != 0) {

					employee = iEmployeeService.getEmployeesBySalaryRange(fromSalary, toSalary);

				} else if (fromSalary != 0 && toSalary == 0) {

					employee = iEmployeeService.getEmployeesByFromSalary(fromSalary);

				} else {

					employee = iEmployeeService.getEmployeesByToSalary(toSalary);

				}
			}
			if (employee != null) {
				return new ResponseEntity<>(employee, HttpStatus.OK);
			} else {
				throw new Exception();
			}
		} catch (Exception exception) {
			return new ResponseEntity<>("EMPLOYEE DOES NOT EXISTS, PLEASE CHECK IT", HttpStatus.BAD_REQUEST);
		}
	}

}
