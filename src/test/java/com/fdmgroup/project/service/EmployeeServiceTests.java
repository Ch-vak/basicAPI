package com.fdmgroup.project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fdmgroup.project.model.Employee;

@SpringBootTest
public class EmployeeServiceTests {

	@Autowired
	EmployeeService empService;

	Employee emp1, emp2, emp3;

	@BeforeEach
	void setUp() throws Exception {
		emp1 = new Employee("dummyFirst1", "dummyLast1", LocalDate.now(), "dummyAddress1");
		emp2 = new Employee("dummyFirst2", "dummyLast2", LocalDate.now(), "dummyAddress2");
		emp3 = new Employee("dummyFirst3", "dummyLast3", LocalDate.now(), "dummyAddress3");
		empService.save(emp1);
		empService.save(emp2);
		empService.save(emp3);

		assertTrue(emp1.getId() > 0);
		assertTrue(emp2.getId() > 0);
		assertTrue(emp3.getId() > 0);
	}

	@Test
	void test_findAllByAddressKeywordReturnSizeEqualZero_whenNoneMatchEmployeeAddress() {
		List<Employee> result = empService.findAllByAddressKeyword("ZZZ");

		assertTrue(result.isEmpty());
	}

	@Test
	void test_findAllReturnAllCurrentEmployeess() {
		List<Employee> result = empService.findAll();

		assertTrue(result.size() >= 3);
	}

	@Test
	void test_findAllByAddressKeywordReturnsSizeGreaterEqualThanThree_whenEmployeeWithAddressMatchesTimesThree() {
		List<Employee> result = empService.findAllByAddressKeyword("dummy");

		assertTrue(result.size() >= 3);
	}

	@Test
	void test_findById_returnsProperValues_WhenEmployeeHasBeenCreated() {
		assertTrue(emp1.getId() > 0);
		Optional<Employee> result = empService.findById((int) emp1.getId());

		assertTrue(result.isPresent());
		assertEquals("dummyFirst1", result.get().getFirstName());
		assertEquals("dummyLast1", result.get().getLastName());
		assertEquals("dummyAddress1", result.get().getAddress());
	}

	@Test
	void test_findByfirstAndLastNameReturnsNothing_whenNewEmployeeDoesNotMatch() {

		Optional<Employee> result = empService.findByFirstName("dummy1");
		Optional<Employee> result1 = empService.findByLastName("dummy1");
		assertTrue(result.isEmpty());
		assertTrue(result1.isEmpty());
	}

	@Test
	void test_findByfirstAndLastNameReturnsValue_whenNewEmployeeMatches() {

		Optional<Employee> result = empService.findByFirstName("dummyFirst1");
		Optional<Employee> result1 = empService.findByLastName("dummyLast1");
		assertTrue(result.isPresent());
		assertTrue(result1.isPresent());

	}

	@Test
	void test_saveUpdatesExistingEmpoloyee_WhenExistingEmployeedRetrievedThenUpdated() {
		Optional<Employee> empOpt = empService.findById(1);

		assertTrue(empOpt.isPresent());

		Employee emp = empOpt.get();
		emp.setFirstName("UpdatedName");
		empService.save(emp);

		assertEquals("UpdatedName", empService.findById(1).get().getFirstName());
	}

}
