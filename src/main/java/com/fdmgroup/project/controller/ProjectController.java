package com.fdmgroup.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProjectController {

	@RequestMapping("/")
	public String index() {
		return "index";
	}

}
