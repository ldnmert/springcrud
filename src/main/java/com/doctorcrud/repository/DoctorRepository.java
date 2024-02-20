package com.doctorcrud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.doctorcrud.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long>, JpaSpecificationExecutor<Doctor> {

	@Query("SELECT x FROM Doctor x WHERE (:min IS NULL OR x.age >= :min) AND (:max IS NULL OR x.age <= :max)")
	List<Doctor> ageBeween(@Param("min") Integer min, @Param("max") Integer max);

	@Query("SELECT x FROM Doctor x WHERE LOWER(x.doctorName) LIKE LOWER(CONCAT('%', :name, '%'))")
	List<Doctor> forName(@Param("name") String name);

	@Query("SELECT x FROM Doctor x WHERE x.country = :city")
	List<Doctor> forCity(@Param("city") String city);
}
