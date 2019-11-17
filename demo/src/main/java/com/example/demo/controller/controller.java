package com.example.demo.controller;

import java.util.List;
import java.util.Optional;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import com.example.demo.dao.Employee;
import com.example.demo.dao.EmployeeDAOImple;
import com.example.demo.service.EmployeeServiceImple;

@RestController
public class controller {

	@Autowired
	EmployeeServiceImple imp;
	
	@Autowired
	KafkaTemplate<String, Employee> kafkaTemplate;
	
	private static final String TOPIC="Demo_topic";
	//----- fetch all employees record-------
	
	@GetMapping("/data")
	public ResponseEntity<List<Employee>>  getAllEmployee() {
		
		List<Employee> employees= imp.getEmployee();
		if(employees.isEmpty()) {
			return new ResponseEntity<List<Employee>>(HttpStatus.OK);
		}
		
		return new ResponseEntity<List<Employee>>(employees, HttpStatus.FOUND);
		
	}
	
	//-----fetch employee record using id -----------
	
	
	@GetMapping("/data/{empid}")
	public ResponseEntity<Optional<Employee>> getEmployeeById(@PathVariable String empid){
		
		Integer id= Integer.parseInt(empid);
		Optional<Employee> employee= imp.getEmployeeById(id);
		if(!employee.isPresent()) {
			return new ResponseEntity<Optional<Employee>>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Optional<Employee>>(employee,HttpStatus.FOUND);
		
	}
	
	//------fetch employee data using employee name-----
	
	@GetMapping("/data/name/{empname}")
	public ResponseEntity<Optional<List<Employee>>> getEmployeeByName(@PathVariable String empname){
		
		
		Optional<List<Employee>> employee= imp.getEmployeeByName(empname);
		if(!employee.isPresent()) {
			return new ResponseEntity<Optional<List<Employee>>>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Optional<List<Employee>>>(employee,HttpStatus.FOUND);
		
	}
	
	// -------add new employee--------
	
	@PostMapping("/data")
	public ResponseEntity<Employee> add(@RequestBody Employee da,UriComponentsBuilder ucBuilder) {
		
		boolean isEmpPresant= imp.isPresant(da);
		
		if(isEmpPresant) {
			return new ResponseEntity<Employee>(HttpStatus.CONFLICT);
			
		}
		
		Employee new_emp=imp.add(da);
		
		
		kafkaTemplate.send(TOPIC, da);
		
		
		HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/data/{id}").buildAndExpand(new_emp.getId()).toUri());
        
        return new ResponseEntity<Employee>(new_emp,headers, HttpStatus.CREATED);
		
		 
	}
	
	//------delete employee by ID-------
	
	@DeleteMapping("/data/{empid}")
	public ResponseEntity<String> deleteById(@PathVariable int empid)
	{
		Integer id= Integer.valueOf(empid);
		Optional<Employee> employee=imp.getById(id);
		
		if(!employee.isPresent()) {
			return new ResponseEntity<String>("Data Not Found",HttpStatus.NOT_FOUND);
		}
		String msg=imp.deleteById(id);
		return new ResponseEntity<String>(msg,HttpStatus.NOT_FOUND);
		
	}

	//----update dept name using employee id.-------

	@PutMapping("/data/{empid}")
	public ResponseEntity<Employee> UpdateEmployeeDept(@PathVariable int empid , @RequestBody Employee employee,UriComponentsBuilder ucBuilder ){
		
		Integer id= Integer.valueOf(empid);
		Employee emp=imp.getById(id).orElse(new Employee());
		
		if(emp.getName()==null) {
			return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
		}
		
		Employee emp1=imp.updateEmployeeDept(id,employee.getDept());
		HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/data/{id}").buildAndExpand(emp1.getId()).toUri());
		return new ResponseEntity<Employee>(emp1,HttpStatus.ACCEPTED);
		
	}

	
	
}
