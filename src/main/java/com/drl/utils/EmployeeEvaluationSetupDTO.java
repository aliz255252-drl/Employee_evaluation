package com.drl.utils;

import java.util.List;
import java.util.Map;

import com.drl.entities.EvaluationGrades;
import com.drl.entities.EvaluatorEvaluation;
import com.drl.entities.EvaluatorEvaluationDocument;
import com.drl.entities.HrEmployee;
import com.drl.entities.HrEvaluationFactor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EmployeeEvaluationSetupDTO {
	
    @JsonProperty("evaluationCampaign")
	private String evaluationCampaign;	
    @JsonProperty("finishDate")
	private String finishDate;
    @JsonProperty("createdDate")
	private String createdDate;
    @JsonProperty("calculationMethod")
    private String calculationMethod;
    @JsonProperty("emplist")
	private List<HrEmployee> emplist;	
    @JsonProperty("factorlist")
	private List<HrEvaluationFactor> factorlist;	
    @JsonProperty("evaluatorlist")
	private List<EvaluatorDTO> evaluatorlist;
    @JsonProperty("gradelist")
	private List<EvaluationGradesDTO> gradelist;
    @JsonProperty("evualtions")
	private Map<String, List<EvaluatorEvaluationDTO>> evualtions;
    @JsonProperty("documentlist")
	private List<EvaluatorEvaluationDocumentDTO> documentlist;
		
}
