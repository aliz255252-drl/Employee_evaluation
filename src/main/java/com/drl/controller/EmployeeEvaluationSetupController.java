package com.drl.controller;

import java.util.List;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.drl.config.AppConstantUtil;
import com.drl.entities.HrEmployee;
import com.drl.entities.HrEvaluationCampaign;
import com.drl.entities.HrEvaluationFactor;
import com.drl.service.EvaluationSetupService;
import com.drl.utils.EmployeeEvaluationSetupDTO;
import com.drl.utils.EvaluationSetupResponse;
import com.drl.utils.EvaluatorDTO;
import com.drl.utils.HrEmployeeDto;

@RestController
@RequestMapping(AppConstantUtil.EVALUATION_SETUP)
public class EmployeeEvaluationSetupController {
	
	@Autowired
    private EvaluationSetupService evaluationSetupService;

    @PostMapping("/create")
    public ResponseEntity<String> createEvaluationSetup(@RequestBody EmployeeEvaluationSetupDTO evaluationSetupRequest) {
    	try {
        // Call the service method to handle the business logic
    		evaluationSetupService.createEvaluationSetups(evaluationSetupRequest);
    		return ResponseEntity.status(HttpStatus.SC_CREATED)
                    .body("Evaluation Setup Created Successfully!");
        
    } catch (IllegalArgumentException | IllegalStateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred");
    	}
	}
    
    @GetMapping("/getcampaignsetups")
    public ResponseEntity<List<HrEvaluationCampaign>> getCampaignSetups() {
    	return evaluationSetupService.getAllCampaignSetups();
	}
    
      
    @GetMapping("/getemployeebysetup/{campaignId}")
    public ResponseEntity<List<HrEmployeeDto>> getEmployeeBySetupId(@PathVariable Integer campaignId) {
        return evaluationSetupService.getSetupById(campaignId);                
    }
 
    
    @GetMapping("/getfactorsbyemp/{employeeid}/{campaignId}")
    public ResponseEntity<List<HrEvaluationFactor>> getFactorsByEmpId(@PathVariable Integer employeeid, @PathVariable Integer campaignId) {
        return evaluationSetupService.getFactorsByEmpId(employeeid, campaignId);                
    }
    
    
    @GetMapping("/getevaluatorsbyemp/{employeeid}/{campaignId}")
    public ResponseEntity<List<EvaluatorDTO>> getEvaluatorsByEmp(
            @PathVariable Integer employeeid,
            @PathVariable Integer campaignId) {
        return evaluationSetupService.getEvaluatorsByEmpId(employeeid, campaignId);                
    }
    
    
    @GetMapping("/getempbyevaluator")
    public ResponseEntity<List<EvaluationSetupResponse>> getEmpByEvaluator() {
        return evaluationSetupService.getEmpbyEvaluators();                
    }
    
    
	@DeleteMapping(value = "/employee/{employeeId}/{campaignId}")
	public ResponseEntity<Object> deleteEmployee(@PathVariable Integer employeeId, @PathVariable Integer campaignId) {
        return evaluationSetupService.deleteEmployeeSrtup(employeeId, campaignId);                
	}
    
	@DeleteMapping(value = "/factors/{employeeId}/{campaignId}/{factorId}")
	public ResponseEntity<Object> deleteFactors(@PathVariable Integer employeeId,
												@PathVariable Integer campaignId,
												@PathVariable Integer factorId) {
        return evaluationSetupService.deleteFactorsSetup(employeeId, campaignId, factorId);                
	}
	
	@DeleteMapping(value = "/evaluators/{employeeId}/{campaignId}/{user_name}")
	public ResponseEntity<Object> deleteEvaluators(@PathVariable Integer employeeId,
												@PathVariable Integer campaignId,
												@PathVariable String user_name) {
        return evaluationSetupService.deleteEvaluators(employeeId, campaignId, user_name);                
	}
	
	
    @PostMapping("/update")
    public ResponseEntity<Object> updateEvaluationSetup(@RequestBody EmployeeEvaluationSetupDTO evaluationSetupRequest) {
    	try {
        // Call the service method to handle the business logic
    		EmployeeEvaluationSetupDTO updatedDto = evaluationSetupService.updateEvaluationSetup(evaluationSetupRequest);
    		return ResponseEntity.status(HttpStatus.SC_CREATED)
                    .body(updatedDto);
        
    } catch (IllegalArgumentException | IllegalStateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred");
    	}
	}

	
	
}

