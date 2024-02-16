package com.test.mmm.repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.test.mmm.data.Employee;

import aj.org.objectweb.asm.TypeReference;

@Repository
public class EmployeeRepository implements IEmployeeRepository {
	private static List<Employee> employees = new ArrayList<Employee>();
	private static List<Employee> employeesDB = new ArrayList<Employee>();

	private final ObjectMapper objectMapper = new ObjectMapper();
	JsonNode json;
	DateTimeFormatter newPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

	{
		try (InputStream inputStream = TypeReference.class.getResourceAsStream("/static/employee.json")) {
			json = objectMapper.readValue(inputStream, JsonNode.class);
		} catch (IOException e) {
			throw new RuntimeException("Failed to read JSON data", e);
		}

		try {
			for (JsonNode node : json) {
				String id = node.get("id").asText();
				String firstName = node.get("firstName").asText();
				String lastName = node.get("lastName").asText();
				String dateOfBirth = node.get("dateOfBirth").asText();
				int salary = node.get("salary").asInt();
				String dateOfJoining = node.get("dateOfJoining").asText();

				String department = node.get("department").asText();

				Employee e = new Employee(id, firstName, lastName, dateOfBirth, salary, dateOfJoining, department);

				employees.add(e);

				employeesDB = new ArrayList<>(employees);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JsonNode getNode(JsonNode json) {
		return Optional.ofNullable(json).orElseThrow(() -> new IllegalArgumentException("Invalid JSON Object"));
	}

	@Override
	public List<Employee> getAllEmployees() {
		return new ArrayList<>(employees);
	}

	@Override
	public Employee findById(String id) {
		for (int i = 0; i < employees.size(); i++) {
			if (employees.get(i).getId().equalsIgnoreCase(id)) {
				return employees.get(i);
			}
		}
		return null;
	}

	@Override
	public Employee addEmployee(Employee p) {

		Employee emp = new Employee();
		emp.setId(p.getId());
		emp.setFirstName(p.getFirstName());
		emp.setLastName(p.getLastName());
		emp.setDateOfBirth(p.getDateOfBirth());
		emp.setSalary(p.getSalary());
		emp.setDateOfJoining(p.getDateOfJoining());
		emp.setDepartment(p.getDepartment());

		employeesDB.add(emp);

		ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
		PrintWriter out;
		try {

			out = new PrintWriter(new BufferedWriter(new FileWriter("src/main/resources/static/employee.json", false)));
			writer.writeValue(out, employeesDB);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return emp;
	}

	@Override
	public List<Employee> getEmployeesByName(String name) {

		List<Employee> empsByName = new ArrayList<Employee>();

		empsByName = employees.stream().filter(emp -> name.contains(emp.getFirstName())).collect(Collectors.toList());

		List<Employee> empsByNameLast = employees.stream().filter(emp -> name.contains(emp.getLastName()))
				.collect(Collectors.toList());

		List<Employee> result = Stream.concat(empsByName.stream(), empsByNameLast.stream()).distinct()
				.collect(Collectors.toList());

		return result;
	}

	@Override
	public List<Employee> getEmployeesByFromSalary(int fromSalary) {

		List<Employee> emps = new ArrayList<Employee>();

		emps = employees.stream().filter(emp -> emp.getSalary() >= fromSalary).collect(Collectors.toList());

		return emps;
	}

	@Override
	public List<Employee> getEmployeesByToSalary(int toSalary) {

		List<Employee> emps = new ArrayList<Employee>();

		emps = employees.stream().filter(emp -> emp.getSalary() <= toSalary).collect(Collectors.toList());

		return emps;
	}

	@Override
	public List<Employee> getEmployeesBySalaryRange(int fromSalary, int toSalary) {

		List<Employee> empstoSalary = new ArrayList<Employee>();

		empstoSalary = employees.stream().filter(emp -> emp.getSalary() <= toSalary).collect(Collectors.toList());

		List<Employee> emps = empstoSalary.stream().filter(emp -> emp.getSalary() >= fromSalary)
				.collect(Collectors.toList());

		return emps;
	}

}
