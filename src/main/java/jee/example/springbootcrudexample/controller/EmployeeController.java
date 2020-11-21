package jee.example.springbootcrudexample.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jee.example.springbootcrudexample.entity.Employee;
import jee.example.springbootcrudexample.exception.ResourceNotFoundException;
import jee.example.springbootcrudexample.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	//Get all employees
	@GetMapping
	public List<Employee> getAllEmployees(){
		return this.employeeRepository.findAll();
	}
	
	//Get employees by Id
	@GetMapping("/{id}")
	public Employee getEmployeeById(@PathVariable (value = "id") long employeeId) {
		return this.employeeRepository.findById(employeeId).orElseThrow(() ->
		new ResourceNotFoundException("There is no employee with the Id:" + employeeId));
	}
	
	//Create new employee
	@PostMapping
	public Employee createEmployee(@RequestBody Employee employee) {
		return this.employeeRepository.save(employee);
	}
	
	//Update an employee
	@PutMapping("/{id}")
	public Employee updateEmployee(@RequestBody Employee employee, @PathVariable ("id") long employeeId) {
		Employee currentEmployee = this.employeeRepository.findById(employeeId).orElseThrow(() ->
		new ResourceNotFoundException("There is no employee with the Id:" + employeeId));
		currentEmployee.setEmpFName(employee.getEmpFName());
		currentEmployee.setEmpLName(employee.getEmpLName());
		currentEmployee.setEmail(employee.getEmail());
		return this.employeeRepository.save(currentEmployee);
	}
	
	//Delete an employee by Id
	@DeleteMapping("/{id}")
	public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") long employeeId){
		Employee currentEmployee = this.employeeRepository.findById(employeeId).orElseThrow(() ->
		new ResourceNotFoundException("There is no employee with the Id:" + employeeId));
		this.employeeRepository.delete(currentEmployee);
		return ResponseEntity.ok().build();
	}
}
