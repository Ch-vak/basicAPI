package com.fdmgroup.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fdmgroup.project.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

	@Query("select e from Employee e where lower(e.address) like concat('%', lower(:keyword), '%')")
	public List<Employee> findAllByAddressKeyword(String keyword);

	public Optional<Employee> findByFirstName(String firstName);
	
	public Optional<Employee> findByLastName(String firstName);
		
}
