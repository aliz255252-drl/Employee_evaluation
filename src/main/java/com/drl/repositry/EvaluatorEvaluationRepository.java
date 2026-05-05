package com.drl.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.drl.entities.EvaluatorEvaluation;
import com.drl.utils.EvaluatorEvaluationDTO;

import jakarta.transaction.Transactional;

@Repository
public interface EvaluatorEvaluationRepository extends JpaRepository<EvaluatorEvaluation, Integer> {
	
	@Query(value = "SELECT e.* FROM hr_tbl_evaluator_evaluation e WHERE e.ser_evaluation_setup_evaluators_id = :setupId AND e.ser_user_id = :userId", nativeQuery = true)
	List<EvaluatorEvaluationDTO> findBySetupIdAndUserId(@Param(value =  "setupId") Integer setupId,
	                                                 @Param(value = "userId") Integer userId);
	
    @Modifying
    @Transactional
	@Query(value = "delete from hr_tbl_evaluator_evaluation where ser_evaluation_setup_evaluators_id = :setupId", nativeQuery = true)
	int deletebysetupId(@Param(value =  "setupId") Integer setupId);
    
    
    @Query(value = """
            SELECT CASE 
                WHEN EXISTS (
                    SELECT 1 FROM hr_tbl_evaluator_evaluation 
                    WHERE ser_evaluation_setup_evaluators_id IN (
                        SELECT ser_evaluation_setup_evaluators_id 
                        FROM hr_tbl_evaluation_setup_evaluators 
                        WHERE ser_evaluation_setup_id = :setupId
                    )
                ) OR EXISTS (
                    SELECT 1 FROM hr_tbl_evaluation_setup_evaluators 
                    WHERE ser_evaluation_setup_id = :setupId AND bln_evaluate = 'true'
                )
                THEN FALSE ELSE TRUE END
            """, nativeQuery = true)
        boolean shouldProceedWithEvaluation(@Param("setupId") Integer setupId);

	
	
}
