package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface EmployeeDAO  extends CrudRepository<Employee, Integer>{

	boolean existsByName(String name);

	Optional<List<Employee>> findByName(String empname);
	

	 
		
		
	

}
