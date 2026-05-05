package com.drl.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.drl.entities.EmpEvaluationSetup;
import com.drl.repositry.EmpEvaluationSetupRepository;
import com.drl.repositry.EvaluationGradeRepository;
import com.drl.utils.EvaluationGradesDTO;

@Service
public class EmployeeEvaluationService {

    @Autowired
    private EmpEvaluationSetupRepository setupRepository;

    @Autowired
    private EvaluationGradeRepository gradeRepository;

    public Map<Integer, List<EvaluationGradesDTO>> getGradesForEmployee(Integer employeeId, Integer campaignId) {
        List<EmpEvaluationSetup> setups;
        if (campaignId != null) {
            setups = setupRepository.findAllByCampaign_CampaignIdAndEmployee_EmpId(campaignId, employeeId);
        } else {
            setups = setupRepository.findByEmployee_EmpId(employeeId);
        }
        
        Map<Integer, List<EvaluationGradesDTO>> evaluationDetails = new HashMap<>();
        
        for (EmpEvaluationSetup setup : setups) {
            Integer setupId = setup.getId();
            List<EvaluationGradesDTO> grades = gradeRepository.findbySetupId(setupId);
            evaluationDetails.put(setupId, grades);
        }
        
        return evaluationDetails;
    }
}
