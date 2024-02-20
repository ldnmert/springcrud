package com.doctorcrud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.doctorcrud.model.Doctor;
import com.doctorcrud.repository.DoctorManualRepository;
import com.doctorcrud.repository.DoctorRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DoctorServiceImpl implements DoctorService {

	@Autowired
	DoctorRepository dr;
	@Autowired
	DoctorManualRepository dmr;

	@Override
	public void addDoctor(Doctor d) {

		dr.save(d);
	}

	@Override
	public void deleteDoctor(Long x) {

		dr.deleteById(x);
		;
	}

	@Override
	public List<Doctor> showAllDoctors() {

		return dr.findAll();

	}

	@Override
	public Doctor getDoctorByID(Long lo) {

		return dr.findById(lo).orElseThrow(() -> new EntityNotFoundException("doctor not found"));

	}

	@Override
	public List<Doctor> ageBeween(Integer min, Integer max) {
		List<Doctor> a = dr.ageBeween(min, max);
		return a;
	}

	@Override
	public List<Doctor> forName(String name) {
		List<Doctor> a = dr.forName(name);
		return a;
	}

	@Override
	public List<Doctor> forCity(String city) {
		List<Doctor> a = dr.forCity(city);
		return a;
	}

	@Override
	public List<Doctor> findDoctorsByQuiters(String name, Integer minAge, Integer maxAge, String country) {
		List<Doctor> filteredDoctor = dmr.filteredDoctor(name, minAge, maxAge, country);
		if (!filteredDoctor.isEmpty())
			return filteredDoctor;
		else
			throw new EntityNotFoundException();
	}

	@Override
	public List<Doctor> findDoctorEasy(String name, Integer minAge, Integer maxAge, String country) {
		return dmr.filteredDoctorEasyWay(name, minAge, maxAge, country);
	}

	@Override
	public List<Doctor> showAllDoctors(Specification<Doctor> spp) {

		return dr.findAll(spp);
	}

	@Override
	public Page<Doctor> showAllDoctors(Specification<Doctor> spp, Pageable p) {

		return dr.findAll(spp, p);
	}

}
