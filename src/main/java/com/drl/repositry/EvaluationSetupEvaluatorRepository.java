package com.drl.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.drl.entities.EvaluationSetupEvaluator;
import com.drl.utils.EvaluatorDTO;

@Repository
public interface EvaluationSetupEvaluatorRepository extends JpaRepository<EvaluationSetupEvaluator, Integer> {
	
	
	@Query(value = "select e.* from hr_tbl_evaluation_setup_evaluators e where e.ser_evaluation_setup_id = :setuId and ser_user_id = :userId", nativeQuery = true)
	EvaluationSetupEvaluator findbyevaluationsetupId(@Param (value = "setuId") Integer id, @Param (value = "userId") Integer userid);

	@Query(value = "select e.txt_user_name as userName, s.evaluator_level, s.bln_evaluate from sysmgr_tbl_users e join hr_tbl_evaluation_setup_evaluators s "
			+ "on s.ser_user_id = e.ser_user_id "
			+ "where s.ser_evaluation_setup_id = :ser_evaluation_setup_id "
			+ "", nativeQuery = true)
	List<EvaluatorDTO> findEvaluatorsBySetupId(@Param (value = "ser_evaluation_setup_id") Integer id);
    //boolean existsByEvaluationSetupIdAndEvaluatorIdAndEvaluatorLevel(Integer evaluationSetupId,
                                                                   // Integer evaluatorId,
                                                                   // Integer evaluatorLevel);
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM hr_tbl_evaluation_setup_evaluators ee WHERE ee.ser_evaluation_setup_id = :setupId", nativeQuery = true)
	int deleteBySetupId(@Param("setupId") Integer setupId);
	
	@Modifying
	@Transactional
	@Query(value = "delete from hr_tbl_evaluation_setup_evaluators  WHERE ser_evaluation_setup_id = :setupId and ser_user_id = :userId", nativeQuery = true)
	int deleteevaluatorsBySetupId(@Param("setupId") Integer setupId,
								@Param("userId") Integer userId);
		
}

