package com.fdmgroup.project.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTests {

	@Autowired
	private ProjectController controller;

	@Autowired
	private MockMvc mvc;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test_controllerNotNull() {
		assertThat(controller).isNotNull();
	}

	@Test
	void test_requestMapping_returns_HTMLPage() throws Exception {
		mvc.perform(get("/")).andDo(print()).andExpect(status().isOk()).andExpect(view().name("index"));
	}

}
