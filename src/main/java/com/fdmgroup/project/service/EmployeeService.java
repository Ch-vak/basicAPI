package com.fdmgroup.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fdmgroup.project.model.Employee;
import com.fdmgroup.project.repository.EmployeeRepository;
/**
 * Manages the Communication with the Repository 
 * Has Customized methods. findAllByAddressKeyword , which attempts to match and return whole address
 *
 * <p>Being implemented from Controller. At this point everything has been tested
 * For additional error messages please watch the console.
 * In case of an error refer for naming conventions
 * @author Chrysostomos Vakasiras
 */

@Service
public class EmployeeService {

	private final EmployeeRepository empRepo;

	
	public EmployeeService(EmployeeRepository empRep) {
		super();
		this.empRepo = empRep;
	}
	
	public Employee save(Employee emp) {
		return empRepo.save(emp);
	}
	
	public List<Employee> findAll(){
		return empRepo.findAll();
	}
	
	public Optional<Employee> findById(long id){
		return empRepo.findById((int) id);
	}
	
	public List<Employee> findAllByAddressKeyword(String keyword){
		return empRepo.findAllByAddressKeyword(keyword);
	}
	
	public Optional<Employee> findByLastName( String lastName){
		return empRepo.findByLastName( lastName);
	}

	public Optional<Employee> findByFirstName(String firstName) {
		return empRepo.findByFirstName(firstName);
	}
}
