package com.drl.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EvaluationSetupResponse {
	
	private int campaignId;
	private int serEmpId;
	private String name;
	private String employeeId;
	private String empName;
	private String department;
	private String designation;
	private String group;
	private String finishDate;
	private String bln_evaluate;
	
	

}
