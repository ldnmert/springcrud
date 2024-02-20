package com.doctorcrud.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.doctorcrud.model.Doctor;

public interface DoctorService {

	void addDoctor(Doctor d);

	void deleteDoctor(Long x);

	Doctor getDoctorByID(Long d);

	List<Doctor> showAllDoctors();

	List<Doctor> ageBeween(Integer min, Integer max);

	List<Doctor> forName(String name);

	List<Doctor> forCity(String city);

	List<Doctor> findDoctorsByQuiters(String name, Integer minAge, Integer maxAge, String country);

	List<Doctor> findDoctorEasy(String name, Integer minAge, Integer maxAge, String country);

	List<Doctor> showAllDoctors(Specification<Doctor> spp);

	Page<Doctor> showAllDoctors(Specification<Doctor> spp, Pageable p);

}
