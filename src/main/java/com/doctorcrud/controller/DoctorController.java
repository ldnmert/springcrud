package com.doctorcrud.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.doctorcrud.model.Doctor;
import com.doctorcrud.service.DoctorService;

@Controller
public class DoctorController {

	@Autowired
	DoctorService ds;

	@GetMapping("/addDoctorPage")
	String addDoctorPage(@ModelAttribute("doctorInfo") Doctor d, Model m) {
		List<String> doctorBranches = Arrays.asList("Ortopedi", "Dahiliye", "Genel Cerrahi", "Kulak burun bogaz",
				"Beyin cerrahisi");

		m.addAttribute("doctorBranches", doctorBranches);
		return "add-doctor-page.html";
	}

	@PostMapping("/addDoctor")
	String addDoctor(Doctor d) {

	
		ds.addDoctor(d);

		return "redirect:/";
	}

	@GetMapping("/deleteDoctor/{id}")
	String deleteDoctor(@PathVariable Long id) {
		ds.deleteDoctor(id);
		return "redirect:/";

	}

	@GetMapping("/")
	String doctors(Model m) {
		List<Doctor> allDoctors = ds.showAllDoctors();
		m.addAttribute("allDoctors", allDoctors);
		if (allDoctors.isEmpty()) {
			m.addAttribute("isThereAnyDoctor", true);
		}
		return "doctors.html";
	}

	@GetMapping("/updateDoctor/{id}")
	String updateDoctorByID(@PathVariable Long id, Model m) {

		Doctor myDoctor = ds.getDoctorByID(id);

		m.addAttribute("updatedDoctor", myDoctor);
		System.out.println(myDoctor);
		return "update-doctor-page.html";

	}

	@PostMapping("/filter")
	String filter(@RequestParam(required = false) String namefilter, @RequestParam(required = false) Integer ageMin,
			@RequestParam(required = false) Integer ageMax, Model m) {

		List<Doctor> forName = ds.forName(namefilter);
		List<Doctor> forAge = ds.ageBeween(ageMin, ageMax);
		List<Doctor> allDoctor = ds.showAllDoctors();
		List<Doctor> filteredDoctor = new ArrayList();
		for (Doctor doctor : allDoctor) {
			boolean isNameMatch = (namefilter == null || namefilter.isEmpty()) || forName.contains(doctor);
			boolean isAgeMatch = (ageMin == null && ageMax == null) || forAge.contains(doctor);

			if (isNameMatch && isAgeMatch) {
				filteredDoctor.add(doctor);
			}
		}

		m.addAttribute("allDoctors", filteredDoctor);
		if (filteredDoctor.isEmpty()) {
			m.addAttribute("isThereAnyDoctor", true);
		}

		return "doctors.html";
	}
}
