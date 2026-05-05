package com.drl.repositry;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.drl.entities.EmpEvaluationSetup;
import com.drl.entities.HrEmployee;
import com.drl.entities.HrEvaluationCampaign;
import com.drl.entities.HrEvaluationFactor;
import com.drl.utils.EvaluationAssignmentView;
import com.drl.utils.EvaluatorDTO;
import com.drl.utils.HrEmployeeDto;

public interface EmpEvaluationSetupRepository extends JpaRepository<EmpEvaluationSetup, Integer> {

	public abstract boolean existsByCampaign_CampaignId(Integer campaignId);

	public abstract boolean existsByCampaign_CampaignIdAndEmployee_EmpId(Integer campaignId, Integer empId);

	@Query(value = "select *  from hr_tbl_evaluation_campaign where ser_evaluation_campaign_id "
			+ " in (select distinct(ser_evaluation_campaign_id) from hr_tbl_evaluation_setup) ", nativeQuery = true)
	List<HrEvaluationCampaign> findbyCampaignSetups();

	@Query(value = "select e.ser_employee_id, e.txt_employee_id, e.txt_name, g.txt_employee_group, des.txt_name as designation, d.txt_department_name as txt_department_name from hr_tbl_employee e \r\n"
			+ "join hr_tbl_employee_group g on g.ser_employee_group_id = e.ser_employee_group_id\r\n"
			+ "join sysmgr_tbl_department d on d.ser_department_id = e.ser_department_id\r\n"
			+ "join hr_tbl_designation des on des.ser_designation_id = e.ser_designation_id\r\n"
			+ "where e.ser_employee_id in ("
			+ "select ser_employee_id from hr_tbl_evaluation_setup where ser_evaluation_campaign_id = :campaignId)"
			+ "", nativeQuery = true)
	List<HrEmployeeDto> findEmployeeBySetupID(@Param(value = "campaignId") Integer campaignId);

	@Query(value = "select distinct * from hr_tbl_evaluation_factor where ser_evaluation_factor_id in ("
			+ "select ser_evaluation_factor_id from hr_tbl_evaluation_setup_factors where "
			+ "ser_evaluation_setup_id in (select ser_evaluation_setup_id from hr_tbl_evaluation_setup "
			+ "where ser_employee_id = :employeeId and ser_evaluation_campaign_id = :campaignId))", nativeQuery = true)
	List<HrEvaluationFactor> getFactorsByEmpId(@Param(value = "employeeId") Integer empId,
			@Param(value = "campaignId") Integer campaignId);

	@Query(value = "select distinct u.txt_user_name as userName, e.evaluator_level, e.bln_evaluate from Sysmgr_tbl_users u join hr_tbl_evaluation_setup_evaluators e "
			+ " on u.ser_user_id = e.ser_user_id"
			+ " where "
			+ " ser_evaluation_setup_id in (select ser_evaluation_setup_id from hr_tbl_evaluation_setup "
			+ " where ser_employee_id = :employeeId and ser_evaluation_campaign_id = :campaignId)", nativeQuery = true)
	List<EvaluatorDTO> getEvaluatorsByEmpId(@Param(value = "employeeId") Integer empId,
			@Param(value = "campaignId") Integer campaignId);

	@Query(value = "select distinct "
			+ "c.ser_evaluation_campaign_id as campaignId, "
			+ "emp.ser_employee_id as serEmpId, "
			+ "c.txt_name as name, "
			+ "emp.txt_employee_id as employeeId, "
			+ "emp.txt_name as empName, "
			+ "dep.txt_department_name as department, "
			+ "des.txt_name as designation, "
			+ "g.txt_employee_group as groupName, "
			+ "s.finish_date as finishDate, "
			+ "e.bln_evaluate as blnEvaluate "
			+ "from hr_tbl_evaluation_campaign c "
			+ "join hr_tbl_evaluation_setup s on s.ser_evaluation_campaign_id = c.ser_evaluation_campaign_id "
			+ "join hr_tbl_employee emp on emp.ser_employee_id = s.ser_employee_id "
			+ "join hr_tbl_employee_group g ON g.ser_employee_group_id = emp.ser_employee_group_id "
			+ "join hr_tbl_designation des on des.ser_designation_id = emp.ser_designation_id "
			+ "join sysmgr_tbl_department dep on dep.ser_department_id = emp.ser_department_id "
			+ "join hr_tbl_evaluation_setup_evaluators e on e.ser_evaluation_setup_id = s.ser_evaluation_setup_id "
			+ "where e.ser_user_id = :ser_user_id", nativeQuery = true)
	List<EvaluationAssignmentView> findByEvaluators_serUserId(@Param("ser_user_id") Integer userId);

	@Query(value = "select distinct "
			+ "c.ser_evaluation_campaign_id as campaignId, "
			+ "emp.ser_employee_id as serEmpId, "
			+ "c.txt_name as name, "
			+ "emp.txt_employee_id as employeeId, "
			+ "emp.txt_name as empName, "
			+ "dep.txt_department_name as department, "
			+ "des.txt_name as designation, "
			+ "g.txt_employee_group as groupName, "
			+ "s.finish_date as finishDate, "
			+ "e.bln_evaluate as blnEvaluate "
			+ "from hr_tbl_evaluation_campaign c "
			+ "join hr_tbl_evaluation_setup s on s.ser_evaluation_campaign_id = c.ser_evaluation_campaign_id "
			+ "join hr_tbl_employee emp on emp.ser_employee_id = s.ser_employee_id "
			+ "join hr_tbl_employee_group g ON g.ser_employee_group_id = emp.ser_employee_group_id "
			+ "join hr_tbl_designation des on des.ser_designation_id = emp.ser_designation_id "
			+ "join sysmgr_tbl_department dep on dep.ser_department_id = emp.ser_department_id "
			+ "join hr_tbl_evaluation_setup_evaluators e on e.ser_evaluation_setup_id = s.ser_evaluation_setup_id "
			+ "where e.ser_user_id = :ser_user_id and s.ser_evaluation_campaign_id = :campaignId", nativeQuery = true)
	List<EvaluationAssignmentView> findAssignmentsByEvaluatorAndCampaign(@Param("ser_user_id") Integer userId,
			@Param("campaignId") Integer campaignId);

	@Query(value = "select distinct "
			+ "c.ser_evaluation_campaign_id as campaignId, "
			+ "emp.ser_employee_id as serEmpId, "
			+ "c.txt_name as name, "
			+ "emp.txt_employee_id as employeeId, "
			+ "emp.txt_name as empName, "
			+ "dep.txt_department_name as department, "
			+ "des.txt_name as designation, "
			+ "g.txt_employee_group as groupName, "
			+ "s.finish_date as finishDate, "
			+ "CASE WHEN EXISTS (SELECT 1 FROM hr_tbl_evaluation_setup_evaluators eval WHERE eval.ser_evaluation_setup_id = s.ser_evaluation_setup_id AND eval.bln_evaluate != 'true') THEN 'false' ELSE 'true' END as blnEvaluate "
			+ "from hr_tbl_evaluation_campaign c "
			+ "join hr_tbl_evaluation_setup s on s.ser_evaluation_campaign_id = c.ser_evaluation_campaign_id "
			+ "join hr_tbl_employee emp on emp.ser_employee_id = s.ser_employee_id "
			+ "join hr_tbl_employee_group g ON g.ser_employee_group_id = emp.ser_employee_group_id "
			+ "join hr_tbl_designation des on des.ser_designation_id = emp.ser_designation_id "
			+ "join sysmgr_tbl_department dep on dep.ser_department_id = emp.ser_department_id ", nativeQuery = true)
	List<EvaluationAssignmentView> findAllAssignments();

	@Query(value = "select distinct "
			+ "c.ser_evaluation_campaign_id as campaignId, "
			+ "emp.ser_employee_id as serEmpId, "
			+ "c.txt_name as name, "
			+ "emp.txt_employee_id as employeeId, "
			+ "emp.txt_name as empName, "
			+ "dep.txt_department_name as department, "
			+ "des.txt_name as designation, "
			+ "g.txt_employee_group as groupName, "
			+ "s.finish_date as finishDate, "
			+ "CASE WHEN EXISTS (SELECT 1 FROM hr_tbl_evaluation_setup_evaluators eval WHERE eval.ser_evaluation_setup_id = s.ser_evaluation_setup_id AND eval.bln_evaluate != 'true') THEN 'false' ELSE 'true' END as blnEvaluate "
			+ "from hr_tbl_evaluation_campaign c "
			+ "join hr_tbl_evaluation_setup s on s.ser_evaluation_campaign_id = c.ser_evaluation_campaign_id "
			+ "join hr_tbl_employee emp on emp.ser_employee_id = s.ser_employee_id "
			+ "join hr_tbl_employee_group g ON g.ser_employee_group_id = emp.ser_employee_group_id "
			+ "join hr_tbl_designation des on des.ser_designation_id = emp.ser_designation_id "
			+ "join sysmgr_tbl_department dep on dep.ser_department_id = emp.ser_department_id "
			+ "where s.ser_evaluation_campaign_id = :campaignId", nativeQuery = true)
	List<EvaluationAssignmentView> findAllAssignmentsByCampaign(@Param("campaignId") Integer campaignId);

	@Query(value = "	select e.* from hr_tbl_employee e join hr_tbl_evaluation_setup s on s.ser_employee_id = e.ser_employee_id "
			+ "where s.ser_evaluation_setup_id = :ser_emp_id "
			+ "", nativeQuery = true)
	List<HrEmployee> findByEmpId(@Param(value = "ser_emp_id") Integer id);

	List<EmpEvaluationSetup> findAllByCampaign_CampaignIdAndEmployee_EmpId(Integer campaignId, Integer empId);

	List<EmpEvaluationSetup> findAllByCampaignAndEmployee(HrEvaluationCampaign campaign, HrEmployee employee);

	default Optional<EmpEvaluationSetup> findFirstByCampaign_CampaignIdAndEmployee_EmpId(Integer campaignId,
			Integer empId) {
		List<EmpEvaluationSetup> list = findAllByCampaign_CampaignIdAndEmployee_EmpId(campaignId, empId);
		return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
	}

	default Optional<EmpEvaluationSetup> findFirstByCampaignAndEmployee(HrEvaluationCampaign campaign,
			HrEmployee employee) {
		List<EmpEvaluationSetup> list = findAllByCampaignAndEmployee(campaign, employee);
		return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
	}

	default Optional<EmpEvaluationSetup> findByCampaign_CampaignIdAndEmployee_EmpId(Integer campaignId, Integer empId) {
		return findFirstByCampaign_CampaignIdAndEmployee_EmpId(campaignId, empId);
	}

	default Optional<EmpEvaluationSetup> findByCampaignAndEmployee(HrEvaluationCampaign campaign, HrEmployee employee) {
		return findFirstByCampaignAndEmployee(campaign, employee);
	}

	List<EmpEvaluationSetup> findByEmployee_EmpId(Integer empId);

}
