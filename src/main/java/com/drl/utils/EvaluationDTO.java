package com.drl.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EvaluationDTO {
	
    private Integer evaluatorEvaluationId;
    private Integer evaluationFactorId;
    private Integer userId;
    private Integer obtainMarks;
    private String obtainLevel;
    private String recommendation;
    private String userComments;
    private Integer evaluationSetupEvaluatorId;
    private String factorName;
    private String userName;
    private int serEmpId;
    private int campaignId;
    
	
}
