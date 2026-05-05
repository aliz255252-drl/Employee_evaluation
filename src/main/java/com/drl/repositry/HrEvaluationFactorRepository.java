package com.drl.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.drl.entities.HrEvaluationFactor;

@Repository
public interface HrEvaluationFactorRepository extends JpaRepository<HrEvaluationFactor, Integer> {

	boolean existsByName(String name); // Optional: to prevent duplicates
	
	HrEvaluationFactor findFirstByName(String name);
	
    default HrEvaluationFactor findByName(String name) {
        return findFirstByName(name);
    }
	
	void deleteByName(String name); // Use the Java field name here
	
	 @Query(value = "SELECT e.* " +
	            "FROM hr_tbl_evaluation_factor e " + // use the correct table name, if it's different
	            "WHERE e.bln_status = 'true' " +  // make sure field names match the database columns
	            "AND e.txt_evaluation_group in(:employeeGroup, 'All') ", nativeQuery = true)
	List<HrEvaluationFactor> getActiveFactors(@Param("employeeGroup") String employeeGroup);
	 

	     @Query(value = """
	         SELECT array_agg(DISTINCT level) 
	         FROM (
	             SELECT unnest(levels) AS level
	             FROM hr_tbl_evaluation_factor
	             WHERE factor_type = 'Qualitative'
	               AND levels IS NOT NULL
	         ) t
	         """, nativeQuery = true)
	     String[] findUniqueQualitativeLevels();
	 }
 
	

