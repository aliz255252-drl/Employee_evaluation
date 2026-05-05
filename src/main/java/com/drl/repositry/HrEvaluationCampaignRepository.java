package com.drl.repositry;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.drl.entities.HrEvaluationCampaign;
import com.drl.utils.EvaluationSetupResponse;

@Repository
public interface HrEvaluationCampaignRepository extends JpaRepository<HrEvaluationCampaign, Integer> {

	boolean existsByName(String name);

	// You can add custom query methods here if needed
	Optional<HrEvaluationCampaign> findById(Integer id);

	Optional<HrEvaluationCampaign> findFirstByName(String name);
	
    default Optional<HrEvaluationCampaign> findByName(String name) {
        return findFirstByName(name);
    }
	
    List<HrEvaluationCampaign> findByStatusTrue();
    
    @Query(value = "select distinct c.ser_evaluation_campaign_id as campaignId, emp.ser_employee_id as serEmpId, c.txt_name as name, "
    		+ "emp.txt_employee_id as employeeId, "
    		+ "emp.txt_name as empName, dep.txt_department_name as department, des.txt_name as designation, "
    		+" g.txt_employee_group as group, s.finish_date as finishDate, e.bln_evaluate"
    		+" from hr_tbl_evaluation_campaign c join hr_tbl_evaluation_setup s on s.ser_evaluation_campaign_id = c.ser_evaluation_campaign_id "
    		+" join hr_tbl_employee emp on emp.ser_employee_id = s.ser_employee_id "
            +" JOIN hr_tbl_employee_group g ON g.ser_employee_group_id = emp.ser_employee_group_id "  // ensure the column names match
            +" join hr_tbl_designation des on des.ser_designation_id = emp.ser_designation_id "
            +" join sysmgr_tbl_department dep on dep.ser_department_id = emp.ser_department_id "
    		+" join hr_tbl_evaluation_setup_evaluators e on e.ser_evaluation_setup_id = s.ser_evaluation_setup_id "
    		+" where e.ser_user_id = :ser_user_id", nativeQuery = true)
    List<EvaluationSetupResponse> findByEvaluators_serUserId(@Param("ser_user_id") Integer userId);
    
    

}
