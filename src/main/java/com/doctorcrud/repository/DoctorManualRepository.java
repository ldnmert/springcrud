package com.doctorcrud.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.doctorcrud.model.Doctor;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DoctorManualRepository {

	private final EntityManager em;

	public List<Doctor> filteredDoctor(String isim, Integer ageMın, Integer ageMax, String sehir) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Doctor> cq = cb.createQuery(Doctor.class);

		// select * from employee kısmı
		Root<Doctor> root = cq.from(Doctor.class);

		List<Predicate> allPredicate = new ArrayList();

		// where clause kısmı
		if (isim != null) {
			Predicate isimElemesi = cb.like(root.get("doctorName"), "%" + isim + "%");
			allPredicate.add(isimElemesi);
		}

		if (ageMın != null) {
			Predicate minYasElemesi = cb.greaterThanOrEqualTo(root.get("age"), ageMın);
			allPredicate.add(minYasElemesi);
		}

		if (ageMax != null) {
			Predicate maxYasElemesi = cb.lessThanOrEqualTo(root.get("age"), ageMax);
			allPredicate.add(maxYasElemesi);
		}

		if (sehir != null) {
			Predicate sehirElemesi = cb.equal(root.get("country"), sehir);
			allPredicate.add(sehirElemesi);
		}

		// final query select * from employee where doctorName like "%isim%" and age
		// between ageMın and ageMax

		cq.where(allPredicate.toArray(new Predicate[0]));
		TypedQuery<Doctor> finalQuery = em.createQuery(cq);
		return finalQuery.getResultList();

	}

	public List<Doctor> filteredDoctorEasyWay(String isim, Integer ageMın, Integer ageMax, String sehir) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Doctor> cq = cb.createQuery(Doctor.class);

		Root<Doctor> selectFromDoctor = cq.from(Doctor.class);

		Predicate isimElemesi = cb.equal(selectFromDoctor.get("doctorName"), isim);
		Predicate minYasElemesi = cb.greaterThan(selectFromDoctor.get("age"), ageMın);
		System.out.println("qqd");

		cq.where(cb.or(minYasElemesi, isimElemesi));
		TypedQuery<Doctor> tq = em.createQuery(cq);

		return tq.getResultList();

	}
}
