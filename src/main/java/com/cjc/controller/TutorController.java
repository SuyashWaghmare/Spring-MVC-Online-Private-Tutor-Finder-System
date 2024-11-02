package com.cjc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cjc.model.Admin;
import com.cjc.model.Parent;
import com.cjc.model.Tutor;
import com.cjc.service.ParentService;
import com.cjc.service.TutorService;

@Controller
@RequestMapping("/tutor-api")
public class TutorController {

	@Autowired
	private ParentService parentservice;

	@Autowired
	private TutorService tutorservice;

	@RequestMapping("/")
	public String Check() {
		return "tutorcontroller";
	}

	@RequestMapping("/tutorLoginPage")
	public String adminLoginPage() {
		return "tutorlogin";
	}

	@RequestMapping("/loginTutor")
	public String registerAdmin(@ModelAttribute Tutor tutor, Model model) {
		String loggedInTutor = tutorservice.tutorLogin(tutor);
		if (loggedInTutor != null) {
			return "tutorcontroller";
		} else {
			model.addAttribute("errorMessage", "Invalid username or password");
			return "tutorlogin";
		}
	}

//	@GetMapping("/viewBookings/{tutorid}")
//	public ResponseEntity<List<Parent>> viewBookings(@PathVariable int tutorid) {
//		Tutor tutor = tutorservice.getTutorById(tutorid);
//
//		if (tutor == null) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//
//		List<Parent> bookingParents = tutor.getBookingParents();
//
//		return new ResponseEntity<>(bookingParents, HttpStatus.OK);
//	}
	
	// Not Done
	@GetMapping("/viewBookings")
	public String viewBookings(@RequestParam int tutorid, Model model) {
	    Tutor tutor = tutorservice.getTutorById(tutorid);

	    if (tutor == null) {
	        return "notFound"; 
	    }

	    List<Parent> bookingParents = tutor.getBookingParents();
	    model.addAttribute("bookingParents", bookingParents);

	    return "viewBookings";
	}



	@GetMapping("/viewDemoRequests/{tutorid}")
	public ResponseEntity<List<Parent>> viewDemoRequests(@PathVariable int tutorid) {
		Tutor tutor = tutorservice.getTutorById(tutorid);

		if (tutor == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<Parent> requestedByParents = tutor.getRequestedByParent();

		return new ResponseEntity<>(requestedByParents, HttpStatus.OK);
	}

	@PutMapping("/acceptDemoRequest/{tutorid}/{parentmobileno}")
	public ResponseEntity<String> acceptDemoRequest(@PathVariable int tutorid, @PathVariable long parentmobileno) {
		Tutor existingTutor = tutorservice.getTutorById(tutorid);
		Parent existingParent = parentservice.getParentById(parentmobileno);

		if (existingTutor == null || existingParent == null) {
			return new ResponseEntity<>("Tutor or Parent not found.", HttpStatus.NOT_FOUND);
		}

		List<Parent> requestedByParents = existingTutor.getRequestedByParent();

		if (!requestedByParents.contains(existingParent)) {
			return new ResponseEntity<>("Parent did not request a demo lecture from this tutor.", HttpStatus.NOT_FOUND);
		}

		parentservice.acceptedDemoRequestByTutorMail(existingParent, existingTutor);

		return new ResponseEntity<>("Demo request accepted successfully.", HttpStatus.OK);
	}

	@PutMapping("updateTutorProfile/{tutorid}")
	public ResponseEntity<String> updateTutorProfile(@PathVariable int tutorid, @RequestBody Tutor tutor) {

		Tutor existingTutor = tutorservice.getTutorById(tutorid);

		if (existingTutor == null) {
			return new ResponseEntity<String>("Tutor not found.", HttpStatus.NOT_FOUND);
		}

		existingTutor.setTutorname(tutor.getTutorname());
		existingTutor.setExperience(tutor.getExperience());
		existingTutor.setMobileno(tutor.getMobileno());
		existingTutor.setMailid(tutor.getMailid());
		existingTutor.setDemoAvailable(tutor.getDemoAvailable());
		existingTutor.setBookingAvailable(tutor.getBookingAvailable());
		existingTutor.setUsername(tutor.getUsername());
		existingTutor.setPassword(tutor.getPassword());

		tutorservice.updateTutorData(existingTutor);

		return new ResponseEntity<String>("Tutor profile updated successfully", HttpStatus.OK);
		
		
		
	}

}
