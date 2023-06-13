package com.fdmgroup.project.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.fdmgroup.project.model.Employee;

@DataJpaTest
class EmployeeRepositoryTests {

	@Autowired
	private EmployeeRepository empRepo;

	Employee emp1, emp2, emp3;

	@Test
	void test_01_GeneratesEntityId_whenEmployeePassedIn() {
		emp1 = new Employee();
		empRepo.save(emp1);

		assertTrue(emp1.getId() > 0);
	}

	@Test
	void test_02_findAllByAddressKeywordReturnSizeEqualZero_whenNoneMatchEmployeeAddress() {
		emp1 = new Employee("dummyFirst1", "dummyLast1", LocalDate.now(), "dummyAddress1");
		empRepo.save(emp1);
		List<Employee> result = empRepo.findAllByAddressKeyword("ZZZ");

		assertTrue(emp1.getId() > 0);
		assertTrue(result.isEmpty());

	}

	@Test
	void test_03_findAllByAddressKeywordReturnsSizeGreaterEqualThanThree_whenEmployeeWithAddressMatchesTimesThree() {
		emp1 = new Employee("dummyFirst1", "dummyLast1", LocalDate.now(), "dummyAddress1");
		emp2 = new Employee("dummyFirst2", "dummyLast2", LocalDate.now(), "dummyAddress2");
		emp3 = new Employee("dummyFirst3", "dummyLast3", LocalDate.now(), "dummyAddress3");
		empRepo.save(emp1);
		empRepo.save(emp2);
		empRepo.save(emp3);
		List<Employee> result = empRepo.findAllByAddressKeyword("dummy");

		assertTrue(emp1.getId() > 0);
		assertTrue(emp2.getId() > 0);
		assertTrue(emp3.getId() > 0);
		assertTrue(result.size() >= 3);
	}

	@Test
	void test_04__findById_returnsProperValues_WhenEmployeeHasBeenCreated() {
		emp1 = new Employee("dummyFirst1", "dummyLast1", LocalDate.now(), "dummyAddress1");
		empRepo.save(emp1);

		assertTrue(emp1.getId() > 0);

		Optional<Employee> result = empRepo.findById((int) emp1.getId());

		assertTrue(result.isPresent());
		assertEquals("dummyFirst1", result.get().getFirstName());
		assertEquals("dummyLast1", result.get().getLastName());
		assertEquals("dummyAddress1", result.get().getAddress());

	}

	@Test
	void test_05_findByfirstAndLastNameReturnsValue_whenNewEmployeeMatches() {
		emp1 = new Employee("dummyFirst1", "dummyLast1", LocalDate.now(), "dummyAddress1");
		empRepo.save(emp1);

		Optional<Employee> result = empRepo.findByFirstName("dummyFirst1");
		Optional<Employee> result1 = empRepo.findByLastName("dummyLast1");
		assertTrue(result.isPresent());
		assertTrue(result1.isPresent());
	}

	@Test
	void test_06_findByfirstAndLastNameReturnsNothing_whenNewEmployeeDoesNotMatch() {
		emp1 = new Employee("dummyFirst1", "dummyLast1", LocalDate.now(), "dummyAddress1");
		empRepo.save(emp1);

		Optional<Employee> result = empRepo.findByFirstName("dummy1");
		Optional<Employee> result1 = empRepo.findByLastName("dummy1");
		assertTrue(result.isEmpty());
		assertTrue(result1.isEmpty());
	}

	@Test
	void test_07_saveUpdatesExistingEmpoloyee_WhenExistingEmployeedRetrievedThenUpdated() {
		Optional<Employee> empOpt = empRepo.findById(1);

		assertTrue(empOpt.isPresent());

		Employee emp = empOpt.get();
		emp.setFirstName("UpdatedName");
		empRepo.save(emp);

		assertEquals("UpdatedName", empRepo.findById(1).get().getFirstName());
	}

	@Test
	void test_08_deleteByIdDeletesEmployee_whenProperIdPassesIn() {
		emp1 = new Employee("dummyFirst1", "dummyLast1", LocalDate.now(), "dummyAddress1");
		emp2 = new Employee("dummyFirst2", "dummyLast2", LocalDate.now(), "dummyAddress2");
		emp3 = new Employee("dummyFirst3", "dummyLast3", LocalDate.now(), "dummyAddress3");
		empRepo.save(emp1);
		empRepo.save(emp2);
		empRepo.save(emp3);

		long before = empRepo.count();
		empRepo.deleteById(1);
		long after = empRepo.count();
		assertTrue(after < before);
	}
}
