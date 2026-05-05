package com.drl.service;

import java.util.List;

import com.drl.entities.HrEvaluationFactor;

public interface HrEvaluationFactorService {
	
	HrEvaluationFactor createFactor(HrEvaluationFactor factor) throws Exception;

	List<HrEvaluationFactor> listAllFactors() throws Exception;

	void deleteFactorByName(String name) throws Exception;

	HrEvaluationFactor updateFactor(HrEvaluationFactor updatedFactor);
	
	List<HrEvaluationFactor> getActiveFactors(String group) throws Exception;
	
	public List<String> getUniqueQualitativeLevels(); 

 

}
