package com.drl.repositry;

import com.drl.utils.HrEmployeeDto;
import com.drl.entities.HrEmployee;
import com.drl.entities.HrEvaluationCampaign;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface HrEmployeeRepository extends JpaRepository<HrEmployee, Integer> {

    	//List<HrEmployeeDto> findAllProjected();
	HrEmployee findFirstByEmployeeId(String employeeId);

    default HrEmployee findByEmployeeId(String employeeId) {
        return findFirstByEmployeeId(employeeId);
    }
	
	List<HrEmployee> findByNameContainingIgnoreCase(String name);
	
    @Query(value = "SELECT  e.ser_employee_id, e.txt_employee_id, e.txt_name, g.txt_employee_group, d.txt_name as designation, p.txt_department_name " +
            "FROM hr_tbl_employee e " + // use the correct table name, if it's different
            " JOIN hr_tbl_employee_group g ON g.ser_employee_group_id = e.ser_employee_group_id " + // ensure the column names match
            " join hr_tbl_designation d on d.ser_designation_id = e.ser_designation_id "
            + "join sysmgr_tbl_department p on p.ser_department_id = e.ser_department_id "+
            "WHERE e.txt_status_reason = :statusReason " +  // make sure field names match the database columns
            "AND g.txt_employee_group = :employeeGroup", nativeQuery = true)
    List<HrEmployeeDto> getEmployeeByGroup(@Param("statusReason") String statusReason, 
                                  @Param("employeeGroup") String employeeGroup);
    
    
    @Query(value = "SELECT  e.ser_employee_id, e.txt_employee_id, e.txt_name, g.txt_employee_group, d.txt_name as designation, p.txt_department_name " +
            "FROM hr_tbl_employee e " + // use the correct table name, if it's different
            " join hr_tbl_designation d on d.ser_designation_id = e.ser_designation_id "
            + "join sysmgr_tbl_department p on p.ser_department_id = e.ser_department_id "+
            " join hr_tbl_evaluation_setup s on s.ser_employee_id = e.ser_employee_id "+
            " JOIN hr_tbl_employee_group g ON g.ser_employee_group_id = e.ser_employee_group_id " + // ensure the column names match
            " join hr_tbl_evaluation_setup_evaluators ss on ss.ser_evaluation_setup_id = s.ser_evaluation_setup_id "+
            "WHERE ss.ser_user_id = :ser_user_id and s.ser_evaluation_campaign_id = :campaign_id " , nativeQuery = true)
    List<HrEmployeeDto> getEmployeebyEvaluator(@Param("ser_user_id") Integer userId, @Param("campaign_id") Integer campaignId);
    
    @Query(value = "select e.* from hr_tbl_employee e where e.txt_status_reason = :statusReason", nativeQuery = true)
    List<HrEmployee> findByStatusReason(@Param("statusReason") String statusReason);
    
}

