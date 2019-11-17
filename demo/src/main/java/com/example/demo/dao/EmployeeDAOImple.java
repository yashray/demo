package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDAOImple {
	
	@Autowired
	EmployeeDAO employeedao;
	
	public List<Employee> getEmployee() {
		
		List<Employee> da= (List<Employee>) employeedao.findAll();
		return da;
		
	}
	
	

	
	public Employee addEmployee(Employee da) {
		
		
		return employeedao.save(da);
		
	}

	public boolean isPresant(Employee emp) {
		
		return employeedao.existsByName(emp.getName());
	}

	public Optional<Employee> getEmployeeById(Integer id) {
		
		return employeedao.findById(id);
	}
	
	public String deleteEmployeeById(Integer id) {
		String msg;
		if(employeedao.existsById(id)) {
			employeedao.deleteById(id);
			msg="deleted";
			}else {
				msg="Id not found";
			}
	return msg;
  }



	public Employee updateEmployeeDept(Integer id, String name) {
		
		Employee emp= employeedao.findById(id).orElse(new Employee());
		emp.setDept(name);
		employeedao.save(emp);
		return emp;
		
		
	}




	public Optional<List<Employee>> getEmployeeByName(String empname) {
		
		Optional<List<Employee>> emp= employeedao.findByName(empname);
		return emp;
	}
}