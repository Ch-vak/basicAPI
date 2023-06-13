package com.fdmgroup.project.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fdmgroup.project.model.Employee;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTests {

	@Autowired
	private EmployeeController controller;

	@Autowired
	private MockMvc mvc;

	Employee emp;

	@BeforeEach
	void setUp() {
		emp = new Employee("firstName", "lastName", LocalDate.now(), "address");

	}

	@Test
	void test_controllerNotNull() {
		assertThat(controller).isNotNull();
	}

	@Test
	public void test_allEmployees_ReturnsCorrectView() throws Exception {
		mvc.perform(post("/allEmployees")).andExpect(status().isOk()).andExpect(view().name("employee/all-employees"));

		mvc.perform(get("/allEmployees")).andExpect(status().isOk()).andExpect(view().name("employee/all-employees"));
	}

	@Test
	void test_addEmployee_editEmployee_returnCorrectView() throws Exception {

		mvc.perform(
				post("/employeeSumbit").contentType(MediaType.APPLICATION_FORM_URLENCODED).flashAttr("employee", emp))
				.andDo(print()).andExpect(status().isOk()).andExpect(forwardedUrl("/allEmployees"));

	}

	@Test
	void test_checkThat_addEmployeeANDeditEmployee_returnsCorrectView() throws Exception {

		mvc.perform(get("/addEmployee")).andExpect(status().isOk()).andExpect(view().name("employee/add-employee"));

		mvc.perform(get("/editEmployee").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("id", "1")
				.flashAttr("employee", emp)).andExpect(status().isOk())
				.andExpect(view().name("employee/edit-employee"));
	}

	@Test
	void test_that_findAllByAddressKeyword_returns_correctValues() throws Exception {
		mvc.perform(get("/findAllByAddressKeyword").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("keyword", "ad").flashAttr("model", "emp")).andExpect(status().isOk())
				.andExpect(view().name("employee/all-employees"));
	}

	@Test
	void test_that_findByFirstName_and_findBylastName_returnVIEW_whenParameters_are_passedIN() throws Exception {

		mvc.perform(get("/findByFirstName").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("firstName", "firstName").flashAttr("model", emp)).andExpect(status().isOk())
				.andExpect(view().name("employee/all-employees"));

		mvc.perform(get("/findByLastName").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("lastName", "lastName").flashAttr("model", emp)).andExpect(status().isOk())
				.andExpect(view().name("employee/all-employees"));
	}

	@Test
	void test_that_findByFirstName_and_findBylastName_returnVIEW_whenNO__Parameters_are_passedIN() throws Exception {

		mvc.perform(get("/findByFirstName").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("firstName", "a")
				.flashAttr("model", emp)).andExpect(status().isOk()).andExpect(view().name("forward:/"));

		mvc.perform(get("/findByLastName").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("lastName", "a")
				.flashAttr("model", emp)).andExpect(status().isOk()).andExpect(view().name("forward:/"));
	}

	@Test
	void test_findALlByAddresssKeywordsCorrectView() throws Exception {
		mvc.perform(get("/findAllByAddressKeyword").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("keyword", "a").flashAttr("model", emp)).andExpect(status().isOk())
				.andExpect(view().name("employee/all-employees"));

		mvc.perform(get("/findAllByAddressKeyword").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("keyword", "z").flashAttr("model", emp)).andExpect(status().isOk())
				.andExpect(view().name("forward:/"));
	}

	@Test
	void test_findZeromatchesAndThrowIllegalArgumentException() throws Exception {
		Employee badEmp = new Employee("", null, LocalDate.now(), null);

		mvc.perform(post("/employeeSumbit").contentType(MediaType.APPLICATION_FORM_URLENCODED).flashAttr("employee",
				badEmp)).andDo(print()).andExpect(status().isOk()).andExpect(view().name("forward:/"));
	}

}
