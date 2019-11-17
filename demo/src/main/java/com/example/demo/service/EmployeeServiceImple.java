package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dao.Employee;
import com.example.demo.dao.EmployeeDAOImple;

@Component
public class EmployeeServiceImple {

	

	
	
	@Autowired
	EmployeeDAOImple imp;
	
	public List<Employee> getEmployee(){
		return imp.getEmployee();
		
	}
	
	
	public Optional<Employee> getEmployeeById(Integer Id){
		
		return imp.getEmployeeById(Id);
		
	}
	public Employee add(Employee da) {
		return imp.addEmployee(da);
		
	}
	
	public Optional<Employee> getById(Integer id) {
		
		return imp.getEmployeeById(id);
		
	}
	
	public String deleteById(Integer id) {
		
		String n= imp.deleteEmployeeById(id);
		
		return n;
		
	}
	
	public boolean isPresant(Employee emp){
		
		return imp.isPresant(emp);
	}
	
	public Employee updateEmployeeDept(Integer id, String Name) {
		return imp.updateEmployeeDept(id,Name);
		
	}


	public Optional<List<Employee>> getEmployeeByName(String empname) {
		
		Optional<List<Employee>> emp= imp.getEmployeeByName(empname);
		return emp;
	}
}
