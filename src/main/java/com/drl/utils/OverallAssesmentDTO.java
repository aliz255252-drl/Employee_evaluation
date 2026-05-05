package com.drl.utils;

import lombok.Data;

@Data
public class OverallAssesmentDTO {
	
	private long overallAssesmentId;
    private Long empId;
    private Integer totalMarks;
    private Integer obtainMarks;
    private String additionalComments;
    private String gradingExpression;
    private String addatonalComments;
    private Integer campaignId;
    private Integer employeeId;
    
}
