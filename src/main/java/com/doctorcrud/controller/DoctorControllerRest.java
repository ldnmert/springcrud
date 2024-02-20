package com.doctorcrud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.doctorcrud.model.Doctor;
import com.doctorcrud.repository.DoctorSpecification;
import com.doctorcrud.service.DoctorService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/restapi")
public class DoctorControllerRest {

	@Autowired
	DoctorService ds;

	@GetMapping()
	public ResponseEntity<List<Doctor>> getAllDoctors() {
		List<Doctor> doctors = ds.showAllDoctors();

		return new ResponseEntity<>(doctors, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getDoctorById(@PathVariable Long id) {

		try {
			return new ResponseEntity<>(ds.getDoctorByID(id), HttpStatus.OK);
		}

		catch (EntityNotFoundException enfe) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<String> addDoctor(@RequestBody Doctor d) {

		ds.addDoctor(d);
		return new ResponseEntity<>("Eklendi", HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateDoctor(@RequestBody Doctor d, @PathVariable Long id) {
		if (ds.getDoctorByID(id) == null)
			return new ResponseEntity<>("Doctor Not Found", HttpStatus.NOT_FOUND);
		else {
			d.setDoctorID(id);
			ds.addDoctor(d);
			return new ResponseEntity<>("Guncellendi", HttpStatus.OK);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {
		ds.deleteDoctor(id);
		return new ResponseEntity<>("Doktor	 silindi..", HttpStatus.OK);
	}

	@GetMapping("/doctor")
	public ResponseEntity<?> getDoctorByQuiters(@RequestParam(required = false) String nameFounder,
			@RequestParam(required = false) Integer minAge, @RequestParam(required = false) Integer maxAge,
			@RequestParam(required = false) String country) {

		try {
			List<Doctor> result = ds.findDoctorsByQuiters(nameFounder, minAge, maxAge, country);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (EntityNotFoundException enfe) {
			return new ResponseEntity<>("Kriterlere gore doktor bulunamadı", HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/doctorsecondway")
	public ResponseEntity<List<Doctor>> getDoctorByQuiters2(@RequestParam(required = false) String nameFounder,
			@RequestParam(required = false) Integer minAge, @RequestParam(required = false) Integer maxAge,
			@RequestParam(required = false) String country) {
		List<Doctor> doctorsq = ds.findDoctorEasy(nameFounder, minAge, maxAge, country);
		return new ResponseEntity<>(doctorsq, HttpStatus.OK);
	}

	@GetMapping("/doctorspec")
	public ResponseEntity<List<Doctor>> getDoctorsUsingSpec(@RequestParam(required = false) String nameFounder,
			@RequestParam(required = false) Integer minAge, @RequestParam(required = false) Integer maxAge,
			@RequestParam(required = false) String country) {
		Specification<Doctor> spp = Specification.where(DoctorSpecification.isimElemesi(nameFounder))
				.and(DoctorSpecification.ageElemesi(minAge, maxAge)).and(DoctorSpecification.countryElemesi(country));

		return new ResponseEntity<>(ds.showAllDoctors(spp), HttpStatus.OK);
	}

}