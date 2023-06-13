package com.fdmgroup.project.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employeegen")
	@SequenceGenerator(name = "employeegen", sequenceName = "employee_id_seq", allocationSize = 1)
	private long id;

	private String firstName;
	private String lastName;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate hireDate;
	private String address;

	public Employee(String firstName, String lastName, LocalDate hireDate, String address) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.hireDate = hireDate;
		this.address = address;
	}

	public Employee() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getHireDate() {
		return hireDate;
	}

	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
