package com.drl.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//import org.springframework.web.bind.annotation.RequestParam;

import com.drl.entities.EvaluationGrades;
import com.drl.utils.EvaluationGradesDTO;
//import com.fasterxml.jackson.annotation.JacksonInject.Value;

public interface EvaluationGradeRepository extends JpaRepository<EvaluationGrades, Integer> {
	@Query(value = "select g.* from hr_tbl_evaluation_setup_grades g where g.ser_evaluation_setup_id = :ser_evaluation_setup_id", nativeQuery = true)
	List<EvaluationGradesDTO> findbySetupId(@Param (value = "ser_evaluation_setup_id") Integer id);

}
