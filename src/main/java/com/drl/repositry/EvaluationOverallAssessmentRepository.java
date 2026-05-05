package com.drl.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.drl.entities.EvaluationOverallAssessment;

@Repository
public interface EvaluationOverallAssessmentRepository extends JpaRepository<EvaluationOverallAssessment, Long> {
    // You can add custom query methods if needed
}