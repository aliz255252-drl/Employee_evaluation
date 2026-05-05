package com.drl.repositry;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.drl.entities.EvaluationSetupFactor;
import com.drl.entities.HrEvaluationFactor;

@Repository
public interface EvaluationSetupFactorRepository extends JpaRepository<EvaluationSetupFactor, Integer> {

	@Query(value = "select e.* from hr_tbl_evaluation_factor e join hr_tbl_evaluation_setup_factors s on s.ser_evaluation_factor_id = e.ser_evaluation_factor_id "
			+ "where s.ser_evaluation_setup_id = :ser_evaluation_setup_id "
			+ "", nativeQuery = true)
	List<HrEvaluationFactor> findByEvaluationSetupID(@Param (value = "ser_evaluation_setup_id") Integer id);
    //boolean existsByEvaluationSetupIdAndFactorId(Integer id, Integer factorId);
	
	@Transactional
	@Modifying
	@Query(value = "delete from hr_tbl_evaluation_setup_factors  WHERE ser_evaluation_setup_id = :setupId", nativeQuery = true)
	int deleteBySetupId(@Param("setupId") Integer setupId);
	@Transactional
	@Modifying
	@Query(value = "delete from hr_tbl_evaluation_setup_factors  WHERE ser_evaluation_setup_id = :setupId and ser_evaluation_factor_id = :factorId", nativeQuery = true)
	int deleteFctorsBySetupId(@Param("setupId") Integer setupId,
								@Param("factorId") Integer factorId);


}
