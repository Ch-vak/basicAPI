package com.fdmgroup.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdmgroup.project.model.Employee;
import com.fdmgroup.project.service.EmployeeService;

/**
 * Manages the Communication with the Front side of the Server Has Customized
 * methods. findAllByAddressKeyword , which attempts to match and return whole
 * address
 *
 * <p>
 * Forwarding to the index page for incorrect attempts For additional error
 * messages please watch the console.
 *
 * @author Chrysostomos Vakasiras
 */

@Controller
public class EmployeeController {

	private final EmployeeService empServ;

	public EmployeeController(EmployeeService empServ) {
		super();
		this.empServ = empServ;
	}

	@GetMapping("allEmployees")
	public String allEmployees(Model model) {
		model.addAttribute("listOfEmployees", empServ.findAll());
		return "employee/all-employees";
	}

	@PostMapping("allEmployees")
	public String allEmployees1(Model model) {
		model.addAttribute("listOfEmployees", empServ.findAll());
		return "employee/all-employees";
	}

	@GetMapping("addEmployee")
	public String addEmployee(Model model) {
		model.addAttribute("employee", new Employee());
		return "employee/add-employee";
	}

	@PostMapping("/employeeSumbit")
	public String addEmployeeSumbit(@ModelAttribute Employee emp) {
		if ("" == emp.getFirstName() || "" == emp.getLastName() || "" == emp.getAddress()) {
			System.err.println("zero Value Found");
			return "forward:/";
		}
		empServ.save(emp);
		return "forward:/allEmployees";
	}

	@GetMapping("/editEmployee")
	public String editEmployee(@RequestParam int id, Model model) {
		model.addAttribute("employee", empServ.findById(id));
		return "employee/edit-employee";
	}

	/**
	 * Attempts to match based on keyword. (1)
	 * <p>
	 * In case on a non match keyword redirect to the index. (2)
	 * <p>
	 * 
	 * @param String keyword (3) Additionally Model.Originally it has been using it.
	 * @return List<Employee>
	 */
	@GetMapping("/findAllByAddressKeyword")
	public String findAllByAddressKeyword(@RequestParam String keyword, Model model) {
		if (empServ.findAllByAddressKeyword(keyword).isEmpty()) {
			System.err.println("not found");
			return "forward:/";
		}
		model.addAttribute("listOfEmployees", empServ.findAllByAddressKeyword(keyword));
		return "employee/all-employees";
	}

	@GetMapping("/findByFirstName")
	public String findByFirstName(@RequestParam String firstName, Model model) {
		if (empServ.findByFirstName(firstName).isPresent()) {
			model.addAttribute("listOfEmployees", empServ.findByFirstName(firstName).get());
			return "employee/all-employees";
		}
		System.err.println("not found");
		return "forward:/";
	}

	@GetMapping("/findByLastName")
	public String findByLastName(@RequestParam String lastName, Model model) {
		if (empServ.findByLastName(lastName).isPresent()) {
			model.addAttribute("listOfEmployees", empServ.findByLastName(lastName).get());
			return "employee/all-employees";
		}
		System.err.println("not found");
		return "forward:/";
	}
}
