package com.drl.utils;
import java.util.List;
import com.drl.entities.EvaluatorEvaluationDocument;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmpEvaluationDTO {
	
	private List<EvaluationDTO> evaluationDTO; 
	private List<EvaluatorEvaluationDocument> documentsList;
		
}
