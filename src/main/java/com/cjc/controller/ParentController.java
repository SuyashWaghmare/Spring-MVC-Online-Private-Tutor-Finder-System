package com.cjc.controller;

import java.util.List;
import java.util.Map;

import javax.naming.spi.DirStateFactory.Result;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cjc.model.Book;
import com.cjc.model.Parent;
import com.cjc.model.Tutor;
import com.cjc.service.BookService;
import com.cjc.service.ParentService;
import com.cjc.service.TutorService;

@Controller
@RequestMapping("/parent-api")
public class ParentController {

	@Autowired
	private ParentService parentservice;

	@Autowired
	private TutorService tutorservice;

	@Autowired
	private BookService bookservice;

	@RequestMapping("/")
	public String Check() {
		return "parentcontroller";
	}

	@RequestMapping("/reParentPage")
	public String reParentPage(Model model) {
		 model.addAttribute("parent", new Parent());
		return "registerparent";
	}

	@PostMapping("/registerParents")
	public String registerParents(@Valid Parent parent, BindingResult result, Model model)
			throws NumberFormatException {

		if (result.hasErrors()) {

			return "registerparent";
		}

		parentservice.addParent(parent);
		return "parentregistrationSuccess";
	}

//
//	@PostMapping("/parentLogin")
//	ResponseEntity<String> parentLogin(@RequestBody Parent parent) {
//		return new ResponseEntity<String>(parentservice.parentLogin(parent), HttpStatus.OK);
//	}

	@RequestMapping("/parentLoginPage")
	public String parentLoginPage() {
		return "parentlogin";
	}

	@RequestMapping("/loginParent")
	public String registerAdmin(@ModelAttribute Parent parent, Model model) {
		String loggedInParent = parentservice.parentLogin(parent);
		if (loggedInParent != null) {
			return "parentcontroller";
		} else {
			model.addAttribute("errorMessage", "Invalid username or password");
			return "parentlogin";
		}
	}

	@GetMapping("/viewAllTutors")
	ResponseEntity<List<Tutor>> viewAllTutors() {
		return new ResponseEntity<List<Tutor>>(tutorservice.viewAllTutors(), HttpStatus.OK);

	}

	@GetMapping("/viewAllEbooks")
	ResponseEntity<List<Book>> viewAllEbooks() {
		return new ResponseEntity<List<Book>>(bookservice.viewAllEbooks(), HttpStatus.OK);
	}

	@PutMapping("/requestForDemoLecture/{parentmobileno}/{tutorid}")
	public ResponseEntity<String> requestForDemoLecture(@PathVariable long parentmobileno, @PathVariable int tutorid) {

		Parent existingParent = parentservice.getParentById(parentmobileno);

		if (existingParent == null) {
			return new ResponseEntity<String>("Parent not found.", HttpStatus.NOT_FOUND);
		}

		Tutor existingTutor = tutorservice.getTutorById(tutorid);

		if (existingTutor == null) {
			return new ResponseEntity<String>("Tutor not found.", HttpStatus.NOT_FOUND);
		}

		if (!"Yes".equals(existingTutor.getDemoAvailable())) {
			return new ResponseEntity<String>("Tutor is not available for a demo lecture.", HttpStatus.NOT_ACCEPTABLE);
		}

		existingParent.getRequestedTutors().add(existingTutor);

		existingTutor.getRequestedByParent().add(existingParent);

		parentservice.updateParent(existingParent);
		parentservice.sendDemoRequestEmailToParent(existingParent, existingTutor);

		tutorservice.updateTutor(existingTutor);
		tutorservice.sendDemoRequestEmailToTutor(existingParent, existingTutor);

		return new ResponseEntity<String>(" Demo Lecture Request sent  successfully.", HttpStatus.OK);
	}

	@GetMapping("/viewDemoRequestParentToTutor/{parentmobileno}")
	public ResponseEntity<List<Tutor>> viewDemoRequestParentToTutor(@PathVariable long parentmobileno) {
		Parent parent = parentservice.getParentById(parentmobileno);

		if (parent == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<Tutor> requestedTutors = parent.getRequestedTutors();

		return new ResponseEntity<>(requestedTutors, HttpStatus.OK);
	}

	@PostMapping("/bookTutor/{parentmobileno}/{tutorid}")
	public ResponseEntity<String> bookTutor(@PathVariable long parentmobileno, @PathVariable int tutorid) {
		Parent existingParent = parentservice.getParentById(parentmobileno);
		Tutor existingTutor = tutorservice.getTutorById(tutorid);

		if (existingParent == null || existingTutor == null) {
			return new ResponseEntity<>("Parent or Tutor not found.", HttpStatus.NOT_FOUND);
		}

		if (!"Yes".equals(existingTutor.getBookingAvailable())) {
			return new ResponseEntity<String>("Tutor is not available too Book", HttpStatus.NOT_ACCEPTABLE);
		}

		existingParent.getBookedTutors().add(existingTutor);

		existingTutor.getBookingParents().add(existingParent);

		parentservice.saveParentAgain(existingParent);
		tutorservice.saveTutorAgain(existingTutor);

		parentservice.senBookedTutorEmailToParent(existingParent, existingTutor);
		tutorservice.sendBookedTutorByParentEmailToTutor(existingParent, existingTutor);

		return new ResponseEntity<>("Tutor booked successfully.", HttpStatus.OK);
	}

	@GetMapping("/viewBookedTutor/{parentmobileno}")
	public ResponseEntity<List<Tutor>> viewBookedTutor(@PathVariable long parentmobileno) {
		Parent existingParent = parentservice.getParentById(parentmobileno);

		if (existingParent == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<Tutor> bookedTutors = existingParent.getBookedTutors();

		return new ResponseEntity<>(bookedTutors, HttpStatus.OK);
	}

	@PostMapping("/rateTutor/{parentmobileno}/{tutorid}")
	public ResponseEntity<String> rateTutor(@PathVariable long parentmobileno, @PathVariable int tutorid,
			@RequestBody Map<String, Double> ratingRequest) {

		Parent existingParent = parentservice.getParentById(parentmobileno);

		if (existingParent == null) {
			return new ResponseEntity<String>("Parent not found.", HttpStatus.NOT_FOUND);
		}

		Tutor existingTutor = tutorservice.getTutorById(tutorid);

		if (existingTutor == null) {
			return new ResponseEntity<String>("Tutor not found.", HttpStatus.NOT_FOUND);
		}

		double rating = ratingRequest.getOrDefault("rating", 0.0);

		double currentRating = existingTutor.getRating();
		int totalRatings = existingParent.getBookedTutors().size();
		double updatedRating = (currentRating * totalRatings + rating) / (totalRatings + 1);

		existingTutor.setRating(updatedRating);

		tutorservice.saveTutorRating(existingTutor);

		return new ResponseEntity<String>("Tutor rated successfully", HttpStatus.OK);

	}

}
