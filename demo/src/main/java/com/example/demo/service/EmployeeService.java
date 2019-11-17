package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.Employee;
import com.example.demo.dao.EmployeeDAOImple;


interface EmployeeService {
	

	
	public List<Employee> getEmployee();
	public Employee add(Employee da);
	public Employee getById(Integer id);
	public String deletebyId(Integer id);
	public Employee updateEmployeeDept(Integer id, String name);
	public Optional<List<Employee>> getEmployeeByName(String empname);
	
	
	
}
