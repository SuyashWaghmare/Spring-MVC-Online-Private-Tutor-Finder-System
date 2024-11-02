package com.cjc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cjc.model.Admin;
import com.cjc.model.Book;
import com.cjc.model.Parent;
import com.cjc.model.Tutor;
import com.cjc.service.AdminService;
import com.cjc.service.BookService;
import com.cjc.service.ParentService;
import com.cjc.service.TutorService;

@Controller
@RequestMapping("/admin-api")
public class AdminController {

	@Autowired
	private AdminService adminservice;

	@Autowired
	private TutorService tutorservice;

	@Autowired
	private BookService bookservice;

	@Autowired
	private ParentService parentservice;

	@RequestMapping("/")
	public String Check() {
		return "admincontroller";
	}

	@RequestMapping("/regAdminPage")
	public String regAdminPage() {
		return "registeradmin";
	}

	@PostMapping("/registerAdmin")
	public String registerAdmin(@ModelAttribute Admin admin) {
		adminservice.loginSave(admin);
		return "registrationSuccess";
	}

	@RequestMapping("/loginAdmin")
	public String registerAdmin(@ModelAttribute Admin admin, Model model) {
		String loggedInAdmin = adminservice.adminLogin(admin);
		if (loggedInAdmin != null) {
			return "admincontroller";
		} else {
			model.addAttribute("errorMessage", "Invalid username or password");
			return "adminlogin";
		}
	}

	@RequestMapping("/adminLoginPage")
	public String adminLoginPage() {
		return "adminlogin";
	}

	@GetMapping("/viewAllParents")
	public String viewAllParents(Model model) {
		List<Parent> parents = parentservice.viewAllParents();
		model.addAttribute("parents", parents);
		return "viewAllParents";
	}

	@PostMapping("/addBooksByAdmin")
	public String addBooksByAdmin(@ModelAttribute Book book, Model model) {

		if (book.getBookid() == 0 || book.getBookname().trim().isEmpty()) {
			model.addAttribute("errorMessage", "Not Accepted");
		} else {
			bookservice.addBooks(book);
			model.addAttribute("successMessage", "Book Added Successfully");
		}
		return "addBooks";
	}

	@RequestMapping("/addBookPage")
	public String addBookPage() {
		return "addBooks";
	}

	@RequestMapping("/addTutorByAdminPage")
	public String addTutorByAdminPage() {
		return "addTutor";
	}

	@PostMapping("/addTutorByAdmin")
	public String addTutorByAdmin(@ModelAttribute Tutor tutor, Model model) {
		if (tutor.getTutorid() == 0 || tutor.getTutorname().trim().isEmpty() || tutor.getMobileno() == 0
				|| tutor.getMailid().trim().isEmpty() || tutor.getUsername().trim().isEmpty()
				|| tutor.getPassword().trim().isEmpty()) {
			model.addAttribute("errorMessage", "Not Accepted");
		} else {
			tutorservice.addTutor(tutor);
			model.addAttribute("successMessage", "Tutor Added Successfully");
		}
		return "addTutor";
	}

}
