package com.doctorcrud.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.doctorcrud.model.Doctor;

import jakarta.persistence.criteria.Predicate;

public class DoctorSpecification {

	public static Specification<Doctor> kriterElemesi(String isim, Integer ageMın, Integer ageMax, String sehir) {
		return (root, query, cb) -> {
			query.where();
			List<Predicate> allPredicate = new ArrayList();

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

			Predicate allOfThem = cb.and(allPredicate.toArray(new Predicate[0]));

			return allOfThem;
		};

	}

	public static Specification<Doctor> isimElemesi(String doctorName) {

		return (root, query, criteriaBuxilder) -> {

			if (doctorName == null || doctorName.isEmpty())
				return criteriaBuxilder.conjunction();
			return criteriaBuxilder.like(root.get("doctorName"), "%" + doctorName + "%");

		};
	}

	public static Specification<Doctor> ageElemesi(Integer minYas, Integer maxYas) {
		return (root, query, criteriaBuxilder) -> {
			List<Predicate> pre = new ArrayList();

			Predicate minYasElemesi = criteriaBuxilder.greaterThanOrEqualTo(root.get("age"), minYas);
			Predicate maxYasElemesi = criteriaBuxilder.lessThanOrEqualTo(root.get("age"), maxYas);
			if (minYas != null)
				pre.add(minYasElemesi);
			if (maxYas != null)
				pre.add(maxYasElemesi);
			return criteriaBuxilder.and(pre.toArray(new Predicate[0]));

		};
	}

	public static Specification<Doctor> countryElemesi(String country) {
		return (root, query, criteriaBuxilder) -> {
			if (country == null || country.isEmpty())
				return criteriaBuxilder.conjunction();
			return criteriaBuxilder.like(root.get("country"), "%" + country + "%");

		};
	}

}
